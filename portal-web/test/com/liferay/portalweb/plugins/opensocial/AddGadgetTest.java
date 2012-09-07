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
public class AddGadgetTest extends BaseTestCase {
	public void testAddGadget() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.click(RuntimeVariables.replace("link=Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=OpenSocial Gadget Publisher");
		selenium.clickAt("link=OpenSocial Gadget Publisher",
			RuntimeVariables.replace("OpenSocial Gadget Publisher"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("//input[@value='Publish Gadget']");
		selenium.clickAt("//input[@value='Publish Gadget']",
			RuntimeVariables.replace("Publish Gadget"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//input[@id='_1_WAR_opensocialportlet_url']");
		selenium.type("//input[@id='_1_WAR_opensocialportlet_url']",
			RuntimeVariables.replace(
				"http://opensocial-resources.googlecode.com/svn/samples/tutorial/tags/api-0.8/helloworld.xml"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Hello World!"),
			selenium.getText("//tr[3]/td"));
		assertEquals(RuntimeVariables.replace(
				"http://opensocial-resources.googlecode.com/svn/samples/tutorial/tags/api-0.8/helloworld.xml"),
			selenium.getText("//td[2]/a"));
	}
}