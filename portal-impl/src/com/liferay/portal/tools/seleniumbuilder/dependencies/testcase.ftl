package ${seleniumBuilderContext.getTestCasePackageName(testCaseName)};

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

public class ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)} extends BaseTestCase {

	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();
	}

	@Override
	public void tearDown() throws Exception {
	}

}