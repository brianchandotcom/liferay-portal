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

package com.liferay.portalweb.portal.tags.tagsadmin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPropertiesTest extends BaseTestCase {
	public void testAddProperties() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Tags", RuntimeVariables.replace("Tags"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//h1[@class='header-title']/span");
				assertEquals(RuntimeVariables.replace("selenium ide"),
					selenium.getText("//h1[@class='header-title']/span"));
				selenium.clickAt("//input[@id='editTagButton']",
					RuntimeVariables.replace("Edit"));
				Thread.sleep(5000);
				selenium.waitForVisible("//input[@id='_99_name']");

				boolean propertiesVisible = selenium.isVisible(
						"//input[@id='_99_key0']");

				if (propertiesVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div/div/div/div/span",
					RuntimeVariables.replace("Properties"));
				selenium.waitForVisible("//input[@id='_99_key0']");
				assertTrue(selenium.isVisible("//input[@id='_99_key0']"));

			case 2:
				selenium.type("//input[@id='_99_key0']",
					RuntimeVariables.replace("This is a tag for anything"));
				selenium.type("//input[@id='_99_value0']",
					RuntimeVariables.replace("related to selenium."));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.clickAt("link=Tags", RuntimeVariables.replace("Tags"));
				selenium.waitForText("//span[@class='property-key']",
					"This is a tag for anything");
				assertEquals(RuntimeVariables.replace(
						"This is a tag for anything"),
					selenium.getText("//span[@class='property-key']"));
				assertEquals(RuntimeVariables.replace("related to selenium."),
					selenium.getText("//span[@class='property-value']"));

			case 100:
				label = -1;
			}
		}
	}
}