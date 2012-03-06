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

package com.liferay.portalweb.portlet.announcements.usecase.demo1;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.announcements.announcementsentry.addannouncementsentrygeneral.AddPageAnnouncementsTest;
import com.liferay.portalweb.portlet.announcements.announcementsentry.addannouncementsentrygeneral.AddPortletAnnouncementsTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class Demo1Tests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAnnouncementsTest.class);
		testSuite.addTestSuite(AddPortletAnnouncementsTest.class);
		testSuite.addTestSuite(AddAnnouncementsEntryGeneralTest.class);
		testSuite.addTestSuite(ViewAnnouncementsEntryTest.class);
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddPageAnnouncementsSiteNameTest.class);
		testSuite.addTestSuite(AddPortletAnnouncementsSiteNameTest.class);
		testSuite.addTestSuite(ViewAnnouncementsEntrySiteNameTest.class);
		testSuite.addTestSuite(AddScopedAnnouncementsEntrySiteNameTest.class);
		testSuite.addTestSuite(ViewScopedAnnouncementsEntrySiteNameTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(SignInUsersTest.class);
		testSuite.addTestSuite(Test_SignInTest.class);
		testSuite.addTestSuite(Test_ViewAnnouncementsEntryTest.class);
		testSuite.addTestSuite(Test_ViewScopedAnnouncementsEntryTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownAnnouncementsEntryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownUsersTest.class);
		testSuite.addTestSuite(TearDownSitesTest.class);

		return testSuite;
	}
}