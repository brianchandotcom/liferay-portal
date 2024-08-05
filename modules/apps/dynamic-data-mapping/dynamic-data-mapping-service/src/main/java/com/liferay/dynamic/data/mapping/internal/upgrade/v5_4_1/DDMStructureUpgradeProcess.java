/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_4_1;

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

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author István András Dézsi
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
		_upgradeDDMStructure();
		_upgradeDDMStructureVersion();
	}

	private void _setProperty(
		Set<Locale> availableLocales, DDMFormField ddmFormField,
		String propertyName, String propertyValue) {

		LocalizedValue localizedValue =
			(LocalizedValue)ddmFormField.getProperty(propertyName);

		if (localizedValue != null) {
			Map<Locale, String> values = localizedValue.getValues();

			for (String value : values.values()) {
				if (Validator.isNotNull(value)) {
					return;
				}
			}
		}

		ddmFormField.setProperty(
			propertyName,
			new LocalizedValue() {
				{
					for (Locale locale : availableLocales) {
						addString(locale, _language.get(locale, propertyValue));
					}
				}
			});
	}

	private void _upgradeDDMStructure() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select ctCollectionId, structureId, definition from " +
						"DDMStructure where classNameId = ?");
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"ctCollectionId = ? and structureId = ?")) {

			_upgradeDDMStructureDefinition(
				"structureId", selectPreparedStatement,
				updatePreparedStatement);
		}
	}

	private void _upgradeDDMStructureDefinition(
			String idColumnName, PreparedStatement selectPreparedStatement,
			PreparedStatement updatePreparedStatement)
		throws Exception {

		selectPreparedStatement.setLong(
			1, PortalUtil.getClassNameId(_CLASS_NAME_DDM_FORM_INSTANCE));

		try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
			while (resultSet.next()) {
				DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
					_ddmFormDeserializer, resultSet.getString("definition"));

				ListUtil.isNotEmptyForEach(
					ddmForm.getDDMFormFields(),
					ddmFormField -> _upgradeRequireConfirmationProperties(
						ddmForm.getAvailableLocales(), ddmFormField));

				updatePreparedStatement.setString(
					1,
					DDMFormSerializeUtil.serialize(
						ddmForm, _ddmFormSerializer));

				updatePreparedStatement.setLong(
					2, resultSet.getLong("ctCollectionId"));

				updatePreparedStatement.setLong(
					3, resultSet.getLong(idColumnName));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}
	}

	private void _upgradeDDMStructureVersion() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select DDMStructureVersion.ctCollectionId, ",
						"DDMStructureVersion.structureVersionId, ",
						"DDMStructureVersion.definition from ",
						"DDMStructureVersion inner join DDMStructure on ",
						"DDMStructure.structureId = ",
						"DDMStructureVersion.structureId where ",
						"DDMStructure.classNameId = ?"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"ctCollectionId = ? and structureVersionId = ?")) {

			_upgradeDDMStructureDefinition(
				"structureVersionId", selectPreparedStatement,
				updatePreparedStatement);
		}
	}

	private void _upgradeRequireConfirmationProperties(
		Set<Locale> availableLocales, DDMFormField ddmFormField) {

		ListUtil.isNotEmptyForEach(
			ddmFormField.getNestedDDMFormFields(),
			nestedDDMFormField -> _upgradeRequireConfirmationProperties(
				availableLocales, nestedDDMFormField));

		if (!Objects.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.TEXT) &&
			!Objects.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.NUMERIC)) {

			return;
		}

		_setProperty(
			availableLocales, ddmFormField, "confirmationErrorMessage",
			"the-information-does-not-match");
		_setProperty(
			availableLocales, ddmFormField, "confirmationLabel", "confirm");
	}

	private static final String _CLASS_NAME_DDM_FORM_INSTANCE =
		"com.liferay.dynamic.data.mapping.model.DDMFormInstance";

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;
	private final Language _language;

}