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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.Map;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class CalendarFieldsFixture {

	public CalendarFieldsFixture(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	public void populateGroupRoleId(Map<String, String> fieldValues)
		throws PortalException {

		Role role = _roleLocalService.getDefaultGroupRole(_group.getGroupId());

		fieldValues.put(
			Field.GROUP_ROLE_ID,
			_group.getGroupId() + StringPool.DASH + role.getRoleId());
	}

	public void populateRoleId(String roleName, Map<String, String> fieldValues)
		throws PortalException {

		Role role = _roleLocalService.getRole(_group.getCompanyId(), roleName);

		fieldValues.put(Field.ROLE_ID, String.valueOf(role.getRoleId()));
	}

	public void populateUID(
		Calendar calendar, Map<String, String> fieldValues) {

		fieldValues.put(
			Field.UID,
			calendar.getModelClassName() + "_PORTLET_" +
				calendar.getCalendarId());
	}

	public void populateUID(
		CalendarBooking calendarBooking, Map<String, String> fieldValues) {

		fieldValues.put(
			Field.UID,
			calendarBooking.getModelClassName() + "_PORTLET_" +
				calendarBooking.getCalendarBookingId());
	}

	public void setGroup(Group group) {
		_group = group;
	}

	private Group _group;
	private final RoleLocalService _roleLocalService;

}