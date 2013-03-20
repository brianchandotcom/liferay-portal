<#assign macro = macroElement.attributeValue("macro")>

<#assign x = macro?last_index_of("#")>

parameters = new HashMap<String, String>();

parameters.putAll(localVariables);

<#if macroElement.element("param")??>
	<#assign paramElements = macroElement.elements("param")>

	<#list paramElements as paramElement>
		<#assign paramName = paramElement.attributeValue("name")>

		<#assign paramValue = paramElement.attributeValue("value")>

		parameters.put("${paramName}", "${paramValue}");
	</#list>
</#if>

${seleniumBuilderFileUtil.getVariableName(macro?substring(0, x))}Macro.${macro?substring(x + 1)}(parameters)