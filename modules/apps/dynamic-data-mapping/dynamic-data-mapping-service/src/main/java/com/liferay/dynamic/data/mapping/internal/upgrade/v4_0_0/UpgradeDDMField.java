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

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LRUMap;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class UpgradeDDMField extends UpgradeProcess {

	public UpgradeDDMField(
		JSONFactory jsonFactory,
		DDMFormDeserializer jsonDDMFormJSONDeserializer,
		DDMFormValuesDeserializer jsonDDMFormValuesDeserializer) {

		_jsonFactory = jsonFactory;
		_jsonDDMFormJSONDeserializer = jsonDDMFormJSONDeserializer;
		_jsonDDMFormValuesDeserializer = jsonDDMFormValuesDeserializer;
	}

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"create table DDMField (mvccVersion LONG default 0 not null,",
				"ctCollectionId LONG default 0 not null, fieldId LONG not ",
				"null, companyId LONG, structureVersionId LONG, parentFieldId ",
				"LONG, storageId LONG, fieldName VARCHAR(255) null, fieldType ",
				"VARCHAR(255) null, priority INTEGER, instanceId VARCHAR(75) ",
				"null, localizable BOOLEAN, primary key (fieldId, ",
				"ctCollectionId))"));

		runSQL(
			StringBundler.concat(
				"create table DDMFieldAttribute (mvccVersion LONG default 0 ",
				"not null, ctCollectionId LONG default 0 not null, ",
				"fieldAttributeId LONG not null, companyId LONG, fieldId ",
				"LONG, storageId LONG, languageId VARCHAR(75) null, ",
				"attributeName VARCHAR(255) null, smallAttributeValue ",
				"VARCHAR(255) null, largeAttributeValue TEXT null, primary ",
				"key (fieldAttributeId, ctCollectionId))"));

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select DDMContent.contentId, DDMContent.companyId, ",
						"DDMContent.data_, DDMStorageLink.structureVersionId, ",
						"DDMStructure.structureId from DDMContent inner join ",
						"DDMStorageLink on DDMStorageLink.classPK = ",
						"DDMContent.contentId inner join DDMStructureVersion ",
						"on DDMStructureVersion.structureVersionId = ",
						"DDMStorageLink.structureVersionId inner join ",
						"DDMStructure on DDMStructureVersion.structureId = ",
						"DDMStructure.structureId where ",
						"DDMStructure.storageType = 'json' and ",
						"DDMContent.ctCollectionId = 0 and ",
						"DDMStorageLink.ctCollectionId = 0 and ",
						"DDMStructureVersion.ctCollectionId = 0 and ",
						"DDMStructure.ctCollectionId = 0"));
			PreparedStatement insertDDMFieldPreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						StringBundler.concat(
							"insert into DDMField (mvccVersion, ",
							"ctCollectionId, fieldId, companyId, ",
							"structureVersionId, parentFieldId, storageId, ",
							"fieldName, fieldType, priority, instanceId, ",
							"localizable) values (0, 0, ?, ?, ?, ?, ?, ?, ?, ",
							"?, ?, ?)")));
			PreparedStatement insertDDMFieldAttributePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						StringBundler.concat(
							"insert into DDMFieldAttribute (mvccVersion, ",
							"ctCollectionId, fieldAttributeId, companyId, ",
							"fieldId, storageId, languageId, attributeName, ",
							"smallAttributeValue, largeAttributeValue) values ",
							"(0, 0, ?, ?, ?, ?, ?, ?, ?, ?)")));
			PreparedStatement deleteDDMContentPreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"delete from DDMContent where contentId = ? and " +
							"ctCollectionId = 0"));
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				_upgradeDDMContent(
					insertDDMFieldPreparedStatement,
					insertDDMFieldAttributePreparedStatement,
					deleteDDMContentPreparedStatement,
					resultSet.getLong("contentId"),
					resultSet.getLong("companyId"),
					resultSet.getLong("structureId"),
					resultSet.getLong("structureVersionId"),
					resultSet.getString("data_"));
			}

			insertDDMFieldPreparedStatement.executeBatch();

			insertDDMFieldAttributePreparedStatement.executeBatch();

			deleteDDMContentPreparedStatement.executeBatch();
		}

		_ddmForms.clear();

		_fullHierarchyDDMForms.clear();

		runSQL(
			"update DDMStructure set storageType = 'default' where " +
				"storageType = 'json'");

		runSQL(
			"update DDMStructureVersion set storageType = 'default' where " +
				"storageType = 'json'");

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select formInstanceId, settings_ from DDMFormInstance " +
						"where ctCollectionId = 0");
			ResultSet resultSet = selectPreparedStatement.executeQuery();
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update DDMFormInstance set settings_ = ? where " +
							"formInstanceId = ? and ctCollectionId = 0"))) {

			while (resultSet.next()) {
				String settings = resultSet.getString("settings_");

				if (Validator.isNotNull(settings)) {
					JSONObject settingsJSONObject =
						_jsonFactory.createJSONObject(settings);

					JSONArray fieldValuesJSONArray =
						settingsJSONObject.getJSONArray("fieldValues");

					for (int i = 0; i < fieldValuesJSONArray.length(); i++) {
						JSONObject jsonObject =
							fieldValuesJSONArray.getJSONObject(i);

						if (Objects.equals(
								jsonObject.getString("name"), "storageType")) {

							JSONArray oldJSONArray =
								_jsonFactory.createJSONArray(
									jsonObject.getString("value"));

							JSONArray newJSONArray =
								_jsonFactory.createJSONArray();

							for (Object value : oldJSONArray) {
								if (Objects.equals(value, "json")) {
									value = "default";
								}

								newJSONArray.put(value);
							}

							jsonObject.put("value", newJSONArray);

							break;
						}
					}

					updatePreparedStatement.setString(
						1, settingsJSONObject.toJSONString());

					updatePreparedStatement.setLong(
						2, resultSet.getLong("formInstanceId"));

					updatePreparedStatement.addBatch();
				}
			}

			updatePreparedStatement.executeBatch();
		}
	}

	private void _collectDDMFieldInfos(
		Map<String, DDMFieldInfo> ddmFieldInfoMap,
		List<DDMFormFieldValue> ddmFormValues, String parentInstanceId) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormValues) {
			DDMFieldInfo ddmFieldInfo = new DDMFieldInfo(
				ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId(),
				parentInstanceId);

			ddmFieldInfoMap.put(ddmFieldInfo._instanceId, ddmFieldInfo);

			Value value = ddmFormFieldValue.getValue();

			if (value != null) {
				Map<Locale, String> values = value.getValues();

				for (Map.Entry<Locale, String> entry : values.entrySet()) {
					ddmFieldInfo._ddmFieldAttributeInfos.addAll(
						_getDDMFieldAttributeInfos(
							LanguageUtil.getLanguageId(entry.getKey()),
							entry.getValue()));
				}
			}

			_collectDDMFieldInfos(
				ddmFieldInfoMap,
				ddmFormFieldValue.getNestedDDMFormFieldValues(),
				ddmFieldInfo._instanceId);
		}
	}

	private List<DDMFieldAttributeInfo> _getDDMFieldAttributeInfos(
		String languageId, String valueString) {

		int length = valueString.length();

		if ((length > 1) &&
			(valueString.charAt(0) == CharPool.OPEN_CURLY_BRACE) &&
			(valueString.charAt(length - 1) == CharPool.CLOSE_CURLY_BRACE)) {

			try {
				JSONSerializer jsonSerializer =
					_jsonFactory.createJSONSerializer();

				JSONObject jsonObject = _jsonFactory.createJSONObject(
					valueString);

				Set<String> keySet = jsonObject.keySet();

				if (!keySet.isEmpty()) {
					List<DDMFieldAttributeInfo> ddmFieldAttributeInfos =
						new ArrayList<>(keySet.size());

					for (String key : jsonObject.keySet()) {
						ddmFieldAttributeInfos.add(
							new DDMFieldAttributeInfo(
								key,
								jsonSerializer.serialize(jsonObject.get(key)),
								languageId));
					}

					return ddmFieldAttributeInfos;
				}
			}
			catch (JSONException jsonException) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to parse: " + valueString, jsonException);
				}
			}
		}

		return Collections.singletonList(
			new DDMFieldAttributeInfo(
				StringPool.BLANK, valueString, languageId));
	}

	private DDMForm _getDDMForm(long structureId) throws Exception {
		DDMForm ddmForm = _ddmForms.get(structureId);

		if (ddmForm != null) {
			return ddmForm;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"select definition from DDMStructure where structureId = ? " +
					"and ctCollectionId = 0")) {

			ps.setLong(1, structureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					ddmForm = DDMFormDeserializeUtil.deserialize(
						_jsonDDMFormJSONDeserializer,
						rs.getString("definition"));

					_ddmForms.put(structureId, ddmForm);

					return ddmForm;
				}
			}
		}

		throw new UpgradeException(
			"Unable to find dynamic data mapping structure with ID " +
				structureId);
	}

	private DDMForm _getFullHierarchyDDMForm(long structureId)
		throws Exception {

		DDMForm fullHierarchyDDMForm = _fullHierarchyDDMForms.get(structureId);

		if (fullHierarchyDDMForm != null) {
			return fullHierarchyDDMForm;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"select parentStructureId from DDMStructure where " +
					"structureId = ? and ctCollectionId = 0")) {

			ps.setLong(1, structureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					long parentStructureId = rs.getLong("parentStructureId");

					fullHierarchyDDMForm = _getDDMForm(structureId);

					if (parentStructureId > 0) {
						DDMForm parentDDMForm = _getFullHierarchyDDMForm(
							parentStructureId);

						List<DDMFormField> ddmFormFields =
							fullHierarchyDDMForm.getDDMFormFields();

						ddmFormFields.addAll(parentDDMForm.getDDMFormFields());
					}

					_fullHierarchyDDMForms.put(
						structureId, fullHierarchyDDMForm);

					return fullHierarchyDDMForm;
				}
			}
		}

		throw new UpgradeException(
			"Unable to find dynamic data mapping structure with ID " +
				structureId);
	}

	private void _upgradeDDMContent(
			PreparedStatement insertDDMFieldPreparedStatement,
			PreparedStatement insertDDMFieldAttributePreparedStatement,
			PreparedStatement deleteDDMContentPreparedStatement, long contentId,
			long companyId, long structureId, long structureVersionId,
			String data)
		throws Exception {

		DDMForm ddmForm = _getFullHierarchyDDMForm(structureId);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_jsonDDMFormValuesDeserializer.deserialize(
					DDMFormValuesDeserializerDeserializeRequest.Builder.
						newBuilder(
							data, ddmForm
						).build());

		if (ddmFormValuesDeserializerDeserializeResponse.getException() !=
				null) {

			throw ddmFormValuesDeserializerDeserializeResponse.getException();
		}

		DDMFormValues ddmFormValues =
			ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		DDMFieldInfo rootDDMFieldInfo = new DDMFieldInfo(
			StringPool.BLANK, StringPool.BLANK, null);

		Map<String, DDMFieldInfo> ddmFieldInfoMap = LinkedHashMapBuilder.put(
			StringPool.BLANK, rootDDMFieldInfo
		).build();

		Collections.addAll(
			rootDDMFieldInfo._ddmFieldAttributeInfos,
			new DDMFieldAttributeInfo(
				"availableLanguageIds",
				StringUtil.merge(
					ddmFormValues.getAvailableLocales(),
					LocaleUtil::toLanguageId, StringPool.COMMA),
				StringPool.BLANK),
			new DDMFieldAttributeInfo(
				"defaultLanguageId",
				LocaleUtil.toLanguageId(ddmFormValues.getDefaultLocale()),
				StringPool.BLANK));

		_collectDDMFieldInfos(
			ddmFieldInfoMap, ddmFormValues.getDDMFormFieldValues(), null);

		int priority = 0;

		Map<String, Long> instanceToFieldIdMap = new HashMap<>();

		for (DDMFieldInfo ddmFieldInfo : ddmFieldInfoMap.values()) {
			long fieldId = increment(DDMField.class.getName());

			long parentFieldId = 0;

			if (ddmFieldInfo._parentInstanceId != null) {
				parentFieldId = instanceToFieldIdMap.get(
					ddmFieldInfo._parentInstanceId);
			}

			String fieldType = StringPool.BLANK;
			boolean localizable = false;

			if (ddmFieldInfo != rootDDMFieldInfo) {
				DDMFormField ddmFormField = ddmFormFieldsMap.get(
					ddmFieldInfo._fieldName);

				fieldType = ddmFormField.getType();
				localizable = ddmFormField.isLocalizable();
			}

			insertDDMFieldPreparedStatement.setLong(1, fieldId);
			insertDDMFieldPreparedStatement.setLong(2, companyId);
			insertDDMFieldPreparedStatement.setLong(3, structureVersionId);
			insertDDMFieldPreparedStatement.setLong(4, parentFieldId);
			insertDDMFieldPreparedStatement.setLong(5, contentId);
			insertDDMFieldPreparedStatement.setString(
				6, ddmFieldInfo._fieldName);
			insertDDMFieldPreparedStatement.setString(7, fieldType);
			insertDDMFieldPreparedStatement.setInt(8, priority);
			insertDDMFieldPreparedStatement.setString(
				9, ddmFieldInfo._instanceId);
			insertDDMFieldPreparedStatement.setBoolean(10, localizable);

			insertDDMFieldPreparedStatement.addBatch();

			priority++;

			instanceToFieldIdMap.put(ddmFieldInfo._instanceId, fieldId);

			for (DDMFieldAttributeInfo ddmFieldAttributeInfo :
					ddmFieldInfo._ddmFieldAttributeInfos) {

				String smallAttributeValue = null;
				String largeAttributeValue = null;

				if (ddmFieldAttributeInfo._attributeValue != null) {
					if (ddmFieldAttributeInfo._attributeValue.length() <= 255) {
						smallAttributeValue =
							ddmFieldAttributeInfo._attributeValue;
					}
					else {
						largeAttributeValue =
							ddmFieldAttributeInfo._attributeValue;
					}
				}

				insertDDMFieldAttributePreparedStatement.setLong(
					1, increment(DDMFieldAttribute.class.getName()));
				insertDDMFieldAttributePreparedStatement.setLong(2, companyId);
				insertDDMFieldAttributePreparedStatement.setLong(3, fieldId);
				insertDDMFieldAttributePreparedStatement.setLong(4, contentId);
				insertDDMFieldAttributePreparedStatement.setString(
					5, ddmFieldAttributeInfo._languageId);
				insertDDMFieldAttributePreparedStatement.setString(
					6, ddmFieldAttributeInfo._attributeName);
				insertDDMFieldAttributePreparedStatement.setString(
					7, smallAttributeValue);
				insertDDMFieldAttributePreparedStatement.setString(
					8, largeAttributeValue);

				insertDDMFieldAttributePreparedStatement.addBatch();
			}
		}

		deleteDDMContentPreparedStatement.setLong(1, contentId);

		deleteDDMContentPreparedStatement.addBatch();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDDMField.class);

	private final Map<Long, DDMForm> _ddmForms = new LRUMap<>(1000);
	private final Map<Long, DDMForm> _fullHierarchyDDMForms = new LRUMap<>(
		1000);
	private final DDMFormDeserializer _jsonDDMFormJSONDeserializer;
	private final DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;
	private final JSONFactory _jsonFactory;

	private static class DDMFieldAttributeInfo {

		private DDMFieldAttributeInfo(
			String attributeName, String attributeValue, String languageId) {

			_attributeName = attributeName;
			_attributeValue = attributeValue;
			_languageId = languageId;
		}

		private final String _attributeName;
		private final String _attributeValue;
		private final String _languageId;

	}

	private static class DDMFieldInfo {

		private DDMFieldInfo(
			String fieldName, String instanceId, String parentInstanceId) {

			_fieldName = fieldName;
			_instanceId = instanceId;
			_parentInstanceId = parentInstanceId;
		}

		private final List<DDMFieldAttributeInfo> _ddmFieldAttributeInfos =
			new ArrayList<>();
		private final String _fieldName;
		private final String _instanceId;
		private final String _parentInstanceId;

	}

}