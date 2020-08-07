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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_8_0;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * @author Marcos Martins
 */
public class UpgradeDDMStructure extends UpgradeProcess {

	public UpgradeDDMStructure(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormLayoutDeserializer ddmFormLayoutDeserializer,
		DDMFormLayoutSerializer ddmFormLayoutSerializer,
		DDMFormSerializer ddmFormSerializer, JSONFactory jsonFactory) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormLayoutDeserializer = ddmFormLayoutDeserializer;
		_ddmFormLayoutSerializer = ddmFormLayoutSerializer;
		_ddmFormSerializer = ddmFormSerializer;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeDDMStructureLayout();

		_upgradeDDMStructureVersion();

		_upgradeDDMStructure();
	}

	private String _generateDDMFormFieldName() {
		String ddmFormFieldName = "Field";

		Random random = new Random();

		for (int i = 0; i < _DDM_FORM_FIELD_NAME_RANDOM_NUMBERS_LENGTH; i++) {
			ddmFormFieldName = ddmFormFieldName.concat(
				String.valueOf(random.nextInt(10)));
		}

		return ddmFormFieldName;
	}

	private DDMForm _getDDMForm(String definition) throws Exception {
		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(
					DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
						definition
					).build());

		Exception exception =
			ddmFormDeserializerDeserializeResponse.getException();

		if (exception != null) {
			throw new UpgradeException(exception);
		}

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private void _upgradeDDMStructure() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("select DDMStructure.structureId, ");
		sb.append("DDMStructureVersion.definition from DDMStructure inner ");
		sb.append("join DDMStructureVersion on DDMStructure.structureid = ");
		sb.append("DDMStructureVersion.structureid where ");
		sb.append("DDMStructure.version = DDMStructureVersion.version and ");
		sb.append("DDMStructure.classnameId = ?");

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
					String structureVersionDefinition = rs.getString(
						"definition");

					ps2.setString(1, structureVersionDefinition);

					long structureId = rs.getLong("structureId");

					ps2.setLong(2, structureId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private void _upgradeDDMStructureLayout() throws Exception {
		StringBundler sb = new StringBundler(9);

		sb.append("select DDMStructureLayout.structureLayoutId, ");
		sb.append("DDMStructureLayout.structureVersionId, ");
		sb.append("DDMStructureLayout.definition from DDMStructureLayout ");
		sb.append("inner join DDMStructureVersion on ");
		sb.append("DDMStructureLayout.structureVersionId = ");
		sb.append("DDMStructureVersion.structureVersionId inner join ");
		sb.append("DDMStructure on DDMStructure.structureId = ");
		sb.append("DDMStructureVersion.structureId where ");
		sb.append("DDMStructure.classnameId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureLayout set definition = ? where " +
						"structureLayoutId = ?")) {

			ps1.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString("definition");

					long structureVersionId = rs.getLong("structureVersionId");

					ps2.setString(
						1,
						_upgradeDDMStructureLayoutDefinition(
							definition, structureVersionId));

					long structureLayoutId = rs.getLong("structureLayoutId");

					ps2.setLong(2, structureLayoutId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private String _upgradeDDMStructureLayoutDefinition(
			String definition, long structureVersionId)
		throws Exception {

		List<Tuple> ddmFormFieldTuples = new ArrayList<>();

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				_ddmFormLayoutDeserializer.deserialize(
					DDMFormLayoutDeserializerDeserializeRequest.Builder.
						newBuilder(
							definition
						).build());

		Exception exception =
			ddmFormLayoutDeserializerDeserializeResponse.getException();

		if (exception != null) {
			throw new UpgradeException(exception);
		}

		DDMFormLayout ddmFormLayout =
			ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();

		if (Objects.equals(ddmFormLayout.getPaginationMode(), "pagination")) {
			ddmFormLayout.setPaginationMode(DDMFormLayout.WIZARD_MODE);
		}

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			for (DDMFormLayoutRow ddmFormLayoutRow :
					ddmFormLayoutPage.getDDMFormLayoutRows()) {

				for (DDMFormLayoutColumn ddmFormLayoutColumn :
						ddmFormLayoutRow.getDDMFormLayoutColumns()) {

					List<String> ddmFormFieldNames =
						ddmFormLayoutColumn.getDDMFormFieldNames();

					if (ddmFormFieldNames.size() > 1) {
						String ddmFormFieldName = _generateDDMFormFieldName();

						ddmFormFieldTuples.add(
							new Tuple(
								ddmFormFieldName, "Fields Group",
								ddmFormLayoutColumn.getSize(),
								ddmFormFieldNames));

						ddmFormLayoutColumn.setDDMFormFieldNames(
							Arrays.asList(ddmFormFieldName));
					}
				}
			}
		}

		if (!ddmFormFieldTuples.isEmpty()) {
			_nestedFieldsMap.put(structureVersionId, ddmFormFieldTuples);
		}

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(
					DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
						ddmFormLayout
					).build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	private void _upgradeDDMStructureVersion() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select DDMStructureVersion.structureVersionId, ");
		sb.append("DDMStructureVersion.definition from DDMStructure inner ");
		sb.append("join DDMStructureVersion on DDMStructure.structureId = ");
		sb.append("DDMStructureVersion.structureId where ");
		sb.append("DDMStructure.classnameId = ?");

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
					String definition = rs.getString("definition");

					long structureVersionId = rs.getLong("structureVersionId");

					ps2.setString(
						1,
						_upgradeDDMStructureVersionDefinition(
							definition, structureVersionId));

					ps2.setLong(2, structureVersionId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private String _upgradeDDMStructureVersionDefinition(
			String definition, long structureVersionId)
		throws Exception {

		DDMForm ddmForm = _getDDMForm(definition);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		List<Tuple> ddmFormFieldTuples = _nestedFieldsMap.get(
			structureVersionId);

		if (ddmFormFieldTuples != null) {
			for (Tuple ddmFormFieldTuple : ddmFormFieldTuples) {
				String fieldSetDDMFormFieldName =
					(String)ddmFormFieldTuple.getObject(
						_DDM_FORM_FIELD_TUPLE_NAME);

				DDMFormField fieldSetDDMFormField = new DDMFormField(
					fieldSetDDMFormFieldName, "fieldset");

				LocalizedValue localizedValue = new LocalizedValue();

				Set<Locale> locales = ddmForm.getAvailableLocales();

				locales.forEach(
					locale -> localizedValue.addString(
						locale,
						(String)ddmFormFieldTuple.getObject(
							_DDM_FORM_FIELD_TUPLE_LABEL)));

				fieldSetDDMFormField.setLabel(localizedValue);

				fieldSetDDMFormField.setProperty(
					"ddmStructureId", StringPool.BLANK);
				fieldSetDDMFormField.setProperty(
					"ddmStructureLayoutId", StringPool.BLANK);

				JSONArray rows = _jsonFactory.createJSONArray();

				List<String> nestedNames =
					(List<String>)ddmFormFieldTuple.getObject(
						_DDM_FORM_FIELD_TUPLE_NESTED_FIELD_NAMES);

				nestedNames.forEach(
					ddmFormFieldName -> {
						fieldSetDDMFormField.addNestedDDMFormField(
							ddmFormFieldsMap.get(ddmFormFieldName));

						rows.put(
							JSONUtil.put(
								"columns",
								JSONUtil.put(
									JSONUtil.put(
										"fields", JSONUtil.put(ddmFormFieldName)
									).put(
										"size",
										ddmFormFieldTuple.getObject(
											_DDM_FORM_FIELD_TUPLE_COLUMN_SIZE)
									))));

						ddmFormFieldsMap.remove(ddmFormFieldName);
					});

				fieldSetDDMFormField.setProperty("rows", rows);

				fieldSetDDMFormField.setShowLabel(false);

				ddmFormFieldsMap.put(
					fieldSetDDMFormFieldName, fieldSetDDMFormField);
			}

			List<DDMFormField> ddmFormFields = new ArrayList<>(
				ddmFormFieldsMap.values());

			ddmForm.setDDMFormFields(ddmFormFields);
		}

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(
				DDMFormSerializerSerializeRequest.Builder.newBuilder(
					ddmForm
				).build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	private static final int _DDM_FORM_FIELD_NAME_RANDOM_NUMBERS_LENGTH = 8;

	private static final int _DDM_FORM_FIELD_TUPLE_COLUMN_SIZE = 2;

	private static final int _DDM_FORM_FIELD_TUPLE_LABEL = 1;

	private static final int _DDM_FORM_FIELD_TUPLE_NAME = 0;

	private static final int _DDM_FORM_FIELD_TUPLE_NESTED_FIELD_NAMES = 3;

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;
	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final DDMFormSerializer _ddmFormSerializer;
	private final JSONFactory _jsonFactory;
	private final Map<Long, List<Tuple>> _nestedFieldsMap = new HashMap<>();

}