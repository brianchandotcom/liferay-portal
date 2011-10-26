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

package com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypepagerevision;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddVocabularyAssetTypePageRevisionTest extends BaseTestCase {
	public void testAddVocabularyAssetTypePageRevision()
		throws Exception {
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
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Categories",
					RuntimeVariables.replace("Categories"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Vocabulary']",
					RuntimeVariables.replace("Add Vocabulary"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_147_title_en_US']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_147_title_en_US']",
					RuntimeVariables.replace("Vocabulary Name"));
				selenium.type("//textarea[@id='_147_description_en_US']",
					RuntimeVariables.replace("Vocabulary Description"));
				assertEquals(RuntimeVariables.replace("Associated Asset Types"),
					selenium.getText(
						"//div[@id='vocabularyExtraFieldsPanelContainer']/div/div/span"));

				boolean chooseAssetTypeNotVisible = selenium.isVisible(
						"_147_classNameId0");

				if (chooseAssetTypeNotVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='vocabularyExtraFieldsPanelContainer']/div/div/span",
					RuntimeVariables.replace("Associated Asset Types"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//select[@id='_147_classNameId0']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:
				selenium.select("//select[@id='_147_classNameId0']",
					RuntimeVariables.replace("Page Revision"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-message-response portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Vocabulary Name")
												.equals(selenium.getText(
										"//span[@class='vocabulary-item']/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Vocabulary Name"),
					selenium.getText("//span[@class='vocabulary-item']/a"));

			case 100:
				label = -1;
			}
		}
	}
}