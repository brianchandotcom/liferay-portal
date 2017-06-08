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

import java.util.HashMap;
import java.util.Map;

public class ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)}
	<#if rootElement.attributeValue("extends")??>
		<#assign extendedTestCase = rootElement.attributeValue("extends")>

		extends ${extendedTestCase}TestCase {
	<#else>
		extends BaseTestCase {
	</#if>

	<#if rootElement.element("var")??>
		public ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)}() {
			super();

			<#assign varElements = rootElement.elements("var")>

			<#assign context = "definitionScopeVariables">

			<#list varElements as varElement>
				<#include "var_element.ftl">
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

			(String commandName) throws Exception {
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

				selenium.sendLogger("${testCaseName?uncap_first}TestCase" + commandName, "start");

				selenium.sendLogger("${testCaseName?uncap_first}TestCase" + commandName, "pending");

				<#assign lineNumber = methodElement.attributeValue("line-number")>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

				<#assign blockElement = methodElement>

				<#include "test_case_block_element.ftl">

				<#assign lineNumber = methodElement.attributeValue("line-number")>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
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
					methodSetUp("${commandName}");
				</#if>

				<#if commandElement.attributeValue("depends")??>
					<#assign depends = commandElement.attributeValue("depends")>

					if (!ArrayUtil.contains(TestPropsValues.FIXED_ISSUES, "${depends}")) {
						throw new SeleniumException();
					}
				</#if>

				method${commandName}("${commandName}");

				testPassed = true;
			}
			catch (SeleniumException se) {
				testSkipped = true;
			}
			finally {
				<#if rootElement.element("tear-down")??>
					if (!TestPropsValues.TEST_SKIP_TEAR_DOWN) {
							methodTearDown("${commandName}");
					}
				</#if>

				if (testSkipped) {
					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "skip");
				}
				else if (testPassed) {
					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "pass");
				}
				else {
					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "fail");
				}
			}
		}
	</#list>

	static {
		<#assign commandElements = rootElement.elements("command")>

		testCaseCount = ${commandElements?size};
	}
}