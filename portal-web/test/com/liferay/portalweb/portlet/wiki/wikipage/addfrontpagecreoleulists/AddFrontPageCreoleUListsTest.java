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

package com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoleulists;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFrontPageCreoleUListsTest extends BaseTestCase {
	public void testAddFrontPageCreoleULists() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"This page is empty. Edit it to add some text."),
			selenium.getText("//div[@class='wiki-body']/div/a"));
		selenium.clickAt("//div[@class='wiki-body']/div/a",
			RuntimeVariables.replace(
				"This page is empty. Edit it to add some text."));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertTrue(selenium.isVisible("//span[@id='cke_34_label']"));
		selenium.clickAt("//span[@id='cke_34_label']",
			RuntimeVariables.replace("Source"));
		assertTrue(selenium.isVisible(
				"//td[@id='cke_contents__36_editor']/textarea"));
		selenium.type("//td[@id='cke_contents__36_editor']/textarea",
			RuntimeVariables.replace(
				"* Item1\n** Subitem1a\n* Item2\n** Subitem2a\n** Subitem2b\n* Item3\n** Subitem3a\n** Subitem3b\n** Subitem3c"));
		selenium.clickAt("//span[@id='cke_34_label']",
			RuntimeVariables.replace("Source"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//td[@id='cke_contents__36_editor']/iframe"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Item1 Subitem1a Item2 Subitem2a Subitem2b Item3 Subitem3a Subitem3b Subitem3c"),
			selenium.getText("//div[@class='wiki-body']/ul"));
		assertEquals(RuntimeVariables.replace("Item1"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/li)[1]"));
		assertEquals(RuntimeVariables.replace("Subitem1a"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/ul)[1]"));
		assertEquals(RuntimeVariables.replace("Subitem1a"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/ul/li)[1]"));
		assertEquals(RuntimeVariables.replace("Item2"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/li)[2]"));
		assertEquals(RuntimeVariables.replace("Subitem2a Subitem2b"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/ul)[2]"));
		assertEquals(RuntimeVariables.replace("Subitem2a"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/ul/li)[2]"));
		assertEquals(RuntimeVariables.replace("Subitem2b"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/ul/li)[3]"));
		assertEquals(RuntimeVariables.replace("Item3"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/li)[3]"));
		assertEquals(RuntimeVariables.replace("Subitem3a Subitem3b Subitem3c"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/ul)[3]"));
		assertEquals(RuntimeVariables.replace("Subitem3a"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/ul/li)[4]"));
		assertEquals(RuntimeVariables.replace("Subitem3b"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/ul/li)[5]"));
		assertEquals(RuntimeVariables.replace("Subitem3c"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ul/ul/li)[6]"));
	}
}