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

package com.liferay.portalweb.portlet.documentlibrarydisplay.document.deletedldocumentmultipledldactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteDLDocumentMultipleDLDActionsTest extends BaseTestCase {
	public void testDeleteDLDocumentMultipleDLDActions()
		throws Exception {
		selenium.open("/web/guest/home");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"link=Documents and Media Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Documents and Media Display Test Page",
			RuntimeVariables.replace("Documents and Media Display Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		Thread.sleep(5000);
		assertFalse(selenium.isChecked("//tr[3]/td[1]/input"));
		selenium.clickAt("//tr[3]/td[1]/input",
			RuntimeVariables.replace("DL Document 1 Checkbox"));
		assertTrue(selenium.isChecked("//tr[3]/td[1]/input"));
		assertFalse(selenium.isChecked("//tr[4]/td[1]/input"));
		selenium.clickAt("//tr[4]/td[1]/input",
			RuntimeVariables.replace("DL Document 2 Checkbox"));
		assertTrue(selenium.isChecked("//tr[4]/td[1]/input"));
		assertFalse(selenium.isChecked("//tr[5]/td[1]/input"));
		selenium.clickAt("//tr[5]/td[1]/input",
			RuntimeVariables.replace("DL Document 3 Checkbox"));
		assertTrue(selenium.isChecked("//tr[5]/td[1]/input"));
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace(""));
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));

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
		assertFalse(selenium.isTextPresent("DL Document1 Title"));
		assertFalse(selenium.isTextPresent("DL Document2 Title"));
		assertFalse(selenium.isTextPresent("DL Document3 Title"));
	}
}