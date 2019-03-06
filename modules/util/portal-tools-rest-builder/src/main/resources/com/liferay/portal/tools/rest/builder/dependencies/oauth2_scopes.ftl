#
# This is a generated file.
#

<#list freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName) as javaMethodSignature>
	public ${javaMethodSignature.returnType} ${configYAML.apiPackagePath}.internal.resource.${escapedVersion}.${schemaName}ResourceImpl.${javaMethodSignature.methodName}(${freeMarkerTool.getResourceOAuth2ScopesParameters(javaMethodSignature.javaMethodParameters)}) throws java.lang.Exception:everything.<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get")>read<#else>write</#if>
</#list>