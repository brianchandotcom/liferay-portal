/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Leonardo Barros
 */
public class KaleoActionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select kaleoActionId, script from KaleoAction where script " +
					"like '%WorkflowConstants.toStatus(%'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long kaleoActionId = resultSet.getLong(1);

				String script = resultSet.getString(2);

				script = StringUtil.replace(
					script, "WorkflowConstants.toStatus(",
					"WorkflowConstants.getLabelStatus(");

				_updateScript(kaleoActionId, script);
			}
		}
	}

	private void _updateScript(long kaleoActionId, String script)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update KaleoAction set script = ? where kaleoActionId = ?")) {

			preparedStatement.setString(1, script);
			preparedStatement.setLong(2, kaleoActionId);

			preparedStatement.executeUpdate();
		}
	}

}