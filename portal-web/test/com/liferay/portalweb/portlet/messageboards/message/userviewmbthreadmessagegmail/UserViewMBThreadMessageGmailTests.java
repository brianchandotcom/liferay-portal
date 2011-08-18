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

package com.liferay.portalweb.portlet.messageboards.message.userviewmbthreadmessagegmail;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserViewMBThreadMessageGmailTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AssignMembersSiteUserTest.class);
		testSuite.addTestSuite(AddPageMBSiteTest.class);
		testSuite.addTestSuite(AddPortletMBSiteTest.class);
		testSuite.addTestSuite(ConfigurePortletServerEmailTest.class);
		testSuite.addTestSuite(AddMBCategorySiteTest.class);
		testSuite.addTestSuite(
			ConfigurePortletCategoryMailingListActiveTest.class);
		testSuite.addTestSuite(AddMBCategoryMessageTest.class);
		testSuite.addTestSuite(
			GmailReplyMBCategoryThreadMessageEmailTest.class);
		testSuite.addTestSuite(UserViewMBThreadMessageGmailTest.class);
		testSuite.addTestSuite(GmailTearDownEmailTest.class);
		testSuite.addTestSuite(GmailTearDownEmail2Test.class);
		testSuite.addTestSuite(GmailTearDownMailingListMessageTest.class);
		testSuite.addTestSuite(TearDownMBCategoryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownSitesTest.class);

		return testSuite;
	}

}