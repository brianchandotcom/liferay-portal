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

package com.liferay.portalweb.portlet.directory.organizations.vieworganizationuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewOrganizationUserTest extends BaseTestCase {
	public void testViewOrganizationUser() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Directory Test Page",
					RuntimeVariables.replace("Directory Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace("Organizations"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.type("//input[@name='_11_keywords']",
					RuntimeVariables.replace("Test Organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@name='_11_keywords']",
					RuntimeVariables.replace(""));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText("//td[1]/a"));
				assertEquals(RuntimeVariables.replace(""),
					selenium.getText("//td[2]/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText("//td[3]/a"));
				assertEquals(RuntimeVariables.replace("Diamond Bar"),
					selenium.getText("//td[4]/a"));
				assertEquals(RuntimeVariables.replace("California"),
					selenium.getText("//td[5]/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText("//td[6]/a"));
				selenium.clickAt("link=View Users",
					RuntimeVariables.replace("View Users"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn"),
					selenium.getText("//td[1]/a"));
				assertEquals(RuntimeVariables.replace("userln"),
					selenium.getText("//td[2]/a"));
				assertEquals(RuntimeVariables.replace(""),
					selenium.getText("//td[4]/a"));
				assertEquals(RuntimeVariables.replace("usersn"),
					selenium.getText("//td[3]/a"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText("//td[5]/a"));
				selenium.clickAt("//td[1]/a", RuntimeVariables.replace("userfn"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn usermn userln"),
					selenium.getText(
						"//div[@class='user-information']/div[1]/h2"));
				assertEquals(RuntimeVariables.replace("Email Address"),
					selenium.getText("//dl[@class='property-list']/dt[1]"));
				assertEquals(RuntimeVariables.replace("userea@liferay.com"),
					selenium.getText("//dl[@class='property-list']/dd[1]"));
				assertEquals(RuntimeVariables.replace("Birthday"),
					selenium.getText("//dl[@class='property-list']/dt[2]"));
				assertEquals(RuntimeVariables.replace("4/10/86"),
					selenium.getText("//dl[@class='property-list']/dd[2]"));
				assertEquals(RuntimeVariables.replace("Gender"),
					selenium.getText("//dl[@class='property-list']/dt[3]"));
				assertEquals(RuntimeVariables.replace("Male"),
					selenium.getText("//dl[@class='property-list']/dd[3]"));
				assertEquals(RuntimeVariables.replace("Organization"),
					selenium.getText("//dl[@class='property-list']/dt[4]"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText("//dl[@class='property-list']/dd[4]"));
				assertEquals(RuntimeVariables.replace("Billing"),
					selenium.getText("//li[@class='primary']/em"));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"12345 Test Street"));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"11111, Diamond Bar"));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"(Mailing)"));

			case 100:
				label = -1;
			}
		}
	}
}