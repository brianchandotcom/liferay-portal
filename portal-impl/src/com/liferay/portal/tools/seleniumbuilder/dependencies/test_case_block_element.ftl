<#assign elements = blockElement.elements()>

<#list elements as element>
	<#assign name = element.getName()>

	<#if name == "execute">
		<#if element.attributeValue("action")??>
			<#assign actionElement = element>

			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

			<#include "action_log_element.ftl">

			<#include "action_element.ftl">

			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
		<#elseif element.attributeValue("macro")??>
			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

			<#assign macroElement = element>

			<#include "macro_element.ftl">

			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
		<#elseif element.attributeValue("test-case")??>
			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

			<#assign testCaseElement = element>

			<#include "test_case_element.ftl">

			<#assign lineNumber = element.attributeValue ("line-number")>

			selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
		</#if>
	<#elseif name == "var">
		<#assign varElement = element>

		<#assign context = "commandScopeVariables">

		<#include "var_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

		<#assign lineNumber = element.attributeValue("line-number")>

		selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
	</#if>
</#list>