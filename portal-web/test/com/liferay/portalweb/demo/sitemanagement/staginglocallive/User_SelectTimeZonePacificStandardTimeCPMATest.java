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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_SelectTimeZonePacificStandardTimeCPMATest extends BaseTestCase {
	public void testUser_SelectTimeZonePacificStandardTimeCPMA()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=selen01 lenn nium01");
		selenium.clickAt("link=selen01 lenn nium01",
			RuntimeVariables.replace("selen01 lenn nium01"));
		selenium.waitForVisible("//a[@id='_2_displaySettingsLink']");
		selenium.clickAt("//a[@id='_2_displaySettingsLink']",
			RuntimeVariables.replace("Display Settings"));
		selenium.waitForVisible("//select[@name='_2_timeZoneId']");
		selenium.select("//select[@name='_2_timeZoneId']",
			RuntimeVariables.replace("(UTC -08:00) Pacific Standard Time"));
		assertEquals("(UTC -08:00) Pacific Standard Time",
			selenium.getSelectedLabel("//select[@name='_2_timeZoneId']"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("(UTC -08:00) Pacific Standard Time",
			selenium.getSelectedLabel("//select[@name='_2_timeZoneId']"));
	}
}