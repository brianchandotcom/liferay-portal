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

package com.liferay.portalweb.portlet.shopping.item.addcategoryitems;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.shopping.category.addcategory.AddCategoryTest;
import com.liferay.portalweb.portlet.shopping.category.addcategory.TearDownShoppingCategoryTest;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitem.AddCategoryItem1Test;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitem.AddCategoryItem2Test;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitem.AddCategoryItem3Test;
import com.liferay.portalweb.portlet.shopping.portlet.addportletshopping.AddPageShoppingTest;
import com.liferay.portalweb.portlet.shopping.portlet.addportletshopping.AddPortletShoppingTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCategoryItemsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageShoppingTest.class);
		testSuite.addTestSuite(AddPortletShoppingTest.class);
		testSuite.addTestSuite(AddCategoryTest.class);
		testSuite.addTestSuite(AddCategoryItem1Test.class);
		testSuite.addTestSuite(AddCategoryItem2Test.class);
		testSuite.addTestSuite(AddCategoryItem3Test.class);
		testSuite.addTestSuite(ViewAddCategoryItemsTest.class);
		testSuite.addTestSuite(TearDownShoppingCategoryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}