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

package com.liferay.portalweb.portlet.dynamicdatalistdisplay.list.addlistddld;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddListDDLDTest extends BaseTestCase {
	public void testAddListDDLD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Dynamic Data List Display Test Page",
			RuntimeVariables.replace("Dynamic Data List Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//img[@title='Add List']",
			RuntimeVariables.replace("Add List"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_167_name_en_US']",
			RuntimeVariables.replace("List Name"));
		selenium.type("//textarea[@id='_167_description_en_US']",
			RuntimeVariables.replace("List Description"));
		selenium.clickAt("link=Select", RuntimeVariables.replace("Select"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//input[@name='_166_keywords']");
		selenium.type("//input[@name='_166_keywords']",
			RuntimeVariables.replace("Data Definition"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Data Definition')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Data Definition"),
			selenium.getText("//tr[contains(.,'Data Definition')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Data Definition')]/td[4]/a"));
		selenium.clickAt("//tr[contains(.,'Data Definition')]/td[3]/a",
			RuntimeVariables.replace("Data Definition"));
		selenium.selectFrame("relative=top");
		assertEquals(RuntimeVariables.replace("Data Definition"),
			selenium.getText(
				"//span[contains(@id,'ddmStructureNameDisplay')]/a"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("List Name"),
			selenium.getText("//h1[@class='header-title']"));
	}
}