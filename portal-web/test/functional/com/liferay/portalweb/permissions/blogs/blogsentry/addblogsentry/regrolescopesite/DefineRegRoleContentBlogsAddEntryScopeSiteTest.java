/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.permissions.blogs.blogsentry.addblogsentry.regrolescopesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineRegRoleContentBlogsAddEntryScopeSiteTest extends BaseTestCase {
	public void testDefineRegRoleContentBlogsAddEntryScopeSite()
		throws Exception {
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
				selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_128_keywords']",
					RuntimeVariables.replace("Regrole"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Roles Regrole Name"),
					selenium.getText(
						"//tr[contains(.,'Roles Regrole Name')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'Roles Regrole Name')]/td[1]/a",
					RuntimeVariables.replace("Roles Regrole Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Define Permissions"),
					selenium.getText(
						"//ul[@class='tabview-list']/li/span/a[contains(.,'Define Permissions')]"));
				selenium.clickAt("//ul[@class='tabview-list']/li/span/a[contains(.,'Define Permissions')]",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Roles Regrole Name"),
					selenium.getText("//h1[@class='header-title']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Blogs"));
				selenium.waitForPageToLoad("30000");

				boolean blogsAddEntryChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogsADD_ENTRY']");

				if (blogsAddEntryChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.blogsADD_ENTRY']",
					RuntimeVariables.replace("Blogs Add Entry"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogsADD_ENTRY']"));
				selenium.clickAt("//tr[contains(.,'Add Entry')]/td[4]/span/a[contains(.,'Limit Scope')]/span",
					RuntimeVariables.replace("Limit Scope"));
				Thread.sleep(1000);
				selenium.selectWindow("title=Roles");
				selenium.waitForVisible("//tr[contains(.,'Site Name')]/td/a");
				selenium.clickAt("//tr[contains(.,'Site Name')]/td/a",
					RuntimeVariables.replace("Site Name"));
				selenium.selectWindow("null");
				selenium.waitForVisible("//span[@class='lfr-token-text']");
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText("//span[@class='lfr-token-text']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Blogs"),
					selenium.getText("//tr[contains(.,'Blogs')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("Blogs"),
					selenium.getText("//tr[contains(.,'Blogs')]/td[2]"));
				assertEquals(RuntimeVariables.replace("Add Entry"),
					selenium.getText("//tr[contains(.,'Blogs')]/td[3]"));
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText("//tr[contains(.,'Blogs')]/td[4]"));
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//tr[contains(.,'Blogs')]/td[5]/span/a/span"));

			case 100:
				label = -1;
			}
		}
	}
}