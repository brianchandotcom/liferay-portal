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

package com.liferay.portalweb.portal.controlpanel.users.useraddress.searchuseraddresscp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserAddressCPTest extends BaseTestCase {
	public void testAddUserAddressCP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("selen01"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("User Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_125_addressesLink']");
		assertTrue(selenium.isPartialText("//a[@id='_125_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_125_addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.type("//input[@id='_125_addressStreet1_0']",
			RuntimeVariables.replace("123 Street Dr."));
		selenium.select("//select[@id='_125_addressCountryId0']",
			RuntimeVariables.replace("United States"));
		selenium.waitForPartialText("//select[@id='_125_addressRegionId0']",
			"California");
		selenium.select("//select[@id='_125_addressRegionId0']",
			RuntimeVariables.replace("California"));
		selenium.type("//input[@id='_125_addressZip0']",
			RuntimeVariables.replace("11111"));
		selenium.type("//input[@id='_125_addressCity0']",
			RuntimeVariables.replace("Walnut"));
		selenium.select("//select[@id='_125_addressTypeId0']",
			RuntimeVariables.replace("Personal"));
		selenium.clickAt("//input[@id='_125_addressMailing0Checkbox']",
			RuntimeVariables.replace("Mailing Checkbox"));
		selenium.clickAt("//input[@id='_125_addressPrimary0']",
			RuntimeVariables.replace("Primary Button"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("123 Street Dr.",
			selenium.getValue("//input[@id='_125_addressStreet1_0']"));
		assertEquals("Personal",
			selenium.getSelectedLabel("//select[@id='_125_addressTypeId0']"));
		assertEquals("11111",
			selenium.getValue("//input[@id='_125_addressZip0']"));
		assertEquals("Walnut",
			selenium.getValue("//input[@id='_125_addressCity0']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_125_addressCountryId0']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_125_addressRegionId0']"));
		assertTrue(selenium.isChecked("//input[@id='_125_addressPrimary0']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_125_addressMailing0Checkbox']"));
	}
}