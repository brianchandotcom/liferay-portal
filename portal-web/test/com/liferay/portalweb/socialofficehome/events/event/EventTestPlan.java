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

package com.liferay.portalweb.socialofficehome.events.event;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.events.event.configureportletdisplaydays2.ConfigurePortletDisplayDays2Tests;
import com.liferay.portalweb.socialofficehome.events.event.viewdeleteevented.ViewDeleteEventEDTests;
import com.liferay.portalweb.socialofficehome.events.event.viewdeleteeventsiteed.ViewDeleteEventSiteEDTests;
import com.liferay.portalweb.socialofficehome.events.event.viewevented.ViewEventEDTests;
import com.liferay.portalweb.socialofficehome.events.event.vieweventmultipleed.ViewEventMultipleEDTests;
import com.liferay.portalweb.socialofficehome.events.event.vieweventmultiplesiteed.ViewEventMultipleSiteEDTests;
import com.liferay.portalweb.socialofficehome.events.event.vieweventsiteed.ViewEventSiteEDTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EventTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ConfigurePortletDisplayDays2Tests.suite());
		testSuite.addTest(ViewDeleteEventEDTests.suite());
		testSuite.addTest(ViewDeleteEventSiteEDTests.suite());
		testSuite.addTest(ViewEventEDTests.suite());
		testSuite.addTest(ViewEventMultipleEDTests.suite());
		testSuite.addTest(ViewEventMultipleSiteEDTests.suite());
		testSuite.addTest(ViewEventSiteEDTests.suite());

		return testSuite;
	}

}