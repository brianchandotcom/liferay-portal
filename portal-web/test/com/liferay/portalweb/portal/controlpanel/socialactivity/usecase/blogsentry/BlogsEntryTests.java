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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.blogsentry;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addportletsite.AddPageBlogsSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addportletsite.AddPageUSSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addportletsite.AddPortletBlogsSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addportletsite.AddPortletUSSiteTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.edituserpassword.EditUserPasswordTest;
import com.liferay.portalweb.portal.controlpanel.users.user.editusersite.EditUserSiteTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddPageBlogsSiteTest.class);
		testSuite.addTestSuite(AddPortletBlogsSiteTest.class);
		testSuite.addTestSuite(AddPageUSSiteTest.class);
		testSuite.addTestSuite(AddPortletUSSiteTest.class);
		testSuite.addTestSuite(EnableSocialActivityBlogsEntrySiteTest.class);
		testSuite.addTestSuite(ConfigurePortletShowHeaderTextOffTest.class);
		testSuite.addTestSuite(ConfigurePortletShowTotalsOffTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter1SubscriptionsTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter2CommentsTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter3BlogEntriesTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter4BlogEntryUpdatesTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter5VotesTest.class);
		testSuite.addTestSuite(AddBlogsEntry1SiteTest.class);
		testSuite.addTestSuite(ViewUserStatisticsAddBlogsEntry1Test.class);
		testSuite.addTestSuite(AddBlogsEntry2SiteTest.class);
		testSuite.addTestSuite(ViewUserStatisticsAddBlogsEntry2Test.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(EditUserPasswordTest.class);
		testSuite.addTestSuite(EditUserSiteTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_SubscribeBlogsEntry2SiteTest.class);
		testSuite.addTestSuite(User_ViewUserStatisticsSubscribeBlogsEntry2Test.class);
		testSuite.addTestSuite(User_CommentBlogsEntry2SiteTest.class);
		testSuite.addTestSuite(User_ViewUserStatisticsCommentBlogsEntry2Test.class);
		testSuite.addTestSuite(User_VoteBlogsEntry2SiteTest.class);
		testSuite.addTestSuite(User_ViewUserStatisticsVoteBlogsEntry2Test.class);
		testSuite.addTestSuite(User_UnsubscribeBlogsEntry2SiteTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(EditBlogsEntry2SiteTest.class);
		testSuite.addTestSuite(ViewUserStatisticsEditBlogsEntry2Test.class);
		testSuite.addTestSuite(TearDownSiteTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}
}