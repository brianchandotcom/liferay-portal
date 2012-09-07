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

package com.liferay.portalweb.portal.dbupgrade.sampledata6010.social.relation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SRl_AddPortletRequestsTest extends BaseTestCase {
	public void testSRl_AddPortletRequests() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialrelationsn1/home/");
		selenium.waitForVisible("link=Requests Test Page");
		selenium.click(RuntimeVariables.replace("link=Requests Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("More\u2026"),
			selenium.getText("//a[@id='_145_addApplication']"));
		selenium.clickAt("//a[@id='_145_addApplication']",
			RuntimeVariables.replace("More\u2026"));
		selenium.waitForElementPresent("//div[@title='Requests']/p/a");
		selenium.clickAt("//div[@title='Requests']/p/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//div/div/div[1]/span[1]");
		assertTrue(selenium.isVisible("//div/div/div[1]/span[1]"));
	}
}