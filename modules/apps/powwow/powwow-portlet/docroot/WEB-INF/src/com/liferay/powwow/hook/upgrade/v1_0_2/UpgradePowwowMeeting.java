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

package com.liferay.powwow.hook.upgrade.v1_0_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.sql.SQLException;

/**
 * @author Marco Calderon
 */
public class UpgradePowwowMeeting extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updatePowwowMeeting();
	}

	protected void updatePowwowMeeting() throws IOException, SQLException {
		StringBundler sb = new StringBundler(5);

		sb.append("update CalendarBooking inner join PowwowMeeting on ");
		sb.append("CalendarBooking.calendarBookingId = ");
		sb.append("PowwowMeeting.calendarBookingId set ");
		sb.append("CalendarBooking.status =");
		sb.append(WorkflowConstants.STATUS_INACTIVE);

		runSQL(sb.toString());
	}

}