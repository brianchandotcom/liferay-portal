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

package com.liferay.portalweb.socialofficeprofile.profile.souseditsmsprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_EditSMSProfileTest extends BaseTestCase {
	public void testSOUs_EditSMSProfile() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("SMS:"),
			selenium.getText("//div[@data-title='SMS']/h3"));
		assertEquals(RuntimeVariables.replace("socialoffice01@liferay.com"),
			selenium.getText("//div[@data-title='SMS']/ul/li"));
		selenium.clickAt("//div[@data-title='SMS']",
			RuntimeVariables.replace("SMS:"));
		selenium.waitForVisible("//input[contains(@id,'smsSn')]");
		selenium.type("//input[contains(@id,'smsSn')]",
			RuntimeVariables.replace("socialoffice01edit@liferay.com"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//div[@data-title='SMS']/ul/li",
			"socialoffice01edit@liferay.com");
		assertEquals(RuntimeVariables.replace("SMS:"),
			selenium.getText("//div[@data-title='SMS']/h3"));
		assertEquals(RuntimeVariables.replace("socialoffice01edit@liferay.com"),
			selenium.getText("//div[@data-title='SMS']/ul/li"));
	}
}