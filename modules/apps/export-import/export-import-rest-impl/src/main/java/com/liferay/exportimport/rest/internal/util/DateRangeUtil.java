/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.portal.kernel.util.DateRange;

import jakarta.ws.rs.BadRequestException;

import java.util.Date;

/**
 * @author Daniel Raposo
 */
public class DateRangeUtil {

	public static DateRange toDateRange(Date startDate, Date endDate) {
		if ((startDate == null) && (endDate == null)) {
			return null;
		}

		if ((startDate != null) && (endDate != null) &&
			!startDate.before(endDate)) {

			throw new BadRequestException("Start date must be before end date");
		}

		return new DateRange(
			(startDate == null) ? new Date(0) : startDate,
			(endDate == null) ? new Date() : endDate);
	}

}