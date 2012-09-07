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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.stagingcommunity.webcontentdisplay;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletStagingCommunityWCDTest extends BaseTestCase {
	public void testAddPortletStagingCommunityWCD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_134_name",
			RuntimeVariables.replace(
				"Community Staging Community Web Content Display"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"link=Page Staging Community Web Content Display");
		selenium.clickAt("link=Page Staging Community Web Content Display",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Add Application", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		selenium.waitForVisible("layout_configuration_content");
		selenium.typeKeys("layout_configuration_content",
			RuntimeVariables.replace("w"));
		selenium.waitForVisible("//div[@title='Web Content Display']/p/a");
		selenium.clickAt("//div[@title='Web Content Display']/p/a",
			RuntimeVariables.replace(""));
		selenium.waitForVisible("//td[1]/div");
		assertTrue(selenium.isVisible("//td[1]/div"));
	}
}