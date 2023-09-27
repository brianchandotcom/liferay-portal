/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalDateTimeUtil {

	public static LocalDateTime toLocalDateTime(Date date) {
		return toLocalDateTime(date, null);
	}

	public static LocalDateTime toLocalDateTime(Date date, Date defaultDate) {
		Instant instant = null;

		if (date == null) {
			if (defaultDate == null) {
				defaultDate = new Date();
			}

			instant = defaultDate.toInstant();
		}
		else {
			instant = date.toInstant();
		}

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		return zonedDateTime.toLocalDateTime();
	}

}