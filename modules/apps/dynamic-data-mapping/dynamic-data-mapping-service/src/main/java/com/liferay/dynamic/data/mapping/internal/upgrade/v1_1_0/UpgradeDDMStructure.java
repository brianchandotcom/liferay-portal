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
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Inácio Nery
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
		upgradeDDMStructureDefinition();
		upgradeDDMStructureVersionDefinition();
	}

	protected String updateDefinition(String definition) {
		DDMFormDeserializerDeserializeRequest.Builder deserializerBuilder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				definition);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(deserializerBuilder.build());

		DDMForm ddmForm = ddmFormDeserializerDeserializeResponse.getDDMForm();

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
				ddmFormFieldsMap.values(), visibilityExpression);

			DDMFormRule ddmFormRule = new DDMFormRule(
				Arrays.asList(
					"setVisible('" + ddmFormField.getName() + "', true)"),
				visibilityExpression);

			ddmFormRules.add(ddmFormRule);

			ddmFormField.setVisibilityExpression(StringPool.BLANK);
		}

		ddmForm.setDDMFormRules(ddmFormRules);

		DDMFormSerializerSerializeRequest.Builder serializerBuilder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(serializerBuilder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	protected void upgradeDDMStructureDefinition() throws Exception {
		StringBundler sb = new StringBundler(2);

		sb.append("select DDMStructure.definition, DDMStructure.structureId ");
		sb.append("from DDMStructure");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?")) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString(1);
					long structureId = rs.getLong(2);

					String newDefinition = updateDefinition(definition);

					ps2.setString(1, newDefinition);

					ps2.setLong(2, structureId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected void upgradeDDMStructureVersionDefinition() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select DDMStructureVersion.definition, ");
		sb.append("DDMStructureVersion.structureVersionId from ");
		sb.append("DDMStructureVersion");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString(1);
					long structureVersionId = rs.getLong(2);

					String newDefinition = updateDefinition(definition);

					ps2.setString(1, newDefinition);

					ps2.setLong(2, structureVersionId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private String _convertExpression(
		Collection<DDMFormField> ddmFormFields, String visibilityExpression) {

		List<String> parameterValues =
			ExpressionParameterValueExtractor.extractParameterValues(
				visibilityExpression);

		for (String parameterValue : parameterValues) {
			if (Validator.isNull(parameterValue)) {
				continue;
			}

			Stream<DDMFormField> ddmFormFieldsStream = ddmFormFields.stream();

			boolean fieldName = ddmFormFieldsStream.anyMatch(
				ddmFormField -> ddmFormField.getProperty(
					"name"
				).equals(
					parameterValue
				));

			if (!fieldName) {
				continue;
			}

			StringBundler sb = new StringBundler(5);

			sb.append("getValue(");
			sb.append(StringPool.APOSTROPHE);
			sb.append(parameterValue);
			sb.append(StringPool.APOSTROPHE);
			sb.append(")");

			visibilityExpression = StringUtil.replace(
				visibilityExpression, parameterValue, sb.toString());
		}

		return visibilityExpression;
	}

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;

}