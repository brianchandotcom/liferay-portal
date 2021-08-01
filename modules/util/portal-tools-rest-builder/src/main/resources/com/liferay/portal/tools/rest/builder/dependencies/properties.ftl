#
# This is a generated file.
#

api.version=${openAPIYAML.info.version}
<#assign
	javaDataType = freeMarkerTool.getJavaDataType(configYAML, openAPIYAML, schemaName)!""

	generateBatch = configYAML.generateBatch && javaDataType?has_content
/>
<#if !stringUtil.equals(schemaName, "openapi") && generateBatch>
batch.engine.task.item.delegate=true
</#if>
<#if configYAML.resourceApplicationSelect??>
osgi.jaxrs.application.select=${configYAML.resourceApplicationSelect}
<#elseif configYAML.application??>
osgi.jaxrs.application.select=(osgi.jaxrs.name=${configYAML.application.name})
</#if>
osgi.jaxrs.resource=true