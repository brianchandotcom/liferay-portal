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

package com.liferay.portalweb.demo.media.dmstagingdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PublishToLiveNowPageDMMusicTest extends BaseTestCase {
	public void testPublishToLiveNowPageDMMusic() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no documents or media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@data-title='DM Music Title']/a/span[@class='entry-title']"));
		selenium.clickAt("link=Staging", RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Staging Arrow"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Publish to Live Now')]");
		assertEquals(RuntimeVariables.replace("Publish to Live Now"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Publish to Live Now')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Publish to Live Now')]",
			RuntimeVariables.replace("Publish to Live Now"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@value='Publish']");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));
	}
}