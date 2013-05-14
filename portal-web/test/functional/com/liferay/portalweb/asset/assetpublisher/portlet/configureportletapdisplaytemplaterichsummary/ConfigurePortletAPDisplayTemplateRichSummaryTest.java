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

package com.liferay.portalweb.asset.assetpublisher.portlet.configureportletapdisplaytemplaterichsummary;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletAPDisplayTemplateRichSummaryTest
	extends BaseTestCase {
	public void testConfigurePortletAPDisplayTemplateRichSummary()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForElementPresent(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		selenium.waitForVisible(
			"//ul[contains(.,'Display Settings')]/li[2]/span/a");
		selenium.clickAt("//ul[contains(.,'Display Settings')]/li[2]/span/a",
			RuntimeVariables.replace("Display Settings"));
		selenium.waitForVisible(
			"//div[@class='display-template']/span/span/span/select");
		selenium.select("//div[@class='display-template']/span/span/span/select	",
			RuntimeVariables.replace("Rich Summary"));
		Thread.sleep(1000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']	"));
		selenium.selectFrame("relative=top");
	}
}