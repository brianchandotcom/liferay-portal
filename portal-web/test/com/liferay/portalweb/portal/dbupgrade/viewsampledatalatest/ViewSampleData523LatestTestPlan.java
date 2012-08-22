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
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.address.Address523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcements.Announcements523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcementsdelivery.AnnouncementsDelivery523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.bookmarks.Bookmarks523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.calendar.Calendar523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.community.Community523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.documentlibrary.DocumentLibrary523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.expando.Expando523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.Groups523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.login.LoginTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.organizations.Organizations523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.polls.Polls523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.portletpermissions.PortletPermissions523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.shopping.Shopping523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.social.Social523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingcommunity.StagingCommunity523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingorganization.StagingOrganization523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.tags.Tags523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.webcontent.WebContent523LatestTestPlan;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.wiki.Wiki523LatestTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSampleData523LatestTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(Address523LatestTestPlan.suite());
		testSuite.addTest(Announcements523LatestTestPlan.suite());
		testSuite.addTest(AnnouncementsDelivery523LatestTestPlan.suite());
		testSuite.addTest(Bookmarks523LatestTestPlan.suite());
		testSuite.addTest(Calendar523LatestTestPlan.suite());
		testSuite.addTest(Community523LatestTestPlan.suite());
		testSuite.addTest(DocumentLibrary523LatestTestPlan.suite());
		testSuite.addTest(Expando523LatestTestPlan.suite());
		testSuite.addTest(Groups523LatestTestPlan.suite());
		testSuite.addTest(Organizations523LatestTestPlan.suite());
		testSuite.addTest(Polls523LatestTestPlan.suite());
		testSuite.addTest(PortletPermissions523LatestTestPlan.suite());
		testSuite.addTest(Shopping523LatestTestPlan.suite());
		testSuite.addTest(Social523LatestTestPlan.suite());
		testSuite.addTest(StagingCommunity523LatestTestPlan.suite());
		testSuite.addTest(StagingOrganization523LatestTestPlan.suite());
		testSuite.addTest(Tags523LatestTestPlan.suite());
		testSuite.addTest(WebContent523LatestTestPlan.suite());
		testSuite.addTest(Wiki523LatestTestPlan.suite());

		return testSuite;
	}

}