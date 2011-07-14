/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentLocalizedTest extends BaseTestCase {
	public void testViewWebContentLocalized() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("link=Web Content"));
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("Hello World Localized Article"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		assertEquals("Hello World Page Name",
			selenium.getValue("//input[@id='page-name']"));
		assertEquals("Hello World Page Description",
			selenium.getValue("//input[@id='page-description']"));
		selenium.clickAt("//a[@id='_15_changeLanguageId']",
			RuntimeVariables.replace("Change"));
		selenium.select("//select[@name='_15_defaultLanguageId']",
			RuntimeVariables.replace("Chinese (China)"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		assertEquals("\u4e16\u754c\u60a8\u597d Page Name",
			selenium.getValue("//input[@id='page-name']"));
		assertEquals("\u4e16\u754c\u60a8\u597d Page Description",
			selenium.getValue("//input[@id='page-description']"));
		selenium.clickAt("_15_changeLanguageId",
			RuntimeVariables.replace("Change"));
		selenium.select("_15_defaultLanguageId",
			RuntimeVariables.replace("English (United States)"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
	}
}