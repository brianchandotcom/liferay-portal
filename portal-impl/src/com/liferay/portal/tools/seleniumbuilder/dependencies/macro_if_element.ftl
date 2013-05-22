<#if ifElement.getName() == "elseif">
	<#assign ifType = "else if">
<#elseif ifElement.getName() == "while">
	<#assign ifType = "while">
<#else>
	<#assign ifType = "if">
</#if>

${ifType} (
	<#if ifElement.element("condition")??>
		<#assign conditionElement = ifElement.element("condition")>

		<#if conditionElement.attributeValue("action")??>
			<#assign actionElement = conditionElement>

			<#include "action_element.ftl">
		<#elseif conditionElement.attributeValue("macro")??>
			<#assign macroElement = conditionElement>

			<#include "macro_element.ftl">
		</#if>
	<#elseif ifElement.element("contains")??>
		<#assign containsElement = ifElement.element("contains")>

		<#assign string = containsElement.attributeValue("string")>

		<#if string?contains("${") && string?contains("}")>
			<#assign string = string?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign string = string?replace("}", "\") + \"")>
		</#if>

		<#assign substring = containsElement.attributeValue("substring")>

		<#if substring?contains("${") && substring?contains("}")>
			<#assign substring = substring?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign substring = substring?replace("}", "\") + \"")>
		</#if>

		("${string}").contains("${substring}")
	<#elseif ifElement.element("equals")??>
		<#assign equalsElement = ifElement.element("equals")>

		<#assign arg1 = equalsElement.attributeValue("arg1")>

		<#if arg1?contains("${") && arg1?contains("}")>
			<#assign arg1 = arg1?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign arg1 = arg1?replace("}", "\") + \"")>
		</#if>

		<#assign arg2 = equalsElement.attributeValue("arg2")>

		<#if arg2?contains("${") && arg2?contains("}")>
			<#assign arg2 = arg2?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign arg2 = arg2?replace("}", "\") + \"")>
		</#if>

		("${arg1}").equals("${arg2}")
	<#elseif ifElement.element("isset")??>
		<#assign equalsElement = ifElement.element("isset")>

		<#assign var = equalsElement.attributeValue("var")>

		<#if var?contains("${") && var?contains("}")>
			<#assign var = var?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign var = var?replace("}", "\") + \"")>
		</#if>

		commandScopeVariables.containsKey("${var}")
	</#if>
) {
	<#if ifElement.element("condition")??>
		<#assign conditionalElement = ifElement.element("condition")>
	<#elseif ifElement.element("contains")??>
		<#assign conditionalElement = ifElement.element("contains")>
	<#elseif ifElement.element("equals")??>
		<#assign conditionalElement = ifElement.element("equals")>
	<#elseif ifElement.element("isset")??>
		<#assign conditionalElement = ifElement.element("isset")>
	</#if>

	<#assign lineNumber = conditionalElement.attributeValue("line-number")>

	liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pending");

	liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");

	<#assign thenElement = ifElement.element("then")>

	<#assign lineNumber = thenElement.attributeValue("line-number")>

	liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pending");

	<#assign blockElement = thenElement>

	<#include "macro_block_element.ftl">

	<#assign lineNumber = thenElement.attributeValue("line-number")>

	liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
}