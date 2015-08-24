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

package com.liferay.portal.kernel.util;

import java.util.Comparator;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class TimeZoneComparator implements Comparator<TimeZone> {

	public TimeZoneComparator() {
	}

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