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

package com.liferay.portalweb.demo.media.dmdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDMMetadataSetTest extends BaseTestCase {
	public void testTearDownDMMetadataSet() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Manage"),
					selenium.getText(
						"//span[@title='Manage']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Manage']/ul/li/strong/a/span",
					RuntimeVariables.replace("Manage"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Metadata Sets')]");
				assertEquals(RuntimeVariables.replace("Metadata Sets"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Metadata Sets')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Metadata Sets')]",
					RuntimeVariables.replace("Metadata Sets"));
				selenium.waitForVisible(
					"//iframe[contains(@src,'DLFileEntryMetadata')]");
				selenium.selectFrame(
					"//iframe[contains(@src,'DLFileEntryMetadata')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible(
					"//input[@id='_166_toggle_id_ddm_structure_searchkeywords']");
				selenium.type("//input[@id='_166_toggle_id_ddm_structure_searchkeywords']",
					RuntimeVariables.replace("song"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean metadataSetPresent = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!metadataSetPresent) {
					label = 2;

					continue;
				}

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
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}