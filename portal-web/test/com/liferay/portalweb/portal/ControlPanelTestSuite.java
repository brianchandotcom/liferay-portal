/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal;

import com.liferay.portalweb.portal.controlpanel.admin.AdminTests;
import com.liferay.portalweb.portal.controlpanel.blogs.BlogsTests;
import com.liferay.portalweb.portal.controlpanel.bookmarks.BookmarksTests;
import com.liferay.portalweb.portal.controlpanel.calendar.CalendarTests;
import com.liferay.portalweb.portal.controlpanel.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.OrganizationTests;
import com.liferay.portalweb.portal.controlpanel.pagetemplates.PageTemplatesTests;
import com.liferay.portalweb.portal.controlpanel.passwordpolicies.PasswordPoliciesTests;
import com.liferay.portalweb.portal.controlpanel.polls.PollsTests;
import com.liferay.portalweb.portal.controlpanel.settings.SettingsTests;
import com.liferay.portalweb.portal.controlpanel.sites.SitesTests;
import com.liferay.portalweb.portal.controlpanel.usergroup.UserGroupTests;
import com.liferay.portalweb.portal.controlpanel.users.UsersTests;
import com.liferay.portalweb.portal.controlpanel.virtualhosting.VirtualHostingTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.WebContentTests;
import com.liferay.portalweb.portal.login.LoginTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ControlPanelTestSuite extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(AdminTests.suite());
		testSuite.addTest(BlogsTests.suite());
		testSuite.addTest(BookmarksTests.suite());
		testSuite.addTest(CalendarTests.suite());
		testSuite.addTest(MessageBoardsTests.suite());
		testSuite.addTest(OrganizationTests.suite());
		testSuite.addTest(PageTemplatesTests.suite());
		testSuite.addTest(PasswordPoliciesTests.suite());
		testSuite.addTest(PollsTests.suite());
		testSuite.addTest(SettingsTests.suite());
		testSuite.addTest(SitesTests.suite());
		testSuite.addTest(UserGroupTests.suite());
		testSuite.addTest(UsersTests.suite());
		testSuite.addTest(VirtualHostingTests.suite());
		testSuite.addTest(WebContentTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}