<#assign testCaseCommand = testCaseCommandElement.attributeValue("name")>

<li id="${testCaseName?uncap_first}TestCase${testCaseCommand}">
	<#assign lineFolds = lineFolds + 1>

	<div>
		<div id="toggle${lineFolds}" class="expand-toggle">+</div>
	</div>

	<div>
		<div class="expand-line">
			<h3 class="testCaseCommand">${testCaseName}#${testCaseCommand}
				<#if testCaseCommandElement.attributeValue("depends")??>
					[depends ~ ${testCaseCommandElement.attributeValue("depends")}]
				</#if>
			</h3>
		</div>
	</div>

	<ul id="collapseToggle${lineFolds}" class="collapse">
		<#assign testCaseVarElements = testCaseRootElement.elements("var")>

		<#list testCaseVarElements as testCaseVarElement>
			<#assign lineNumber = testCaseVarElement.attributeValue("line-number")>

			<li id="${testCaseName?uncap_first}TestCase${lineNumber}">
				<#assign displayElement = testCaseVarElement>

				<#include "element_whole_html.ftl">
			</li>
		</#list>

		<#if testCaseRootElement.element("set-up")??>
			<#assign testCaseSetupElement = testCaseRootElement.element("set-up")>

			<#assign lineNumber = testCaseSetupElement.attributeValue("line-number")>

			<li id="${testCaseName?uncap_first}TestCase${lineNumber}">
				<#assign displayElement = testCaseSetupElement>

				<#include "element_open_html.ftl">

				<#assign blockElement = testCaseSetupElement>

				<#assign void = testCaseNameStack.push(testCaseName)>

				<#assign void = blockLevelStack.push("testcase")>

				<#include "block_element_html.ftl">

				<#assign void = blockLevelStack.pop()>

				<#assign void = testCaseNameStack.pop()>

				<#assign displayElement = testCaseSetupElement>

				<#include "element_close_html.ftl">
			</li>
		</#if>

		<#assign lineNumber = testCaseCommandElement.attributeValue("line-number")>

		<li id="${testCaseNameStack.peek()?uncap_first}TestCase${lineNumber}">
			<#assign displayElement = testCaseCommandElement>

			<#include "element_open_html.ftl">

			<#assign void = blockLevelStack.push("testcase")>

			<#assign blockElement = testCaseCommandElement>

			<#include "block_element_html.ftl">

			<#assign void = blockLevelStack.pop()>
		
			<#assign displayElement = testCaseCommandElement>

			<#include "element_close_html.ftl">
		</li>

		<#if testCaseRootElement.element("tear-down")??>
			<#assign testCaseTearDownElement = testCaseRootElement.element("tear-down")>

			<#assign lineNumber = testCaseTearDownElement.attributeValue("line-number")>

			<li id="${testCaseName?uncap_first}TestCase${lineNumber}">
				<#assign displayElement = testCaseTearDownElement>

				<#include "element_open_html.ftl">

				<#assign void = testCaseNameStack.push(testCaseName)>

				<#assign void = blockLevelStack.push("testcase")>

				<#assign blockElement = testCaseTearDownElement>

				<#include "block_element_html.ftl">

				<#assign void = blockLevelStack.pop()>

				<#assign void = testCaseNameStack.pop()>

				<#assign displayElement = testCaseTearDownElement>

				<#include "element_close_html.ftl">
			</li>
		</#if>
	</ul>
</li>