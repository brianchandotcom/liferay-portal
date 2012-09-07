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

package com.liferay.portalweb.socialofficeprofile.profile.souseditaddressnullprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_EditAddressNullProfileTest extends BaseTestCase {
	public void testSOUs_EditAddressNullProfile() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		selenium.waitForVisible("//div[@data-title='Addresses']/h3");
		assertEquals(RuntimeVariables.replace("Addresses:"),
			selenium.getText("//div[@data-title='Addresses']/h3"));
		assertEquals(RuntimeVariables.replace(
				"Personal 123 Liferay Ln., Ray of Light, 91234, California, United States (Mailing)"),
			selenium.getText("//div[@data-title='Addresses']/ul/li"));
		selenium.clickAt("//div[@data-title='Addresses']",
			RuntimeVariables.replace("Addresses:"));
		selenium.waitForVisible("//input[contains(@id,'addressStreet1')]");
		Thread.sleep(5000);
		selenium.type("//input[contains(@id,'addressStreet1')]",
			RuntimeVariables.replace(""));
		selenium.type("//input[contains(@id,'addressZip')]",
			RuntimeVariables.replace(""));
		selenium.type("//input[contains(@id,'addressCity')]",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//div[@data-title='Addresses']/ul/li");
		selenium.waitForVisible("//li[@data-title='Addresses']");
		assertEquals(RuntimeVariables.replace("Addresses"),
			selenium.getText("//li[@data-title='Addresses']"));
		assertFalse(selenium.isTextPresent("Addresses:"));
		assertFalse(selenium.isTextPresent(
				"Personal 123 Liferay Ln., Ray of Light, 91234, California, United States (Mailing)"));
	}
}