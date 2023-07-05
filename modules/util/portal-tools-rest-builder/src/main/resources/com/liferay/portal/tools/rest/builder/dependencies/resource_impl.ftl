package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;

import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author ${configYAML.author}
 */
@Component(
	<#if configYAML.liferayEnterpriseApp>enabled = false,</#if>
	properties = "OSGI-INF/liferay/rest/${escapedVersion}/${stringUtil.toLowerCase(schemaPath)}.properties",
	scope = ServiceScope.PROTOTYPE,
	service = ${schemaName}Resource.class
)
public class ${schemaName}ResourceImpl extends Base${schemaName}ResourceImpl {
}