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

package com.liferay.calendar.service.persistence.impl;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.persistence.CalendarResourcePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Eduardo Lundgren
 * @generated
 */
public class CalendarResourceFinderBaseImpl extends BasePersistenceImpl<CalendarResource> {
	public CalendarResourceFinderBaseImpl() {
		setModelClass(CalendarResource.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("code", "code_");
			dbColumnNames.put("active", "active_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCalendarResourcePersistence().getBadColumnNames();
	}

	/**
	 * Returns the calendar resource persistence.
	 *
	 * @return the calendar resource persistence
	 */
	public CalendarResourcePersistence getCalendarResourcePersistence() {
		return calendarResourcePersistence;
	}

	/**
	 * Sets the calendar resource persistence.
	 *
	 * @param calendarResourcePersistence the calendar resource persistence
	 */
	public void setCalendarResourcePersistence(
		CalendarResourcePersistence calendarResourcePersistence) {
		this.calendarResourcePersistence = calendarResourcePersistence;
	}

	@BeanReference(type = CalendarResourcePersistence.class)
	protected CalendarResourcePersistence calendarResourcePersistence;
	private static final Log _log = LogFactoryUtil.getLog(CalendarResourceFinderBaseImpl.class);
}