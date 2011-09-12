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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontent150character.AddWHEntryContent150CharacterTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontent151character.AddWHEntryContent151CharacterTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebycoworkers.AddWHEntryContentViewableByCoworkersTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebyeveryone.AddWHEntryContentViewableByEveryoneTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebyfollowers.AddWHEntryContentViewableByFollowersTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebyfriends.AddWHEntryContentViewableByFriendsTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.deletewhentrycontent.DeleteWHEntryContentTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.editwhentrycontentviewablebycoworkers.EditWHEntryContentViewableByCoworkersTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.editwhentrycontentviewablebyeveryone.EditWHEntryContentViewableByEveryoneTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.editwhentrycontentviewablebyfollowers.EditWHEntryContentViewableByFollowersTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.editwhentrycontentviewablebyfriends.EditWHEntryContentViewableByFriendsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WHEntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWHEntryContent150CharacterTests.suite());
		testSuite.addTest(AddWHEntryContent151CharacterTests.suite());
		testSuite.addTest(AddWHEntryContentViewableByCoworkersTests.suite());
		testSuite.addTest(AddWHEntryContentViewableByEveryoneTests.suite());
		testSuite.addTest(AddWHEntryContentViewableByFollowersTests.suite());
		testSuite.addTest(AddWHEntryContentViewableByFriendsTests.suite());
		testSuite.addTest(DeleteWHEntryContentTests.suite());
		testSuite.addTest(EditWHEntryContentViewableByCoworkersTests.suite());
		testSuite.addTest(EditWHEntryContentViewableByEveryoneTests.suite());
		testSuite.addTest(EditWHEntryContentViewableByFollowersTests.suite());
		testSuite.addTest(EditWHEntryContentViewableByFriendsTests.suite());

		return testSuite;
	}

}