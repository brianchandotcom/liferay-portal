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

package com.liferay.portalweb.plugins.kaleo.assetpublisher.mbmessage.viewmessagecompleted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMessageCompletedGuestTest extends BaseTestCase {
	public void testViewMessageCompletedGuest() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Asset Publisher Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Asset Publisher Page",
			RuntimeVariables.replace("Asset Publisher Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//section"));
		assertEquals(RuntimeVariables.replace("Asset Publisher"),
			selenium.getText("//h1/span[2]"));
		assertEquals(RuntimeVariables.replace("Message Boards Message Subject"),
			selenium.getText("//h3/a"));
		assertEquals(RuntimeVariables.replace("Message Boards Message Body"),
			selenium.getText("//div[@class='asset-summary']"));
		assertTrue(selenium.isPartialText("//div[2]/a", "Read More"));
		assertFalse(selenium.isTextPresent("There are no results."));
		selenium.clickAt("//div[2]/a", RuntimeVariables.replace("Read More"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Message Boards Message Subject"),
			selenium.getText("//div[1]/h1/span"));
		assertTrue(selenium.isPartialText("//div/div/div/div[2]/div[1]",
				"Message Boards Message Body"));
	}
}