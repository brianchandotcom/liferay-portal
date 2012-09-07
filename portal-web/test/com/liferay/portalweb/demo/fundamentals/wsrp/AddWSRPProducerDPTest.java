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

package com.liferay.portalweb.demo.fundamentals.wsrp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWSRPProducerDPTest extends BaseTestCase {
	public void testAddWSRPProducerDP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=WSRP", RuntimeVariables.replace("WSRP"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Producers", RuntimeVariables.replace("Producers"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Producer']",
			RuntimeVariables.replace("Add Producer"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_1_WAR_wsrpportlet_name']",
			RuntimeVariables.replace("Producer"));
		selenium.addSelection("//select[@id='_1_WAR_wsrpportlet_availablePortletIds']",
			RuntimeVariables.replace("Test Misc"));
		selenium.clickAt("//button[@title='Move selected items from Available to Current.']",
			RuntimeVariables.replace("Move Left Arrow"));
		selenium.waitForText("//select[@id='_1_WAR_wsrpportlet_currentPortletIds']",
			"Test Misc");
		assertEquals(RuntimeVariables.replace("Test Misc"),
			selenium.getText(
				"//select[@id='_1_WAR_wsrpportlet_currentPortletIds']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Producer"),
			selenium.getText("//td[1]/a"));
	}
}