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

package com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettyperequired;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddVocabularyAssetTypeRequiredTest extends BaseTestCase {
	public void testAddVocabularyAssetTypeRequired() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Categories",
					RuntimeVariables.replace("Categories"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Vocabulary']",
					RuntimeVariables.replace("Add Vocabulary"));
				selenium.waitForVisible("//input[@id='_147_title_en_US']");
				selenium.type("//input[@id='_147_title_en_US']",
					RuntimeVariables.replace("Vocabulary Name"));
				selenium.type("//textarea[@id='_147_description_en_US']",
					RuntimeVariables.replace("Vocabulary Description"));
				assertEquals(RuntimeVariables.replace("Associated Asset Types"),
					selenium.getText(
						"//div[@id='vocabularyExtraFieldsPanelContainer']/div/div/span"));

				boolean requiredCheckBoxNotVisible = selenium.isVisible(
						"_147_required0Checkbox");

				if (requiredCheckBoxNotVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='vocabularyExtraFieldsPanelContainer']/div/div/span",
					RuntimeVariables.replace("Associated Asset Types"));
				selenium.waitForVisible("//input[@id='_147_required0Checkbox']");

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='_147_required0Checkbox']"));
				selenium.clickAt("//input[@id='_147_required0Checkbox']",
					RuntimeVariables.replace("Required"));
				assertTrue(selenium.isChecked(
						"//input[@id='_147_required0Checkbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForVisible(
					"//div[@class='lfr-message-response portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));
				selenium.waitForText("//span[@class='vocabulary-item']/a",
					"Vocabulary Name");
				assertEquals(RuntimeVariables.replace("Vocabulary Name"),
					selenium.getText("//span[@class='vocabulary-item']/a"));

			case 100:
				label = -1;
			}
		}
	}
}