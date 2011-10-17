/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.properties.mailintegration.webcontent.wcwebcontent.gmailvieweditwcwebcontentupdatedemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureServerAdministrationMailTest extends BaseTestCase {
	public void testConfigureServerAdministrationMail()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Server Administration",
			RuntimeVariables.replace("Server Administration"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Mail", RuntimeVariables.replace("Mail"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_137_pop3Host']",
			RuntimeVariables.replace("pop.gmail.com"));
		selenium.type("//input[@id='_137_pop3Port']",
			RuntimeVariables.replace("995"));
		assertFalse(selenium.isChecked("//input[@id='_137_pop3SecureCheckbox']"));
		selenium.clickAt("//input[@id='_137_pop3SecureCheckbox']",
			RuntimeVariables.replace("Use a Secure Network Connection"));
		assertTrue(selenium.isChecked("//input[@id='_137_pop3SecureCheckbox']"));
		selenium.type("//input[@id='_137_pop3User']",
			RuntimeVariables.replace("liferay.qa.server.trunk@gmail.com"));
		selenium.type("//input[@id='_137_pop3Password']",
			RuntimeVariables.replace("loveispatient"));
		selenium.type("//input[@id='_137_smtpHost']",
			RuntimeVariables.replace("smtp.gmail.com"));
		selenium.type("//input[@id='_137_smtpPort']",
			RuntimeVariables.replace("465"));
		assertFalse(selenium.isChecked("//input[@id='_137_smtpSecureCheckbox']"));
		selenium.click("//input[@id='_137_smtpSecureCheckbox']");
		assertTrue(selenium.isChecked("//input[@id='_137_smtpSecureCheckbox']"));
		selenium.type("//input[@id='_137_smtpUser']",
			RuntimeVariables.replace("liferay.qa.server.trunk@gmail.com"));
		selenium.type("//input[@id='_137_smtpPassword']",
			RuntimeVariables.replace("loveispatient"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("pop.gmail.com",
			selenium.getValue("//input[@id='_137_pop3Host']"));
		assertEquals("995", selenium.getValue("//input[@id='_137_pop3Port']"));
		assertTrue(selenium.isChecked("//input[@id='_137_pop3SecureCheckbox']"));
		assertEquals("liferay.qa.server.trunk@gmail.com",
			selenium.getValue("//input[@id='_137_pop3User']"));
		assertEquals("loveispatient",
			selenium.getValue("//input[@id='_137_pop3Password']"));
		assertEquals("smtp.gmail.com",
			selenium.getValue("//input[@id='_137_smtpHost']"));
		assertEquals("465", selenium.getValue("//input[@id='_137_smtpPort']"));
		assertTrue(selenium.isChecked("//input[@id='_137_smtpSecureCheckbox']"));
		assertEquals("liferay.qa.server.trunk@gmail.com",
			selenium.getValue("//input[@id='_137_smtpUser']"));
		assertEquals("loveispatient",
			selenium.getValue("//input[@id='_137_smtpPassword']"));
	}
}