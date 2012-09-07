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
public class SOUs_EditIntroductionProfileTest extends BaseTestCase {
	public void testSOUs_EditIntroductionProfile() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("Introduction:"),
			selenium.getText("//div[@data-title='Introduction']/h3"));
		assertEquals(RuntimeVariables.replace("Introduction Content"),
			selenium.getText("//div[@data-title='Introduction']/ul/li/span"));
		selenium.click("//div[@data-title='Introduction']");
		selenium.waitForVisible("//textarea[contains(@id,'comments')]");
		selenium.type("//textarea[contains(@id,'comments')]",
			RuntimeVariables.replace("Introduction Content Edit"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//div[@data-title='Introduction']/ul/li/span",
			"Introduction Content Edit");
		assertEquals(RuntimeVariables.replace("Introduction:"),
			selenium.getText("//div[@data-title='Introduction']/h3"));
		assertEquals(RuntimeVariables.replace("Introduction Content Edit"),
			selenium.getText("//div[@data-title='Introduction']/ul/li/span"));
	}
}