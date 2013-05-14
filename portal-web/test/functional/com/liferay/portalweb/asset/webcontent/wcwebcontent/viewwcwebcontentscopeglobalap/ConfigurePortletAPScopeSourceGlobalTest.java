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

package com.liferay.portalweb.asset.webcontent.wcwebcontent.viewwcwebcontentscopeglobalap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletAPScopeSourceGlobalTest extends BaseTestCase {
	public void testConfigurePortletAPScopeSourceGlobal()
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
		selenium.waitForVisible(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//span[@title='Select']/ul/li/strong/a/span");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//span[@title='Select']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Select']/ul/li/strong/a/span",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Global')]");
		assertEquals(RuntimeVariables.replace("Global"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Global')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Global')]",
			RuntimeVariables.replace("Global"));
		selenium.waitForVisible("//tr[contains(.,'Global')]/td[1]/span/span");
		assertEquals(RuntimeVariables.replace("Global"),
			selenium.getText("//tr[contains(.,'Global')]/td[1]/span/span"));
		assertEquals(RuntimeVariables.replace("Global"),
			selenium.getText("//tr[contains(.,'Global')]/td[2]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Global')]/td[3]/span/a[contains(@href,'remove-scope')]"));
		Thread.sleep(1000);
		selenium.clickAt("//tr[contains(.,'Current Site (Liferay)')]/td[3]/span/a[contains(@href,'remove-scope')]",
			RuntimeVariables.replace("Remove Current Site (Liferay) Scope"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForTextNotPresent("Current Site (Liferay) Scope");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Global"),
			selenium.getText("//tr[contains(.,'Global')]/td[1]/span/span"));
		assertEquals(RuntimeVariables.replace("Global"),
			selenium.getText("//tr[contains(.,'Global')]/td[2]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Global')]/td[3]/span/a[contains(@href,'remove-scope')]"));
		assertFalse(selenium.isTextPresent("Current Site (Liferay) Scope"));
	}
}