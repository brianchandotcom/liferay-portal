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

package com.liferay.portalweb.socialofficehome.notifications.notification;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.notifications.notification.requestccaddasconnection.RequestCCAddConnnectionTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.requestprofileaddasconnection.RequestProfileAddConnnectionTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationsjoinprivatesite.SOUs_ConfirmNotificationsJoinPrivateSiteTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationsjoinrestrictedsite.SOUs_ConfirmNotificationsJoinRestrictedSiteTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationsjoinsite.SOUs_ConfirmNotificationsJoinSiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class NotificationTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(RequestCCAddConnnectionTests.suite());
		testSuite.addTest(RequestProfileAddConnnectionTests.suite());
		testSuite.addTest(
			SOUs_ConfirmNotificationsJoinPrivateSiteTests.suite());
		testSuite.addTest(
			SOUs_ConfirmNotificationsJoinRestrictedSiteTests.suite());
		testSuite.addTest(SOUs_ConfirmNotificationsJoinSiteTests.suite());

		return testSuite;
	}

}