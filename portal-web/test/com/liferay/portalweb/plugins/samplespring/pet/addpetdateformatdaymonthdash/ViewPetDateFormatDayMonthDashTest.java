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

package com.liferay.portalweb.plugins.samplespring.pet.addpetdateformatdaymonthdash;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPetDateFormatDayMonthDashTest extends BaseTestCase {
	public void testViewPetDateFormatDayMonthDash() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Pets Test Page",
			RuntimeVariables.replace("Pets Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Pets"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertEquals(RuntimeVariables.replace("Bubbles"),
			selenium.getText(
				"//div[@class='portlet-body']/table/tbody/tr[contains(.,'Fish')]/td[contains(.,'Bubbles')]/a"));
		selenium.clickAt("//div[@class='portlet-body']/table/tbody/tr[contains(.,'Fish')]/td[contains(.,'Bubbles')]/a",
			RuntimeVariables.replace("Bubbles"));
		selenium.waitForText("//div[@class='portlet-body']/h1",
			"Pet Info for: Bubbles");
		assertEquals(RuntimeVariables.replace("Species: Fish"),
			selenium.getText("//div[@class='portlet-body']/ul/li[1]"));
		assertEquals(RuntimeVariables.replace("Breed: Goldfish"),
			selenium.getText("//div[@class='portlet-body']/ul/li[2]"));
		assertEquals(RuntimeVariables.replace(
				"Birthdate: Sun Aug 14 00:00:00 GMT 2011"),
			selenium.getText("//div[@class='portlet-body']/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("Description:"),
			selenium.getText("//div[@class='portlet-body']/ul/li[4]"));
	}
}