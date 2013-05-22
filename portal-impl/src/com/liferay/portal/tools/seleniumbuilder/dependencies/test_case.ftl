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

				<#assign varValue = varElement.attributeValue("value")>

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

		commandScopeVariables = new HashMap<String, String>();

		commandScopeVariables.putAll(definitionScopeVariables);

		<#if rootElement.element("set-up")??>
			<#assign setUpElement = rootElement.element("set-up")>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(setUpElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(setUpElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
			</#list>

			<#assign blockElement = setUpElement>

			<#include "test_case_block_element.ftl">
		</#if>
	}

	<#assign commandElements = rootElement.elements("command")>

	<#list commandElements as commandElement>
		<#assign commandName = commandElement.attributeValue("name")>

		public void test${commandName}() throws Exception {
			commandScopeVariables = new HashMap<String, String>();

			commandScopeVariables.putAll(definitionScopeVariables);

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
			</#list>

			selenium.sendLogger("${testCaseName}TestCase__${commandName}", "start");

			<#assign lineNumber = commandElement.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName}TestCase__${lineNumber}", "pending");

			<#assign blockElement = commandElement>

			<#include "test_case_block_element.ftl">

			<#assign lineNumber = commandElement.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName}TestCase__${lineNumber}", "pass");
		}
	</#list>

	@Override
	public void tearDown() throws Exception {
		commandScopeVariables = new HashMap<String, String>();

		commandScopeVariables.putAll(definitionScopeVariables);

		<#if rootElement.element("tear-down")??>
			<#assign tearDownElement = rootElement.element("tear-down")>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(tearDownElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(tearDownElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
			</#list>

			<#assign blockElement = tearDownElement>

			<#include "test_case_block_element.ftl">
		</#if>
	}

}