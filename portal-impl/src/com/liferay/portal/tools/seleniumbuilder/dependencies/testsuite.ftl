package ${seleniumBuilderContext.getTestSuitePackageName(testSuiteName)};

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.StopSeleniumTest;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

<#assign rootElement = seleniumBuilderContext.getTestSuiteRootElement(testSuiteName)>

<#assign executeElements = rootElement.elements("execute")>

<#list executeElements as executeElement>
	<#if executeElement.attributeValue("test-case")??>
		<#assign testCaseName = executeElement.attributeValue("test-case")>

		<#assign testCaseClassName = seleniumBuilderContext.getTestCaseClassName(testCaseName)>

		import ${testCaseClassName};
	</#if>
</#list>

import junit.framework.TestSuite;

public class ${seleniumBuilderContext.getTestSuiteSimpleClassName(testSuiteName)} extends BaseTestSuite {

	public static TestSuite testSuite() {
		TestSuite testSuite = new TestSuite();

		<#list executeElements as executeElement>
			<#if executeElement.attributeValue("test-case")??>
				<#assign testCaseName = executeElement.attributeValue("test-case")>

				<#assign testCaseSimpleClassName = seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)>

				testSuite.addTestSuite(${testCaseSimpleClassName}.class);
			</#if>
		</#list>

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}