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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontenttemplatewcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalizeWCWebContentTemplateWCDTest extends BaseTestCase {
	public void testLocalizeWCWebContentTemplateWCD() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"link=Web Content Display Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//img[@alt='Edit Web Content']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//img[@alt='Edit Web Content']",
					RuntimeVariables.replace("Edit Web Content"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Hello World Localized Article"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace(
						"Web Content Localized Structure Name"),
					selenium.getText("//span[@class='structure-name-label']"));
				assertEquals(RuntimeVariables.replace(
						"Web Content Localized Template Name"),
					selenium.getText("//span[@class='template-name-label']"));
				assertTrue(selenium.isElementPresent(
						"//span[2]/div/span/span/span/input[2]"));

				boolean localizablePageNameChecked = selenium.isChecked(
						"//span[2]/div/span/span/span/input[2]");

				if (localizablePageNameChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span[2]/div/span/span/span/input[2]",
					RuntimeVariables.replace(""));

			case 2:
				assertTrue(selenium.isElementPresent(
						"//li[2]/span[2]/div/span/span/span/input[2]"));

				boolean localizablePageDescriptionChecked = selenium.isChecked(
						"//li[2]/span[2]/div/span/span/span/input[2]");

				if (localizablePageDescriptionChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li[2]/span[2]/div/span/span/span/input[2]",
					RuntimeVariables.replace(""));

			case 3:
				assertTrue(selenium.isChecked(
						"//span[2]/div/span/span/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//li[2]/span[2]/div/span/span/span/input[2]"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save as Draft']",
					RuntimeVariables.replace("Save as Draft"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Status: Draft"),
					selenium.getText("//span[@class='workflow-status']"));
				assertEquals("Hello World Page Name",
					selenium.getValue("//input[@id='page-name']"));
				assertEquals("Hello World Page Description",
					selenium.getValue("//input[@id='page-description']"));
				assertEquals(RuntimeVariables.replace("Add Translation"),
					selenium.getText("//strong/a/span"));
				selenium.clickAt("//strong/a/span",
					RuntimeVariables.replace("Add Translation"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Chinese (China)"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.click(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//iframe[@id='_15_zh_CN']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.selectFrame("//iframe[@id='_15_zh_CN']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='page-name']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='page-name']",
					RuntimeVariables.replace(
						"\u4e16\u754c\u60a8\u597d Page Name"));
				selenium.type("//input[@id='page-description']",
					RuntimeVariables.replace(
						"\u4e16\u754c\u60a8\u597d Page Description"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.selectFrame("relative=top");
				assertEquals(RuntimeVariables.replace("Chinese (China)"),
					selenium.getText(
						"//a[@class='journal-article-translation journal-article-translation-zh_CN']"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Hello World Page Name"),
					selenium.getText("//td[@class='page-name']"));
				assertEquals(RuntimeVariables.replace(
						"Hello World Page Description"),
					selenium.getText("//td[@class='page-description']"));

			case 100:
				label = -1;
			}
		}
	}
}