import data.Repos
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.application.*
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import models.Customer
import models.Employee
import models.Ticket

fun Routing.configureApiEndpoints() {
    route("/customers") {
        get {
            val list = Repos.Customers.getAll()
            call.respond(list)
        }

        get("/{customerId}") {
            val id = call.parameters["customerId"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "customerId has to be an integer")
                return@get
            }

            // Repos.Customers[id] is the same as Repos.Customers.get(id)
            val entity = Repos.Customers[id]

            call.respond(entity ?: HttpStatusCode.NotFound)
        }

        post {
            try {
                val entity = call.receive<Customer>()
                Repos.Customers.add(entity)
                call.respond(HttpStatusCode.NoContent)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "")
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "")
            } catch (ex: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "")
            }
        }

        delete("/{customerId}") {
            val id = call.parameters["customerId"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            if (Repos.Customers.delete(id)) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

    route("/employees") {
        get {
            val list = Repos.Employees.getAll()
            call.respond(list)
        }

        get("/{employeeId:Int}") {
            val id = call.parameters["employeeId"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            // Repos.Employees[id] is the same as Repos.Employees.get(id)
            val entity = Repos.Employees[id]

            call.respond(entity ?: HttpStatusCode.NotFound)
        }

        post {
            try {
                val entity = call.receive<Employee>()
                Repos.Employees.add(entity)
                call.respond(HttpStatusCode.NoContent)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "")
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "")
            } catch (ex: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "")
            }
        }

        delete("/{employeeId}") {
            val id = call.parameters["employeeId"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            if (Repos.Employees.delete(id)) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

    route("/tickets") {
        get {
            val list = Repos.Tickets.getAll()
            call.respond(list)
        }

        get("/{ticketId:Int}") {
            val id = call.parameters["ticketId"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            // Repos.Tickets[id] is the same as Repos.Tickets.get(id)
            val entity = Repos.Tickets[id]

            call.respond(entity ?: HttpStatusCode.NotFound)
        }

        post {
            try {
                val entity = call.receive<Ticket>()
                Repos.Tickets.add(entity)
                call.respond(HttpStatusCode.NoContent)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "")
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "")
            } catch (ex: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "")
            }
        }

        delete("/{ticketId}") {
            val id = call.parameters["ticketId"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            if (Repos.Tickets.delete(id)) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

    // neuer Endpoint für die supporterRole - muss noch getestet werden, ob die so funktioniert
    route("/supporter") {
        get {
            val list = Repos.Servers.getAll()
            call.respond(list)
        }
        get("/{serverId}") {
            val serverId = call.parameters["serverId"]?.toIntOrNull()
            if (serverId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val entity = Repos.Servers.get(serverId)?.supporterRoleID

            call.respond(entity ?: HttpStatusCode.NotFound)
        }
    }
}