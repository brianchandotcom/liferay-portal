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

package com.liferay.calendar.upgrade.v1_0_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Adam Brandizzi
 * @deprecated As of 2.5.0, moved to {@link
 *             com.liferay.calendar.internal.upgrade.v1_0_2.UpgradeCalendar}
 */
@Deprecated
public class UpgradeCalendar extends UpgradeProcess {

	public UpgradeCalendar() {
		_upgradeCalendar =
			new com.liferay.calendar.internal.upgrade.v1_0_2.UpgradeCalendar();
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeCalendar.doUpgrade();
	}

	protected void updateCalendarTable() throws Exception {
		_upgradeCalendar.updateCalendarTable();
	}

	protected void updateCalendarTimeZoneId(long calendarId, String timeZoneId)
		throws Exception {

		_upgradeCalendar.updateCalendarTimeZoneId(calendarId, timeZoneId);
	}

	protected void updateCalendarTimeZoneIds() throws Exception {
		_upgradeCalendar.updateCalendarTimeZoneIds();
	}

	private final com.liferay.calendar.internal.upgrade.v1_0_2.UpgradeCalendar
		_upgradeCalendar;

}