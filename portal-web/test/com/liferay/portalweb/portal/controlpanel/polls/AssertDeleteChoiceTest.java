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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertDeleteChoiceTest extends BaseTestCase {
	public void testAssertDeleteChoice() throws Exception {
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
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Question']",
			RuntimeVariables.replace("Add Question"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_25_title_en_US']",
			RuntimeVariables.replace("Delete Choice Title Test"));
		selenium.type("//textarea[@id='_25_description_en_US']",
			RuntimeVariables.replace("Delete Choice Description Test"));
		selenium.type("//input[@id='_25_choiceDescriptiona_en_US']",
			RuntimeVariables.replace("Delete Choice A"));
		selenium.type("//input[@id='_25_choiceDescriptionb_en_US']",
			RuntimeVariables.replace("Delete Choice B"));
		selenium.clickAt("//input[@value='Add Choice']",
			RuntimeVariables.replace("Add Choice"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_25_choiceDescriptionc_en_US']",
			RuntimeVariables.replace("Delete Choice C"));
		selenium.clickAt("//input[@value='Add Choice']",
			RuntimeVariables.replace("Add Choice"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_25_choiceDescriptiond_en_US']",
			RuntimeVariables.replace("Delete Choice D"));
		selenium.clickAt("//input[@value='Add Choice']",
			RuntimeVariables.replace("Add Choice"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_25_choiceDescriptione_en_US']",
			RuntimeVariables.replace("Delete Choice E"));
		assertEquals("Delete Choice C",
			selenium.getValue("//input[@id='_25_choiceDescriptionc_en_US']"));
		assertEquals("Delete Choice D",
			selenium.getValue("//input[@id='_25_choiceDescriptiond_en_US']"));
		assertEquals("Delete Choice E",
			selenium.getValue("//input[@id='_25_choiceDescriptione_en_US']"));
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Delete Choice C",
			selenium.getValue("//input[@id='_25_choiceDescriptionc_en_US']"));
		assertEquals("Delete Choice D",
			selenium.getValue("//input[@id='_25_choiceDescriptiond_en_US']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@id='_25_choiceDescriptione_en_US']"));
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Delete Choice C",
			selenium.getValue("//input[@id='_25_choiceDescriptionc_en_US']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@id='_25_choiceDescriptiond_en_US']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@id='_25_choiceDescriptione_en_US']"));
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//input[@id='_25_choiceDescriptionc_en_US']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@id='_25_choiceDescriptiond_en_US']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@id='_25_choiceDescriptione_en_US']"));
	}
}