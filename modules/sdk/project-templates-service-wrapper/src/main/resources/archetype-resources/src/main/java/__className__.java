#set ($lastIndexOf = $serviceWrapperClass.lastIndexOf("."))
#set ($substringIndex = $lastIndexOf + 1)
#set ($serviceWrapperClassName = $serviceWrapperClass.substring($substringIndex))
package ${package};

import ${serviceWrapperClass};

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
	},
	service = ServiceWrapper.class
)
public class ${className} extends ${serviceWrapperClassName} {

	public ${className}() {
		super(null);
	}

}