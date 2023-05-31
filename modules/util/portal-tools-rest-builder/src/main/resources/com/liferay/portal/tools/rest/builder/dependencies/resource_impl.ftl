package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;

import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

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
	<#assign
		generateBatch =
			freeMarkerTool.generateBatch(
				configYAML,
				freeMarkerTool.getJavaDataType(configYAML, openAPIYAML, schemaName)!""
				freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName),
				schemaName)
	/>

	<#if generateBatch>
		service = {${schemaName}Resource.class, VulcanBatchEngineTaskItemDelegate.class}
	<#else>
		service = ${schemaName}Resource.class
	</#if>
)
public class ${schemaName}ResourceImpl extends Base${schemaName}ResourceImpl {
}