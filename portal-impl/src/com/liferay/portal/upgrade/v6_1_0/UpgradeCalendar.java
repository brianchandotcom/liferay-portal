/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jabsorb.JSONSerializer;

/**
 * @author Matthew Kong
 */
public class UpgradeCalendar extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		initSerializer();

		updateCalEvent();
	}

	protected void initSerializer() throws Exception {
		_jsonSerializer = new JSONSerializer();

		_jsonSerializer.registerDefaultSerializers();
	}

	protected void updateCalEvent() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select eventId, recurrence from CalEvent");

			rs = ps.executeQuery();

			while (rs.next()) {
				long eventId = rs.getLong("eventId");
				String oldRecurrence = rs.getString("recurrence");

				TZSRecurrence recurrence =
					(TZSRecurrence)_jsonSerializer.fromJSON(oldRecurrence);

				String newRecurrence = JSONFactoryUtil.serialize(recurrence);

				updateCalEvent(eventId, newRecurrence);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateCalEvent(long eventId, String recurrence)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update CalEvent set recurrence = ? where eventId = ?");

			ps.setString(1, recurrence);
			ps.setLong(2, eventId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private org.jabsorb.JSONSerializer _jsonSerializer;

}