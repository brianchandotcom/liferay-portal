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

package com.liferay.portalweb.demo.devcon6100.media.dmcheckincheckoutdocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CompareVersionsDMDocumentTxtTest extends BaseTestCase {
	public void testCompareVersionsDMDocumentTxt() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Documents and Media Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Document Title Edit"),
			selenium.getText(
				"//div[@data-title='DM Document Title Edit']/a/span[2]"));
		selenium.clickAt("//div[@data-title='DM Document Title Edit']/a/span[2]",
			RuntimeVariables.replace("DM Document Title Edit"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isChecked("//tr[3]/td[1]/input"));
		selenium.clickAt("//tr[3]/td[1]/input", RuntimeVariables.replace("1.1"));
		assertTrue(selenium.isChecked("//tr[3]/td[1]/input"));
		assertFalse(selenium.isChecked("//tr[4]/td[1]/input"));
		selenium.clickAt("//tr[4]/td[1]/input", RuntimeVariables.replace("1.0"));
		assertTrue(selenium.isChecked("//tr[4]/td[1]/input"));
		selenium.clickAt("//input[@value='Compare Versions']",
			RuntimeVariables.replace("Compare Versions"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Document Title 1.0"),
			selenium.getText("//td"));
		assertEquals(RuntimeVariables.replace("DM Document Title Edit 1.1"),
			selenium.getText("//td[2]"));
		assertEquals(RuntimeVariables.replace("Line 11"),
			selenium.getText("//th"));
		assertEquals(RuntimeVariables.replace("Line 11"),
			selenium.getText("//th[2]"));
		assertEquals(RuntimeVariables.replace("This is a *.txt document."),
			selenium.getText(
				"xPath=(//table[@class='taglib-diff-table'])[2]//ins"));
	}
}