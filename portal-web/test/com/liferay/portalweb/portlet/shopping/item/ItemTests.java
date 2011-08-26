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

package com.liferay.portalweb.portlet.shopping.item;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitem.AddCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitemmultiple.AddCategoryItemMultipleTests;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitemnamenull.AddCategoryItemNameNullTests;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitemskunull.AddCategoryItemSKUNullTests;
import com.liferay.portalweb.portlet.shopping.item.addtoshoppingcartcategoryitem.AddToShoppingCartCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.addtoshoppingcartcategoryitemmultiple.AddToShoppingCartCategoryItemMultipleTests;
import com.liferay.portalweb.portlet.shopping.item.deletecategoryitem.DeleteCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.editcategoryitem.EditCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.emptycartcategoryitem.EmptyCartCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.movecategory1itemtocategory2.MoveCategory1ItemToCategory2Tests;
import com.liferay.portalweb.portlet.shopping.item.searchcategoryitem.SearchCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.updatecartcategoryitemquantity.UpdateCartCategoryItemQuantityTests;
import com.liferay.portalweb.portlet.shopping.item.updatecartcategoryitemquantity0.UpdateCartCategoryItemQuantity0Tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ItemTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddCategoryItemTests.suite());
		testSuite.addTest(AddCategoryItemMultipleTests.suite());
		testSuite.addTest(AddCategoryItemNameNullTests.suite());
		testSuite.addTest(AddCategoryItemSKUNullTests.suite());
		testSuite.addTest(AddToShoppingCartCategoryItemTests.suite());
		testSuite.addTest(AddToShoppingCartCategoryItemMultipleTests.suite());
		testSuite.addTest(DeleteCategoryItemTests.suite());
		testSuite.addTest(EditCategoryItemTests.suite());
		testSuite.addTest(EmptyCartCategoryItemTests.suite());
		testSuite.addTest(MoveCategory1ItemToCategory2Tests.suite());
		testSuite.addTest(SearchCategoryItemTests.suite());
		testSuite.addTest(UpdateCartCategoryItemQuantityTests.suite());
		testSuite.addTest(UpdateCartCategoryItemQuantity0Tests.suite());

		return testSuite;
	}

}