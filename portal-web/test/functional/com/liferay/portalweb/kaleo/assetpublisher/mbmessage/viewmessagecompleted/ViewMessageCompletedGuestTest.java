/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.kaleo.assetpublisher.mbmessage.viewmessagecompleted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMessageCompletedGuestTest extends BaseTestCase {
	public void testViewMessageCompletedGuest() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//section"));
		assertEquals(RuntimeVariables.replace("Asset Publisher"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Message Boards Message Subject"),
			selenium.getText("//h3[@class='asset-title']"));
		assertEquals(RuntimeVariables.replace("Message Boards Message Body"),
			selenium.getText("//div[@class='asset-summary']"));
		assertTrue(selenium.isPartialText("//div[@class='asset-more']/a",
				"Read More"));
		assertFalse(selenium.isTextPresent("There are no results."));
		selenium.clickAt("//div[@class='asset-more']/a",
			RuntimeVariables.replace("Read More"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Message Boards Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Message Boards Message Body"),
			selenium.getText("//div[@class='thread-body']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
	}
}