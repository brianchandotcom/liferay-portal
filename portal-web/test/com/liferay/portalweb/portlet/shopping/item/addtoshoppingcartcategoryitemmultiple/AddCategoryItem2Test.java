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

package com.liferay.portalweb.portlet.shopping.item.addtoshoppingcartcategoryitemmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCategoryItem2Test extends BaseTestCase {
	public void testAddCategoryItem2() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Shopping Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Shopping Category Name\nShopping Category Description"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace(
				"Shopping Category Name\nShopping Category Description"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Item']",
			RuntimeVariables.replace("Add Item"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_34_sku']", RuntimeVariables.replace("2222"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_34_name']",
			RuntimeVariables.replace("Shopping Category Item2 Name"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_34_description']",
			RuntimeVariables.replace("Shopping Category Item2 Description"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_34_properties']",
			RuntimeVariables.replace("Shopping Category Item2 Properties"));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isChecked(
				"//input[@id='_34_requiresShippingCheckbox']"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@id='_34_requiresShippingCheckbox']",
			RuntimeVariables.replace("Requires Shipping"));
		assertTrue(selenium.isChecked(
				"//input[@id='_34_requiresShippingCheckbox']"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_34_stockQuantity']",
			RuntimeVariables.replace("50"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_34_price0']",
			RuntimeVariables.replace("$9.99"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_34_minQuantity0']",
			RuntimeVariables.replace("1"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Shopping Category Item1 Name\nShopping Category Item1 Description\nShopping: Category Item1 Properties"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace(
				"Shopping Category Item2 Name\nShopping Category Item2 Description\nShopping: Category Item2 Properties"),
			selenium.getText("//tr[4]/td[2]/a"));
	}
}