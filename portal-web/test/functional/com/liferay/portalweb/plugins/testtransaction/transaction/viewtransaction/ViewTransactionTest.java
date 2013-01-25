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

package com.liferay.portalweb.plugins.testtransaction.transaction.viewtransaction;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewTransactionTest extends BaseTestCase {
	public void testViewTransaction() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Test Transaction Test Page",
			RuntimeVariables.replace("Test Transaction Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//p[1]", "BarLocalServiceUtil"));
		assertTrue(selenium.isPartialText("//p[1]", "addBar_Success=PASSED"));
		assertTrue(selenium.isPartialText("//p[1]",
				"addBarAndClassName_PortalRollback=PASSED"));
		assertTrue(selenium.isPartialText("//p[1]",
				"addBarAndClassName_PortletRollback=PASSED"));
		assertTrue(selenium.isPartialText("//p[2]", "PortalServiceUtil"));
		assertTrue(selenium.isPartialText("//p[2]",
				"testAddClassNameAndTestTransactionPortletBar_Success=PASSED"));
		assertTrue(selenium.isPartialText("//p[2]",
				"testAddClassNameAndTestTransactionPortletBar_PortalRollback=PASSED"));
		assertTrue(selenium.isPartialText("//p[2]",
				"testAddClassNameAndTestTransactionPortletBar_PortletRollback=PASSED"));
	}
}