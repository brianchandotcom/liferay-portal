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

package com.liferay.portalweb.demo.devcon6100.media.dmdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMFolderMusicTagMP3Test extends BaseTestCase {
	public void testAddDMFolderMusicTagMP3() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"link=Documents and Media Test Page")) {
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
				assertEquals(RuntimeVariables.replace("DM Folder Name"),
					selenium.getText(
						"//div[@data-title='DM Folder Name']/a/span[@class='entry-title']"));
				selenium.clickAt("//div[@data-title='DM Folder Name']/a/span[@class='entry-title']",
					RuntimeVariables.replace("DM Folder Name"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("DM Folder Name")
												.equals(selenium.getText(
										"//li[@class='folder selected']/a/span[@class='entry-title']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("DM Folder Name"),
					selenium.getText(
						"//li[@class='folder selected']/a/span[@class='entry-title']"));
				assertEquals(RuntimeVariables.replace("DM Music Title"),
					selenium.getText(
						"//div[@data-title='DM Music Title']/a/span[@class='entry-title']"));
				selenium.clickAt("//div[@data-title='DM Music Title']/a/span[@class='entry-title']",
					RuntimeVariables.replace("DM Music Title"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText("//button[.='Edit']"));
				selenium.clickAt("//button[.='Edit']",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
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

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@title='Add Tags']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@title='Add Tags']",
					RuntimeVariables.replace("MP3"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"Your request completed successfully.")
												.equals(selenium.getText(
										"//div[@class='portlet-msg-success']"))) {
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
				assertEquals(RuntimeVariables.replace("mp3"),
					selenium.getText("//span[@class='tag' and .='mp3']"));

			case 100:
				label = -1;
			}
		}
	}
}