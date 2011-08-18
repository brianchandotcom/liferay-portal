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

package com.liferay.portalweb.socialofficehome.activities;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.socialofficehome.activities.soco_viewmicroblogsentryactivitiescoworkers.SOCo_ViewMicroblogsEntryActivitiesCoworkersTests;
import com.liferay.portalweb.socialofficehome.activities.sofo_viewmicroblogsentryactivitiesfollowing.SOFo_ViewMicroblogsEntryActivitiesFollowingTests;
import com.liferay.portalweb.socialofficehome.activities.sofr_viewmicroblogsentryactivitiesfriends.SOFr_ViewMicroblogsEntryActivitiesFriendsTests;
import com.liferay.portalweb.socialofficehome.activities.viewmicroblogsentryactivitiesme.ViewMicroblogsEntryActivitiesMeTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivitiesTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(SOCo_ViewMicroblogsEntryActivitiesCoworkersTests.suite());
		testSuite.addTest(SOFo_ViewMicroblogsEntryActivitiesFollowingTests.suite());
		testSuite.addTest(SOFr_ViewMicroblogsEntryActivitiesFriendsTests.suite());
		testSuite.addTest(ViewMicroblogsEntryActivitiesMeTests.suite());

		return testSuite;
	}

}