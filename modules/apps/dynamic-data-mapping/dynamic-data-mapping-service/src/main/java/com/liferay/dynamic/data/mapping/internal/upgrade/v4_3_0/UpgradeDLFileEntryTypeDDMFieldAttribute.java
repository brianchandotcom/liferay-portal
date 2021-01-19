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

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Alicia García
 */
public class UpgradeDLFileEntryTypeDDMFieldAttribute extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBuilder sb = new StringBuilder(8);

		sb.append("select DDMField.storageId, DDMField.fieldName ");
		sb.append("from DLFileEntryType inner join DDMStructureVersion on ");
		sb.append("DDMStructureVersion.structureId = ");
		sb.append("DLFileEntryType.dataDefinitionId ");
		sb.append("inner join DDMField on ");
		sb.append("DDMStructureVersion.structureVersionId = ");
		sb.append("DDMField.structureVersionId and ");
		sb.append("DDMField.fieldType like ? ");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb.toString())) {

			PreparedStatement ps2 = connection.prepareStatement(
				"select fieldAttributeId, smallAttributeValue from " +
					"DDMFieldAttribute where storageId = ? and " +
						"smallAttributeValue in (? , ?) ");

			PreparedStatement ps3 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update DDMFieldAttribute set smallAttributeValue = ? " +
						"where fieldAttributeId = ? "));

			ps1.setString(1, "checkbox");

			try (ResultSet rs1 = ps1.executeQuery()) {
				while (rs1.next()) {
					ps2.setLong(1, rs1.getLong(1));
					ps2.setString(2, Boolean.TRUE.toString());
					ps2.setString(3, Boolean.FALSE.toString());

					try (ResultSet rs2 = ps2.executeQuery()) {
						while (rs2.next()) {
							if (Objects.equals(
									Boolean.TRUE.toString(),
									rs2.getString(2))) {

								ps3.setString(
									1,
									Arrays.toString(
										new String[] {rs1.getString(2)}));
							}
							else {
								ps3.setString(
									1, Arrays.toString(new String[0]));
							}

							ps3.setLong(2, rs2.getLong(1));

							ps3.addBatch();
						}
					}
				}

				ps3.executeBatch();
			}
		}
	}

}