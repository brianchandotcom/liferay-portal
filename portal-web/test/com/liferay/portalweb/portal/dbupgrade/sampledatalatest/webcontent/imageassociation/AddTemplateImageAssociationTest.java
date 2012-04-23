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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTemplateImageAssociationTest extends BaseTestCase {
	public void testAddTemplateImageAssociation() throws Exception {
		selenium.open("/web/web-content-image-association-community/");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Templates", RuntimeVariables.replace("Templates"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//input[@value='Add Template']",
			RuntimeVariables.replace("Add Template"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_15_name_en_US']",
			RuntimeVariables.replace("Image Template Test"));
		selenium.type("//textarea[@id='_15_description_en_US']",
			RuntimeVariables.replace("This is an image template test."));
		selenium.click("//input[@value='Select']");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//td[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Image Structure Test"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Image Structure Test"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Image Structure Test")
										.equals(selenium.getText(
								"//a[@id='_15_structureName']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Image Structure Test"),
			selenium.getText("//a[@id='_15_structureName']"));
		selenium.clickAt("//input[@id='_15_editorButton']",
			RuntimeVariables.replace("Launch Editor"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//textarea[@id='_15_plainEditorField']")) {
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
				if (selenium.isElementPresent(
							"//div[@class='ace_layer ace_gutter-layer']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//textarea[@id='_15_plainEditorField']",
			RuntimeVariables.replace(
				"<h1 id=\"web-content-title\">$text-test.getData()</h1>\n\n<h2 id=\"image-title\">Image Test:</h2>\n<a id=\"image\"><img src=\"$image-test.data\"></img></a>\n\n<h2 id=\"image-gallery-title\">Image Gallery Image Test:</h2>\n<a id=\"image-gallery\"><img src=\"$image-gallery-test.data\"></img></a>"));
		selenium.clickAt("//input[@value='Update']",
			RuntimeVariables.replace("Update"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Image Template Test"),
			selenium.getText("//tr[3]/td[3]"));
	}
}