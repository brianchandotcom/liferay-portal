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

package com.liferay.portalweb.plugins.samplespring.portlet.modifydateformatdaymonthdash;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ModifyDateFormatDayMonthDashTest extends BaseTestCase {
	public void testModifyDateFormatDayMonthDash() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Pets Test Page",
			RuntimeVariables.replace("Pets Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Edit Mode"),
			selenium.getText("//div/div/a"));
		selenium.clickAt("//div/div/a", RuntimeVariables.replace("Edit Mode"));
		selenium.waitForText("//div[@class='portlet-body']/h1",
			"Modify Preferences");
		assertEquals(RuntimeVariables.replace("Modify Preferences"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertEquals(RuntimeVariables.replace(
				"Current Date Format is: MM/dd/yyyy"),
			selenium.getText("//div[@class='portlet-body']/p"));
		assertTrue(selenium.isVisible("//select[@name='dateFormat']"));
		selenium.select("//select[@name='dateFormat']",
			RuntimeVariables.replace("dd-MM-yyyy"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Modify"));
		selenium.waitForText("//div[@class='portlet-body']/p",
			"Current Date Format is: dd-MM-yyyy");
		assertEquals(RuntimeVariables.replace(
				"Current Date Format is: dd-MM-yyyy"),
			selenium.getText("//div[@class='portlet-body']/p"));
		assertEquals(RuntimeVariables.replace("View Mode"),
			selenium.getText("//div/div/a"));
		selenium.clickAt("//div/div/a", RuntimeVariables.replace("View Mode"));
		selenium.waitForText("//div/div/a", "Edit Mode");
		assertEquals(RuntimeVariables.replace("Edit Mode"),
			selenium.getText("//div/div/a"));
	}
}