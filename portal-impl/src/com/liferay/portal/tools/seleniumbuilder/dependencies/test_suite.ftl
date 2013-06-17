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

		<#list executeElements as executeElement>
			<#if executeElement.attributeValue("test-case")??>
				<#assign testCaseName = executeElement.attributeValue("test-case")>

				<#assign testCaseName = testCaseName?uncap_first>

				<#assign testCaseSimpleClassName = seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName?cap_first)>

				testSuite.addTestSuite(${testCaseSimpleClassName}.class);
			<#elseif executeElement.attributeValue("test-class")??>
				<#assign importTestSuiteName = executeElement.attributeValue("test-class")>

				<#assign importTestSuiteSimpleClassName = seleniumBuilderFileUtil.getClassSimpleClassName(importTestSuiteName)>

				testSuite.addTest(${importTestSuiteSimpleClassName}.suite());
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