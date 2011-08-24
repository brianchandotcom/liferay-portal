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

package com.liferay.portalweb.portlet.breadcrumb.portlet.viewportletbreadcrumb;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletBreadcrumbTest extends BaseTestCase {
	public void testViewPortletBreadcrumb() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Breadcrumb Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Breadcrumb Test Page",
			RuntimeVariables.replace("Breadcrumb Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText(
				"//div/ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Breadcrumb Test Page"),
			selenium.getText(
				"//div/ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li[2]/span/a"));
		assertFalse(selenium.isElementPresent(
				"//div[@class='portlet-body']/ul/li[3]"));
		selenium.clickAt("link=Child Test Page",
			RuntimeVariables.replace("Child Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText(
				"//div/ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Breadcrumb Test Page"),
			selenium.getText(
				"//div/ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Child Test Page"),
			selenium.getText(
				"//div/ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li[3]/span/a"));
	}
}