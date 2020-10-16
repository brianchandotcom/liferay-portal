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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_1;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Sam Ziemer
 */
public class UpgradeDDMStructureEmptyValidation extends UpgradeProcess {

	public UpgradeDDMStructureEmptyValidation(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormSerializer ddmFormSerializer) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormSerializer = ddmFormSerializer;
	}

	@Override
	public void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select structureId, definition from DDMStructure where " +
					"classNameId = ? and definition like '%validation%'");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement ps3 = connection.prepareStatement(
				"select structureVersionId, definition from " +
					"DDMStructureVersion where structureId = ?");
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(
					"com.liferay.dynamic.data.lists.model.DDLRecordSet"));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString("definition");

					String newDefinition = _updateDDMFormFieldValidation(
						definition);

					if (newDefinition.equals(definition)) {
						continue;
					}

					ps2.setString(1, newDefinition);
					ps2.setLong(2, rs.getLong("structureId"));

					ps2.addBatch();

					ps3.setLong(1, rs.getLong("structureId"));

					try (ResultSet rs2 = ps3.executeQuery()) {
						while (rs2.next()) {
							definition = rs2.getString("definition");

							newDefinition = _updateDDMFormFieldValidation(
								definition);

							if (newDefinition.equals(definition)) {
								continue;
							}

							ps4.setString(1, newDefinition);
							ps4.setLong(2, rs2.getLong("structureVersionId"));

							ps4.addBatch();
						}
					}
				}

				ps2.executeBatch();

				ps4.executeBatch();
			}
		}
	}

	private String _updateDDMFormFieldValidation(String definition)
		throws Exception {

		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, definition);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			DDMFormFieldValidation ddmFormFieldValidation =
				ddmFormField.getDDMFormFieldValidation();

			if (ddmFormFieldValidation != null) {
				DDMFormFieldValidationExpression
					ddmFormFieldValidationExpression =
						ddmFormFieldValidation.
							getDDMFormFieldValidationExpression();

				if (Validator.isNull(
						ddmFormFieldValidationExpression.getName()) &&
					Validator.isNull(
						ddmFormFieldValidationExpression.getValue())) {

					ddmFormField.setDDMFormFieldValidation(null);
				}
			}
		}

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;

}