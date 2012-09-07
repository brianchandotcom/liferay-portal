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

package com.liferay.portalweb.socialofficehome.events.event.vieweventmultipleed;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPageCalendarSOTest extends BaseTestCase {
	public void testTearDownPageCalendarSO() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				selenium.click("//div[@id='dockbar']");
				selenium.waitForVisible("//li[@id='_145_toggleControls']");
				selenium.clickAt("//li[@id='_145_toggleControls']",
					RuntimeVariables.replace("Edit Controls"));

				boolean EditControlOff = selenium.isElementPresent(
						"//body[@class='normal yui3-skin-sam signed-in private-page user-site user-group dockbar-ready controls-hidden']");

				if (!EditControlOff) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Edit Controls"),
					selenium.getText("//li[@id='_145_toggleControls']"));
				selenium.clickAt("//li[@id='_145_toggleControls']",
					RuntimeVariables.replace("Edit Controls"));

			case 2:
				selenium.clickAt("//nav[@id='navigation']",
					RuntimeVariables.replace("Navigation"));
				selenium.waitForVisible(
					"//nav/ul/li[contains(.,'Calendar Test Page')]/a/span");

				boolean calendarPagePresent = selenium.isElementPresent(
						"//nav/ul/li[contains(.,'Calendar Test Page')]/a/span");

				if (!calendarPagePresent) {
					label = 3;

					continue;
				}

				selenium.mouseOver(
					"//nav/ul/li[contains(.,'Calendar Test Page')]/a/span");
				selenium.waitForVisible(
					"//nav/ul/li[contains(.,'Calendar Test Page')]/span[@class='delete-tab']");
				assertEquals(RuntimeVariables.replace("X"),
					selenium.getText(
						"//nav/ul/li[contains(.,'Calendar Test Page')]/span[@class='delete-tab']"));
				selenium.click(
					"//nav/ul/li[contains(.,'Calendar Test Page')]/span[@class='delete-tab']");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this page?");
				selenium.waitForElementNotPresent(
					"//nav/ul/li[contains(.,'Calendar Test Page')]/span[@class='delete-tab']");

			case 3:
				assertEquals(RuntimeVariables.replace("Edit Controls"),
					selenium.getText("//li[@id='_145_toggleControls']"));
				selenium.clickAt("//li[@id='_145_toggleControls']",
					RuntimeVariables.replace("Edit Controls"));

			case 100:
				label = -1;
			}
		}
	}
}