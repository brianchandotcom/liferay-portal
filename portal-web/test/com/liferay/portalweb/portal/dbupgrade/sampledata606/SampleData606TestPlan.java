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

package com.liferay.portalweb.portal.dbupgrade.sampledata606;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.address.AddressTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.announcements.AnnouncementsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.announcementsdelivery.AnnouncementsDeliveryTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.bookmarks.BookmarksTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.calendar.CalendarTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.community.CommunityTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.documentlibrary.DocumentLibraryTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.expando.ExpandoTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.groups.GroupsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.organizations.OrganizationsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.phone.PhoneTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.polls.PollsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.portletpermissions.PortletPermissionsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.shopping.ShoppingTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.social.SocialTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.stagingcommunity.StagingCommunityTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.stagingorganization.StagingOrganizationTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.tags.TagsTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.webcontent.WebContentTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.website.WebsiteTestPlan;
import com.liferay.portalweb.portal.dbupgrade.sampledata606.wiki.WikiTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SampleData606TestPlan extends BaseTestSuite {

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