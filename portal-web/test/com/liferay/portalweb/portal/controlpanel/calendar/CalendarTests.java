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

package com.liferay.portalweb.portal.controlpanel.calendar;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class CalendarTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(SetTimezoneTest.class);
		testSuite.addTestSuite(AddAppointmentEventTest.class);
		testSuite.addTestSuite(AddConcertEventTest.class);
		testSuite.addTestSuite(AddVacationEventTest.class);
		testSuite.addTestSuite(GetAppointmentEventsTest.class);
		testSuite.addTestSuite(GetConcertEventsTest.class);
		testSuite.addTestSuite(GetVacationEventsTest.class);
		testSuite.addTestSuite(EditEventTest.class);
		testSuite.addTestSuite(AddTemporaryEventTest.class);
		testSuite.addTestSuite(DeleteTemporaryEventTest.class);
		testSuite.addTestSuite(AddNullTitleEventTest.class);
		testSuite.addTestSuite(AddInvalidStartDateEventTest.class);
		testSuite.addTestSuite(AddInvalidEndDateEventTest.class);
		testSuite.addTestSuite(AddInvalidDurationEventTest.class);
		testSuite.addTestSuite(AddInvalidRepeatEventTest.class);
		testSuite.addTestSuite(TearDownEventCPTest.class);
		testSuite.addTestSuite(ImportLARTest.class);
		testSuite.addTestSuite(AssertImportLARTest.class);
		testSuite.addTestSuite(TearDownEventCPTest.class);
		testSuite.addTestSuite(AddRepeatingEventTest.class);
		testSuite.addTestSuite(AddDailyRepeatingEventTest.class);
		testSuite.addTestSuite(AddWeeklyRepeatingEventTest.class);
		testSuite.addTestSuite(AddWeekDayRepeatingEventTest.class);
		testSuite.addTestSuite(AddMonthlyDateRepeatingEventTest.class);
		testSuite.addTestSuite(AddMonthlyDayRepeatingEventTest.class);
		testSuite.addTestSuite(AddYearlyDateRepeatingEventTest.class);
		testSuite.addTestSuite(AddYearlyDayRepeatingEventTest.class);
		testSuite.addTestSuite(TearDownEventCPTest.class);

		return testSuite;
	}

}