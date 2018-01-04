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

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
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
public class UpgradeDataProviderInstance extends UpgradeProcess {

	public UpgradeDataProviderInstance(
		DDMDataProviderTracker ddmDataProviderTracker,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer) {

		_ddmDataProviderTracker = ddmDataProviderTracker;
		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_ddmFormValuesJSONSerializer = ddmFormValuesJSONSerializer;
	}

	protected void addDefaultInputParameters(DDMFormValues ddmFormValues) {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			ddmFormValues, "inputParameters", null);

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "inputParameterLabel", StringPool.BLANK));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "inputParameterName", StringPool.BLANK));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "inputParameterRequired", "false"));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(ddmFormValues, "inputParameterType", "[]"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
	}

	protected void addDefaultOutputParameters(DDMFormValues ddmFormValues) {
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

		String outputParameterPath = createOutputPathValue(
			ddmFormValues.getDefaultLocale(), keyDDMFormFieldValue.getValue(),
			valueDDMFormFieldValue.getValue());

		ddmFormValues.addDDMFormFieldValue(
			createDefaultOutputParameters(ddmFormValues, outputParameterPath));
	}

	protected void addPaginationParameter(DDMFormValues ddmFormValues) {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		if (ddmFormFieldValuesMap.containsKey("pagination")) {
			return;
		}

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(ddmFormValues, "pagination", "true"));
	}

	protected void addStartEndParameters(DDMFormValues ddmFormValues) {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		if (ddmFormFieldValuesMap.containsKey("paginationStartParameterName") ||
			ddmFormFieldValuesMap.containsKey("paginationEndParameterName")) {

			return;
		}

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "paginationStartParameterName", "start"));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "paginationEndParameterName", "end"));
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
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

	protected DDMFormFieldValue createDefaultOutputParameters(
		DDMFormValues ddmFormValues, String outputParameterPath) {

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			ddmFormValues, "outputParameters", null);

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "outputParameterLabel",
				_DEFAULT_OUTPUT_PARAMETER_LABEL));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "outputParameterName",
				_DEFAULT_OUTPUT_PARAMETER_NAME));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "outputParameterPath", outputParameterPath));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "outputParameterType", "[\"list\"]"));

		return ddmFormFieldValue;
	}

	protected String createOutputPathValue(
		Locale locale, Value key, Value value) {

		StringBundler sb = new StringBundler(3);

		sb.append(key.getString(locale));
		sb.append(CharPool.SEMICOLON);
		sb.append(value.getString(locale));

		return sb.toString();
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
				long dataProviderInstanceId = rs.getLong(1);
				String dataProviderInstanceDefinition = rs.getString(2);
				String type = rs.getString(3);

				String newDefinition = upgradeDataProviderInstanceDefinition(
					dataProviderInstanceDefinition, type);

				ps2.setString(1, newDefinition);

				ps2.setLong(2, dataProviderInstanceId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected String upgradeDataProviderInstanceDefinition(
			String dataProviderInstanceDefinition, String type)
		throws Exception {

		DDMDataProvider ddmDataProvider =
			_ddmDataProviderTracker.getDDMDataProvider(type);

		DDMFormValues ddmFormValues =
			_ddmFormValuesJSONDeserializer.deserialize(
				DDMFormFactory.create(ddmDataProvider.getSettings()),
				dataProviderInstanceDefinition);

		addDefaultInputParameters(ddmFormValues);

		addDefaultOutputParameters(ddmFormValues);

		addPaginationParameter(ddmFormValues);

		addStartEndParameters(ddmFormValues);

		return _ddmFormValuesJSONSerializer.serialize(ddmFormValues);
	}

	private static final String _DEFAULT_OUTPUT_PARAMETER_LABEL =
		"Default Output";

	private static final String _DEFAULT_OUTPUT_PARAMETER_NAME =
		"Default-Output";

	private final DDMDataProviderTracker _ddmDataProviderTracker;
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;

}