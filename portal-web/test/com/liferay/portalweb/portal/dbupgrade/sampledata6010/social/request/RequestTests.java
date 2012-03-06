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

package com.liferay.portalweb.portal.dbupgrade.sampledata6010.social.request;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class RequestTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUserSRqTest.class);
		testSuite.addTestSuite(AddUserSRqPasswordTest.class);
		testSuite.addTestSuite(AddUserSRq2Test.class);
		testSuite.addTestSuite(AddUserSRq2PasswordTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SRq_SignInTest.class);
		testSuite.addTestSuite(SRq_AddPageSummaryTest.class);
		testSuite.addTestSuite(SRq_AddPortletSummaryTest.class);
		testSuite.addTestSuite(SRq_AddPageRequestTest.class);
		testSuite.addTestSuite(SRq_AddPortletRequestsTest.class);
		testSuite.addTestSuite(SRq_AddPageFriendsTest.class);
		testSuite.addTestSuite(SRq_AddPortletFriendsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SRq2_SignInTest.class);
		testSuite.addTestSuite(SRq2_AddAsFriendTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SRq_SignInTest.class);
		testSuite.addTestSuite(SRq_ViewRequestsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);

		return testSuite;
	}
}