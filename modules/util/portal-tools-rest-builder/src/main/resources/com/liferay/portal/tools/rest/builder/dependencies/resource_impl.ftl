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
	properties = "OSGI-INF/liferay/rest/${escapedVersion}/${stringUtil.toLowerCase(schemaPath)}.properties",
	scope = ServiceScope.PROTOTYPE,
	service =
	<#if configYAML.generateBatch>
		{BatchEngineTaskItemDelegate.class, ${schemaName}Resource.class}
	<#else>
        ${schemaName}Resource.class
	</#if>
)
public class ${schemaName}ResourceImpl extends Base${schemaName}ResourceImpl
	<#if configYAML.generateBatch>
		implements BatchEngineTaskItemDelegate<${schemaName}>
	</#if>
	{
}