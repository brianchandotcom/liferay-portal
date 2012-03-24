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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.renamewdfrontpagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RenameWDFrontPageChildPageTest extends BaseTestCase {
	public void testRenameWDFrontPageChildPage() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Wiki Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//div[@class='child-pages']/ul/li/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage1 Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText("//div[3]/span[2]/a/span"));
		selenium.clickAt("//div[3]/span[2]/a/span",
			RuntimeVariables.replace("Details"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText("//tr[9]/td/ul/li[3]/a/span"));
		selenium.clickAt("//tr[9]/td/ul/li[3]/a/span",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.type("//div/span/span/span/input",
			RuntimeVariables.replace("Wiki FrontPage ChildPage2 Title"));
		selenium.clickAt("//input[@value='Rename']",
			RuntimeVariables.replace("Rename"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage2 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"(Redirected from Wiki FrontPage ChildPage1 Title)"),
			selenium.getText("//div[@class='page-redirect']"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage1 Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertNotEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Wiki Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage2 Title"),
			selenium.getText("//div[@class='child-pages']/ul/li/a"));
		assertFalse(selenium.isTextPresent("Wiki FrontPage ChildPage1 Title"));
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Wiki Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=All Pages", RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"),
			selenium.getText("//tr[4]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage2 Title"),
			selenium.getText("//tr[5]/td[1]/a"));
	}
}