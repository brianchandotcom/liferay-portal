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

package com.liferay.portalweb.socialofficehome.microblogs.microblogsentry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.addmicroblogscontent150character.AddMicroblogsContent150CharacterTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.addmicroblogscontent151character.AddMicroblogsContent151CharacterTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.addmicroblogscontentviewablebycoworkers.AddMicroblogsContentViewableByCoworkersTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.addmicroblogscontentviewablebyeveryone.AddMicroblogsContentViewableByEveryoneTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.addmicroblogscontentviewablebyfollowers.AddMicroblogsContentViewableByFollowersTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.addmicroblogscontentviewablebyfriends.AddMicroblogsContentViewableByFriendsTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.deletemicroblogscontent.DeleteMicroblogsContentTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.editmicroblogscontentviewablebycoworkers.EditMicroblogsContentViewableByCoworkersTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.editmicroblogscontentviewablebyeveryone.EditMicroblogsContentViewableByEveryoneTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.editmicroblogscontentviewablebyfollowers.EditMicroblogsContentViewableByFollowersTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.editmicroblogscontentviewablebyfriends.EditMicroblogsContentViewableByFriendsTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.sofrdeletereplymicroblogscontent.SOFr_DeleteReplyMicroblogsContentTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.sofrdeleterepostmicroblogscontent.SOFr_DeleteRepostMicroblogsContentTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.sofrreplymicroblogscontent.SOFr_ReplyMicroblogsContentTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.sofrrepostmicroblogscontent.SOFr_RepostMicroblogsContentTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.viewmicroblogsmentions.ViewMicroblogsMentionsTests;
import com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.viewmicroblogstimeline.ViewMicroblogsTimelineTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MicroblogsEntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddMicroblogsContent150CharacterTests.suite());
		testSuite.addTest(AddMicroblogsContent151CharacterTests.suite());
		testSuite.addTest(AddMicroblogsContentViewableByCoworkersTests.suite());
		testSuite.addTest(AddMicroblogsContentViewableByEveryoneTests.suite());
		testSuite.addTest(AddMicroblogsContentViewableByFollowersTests.suite());
		testSuite.addTest(AddMicroblogsContentViewableByFriendsTests.suite());
		testSuite.addTest(DeleteMicroblogsContentTests.suite());
		testSuite.addTest(
			EditMicroblogsContentViewableByCoworkersTests.suite());
		testSuite.addTest(EditMicroblogsContentViewableByEveryoneTests.suite());
		testSuite.addTest(
			EditMicroblogsContentViewableByFollowersTests.suite());
		testSuite.addTest(EditMicroblogsContentViewableByFriendsTests.suite());
		testSuite.addTest(SOFr_DeleteReplyMicroblogsContentTests.suite());
		testSuite.addTest(SOFr_DeleteRepostMicroblogsContentTests.suite());
		testSuite.addTest(SOFr_ReplyMicroblogsContentTests.suite());
		testSuite.addTest(SOFr_RepostMicroblogsContentTests.suite());
		testSuite.addTest(ViewMicroblogsMentionsTests.suite());
		testSuite.addTest(ViewMicroblogsTimelineTests.suite());

		return testSuite;
	}

}