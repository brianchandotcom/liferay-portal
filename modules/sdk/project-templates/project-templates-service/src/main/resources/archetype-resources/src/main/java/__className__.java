#set ($lastIndexOf = $serviceClass.lastIndexOf("."))
#set ($substringIndex = $lastIndexOf + 1)
#set ($serviceClassName = $serviceClass.substring($substringIndex))
package ${package};

import ${serviceClass};

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
		// TODO enter required service properties
	},
	service = ${serviceClassName}.class
)
public class ${className} implements ${serviceClassName} {

	// TODO enter required service methods

}