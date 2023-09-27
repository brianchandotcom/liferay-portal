/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.upgrade.v1_0_4;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
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
		_updateCalEventClassName();

		_deleteRelatedAssetEntries();

		_deleteCalEventClassName();
		_deleteDuplicateResourcePermissions();

		super.doUpgrade();
	}

	@Override
	protected String[][] getClassNames() {
		return new String[0][0];
	}

	@Override
	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	private void _deleteCalEventClassName() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"delete from Counter where name like '" +
					_CLASS_NAME_CAL_EVENT + "%'");

			runSQL(
				"delete from ClassName_ where value like '" +
					_CLASS_NAME_CAL_EVENT + "%'");

			runSQL(
				"delete from MBDiscussion where classNameId = " +
					PortalUtil.getClassNameId(_CLASS_NAME_CAL_EVENT));

			runSQL(
				"delete from MBMessage where classNameId = " +
					PortalUtil.getClassNameId(_CLASS_NAME_CAL_EVENT));

			runSQL(
				"delete from ResourceAction where name like '" +
					_CLASS_NAME_CAL_EVENT + "%'");

			runSQL(
				"delete from ResourceBlock where name like '" +
					_CLASS_NAME_CAL_EVENT + "%'");

			runSQL(
				"delete from ResourcePermission where name like '" +
					_CLASS_NAME_CAL_EVENT + "%'");

			runSQL(
				"delete from Subscription where classNameId = " +
					PortalUtil.getClassNameId(_CLASS_NAME_CAL_EVENT));
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
	}

	private void _deleteDuplicateResourcePermissions() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select orp.resourcePermissionId from ",
							"ResourcePermission orp, ResourcePermission nrp ",
							"where orp.companyId = nrp.companyId and ",
							"orp.scope = nrp.scope and orp.primKey = ",
							"nrp.primKey and orp.roleId = nrp.roleId and ",
							"orp.name = ? and nrp.name = ?"))) {

				preparedStatement.setString(1, _RESOURCE_NAMES[0][0]);
				preparedStatement.setString(2, _RESOURCE_NAMES[0][1]);

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					String deleteSQL =
						"delete from ResourcePermission where " +
							"resourcePermissionId = " + resultSet.getLong(1);

					runSQL(deleteSQL);
				}
			}
			catch (Exception exception) {
				throw new UpgradeException(exception);
			}
		}
	}

	private void _deleteRelatedAssetEntries() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select entryId from AssetEntry where classNameId = ?");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from AssetLink where entryId1 = ? or entryId2 = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection, "delete from AssetEntry where entryId = ? ")) {

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(_CLASS_NAME_CAL_EVENT));

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				long entryId = resultSet.getLong("entryId");

				preparedStatement2.setLong(1, entryId);
				preparedStatement2.setLong(2, entryId);

				preparedStatement2.addBatch();

				preparedStatement3.setLong(1, entryId);
				preparedStatement3.addBatch();
			}

			preparedStatement2.executeBatch();

			preparedStatement3.executeBatch();
		}
		catch (SQLException sqlException) {
			throw new UpgradeException(sqlException);
		}
	}

	private void _updateCalEventClassName() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select classNameId from ClassName_ where value like ?");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select vocabularyId, settings_ from AssetVocabulary where " +
					"settings_ like ?");
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"update AssetVocabulary set settings_ = ? where vocabularyId " +
					"= ?")) {

			preparedStatement1.setString(1, _CLASS_NAME_CAL_EVENT + "%");

			ResultSet resultSet = preparedStatement1.executeQuery();

			long calEventClassNameId = 0;

			if (resultSet.next()) {
				calEventClassNameId = resultSet.getLong("classNameId");
			}
			else {
				return;
			}

			preparedStatement1.setString(1, _CLASS_NAME_CALENDAR_BOOKING + "%");

			resultSet = preparedStatement1.executeQuery();

			long calBookingClassNameId = 0;

			if (resultSet.next()) {
				calBookingClassNameId = resultSet.getLong("classNameId");
			}
			else {
				return;
			}

			preparedStatement2.setString(1, "%" + calEventClassNameId + "%");

			resultSet = preparedStatement2.executeQuery();

			while (resultSet.next()) {
				String oldSettings = resultSet.getString("settings_");
				long vocabularyId = resultSet.getLong("vocabularyId");

				String newSettings = StringUtil.replace(
					oldSettings, String.valueOf(calEventClassNameId),
					String.valueOf(calBookingClassNameId));

				preparedStatement3.setString(1, newSettings);

				preparedStatement3.setLong(2, vocabularyId);

				preparedStatement3.execute();
			}
		}
		catch (SQLException sqlException) {
			throw new UpgradeException(sqlException);
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