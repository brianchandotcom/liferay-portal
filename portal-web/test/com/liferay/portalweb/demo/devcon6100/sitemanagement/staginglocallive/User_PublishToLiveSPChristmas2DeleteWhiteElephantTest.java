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

package com.liferay.portalweb.demo.devcon6100.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_PublishToLiveSPChristmas2DeleteWhiteElephantTest
	extends BaseTestCase {
	public void testUser_PublishToLiveSPChristmas2DeleteWhiteElephant()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Site Name")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertFalse(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText(
						"//div[@class='staging-bar']/ul/li[2]/span/a"));
				selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertFalse(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));

				boolean christmas2Present = selenium.isElementPresent(
						"link=Christmas 2");

				if (!christmas2Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Christmas 2",
					RuntimeVariables.replace("Christmas 2"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Christmas 2 Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				Thread.sleep(5000);
				assertTrue(selenium.isVisible("link=Home"));
				selenium.clickAt("link=Home", RuntimeVariables.replace("Home"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("//span[2]/span/ul/li/strong/a",
					RuntimeVariables.replace("Christmas 2 Staging"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Publish Christmas 2 to Live now."),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a",
					RuntimeVariables.replace("Publish Christmas 2 to Live now."));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[2]/div[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@value='Change Selection']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Change Selection']",
					RuntimeVariables.replace("Change Selection"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"Note that selecting no pages from the tree reverts to implicit selection of all pages.")
												.equals(selenium.getText(
										"//div[@class='portlet-msg-info']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Note that selecting no pages from the tree reverts to implicit selection of all pages."),
					selenium.getText("//div[@class='portlet-msg-info']"));

				boolean treeNotExpanded = selenium.isElementPresent(
						"//div[contains(@class,'aui-tree-expanded')]");

				if (treeNotExpanded) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[contains(@class,'aui-tree-expanded')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isVisible(
						"//div[contains(@class,'aui-tree-expanded')]"));
				assertTrue(selenium.isVisible(
						"//div[contains(@class,'aui-tree-collapsed')]"));

			case 3:
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[4]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//div[4]"));
				selenium.clickAt("//div[4]",
					RuntimeVariables.replace("Public Pages"));
				selenium.clickAt("//div[4]",
					RuntimeVariables.replace("Public Pages"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//li/ul/li[1]/div/div[4]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText("//li/ul/li[1]/div/div[4]"));
				selenium.clickAt("//li/ul/li[1]/div/div[4]",
					RuntimeVariables.replace("Home"));
				assertEquals(RuntimeVariables.replace("Calendar"),
					selenium.getText("//li[2]/div/div[4]"));
				selenium.clickAt("//li[2]/div/div[4]",
					RuntimeVariables.replace("Calendar"));
				assertEquals(RuntimeVariables.replace("Wiki"),
					selenium.getText("//li[3]/div/div[4]"));
				selenium.clickAt("//li[3]/div/div[4]",
					RuntimeVariables.replace("Wiki"));
				assertEquals(RuntimeVariables.replace("White Elephant"),
					selenium.getText("//li[4]/div/div[4]"));
				selenium.clickAt("//li[4]/div/div[4]",
					RuntimeVariables.replace("White Elephant"));
				assertEquals(RuntimeVariables.replace("Prices"),
					selenium.getText("//li[5]/div/div[4]"));
				selenium.clickAt("//li[5]/div/div[4]",
					RuntimeVariables.replace("Prices"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//tr/td[2]/span[contains(.,'Home')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText("//tr/td[2]/span[contains(.,'Home')]"));
				assertEquals(RuntimeVariables.replace("Calendar"),
					selenium.getText("//tr/td[2]/span[contains(.,'Calendar')]"));
				assertEquals(RuntimeVariables.replace("Wiki"),
					selenium.getText("//tr/td[2]/span[contains(.,'Wiki')]"));
				assertEquals(RuntimeVariables.replace("White Elephant"),
					selenium.getText(
						"//tr/td[2]/span[contains(.,'White Elephant')]"));
				assertEquals(RuntimeVariables.replace("Change"),
					selenium.getText(
						"//tr[contains(.,'White Elephant')]/td[3]/div/span/a"));
				selenium.clickAt("//tr[contains(.,'White Elephant')]/td[3]/div/span/a",
					RuntimeVariables.replace("Change"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//tr[contains(.,'White Elephant')]/td[3]/div[2]/span[1]/span/span/input")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//tr[contains(.,'White Elephant')]/td[3]/div[2]/span[1]/span/span/input",
					RuntimeVariables.replace("Delete live page."));
				assertEquals(RuntimeVariables.replace("Prices"),
					selenium.getText("//tr/td[2]/span[contains(.,'Prices')]"));
				Thread.sleep(5000);

				boolean allVisible = selenium.isVisible("_88_rangeAll");

				if (allVisible) {
					label = 4;

					continue;
				}

				selenium.clickAt("//div[2]/div[1]/a",
					RuntimeVariables.replace("Plus"));

			case 4:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@id='_88_rangeAll']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@id='_88_rangeAll']",
					RuntimeVariables.replace("All"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Publish']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}