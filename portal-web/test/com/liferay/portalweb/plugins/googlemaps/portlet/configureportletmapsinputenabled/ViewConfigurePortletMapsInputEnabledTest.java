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

package com.liferay.portalweb.plugins.googlemaps.portlet.configureportletmapsinputenabled;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletMapsInputEnabledTest extends BaseTestCase {
	public void testViewConfigurePortletMapsInputEnabled()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Google Maps Test Page",
			RuntimeVariables.replace("Google Maps Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		Thread.sleep(5000);
		assertEquals("1220 Brea Canyon Road, Diamond Bar, CA, 91789",
			selenium.getValue("//input[contains(@name,'_mapAddress')]"));
		assertTrue(selenium.isVisible("//input[@value='Get Map']"));
		assertEquals(RuntimeVariables.replace(
				"1220 Brea Canyon Road, Diamond Bar, CA, 91789"),
			selenium.getText("//div[@style='overflow: auto;']"));
	}
}