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

package com.liferay.portalweb.portlet.shopping.item.deletecategoryitem;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDeleteCategoryItemTest extends BaseTestCase {
	public void testViewDeleteCategoryItem() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[1]/a",
				"Shopping Category Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[1]/a",
				"Shopping Category Description"));
		selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td[1]/a",
			RuntimeVariables.replace(
				"Shopping Category Name Shopping Category Description"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Categories"),
			selenium.getText("//div[@id='shoppingCategoriesPanel']/div[1]/div"));
		assertEquals("Add Subcategory",
			selenium.getValue(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[1]/span[1]/span/input"));
		assertEquals("Permissions",
			selenium.getValue(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[1]/span[2]/span/input"));
		assertEquals(RuntimeVariables.replace("Items"),
			selenium.getText("//div[@id='shoppingItemsPanel']/div[1]/div/span"));
		assertEquals("Add Item",
			selenium.getValue(
				"//div[@id='shoppingItemsPanel']/div[2]/div/span/span/input"));
		assertFalse(selenium.isTextPresent("Shopping Category Item Name"));
		assertFalse(selenium.isTextPresent("Shopping Category Item Description"));
		assertFalse(selenium.isTextPresent("Shopping: Category Item Properties"));
	}
}