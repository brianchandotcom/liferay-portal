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
public class ViewKBAArticleSectionsPortletsTest extends BaseTestCase {
	public void testViewKBAArticleSectionsPortlets() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[3]/td[3]/a"));
		assertTrue(selenium.isVisible("//tr[3]/td[4]/a"));
		assertTrue(selenium.isVisible("//tr[3]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("0 (Approved)"),
			selenium.getText("//tr[3]/td[6]/a"));
		assertTrue(selenium.isVisible("//tr[3]/td[7]/a"));
		selenium.clickAt("//tr[3]/td[2]/a",
			RuntimeVariables.replace("KB Admin Article"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("KB Admin Article"),
			selenium.getText("//div[@class='kb-title']"));
		assertEquals(RuntimeVariables.replace(
				"This is an article created from the KB Admin"),
			selenium.getText("//div[@class='kb-entity-body']/p"));
	}
}