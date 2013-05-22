<#assign elements = blockElement.elements()>

<#list elements as element>
	<#assign name = element.getName()>

	<#if name == "execute">
		<#if element.attributeValue("action")??>
			<#assign actionElement = element>

			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName}TestCase__${lineNumber}", "pending");

			<#include "action_element.ftl">

			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName}TestCase__${lineNumber}", "pass");
		<#elseif element.attributeValue("macro")??>
			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName}TestCase__${lineNumber}", "pending");

			<#assign macroElement = element>

			<#include "macro_element.ftl">

			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName}TestCase__${lineNumber}", "pass");
		</#if>
	<#elseif name == "var">
		<#assign varName = element.attributeValue("name")>

		<#assign varValue = element.attributeValue("value")>

		<#if varValue?contains("${") && varValue?contains("}")>
			<#assign varValue = varValue?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign varValue = varValue?replace("}", "\") + \"")>
		</#if>

		<#assign lineNumber = element.attributeValue("line-number")>

		selenium.sendLogger("${testCaseName}TestCase__${lineNumber}", "pending");

		commandScopeVariables.put("${varName}", "${varValue}");

		<#assign lineNumber = element.attributeValue("line-number")>

		selenium.sendLogger("${testCaseName}TestCase__${lineNumber}", "pass");
	</#if>
</#list>