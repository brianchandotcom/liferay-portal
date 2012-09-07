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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.gmailviewpmmessage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditPortalInstanceMailDomainTest extends BaseTestCase {
	public void testEditPortalInstanceMailDomain() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Portal Instances",
			RuntimeVariables.replace("Portal Instances"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("liferay.com"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("localhost"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("liferay.com"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isElementPresent("//td[5]/a"));
		selenium.clickAt("//td[4]/a", RuntimeVariables.replace("liferay.com"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_135_mx']",
			RuntimeVariables.replace("gmail.com"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("liferay.com"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("localhost"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("gmail.com"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isElementPresent("//td[5]/a"));
	}
}