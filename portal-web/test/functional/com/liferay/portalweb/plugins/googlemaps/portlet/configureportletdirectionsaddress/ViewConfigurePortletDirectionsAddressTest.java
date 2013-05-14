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

package com.liferay.portalweb.plugins.googlemaps.portlet.configureportletdirectionsaddress;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletDirectionsAddressTest extends BaseTestCase {
	public void testViewConfigurePortletDirectionsAddress()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Google Maps Test Page",
			RuntimeVariables.replace("Google Maps Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace(
				"From 1220 Brea Canyon Road, Diamond Bar, CA, 91789"),
			selenium.getText("xPath=(//div[@class='field-wrapper-content'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"To 205 W. Wacker Dr, Suite 513 Chicago, IL, 60606"),
			selenium.getText("xPath=(//div[@class='field-wrapper-content'])[2]"));
	}
}