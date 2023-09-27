/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class DDMFormFieldSettingsUpgradeProcess extends UpgradeProcess {

	public DDMFormFieldSettingsUpgradeProcess(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormSerializer ddmFormSerializer) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormSerializer = ddmFormSerializer;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructure.structureId, DDMStructure.definition ",
					"from DDLRecordSet inner join DDMStructure on ",
					"DDLRecordSet.DDMStructureId = DDMStructure.structureId ",
					"where DDLRecordSet.scope = ? and DDMStructure.definition ",
					"like ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"select structureVersionId, definition from " +
					"DDMStructureVersion where structureId = ?");
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			preparedStatement1.setInt(1, _SCOPE_FORMS);
			preparedStatement1.setString(2, "%ddmDataProviderInstanceId%");

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String definition = resultSet.getString(2);

					String newDefinition = _upgradeRecordSetStructure(
						definition);

					if (Objects.equals(definition, newDefinition)) {
						continue;
					}

					preparedStatement2.setString(1, newDefinition);

					long structureId = resultSet.getLong(1);

					preparedStatement2.setLong(2, structureId);

					preparedStatement2.addBatch();

					preparedStatement3.setLong(1, structureId);

					try (ResultSet resultSet2 =
							preparedStatement3.executeQuery()) {

						while (resultSet2.next()) {
							definition = resultSet2.getString("definition");

							newDefinition = _upgradeRecordSetStructure(
								definition);

							if (Objects.equals(definition, newDefinition)) {
								continue;
							}

							preparedStatement4.setString(1, newDefinition);

							long structureVersionId = resultSet2.getLong(
								"structureVersionId");

							preparedStatement4.setLong(2, structureVersionId);

							preparedStatement4.addBatch();
						}
					}
				}

				preparedStatement2.executeBatch();

				preparedStatement4.executeBatch();
			}
		}
	}

	private String _upgradeRecordSetStructure(String definition)
		throws Exception {

		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, definition);

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			Map<String, Object> properties = ddmFormField.getProperties();

			if (properties.containsKey("ddmDataProviderInstanceId")) {
				String ddmDataProviderInstanceId = GetterUtil.getString(
					properties.get("ddmDataProviderInstanceId"));

				ddmFormField.setProperty(
					"ddmDataProviderInstanceId",
					"[\"" + ddmDataProviderInstanceId + "\"]");

				ddmFormField.setProperty(
					"ddmDataProviderInstanceOutput", "[\"Default-Output\"]");
			}
		}

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	private static final int _SCOPE_FORMS = 2;

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;

}