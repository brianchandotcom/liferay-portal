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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.linkwdfrontpagechildpagetowdfrontpagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LinkWDFrontPageChildPageToWDFrontPageChildPageTest
	extends BaseTestCase {
	public void testLinkWDFrontPageChildPageToWDFrontPageChildPage()
		throws Exception {
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
			selenium.getText("xPath=(//div[@class='child-pages']/ul/li/a)[2]"));
		selenium.clickAt("xPath=(//div[@class='child-pages']/ul/li/a)[2]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage2 Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isTextPresent("Wiki Front Page Child Page1 Title"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//div[3]/span[1]/a/span"));
		selenium.clickAt("//div[3]/span[1]/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//textarea[contains(@id,'_editor') and contains(@style,'display: none;')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@class='cke_button_source cke_on']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//td[contains(@id,'cke_contents__54')]/textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//td[contains(@id,'cke_contents__54')]/textarea",
			RuntimeVariables.replace(
				"Wiki FrontPage ChildPage2 Content\n\n[[Wiki FrontPage ChildPage1 Title]]"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//textarea[contains(@id,'_editor') and contains(@style,'display: none;')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"//td[contains(@id,'cke_contents__54')]/iframe"));
		selenium.selectFrame("//td[contains(@id,'cke_contents__54')]/iframe");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText("//body",
							"Wiki FrontPage ChildPage1 Title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText("//body",
							"Wiki FrontPage ChildPage2 Content")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"),
			selenium.getText("//div[@class='wiki-body']/p/a"));
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
			selenium.getText("xPath=(//div[@class='child-pages']/ul/li/a)[2]"));
		selenium.clickAt("xPath=(//div[@class='child-pages']/ul/li/a)[2]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage2 Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//div[@class='wiki-body']/p/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage1 Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}