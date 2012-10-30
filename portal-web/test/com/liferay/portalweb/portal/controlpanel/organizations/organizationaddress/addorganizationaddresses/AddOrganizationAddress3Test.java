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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationaddress.addorganizationaddresses;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationAddress3Test extends BaseTestCase {
	public void testAddOrganizationAddress3() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("Organization Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a[2]/strong"));
		selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td[2]/a[2]/strong",
			RuntimeVariables.replace("Organization Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_125_addressesLink']");
		assertTrue(selenium.isPartialText("//a[@id='_125_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_125_addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.waitForVisible("//div[2]/span/span/button[1]");
		selenium.clickAt("//div[2]/span/span/button[1]",
			RuntimeVariables.replace("Add Row"));
		selenium.waitForVisible("//input[@id='_125_addressStreet1_3']");
		selenium.type("//input[@id='_125_addressStreet1_3']",
			RuntimeVariables.replace("12345 Selenium St"));
		selenium.type("//input[@id='_125_addressCity3']",
			RuntimeVariables.replace("Diamond Bar"));
		selenium.type("//input[@id='_125_addressZip3']",
			RuntimeVariables.replace("41111"));
		Thread.sleep(5000);
		selenium.waitForPartialText("//select[@id='_125_addressCountryId3']",
			"United States");
		selenium.select("//select[@id='_125_addressCountryId3']",
			RuntimeVariables.replace("label=United States"));
		selenium.waitForPartialText("//select[@id='_125_addressRegionId3']",
			"California");
		selenium.select("//select[@id='_125_addressRegionId3']",
			RuntimeVariables.replace("label=California"));
		selenium.select("//select[@id='_125_addressTypeId3']",
			RuntimeVariables.replace("label=Billing"));
		selenium.clickAt("//input[@id='_125_addressMailing1Checkbox3']",
			RuntimeVariables.replace("Mailing Checkbox"));
		selenium.clickAt("//input[@id='_125_addressPrimary3']",
			RuntimeVariables.replace("Primary Button"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForSelectedLabel("//select[@id='_125_addressCountryId2']",
			"United States");
		selenium.waitForSelectedLabel("//select[@id='_125_addressRegionId2']",
			"California");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForElementPresent("//input[@id='_125_addressStreet1_2']");
		assertEquals("12345 Selenium St",
			selenium.getValue("//input[@id='_125_addressStreet1_2']"));
		assertEquals("Diamond Bar",
			selenium.getValue("//input[@id='_125_addressCity2']"));
		assertEquals("41111",
			selenium.getValue("//input[@id='_125_addressZip2']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_125_addressCountryId2']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_125_addressRegionId2']"));
		assertEquals("Billing",
			selenium.getSelectedLabel("//select[@id='_125_addressTypeId2']"));
	}
}