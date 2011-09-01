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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTemplateImageAssociationTest extends BaseTestCase {
	public void testAddTemplateImageAssociation() throws Exception {
		selenium.open("/web/web-content-image-association-community/");

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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Templates", RuntimeVariables.replace("Templates"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Template']",
			RuntimeVariables.replace("Add Template"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_15_name']",
			RuntimeVariables.replace("Image Template Test"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_15_description']",
			RuntimeVariables.replace("This is an image template test."));
		selenium.saveScreenShotAndSource();
		selenium.click("//input[@value='Select']");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace(
				"Image Structure Test\nThis is an image structure test."));

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Image Structure Test"),
			selenium.getText("//a[@id='_15_structureName']"));
		selenium.clickAt("//input[@id='_15_editorButton']",
			RuntimeVariables.replace(""));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//textarea[@id='_15_xslContent']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_15_xslContent']",
			RuntimeVariables.replace(
				"<h1 id=\"web-content-title\">$text-test.getData()</h1>\n\n<h2 id=\"image-title\">Image Test:</h2>\n<a id=\"image\"><img src=\"$image-test.data\"></img></a>\n\n<h2 id=\"image-gallery-title\">Image Gallery Image Test:</h2>\n<a id=\"image-gallery\"><img src=\"$image-gallery-test.data\"></img></a>"));
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		selenium.click("//input[@value='Update']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Image Template Test\n This is an image template test."),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
	}
}