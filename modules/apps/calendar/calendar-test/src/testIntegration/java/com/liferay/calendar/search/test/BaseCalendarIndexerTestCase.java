/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.calendar.search.test;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

/**
 * @author Wade Cao
 */
public abstract class BaseCalendarIndexerTestCase {

	@Before
	public void setUp() throws Exception {
		calendarFixture = createCalendarFixture();

		calendarFixture.setUp();

		calendarSearchFixture = createSingleDocumentSearchFixture();
		indexedFieldsFixture = createIndexedFieldsFixture();
	}

	protected CalendarFixture createCalendarFixture() {
		return new CalendarFixture(
			_calendars, _calendarBookings, _groups, _users,
			calendarLocalService, calendarBookingLocalService);
	}

	protected IndexedFieldsFixture createIndexedFieldsFixture() {
		return new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected CalendarSearchFixture createSingleDocumentSearchFixture() {
		return new CalendarSearchFixture(indexerRegistry);
	}

	protected void setGroup(Group group) {
		calendarFixture.setGroup(group);
		calendarSearchFixture.setGroup(group);
	}

	protected void setIndexerClass(Class<?> clazz) {
		calendarSearchFixture.setIndexerClass(clazz);
	}

	protected void setUser(User user) {
		calendarFixture.setUser(user);
		calendarSearchFixture.setUser(user);
	}

	@Inject
	protected CalendarBookingLocalService calendarBookingLocalService;

	protected CalendarFixture calendarFixture;

	@Inject
	protected CalendarLocalService calendarLocalService;

	protected CalendarSearchFixture calendarSearchFixture;
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected IndexerRegistry indexerRegistry;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	@DeleteAfterTestRun
	private final List<CalendarBooking> _calendarBookings = new ArrayList<>(1);

	@DeleteAfterTestRun
	private final List<Calendar> _calendars = new ArrayList<>(1);

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>(1);

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>(1);

}