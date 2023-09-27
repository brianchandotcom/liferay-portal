/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.model.impl;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.util.TimeZone;

/**
 * @author Eduardo Lundgren
 */
public class CalendarImpl extends CalendarBaseImpl {

	@Override
	public CalendarResource getCalendarResource() throws PortalException {
		return CalendarResourceLocalServiceUtil.getCalendarResource(
			getCalendarResourceId());
	}

	@Override
	public TimeZone getTimeZone() {
		return TimeZoneUtil.getTimeZone(getTimeZoneId());
	}

}