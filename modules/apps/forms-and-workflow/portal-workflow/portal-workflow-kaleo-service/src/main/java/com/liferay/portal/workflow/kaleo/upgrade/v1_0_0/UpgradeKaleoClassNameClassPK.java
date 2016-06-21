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

package com.liferay.portal.workflow.kaleo.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Rafael Praxedes
 */
public class UpgradeKaleoClassNameClassPK extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeKaleoClassNameClassPK(
			"KaleoAction", KaleoNode.class.getName(), "kaleoNodeId");
		upgradeKaleoClassNameClassPK(
			"KaleoLog", KaleoNode.class.getName(), "kaleoNodeId");
		upgradeKaleoClassNameClassPK(
			"KaleoNotification", KaleoNode.class.getName(), "kaleoNodeId");
		upgradeKaleoClassNameClassPK(
			"KaleoTaskAssignment", KaleoTask.class.getName(), "kaleoTaskId");
	}

	protected void upgradeKaleoClassNameClassPK(
			String tableName, String kaleoClassName,
			String kaleoClassPKOldColumn)
		throws SQLException {

		StringBundler updateScriptSB = new StringBundler(11);

		updateScriptSB.append("update ");
		updateScriptSB.append(tableName);
		updateScriptSB.append(" set kaleoClassName = ?,");
		updateScriptSB.append("kaleoClassPK = ");
		updateScriptSB.append(kaleoClassPKOldColumn);
		updateScriptSB.append(" where kaleoClassName is null and ");
		updateScriptSB.append("kaleoClassPK is null and ");
		updateScriptSB.append(kaleoClassPKOldColumn);
		updateScriptSB.append(" is not null and ");
		updateScriptSB.append(kaleoClassPKOldColumn);
		updateScriptSB.append(" > 0 ");

		try (PreparedStatement ps = connection.prepareStatement(
				updateScriptSB.toString())) {

			ps.setString(1, kaleoClassName);

			ps.executeUpdate();
		}
	}

}