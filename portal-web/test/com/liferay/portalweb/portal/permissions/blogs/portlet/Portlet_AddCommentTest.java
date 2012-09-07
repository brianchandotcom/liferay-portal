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

package com.liferay.portalweb.portal.permissions.blogs.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Portlet_AddCommentTest extends BaseTestCase {
	public void testPortlet_AddComment() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Blogs Permissions Page");
				selenium.clickAt("link=Blogs Permissions Page",
					RuntimeVariables.replace("Blogs Permissions Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//div[@class='entry-title']/h2/a",
					RuntimeVariables.replace("Blogs Entry Title Temporary"));
				selenium.waitForPageToLoad("30000");

				boolean blogCommentsExpanded = selenium.isVisible(
						"//input[@class='form-text lfr-input-resource ']");

				if (blogCommentsExpanded) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-panel-title']/span[contains(text(),'Comments')]",
					RuntimeVariables.replace(""));

			case 2:
				selenium.waitForVisible("link=Be the first.");
				selenium.clickAt("link=Be the first.",
					RuntimeVariables.replace("Be the first."));
				selenium.waitForVisible(
					"//textarea[@name='_33_postReplyBody0']");
				selenium.waitForVisible("//input[@value='Reply']");
				selenium.type("//textarea[@name='_33_postReplyBody0']",
					RuntimeVariables.replace("Portlet Comment"));
				selenium.clickAt("//input[@value='Reply']",
					RuntimeVariables.replace("Reply"));
				selenium.waitForVisible(
					"//div[@class='lfr-message-response portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Portlet Comment"),
					selenium.getText("//div[@class='lfr-discussion-message']"));

			case 100:
				label = -1;
			}
		}
	}
}