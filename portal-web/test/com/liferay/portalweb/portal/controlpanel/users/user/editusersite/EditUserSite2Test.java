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

package com.liferay.portalweb.portal.controlpanel.users.user.editusersite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditUserSite2Test extends BaseTestCase {
	public void testEditUserSite2() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
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
				selenium.clickAt("link=Search All Users",
					RuntimeVariables.replace("Search All Users"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));
				selenium.waitForVisible("//input[@name='_125_keywords']");

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("userfn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn"),
					selenium.getText("//tr[3]/td[2]/a"));
				selenium.clickAt("//tr[3]/td[2]/a",
					RuntimeVariables.replace("userfn"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText("//a[@id='_125_sitesLink']",
						"Sites"));
				selenium.clickAt("//a[@id='_125_sitesLink']",
					RuntimeVariables.replace("Sites"));
				selenium.waitForVisible("//div[@id='_125_sites']/span/a/span");
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText("//div[@id='_125_sites']/span/a/span"));
				selenium.clickAt("//div[@id='_125_sites']/span/a/span",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);
				selenium.selectWindow("title=Users and Organizations");
				selenium.waitForVisible("//input[@id='_125_name']");
				selenium.type("//input[@id='_125_name']",
					RuntimeVariables.replace("Site2"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Site2 Name"),
					selenium.getText("//tr[3]/td[1]/a"));
				selenium.clickAt("//tr[3]/td[1]/a",
					RuntimeVariables.replace("Site2 Name"));
				selenium.selectWindow("null");
				selenium.waitForText("//table[@data-searchcontainerid='_125_groupsSearchContainer']/tr/td",
					"Site2 Name");
				assertEquals(RuntimeVariables.replace("Site2 Name"),
					selenium.getText(
						"//table[@data-searchcontainerid='_125_groupsSearchContainer']/tr/td"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Site2 Name"),
					selenium.getText(
						"//table[@data-searchcontainerid='_125_groupsSearchContainer']/tbody/tr/td[contains(.,'Site2 Name')]"));

			case 100:
				label = -1;
			}
		}
	}
}