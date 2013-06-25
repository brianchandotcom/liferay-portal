package ${seleniumBuilderContext.getTestCasePackageName(testCaseName)};

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

<#assign rootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "action")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getActionClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "macro")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getMacroClassName(childElementAttributeValue)};
</#list>

import java.util.HashMap;
import java.util.Map;

public class ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)} extends BaseTestCase {

	<#if rootElement.element("var")??>
		public ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)}() {
			super();

			<#assign varElements = rootElement.elements("var")>

			<#list varElements as varElement>
				<#assign varName = varElement.attributeValue("name")>

				<#if varElement.attributeValue("value")??>
					<#assign varValue = varElement.attributeValue("value")>
				<#elseif varElement.getText()??>
					<#assign varValue = varElement.getText()>
				</#if>

				definitionScopeVariables.put("${varName}", "${varValue}");
			</#list>
		}
	</#if>

	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		if (Validator.isNull(selenium.getPrimaryTestSuiteName())) {
			selenium.setPrimaryTestSuiteName("${seleniumBuilderContext.getTestCaseClassName(testCaseName)}");
		}

		selenium.startLogger();
	}

	<#assign commandElements = rootElement.elements("command")>

	<#list commandElements as commandElement>
		<#assign commandName = commandElement.attributeValue("name")>

		public void test${commandName}() throws Exception {
			commandScopeVariables = new HashMap<String, String>();

			commandScopeVariables.putAll(definitionScopeVariables);

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
			</#list>

			try {
				<#if rootElement.element("set-up")??>
					commandScopeVariables = new HashMap<String, String>();

					commandScopeVariables.putAll(definitionScopeVariables);

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "start");

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "pending");

					<#assign setUpElement = rootElement.element("set-up")>

					<#assign lineNumber = setUpElement.attributeValue("line-number")>

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

					<#assign blockElement = setUpElement>

					<#include "test_case_block_element.ftl">

					<#assign lineNumber = setUpElement.attributeValue("line-number")>

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
				</#if>

				commandScopeVariables = new HashMap<String, String>();

				commandScopeVariables.putAll(definitionScopeVariables);

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "start");

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "pending");

				<#assign lineNumber = commandElement.attributeValue("line-number")>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

				<#assign blockElement = commandElement>

				<#include "test_case_block_element.ftl">

				<#assign lineNumber = commandElement.attributeValue("line-number")>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
			}
			finally {
				<#if rootElement.element("tear-down")??>
					commandScopeVariables = new HashMap<String, String>();

					commandScopeVariables.putAll(definitionScopeVariables);

					<#assign tearDownElement = rootElement.element("tear-down")>

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "start");

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "pending");

					<#assign lineNumber = tearDownElement.attributeValue("line-number")>

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

					<#assign blockElement = tearDownElement>

					<#include "test_case_block_element.ftl">

					<#assign lineNumber = tearDownElement.attributeValue("line-number")>

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
				</#if>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "pass");
			}
		}
	</#list>
}