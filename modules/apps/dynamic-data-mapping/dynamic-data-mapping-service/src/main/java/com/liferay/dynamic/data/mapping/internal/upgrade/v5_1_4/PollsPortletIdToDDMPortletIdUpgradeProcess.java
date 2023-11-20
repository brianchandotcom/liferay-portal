/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_4;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rebeca Silva
 */
public class PollsPortletIdToDDMPortletIdUpgradeProcess
	extends BasePortletIdUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"delete from Portlet where portletId = '", _PORTLET_ID_POLLS,
				"' OR portletId = '", _PORTLET_ID_POLLS_DISPLAY, "'"));
		runSQL(
			StringBundler.concat(
				"delete from ResourcePermission where name = '",
				_PORTLET_ID_POLLS, "' OR name = '", _PORTLET_ID_POLLS_DISPLAY,
				"'"));

		removeDuplicatePortletPreferences();

		super.doUpgrade();

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select portletPreferencesId from PortletPreferences ",
						"where portletId like '%",
						DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM, "%'"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					StringBundler.concat(
						"update PortletPreferenceValue set name = ",
						"'formInstanceId' where name = 'questionId' AND ",
						"portletPreferencesId = ?"));
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				updatePreparedStatement.setLong(1, resultSet.getLong(1));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{_PORTLET_ID_POLLS, DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN},
			{
				_PORTLET_ID_POLLS_DISPLAY,
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM
			}
		};
	}

	protected void removeDuplicatePortletPreferences() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ownerId, ownerType, plid from PortletPreferences ",
					"where portletId = '", _PORTLET_ID_POLLS, "'"));
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from PortletPreferences where ownerId = ? and " +
						"ownerType = ? and plid = ? and portletId = ?")) {

			while (resultSet.next()) {
				preparedStatement2.setLong(1, resultSet.getLong(1));
				preparedStatement2.setInt(2, resultSet.getInt(2));
				preparedStatement2.setLong(3, resultSet.getLong(3));
				preparedStatement2.setString(
					4, DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final String _PORTLET_ID_POLLS =
		"com_liferay_polls_web_portlet_PollsPortlet";

	private static final String _PORTLET_ID_POLLS_DISPLAY =
		"com_liferay_polls_web_portlet_PollsDisplayPortlet";

}