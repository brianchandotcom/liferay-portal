<#assign conditionalCaseNames = ["and", "condition", "contains", "equals", "isset", "not", "or"]>

<#if ifConditionalElement.getName() == "and">
		<#assign conditionalCases = ifConditionalElement.elements()>

		<#assign firstCase = true>

		(
			<#list conditionalCases as conditionalCase>
				<#if conditionalCaseNames?seq_contains(conditionalCase.getName())>
					<#assign ifConditionalElement = conditionalCase>

					<#include "if_conditional_element.ftl">
				</#if>

				<#if conditionalCase_has_next>
					&&
				</#if>
			</#list>
		)
<#elseif ifConditionalElement.getName() == "condition">
	<#if ifConditionalElement.attributeValue("action")??>
		<#assign actionElement = ifConditionalElement>

		(<#include "action_log_element.ftl">) && (<#include "action_element.ftl">)
	<#elseif ifConditionalElement.attributeValue("macro")??>
		<#assign macroElement = ifConditionalElement>

		<#include "macro_element.ftl">
	</#if>
<#elseif ifConditionalElement.getName() == "contains">
	<#assign string = ifConditionalElement.attributeValue("string")>

	<#assign substring = ifConditionalElement.attributeValue("substring")>

	(
	<#if string?contains("${") && string?contains("}")>
		RuntimeVariables.evaluateVariable("${string}", commandScopeVariables)
	<#else>
		"${string}"
	</#if>
	)

	.contains

	(
	<#if substring?contains("${") && substring?contains("}")>
		RuntimeVariables.evaluateVariable("${substring}", commandScopeVariables)
	<#else>
		"${substring}"
	</#if>
	)
<#elseif ifConditionalElement.getName() == "equals">
	<#assign arg1 = ifConditionalElement.attributeValue("arg1")>

	<#assign arg2 = ifConditionalElement.attributeValue("arg2")>

	(
	<#if arg1?contains("${") && arg1?contains("}")>
		RuntimeVariables.evaluateVariable("${arg1}", commandScopeVariables)
	<#else>
		"${arg1}"
	</#if>
	)

	.equals

	(
	<#if arg2?contains("${") && arg2?contains("}")>
		RuntimeVariables.evaluateVariable("${arg2}", commandScopeVariables)
	<#else>
		"${arg2}"
	</#if>
	)
<#elseif ifConditionalElement.getName() == "isset">
	<#assign var = ifConditionalElement.attributeValue("var")>

	<#if var?contains("${") && var?contains("}")>
		RuntimeVariables.variableExists(RuntimeVariables.evaluateVariable("${var}", commandScopeVariables))
	</#if>

	RuntimeVariables.variableExists("${var}", commandScopeVariables)
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

		<#include "if_conditional_element.ftl">
	)
<#elseif ifConditionalElement.getName() == "or" >
		<#assign conditionalCases = ifConditionalElement.elements()>

		<#assign firstCase = true>

		(
			<#list conditionalCases as conditionalCase>
				<#if conditionalCaseNames?seq_contains(conditionalCase.getName())>
					<#assign ifConditionalElement = conditionalCase>

					<#include "if_conditional_element.ftl">
				</#if>

				<#if conditionalCase_has_next>
					||
				</#if>
			</#list>
		)
</#if>