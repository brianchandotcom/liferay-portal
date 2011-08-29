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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.blockccfriendrmenu.BlockCCFriendRMenuTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.blockccuseractions.BlockCCUserActionsTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.ignorecccoworkerrequest.IgnoreCCCoworkerRequestTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.searchccuserfindpeoplelink.SearchCCUserFindPeopleLinkTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.socoaddascoworkerccactions.SOCo_AddAsCoworkerCCActionsTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.socoremoveascoworkercccoworkerrmenu.SOCo_RemoveAsCoworkerCCCoworkerRMenuTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.sofoaddasfollowerccactions.SOFo_AddAsFollowerCCActionsTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.sofounfollowccfollowerrmenu.SOFo_UnfollowCCFollowerRMenuTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.sofraddasfriendccactions.SOFr_AddAsFriendCCActionsTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.sofrremoveasfriendccfriendrmenu.SOFr_RemoveAsFriendCCFriendRMenuTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.unblockccuseractions.UnblockCCUserActionsTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.viewccfriendcontactcard.ViewCCFriendContactCardTests;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.viewccrequestslink.ViewCCRequestsLinkTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ContactsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(BlockCCFriendRMenuTests.suite());
		testSuite.addTest(BlockCCUserActionsTests.suite());
		testSuite.addTest(IgnoreCCCoworkerRequestTests.suite());
		testSuite.addTest(SearchCCUserFindPeopleLinkTests.suite());
		testSuite.addTest(SOCo_AddAsCoworkerCCActionsTests.suite());
		testSuite.addTest(SOCo_RemoveAsCoworkerCCCoworkerRMenuTests.suite());
		testSuite.addTest(SOFo_AddAsFollowerCCActionsTests.suite());
		testSuite.addTest(SOFo_UnfollowCCFollowerRMenuTests.suite());
		testSuite.addTest(SOFr_AddAsFriendCCActionsTests.suite());
		testSuite.addTest(SOFr_RemoveAsFriendCCFriendRMenuTests.suite());
		testSuite.addTest(UnblockCCUserActionsTests.suite());
		testSuite.addTest(ViewCCFriendContactCardTests.suite());
		testSuite.addTest(ViewCCRequestsLinkTests.suite());

		return testSuite;
	}

}