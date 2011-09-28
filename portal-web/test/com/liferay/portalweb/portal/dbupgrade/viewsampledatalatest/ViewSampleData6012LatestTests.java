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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.address.Address6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcements.Announcements6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcementsdelivery.AnnouncementsDelivery6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.bookmarks.Bookmarks6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.calendar.Calendar6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.community.Community6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.documentlibrary.DocumentLibrary6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.expando.Expando6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.Groups6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.login.LoginTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.organizations.Organizations6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.portletpermissions.PortletPermissions6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.shopping.Shopping6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.social.Social6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingcommunity.StagingCommunity6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingorganization.StagingOrganization6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.tags.Tags6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.webcontent.WebContent6012LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.wiki.Wiki6012LatestTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSampleData6012LatestTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(Address6012LatestTests.suite());
		testSuite.addTest(Announcements6012LatestTests.suite());
		testSuite.addTest(AnnouncementsDelivery6012LatestTests.suite());
		testSuite.addTest(Bookmarks6012LatestTests.suite());
		testSuite.addTest(Calendar6012LatestTests.suite());
		testSuite.addTest(Community6012LatestTests.suite());
		testSuite.addTest(DocumentLibrary6012LatestTests.suite());
		testSuite.addTest(Expando6012LatestTests.suite());
		testSuite.addTest(Groups6012LatestTests.suite());
		testSuite.addTest(Organizations6012LatestTests.suite());
		testSuite.addTest(PortletPermissions6012LatestTests.suite());
		testSuite.addTest(Shopping6012LatestTests.suite());
		testSuite.addTest(Social6012LatestTests.suite());
		testSuite.addTest(StagingCommunity6012LatestTests.suite());
		testSuite.addTest(StagingOrganization6012LatestTests.suite());
		testSuite.addTest(Tags6012LatestTests.suite());
		testSuite.addTest(WebContent6012LatestTests.suite());
		testSuite.addTest(Wiki6012LatestTests.suite());

		return testSuite;
	}

}