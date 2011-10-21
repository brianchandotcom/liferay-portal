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

package com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoletableofcontents;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFrontPageCreoleTableOfContentsTest extends BaseTestCase {
	public void testAddFrontPageCreoleTableOfContents()
		throws Exception {
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
				"<<TableOfContents>>\n== Unit ==\n=== Chapter ===\n==== Section ===="));
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
		assertEquals(RuntimeVariables.replace("Table of Contents [-]"),
			selenium.getText("//div[@class='collapsebox']/h4"));
		assertEquals(RuntimeVariables.replace("[-]"),
			selenium.getText("//div[@class='collapsebox']//a"));
		assertEquals(RuntimeVariables.replace("Unit"),
			selenium.getText("//div[@class='toc-index']/ol/li/a"));
		assertEquals(RuntimeVariables.replace("Chapter"),
			selenium.getText("//div[@class='toc-index']/ol/li/ol/li/a"));
		assertEquals(RuntimeVariables.replace("Section"),
			selenium.getText("//div[@class='toc-index']/ol/li/ol/li/ol/li/a"));
		assertEquals(RuntimeVariables.replace("Unit #"),
			selenium.getText("//div[6]/div/h2"));
		assertEquals(RuntimeVariables.replace("Chapter #"),
			selenium.getText("//div[6]/div/h3"));
		assertEquals(RuntimeVariables.replace("Section #"),
			selenium.getText("//div[6]/div/h4"));
	}
}