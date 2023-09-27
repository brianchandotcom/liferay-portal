/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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