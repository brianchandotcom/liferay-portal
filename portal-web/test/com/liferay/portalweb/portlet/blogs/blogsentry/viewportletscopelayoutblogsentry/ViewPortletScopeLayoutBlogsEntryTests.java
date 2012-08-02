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

package com.liferay.portalweb.portlet.blogs.blogsentry.viewportletscopelayoutblogsentry;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogs.AddPageBlogs1Test;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogs.AddPageBlogs2Test;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogs.AddPortletBlogs1Test;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogs.AddPortletBlogs2Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletScopeLayoutBlogsEntryTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageBlogs1Test.class);
		testSuite.addTestSuite(AddPortletBlogs1Test.class);
		testSuite.addTestSuite(AddPageBlogs2Test.class);
		testSuite.addTestSuite(AddPortletBlogs2Test.class);
		testSuite.addTestSuite(ConfigurePortletScopeLayoutCurrentPagePage2Test.class);
		testSuite.addTestSuite(AddPortletScopeLayoutCurrentPageBlogsEntryPage2Test.class);
		testSuite.addTestSuite(ViewPortletScopeDefaultBlogsEntryCPTest.class);
		testSuite.addTestSuite(ViewPortletScopePage2BlogsEntryCPTest.class);
		testSuite.addTestSuite(ViewPortletScopeLayoutCurrentPageBlogsEntryPage2Test.class);
		testSuite.addTestSuite(ViewPortletScopeLayoutDefaultBlogsEntryPage1Test.class);
		testSuite.addTestSuite(ConfigurePortletScopeLayoutPage2Page1Test.class);
		testSuite.addTestSuite(ViewPortletScopeLayoutPage2BlogsEntryPage1Test.class);
		testSuite.addTestSuite(TearDownPortletScopeLayoutCurrentPageBlogsEntryPage2Test.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}