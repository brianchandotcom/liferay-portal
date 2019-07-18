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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_2_1;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Leonardo Barros
 */
public class UpgradeDDMStructureLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("DDMStructureLayout", "classNameId") &&
			hasColumn("DDMStructureLayout", "structureLayoutKey")) {

			StringBundler sb = new StringBundler(2);

			sb.append("select DDMStructureLayout.structureLayoutId from ");
			sb.append("DDMStructureLayout");

			StringBundler sb2 = new StringBundler(4);

			sb2.append("update DDMStructureLayout set classNameId = (select ");
			sb2.append("classNameId from ClassName_ where value = ");
			sb2.append("'com.liferay.dynamic.data.mapping.model.DDMStructure'");
			sb2.append("), structureLayoutKey = ? where structureLayoutId = ?");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString());
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sb2.toString())) {

				try (ResultSet rs = ps1.executeQuery()) {
					while (rs.next()) {
						ps2.setString(
							1,
							String.valueOf(increment()));
						ps2.setLong(2, rs.getLong(1));

						ps2.addBatch();
					}

					ps2.executeBatch();
				}
			}
		}
	}

}