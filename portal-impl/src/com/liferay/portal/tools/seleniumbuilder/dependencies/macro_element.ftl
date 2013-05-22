<#assign macro = macroElement.attributeValue("macro")>

<#assign x = macro?last_index_of("#")>

<#assign void = macroNameStack.push(macro?substring(0, x))>

<#if macroElement.getName() == "execute">
	executeScopeVariables = new HashMap<String, String>();

	executeScopeVariables.putAll(commandScopeVariables);

	<#if macroElement.element("var")??>
		<#assign varElements = macroElement.elements("var")>

		<#list varElements as varElement>
			<#assign varName = varElement.attributeValue("name")>

			<#assign varValue = varElement.attributeValue("value")>

			<#if varValue?contains("${") && varValue?contains("}")>
				<#assign varValue = varValue?replace("${", "\" + commandScopeVariables.get(\"")>

				<#assign varValue = varValue?replace("}", "\") + \"")>
			</#if>

			executeScopeVariables.put("${varName}", "${varValue}");
		</#list>
	</#if>
</#if>

<#if macroElement.getName() == "execute" && macro?substring(x + 1)?starts_with("is")>
	return
</#if>

${seleniumBuilderFileUtil.getVariableName(macro?substring(0, x))}Macro.${macro?substring(x + 1)}(executeScopeVariables)

<#if macroElement.getName() == "execute">
	;
</#if>

<#assign void = macroNameStack.pop()>