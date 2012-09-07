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

package com.liferay.portalweb.demo.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_ViewPublicPagesSiteBWCTest extends BaseTestCase {
	public void testGuest_ViewPublicPagesSiteBWC() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.openWindow("http://www.able.com:8080",
			RuntimeVariables.replace("home"));
		selenium.waitForPopUp("home", RuntimeVariables.replace(""));
		selenium.selectWindow("home");
		Thread.sleep(5000);
		Thread.sleep(5000);
		selenium.waitForVisible("//a[@class='logo custom-logo']");
		assertTrue(selenium.isVisible("//a[@class='logo custom-logo']"));
		assertTrue(selenium.isElementPresent("//img[@height='156']"));
		assertTrue(selenium.isElementPresent("//img[@width='320']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='logo default-logo']"));
		assertTrue(selenium.isElementPresent(
				"//body[@class='green yui3-skin-sam controls-visible signed-out public-page site']"));
		assertTrue(selenium.isElementNotPresent(
				"//body[@class='blue yui3-skin-sam controls-visible signed-out public-page site']"));
		assertTrue(selenium.isVisible("link=Home"));
		selenium.clickAt("link=Home", RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		assertTrue(selenium.isVisible("link=Arenas"));
		selenium.clickAt("link=Arenas", RuntimeVariables.replace("Arenas"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Arenas"),
			selenium.getText("//li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		selenium.mouseOver("link=Arenas");
		assertTrue(selenium.isVisible("link=Arena Pernambuco"));
		selenium.clickAt("link=Arena Pernambuco",
			RuntimeVariables.replace("Arena Pernambuco"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Arenas"),
			selenium.getText("//li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Arena Pernambuco"),
			selenium.getText("//li[4]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		selenium.mouseOver("link=Arenas");
		assertTrue(selenium.isVisible("link=Arena da Baixada"));
		selenium.clickAt("link=Arena da Baixada",
			RuntimeVariables.replace("Arena da Baixada"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Arenas"),
			selenium.getText("//li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Arena da Baixada"),
			selenium.getText("//li[4]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		selenium.mouseOver("link=Arenas");
		assertTrue(selenium.isVisible("link=Maracana"));
		selenium.clickAt("link=Maracana", RuntimeVariables.replace("Maracana"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Arenas"),
			selenium.getText("//li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Maracana"),
			selenium.getText("//li[4]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		assertTrue(selenium.isElementNotPresent("link=Accommodations"));
		assertTrue(selenium.isElementNotPresent("link=Maps"));
		Thread.sleep(5000);
		Thread.sleep(5000);
		selenium.close();
		selenium.selectWindow("null");
	}
}