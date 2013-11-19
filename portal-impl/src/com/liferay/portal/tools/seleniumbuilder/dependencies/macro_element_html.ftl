<#assign displayElement = macroElement>

<#include "element_open_html.ftl">

<#assign macro = macroElement.attributeValue("macro")>

<#assign x = macro?last_index_of("#")>

<#assign macroCommand = macro?substring(x + 1)>

<#assign macroName = macro?substring(0, x)>

<#assign macroRootElement = seleniumBuilderContext.getMacroRootElement(macroName)>

<#if macroRootElement.attributeValue("extends")??>
	<#assign extendedMacroName = macroRootElement.attributeValue("extends")>

	<#assign extendedMacroRootElement = seleniumBuilderContext.getMacroRootElement(extendedMacroName)>
</#if>

<#assign macroCommandNames = seleniumBuilderContext.getMacroCommandNames(macroName)>

<#if extendedMacroName??>
	<#if macroRootElement.element("command")??>
		<#assign currentMacroCommandElements = macroRootElement.elements("command")>

		<#assign extendedMacroCommandElements = extendedMacroRootElement.elements("command")>

		<#assign macroCommandFound = false>

		<#list macroCommandNames as macroCommandName>
			<#list currentMacroCommandElements as currentMacroCommandElement>
				<#assign currentMacroCommandName = currentMacroCommandElement.attributeValue("name")>

				<#if macroCommand == currentMacroCommandName>
					<#assign void = macroNameStack.push(macroName)>

					<#assign macroRootVarElements = macroRootElement.elements("var")>

					<#list macroRootVarElements as macroRootVarElement>
						<#assign lineNumber = macroRootVarElement.attributeValue("line-number")>

						<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
							<#assign displayElement = macroRootVarElement>

							<#include "element_whole_html.ftl">
						</li>
					</#list>

					<#assign void = blockLevelStack.push("macro")>

					<#assign blockElement = currentMacroCommandElement>

					<#include "block_element_html.ftl">

					<#assign void = blockLevelStack.pop()>

					<#assign macroCommandFound = true>

					<#break>
				</#if>
			</#list>

			<#if !macroCommandFound>
				<#list extendedMacroCommandElements as extendedMacroCommandElement>
					<#assign extendedMacroCommandName = extendedMacroCommandElement.attributeValue("name")>

					<#if macroCommand == extendedMacroCommandName>
						<#assign void = macroNameStack.push(extendedMacroName)>

						<#assign extendedMacroRootVarElements = extendedMacroRootElement.elements("var")>

						<#list extendedMacroRootVarElements as extendedMacroRootVarElement>
							<#assign lineNumber = extendedMacroRootVarElement.attributeValue("line-number")>

							<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
								<#assign displayElement = extendedMacroRootVarElement>

								<#include "element_whole_html.ftl">
							</li>
						</#list>
						
						<#assign void = blockLevelStack.push("macro")>

						<#assign blockElement = extendedMacroCommandElement>

						<#include "block_element_html.ftl">

						<#assign void = blockLevelStack.pop()>

						<#assign macroCommandFound = true>

						<#break>
					</#if>
				</#list>
			</#if>

			<#if macroCommandFound>
				<#assign macroCommandFound = false>

				<#break>
			</#if>
		</#list>
	<#else>
		<#list extendedMacroCommandElements as extendedMacroCommandElement>
			<#assign extendedMacroCommandName = extendedMacroCommandElement.attributeValue("name")>

			<#if macroCommand == extendedMacroCommandName>
				<#assign void = macroNameStack.push(extendedMacroName)>

				<#assign extendedMacroRootVarElements = extendedMacroRootElement.elements("var")>

				<#list extendedMacroRootVarElements as extendedMacroRootVarElement>
					<#assign lineNumber = extendedMacroRootVarElement.attributeValue("line-number")>

					<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
						<#assign displayElement = extendedMacroRootVarElement>

						<#include "element_whole_html.ftl">
					</li>
				</#list>

				<#assign void = blockLevelStack.push("macro")>

				<#assign blockElement = extendedMacroCommandElement>

				<#include "block_element_html.ftl">

				<#assign void = blockLevelStack.pop()>

				<#break>
			</#if>
		</#list>
	</#if>
<#else>
	<#assign void = macroNameStack.push(macroName)>

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

			<#assign void = blockLevelStack.push("macro")>

			<#assign blockElement = macroCommandElement>

			<#include "block_element_html.ftl">

			<#assign void = blockLevelStack.pop()>

			<#break>
		</#if>
	</#list>
</#if>

<#assign void = macroNameStack.pop()>

<#assign displayElement = macroElement>

<#include "element_close_html.ftl">