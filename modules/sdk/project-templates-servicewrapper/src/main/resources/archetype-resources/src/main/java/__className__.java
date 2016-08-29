#set( $lastIndexOf = $serviceClass.lastIndexOf(".") )
#set( $substringIndex = $lastIndexOf + 1 )
#set( $serviceClassName = $serviceClass.substring($substringIndex) )
package ${package};

import ${serviceClass};

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
	},
	service = ServiceWrapper.class
)
public class ${className} extends ${serviceClassName} {

	public ${className}() {
		super(null);
	}

}