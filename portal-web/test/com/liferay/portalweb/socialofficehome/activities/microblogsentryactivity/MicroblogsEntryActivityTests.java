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

package com.liferay.portalweb.socialofficehome.activities.microblogsentryactivity;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.socialofficehome.activities.microblogsentryactivity.socoviewmicroblogsentryactivitycoworkers.SOCo_ViewMicroblogsEntryActivityCoworkersTests;
import com.liferay.portalweb.socialofficehome.activities.microblogsentryactivity.sofoviewmicroblogsentryactivityfollowing.SOFo_ViewMicroblogsEntryActivityFollowingTests;
import com.liferay.portalweb.socialofficehome.activities.microblogsentryactivity.sofrviewmicroblogsentryactivityfriends.SOFr_ViewMicroblogsEntryActivityFriendsTests;
import com.liferay.portalweb.socialofficehome.activities.microblogsentryactivity.viewmicroblogsentryactivityme.ViewMicroblogsEntryActivityMeTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MicroblogsEntryActivityTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(
			SOCo_ViewMicroblogsEntryActivityCoworkersTests.suite());
		testSuite.addTest(
			SOFo_ViewMicroblogsEntryActivityFollowingTests.suite());
		testSuite.addTest(SOFr_ViewMicroblogsEntryActivityFriendsTests.suite());
		testSuite.addTest(ViewMicroblogsEntryActivityMeTests.suite());

		return testSuite;
	}

}