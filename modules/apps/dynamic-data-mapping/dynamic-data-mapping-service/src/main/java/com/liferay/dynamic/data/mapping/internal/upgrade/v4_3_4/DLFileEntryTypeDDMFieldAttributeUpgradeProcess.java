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

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_4;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.NumberFormat;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Alicia García
 */
public class DLFileEntryTypeDDMFieldAttributeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateCheckboxFieldType();
		_updateNumericFieldType();
	}

	private void _updateCheckboxFieldType() throws Exception {
		StringBundler sb = new StringBundler(8);

		sb.append("select DDMField.storageId, DDMField.fieldName from ");
		sb.append("DLFileEntryType inner join DDMStructureLink on ");
		sb.append("DDMStructureLink.classPK = ");
		sb.append("DLFileEntryType.fileEntryTypeId inner join ");
		sb.append("DDMStructureVersion on DDMStructureVersion.structureId = ");
		sb.append("DDMStructureLink.structureId inner join DDMField on ");
		sb.append("DDMStructureVersion.structureVersionId = ");
		sb.append("DDMField.structureVersionId and DDMField.fieldType like ? ");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb.toString())) {

			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select fieldAttributeId, smallAttributeValue from " +
					"DDMFieldAttribute where storageId = ? and " +
						"smallAttributeValue in (? , ?) ");

			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update DDMFieldAttribute set smallAttributeValue = " +
							"? where fieldAttributeId = ? "));

			preparedStatement1.setString(1, "%checkbox%");

			try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
				while (resultSet1.next()) {
					preparedStatement2.setLong(1, resultSet1.getLong(1));
					preparedStatement2.setString(2, Boolean.TRUE.toString());
					preparedStatement2.setString(3, Boolean.FALSE.toString());

					try (ResultSet resultSet2 =
							preparedStatement2.executeQuery()) {

						while (resultSet2.next()) {
							if (Objects.equals(
									Boolean.TRUE.toString(),
									resultSet2.getString(2))) {

								preparedStatement3.setString(
									1,
									Arrays.toString(
										new String[] {
											resultSet1.getString(2)
										}));
							}
							else {
								preparedStatement3.setString(
									1, Arrays.toString(new String[0]));
							}

							preparedStatement3.setLong(
								2, resultSet2.getLong(1));

							preparedStatement3.addBatch();
						}
					}
				}

				preparedStatement3.executeBatch();
			}
		}
	}

	private void _updateNumericFieldType() throws Exception {
		StringBundler sb = new StringBundler(8);

		sb.append("select DDMField.storageId, DDMField.fieldId from ");
		sb.append("DLFileEntryType inner join DDMStructureLink on ");
		sb.append("DDMStructureLink.classPK = ");
		sb.append("DLFileEntryType.fileEntryTypeId inner join ");
		sb.append("DDMStructureVersion on DDMStructureVersion.structureId = ");
		sb.append("DDMStructureLink.structureId inner join DDMField on ");
		sb.append("DDMStructureVersion.structureVersionId = ");
		sb.append("DDMField.structureVersionId and DDMField.fieldType like ? ");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb.toString())) {

			sb = new StringBundler(4);

			sb.append("select fieldAttributeId, languageId, ");
			sb.append("smallAttributeValue from DDMFieldAttribute where ");
			sb.append("storageId = ? and fieldId = ? and (attributeName is ");
			sb.append("null or attributeName = '') ");

			PreparedStatement preparedStatement2 = connection.prepareStatement(
				sb.toString());

			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update DDMFieldAttribute set smallAttributeValue = " +
							"? where fieldAttributeId = ? "));

			preparedStatement1.setString(1, "numeric");

			try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
				while (resultSet1.next()) {
					preparedStatement2.setLong(1, resultSet1.getLong(1));
					preparedStatement2.setLong(2, resultSet1.getLong(2));

					try (ResultSet resultSet2 =
							preparedStatement2.executeQuery()) {

						while (resultSet2.next()) {
							String languageId = resultSet2.getString(2);

							Locale locale = LocaleUtil.fromLanguageId(
								languageId);

							NumberFormat numberFormat =
								NumberFormat.getNumberInstance(locale);

							numberFormat.setGroupingUsed(true);

							String valueString = resultSet2.getString(3);

							if (Validator.isNull(valueString)) {
								preparedStatement3.setString(1, null);
							}
							else {
								Number number = numberFormat.parse(valueString);

								numberFormat.setGroupingUsed(false);

								preparedStatement3.setString(
									1, numberFormat.format(number));
							}

							preparedStatement3.setLong(
								2, resultSet2.getLong(1));

							preparedStatement3.addBatch();
						}
					}
				}

				preparedStatement3.executeBatch();
			}
		}
	}

}