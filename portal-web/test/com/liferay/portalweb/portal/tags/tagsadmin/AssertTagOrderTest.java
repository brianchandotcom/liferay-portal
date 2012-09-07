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

package com.liferay.portalweb.portal.tags.tagsadmin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertTagOrderTest extends BaseTestCase {
	public void testAssertTagOrder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Tags", RuntimeVariables.replace("Tags"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("//div[2]/ul/li[1]/div/span/a");
		assertEquals(RuntimeVariables.replace("blue"),
			selenium.getText("//div[2]/ul/li[1]/div/span/a"));
		assertEquals(RuntimeVariables.replace("blue car"),
			selenium.getText("//div[2]/ul/li[2]/div/span/a"));
		assertEquals(RuntimeVariables.replace("blue green"),
			selenium.getText("//div[2]/ul/li[3]/div/span/a"));
		assertEquals(RuntimeVariables.replace("green"),
			selenium.getText("//div[2]/ul/li[4]/div/span/a"));
		assertEquals(RuntimeVariables.replace("green tree"),
			selenium.getText("//div[2]/ul/li[5]/div/span/a"));
	}
}