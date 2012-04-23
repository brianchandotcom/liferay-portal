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

package com.liferay.portalweb.portal.dbupgrade.sampledata610.groups.pagelayout;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DragAndDropPortletTest extends BaseTestCase {
	public void testDragAndDropPortlet() throws Exception {
		selenium.open("/web/group-page-layout-community/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Page Layout Page",
			RuntimeVariables.replace("Page Layout Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//div[@id='main-content']",
			RuntimeVariables.replace("Main Content"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//script[contains(@src,'/liferay/layout_column.js')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//nav[@id='navigation']",
			RuntimeVariables.replace("Navigation"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//div[@id='navigation_shim']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.dragAndDropToObject("xPath=(//h1/span[2])[2]",
			"//div[@id='column-2']");
		assertEquals(RuntimeVariables.replace("Breadcrumb"),
			selenium.getText(
				"//div[@id='layout-column_column-1' and @class='portlet-dropzone portlet-column-content portlet-column-content-first']/div[1]/div[1]/section/header/h1/span[2]"));
		assertEquals(RuntimeVariables.replace("Navigation"),
			selenium.getText(
				"//div[@id='layout-column_column-2' and @class='portlet-dropzone portlet-column-content portlet-column-content-last']/div[1]/div[1]/section/header/h1/span[2]"));
	}
}