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
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Set;

/**
 * @author Marcos Martins
 */
public class DDMStructureUpgradeProcess extends UpgradeProcess {

	public DDMStructureUpgradeProcess(
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

	private void _upgradeDDMFormFieldOptionsReferences(
		DDMFormFieldOptions ddmFormFieldOptions) {

		if (ddmFormFieldOptions == null) {
			return;
		}

		Set<String> ddmFormFieldOptionsValues =
			ddmFormFieldOptions.getOptionsValues();

		ddmFormFieldOptionsValues.forEach(
			ddmFormFieldOptionsValue -> ddmFormFieldOptions.addOptionReference(
				ddmFormFieldOptionsValue,
				ddmFormFieldOptionsValue.replaceAll(
					"([\\p{Punct}|\\p{Space}$]|_)+", StringPool.BLANK)));
	}

	private void _upgradeDDMFormFieldReference(DDMFormField ddmFormField) {
		ddmFormField.setFieldReference(ddmFormField.getName());

		if (!StringUtil.equals(ddmFormField.getType(), "fieldset")) {
			_upgradeDDMFormFieldOptionsReferences(
				ddmFormField.getDDMFormFieldOptions());
			_upgradeDDMFormFieldOptionsReferences(
				(DDMFormFieldOptions)ddmFormField.getProperty("columns"));
			_upgradeDDMFormFieldOptionsReferences(
				(DDMFormFieldOptions)ddmFormField.getProperty("rows"));
		}

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.forEach(
			nestedDDMFormField -> _upgradeDDMFormFieldReference(
				nestedDDMFormField));
	}

	private void _upgradeDDMStructure() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructure.structureId, ",
					"DDMStructureVersion.definition from DDMStructure inner ",
					"join DDMStructureVersion on DDMStructure.structureId = ",
					"DDMStructureVersion.structureId where ",
					"DDMStructure.version = DDMStructureVersion.version and ",
					"DDMStructure.classNameId in (?, ?)"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?")) {

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));
			preparedStatement1.setLong(
				2, PortalUtil.getClassNameId(JournalArticle.class.getName()));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String definition = resultSet.getString("definition");

					preparedStatement2.setString(1, definition);

					long structureId = resultSet.getLong("structureId");

					preparedStatement2.setLong(2, structureId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeDDMStructureVersion() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructureVersion.structureVersionId, ",
					"DDMStructureVersion.definition from DDMStructure inner ",
					"join DDMStructureVersion on DDMStructure.structureId = ",
					"DDMStructureVersion.structureId where ",
					"DDMStructure.classNameId = ? or DDMStructure.classNameId ",
					"= ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));
			preparedStatement1.setLong(
				2, PortalUtil.getClassNameId(JournalArticle.class.getName()));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long structureVersionId = resultSet.getLong(
						"structureVersionId");

					preparedStatement2.setString(
						1,
						_upgradeDDMStructureVersionDefinition(
							resultSet.getString("definition")));
					preparedStatement2.setLong(2, structureVersionId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private String _upgradeDDMStructureVersionDefinition(String definition)
		throws Exception {

		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, definition);

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		ddmFormFields.forEach(
			ddmFormField -> _upgradeDDMFormFieldReference(ddmFormField));

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;

}