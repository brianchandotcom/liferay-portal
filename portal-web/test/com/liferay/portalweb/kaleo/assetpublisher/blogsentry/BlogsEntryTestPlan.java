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

package com.liferay.portalweb.kaleo.assetpublisher.blogsentry;

import com.liferay.portalweb.kaleo.assetpublisher.blogsentry.viewblogsentryassignedtome.ViewBlogsEntryAssignedToMeTests;
import com.liferay.portalweb.kaleo.assetpublisher.blogsentry.viewblogsentryassignedtomyroles.ViewBlogsEntryAssignedToMyRolesTests;
import com.liferay.portalweb.kaleo.assetpublisher.blogsentry.viewblogsentrycompleted.ViewBlogsEntryCompletedTests;
import com.liferay.portalweb.kaleo.assetpublisher.blogsentry.viewblogsentryrejected.ViewBlogsEntryRejectedTests;
import com.liferay.portalweb.kaleo.assetpublisher.blogsentry.viewblogsentryresubmitted.ViewBlogsEntryResubmittedTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewBlogsEntryAssignedToMeTests.suite());
		testSuite.addTest(ViewBlogsEntryAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewBlogsEntryCompletedTests.suite());
		testSuite.addTest(ViewBlogsEntryRejectedTests.suite());
		testSuite.addTest(ViewBlogsEntryResubmittedTests.suite());

		return testSuite;
	}

}