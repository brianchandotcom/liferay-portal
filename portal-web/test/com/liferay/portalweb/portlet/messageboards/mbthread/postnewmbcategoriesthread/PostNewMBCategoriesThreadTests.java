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

package com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategoriesthread;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategory.AddMBCategory1Test;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategory.AddMBCategory2Test;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategory.AddMBCategory3Test;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategory.TearDownMBCategoryTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategory1ThreadTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategory2ThreadTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategory3ThreadTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbthread.TearDownMBThreadTest;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmb.AddPageMBTest;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmb.AddPortletMBTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PostNewMBCategoriesThreadTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(AddMBCategory1Test.class);
		testSuite.addTestSuite(AddMBCategory2Test.class);
		testSuite.addTestSuite(AddMBCategory3Test.class);
		testSuite.addTestSuite(PostNewMBCategory1ThreadTest.class);
		testSuite.addTestSuite(PostNewMBCategory2ThreadTest.class);
		testSuite.addTestSuite(PostNewMBCategory3ThreadTest.class);
		testSuite.addTestSuite(ViewMBCategory1ThreadTest.class);
		testSuite.addTestSuite(ViewMBCategory2ThreadTest.class);
		testSuite.addTestSuite(ViewMBCategory3ThreadTest.class);
		testSuite.addTestSuite(TearDownMBCategoryTest.class);
		testSuite.addTestSuite(TearDownMBThreadTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}