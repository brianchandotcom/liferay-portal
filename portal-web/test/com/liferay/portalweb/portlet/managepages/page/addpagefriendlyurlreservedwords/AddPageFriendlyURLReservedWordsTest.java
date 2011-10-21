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

package com.liferay.portalweb.portlet.managepages.page.addpagefriendlyurlreservedwords;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageFriendlyURLReservedWordsTest extends BaseTestCase {
	public void testAddPageFriendlyURLReservedWords() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//li[contains(@class,'manage-page')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//li[contains(@class,'manage-page')]/a",
					RuntimeVariables.replace("Manage Pages"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Public Pages")
												.equals(selenium.getText(
										"//a[@class='layout-tree']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//a[@class='layout-tree']"));

				boolean welcomePresent = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcomePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Welcome")
												.equals(selenium.getText(
										"//li/ul/li[1]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Welcome"),
					selenium.getText("//li/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Manage Pages Test Page"),
					selenium.getText("//li/ul/li[2]/div/div[3]/a"));
				selenium.clickAt("//li/ul/li[2]/div/div[3]/a",
					RuntimeVariables.replace("Manage Pages Test Page"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Manage Pages Test Page")
												.equals(selenium.getValue(
										"//div[1]/fieldset/div/span[1]/span/span/span/input"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals("Manage Pages Test Page",
					selenium.getValue(
						"//div[1]/fieldset/div/span[1]/span/span/span/input"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/c"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword c."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/combo"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword combo."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/delegate"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword delegate."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/display_chart"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword display_chart."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/dtd"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword dtd."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/facebook"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Facebook."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/google_gadget"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword google_gadget."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/group"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Group."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/html"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword HTML."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/image"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Image."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/language"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Language."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/netvibes"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Netvibes."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/page"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Page."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/pbhs"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword pbhs."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/poller"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword poller."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/public"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Public."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/private"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Private."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/rss"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword RSS."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/sharepoint"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword sharepoint."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/sitemap.xml"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword sitemap.xml."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/tags"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Tags."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/software_catalog"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword software_catalog."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/_vti_"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword _vti_."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/wap"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword wap."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/web"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword web."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/widget"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword widget."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/wsrp"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword wsrp."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));
				selenium.type("//span[2]/input[@id='_88_friendlyURL']",
					RuntimeVariables.replace("/xmlrpc"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword xmlrpc."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));

			case 100:
				label = -1;
			}
		}
	}
}