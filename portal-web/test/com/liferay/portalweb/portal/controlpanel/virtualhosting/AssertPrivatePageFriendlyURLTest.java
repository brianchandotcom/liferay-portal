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

package com.liferay.portalweb.portal.controlpanel.virtualhosting;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertPrivatePageFriendlyURLTest extends BaseTestCase {
	public void testAssertPrivatePageFriendlyURL() throws Exception {
		selenium.open("/group/alpha/");
		assertEquals(RuntimeVariables.replace(
				"http://localhost:8080/group/alpha/"), selenium.getLocation());
		assertEquals(RuntimeVariables.replace("Virtual Hosting Community"),
			selenium.getText("//li[2]/span/a"));
		assertTrue(selenium.isElementPresent("link=Private Page"));
		selenium.clickAt("link=Private Page",
			RuntimeVariables.replace("Private Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"http://localhost:8080/group/alpha/private-page"),
			selenium.getLocation());
	}
}