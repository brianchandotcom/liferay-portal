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

package com.liferay.portalweb.portal.controlpanel.categories.category.addcategoryproperty;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCategoryPropertyTest extends BaseTestCase {
	public void testViewCategoryProperty() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//li/div/div[4]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText("//li/div/div[4]"));
		selenium.clickAt("//li/div/div[4]",
			RuntimeVariables.replace("Category Name"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='view-category']/div/h1/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText("//div[@class='view-category']/div/h1/span"));
		assertEquals(RuntimeVariables.replace(
				"Description: Category Description"),
			selenium.getText("//div[@class='view-category']/div[2]"));
		assertEquals(RuntimeVariables.replace("Properties:"),
			selenium.getText("//div[@class='view-category']/div[3]/label"));
		assertEquals(RuntimeVariables.replace("Category Property Key"),
			selenium.getText("//span[@class='property-key']"));
		assertEquals(RuntimeVariables.replace("Category Property Value"),
			selenium.getText("//span[@class='property-value']"));
	}
}