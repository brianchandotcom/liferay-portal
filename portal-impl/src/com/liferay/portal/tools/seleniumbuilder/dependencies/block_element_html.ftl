<#assign elements = blockElement.elements()>

<#list elements as element>
	<#assign lineNumber = element.attributeValue("line-number")>

	<#assign blockLevel = blockLevelStack.peek()>

	<#if blockLevel == "testcase">
		<#assign lineId = "${testCaseNameStack.peek()?uncap_first}TestCase">
	<#elseif blockLevel == "macro">
		<#assign lineId = "${macroNameStack.peek()?uncap_first}Macro">
	</#if>

	<li id="${lineId}${lineNumber}">
		<#if element.getName() == "echo" || element.getName() == "fail" || element.getName() == "property" || element.getName() == "var">
			<#assign displayElement = element>

			<#include "element_whole_html.ftl">
		<#elseif element.getName() == "execute">
			<#if element.attributeValue("action")??>
				<#assign displayElement = element>

				<#include "element_whole_html.ftl">

				<#if element.element("var")??>
					<#assign displayElement = element>

					<#include "element_open_html.ftl">

					<#assign displayElement = element>

					<#include "element_close_html.ftl">
				</#if>
			<#elseif element.attributeValue("function")??>
				<#assign displayElement = element>

				<#include "element_whole_html.ftl">

				<#if element.element("var")??>
					<#assign displayElement = element>

					<#include "element_open_html.ftl">

					<#assign displayElement = element>

					<#include "element_close_html.ftl">
				</#if>
			<#elseif element.attributeValue("macro")??>
				<#assign macroElement = element>

				<#include "macro_element_html.ftl">
			<#elseif element.attributeValue("test-case")??>
				<#assign testCaseExecuteElement = element>

				<#assign void = testCaseNameStack.push(extendedTestCase)>

				<#include "extended_test_case_element_html.ftl">

				<#assign void = testCaseNameStack.pop()>
			</#if>
		<#elseif element.getName() == "for">
			<#assign forElement = element>

			<#include "for_element_html.ftl">
		<#elseif element.getName() == "if">
			<#assign displayElement = element>

			<#include "element_open_html.ftl">

			<#assign ifElement = element>

			<#include "if_element_html.ftl">

			<#assign elseifElements = element.elements("elseif")>

			<#list elseifElements as elseifElement>
				<#assign lineNumber = elseifElement.attributeValue("line-number")>

				<#assign blockLevel = blockLevelStack.peek()>

				<#if blockLevel == "testcase">
					<#assign lineId = "${testCaseNameStack.peek()?uncap_first}TestCase">
				<#elseif blockLevel == "macro">
					<#assign lineId = "${macroNameStack.peek()?uncap_first}Macro">
				</#if>

				<li id="${lineId}${lineNumber}">
					<#assign displayElement = elseifElement>

					<#include "element_open_html.ftl">

					<#assign ifElement = elseifElement>

					<#include "if_element_html.ftl">

					<#assign displayElement = elseifElement>

					<#include "element_close_html.ftl">
				</li>
			</#list>

			<#if element.element("else")??>
				<#assign elseElement = element.element("else")>

				<#assign lineNumber = elseElement.attributeValue("line-number")>

				<#assign blockLevel = blockLevelStack.peek()>

				<#if blockLevel == "testcase">
					<#assign lineId = "${testCaseNameStack.peek()?uncap_first}TestCase">
				<#elseif blockLevel == "macro">
					<#assign lineId = "${macroNameStack.peek()?uncap_first}Macro">
				</#if>

				<li id="${lineId}${lineNumber}">
					<#assign displayElement = elseElement>

					<#include "element_open_html.ftl">

					<#assign blockElement = element.element("else")>

					<#include "block_element_html.ftl">

					<#assign displayElement = elseElement>

					<#include "element_close_html.ftl">
				</li>
			</#if>

			<#assign displayElement = element>

			<#include "element_close_html.ftl">
		<#elseif element.getName() == "while">
			<#assign displayElement = element>

			<#include "element_open_html.ftl">

			<#assign ifElement = element>

			<#include "if_element_html.ftl">

			<#assign displayElement = element>

			<#include "element_close_html.ftl">
		</#if>
	</li>
</#list>