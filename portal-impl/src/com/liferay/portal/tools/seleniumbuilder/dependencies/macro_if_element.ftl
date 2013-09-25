<#if ifElement.getName() == "elseif">
	<#assign ifType = "else if">
<#elseif ifElement.getName() == "while">
	<#assign ifType = "while">
<#else>
	<#assign ifType = "if">
</#if>

${ifType} (
	<#if ifElement.element("and")??>
		<#assign conditionalElement = ifElement.element("and")>
	<#elseif ifElement.element("condition")??>
		<#assign conditionalElement = ifElement.element("condition")>
	<#elseif ifElement.element("contains")??>
		<#assign conditionalElement = ifElement.element("contains")>
	<#elseif ifElement.element("equals")??>
		<#assign conditionalElement = ifElement.element("equals")>
	<#elseif ifElement.element("isset")??>
		<#assign conditionalElement = ifElement.element("isset")>
	<#elseif ifElement.element("not")??>
		<#assign conditionalElement = ifElement.element("not")>
	<#elseif ifElement.element("or")??>
		<#assign conditionalElement = ifElement.element("or")>
	</#if>

	<#assign ifConditionalElement = conditionalElement>

	<#include "macro_if_conditional_element.ftl">
) {
	<#assign lineNumber = conditionalElement.attributeValue("line-number")>

	liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pending");

	liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");

	<#assign thenElement = ifElement.element("then")>

	<#assign lineNumber = thenElement.attributeValue("line-number")>

	liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pending");

	<#assign blockElement = thenElement>

	<#include "macro_block_element.ftl">

	<#assign lineNumber = thenElement.attributeValue("line-number")>

	liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
}