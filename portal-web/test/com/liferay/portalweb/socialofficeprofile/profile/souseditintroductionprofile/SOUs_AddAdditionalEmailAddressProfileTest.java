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

package com.liferay.portalweb.socialofficeprofile.profile.souseditintroductionprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_AddAdditionalEmailAddressProfileTest extends BaseTestCase {
	public void testSOUs_AddAdditionalEmailAddressProfile()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace(
				"To complete your profile, please add:"),
			selenium.getText("//p[@class='portlet-msg portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Email Address"),
			selenium.getText("//li[@data-title='Additional Email Addresses']"));
		selenium.clickAt("//li[@data-title='Additional Email Addresses']",
			RuntimeVariables.replace("Email Address"));
		selenium.waitForVisible(
			"//span[contains(.,'Email Address')]/span/input");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/auto_fields.js')]");
		selenium.type("//span[contains(.,'Email Address')]/span/input",
			RuntimeVariables.replace("socialoffice02@liferay.com"));
		selenium.clickAt("//input[contains(@id,'emailAddressPrimary')]",
			RuntimeVariables.replace("Primary"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible(
			"//div[@data-title='Additional Email Addresses']/h3");
		assertEquals(RuntimeVariables.replace("Additional Email Addresses:"),
			selenium.getText(
				"//div[@data-title='Additional Email Addresses']/h3"));
		assertEquals(RuntimeVariables.replace("Email Address"),
			selenium.getText(
				"//div[@data-title='Additional Email Addresses']/ul/li/span"));
		assertEquals(RuntimeVariables.replace("socialoffice02@liferay.com"),
			selenium.getText(
				"//div[@data-title='Additional Email Addresses']/ul/li/span[2]/a"));
	}
}