package ${package}.${template};

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceWrapper;
import ${serviceWrapperClassPackage}.${serviceWrapperClassName};

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(service = ServiceWrapper.class)
public class ${className}ServiceWrapper extends ${serviceWrapperClassName} {

	public ${className}ServiceWrapper() {
		super(null);
	}
}