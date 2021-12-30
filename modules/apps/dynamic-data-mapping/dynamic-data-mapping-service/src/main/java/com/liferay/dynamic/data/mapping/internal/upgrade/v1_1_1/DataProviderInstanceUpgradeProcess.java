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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1;

import com.liferay.dynamic.data.mapping.data.provider.settings.DDMDataProviderSettingsProvider;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesSerializeUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class DataProviderInstanceUpgradeProcess extends UpgradeProcess {

	public DataProviderInstanceUpgradeProcess(
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
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select dataProviderInstanceId, definition, type_ from " +
					"DDMDataProviderInstance");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMDataProviderInstance set definition = ? where " +
						"dataProviderInstanceId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String dataProviderInstanceDefinition = resultSet.getString(2);
				String type = resultSet.getString(3);

				String newDefinition = _upgradeDataProviderInstanceDefinition(
					dataProviderInstanceDefinition, type);

				preparedStatement2.setString(1, newDefinition);

				long dataProviderInstanceId = resultSet.getLong(1);

				preparedStatement2.setLong(2, dataProviderInstanceId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private void _addDefaultInputParameters(DDMFormValues ddmFormValues) {
		DDMFormFieldValue ddmFormFieldValue = _createDDMFormFieldValue(
			ddmFormValues, "inputParameters", null);

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "inputParameterLabel", StringPool.BLANK));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "inputParameterName", StringPool.BLANK));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "inputParameterRequired", "false"));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "inputParameterType", "[]"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
	}

	private void _addDefaultOutputParameters(DDMFormValues ddmFormValues) {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		if (!ddmFormFieldValuesMap.containsKey("key") ||
			!ddmFormFieldValuesMap.containsKey("value")) {

			return;
		}

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"key");

		DDMFormFieldValue keyDDMFormFieldValue = ddmFormFieldValues.get(0);

		ddmFormFieldValues = ddmFormFieldValuesMap.get("value");

		DDMFormFieldValue valueDDMFormFieldValue = ddmFormFieldValues.get(0);

		String outputParameterPath = _createOutputPathValue(
			ddmFormValues.getDefaultLocale(), keyDDMFormFieldValue.getValue(),
			valueDDMFormFieldValue.getValue());

		ddmFormValues.addDDMFormFieldValue(
			_createDefaultOutputParameters(ddmFormValues, outputParameterPath));
	}

	private void _addPaginationParameter(DDMFormValues ddmFormValues) {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		if (ddmFormFieldValuesMap.containsKey("pagination")) {
			return;
		}

		ddmFormValues.addDDMFormFieldValue(
			_createDDMFormFieldValue(ddmFormValues, "pagination", "false"));
	}

	private void _addStartEndParameters(DDMFormValues ddmFormValues) {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		if (ddmFormFieldValuesMap.containsKey("paginationStartParameterName") ||
			ddmFormFieldValuesMap.containsKey("paginationEndParameterName")) {

			return;
		}

		ddmFormValues.addDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "paginationStartParameterName", "start"));
		ddmFormValues.addDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "paginationEndParameterName", "end"));
	}

	private DDMFormFieldValue _createDDMFormFieldValue(
		DDMFormValues ddmFormValues, String name, String value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setDDMFormValues(ddmFormValues);
		ddmFormFieldValue.setInstanceId(StringUtil.randomString());
		ddmFormFieldValue.setName(name);

		if (Validator.isNotNull(value)) {
			ddmFormFieldValue.setValue(new UnlocalizedValue(value));
		}

		return ddmFormFieldValue;
	}

	private DDMFormFieldValue _createDefaultOutputParameters(
		DDMFormValues ddmFormValues, String outputParameterPath) {

		DDMFormFieldValue ddmFormFieldValue = _createDDMFormFieldValue(
			ddmFormValues, "outputParameters", null);

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "outputParameterLabel",
				_DEFAULT_OUTPUT_PARAMETER_LABEL));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "outputParameterName",
				_DEFAULT_OUTPUT_PARAMETER_NAME));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "outputParameterPath", outputParameterPath));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(
				ddmFormValues, "outputParameterType", "[\"list\"]"));

		return ddmFormFieldValue;
	}

	private String _createOutputPathValue(
		Locale locale, Value key, Value value) {

		return StringBundler.concat(
			key.getString(locale), CharPool.SEMICOLON, value.getString(locale));
	}

	private String _upgradeDataProviderInstanceDefinition(
			String dataProviderInstanceDefinition, String type)
		throws Exception {

		DDMDataProviderSettingsProvider ddmDataProviderSettingsProvider =
			_ddmDataProviderSettingsProviderServiceTracker.getService(type);

		DDMFormValues ddmFormValues = DDMFormValuesDeserializeUtil.deserialize(
			dataProviderInstanceDefinition,
			DDMFormFactory.create(
				ddmDataProviderSettingsProvider.getSettings()),
			_ddmFormValuesDeserializer);

		_addDefaultInputParameters(ddmFormValues);

		_addDefaultOutputParameters(ddmFormValues);

		_addPaginationParameter(ddmFormValues);

		_addStartEndParameters(ddmFormValues);

		return DDMFormValuesSerializeUtil.serialize(
			ddmFormValues, _ddmFormValuesSerializer);
	}

	private static final String _DEFAULT_OUTPUT_PARAMETER_LABEL =
		"Default Output";

	private static final String _DEFAULT_OUTPUT_PARAMETER_NAME =
		"Default-Output";

	private final ServiceTrackerMap<String, DDMDataProviderSettingsProvider>
		_ddmDataProviderSettingsProviderServiceTracker;
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer;
	private final DDMFormValuesSerializer _ddmFormValuesSerializer;

}