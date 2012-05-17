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

package com.liferay.portalweb.demo.fundamentals.announcementsdistribitionscope;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.edituserpassword.EditUserPasswordTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignOutTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.announcements.announcementsentry.addannouncementsentrygeneral.TearDownAnnouncementsEntryTest;
import com.liferay.portalweb.portlet.announcements.portlet.addportletannouncements.AddPageAnnouncementsTest;
import com.liferay.portalweb.portlet.announcements.portlet.addportletannouncements.AddPortletAnnouncementsTest;
import com.liferay.portalweb.portlet.announcements.portlet.addportletannouncementssite.AddPageAnnouncementsSiteTest;
import com.liferay.portalweb.portlet.announcements.portlet.addportletannouncementssite.AddPortletAnnouncementsSiteTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsDistributionScopeTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAnnouncementsTest.class);
		testSuite.addTestSuite(AddPortletAnnouncementsTest.class);
		testSuite.addTestSuite(AddAnnouncementsEntryGeneralTest.class);
		testSuite.addTestSuite(ViewAnnouncementsEntryTest.class);
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddPageAnnouncementsSiteTest.class);
		testSuite.addTestSuite(AddPortletAnnouncementsSiteTest.class);
		testSuite.addTestSuite(ViewAnnouncementsEntrySiteNameTest.class);
		testSuite.addTestSuite(AddScopedAnnouncementsEntrySiteNameTest.class);
		testSuite.addTestSuite(ViewScopedAnnouncementsEntrySiteNameTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(EditUserPasswordTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_ViewAnnouncementsEntryTest.class);
		testSuite.addTestSuite(User_ViewScopedAnnouncementsEntryTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownAnnouncementsEntryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);

		return testSuite;
	}
}