/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.plugins.googleadsense;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertAdSensePresentTest extends BaseTestCase {
	public void testAssertAdSensePresent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Google Adsense Test Page",
			RuntimeVariables.replace("Google Adsense Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//iframe[@id='aswift_0']");
		selenium.selectFrame("//iframe[@id='aswift_0']");
		selenium.waitForVisible("//iframe[@id='google_ads_frame1']");
		selenium.selectFrame("//iframe[@id='google_ads_frame1']");
		assertTrue(selenium.isVisible("//img[@alt='AdChoices']"));
		selenium.selectFrame("relative=top");
	}
}