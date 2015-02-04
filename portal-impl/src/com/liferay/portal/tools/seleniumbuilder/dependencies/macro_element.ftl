<#assign macro = macroElement.attributeValue("macro")>

<#assign x = macro?last_index_of("#")>

<#assign void = macroNameStack.push(macro?substring(0, x))>

<#assign variableContext = variableContextStack.peek()>

<#if macroElement.getName() == "execute">
	executeScopeVariables = new HashMap<String, String>();

	executeScopeVariables.putAll(${variableContext});

	<#if macroElement.element("var")??>
		<#assign varElements = macroElement.elements("var")>

		<#assign void = variableContextStack.push("executeScopeVariables")>

		<#list varElements as varElement>
			<#include "var_element.ftl">
		</#list>

		<#assign void = variableContextStack.pop()>
	</#if>
</#if>

<#assign x = macro?last_index_of("#")>

<#if macroElement.getName() == "execute" && macro?substring(x + 1)?starts_with("is")>
	return
</#if>

${seleniumBuilderFileUtil.getVariableName(macro?substring(0, x))}Macro.${macro?substring(x + 1)}(executeScopeVariables)

<#if macroElement.getName() == "execute">
	;
</#if>

<#assign void = macroNameStack.pop()>