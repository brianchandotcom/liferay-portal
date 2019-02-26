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

package com.liferay.calendar.internal.upgrade.v1_0_4;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Cristina González
 */
public class UpgradeClassNames extends UpgradeKernelPackage {

	@Override
	public void doUpgrade() throws UpgradeException {
		updateCalEventClassName();

		deleteCalEventClassName();
		deleteDuplicateResourcePermissions();
		deleteDuplicateResources();

		super.doUpgrade();
	}

	protected void deleteCalEventClassName() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"delete from Counter where name like '" +
					_CLASS_NAME_CAL_EVENT + "%'");

			runSQL(
				"delete from ClassName_ where value like '" +
					_CLASS_NAME_CAL_EVENT + "%'");

			runSQL(
				"delete from ResourceAction where name like '" +
					_CLASS_NAME_CAL_EVENT + "%'");

			runSQL(
				"delete from ResourceBlock where name like '" +
					_CLASS_NAME_CAL_EVENT + "%'");

			runSQL(
				"delete from ResourcePermission where name like '" +
					_CLASS_NAME_CAL_EVENT + "%'");
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void deleteDuplicateResourcePermissions()
		throws UpgradeException {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(6);

			sb.append("select orp.resourcePermissionId from ");
			sb.append("ResourcePermission orp, ResourcePermission nrp where ");
			sb.append("orp.companyId = nrp.companyId and orp.scope = ");
			sb.append("nrp.scope and orp.primKey = nrp.primKey and ");
			sb.append("orp.roleId = nrp.roleId and orp.name = ? and nrp.name ");
			sb.append("= ?");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString())) {

				ps.setString(1, _RESOURCE_NAMES[0][0]);
				ps.setString(2, _RESOURCE_NAMES[0][1]);

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					String deleteSQL =
						"delete from ResourcePermission where " +
							"resourcePermissionId = " + rs.getLong(1);

					runSQL(deleteSQL);
				}
			}
			catch (Exception e) {
				throw new UpgradeException(e);
			}
		}
	}

	protected void deleteDuplicateResources() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String newName = _RESOURCE_NAMES[0][1];

			String selectSQL =
				"select actionId from ResourceAction where name = '" + newName +
					"'";

			try (PreparedStatement ps = connection.prepareStatement(selectSQL);
				ResultSet rs = ps.executeQuery()) {

				String oldName = _RESOURCE_NAMES[0][0];

				while (rs.next()) {
					runSQL(
						StringBundler.concat(
							"delete from ResourceAction where actionId = '",
							rs.getString(1), "' and name= '", oldName, "'"));
				}
			}
			catch (Exception e) {
				throw new UpgradeException(e);
			}
		}
	}

	@Override
	protected String[][] getClassNames() {
		return new String[0][0];
	}

	@Override
	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	protected void updateCalEventClassName() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select classNameId from ClassName_ where value like ?");
			PreparedStatement ps2 = connection.prepareStatement(
				"select vocabularyId, settings_ from AssetVocabulary where " +
					"settings_ like ?");
			PreparedStatement ps3 = connection.prepareStatement(
				"update AssetVocabulary set settings_ = ? where vocabularyId " +
					"= ?")) {

			ps1.setString(1, _CLASS_NAME_CAL_EVENT + "%");

			ResultSet rs = ps1.executeQuery();

			long calEventClassNameId = 0;

			if (rs.next()) {
				calEventClassNameId = rs.getLong("classNameId");
			}
			else {
				return;
			}

			ps1.setString(1, _CLASS_NAME_CALENDAR_BOOKING + "%");

			rs = ps1.executeQuery();

			long calBookingClassNameId = 0;

			if (rs.next()) {
				calBookingClassNameId = rs.getLong("classNameId");
			}
			else {
				return;
			}

			ps2.setString(1, "%" + calEventClassNameId + "%");

			rs = ps2.executeQuery();

			while (rs.next()) {
				String oldSettings = rs.getString("settings_");
				long vocabularyId = rs.getLong("vocabularyId");

				String newSettings = StringUtil.replace(
					oldSettings, String.valueOf(calEventClassNameId),
					String.valueOf(calBookingClassNameId));

				ps3.setString(1, newSettings);

				ps3.setLong(2, vocabularyId);

				ps3.execute();
			}
		}
		catch (SQLException sqle) {
			throw new UpgradeException(sqle);
		}
	}

	private static final String _CLASS_NAME_CAL_EVENT =
		"com.liferay.portlet.calendar.model.CalEvent";

	private static final String _CLASS_NAME_CALENDAR_BOOKING =
		"com.liferay.calendar.model.CalendarBooking";

	private static final String[][] _RESOURCE_NAMES = {
		{"com.liferay.portlet.calendar", "com.liferay.calendar"}
	};

}