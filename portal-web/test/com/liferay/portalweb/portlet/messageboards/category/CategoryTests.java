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

package com.liferay.portalweb.portlet.messageboards.category;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.messageboards.category.addcategory.AddCategoryTests;
import com.liferay.portalweb.portlet.messageboards.category.addcategorymultiple.AddCategoryMultipleTests;
import com.liferay.portalweb.portlet.messageboards.category.addcategorynamenull.AddCategoryNameNullTests;
import com.liferay.portalweb.portlet.messageboards.category.addmbcategorynameutf8.AddMBCategoryNameUtf8Tests;
import com.liferay.portalweb.portlet.messageboards.category.addsubcategory.AddSubcategoryTests;
import com.liferay.portalweb.portlet.messageboards.category.addsubcategorymultiple.AddSubcategoryMultipleTests;
import com.liferay.portalweb.portlet.messageboards.category.addsubcategorynamenull.AddSubcategoryNameNullTests;
import com.liferay.portalweb.portlet.messageboards.category.deletecategory.DeleteCategoryTests;
import com.liferay.portalweb.portlet.messageboards.category.editcategory.EditCategoryTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class CategoryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddCategoryTests.suite());
		testSuite.addTest(AddCategoryMultipleTests.suite());
		testSuite.addTest(AddCategoryNameNullTests.suite());
		testSuite.addTest(AddMBCategoryNameUtf8Tests.suite());
		testSuite.addTest(AddSubcategoryTests.suite());
		testSuite.addTest(AddSubcategoryMultipleTests.suite());
		testSuite.addTest(AddSubcategoryNameNullTests.suite());
		testSuite.addTest(DeleteCategoryTests.suite());
		testSuite.addTest(EditCategoryTests.suite());

		return testSuite;
	}

}