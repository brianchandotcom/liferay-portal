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

package com.liferay.portalweb.plugins.googlemaps.portlet.configureportletdirectionsinputenabled;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletDirectionsInputEnabledTest extends BaseTestCase {
	public void testConfigurePortletDirectionsInputEnabled()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.click(RuntimeVariables.replace(
						"link=Google Maps Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Google Maps"),
					selenium.getText("//span[@class='portlet-title-text']"));
				selenium.clickAt("//span[@class='portlet-title-text']",
					RuntimeVariables.replace("Google Maps"));
				selenium.waitForElementPresent("//div[@class='yui3-dd-shim']");
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
				selenium.click(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
				selenium.waitForVisible(
					"//iframe[contains(@id,'_1_WAR_googlemapsportlet_INSTANCE')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'_1_WAR_googlemapsportlet_INSTANCE')]");
				selenium.waitForVisible(
					"//input[@id='_86_directionsInputEnabledCheckbox']");

				boolean allowDirectionsAddressToBeEdited = selenium.isChecked(
						"//input[@id='_86_directionsInputEnabledCheckbox']");

				if (allowDirectionsAddressToBeEdited) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_directionsInputEnabledCheckbox']",
					RuntimeVariables.replace("Directions Input Enabled"));
				Thread.sleep(5000);

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_86_directionsInputEnabledCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForText("//div[@class='portlet-msg-success']",
					"You have successfully updated the setup.");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='_86_directionsInputEnabledCheckbox']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}