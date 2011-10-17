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

package com.liferay.portalweb.portlet.messageboards.message.previewmbthreadmessagedetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PreviewMBThreadMessageDetailsTest extends BaseTestCase {
	public void testPreviewMBThreadMessageDetails() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Message Boards Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Post New Thread']",
			RuntimeVariables.replace("Post New Thread"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_subject']",
			RuntimeVariables.replace("MB Thread Message Subject"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//td[@id='cke_contents__19_editor']/iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//td[@id='cke_contents__19_editor']/iframe");
		selenium.type("//body",
			RuntimeVariables.replace("MB Thread Message Body"));
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Preview']",
			RuntimeVariables.replace("Preview"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Thread Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("MB Thread Message Subject"),
			selenium.getText("//div[@class='subject']/a/strong"));
		assertEquals(RuntimeVariables.replace("MB Thread Message Body"),
			selenium.getText("//div[@class='thread-body']"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Message Boards Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("MB Thread Message Subject"));
		selenium.clickAt("link=My Posts", RuntimeVariables.replace("My Posts"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("MB Thread Message Subject"));
	}
}