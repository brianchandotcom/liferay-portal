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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontent150character.AddMicroblogsContent150CharacterTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontent151character.AddMicroblogsContent151CharacterTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontentviewablebyconnections.AddMicroblogsContentViewableByConnectionsTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontentviewablebyeveryone.AddMicroblogsContentViewableByEveryoneTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontentviewablebyfollowers.AddMicroblogsContentViewableByFollowersTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.deletemicroblogscontent.DeleteMicroblogsContentTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.editmicroblogscontentviewablebyconnections.EditMicroblogsContentViewableByConnectionsTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.editmicroblogscontentviewablebyeveryone.EditMicroblogsContentViewableByEveryoneTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.editmicroblogscontentviewablebyfollowers.EditMicroblogsContentViewableByFollowersTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousdeletereplymicroblogscontentprofile.SOUs_DeleteReplyMicroblogsContentProfileTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousdeleterepostmicroblogscontent.SOUs_DeleteRepostMicroblogsContentTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousreplymicroblogscontentprofile.SOUs_ReplyMicroblogsContentProfileTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousreplymicroblogscontenttagprofile.SOUs_ReplyMicroblogsContentTagProfileTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousrepostmicroblogscontentprofile.SOUs_RepostMicroblogsContentProfileTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousviewmbcontentmultiple.SOUs_ViewMBContentMultipleTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.viewmicroblogsmentions.ViewMicroblogsMentionsTests;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.viewmicroblogstimeline.ViewMicroblogsTimelineTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MBEntryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddMicroblogsContent150CharacterTests.suite());
		testSuite.addTest(AddMicroblogsContent151CharacterTests.suite());
		testSuite.addTest(
			AddMicroblogsContentViewableByConnectionsTests.suite());
		testSuite.addTest(AddMicroblogsContentViewableByEveryoneTests.suite());
		testSuite.addTest(AddMicroblogsContentViewableByFollowersTests.suite());
		testSuite.addTest(DeleteMicroblogsContentTests.suite());
		testSuite.addTest(
			EditMicroblogsContentViewableByConnectionsTests.suite());
		testSuite.addTest(EditMicroblogsContentViewableByEveryoneTests.suite());
		testSuite.addTest(
			EditMicroblogsContentViewableByFollowersTests.suite());
		testSuite.addTest(
			SOUs_DeleteReplyMicroblogsContentProfileTests.suite());
		testSuite.addTest(SOUs_DeleteRepostMicroblogsContentTests.suite());
		testSuite.addTest(SOUs_ReplyMicroblogsContentProfileTests.suite());
		testSuite.addTest(SOUs_ReplyMicroblogsContentTagProfileTests.suite());
		testSuite.addTest(SOUs_RepostMicroblogsContentProfileTests.suite());
		testSuite.addTest(SOUs_ViewMBContentMultipleTests.suite());
		testSuite.addTest(ViewMicroblogsMentionsTests.suite());
		testSuite.addTest(ViewMicroblogsTimelineTests.suite());

		return testSuite;
	}

}