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

package com.liferay.calendar.internal.upgrade.v3_0_0;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.upgrade.util.BaseUpgradeResourceBlock;

/**
 * @author Preston Crary
 */
public class UpgradeCalendarBookingResourceBlock
	extends BaseUpgradeResourceBlock {

	@Override
	protected String getClassName() {
		return CalendarBooking.class.getName();
	}

	@Override
	protected String getPrimaryKeyName() {
		return "calendarBookingId";
	}

	@Override
	protected String getTableName() {
		return "CalendarBooking";
	}

	@Override
	protected boolean hasUserId() {
		return true;
	}

}