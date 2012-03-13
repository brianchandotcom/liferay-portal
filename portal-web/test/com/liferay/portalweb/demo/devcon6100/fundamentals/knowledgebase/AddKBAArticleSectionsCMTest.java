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

package com.liferay.portalweb.demo.devcon6100.fundamentals.knowledgebase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddKBAArticleSectionsCMTest extends BaseTestCase {
	public void testAddKBAArticleSectionsCM() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
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
		selenium.clickAt("link=Knowledge Base (Admin)",
			RuntimeVariables.replace("Knowledge Base (Admin)"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//input[@value='Add Article']",
			RuntimeVariables.replace("Add Article"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_1_WAR_knowledgebaseportlet_title']",
			RuntimeVariables.replace("Knowledge Base Article 1"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//td[@id='cke_contents__1_WAR_knowledgebaseportlet_editor']/iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame(
			"//td[@id='cke_contents__1_WAR_knowledgebaseportlet_editor']/iframe");
		selenium.type("//body",
			RuntimeVariables.replace("This is the content of Article 1"));
		selenium.selectFrame("relative=top");
		selenium.addSelection("//select[@id='_1_WAR_knowledgebaseportlet_sections']",
			RuntimeVariables.replace("Content Management"));
		assertEquals("Content Management",
			selenium.getSelectedLabel(
				"//select[@id='_1_WAR_knowledgebaseportlet_sections']"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
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
		selenium.clickAt("link=Knowledge Base (Admin)",
			RuntimeVariables.replace("Knowledge Base (Admin)"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='_1_WAR_knowledgebaseportlet_keywords']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals("1.0",
			selenium.getValue("//tr[3]/td[2]/span/span/span/input"));
		assertEquals(RuntimeVariables.replace("Knowledge Base Article 1"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[3]/td[4]"));
		assertTrue(selenium.isVisible("//tr[3]/td[5]"));
		assertTrue(selenium.isVisible("//tr[3]/td[6]"));
		assertEquals(RuntimeVariables.replace("0 (Approved)"),
			selenium.getText("//tr[3]/td[7]"));
		assertTrue(selenium.isVisible("//tr[3]/td[8]"));
	}
}