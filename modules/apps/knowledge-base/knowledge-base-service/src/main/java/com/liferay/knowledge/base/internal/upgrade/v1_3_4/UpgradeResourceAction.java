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

package com.liferay.knowledge.base.internal.upgrade.v1_3_4;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adolfo Pérez
 */
public class UpgradeResourceAction extends UpgradeProcess {

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

	private boolean _hasViewFeedbackResourceAction() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select count(*) from ResourceAction where actionId = ?");

			ps.setString(1, _ACTION_ID_VIEW_KB_FEEDBACK);

			rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					return true;
				}

				return false;
			}

			return false;
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	private static final String _ACTION_ID_VIEW_KB_FEEDBACK =
		"VIEW_KB_FEEDBACK";

}