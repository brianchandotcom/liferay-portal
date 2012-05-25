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

package com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletfilterdisplaypage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPageAP2PortletWCDContent2Test extends BaseTestCase {
	public void testViewPageAP2PortletWCDContent2() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Asset Publisher Test Page 2",
			RuntimeVariables.replace("Asset Publisher Test Page 2"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("WC WebContent Content2"),
			selenium.getText("//p"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title2"),
			selenium.getText("//h3/a"));
		assertEquals(RuntimeVariables.replace("Asset Publisher Test Page"),
			selenium.getText("//nav/ul/li[2]/a/span"));
		selenium.clickAt("//nav/ul/li[2]/a/span",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h3/a"));
		assertFalse(selenium.isTextPresent("WC WebContent Content2"));
	}
}