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

package com.liferay.portalweb.portlet.blogs.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogs.AddPortletBlogsTests;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogsduplicate.AddPortletBlogsDuplicateTests;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogsorganization.AddPortletBlogsOrganizationTests;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogssite.AddPortletBlogsSiteTests;
import com.liferay.portalweb.portlet.blogs.portlet.configureportletblogsscopecurrentpage.ConfigurePortletBlogsScopeCurrentPageTests;
import com.liferay.portalweb.portlet.blogs.portlet.configureportletdisplaystyleabstract.ConfigurePortletDisplayStyleAbstractTests;
import com.liferay.portalweb.portlet.blogs.portlet.configureportletdisplaystylefullcontent.ConfigurePortletDisplayStyleFullContentTests;
import com.liferay.portalweb.portlet.blogs.portlet.configureportletdisplaystyletitle.ConfigurePortletDisplayStyleTitleTests;
import com.liferay.portalweb.portlet.blogs.portlet.configureportletremoveallpermissions.ConfigurePortletRemoveAllPermissionTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletBlogsTests.suite());
		testSuite.addTest(AddPortletBlogsDuplicateTests.suite());
		testSuite.addTest(AddPortletBlogsOrganizationTests.suite());
		testSuite.addTest(AddPortletBlogsSiteTests.suite());
		testSuite.addTest(ConfigurePortletBlogsScopeCurrentPageTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleAbstractTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleFullContentTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleTitleTests.suite());
		testSuite.addTest(ConfigurePortletRemoveAllPermissionTests.suite());

		return testSuite;
	}

}