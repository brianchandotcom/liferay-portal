/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_4;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.internal.upgrade.BasePollsPortletIdUpgradeProcess;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rebeca Silva
 */
public class PollsPortletIdToDDMPortletIdUpgradeProcess
	extends BasePollsPortletIdUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"delete from Portlet where portletId = '", PORTLET_ID_POLLS,
				"' OR portletId = '", PORTLET_ID_POLLS_DISPLAY, "'"));
		runSQL(
			StringBundler.concat(
				"delete from ResourcePermission where name = '",
				PORTLET_ID_POLLS, "' OR name = '", PORTLET_ID_POLLS_DISPLAY,
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

}