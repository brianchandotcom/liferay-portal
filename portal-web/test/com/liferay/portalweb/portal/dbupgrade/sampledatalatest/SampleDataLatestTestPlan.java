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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.address.AddressTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.announcements.AnnouncementsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.announcementsdelivery.AnnouncementsDeliveryTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.bookmarks.BookmarksTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.calendar.CalendarTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.community.CommunityTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.DocumentLibraryTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.expando.ExpandoTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.groups.GroupsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.messageboards.MessageBoardsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.organizations.OrganizationsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.phone.PhoneTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.polls.PollsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.portletpermissions.PortletPermissionsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.shopping.ShoppingTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.social.SocialTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.stagingcommunity.StagingCommunityTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.stagingorganization.StagingOrganizationTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.tags.TagsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.webcontent.WebContentTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.website.WebsiteTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.WikiTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SampleDataLatestTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddressTestPlan.suite());
		testSuite.addTest(AnnouncementsTestPlan.suite());
		testSuite.addTest(AnnouncementsDeliveryTestPlan.suite());
		testSuite.addTest(BookmarksTestPlan.suite());
		testSuite.addTest(CalendarTestPlan.suite());
		testSuite.addTest(CommunityTestPlan.suite());
		testSuite.addTest(DocumentLibraryTestPlan.suite());
		testSuite.addTest(ExpandoTestPlan.suite());
		testSuite.addTest(GroupsTestPlan.suite());
		testSuite.addTest(MessageBoardsTestPlan.suite());
		testSuite.addTest(OrganizationsTestPlan.suite());
		testSuite.addTest(PhoneTestPlan.suite());
		testSuite.addTest(PollsTestPlan.suite());
		testSuite.addTest(PortletPermissionsTestPlan.suite());
		testSuite.addTest(ShoppingTestPlan.suite());
		testSuite.addTest(StagingCommunityTestPlan.suite());
		testSuite.addTest(StagingOrganizationTestPlan.suite());
		testSuite.addTest(TagsTestPlan.suite());
		testSuite.addTest(WebContentTestPlan.suite());
		testSuite.addTest(WebsiteTestPlan.suite());
		testSuite.addTest(WikiTestPlan.suite());
		testSuite.addTest(SocialTestPlan.suite());

		return testSuite;
	}

}