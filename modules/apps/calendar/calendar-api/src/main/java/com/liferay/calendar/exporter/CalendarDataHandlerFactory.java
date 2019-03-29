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

package com.liferay.calendar.exporter;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marcellus Tavares
 */
public class CalendarDataHandlerFactory {

	public static CalendarDataHandler getCalendarDataHandler(
			CalendarDataFormat calendarDataFormat)
		throws PortalException {

		CalendarDataHandler calendarDataHandler = _calendarDataHandlers.get(
			calendarDataFormat);

		if (calendarDataHandler == null) {
			throw new PortalException(
				"Invalid format type " + calendarDataFormat);
		}

		return calendarDataHandler;
	}

	public static void registerCalendarDataHandler(
		CalendarDataFormat calendarDataFormat,
		CalendarDataHandler calendarDataHandler) {

		_calendarDataHandlers.put(calendarDataFormat, calendarDataHandler);
	}

	public static void unregisterCalendarDataHandler(
		CalendarDataFormat calendarDataFormat) {

		_calendarDataHandlers.remove(calendarDataFormat);
	}

	private static final Map<CalendarDataFormat, CalendarDataHandler>
		_calendarDataHandlers = new ConcurrentHashMap<>();

}