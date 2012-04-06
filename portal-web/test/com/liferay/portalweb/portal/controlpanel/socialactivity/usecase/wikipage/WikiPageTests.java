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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.wikipage;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addmemberssiteuser.AddMembersSiteUserTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addportletsite.AddPageUSSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addportletsite.AddPageWikiSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addportletsite.AddPortletUSSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addportletsite.AddPortletWikiSiteTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.edituserpassword.EditUserPasswordTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiPageTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddPageWikiSiteTest.class);
		testSuite.addTestSuite(AddPortletWikiSiteTest.class);
		testSuite.addTestSuite(AddPageUSSiteTest.class);
		testSuite.addTestSuite(AddPortletUSSiteTest.class);
		testSuite.addTestSuite(EnableSocialActivityWikiPageSiteTest.class);
		testSuite.addTestSuite(ConfigurePortletShowHeaderTextOffTest.class);
		testSuite.addTestSuite(ConfigurePortletShowTotalsOffTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter1WikiPageUpdatesTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter2AttachmentsTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter3CommentsTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter4SubscriptionsTest.class);
		testSuite.addTestSuite(ConfigurePortletAddCounter5WikiPageTest.class);
		testSuite.addTestSuite(AddWikiPageSiteTest.class);
		testSuite.addTestSuite(ViewUserStatisticsAddWikiPageSiteTest.class);
		testSuite.addTestSuite(AddWikiPageAttachment1SiteTest.class);
		testSuite.addTestSuite(ViewUserStatisticsAddWikiPageAttachment1Test.class);
		testSuite.addTestSuite(AddWikiPageAttachment2SiteTest.class);
		testSuite.addTestSuite(ViewUserStatisticsAddWikiPageAttachment2Test.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(EditUserPasswordTest.class);
		testSuite.addTestSuite(AddMembersSiteUserTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_ReadWikiPageSiteTest.class);
		testSuite.addTestSuite(User_ViewUserStatisticsReadWikiPageTest.class);
		testSuite.addTestSuite(User_SubscribeWikiPageSiteTest.class);
		testSuite.addTestSuite(User_ViewUserStatisticsSubscribeWikiPageTest.class);
		testSuite.addTestSuite(User_CommentWikiPageSiteTest.class);
		testSuite.addTestSuite(User_ViewUserStatisticsCommentWikiPageTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(EditWikiPageSiteTest.class);
		testSuite.addTestSuite(ViewUserStatisticsEditWikiPageSiteTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}
}