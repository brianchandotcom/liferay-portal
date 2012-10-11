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

package com.liferay.portalweb.plugins.opensocial;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletOpenSocialGadgetTest extends BaseTestCase {
	public void testAddPortletOpenSocialGadget() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=OpenSocial Test Page");
		selenium.clickAt("link=OpenSocial Test Page",
			RuntimeVariables.replace("OpenSocial Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_145_addApplication']",
				"More"));
		selenium.clickAt("//a[@id='_145_addApplication']",
			RuntimeVariables.replace("More"));
		selenium.waitForElementPresent("//li[@title='Hello World!']/p/a");
		selenium.clickAt("//li[@title='Hello World!']/p/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForElementPresent("//header/h1/span");
		assertEquals(RuntimeVariables.replace("Hello World!"),
			selenium.getText("//header/h1/span"));
		Thread.sleep(5000);
		selenium.selectFrame("//iframe[@class='aui-gadget']");
		assertEquals(RuntimeVariables.replace("Hello, world!"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
	}
}