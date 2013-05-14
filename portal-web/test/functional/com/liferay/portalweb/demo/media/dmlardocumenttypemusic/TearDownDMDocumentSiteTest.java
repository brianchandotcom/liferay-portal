/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.demo.media.dmlardocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDMDocumentSiteTest extends BaseTestCase {
	public void testTearDownDMDocumentSite() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/site-name/");
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//button[@title='Icon View']",
					RuntimeVariables.replace("Icon View"));
				selenium.waitForVisible(
					"//button[contains(@class,'aui-state-active') and @title='Icon View']");
				assertTrue(selenium.isVisible(
						"//button[contains(@class,'aui-state-active') and @title='Icon View']"));

				boolean dmlDocumentPresent = selenium.isElementPresent(
						"//div[2]/a/span[1]/img");

				if (!dmlDocumentPresent) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_20_allRowIdsCheckbox']"));
				selenium.clickAt("//input[@id='_20_allRowIdsCheckbox']",
					RuntimeVariables.replace("All Entries Check Box"));
				assertTrue(selenium.isChecked(
						"//input[@id='_20_allRowIdsCheckbox']"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				assertEquals(RuntimeVariables.replace(
						"There are no documents or media files in this folder."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}