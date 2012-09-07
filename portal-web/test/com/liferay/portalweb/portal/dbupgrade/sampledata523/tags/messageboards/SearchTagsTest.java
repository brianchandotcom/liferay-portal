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

package com.liferay.portalweb.portal.dbupgrade.sampledata523.tags.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchTagsTest extends BaseTestCase {
	public void testSearchTags() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities",
			RuntimeVariables.replace("Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Tags Message Board Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Public Pages - Live (1)"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[1]/a"));
		selenium.clickAt("//tr[@class='portlet-section-body results-row']/td[1]/a",
			RuntimeVariables.replace("Public Pages - Live (1)"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium1"));
		selenium.clickAt("//input[@value='Search Categories']",
			RuntimeVariables.replace("Search Categories"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Message1 Tag1 Test1");
		assertTrue(selenium.isElementPresent("link=Message1 Tag1 Test1"));
		assertTrue(selenium.isElementNotPresent("link=Message2 Tag2 Test2"));
		assertTrue(selenium.isElementNotPresent("link=Message3 Tag3 Test3"));
		assertTrue(selenium.isElementNotPresent("link=MessageA TagA TestA"));
		assertTrue(selenium.isElementNotPresent("link=MessageB TagB TestB"));
		assertTrue(selenium.isElementNotPresent("link=MessageC TagC TestC"));
		selenium.waitForElementPresent("link=Message Boards Page");
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium2"));
		selenium.clickAt("//input[@value='Search Categories']",
			RuntimeVariables.replace("Search Categories"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Message2 Tag2 Test2");
		assertTrue(selenium.isElementPresent("link=Message2 Tag2 Test2"));
		assertTrue(selenium.isElementNotPresent("link=Message1 Tag1 Test1"));
		assertTrue(selenium.isElementNotPresent("link=Message3 Tag3 Test3"));
		assertTrue(selenium.isElementNotPresent("link=MessageA TagA TestA"));
		assertTrue(selenium.isElementNotPresent("link=MessageB TagB TestB"));
		assertTrue(selenium.isElementNotPresent("link=MessageC TagC TestC"));
		selenium.waitForElementPresent("link=Message Boards Page");
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium3"));
		selenium.clickAt("//input[@value='Search Categories']",
			RuntimeVariables.replace("Search Categories"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Message3 Tag3 Test3");
		assertTrue(selenium.isElementPresent("link=Message3 Tag3 Test3"));
		assertTrue(selenium.isElementNotPresent("link=Message1 Tag1 Test1"));
		assertTrue(selenium.isElementNotPresent("link=Message2 Tag2 Test2"));
		assertTrue(selenium.isElementNotPresent("link=MessageA TagA TestA"));
		assertTrue(selenium.isElementNotPresent("link=MessageB TagB TestB"));
		assertTrue(selenium.isElementNotPresent("link=MessageC TagC TestC"));
		selenium.waitForElementPresent("link=Message Boards Page");
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium"));
		selenium.clickAt("//input[@value='Search Categories']",
			RuntimeVariables.replace("Search Categories"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=MessageA TagA TestA");
		assertTrue(selenium.isElementPresent("link=MessageA TagA TestA"));
		assertTrue(selenium.isElementPresent("link=MessageB TagB TestB"));
		assertTrue(selenium.isElementPresent("link=MessageC TagC TestC"));
		assertTrue(selenium.isElementNotPresent("link=Message1 Tag1 Test1"));
		assertTrue(selenium.isElementNotPresent("link=Message2 Tag2 Test2"));
		assertTrue(selenium.isElementNotPresent("link=Message3 Tag3 Test3"));
	}
}