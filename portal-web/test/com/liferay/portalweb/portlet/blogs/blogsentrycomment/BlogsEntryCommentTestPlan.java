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

package com.liferay.portalweb.portlet.blogs.blogsentrycomment;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.blogs.blogsentrycomment.addblogsentrycomment.AddBlogsEntryCommentTests;
import com.liferay.portalweb.portlet.blogs.blogsentrycomment.addblogsentrycomments.AddBlogsEntryCommentsTests;
import com.liferay.portalweb.portlet.blogs.blogsentrycomment.addblogsentryscomment.AddBlogsEntrysCommentTests;
import com.liferay.portalweb.portlet.blogs.blogsentrycomment.deleteblogsentrycomment.DeleteBlogsEntryCommentTests;
import com.liferay.portalweb.portlet.blogs.blogsentrycomment.editblogsentrycomment.EditBlogsEntryCommentTests;
import com.liferay.portalweb.portlet.blogs.blogsentrycomment.subscribetocommentsblogsentry.SubscribeToCommentsBlogsEntryTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryCommentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddBlogsEntryCommentTests.suite());
		testSuite.addTest(AddBlogsEntryCommentsTests.suite());
		testSuite.addTest(AddBlogsEntrysCommentTests.suite());
		testSuite.addTest(DeleteBlogsEntryCommentTests.suite());
		testSuite.addTest(EditBlogsEntryCommentTests.suite());
		testSuite.addTest(SubscribeToCommentsBlogsEntryTests.suite());

		return testSuite;
	}

}