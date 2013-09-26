<#assign conditionalCaseNames = ["and", "condition", "contains", "equals", "isset", "not", "or"]>

<#if ifConditionalElement.getName() == "and">
		<#assign conditionalCases = ifConditionalElement.elements()>

		<#assign firstCase = true>

		(
			<#list conditionalCases as conditionalCase>
				<#if conditionalCaseNames?seq_contains(conditionalCase.getName())>
					<#assign ifConditionalElement = conditionalCase>

					<#include "macro_if_conditional_element.ftl">
				</#if>

				<#if conditionalCase_has_next>
					&&
				</#if>
			</#list>
		)
<#elseif ifConditionalElement.getName() == "condition">
	<#if ifConditionalElement.attributeValue("action")??>
		<#assign actionElement = ifConditionalElement>

		<#include "action_element.ftl">
	<#elseif ifConditionalElement.attributeValue("macro")??>
		<#assign macroElement = ifConditionalElement>

		<#include "macro_element.ftl">
	</#if>
<#elseif ifConditionalElement.getName() == "contains">
	<#assign string = ifConditionalElement.attributeValue("string")>

	<#if string?contains("${") && string?contains("}")>
		<#assign string = string?replace("${", "\" + commandScopeVariables.get(\"")>

		<#assign string = string?replace("}", "\") + \"")>
	</#if>

	<#assign substring = ifConditionalElement.attributeValue("substring")>

	<#if substring?contains("${") && substring?contains("}")>
		<#assign substring = substring?replace("${", "\" + commandScopeVariables.get(\"")>

		<#assign substring = substring?replace("}", "\") + \"")>
	</#if>

	("${string}").contains("${substring}")
<#elseif ifConditionalElement.getName() == "equals">
	<#assign arg1 = ifConditionalElement.attributeValue("arg1")>

	<#if arg1?contains("${") && arg1?contains("}")>
		<#assign arg1 = arg1?replace("${", "\" + commandScopeVariables.get(\"")>

		<#assign arg1 = arg1?replace("}", "\") + \"")>
	</#if>

	<#assign arg2 = ifConditionalElement.attributeValue("arg2")>

	<#if arg2?contains("${") && arg2?contains("}")>
		<#assign arg2 = arg2?replace("${", "\" + commandScopeVariables.get(\"")>

		<#assign arg2 = arg2?replace("}", "\") + \"")>
	</#if>

	("${arg1}").equals("${arg2}")
<#elseif ifConditionalElement.getName() == "isset">
	<#assign var = ifConditionalElement.attributeValue("var")>

	<#if var?contains("${") && var?contains("}")>
		<#assign var = var?replace("${", "\" + commandScopeVariables.get(\"")>

		<#assign var = var?replace("}", "\") + \"")>
	</#if>

	commandScopeVariables.containsKey("${var}")
<#elseif ifConditionalElement.getName() == "not">
	!(
		<#if ifConditionalElement.element("and")??>
			<#assign ifConditionalElement = ifConditionalElement.element("and")>
		<#elseif ifConditionalElement.element("condition")??>
			<#assign ifConditionalElement = ifConditionalElement.element("condition")>
		<#elseif ifConditionalElement.element("contains")??>
			<#assign ifConditionalElement = ifConditionalElement.element("contains")>
		<#elseif ifConditionalElement.element("equals")??>
			<#assign ifConditionalElement = ifConditionalElement.element("equals")>
		<#elseif ifConditionalElement.element("isset")??>
			<#assign ifConditionalElement = ifConditionalElement.element("isset")>
		<#elseif ifConditionalElement.element("not")??>
			<#assign ifConditionalElement = ifConditionalElement.element("not")>
		<#elseif ifConditionalElement.element("or")??>
			<#assign ifConditionalElement = ifConditionalElement.element("or")>
		</#if>

		<#include "macro_if_conditional_element.ftl">
	)
<#elseif ifConditionalElement.getName() == "or" >
		<#assign conditionalCases = ifConditionalElement.elements()>

		<#assign firstCase = true>

		(
			<#list conditionalCases as conditionalCase>
				<#if conditionalCaseNames?seq_contains(conditionalCase.getName())>
					<#assign ifConditionalElement = conditionalCase>

					<#include "macro_if_conditional_element.ftl">
				</#if>

				<#if conditionalCase_has_next>
					||
				</#if>
			</#list>
		)
</#if>