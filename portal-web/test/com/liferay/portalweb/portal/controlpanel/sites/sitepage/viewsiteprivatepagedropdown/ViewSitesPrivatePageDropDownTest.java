/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.sites.sitepage.viewsiteprivatepagedropdown;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSitesPrivatePageDropDownTest extends BaseTestCase {
	public void testViewSitesPrivatePageDropDown() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Community Name")
										.equals(selenium.getText(
								"//li[6]/a/span"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Community Name"),
			selenium.getText("//li[6]/a/span"));
		selenium.clickAt("//li[6]/a/span",
			RuntimeVariables.replace("Community Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Private Page"),
			selenium.getText("//nav/ul/li/a/span"));
		assertEquals(RuntimeVariables.replace("Private Page"),
			selenium.getText("//nav/ul/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace(
				"http://localhost:8080/group/community-name/private-page"),
			selenium.getLocation());
	}
}