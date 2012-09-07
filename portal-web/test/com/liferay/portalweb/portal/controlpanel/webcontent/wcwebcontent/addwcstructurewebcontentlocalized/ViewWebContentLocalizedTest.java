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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcstructurewebcontentlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentLocalizedTest extends BaseTestCase {
	public void testViewWebContentLocalized() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Hello World Localized Article"),
			selenium.getText("//td[3]/a"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("Hello World Localized Article"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Hello World Localized Article",
			selenium.getValue("//input[@id='_15_title_en_US']"));
		assertEquals("Hello World Page Name",
			selenium.getValue("//input[@id='page-name']"));
		assertEquals("Hello World Page Description",
			selenium.getValue("//input[@id='page-description']"));
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText("//span[@id='_15_availableTranslationsLinks']/a"));
		selenium.clickAt("//span[@id='_15_availableTranslationsLinks']/a",
			RuntimeVariables.replace("Chinese (China)"));
		selenium.waitForVisible("//iframe[@id='_15_zh_CN']");
		selenium.selectFrame("//iframe[@id='_15_zh_CN']");
		selenium.waitForVisible("//h1[@class='header-title']/span");
		assertEquals(RuntimeVariables.replace("Hello World Localized Article"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Translating Web Content to Chinese (China)"),
			selenium.getText("//div[@id='_15_availableTranslationContainer']"));
		assertEquals("Hello World Localized Article",
			selenium.getValue("//input[@id='_15_title_zh_CN']"));
		assertEquals("\u4e16\u754c\u60a8\u597d Page Name",
			selenium.getValue("//input[@id='page-name']"));
		assertEquals("\u4e16\u754c\u60a8\u597d Page Description",
			selenium.getValue("//input[@id='page-description']"));
		selenium.selectFrame("relative=top");
	}
}