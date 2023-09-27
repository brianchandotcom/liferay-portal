/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_3_4;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public class ResourceActionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (_hasViewFeedbackResourceAction()) {
			runSQL(
				"delete from ResourceAction where actionId = '" +
					KBActionKeys.VIEW_SUGGESTIONS + "'");

			runSQL(
				StringBundler.concat(
					"update ResourceAction set actionId = '",
					KBActionKeys.VIEW_SUGGESTIONS, "' where actionId = '",
					_ACTION_ID_VIEW_KB_FEEDBACK, "'"));
		}
	}

	private boolean _hasViewFeedbackResourceAction() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from ResourceAction where actionId = ?")) {

			preparedStatement.setString(1, _ACTION_ID_VIEW_KB_FEEDBACK);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					if (resultSet.getInt(1) > 0) {
						return true;
					}

					return false;
				}

				return false;
			}
		}
	}

	private static final String _ACTION_ID_VIEW_KB_FEEDBACK =
		"VIEW_KB_FEEDBACK";

}