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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Leonardo Barros
 */
public class UpgradeKaleoAction extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select kaleoActionId, script from KaleoAction where script " +
					"like '%WorkflowConstants.toStatus(%'");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long kaleoActionId = rs.getLong(1);

				String script = rs.getString(2);

				script = StringUtil.replace(
					script, "WorkflowConstants.toStatus(",
					"WorkflowConstants.getLabelStatus(");

				updateScript(kaleoActionId, script);
			}
		}
	}

	protected void updateScript(long kaleoActionId, String script)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update KaleoAction set script = ? where kaleoActionId = ?")) {

			ps.setString(1, script);
			ps.setLong(2, kaleoActionId);

			ps.executeUpdate();
		}
	}

}