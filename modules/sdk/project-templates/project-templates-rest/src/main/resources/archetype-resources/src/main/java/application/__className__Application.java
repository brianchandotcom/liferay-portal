package ${package}.application;

import java.util.Collections;
import java.util.Set;

#if (${liferayVersion.startsWith("7.0")})
import javax.ws.rs.ApplicationPath;
#end
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
#if (${liferayVersion.startsWith("7.1")} || ${liferayVersion.startsWith("7.2")} || ${liferayVersion.startsWith("7.3")})
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
#end

/**
 * @author ${author}
 */
#if (${liferayVersion.startsWith("7.0")})
@ApplicationPath("/greetings")
@Component(immediate = true, #else
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/greetings",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Greetings.Rest"
	},
#end
	service = Application.class
)
public class ${className}Application extends Application {

	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	@GET
	@Produces("text/plain")
	public String working() {
		return "It works!";
	}

	@GET
	@Path("/morning")
	@Produces("text/plain")
	public String hello() {
		return "Good morning!";
	}

	@GET
	@Path("/morning/{name}")
	@Produces("text/plain")
	public String morning(
		@PathParam("name") String name,
		@QueryParam("drink") String drink) {

		String greeting = "Good Morning " + name;

		if (drink != null) {
			greeting += ". Would you like some " + drink + "?";
		}

		return greeting;
	}

}