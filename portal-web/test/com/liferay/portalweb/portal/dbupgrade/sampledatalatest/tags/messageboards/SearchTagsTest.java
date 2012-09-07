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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.tags.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchTagsTest extends BaseTestCase {
	public void testSearchTags() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/tags-message-board-community/");
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("link=Message1 Tag1 Test1"));
		assertTrue(selenium.isElementNotPresent("link=Message2 Tag2 Test2"));
		assertTrue(selenium.isElementNotPresent("link=Message3 Tag3 Test3"));
		assertTrue(selenium.isElementNotPresent("link=MessageA TagA TestA"));
		assertTrue(selenium.isElementNotPresent("link=MessageB TagB TestB"));
		assertTrue(selenium.isElementNotPresent("link=MessageC TagC TestC"));
		selenium.open("/web/tags-message-board-community/");
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium2"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("link=Message2 Tag2 Test2"));
		assertTrue(selenium.isElementNotPresent("link=Message1 Tag1 Test1"));
		assertTrue(selenium.isElementNotPresent("link=Message3 Tag3 Test3"));
		assertTrue(selenium.isElementNotPresent("link=MessageA TagA TestA"));
		assertTrue(selenium.isElementNotPresent("link=MessageB TagB TestB"));
		assertTrue(selenium.isElementNotPresent("link=MessageC TagC TestC"));
		selenium.open("/web/tags-message-board-community/");
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium3"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("link=Message3 Tag3 Test3"));
		assertTrue(selenium.isElementNotPresent("link=Message1 Tag1 Test1"));
		assertTrue(selenium.isElementNotPresent("link=Message2 Tag2 Test2"));
		assertTrue(selenium.isElementNotPresent("link=MessageA TagA TestA"));
		assertTrue(selenium.isElementNotPresent("link=MessageB TagB TestB"));
		assertTrue(selenium.isElementNotPresent("link=MessageC TagC TestC"));
		selenium.open("/web/tags-message-board-community/");
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("link=MessageA TagA TestA"));
		assertTrue(selenium.isVisible("link=MessageB TagB TestB"));
		assertTrue(selenium.isVisible("link=MessageC TagC TestC"));
		assertTrue(selenium.isElementNotPresent("link=Message1 Tag1 Test1"));
		assertTrue(selenium.isElementNotPresent("link=Message2 Tag2 Test2"));
		assertTrue(selenium.isElementNotPresent("link=Message3 Tag3 Test3"));
	}
}