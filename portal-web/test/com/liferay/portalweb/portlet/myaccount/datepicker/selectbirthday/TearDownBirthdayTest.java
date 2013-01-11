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

package com.liferay.portalweb.portlet.myaccount.datepicker.selectbirthday;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownBirthdayTest extends BaseTestCase {
	public void testTearDownBirthday() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Joe Bloggs",
			RuntimeVariables.replace("Joe Bloggs"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//select[@id='_2_birthdayMonth']",
			RuntimeVariables.replace("January"));
		selenium.select("//select[@id='_2_birthdayMonth']",
			RuntimeVariables.replace("January"));
		selenium.select("//select[@id='_2_birthdayDay']",
			RuntimeVariables.replace("1"));
		selenium.select("//select[@id='_2_birthdayYear']",
			RuntimeVariables.replace("1970"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("January",
			selenium.getSelectedLabel("//select[@id='_2_birthdayMonth']"));
		assertEquals("1",
			selenium.getSelectedLabel("//select[@id='_2_birthdayDay']"));
		assertEquals("1970",
			selenium.getSelectedLabel("//select[@id='_2_birthdayYear']"));
	}
}