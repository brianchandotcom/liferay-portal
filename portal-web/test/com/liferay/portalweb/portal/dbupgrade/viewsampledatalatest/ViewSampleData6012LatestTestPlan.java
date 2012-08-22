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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.address.Address6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcements.Announcements6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcementsdelivery.AnnouncementsDelivery6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.bookmarks.Bookmarks6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.calendar.Calendar6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.community.Community6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.documentlibrary.DocumentLibrary6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.expando.Expando6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.Groups6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.login.LoginTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.organizations.Organizations6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.polls.Polls6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.portletpermissions.PortletPermissions6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.shopping.Shopping6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.social.Social6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingcommunity.StagingCommunity6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingorganization.StagingOrganization6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.tags.Tags6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.webcontent.WebContent6012LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.wiki.Wiki6012LatestTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSampleData6012LatestTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(Address6012LatestTestPlan.suite());
		testSuite.addTest(Announcements6012LatestTestPlan.suite());
		testSuite.addTest(AnnouncementsDelivery6012LatestTestPlan.suite());
		testSuite.addTest(Bookmarks6012LatestTestPlan.suite());
		testSuite.addTest(Calendar6012LatestTestPlan.suite());
		testSuite.addTest(Community6012LatestTestPlan.suite());
		testSuite.addTest(DocumentLibrary6012LatestTestPlan.suite());
		testSuite.addTest(Expando6012LatestTestPlan.suite());
		testSuite.addTest(Groups6012LatestTestPlan.suite());
		testSuite.addTest(Organizations6012LatestTestPlan.suite());
		testSuite.addTest(Polls6012LatestTestPlan.suite());
		testSuite.addTest(PortletPermissions6012LatestTestPlan.suite());
		testSuite.addTest(Shopping6012LatestTestPlan.suite());
		testSuite.addTest(Social6012LatestTestPlan.suite());
		testSuite.addTest(StagingCommunity6012LatestTestPlan.suite());
		testSuite.addTest(StagingOrganization6012LatestTestPlan.suite());
		testSuite.addTest(Tags6012LatestTestPlan.suite());
		testSuite.addTest(WebContent6012LatestTestPlan.suite());
		testSuite.addTest(Wiki6012LatestTestPlan.suite());

		return testSuite;
	}

}