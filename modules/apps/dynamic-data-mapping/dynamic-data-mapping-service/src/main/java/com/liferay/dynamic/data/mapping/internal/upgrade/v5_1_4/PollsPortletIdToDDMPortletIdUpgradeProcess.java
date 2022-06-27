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

		super.doUpgrade();

		_upgradePortletPreferenceValue();
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

	private void _upgradePortletPreferenceValue() throws Exception {
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

	private static final String _PORTLET_ID_POLLS =
		"com_liferay_polls_web_portlet_PollsPortlet";

	private static final String _PORTLET_ID_POLLS_DISPLAY =
		"com_liferay_polls_web_portlet_PollsDisplayPortlet";

}