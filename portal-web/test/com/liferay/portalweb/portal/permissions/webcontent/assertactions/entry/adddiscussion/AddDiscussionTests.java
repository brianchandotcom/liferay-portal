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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.adddiscussion;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPageWCDTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPortletWCDTest;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentwcd.AddWCWebContentWCDTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDiscussionTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageWCDTest.class);
		testSuite.addTestSuite(AddPortletWCDTest.class);
		testSuite.addTestSuite(AddWCWebContentWCDTest.class);
		testSuite.addTestSuite(AddMemberTest.class);
		testSuite.addTestSuite(AddMemberRoleTest.class);
		testSuite.addTestSuite(DefineMemberRoleTest.class);
		testSuite.addTestSuite(AssignUserRolesTest.class);
		testSuite.addTestSuite(EnableCommentsWCDTest.class);
		testSuite.addTestSuite(SelectWebContentWCDTest.class);
		testSuite.addTestSuite(RemoveInlinePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertCannotAddCommentTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AddRolePermissionAddDiscussionTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AddCommentTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);
		testSuite.addTestSuite(TearDownWCRolesTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}