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

package com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addsitepublicpageschildpageportlets;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletsPage2SiteTest extends BaseTestCase {
	public void testViewPortletsPage2Site() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/public-page2");
		selenium.waitForVisible("xpath=(//section)[1]");
		assertTrue(selenium.isVisible("xpath=(//section)[1]"));
		assertEquals(RuntimeVariables.replace("Asset Publisher"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[1]"));
		assertTrue(selenium.isVisible("xpath=(//section)[2]"));
		assertEquals(RuntimeVariables.replace("Language"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[2]"));
		assertTrue(selenium.isVisible("xpath=(//section)[3]"));
		assertEquals(RuntimeVariables.replace("Web Content Display"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[3]"));
	}
}