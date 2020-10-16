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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_9_1;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Set;

/**
 * @author Marcos Martins
 */
public class UpgradeDDMStructure extends UpgradeProcess {

	public UpgradeDDMStructure(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormSerializer ddmFormSerializer) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormSerializer = ddmFormSerializer;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeDDMStructureVersion();

		_upgradeDDMStructure();
	}

	private void _updateDDMFormFieldOptionReferences(
		DDMFormFieldOptions ddmFormFieldOptions) {

		if (ddmFormFieldOptions == null) {
			return;
		}

		Set<String> ddmFormFieldOptionsValues =
			ddmFormFieldOptions.getOptionsValues();

		ddmFormFieldOptionsValues.forEach(
			ddmFormFieldOptionsValue -> ddmFormFieldOptions.addOptionReference(
				ddmFormFieldOptionsValue, ddmFormFieldOptionsValue));
	}

	private void _updateDDMFormFieldReference(DDMFormField ddmFormField) {
		ddmFormField.setProperty("fieldReference", ddmFormField.getName());

		_updateDDMFormFieldOptionReferences(
			ddmFormField.getDDMFormFieldOptions());
		_updateDDMFormFieldOptionReferences(
			(DDMFormFieldOptions)ddmFormField.getProperty("columns"));
		_updateDDMFormFieldOptionReferences(
			(DDMFormFieldOptions)ddmFormField.getProperty("rows"));

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.forEach(
			nestedDDMFormField -> _updateDDMFormFieldReference(
				nestedDDMFormField));
	}

	private void _upgradeDDMStructure() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("select DDMStructure.structureId, ");
		sb.append("DDMStructureVersion.definition from DDMStructure inner ");
		sb.append("join DDMStructureVersion on DDMStructure.structureid = ");
		sb.append("DDMStructureVersion.structureid where ");
		sb.append("DDMStructure.version = DDMStructureVersion.version and ");
		sb.append("DDMStructure.classNameId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?")) {

			ps1.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString("definition");

					ps2.setString(1, definition);

					long structureId = rs.getLong("structureId");

					ps2.setLong(2, structureId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private void _upgradeDDMStructureVersion() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select DDMStructureVersion.structureVersionId, ");
		sb.append("DDMStructureVersion.definition from DDMStructure inner ");
		sb.append("join DDMStructureVersion on DDMStructure.structureId = ");
		sb.append("DDMStructureVersion.structureId where ");
		sb.append("DDMStructure.classNameId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			ps1.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long structureVersionId = rs.getLong("structureVersionId");

					ps2.setString(
						1,
						_upgradeDDMStructureVersionDefinition(
							rs.getString("definition")));

					ps2.setLong(2, structureVersionId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private String _upgradeDDMStructureVersionDefinition(String definition)
		throws Exception {

		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, definition);

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		ddmFormFields.forEach(
			ddmFormField -> _updateDDMFormFieldReference(ddmFormField));

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;

}