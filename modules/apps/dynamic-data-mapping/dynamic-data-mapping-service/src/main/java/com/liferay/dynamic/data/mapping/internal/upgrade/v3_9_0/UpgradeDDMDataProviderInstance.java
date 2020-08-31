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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_9_0;

import com.liferay.dynamic.data.mapping.data.provider.settings.DDMDataProviderSettingsProvider;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesSerializeUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcos Martins
 */
public class UpgradeDDMDataProviderInstance extends UpgradeProcess {

	public UpgradeDDMDataProviderInstance(
		ServiceTrackerMap<String, DDMDataProviderSettingsProvider>
			ddmDataProviderSettingsProviderServiceTracker,
		DDMFormValuesDeserializer ddmFormValuesDeserializer,
		DDMFormValuesSerializer ddmFormValuesSerializer) {

		_ddmDataProviderSettingsProviderServiceTracker =
			ddmDataProviderSettingsProviderServiceTracker;
		_ddmFormValuesDeserializer = ddmFormValuesDeserializer;
		_ddmFormValuesSerializer = ddmFormValuesSerializer;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select dataProviderInstanceId, definition, type_ from " +
					"DDMDataProviderInstance");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMDataProviderInstance set definition = ? where " +
						"dataProviderInstanceId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				String dataProviderInstanceDefinition = rs.getString(2);
				String type = rs.getString(3);

				ps2.setString(
					1,
					_upgradeDataProviderInstanceDefinition(
						dataProviderInstanceDefinition, type));

				long dataProviderInstanceId = rs.getLong(1);

				ps2.setLong(2, dataProviderInstanceId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private void _updateNestedDDMFormFieldValue(
		DDMFormFieldValue nestedDDMFormFieldValue) {

		Value value = nestedDDMFormFieldValue.getValue();

		Map<Locale, String> valuesMap = value.getValues();

		for (Map.Entry<Locale, String> valueEntry : valuesMap.entrySet()) {
			String valueString = valueEntry.getValue();

			String[] valueStringParts = valueString.split(StringPool.SEMICOLON);

			if (valueStringParts.length > 1) {
				String outputPathType = valueStringParts[1];
				String outputPathValue = valueStringParts[0];

				if (outputPathType.equals("List")) {
					StringBundler sb = new StringBundler(3);

					sb.append(StringPool.DOLLAR);
					sb.append(StringPool.DOUBLE_PERIOD);
					sb.append(outputPathValue);

					outputPathValue = sb.toString();
				}

				valuesMap.put(valueEntry.getKey(), outputPathValue);
			}
		}
	}

	private String _upgradeDataProviderInstanceDefinition(
			String dataProviderInstanceDefinition, String type)
		throws Exception {

		DDMDataProviderSettingsProvider ddmDataProviderSettingsProvider =
			_ddmDataProviderSettingsProviderServiceTracker.getService(type);

		DDMForm ddmForm = DDMFormFactory.create(
			ddmDataProviderSettingsProvider.getSettings());

		DDMFormValues ddmFormValues = DDMFormValuesDeserializeUtil.deserialize(
			dataProviderInstanceDefinition, ddmForm,
			_ddmFormValuesDeserializer);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(false);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"outputParameters");

		if (ddmFormFieldValues != null) {
			Map<String, DDMFormField> ddmFormFieldsMap =
				ddmForm.getDDMFormFieldsMap(false);

			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				"outputParameters");

			Map<String, DDMFormField> nestedDDMFormFieldsMap =
				ddmFormField.getNestedDDMFormFieldsMap();

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				List<DDMFormFieldValue> newNestedDDMFormFieldValues =
					new ArrayList<>();

				for (DDMFormFieldValue nestedDDMFormFieldValue :
						ddmFormFieldValue.getNestedDDMFormFieldValues()) {

					String nestedDDMFormFieldValueName =
						nestedDDMFormFieldValue.getName();

					DDMFormField nestedDDMFormField =
						nestedDDMFormFieldsMap.get(nestedDDMFormFieldValueName);

					if (nestedDDMFormField == null) {
						continue;
					}

					if (nestedDDMFormFieldValueName.equals(
							"outputParameterPath")) {

						_updateNestedDDMFormFieldValue(nestedDDMFormFieldValue);
					}

					newNestedDDMFormFieldValues.add(nestedDDMFormFieldValue);
				}

				ddmFormFieldValue.setNestedDDMFormFields(
					newNestedDDMFormFieldValues);
			}
		}

		return DDMFormValuesSerializeUtil.serialize(
			ddmFormValues, _ddmFormValuesSerializer);
	}

	private final ServiceTrackerMap<String, DDMDataProviderSettingsProvider>
		_ddmDataProviderSettingsProviderServiceTracker;
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer;
	private final DDMFormValuesSerializer _ddmFormValuesSerializer;

}