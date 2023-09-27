/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Comparator;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class TimeZoneComparator implements Comparator<TimeZone> {

	@Override
	public int compare(TimeZone timeZone1, TimeZone timeZone2) {
		long currentTime = System.currentTimeMillis();

		Integer totalOffset1 = timeZone1.getOffset(currentTime);
		Integer totalOffset2 = timeZone2.getOffset(currentTime);

		int value = totalOffset1.compareTo(totalOffset2);

		if (value == 0) {
			String timeZoneId1 = timeZone1.getID();
			String timeZoneId2 = timeZone2.getID();

			value = timeZoneId1.compareTo(timeZoneId2);
		}

		return value;
	}

}