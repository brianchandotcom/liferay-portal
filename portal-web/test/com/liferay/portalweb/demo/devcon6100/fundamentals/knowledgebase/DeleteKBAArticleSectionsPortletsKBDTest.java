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
public class DeleteKBAArticleSectionsPortletsKBDTest extends BaseTestCase {
	public void testDeleteKBAArticleSectionsPortletsKBD()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Knowledge Base Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Knowledge Base Display Test Page",
			RuntimeVariables.replace("Knowledge Base Display Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("KB Admin Article"),
			selenium.getText("//tr[3]/td[2]/a"));
		selenium.clickAt("//tr[3]/td[2]/a",
			RuntimeVariables.replace("KB Admin Article"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("KB Admin Article"),
			selenium.getText("//div[@class='kb-title']"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='kb-article-icons']/table/tbody/tr/td[5]/span/a"));
		selenium.clickAt("//div[@class='kb-article-icons']/table/tbody/tr/td[5]/span/a",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertFalse(selenium.isTextPresent("KBA Article Portlets Title"));
	}
}