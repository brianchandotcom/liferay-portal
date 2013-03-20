<#assign macro = macroElement.attributeValue("macro")>

<#assign x = macro?last_index_of("#")>

executeScopeVariables = new HashMap<String, String>();

executeScopeVariables.putAll(commandScopeVariables);

<#if macroElement.element("var")??>
	<#assign varElements = macroElement.elements("var")>

	<#list varElements as varElement>
		<#assign varName = varElement.attributeValue("name")>

		<#assign varValue = varElement.attributeValue("value")>

		executeScopeVariables.put("${varName}", "${varValue}");
	</#list>
</#if>

${seleniumBuilderFileUtil.getVariableName(macro?substring(0, x))}Macro.${macro?substring(x + 1)}(executeScopeVariables)