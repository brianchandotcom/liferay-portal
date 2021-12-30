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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0;

import com.liferay.dynamic.data.mapping.internal.util.ExpressionParameterValueExtractor;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Inácio Nery
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
		_upgradeDDMStructureDefinition();
		_upgradeDDMStructureVersionDefinition();
	}

	private String _convertExpression(
		Map<String, DDMFormField> ddmFormFieldsMap,
		String visibilityExpression) {

		StringBundler sb1 = new StringBundler();

		List<String> parameterValues =
			ExpressionParameterValueExtractor.extractParameterValues(
				visibilityExpression);

		for (String parameterValue : parameterValues) {
			String unquotedParameterValue = StringUtil.unquote(parameterValue);

			if (!ddmFormFieldsMap.containsKey(unquotedParameterValue)) {
				continue;
			}

			int index = visibilityExpression.indexOf(parameterValue);

			sb1.append(visibilityExpression.substring(0, index));

			sb1.append(
				StringBundler.concat(
					"getValue(", StringUtil.quote(unquotedParameterValue),
					")"));

			visibilityExpression = visibilityExpression.substring(
				index + parameterValue.length());
		}

		sb1.append(visibilityExpression);

		return sb1.toString();
	}

	private String _updateDefinition(String definition) throws Exception {
		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, definition);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			String visibilityExpression =
				ddmFormField.getVisibilityExpression();

			if (Validator.isNull(visibilityExpression)) {
				continue;
			}

			visibilityExpression = _convertExpression(
				ddmFormFieldsMap, visibilityExpression);

			DDMFormRule ddmFormRule = new DDMFormRule(
				Arrays.asList(
					"setVisible('" + ddmFormField.getName() + "', true)"),
				visibilityExpression);

			ddmFormRules.add(ddmFormRule);

			ddmFormField.setVisibilityExpression(StringPool.BLANK);
		}

		ddmForm.setDDMFormRules(ddmFormRules);

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	private void _upgradeDDMStructureDefinition() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select DDMStructure.definition, DDMStructure.structureId " +
					"from DDMStructure");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String definition = resultSet.getString(1);
					long structureId = resultSet.getLong(2);

					String newDefinition = _updateDefinition(definition);

					preparedStatement2.setString(1, newDefinition);

					preparedStatement2.setLong(2, structureId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeDDMStructureVersionDefinition() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructureVersion.definition, ",
					"DDMStructureVersion.structureVersionId from ",
					"DDMStructureVersion"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String definition = resultSet.getString(1);
					long structureVersionId = resultSet.getLong(2);

					String newDefinition = _updateDefinition(definition);

					preparedStatement2.setString(1, newDefinition);

					preparedStatement2.setLong(2, structureVersionId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;

}