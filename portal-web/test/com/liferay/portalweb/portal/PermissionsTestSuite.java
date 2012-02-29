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

package com.liferay.portalweb.portal;

import com.liferay.portalweb.portal.login.LoginTests;
import com.liferay.portalweb.portal.permissions.announcements.AnnouncementsTests;
import com.liferay.portalweb.portal.permissions.blogs.BlogsTestPlan;
import com.liferay.portalweb.portal.permissions.controlpanel.ControlPanelTests;
import com.liferay.portalweb.portal.permissions.imagegallery.ImageGalleryTestPlan;
import com.liferay.portalweb.portal.permissions.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portal.permissions.usecase.PermissionsIndividualScopeDemoPlan;
import com.liferay.portalweb.portal.permissions.webcontent.WebContentTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsTestSuite extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(ControlPanelTests.suite());
		testSuite.addTest(AnnouncementsTests.suite());
		testSuite.addTest(BlogsTestPlan.suite());
		//testSuite.addTest(DocumentLibraryTests.suite());
		testSuite.addTest(ImageGalleryTestPlan.suite());
		testSuite.addTest(MessageBoardsTests.suite());
		testSuite.addTest(PermissionsIndividualScopeDemoPlan.suite());
		testSuite.addTest(WebContentTestPlan.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}