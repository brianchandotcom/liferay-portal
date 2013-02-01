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

package com.liferay.portalweb.plugins.sampleapplicationadapter.site.configuresitessampleapplicationadapterhook;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletSampleApplicationAdapterHookTest extends BaseTestCase {
	public void testViewPortletSampleApplicationAdapterHook()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/public-page");
		selenium.clickAt("link=Public Page",
			RuntimeVariables.replace("Public Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//section");
		assertTrue(selenium.isVisible("//section"));
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//span[@title='Go to Site Name']"));
		assertEquals(RuntimeVariables.replace("Navigation"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText(
				"//div[@class='portlet-body']/div/ul/li/span/a[contains(.,'Site Name')]"));
		assertEquals(RuntimeVariables.replace("Public Page"),
			selenium.getText(
				"//div[@class='portlet-body']/div/ul/li/span/a[contains(.,'Public Page')]"));
		assertEquals(RuntimeVariables.replace(
				"This was modified by the Sample Application Adapter."),
			selenium.getText("//div[@class='portlet-body']/p"));
	}
}