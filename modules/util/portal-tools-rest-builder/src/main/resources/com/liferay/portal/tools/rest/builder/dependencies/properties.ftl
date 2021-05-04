#
# This is a generated file.
#

api.version=${openAPIYAML.info.version}
<#if !stringUtil.equals(schemaName, "openapi") && configYAML.generateBatch>
batch.engine.task.item.delegate=true
</#if>
<#if configYAML.application??>
osgi.jaxrs.application.select=(osgi.jaxrs.name=${configYAML.application.name})
</#if>
osgi.jaxrs.resource=true