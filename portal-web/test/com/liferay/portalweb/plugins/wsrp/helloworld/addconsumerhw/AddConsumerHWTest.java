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

package com.liferay.portalweb.plugins.wsrp.helloworld.addconsumerhw;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddConsumerHWTest extends BaseTestCase {
	public void testAddConsumerHW() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=WSRP", RuntimeVariables.replace("WSRP"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Producers", RuntimeVariables.replace("Producers"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("Hello World Producer Name"));
		selenium.waitForPageToLoad("30000");

		String wsrpUrl = selenium.getText("//td[2]/a");
		RuntimeVariables.setValue("wsrpUrl", wsrpUrl);
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=WSRP", RuntimeVariables.replace("WSRP"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Consumers", RuntimeVariables.replace("Consumers"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Consumer']",
			RuntimeVariables.replace("Add Consumer"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_1_WAR_wsrpportlet_name']",
			RuntimeVariables.replace("Hello World Consumer Name"));
		selenium.type("//input[@id='_1_WAR_wsrpportlet_url']",
			RuntimeVariables.replace(RuntimeVariables.getValue("wsrpUrl")));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Hello World Consumer Name"),
			selenium.getText("//td[1]/a"));
	}
}