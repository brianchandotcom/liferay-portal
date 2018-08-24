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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;

import java.sql.PreparedStatement;

/**
 * @author Rafael Praxedes
 */
public class UpgradeKaleoClassNameAndKaleoClassPK extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeKaleoClassNameAndKaleoClassPK(
			"KaleoAction", "kaleoNodeId", KaleoNode.class.getName());
		upgradeKaleoClassNameAndKaleoClassPK(
			"KaleoLog", "kaleoNodeId", KaleoNode.class.getName());
		upgradeKaleoClassNameAndKaleoClassPK(
			"KaleoNotification", "kaleoNodeId", KaleoNode.class.getName());
		upgradeKaleoClassNameAndKaleoClassPK(
			"KaleoTaskAssignment", "kaleoTaskId", KaleoTask.class.getName());
	}

	protected void upgradeKaleoClassNameAndKaleoClassPK(
			String tableName, String columnName, String kaleoClassName)
		throws Exception {

		if (!hasColumn(tableName, columnName)) {
			return;
		}

		StringBundler sb = new StringBundler(10);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set kaleoClassName = ?, kaleoClassPK = ");
		sb.append(columnName);
		sb.append(" where kaleoClassName is null and kaleoClassPK is null ");
		sb.append("and ");
		sb.append(columnName);
		sb.append(" is not null and ");
		sb.append(columnName);
		sb.append(" > 0 ");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setString(1, kaleoClassName);

			ps.executeUpdate();
		}
	}

}