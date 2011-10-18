/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveTagThroughSelectTagTest extends BaseTestCase {
	public void testRemoveTagThroughSelectTag() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Blogs Tags Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Blogs Tags Test Page",
					RuntimeVariables.replace("Blogs Tags Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Tags Blog Entry3 Title"),
					selenium.getText(
						"xPath=(//div[@class='entry-title']/h2/a)[1]"));
				selenium.clickAt("xPath=(//div[@class='entry-title']/h2/a)[1]",
					RuntimeVariables.replace("Tags Blog Entry3 Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText("//span/a/span"));
				selenium.click(RuntimeVariables.replace("//span/a/span"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//td[@id='cke_contents__33_editor']/iframe")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean tagsVisible = selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']");

				if (tagsVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[3]/div/div/span",
					RuntimeVariables.replace("Categorization"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@class='lfr-tag-selector-input aui-field-input-text']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']"));

			case 2:
				assertEquals(RuntimeVariables.replace("selenium1 liferay1"),
					selenium.getText(
						"xPath=(//span[@class='aui-textboxlistentry-text'])[1]"));
				selenium.clickAt("//button[@id='select']",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//label[1]/input")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//label[1]",
					RuntimeVariables.replace("selenium1 liferay1"));
				selenium.clickAt("//button[@id='closethick']",
					RuntimeVariables.replace("Close"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (!RuntimeVariables.replace("selenium1 liferay1")
												 .equals(selenium.getText(
										"xPath=(//span[@class='aui-textboxlistentry-text'])[1]"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertNotEquals(RuntimeVariables.replace("selenium1 liferay1"),
					selenium.getText(
						"xPath=(//span[@class='aui-textboxlistentry-text'])[1]"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Your request completed successfully."));
				assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
					selenium.getText(
						"xPath=(//span[@class='taglib-asset-tags-summary']/a)[1]"));
				assertFalse(selenium.isTextPresent("selenium1 liferay1"));

			case 100:
				label = -1;
			}
		}
	}
}