/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.Time;

import jakarta.ws.rs.BadRequestException;

import java.util.Date;

/**
 * @author Daniel Raposo
 */
public class DateRangeUtil {

	public static DateRange toDateRange(
		Date endDate, Integer last, String range, Date startDate) {

		if (ExportImportDateUtil.RANGE_DATE_RANGE.equals(range)) {
			if ((startDate == null) || (endDate == null)) {
				throw new BadRequestException(
					"A start date and an end date are required for a date " +
						"range");
			}

			return new DateRange(startDate, endDate);
		}

		if (ExportImportDateUtil.RANGE_LAST.equals(range)) {
			if (last == null) {
				throw new BadRequestException(
					"A number of hours is required for a relative date range");
			}

			Date now = new Date();

			return new DateRange(
				new Date(now.getTime() - (last * Time.HOUR)), now);
		}

		return new DateRange(null, null);
	}

}