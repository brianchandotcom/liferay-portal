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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCWebContentDisabledDeleteTest extends BaseTestCase {
	public void testViewWCWebContentDisabledDelete() throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//td[3]/a"));
		assertFalse(selenium.isChecked("//input[@name='_15_allRowIds']"));
		assertFalse(selenium.isChecked("//input[@name='_15_rowIds']"));
		assertTrue(selenium.isVisible(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertTrue(selenium.isVisible(
				"//input[@value='Delete' and @disabled='']"));
		selenium.clickAt("//input[@name='_15_allRowIds']",
			RuntimeVariables.replace("Select All"));
		assertTrue(selenium.isChecked("//input[@name='_15_allRowIds']"));
		selenium.waitForElementNotPresent(
			"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']");
		assertTrue(selenium.isElementNotPresent(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@value='Delete' and @disabled='']"));
		assertTrue(selenium.isVisible("//input[@value='Delete']"));
		selenium.clickAt("//input[@name='_15_allRowIds']",
			RuntimeVariables.replace("Select All"));
		assertFalse(selenium.isChecked("//input[@name='_15_allRowIds']"));
		selenium.waitForVisible(
			"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']");
		assertTrue(selenium.isVisible(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertTrue(selenium.isVisible(
				"//input[@value='Delete' and @disabled='']"));
		selenium.clickAt("//input[@name='_15_rowIds']",
			RuntimeVariables.replace("Row Entry Check Box"));
		assertTrue(selenium.isChecked("//input[@name='_15_rowIds']"));
		selenium.waitForElementNotPresent(
			"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']");
		assertTrue(selenium.isElementNotPresent(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@value='Delete' and @disabled='']"));
		assertTrue(selenium.isVisible("//input[@value='Delete']"));
		selenium.clickAt("//input[@name='_15_rowIds']",
			RuntimeVariables.replace("Row Entry Check Box"));
		assertFalse(selenium.isChecked("//input[@name='_15_rowIds']"));
		selenium.waitForVisible(
			"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']");
		assertTrue(selenium.isVisible(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertTrue(selenium.isVisible(
				"//input[@value='Delete' and @disabled='']"));
	}
}