/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import org.jabsorb.JSONSerializer;

/**
 * @author Juan Fernández
 * @author Matthew Kong
 * @author Mate Thurzo
 */
public class VerifyCalendar extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyEndDate();
		verifyNoAssets();
		verifyRecurrence();
	}

	protected void updateEvent(long eventId, String recurrence)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

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

	protected void verifyEndDate() throws Exception {
		runSQL(
			"update CalEvent set endDate = null where endDate is not null " +
				"and (recurrence like '%\"until\":null%' or recurrence like " +
					"'null')");
	}

	protected void verifyNoAssets() throws Exception {
		List<CalEvent> events = CalEventLocalServiceUtil.getNoAssetEvents();

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + events.size() + " events with no asset");
		}

		for (CalEvent event : events) {
			try {
				CalEventLocalServiceUtil.updateAsset(
					event.getUserId(), event, null, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for event " +
							event.getEventId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for events");
		}
	}

	protected void verifyRecurrence() throws Exception {
		JSONSerializer jsonSerializer = new JSONSerializer();

		jsonSerializer.registerDefaultSerializers();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select eventId, recurrence from CalEvent where (CAST_TEXT(" +
					"recurrence) != '') and recurrence not like " +
						"'%serializable%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long eventId = rs.getLong("eventId");
				String recurrence = rs.getString("recurrence");

				TZSRecurrence recurrenceObj = null;

				if (Validator.isNotNull(recurrence)) {
					recurrenceObj = (TZSRecurrence)jsonSerializer.fromJSON(
						recurrence);
				}

				String newRecurrence = JSONFactoryUtil.serialize(recurrenceObj);

				updateEvent(eventId, newRecurrence);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyCalendar.class);

}