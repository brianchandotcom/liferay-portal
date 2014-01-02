package ${seleniumBuilderContext.getTestCasePackageName(testCaseName)};

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.TestPropsValues;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb.portal.util.liferayselenium.SeleniumException;

<#assign rootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "action")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getActionClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "macro")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getMacroClassName(childElementAttributeValue)};
</#list>

<#if rootElement.attributeValue("extends")??>
	<#assign extendedTestCase = rootElement.attributeValue("extends")>

	import ${seleniumBuilderContext.getTestCaseClassName(extendedTestCase)};
</#if>

import java.util.HashMap;
import java.util.Map;

public class ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)}
	<#if extendedTestCase??>
		extends ${extendedTestCase}TestCase {
	<#else>
		extends BaseTestCase {
	</#if>

	public ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)}() {
		super();

		currentTestCaseName = "${testCaseName?uncap_first}TestCase";
		testCaseName = "${testCaseName?uncap_first}TestCase";

		<#if rootElement.element("var")??>
			<#assign varElements = rootElement.elements("var")>

			definitionScopeVariables = new HashMap<String, String>();

			<#assign context = "definitionScopeVariables">

			<#list varElements as varElement>
				<#include "var_element.ftl">
			</#list>
		</#if>
	}

	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		if (Validator.isNull(selenium.getPrimaryTestSuiteName())) {
			selenium.setPrimaryTestSuiteName("${seleniumBuilderContext.getTestCaseClassName(testCaseName)}");
		}

		selenium.startLogger();

		<#if rootElement.element("var")??>
			<#assign varElements = rootElement.elements("var")>

			<#list varElements as varElement>
				<#assign lineNumber = varElement.attributeValue("line-number")>

				selenium.sendLogger(currentTestCaseName + "${lineNumber}", "pending", ${context});

				selenium.sendLogger(currentTestCaseName + "${lineNumber}", "pass", ${context});
			</#list>
		</#if>
	}

	<#assign methodNames = ["command", "set-up", "tear-down"]>

	<#list methodNames as methodName>
		<#assign methodElements = rootElement.elements("${methodName}")>

		<#list methodElements as methodElement>
			<#if methodName == "set-up">
				public void methodSetUp
			<#elseif methodName == "tear-down">
				public void methodTearDown
			<#else>
				public void method${methodElement.attributeValue("name")}
			</#if>

			(String commandName, boolean nested) throws Exception {
				commandScopeVariables = new HashMap<String, String>();

				commandScopeVariables.putAll(definitionScopeVariables);

				<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(methodElement, "action")>

				<#list childElementAttributeValues as childElementAttributeValue>
					${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
				</#list>

				<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(methodElement, "macro")>

				<#list childElementAttributeValues as childElementAttributeValue>
					${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
				</#list>

				if (!nested) {
					selenium.sendLogger(currentTestCaseName + commandName, "start", commandScopeVariables);

					selenium.sendLogger(currentTestCaseName + commandName, "pending", commandScopeVariables);

					<#assign lineNumber = methodElement.attributeValue("line-number")>

					selenium.sendLogger(testCaseName + "${lineNumber}", "pending", commandScopeVariables);
				}

				<#assign blockElement = methodElement>

				<#assign blockLevel = "testcase">

				<#include "block_element.ftl">

				if (!nested) {
					<#assign lineNumber = methodElement.attributeValue("line-number")>

					selenium.sendLogger(currentTestCaseName + "${lineNumber}", "pass", commandScopeVariables);
				}
			}
		</#list>
	</#list>

	<#assign commandElements = rootElement.elements("command")>

	<#list commandElements as commandElement>
		<#assign commandName = commandElement.attributeValue("name")>

		public void test${commandName}() throws Exception {
			boolean testPassed = false;
			boolean testSkipped = false;

			try {
				definitionScopeVariables.put("testCaseName", "${testCaseName}TestCase${commandName}");

				<#if rootElement.element("set-up")??>
					methodSetUp("${commandName}", false);
				</#if>

				<#if commandElement.attributeValue("depends")??>
					<#assign depends = commandElement.attributeValue("depends")>

					if (!ArrayUtil.contains(TestPropsValues.FIXED_ISSUES, "${depends}")) {
						throw new SeleniumException();
					}
				</#if>

				method${commandName}("${commandName}", false);

				testPassed = true;
			}
			catch (SeleniumException se) {
				testSkipped = true;
			}
			finally {
				<#if rootElement.element("tear-down")??>
					if (!TestPropsValues.TEST_SKIP_TEAR_DOWN) {
							methodTearDown("${commandName}", false);
					}
				</#if>

				if (testSkipped) {
					selenium.sendLogger(testCaseName + "${commandName}", "skip");
				}
				else if (testPassed) {
					selenium.sendLogger(testCaseName + "${commandName}", "pass");
				}
				else {
					selenium.sendLogger(testCaseName + "${commandName}", "fail");
				}
			}
		}
	</#list>

	static {
		<#assign testCaseCommandNames = seleniumBuilderContext.getTestCaseCommandNames(testCaseName)>

		testCaseCount = ${testCaseCommandNames?size};
	}

	private static String testCaseName;

	private int _whileCount;

}