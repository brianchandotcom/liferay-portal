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

package com.liferay.portalweb.portal.controlpanel.categories.property.addcategorypropertymultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCategoryProperty1Test extends BaseTestCase {
	public void testAddCategoryProperty1() throws Exception {
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
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Add Category']",
					RuntimeVariables.replace("Add Category"));
				selenium.waitForVisible("//input[@id='_147_title_en_US']");
				selenium.type("//input[@id='_147_title_en_US']",
					RuntimeVariables.replace("Category Name"));
				selenium.type("//textarea[@id='_147_description_en_US']",
					RuntimeVariables.replace("Category Description"));
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Properties"),
					selenium.getText(
						"//div[@id='assetCategoryPropertiesPanel']/div/div/span"));

				boolean propertyNotVisible = selenium.isVisible("_147_key0");

				if (propertyNotVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='assetCategoryPropertiesPanel']/div/div/span",
					RuntimeVariables.replace("Properties"));
				selenium.waitForVisible("//input[@id='_147_key0']");

			case 2:
				selenium.type("//input[@id='_147_key0']",
					RuntimeVariables.replace("Category Property1 Key"));
				selenium.type("//input[@id='_147_value0']",
					RuntimeVariables.replace("Category Property1 Value"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForVisible(
					"//div[@class='lfr-message-response portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));
				selenium.waitForVisible("//li/div/div[4]");
				assertEquals(RuntimeVariables.replace("Category Name"),
					selenium.getText("//li/div/div[4]"));

			case 100:
				label = -1;
			}
		}
	}
}