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

package com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewOrganizationSiteTest extends BaseTestCase {
	public void testViewOrganizationSite() throws Exception {
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
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//tr[contains(.,'Organization Name')]/td[1]/a",
				"Organization Name"));
		assertTrue(selenium.isPartialText(
				"//tr[contains(.,'Organization Name')]/td[1]/a",
				"Belongs to an organization of type Regular Organization."));
		assertEquals(RuntimeVariables.replace("Private"),
			selenium.getText("//tr[contains(.,'Organization Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("1 Organization"),
			selenium.getText("//tr[contains(.,'Organization Name')]/td[3]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText("//tr[contains(.,'Organization Name')]/td[4]"));
		selenium.clickAt("//tr[contains(.,'Organization Name')]/td[1]/a",
			RuntimeVariables.replace("Organization Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Settings"),
			selenium.getText("//h1[@class='portlet-title']/span"));
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText(
				"//div[@id='_165_details']/fieldset/div/div/div",
				"Organization Name"));
		assertEquals("Private",
			selenium.getSelectedLabel("//select[@id='_165_type']"));
		assertTrue(selenium.isChecked("//input[@id='_165_activeCheckbox']"));
	}
}