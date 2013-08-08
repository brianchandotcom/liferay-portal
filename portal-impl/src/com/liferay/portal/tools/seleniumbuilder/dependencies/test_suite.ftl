package ${seleniumBuilderContext.getTestSuitePackageName(testSuiteName)};

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.NamedTestSuite;
import com.liferay.portalweb.portal.StopSeleniumTest;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

<#assign rootElement = seleniumBuilderContext.getTestSuiteRootElement(testSuiteName)>

<#assign executeElements = rootElement.elements("execute")>

<#list executeElements as executeElement>
	<#if executeElement.attributeValue("test-case")??>
		<#assign testCaseName = executeElement.attributeValue("test-case")>

		<#assign testCaseClassName = seleniumBuilderContext.getTestCaseClassName(testCaseName)>

		import ${testCaseClassName};
	<#elseif executeElement.attributeValue("test-case-command")??>
		<#assign testCaseCommand = executeElement.attributeValue("test-case-command")>

		<#assign x = testCaseCommand?last_index_of("#")>

		<#assign testCaseCommandClass = testCaseCommand?substring(0, x)>

		<#assign testCaseCommandClassName = seleniumBuilderContext.getTestCaseClassName(testCaseCommandClass)>

		import ${testCaseCommandClassName};
	<#elseif executeElement.attributeValue("test-class")??>
		import ${executeElement.attributeValue("test-class")};
	<#elseif executeElement.attributeValue("test-suite")??>
		<#assign importTestSuiteName = executeElement.attributeValue("test-suite")>

		<#assign importTestSuiteClassName = seleniumBuilderContext.getTestSuiteClassName(importTestSuiteName)>

		import ${importTestSuiteClassName};
	</#if>
</#list>

import junit.framework.TestSuite;

public class ${seleniumBuilderContext.getTestSuiteSimpleClassName(testSuiteName)} extends BaseTestSuite {

	public static TestSuite suite() {
		TestSuite testSuite = new NamedTestSuite();

		<#assign testCaseClasses = [] />

		<#list executeElements as executeElement>
			<#if executeElement.attributeValue("test-case-command")??>
				<#assign testCaseCommand = executeElement.attributeValue("test-case-command")>

				<#assign x = testCaseCommand?last_index_of("#")>

				<#assign testCaseCommandClass = testCaseCommand?substring(0, x)>

				<#assign testCaseCommandMethod = testCaseCommand?substring(x + 1)>

				<#assign testCaseClassName = seleniumBuilderContext.getTestCaseSimpleClassName(testCaseCommandClass)>

				<#if !testCaseClasses?seq_contains(testCaseClassName)>
					${testCaseClassName} ${testCaseClassName?uncap_first} = null;

					<#assign testCaseClasses = testCaseClasses + [testCaseClassName] />
				</#if>
			</#if>
		</#list>

		<#list executeElements as executeElement>
			<#if executeElement.attributeValue("test-case")??>
				<#assign testCaseName = executeElement.attributeValue("test-case")>

				<#assign testCaseSimpleClassName = seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)>

				testSuite.addTestSuite(${testCaseSimpleClassName}.class);
			<#elseif executeElement.attributeValue("test-class")??>
				<#assign importTestSuiteName = executeElement.attributeValue("test-class")>

				<#assign importTestSuiteSimpleClassName = seleniumBuilderFileUtil.getClassSimpleClassName(importTestSuiteName)>

				testSuite.addTest(${importTestSuiteSimpleClassName}.suite());
			<#elseif executeElement.attributeValue("test-case-command")??>
				<#assign testCaseCommand = executeElement.attributeValue("test-case-command")>

				<#assign x = testCaseCommand?last_index_of("#")>

				<#assign testCaseCommandClass = testCaseCommand?substring(0, x)>

				<#assign testCaseCommandMethod = testCaseCommand?substring(x + 1)>

				<#assign testCaseClassName = seleniumBuilderContext.getTestCaseSimpleClassName(testCaseCommandClass)>

				${testCaseClassName?uncap_first} = new ${testCaseClassName}();

				${testCaseClassName?uncap_first}.setName("test${testCaseCommandMethod}");

				testSuite.addTest(${testCaseClassName?uncap_first});
			<#elseif executeElement.attributeValue("test-suite")??>
				<#assign importTestSuiteName = executeElement.attributeValue("test-suite")>

				<#assign importTestSuiteSimpleClassName = seleniumBuilderContext.getTestSuiteSimpleClassName(importTestSuiteName)>

				testSuite.addTest(${importTestSuiteSimpleClassName}.suite());
			</#if>
		</#list>

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}