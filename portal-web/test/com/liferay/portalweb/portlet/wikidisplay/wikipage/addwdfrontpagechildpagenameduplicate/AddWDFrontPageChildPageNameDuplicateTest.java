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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.addwdfrontpagechildpagenameduplicate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWDFrontPageChildPageNameDuplicateTest extends BaseTestCase {
	public void testAddWDFrontPageChildPageNameDuplicate()
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
		assertEquals(RuntimeVariables.replace("Add Child Page"),
			selenium.getText("//div[1]/span[1]/a/span"));
		selenium.clickAt("//div[1]/span[1]/a/span",
			RuntimeVariables.replace("Add Child Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[contains(@id,'_title')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title"));

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
			RuntimeVariables.replace("Wiki FrontPage ChildPage Content"));
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
				if (RuntimeVariables.replace("Wiki FrontPage ChildPage Content")
										.equals(selenium.getText("//body"))) {
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
				"Your request failed to complete."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"There is already a page with the specified title."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"This page does not exist yet. Use the form below to create it."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}