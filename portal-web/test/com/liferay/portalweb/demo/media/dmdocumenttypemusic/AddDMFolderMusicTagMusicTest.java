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

package com.liferay.portalweb.demo.media.dmdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMFolderMusicTagMusicTest extends BaseTestCase {
	public void testAddDMFolderMusicTagMusic() throws Exception {
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
				assertEquals(RuntimeVariables.replace("DM Folder Name"),
					selenium.getText(
						"//div[@data-title='DM Folder Name']/a/span[@class='entry-title']"));
				selenium.clickAt("//div[@data-title='DM Folder Name']/a/span[@class='entry-title']",
					RuntimeVariables.replace("DM Folder Name"));
				selenium.waitForVisible(
					"//li[@class='folder selected']/a/span[@class='entry-title']");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("DM Music Title"),
					selenium.getText(
						"//div[@data-title='DM Music Title']/a/span[@class='entry-title']"));
				selenium.clickAt("//div[@data-title='DM Music Title']/a/span[@class='entry-title']",
					RuntimeVariables.replace("DM Music Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText("//button[.='Edit']"));
				selenium.clickAt("//button[.='Edit']",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Categorization"),
					selenium.getText(
						"//div[@id='dlFileEntryCategorizationPanel']/div/div/span"));

				boolean categorizationCollapsed = selenium.isElementPresent(
						"//div[@id='dlFileEntryCategorizationPanel' and contains(@class,'lfr-collapsed')]");

				if (!categorizationCollapsed) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='dlFileEntryCategorizationPanel']/div/div/span",
					RuntimeVariables.replace("Categorization"));

			case 2:
				selenium.waitForVisible("//input[@title='Add Tags']");
				selenium.type("//input[@title='Add Tags']",
					RuntimeVariables.replace("Music"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("music"),
					selenium.getText("//span[@class='tag' and .='music']"));

			case 100:
				label = -1;
			}
		}
	}
}