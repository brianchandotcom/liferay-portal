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

package com.liferay.portalweb.portal.session;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfirmSessionExpireTest extends BaseTestCase {
	public void testConfirmSessionExpire() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Session Expiration Test Page",
			RuntimeVariables.replace("Session Expiration Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(60000);
		selenium.waitForVisible("//input[@value='Extend']");
		Thread.sleep(60000);
		selenium.waitForVisible("//div[@class='popup-alert-warning']");
		assertEquals(RuntimeVariables.replace(
				"Warning! Due to inactivity, your session has expired. Please save any data you may have entered before refreshing the page."),
			selenium.getText("//div[@class='popup-alert-warning']"));
		Thread.sleep(5000);
		selenium.clickAt("link=Session Expiration Test Page",
			RuntimeVariables.replace("Session Expiration Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("link=Sign In"));
	}
}