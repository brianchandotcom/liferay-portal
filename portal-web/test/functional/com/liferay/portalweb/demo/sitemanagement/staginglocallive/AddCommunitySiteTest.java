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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCommunitySiteTest extends BaseTestCase {
	public void testAddCommunitySite() throws Exception {
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
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Community Site"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a",
					RuntimeVariables.replace("Community Site"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace("Site Name"));
				selenium.type("//textarea[@id='_134_description']",
					RuntimeVariables.replace("Site Description"));

				boolean enablePropagationChecked = selenium.isChecked(
						"_134_layoutSetPrototypeLinkEnabledCheckbox");

				if (!enablePropagationChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_134_layoutSetPrototypeLinkEnabledCheckbox']",
					RuntimeVariables.replace(
						"Enable propagation of changes from the site template."));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='_134_layoutSetPrototypeLinkEnabledCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}