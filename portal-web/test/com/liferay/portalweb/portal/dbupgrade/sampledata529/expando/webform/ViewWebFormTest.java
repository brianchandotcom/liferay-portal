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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.expando.webform;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebFormTest extends BaseTestCase {
	public void testViewWebForm() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/expando-web-form-community/");
		selenium.clickAt("link=Web Form Page",
			RuntimeVariables.replace("Web Form Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/span"));
		selenium.clickAt("//strong/span", RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Form Fields",
			RuntimeVariables.replace("Form Fields"));
		selenium.waitForVisible("//div[@class='portlet-msg-alert']");
		assertEquals(RuntimeVariables.replace(
				"There is existing form data. Please export and delete it before making changes to the fields."),
			selenium.getText("//div[@class='portlet-msg-alert']"));
		assertTrue(selenium.isVisible("//input[@value='Export Data']"));
		assertTrue(selenium.isVisible("//input[@value='Delete Data']"));
		assertTrue(selenium.isElementNotPresent("link=Add Row"));
	}
}