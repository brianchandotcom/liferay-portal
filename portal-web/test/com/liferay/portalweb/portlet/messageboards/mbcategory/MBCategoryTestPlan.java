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

package com.liferay.portalweb.portlet.messageboards.mbcategory;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategories.AddMBCategoriesTests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategory.AddMBCategoryTests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategorydescription.AddMBCategoryDescriptionTests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategorynamenull.AddMBCategoryNameNullTests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategorynameutf8.AddMBCategoryNameUtf8Tests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbsubcategories.AddMBSubcategoriesTests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbsubcategory.AddMBSubcategoryTests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbsubcategorydescription.AddMBSubcategoryDescriptionTests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbsubcategorynamenull.AddMBSubcategoryNameNullTests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.deletembcategory.DeleteMBCategoryTests;
import com.liferay.portalweb.portlet.messageboards.mbcategory.editmbcategory.EditMBCategoryTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MBCategoryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddMBCategoriesTests.suite());
		testSuite.addTest(AddMBCategoryTests.suite());
		testSuite.addTest(AddMBCategoryDescriptionTests.suite());
		testSuite.addTest(AddMBCategoryNameNullTests.suite());
		testSuite.addTest(AddMBCategoryNameUtf8Tests.suite());
		testSuite.addTest(AddMBSubcategoriesTests.suite());
		testSuite.addTest(AddMBSubcategoryTests.suite());
		testSuite.addTest(AddMBSubcategoryDescriptionTests.suite());
		testSuite.addTest(AddMBSubcategoryNameNullTests.suite());
		testSuite.addTest(DeleteMBCategoryTests.suite());
		testSuite.addTest(EditMBCategoryTests.suite());

		return testSuite;
	}

}