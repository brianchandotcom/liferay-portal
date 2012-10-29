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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationpage.mergeorganizationpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationPageTest extends BaseTestCase {
	public void testAddOrganizationPage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Organization Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//a[@id='_125_organizationSiteLink']");
				selenium.clickAt("//a[@id='_125_organizationSiteLink']",
					RuntimeVariables.replace("Organization site"));

				boolean createSiteCheckbox = selenium.isChecked(
						"//input[@id='_125_siteCheckbox']");

				if (createSiteCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_125_siteCheckbox']",
					RuntimeVariables.replace("Create Site Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_125_siteCheckbox']"));
				selenium.waitForVisible(
					"//select[@id='_125_publicLayoutSetPrototypeId']");
				selenium.select("//select[@id='_125_publicLayoutSetPrototypeId']",
					RuntimeVariables.replace("label=Community Site"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.open("/web/organization-name/home");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Manage"),
					selenium.getText("//li[@id='_145_manageContent']/a/span"));
				selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
				selenium.waitForVisible(
					"//li[contains(@class,'manage-page')]/a");
				assertEquals(RuntimeVariables.replace("Page"),
					selenium.getText("//li[contains(@class,'manage-page')]/a"));
				selenium.clickAt("//li[contains(@class,'manage-page')]/a",
					RuntimeVariables.replace("Page"));
				Thread.sleep(10000);
				selenium.waitForVisible("//iframe");
				selenium.selectFrame("//iframe");
				selenium.waitForText("//div[3]/a", "Public Pages");
				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//div[3]/a"));
				selenium.clickAt("//div[3]/a",
					RuntimeVariables.replace("Public Pages"));
				selenium.waitForText("//div[2]/span/button", "Add Page");
				assertEquals(RuntimeVariables.replace("Add Page"),
					selenium.getText("//div[2]/span/button"));
				selenium.click("//div[2]/span/button");
				selenium.waitForVisible("//input[@name='_88_name_en_US']");
				selenium.type("//input[@name='_88_name_en_US']",
					RuntimeVariables.replace("Selenium Test Home Page"));
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace("Add Page"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean pagePresent = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (pagePresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@class='aui-tree-hitarea']",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 3:
				selenium.waitForText("link=Selenium Test Home Page",
					"Selenium Test Home Page");
				assertEquals(RuntimeVariables.replace("Selenium Test Home Page"),
					selenium.getText("link=Selenium Test Home Page"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}