#
# This is a generated file.
#

api.version=${openAPIYAML.info.version}
<#if !stringUtil.equals(schemaName, "openapi") && configYAML.generateBatch>
batch.engine.task.item.delegate=true
</#if>
osgi.jaxrs.application.select=(osgi.jaxrs.name=${configYAML.application.name})
osgi.jaxrs.resource=true