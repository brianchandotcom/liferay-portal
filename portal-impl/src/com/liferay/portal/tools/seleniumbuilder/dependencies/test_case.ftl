package ${seleniumBuilderContext.getTestCasePackageName(testCaseName)};

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.TestPropsValues;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb.portal.util.liferayselenium.SeleniumException;

import ${seleniumBuilderContext.getMacroClassName("User")};

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

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)}
	<#if extendedTestCase??>
		extends ${extendedTestCase}TestCase {
	<#else>
		extends BaseTestCase {
	</#if>

	<#assign void = variableContextStack.push("definitionScopeVariables")>

	public ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)}() {


		currentTestCaseName = "${testCaseName?uncap_first}TestCase";
		testCaseName = "${testCaseName?uncap_first}TestCase";

		<#if rootElement.element("var")??>
			<#assign varElements = rootElement.elements("var")>

			definitionScopeVariables = new HashMap<String, String>();

			<#assign void = variableContextStack.push("definitionScopeVariables")>

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

		<#if rootElement.element("property")??>
			<#assign propertyElements = rootElement.elements("property")>

			<#list propertyElements as propertyElement>
				<#assign lineNumber = propertyElement.attributeValue("line-number")>

				selenium.sendLogger(currentTestCaseName + "${lineNumber}", "pending");

				selenium.sendLogger(currentTestCaseName + "${lineNumber}", "pass");
			</#list>
		</#if>

		<#if rootElement.element("var")??>
			<#assign varElements = rootElement.elements("var")>

			<#list varElements as varElement>
				<#assign lineNumber = varElement.attributeValue("line-number")>

				selenium.sendLogger(currentTestCaseName + "${lineNumber}", "pending");

				selenium.sendLogger(currentTestCaseName + "${lineNumber}", "pass");
			</#list>
		</#if>
	}

	<#assign void = variableContextStack.pop()>

	<#assign testCaseMethodNames = ["command", "set-up", "tear-down"]>

	<#list testCaseMethodNames as testCaseMethodName>
		<#assign testCaseMethodElements = rootElement.elements("${testCaseMethodName}")>

		<#list testCaseMethodElements as testCaseMethodElement>
			<#if testCaseMethodName == "set-up">
				public void methodSetUp
			<#elseif testCaseMethodName == "tear-down">
				public void methodTearDown
			<#else>
				public void method${testCaseMethodElement.attributeValue("name")}
			</#if>

			(String commandName, boolean nested) throws Exception {
				<#if testCaseMethodName == "set-up">
					selenium.sendTestCaseCommandLogger("${testCaseName}#SetUp");
				<#elseif testCaseMethodName == "tear-down">
					selenium.sendTestCaseCommandLogger("${testCaseName}#TearDown");
				<#else>
					selenium.sendTestCaseCommandLogger("${testCaseName}#${testCaseMethodElement.attributeValue("name")}");
				</#if>

				commandScopeVariables = new HashMap<String, String>();

				commandScopeVariables.putAll(definitionScopeVariables);

				<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(testCaseMethodElement, "action")>

				<#list childElementAttributeValues as childElementAttributeValue>
					${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
				</#list>

				<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(testCaseMethodElement, "macro")>

				<#list childElementAttributeValues as childElementAttributeValue>
					${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
				</#list>

				if (!nested) {
					selenium.sendLogger(currentTestCaseName + commandName, "start");

					selenium.sendLogger(currentTestCaseName + commandName, "pending");

					<#assign lineNumber = testCaseMethodElement.attributeValue("line-number")>

					selenium.sendLogger(testCaseName + "${lineNumber}", "pending");
				}

				<#assign blockElement = testCaseMethodElement>

				<#assign blockLevel = "testcase">

				<#assign void = variableContextStack.push("commandScopeVariables")>

				<#include "block_element.ftl">

				<#assign void = variableContextStack.pop()>

				if (!nested) {
					<#assign lineNumber = testCaseMethodElement.attributeValue("line-number")>

					selenium.sendLogger(currentTestCaseName + "${lineNumber}", "pass");
				}
			}
		</#list>
	</#list>

	<#assign commandElements = rootElement.elements("command")>

	<#list commandElements as commandElement>
		<#assign commandName = commandElement.attributeValue("name")>

		@Test
		public void test${commandName}() throws Exception {
			boolean testPassed = false;
			boolean testSkipped = false;

			try {
				definitionScopeVariables.put("testCaseName", "${testCaseName}TestCase${commandName}");

				<#if rootElement.element("tear-down")??>
					if (tearDownBeforeTest) {
						UserMacro userSetupMacro = new UserMacro(selenium);

						userSetupMacro.firstLoginPG(definitionScopeVariables);

						methodTearDown("${commandName}", false);

						tearDownBeforeTest = false;
					}
				</#if>

				selenium.sendTestCaseHeaderLogger("${testCaseName}#${commandName}");

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