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
public class ViewImportExportLARPublicPagesSiteLARImportSiteTest
	extends BaseTestCase {
	public void testViewImportExportLARPublicPagesSiteLARImportSite()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//ul[contains(.,'Go to')]/li/a"));
		Thread.sleep(5000);
		selenium.clickAt("//ul[contains(.,'Go to')]/li/a",
			RuntimeVariables.replace("Go to"));
		selenium.waitForVisible(
			"//li[contains(.,'LAR Import Site')]/a/span[contains(.,'Public')]");
		assertEquals(RuntimeVariables.replace("Public"),
			selenium.getText(
				"//li[contains(.,'LAR Import Site')]/a/span[contains(.,'Public')]"));
		selenium.clickAt("//li[contains(.,'LAR Import Site')]/a/span[contains(.,'Public')]",
			RuntimeVariables.replace("Public"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@class='logo custom-logo']");
		assertTrue(selenium.isVisible("//a[@class='logo custom-logo']"));
		assertTrue(selenium.isElementPresent("//img[@height='156']"));
		assertTrue(selenium.isElementPresent("//img[@width='320']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='logo default-logo']"));
		assertTrue(selenium.isElementPresent(
				"//body[@class='green yui3-skin-sam controls-visible signed-in public-page site dockbar-ready']"));
		assertTrue(selenium.isElementNotPresent(
				"//body[@class='blue yui3-skin-sam controls-visible signed-in public-page site dockbar-ready']"));
		assertTrue(selenium.isVisible("link=Home"));
		selenium.clickAt("link=Home", RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//nav/ul/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		assertTrue(selenium.isVisible("link=Arenas"));
		selenium.clickAt("link=Arenas", RuntimeVariables.replace("Arenas"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Arenas"),
			selenium.getText("//nav/ul/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		selenium.mouseOver("link=Arenas");
		assertTrue(selenium.isVisible("link=Arena Pernambuco"));
		selenium.clickAt("link=Arena Pernambuco",
			RuntimeVariables.replace("Arena Pernambuco"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Arenas"),
			selenium.getText("//nav/ul/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Arena Pernambuco"),
			selenium.getText("//nav/ul/li[4]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		selenium.mouseOver("link=Arenas");
		assertTrue(selenium.isVisible("link=Arena da Baixada"));
		selenium.clickAt("link=Arena da Baixada",
			RuntimeVariables.replace("Arena da Baixada"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Arenas"),
			selenium.getText("//nav/ul/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Arena da Baixada"),
			selenium.getText("//nav/ul/li[4]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		selenium.mouseOver("link=Arenas");
		assertTrue(selenium.isVisible("link=Maracana"));
		selenium.clickAt("link=Maracana", RuntimeVariables.replace("Maracana"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Arenas"),
			selenium.getText("//nav/ul/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Maracana"),
			selenium.getText("//nav/ul/li[4]/span/a"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		assertTrue(selenium.isElementNotPresent("link=Accommodations"));
		assertTrue(selenium.isElementNotPresent("link=Maps"));
	}
}