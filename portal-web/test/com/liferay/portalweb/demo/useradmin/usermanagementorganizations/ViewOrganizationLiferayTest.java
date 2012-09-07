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

package com.liferay.portalweb.demo.useradmin.usermanagementorganizations;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewOrganizationLiferayTest extends BaseTestCase {
	public void testViewOrganizationLiferay() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Liferay, Inc."),
			selenium.getText("//a[2]/strong"));
		selenium.clickAt("//a[2]/strong",
			RuntimeVariables.replace("Liferay, Inc."));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Liferay Chicago"),
			selenium.getText("//a[2]/strong"));
		selenium.clickAt("//a[2]/strong",
			RuntimeVariables.replace("Liferay Chicago"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("selen01"),
			selenium.getText("//td[2]/a[.='selen01']"));
		assertEquals(RuntimeVariables.replace("nium01"),
			selenium.getText("//td[3]/a[.='nium01']"));
		assertEquals(RuntimeVariables.replace("selenium01"),
			selenium.getText("//td[4]/a[.='selenium01']"));
	}
}