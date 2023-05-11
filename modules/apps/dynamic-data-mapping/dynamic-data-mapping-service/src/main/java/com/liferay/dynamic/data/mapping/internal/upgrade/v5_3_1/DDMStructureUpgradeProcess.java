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

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_3_1;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Mikel Lorza
 */
public class DDMStructureUpgradeProcess extends UpgradeProcess {

	public DDMStructureUpgradeProcess(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormSerializer ddmFormSerializer, Language language) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormSerializer = ddmFormSerializer;
		_language = language;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeStructureDefinition();
		_upgradeStructureVersionDefinition();
	}

	private void _addLabelToFieldsGroup(
		Set<Locale> availableLocales, List<DDMFormField> ddmFormFields) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (Objects.equals(
					ddmFormField.getType(),
					DDMFormFieldTypeConstants.FIELDSET) &&
				!_isLabelSet(ddmFormField)) {

				ddmFormField.setLabel(
					new LocalizedValue() {
						{
							for (Locale locale : availableLocales) {
								addString(
									locale,
									_language.get(locale, "fields-group"));
							}
						}
					});
				ddmFormField.setLocalizable(true);
				ddmFormField.setProperty("labelAtStructureLevel", true);
				ddmFormField.setShowLabel(true);
			}

			if (ListUtil.isNotEmpty(ddmFormField.getNestedDDMFormFields())) {
				_addLabelToFieldsGroup(
					availableLocales, ddmFormField.getNestedDDMFormFields());
			}
		}
	}

	private String _addLabelToFieldsGroup(String dataDefinition)
		throws Exception {

		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, dataDefinition);

		_addLabelToFieldsGroup(
			ddmForm.getAvailableLocales(), ddmForm.getDDMFormFields());

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	private boolean _isLabelSet(DDMFormField ddmFormField) {
		LocalizedValue localizedValue = ddmFormField.getLabel();

		Map<Locale, String> values = localizedValue.getValues();

		for (String value : values.values()) {
			if (Validator.isNotNull(value)) {
				return true;
			}
		}

		return false;
	}

	private void _upgradeStructureDefinition() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select structureId, definition from DDMStructure where " +
					"classNameId = ? or classNameId = ? order by createDate");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?")) {

			preparedStatement1.setLong(
				1,
				PortalUtil.getClassNameId(_CLASS_NAME_DL_FILE_ENTRY_METADATA));
			preparedStatement1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setString(
						1,
						_addLabelToFieldsGroup(
							resultSet.getString("definition")));
					preparedStatement2.setLong(
						2, resultSet.getLong("structureId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeStructureVersionDefinition() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructure.structureKey,  ",
					"DDMStructureVersion.structureVersionId, ",
					"DDMStructureVersion.definition from DDMStructureVersion ",
					"inner join DDMStructure on DDMStructure.structureId = ",
					"DDMStructureVersion.structureId where ",
					"DDMStructure.classNameId = ? or DDMStructure.classNameId ",
					"= ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			preparedStatement1.setLong(
				1,
				PortalUtil.getClassNameId(_CLASS_NAME_DL_FILE_ENTRY_METADATA));
			preparedStatement1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setString(
						1,
						_addLabelToFieldsGroup(
							resultSet.getString("definition")));
					preparedStatement2.setLong(
						2, resultSet.getLong("structureVersionId"));
					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final String _CLASS_NAME_DL_FILE_ENTRY_METADATA =
		"com.liferay.document.library.kernel.model.DLFileEntryMetadata";

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		"com.liferay.journal.model.JournalArticle";

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;
	private final Language _language;

}