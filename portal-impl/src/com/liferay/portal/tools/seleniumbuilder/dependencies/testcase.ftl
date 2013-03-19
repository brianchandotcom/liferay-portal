package ${seleniumBuilderContext.getTestCasePackageName(testCaseName)};

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

public class ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)} extends BaseTestCase {

	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();
	}

	<#assign rootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

	<#assign testCaseCommandElements = rootElement.elements("test-case-command")>

	<#list testCaseCommandElements as testCaseCommandElement>
		<#assign testCaseCommandName = testCaseCommandElement.attributeValue("name")>

		public void test${testCaseCommandName}() throws Exception {
		}
	</#list>

	@Override
	public void tearDown() throws Exception {
	}

}