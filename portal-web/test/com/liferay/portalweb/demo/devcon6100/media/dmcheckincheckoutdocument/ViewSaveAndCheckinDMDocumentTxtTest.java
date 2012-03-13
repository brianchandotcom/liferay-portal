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
public class ViewSaveAndCheckinDMDocumentTxtTest extends BaseTestCase {
	public void testViewSaveAndCheckinDMDocumentTxt() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Version 1.1"),
			selenium.getText("//h3[contains(@class,'version')]"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("0.5k"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[3]/td[5]"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("0.3k"),
			selenium.getText("//tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[4]/td[5]"));
	}
}