package ${configYAML.apiPackagePath}.internal.resource;

import ${configYAML.apiPackagePath}.resource.${schemaName}Resource;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author ${configYAML.author}
 */
@Component(
	properties = "OSGI-INF/${schemaPath}.properties", scope = ServiceScope.PROTOTYPE,
	service = ${schemaName}Resource.class
)
public class ${schemaName}ResourceImpl extends Base${schemaName}ResourceImpl {
}