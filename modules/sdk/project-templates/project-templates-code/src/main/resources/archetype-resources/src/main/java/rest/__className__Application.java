package ${package}.${template};

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

/**
 * @author ${author}
 */
@ApplicationPath("/$className.toLowerCase()/user")
@Component(
	immediate = true, property = "jaxrs.application=true",
	service = Application.class
)
public class ${className}Application extends Application {

	@Activate
	public void activate() {
		_log.log(LogService.LOG_INFO, "${className} activated.");
	}

	@Override
	public Set<Object> getSingletons() {
		return Collections.singleton((Object)this);
	}

	@GET
	@Path("/$className.toLowerCase()/list")
	@Produces("text/plain")
	public String getUsers() {
		StringBuilder sb = new StringBuilder();

		for (User user : _userLocalService.getUsers(-1, -1)) {
			sb.append(user.getFullName());
			sb.append(",\n");
		}

		return sb.toString();
	}

	@Reference
	private LogService _log;

	@Reference
	private volatile UserLocalService _userLocalService;

}