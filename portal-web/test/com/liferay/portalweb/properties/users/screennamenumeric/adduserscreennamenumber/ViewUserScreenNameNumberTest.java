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

package com.liferay.portalweb.properties.users.screennamenumeric.adduserscreennamenumber;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUserScreenNameNumberTest extends BaseTestCase {
	public void testViewUserScreenNameNumber() throws Exception {
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
				selenium.clickAt("link=Search All Users",
					RuntimeVariables.replace("Search All Users"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForElementPresent("//a[.='\u00ab Basic']");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));
				selenium.waitForVisible("//input[@name='_125_keywords']");

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("11111"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn"),
					selenium.getText("//tr[contains(.,'userfn')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("userln"),
					selenium.getText("//tr[contains(.,'userfn')]/td[3]/a"));
				assertEquals(RuntimeVariables.replace("11111"),
					selenium.getText("//tr[contains(.,'userfn')]/td[4]/a"));
				selenium.clickAt("//tr[contains(.,'userfn')]/td[2]/a",
					RuntimeVariables.replace("userfn"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Users and Organizations"),
					selenium.getText("//span[@class='portlet-title-text']"));
				assertEquals(RuntimeVariables.replace("Browse"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'Browse')]"));
				assertEquals(RuntimeVariables.replace("View Organizations"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'View Organizations')]"));
				assertEquals(RuntimeVariables.replace("View Users"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'View Users')]"));
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span[@title='Add']/ul/li/strong/a"));
				assertEquals(RuntimeVariables.replace("Export Users"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'Export Users')]"));
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				assertEquals(RuntimeVariables.replace("Details"),
					selenium.getText("//div[@id='_125_details']/h3"));
				assertEquals("",
					selenium.getValue("//select[@id='_125_prefixId']"));
				assertEquals("11111",
					selenium.getValue("//input[@id='_125_screenName']"));
				assertEquals("userea@liferay.com",
					selenium.getValue("//input[@id='_125_emailAddress']"));
				assertEquals("userfn",
					selenium.getValue("//input[@id='_125_firstName']"));
				assertEquals("",
					selenium.getValue("//input[@id='_125_middleName']"));
				assertEquals("userln",
					selenium.getValue("//input[@id='_125_lastName']"));
				assertTrue(selenium.isVisible("//img[@class='avatar']"));
				assertEquals(RuntimeVariables.replace("Change"),
					selenium.getText("//span[@class='edit-logo-link']/a/span"));
				assertEquals("0",
					selenium.getValue("//select[@id='_125_birthdayMonth']"));
				assertEquals("1",
					selenium.getValue("//select[@id='_125_birthdayDay']"));
				assertEquals("1970",
					selenium.getValue("//select[@id='_125_birthdayYear']"));
				assertEquals("true",
					selenium.getValue("//select[@id='_125_male']"));
				assertEquals("",
					selenium.getValue("//input[@id='_125_jobTitle']"));
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//span[@class='user-name']"));
				assertEquals(RuntimeVariables.replace("Browse"),
					selenium.getText(
						"//span[@class='lfr-toolbar-button view-button ']/a[contains(.,'Browse')]"));

			case 100:
				label = -1;
			}
		}
	}
}