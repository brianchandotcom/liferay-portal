<#assign displayElement = macroElement>

<#include "element_open_html.ftl">

<#assign macro = macroElement.attributeValue("macro")>

<#assign x = macro?last_index_of("#")>

<#assign macroCommand = macro?substring(x + 1)>

<#assign macroName = macro?substring(0, x)>

<#assign void = macroNameStack.push(macroName)>

<#assign macroRootElement = seleniumBuilderContext.getMacroRootElement(macroName)>

<#assign macroCommandElements = macroRootElement.elements("command")>

<#list macroCommandElements as macroCommandElement>
	<#assign macroCommandName = macroCommandElement.attributeValue("name")>

	<#if macroCommandName == macroCommand>
		<#assign macroRootVarElements = macroRootElement.elements("var")>

		<#list macroRootVarElements as macroRootVarElement>
			<#assign lineNumber = macroRootVarElement.attributeValue("line-number")>

			<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
				<#assign displayElement = macroRootVarElement>

				<#include "element_whole_html.ftl">
			</li>
		</#list>

		<#assign macroVarElements = macroElement.elements("var")>

		<#list macroVarElements as macroVarElement>
			<#assign lineNumber = macroVarElement.attributeValue("line-number")>

			<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
				<#assign displayElement = macroVarElement>

				<#include "element_whole_html.ftl">
			</li>
		</#list>

		<#assign macroBlockElement = macroCommandElement>

		<#include "macro_block_element_html.ftl">

		<#break>
	</#if>
</#list>

<#assign void = macroNameStack.pop()>

<#assign displayElement = macroElement>

<#include "element_close_html.ftl">