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

package com.liferay.portalweb.kaleo.assetpublisher.wcwebcontent.viewwebcontentassignedtome;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAPWebContentTest extends BaseTestCase {
	public void testAddAPWebContent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Add New"),
			selenium.getText("//span[@title='Add New']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Add New']/ul/li/strong/a",
			RuntimeVariables.replace("Add New"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Web Content')]");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Web Content')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Web Content')]",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/lang/en.js')]");
		selenium.waitForVisible("//input[@id='_15_title_en_US']");
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("Web Content Name"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.typeFrame("//div[@id='cke_1_contents']/iframe",
			RuntimeVariables.replace("Web Content Content"));
		selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/lang/en.js')]");
		selenium.waitForVisible("//input[@value='Submit for Publication']");
		selenium.clickAt("//input[@value='Submit for Publication']",
			RuntimeVariables.replace("Submit for Publication"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//section");
		assertEquals(RuntimeVariables.replace("There are no results."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}