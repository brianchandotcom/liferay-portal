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

package com.liferay.portalweb.socialofficehome.events.event.configureportletdisplaydays2;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletDisplayDays2Tests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageCalendarSOTest.class);
		testSuite.addTestSuite(AddPortletCalendarSOTest.class);
		testSuite.addTestSuite(AddEventDisplayDays2SOTest.class);
		testSuite.addTestSuite(ViewEventEDDisplayDays1Test.class);
		testSuite.addTestSuite(ConfigurePortletDisplayDays2Test.class);
		testSuite.addTestSuite(ViewEventEDDisplayDays2Test.class);
		testSuite.addTestSuite(TearDownConfigurePortletDisplayDaysTest.class);
		testSuite.addTestSuite(TearDownEventSOTest.class);
		testSuite.addTestSuite(TearDownPageCalendarSOTest.class);

		return testSuite;
	}
}