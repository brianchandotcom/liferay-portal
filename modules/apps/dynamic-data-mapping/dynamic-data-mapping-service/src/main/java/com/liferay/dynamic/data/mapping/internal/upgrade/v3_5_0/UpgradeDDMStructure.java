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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_5_0;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Marcela Cunha
 */
public class UpgradeDDMStructure extends UpgradeProcess {

	public UpgradeDDMStructure(
		DDMFormDeserializer ddmFormJSONDeserializer,
		DDMFormDeserializer ddmFormXSDDeserializer,
		DDMFormLayoutDeserializer ddmFormLayoutDeserializer,
		DDMFormLayoutSerializer ddmFormLayoutSerializer) {

		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
		_ddmFormXSDDeserializer = ddmFormXSDDeserializer;
		_ddmFormLayoutDeserializer = ddmFormLayoutDeserializer;
		_ddmFormLayoutSerializer = ddmFormLayoutSerializer;
	}

	protected DDMForm deserialize(String content, String type) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializer ddmFormDeserializer = null;

		if (StringUtil.equalsIgnoreCase(type, "json")) {
			ddmFormDeserializer = _ddmFormJSONDeserializer;
		}
		else if (StringUtil.equalsIgnoreCase(type, "xsd")) {
			ddmFormDeserializer = _ddmFormXSDDeserializer;
		}

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	protected void doStructureDefinitionUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select * from DDMStructure where classNameId = ? or " +
					"classNameId = ? ");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?")) {

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(
					"com.liferay.document.library.kernel.model." +
						"DLFileEntryMetadata"));
			ps1.setLong(
				2,
				PortalUtil.getClassNameId(
					"com.liferay.journal.model.JournalArticle"));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = upgradeDefinition(
						rs.getLong("companyId"), rs.getString("definition"));

					ps2.setString(1, definition);

					ps2.setLong(2, rs.getLong("structureId"));
					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected void doStructureLayoutUpgrade() throws Exception {
		StringBundler sb1 = new StringBundler(11);

		sb1.append("select DDMStructureLayout.definition, ");
		sb1.append("DDMStructureLayout.structureLayoutId, ");
		sb1.append("DDMStructure.structureKey, DDMStructure.classNameId from ");
		sb1.append("DDMStructureLayout inner join DDMStructureVersion on ");
		sb1.append("DDMStructureVersion.structureVersionId = ");
		sb1.append("DDMStructureLayout.structureVersionId inner join ");
		sb1.append("DDMStructure on DDMStructure.structureId = ");
		sb1.append("DDMStructureVersion.structureId and DDMStructure.version ");
		sb1.append("= DDMStructureVersion.version where ");
		sb1.append("DDMStructure.classNameId = ? or DDMStructure.classNameId ");
		sb1.append("= ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureLayout set definition = ?, " +
						"classNameId = ?, structureLayoutKey = ? where " +
							"structureLayoutId = ?")) {

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(
					"com.liferay.document.library.kernel.model." +
						"DLFileEntryMetadata"));
			ps1.setLong(
				2,
				PortalUtil.getClassNameId(
					"com.liferay.journal.model.JournalArticle"));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					ps2.setString(
						1,
						upgradeDDMFormLayoutDefinition(
							rs.getString("definition")));
					ps2.setLong(2, rs.getLong("classNameId"));
					ps2.setString(3, rs.getString("structureKey"));
					ps2.setLong(4, rs.getLong("structureLayoutId"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected void doStructureVersionDefinitionUpgrade() throws Exception {
		StringBundler sb1 = new StringBundler(6);

		sb1.append("select DDMStructure.structureKey, DDMStructureVersion.* ");
		sb1.append("from DDMStructureVersion inner join DDMStructure on ");
		sb1.append("DDMStructure.structureId = ");
		sb1.append("DDMStructureVersion.structureId where ");
		sb1.append("DDMStructure.classNameId = ? or DDMStructure.classNameId ");
		sb1.append("= ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(
					"com.liferay.document.library.kernel.model." +
						"DLFileEntryMetadata"));
			ps1.setLong(
				2,
				PortalUtil.getClassNameId(
					"com.liferay.journal.model.JournalArticle"));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = upgradeDefinition(
						rs.getLong("companyId"), rs.getString("definition"));

					ps2.setString(1, definition);

					ps2.setLong(2, rs.getLong("structureVersionId"));
					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		doStructureDefinitionUpgrade();
		doStructureVersionDefinitionUpgrade();
		doStructureLayoutUpgrade();
	}

	protected DDMForm getDDMForm(String definition, String storageType) {
		if (storageType.equals("expando") || storageType.equals("xml")) {
			return deserialize(definition, "xsd");
		}

		return deserialize(definition, "json");
	}

	protected String getNextVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0] + StringPool.PERIOD + ++versionParts[1];
	}

	protected long getStructureVersionId(long structureId) throws Exception {
		StringBundler sb1 = new StringBundler(6);

		sb1.append("select DDMStructureVersion.structureVersionId from ");
		sb1.append("DDMStructureVersion inner join DDMStructure on ");
		sb1.append("DDMStructure.structureId = ");
		sb1.append("DDMStructureVersion.structureId and DDMStructure.version ");
		sb1.append("= DDMStructureVersion.version where ");
		sb1.append("DDMStructure.structureId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString())) {

			ps1.setLong(1, structureId);

			try (ResultSet rs = ps1.executeQuery()) {
				rs.next();

				return rs.getLong("structureVersionId");
			}
		}
	}

	protected String getVersion(Long structureId) throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select MAX(version) from DDMStructureVersion where " +
					"structureId = ?")) {

			ps1.setLong(1, structureId);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String lastVersion = rs.getString(1);

					return getNextVersion(lastVersion);
				}
			}
		}

		return DDMStructureConstants.VERSION_DEFAULT;
	}

	protected void upgradeColorField(JSONObject jsonObject) {
		jsonObject.put(
			"dataType", "string"
		).put(
			"type", "color"
		).put(
			"visibilityExpression", StringPool.BLANK
		);
	}

	protected void upgradeDateField(JSONObject jsonObject) {
		jsonObject.put(
			"dataType", "string"
		).put(
			"type", "date"
		).put(
			"visibilityExpression", StringPool.BLANK
		);
	}

	protected String upgradeDDMFormLayoutDefinition(String content) {
		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				_ddmFormLayoutDeserializer.deserialize(
					DDMFormLayoutDeserializerDeserializeRequest.Builder.
						newBuilder(
							content
						).build());

		DDMFormLayout ddmFormLayout =
			ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();

		Set<Locale> availableLocales = ddmFormLayout.getAvailableLocales();

		List<DDMFormLayoutPage> ddmFormLayoutPages =
			ddmFormLayout.getDDMFormLayoutPages();

		for (DDMFormLayoutPage ddmFormLayoutPage : ddmFormLayoutPages) {
			LocalizedValue localizedValue = ddmFormLayoutPage.getTitle();

			if (localizedValue == null) {
				localizedValue = new LocalizedValue();

				localizedValue.addString(
					ddmFormLayout.getDefaultLocale(),
					LanguageUtil.get(ddmFormLayout.getDefaultLocale(), "page"));

				for (Locale locale : availableLocales) {
					localizedValue.addString(
						locale, LanguageUtil.get(locale, "page"));
				}
			}
			else {
				if (Validator.isNull(
						localizedValue.getString(
							ddmFormLayout.getDefaultLocale()))) {

					localizedValue.addString(
						ddmFormLayout.getDefaultLocale(),
						LanguageUtil.get(
							ddmFormLayout.getDefaultLocale(), "page"));
				}
			}

			ddmFormLayoutPage.setTitle(localizedValue);

			localizedValue = ddmFormLayoutPage.getDescription();

			if (localizedValue == null) {
				localizedValue = new LocalizedValue();

				localizedValue.addString(
					ddmFormLayout.getDefaultLocale(),
					LanguageUtil.get(
						ddmFormLayout.getDefaultLocale(), "description"));

				for (Locale locale : availableLocales) {
					localizedValue.addString(
						locale, LanguageUtil.get(locale, "description"));
				}
			}
			else {
				if (Validator.isNull(
						localizedValue.getString(
							ddmFormLayout.getDefaultLocale()))) {

					localizedValue.addString(
						ddmFormLayout.getDefaultLocale(),
						LanguageUtil.get(
							ddmFormLayout.getDefaultLocale(), "description"));
				}
			}

			ddmFormLayoutPage.setDescription(localizedValue);
		}

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(
					DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
						ddmFormLayout
					).build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	protected void upgradeDecimalField(JSONObject jsonObject) {
		jsonObject.put(
			"dataType", "decimal"
		).put(
			"type", "numeric"
		).put(
			"visibilityExpression", StringPool.BLANK
		);
	}

	protected String upgradeDefinition(long companyId, String definition)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(definition);

		jsonObject.put(
			"fields",
			upgradeFields(companyId, jsonObject.getJSONArray("fields")));

		return jsonObject.toString();
	}

	protected void upgradeDocumentLibraryField(JSONObject jsonObject) {
		jsonObject.put(
			"dataType", "string"
		).put(
			"type", "document_library"
		).put(
			"visibilityExpression", StringPool.BLANK
		);
	}

	protected JSONArray upgradeFields(long companyId, JSONArray fieldsJSONArray)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (fieldsJSONArray != null) {
			for (int i = 0; i < fieldsJSONArray.length(); i++) {
				JSONObject jsonObject = fieldsJSONArray.getJSONObject(i);

				String type = jsonObject.getString("type");

				if (StringUtil.equals(type, "ddm-color")) {
					upgradeColorField(jsonObject);
				}
				else if (StringUtil.equals(type, "ddm-date")) {
					upgradeDateField(jsonObject);
				}
				else if (type.startsWith("ddm-decimal")) {
					upgradeDecimalField(jsonObject);
				}
				else if (type.startsWith("ddm-documentlibrary")) {
					upgradeDocumentLibraryField(jsonObject);
				}
				else if (type.startsWith("ddm-geolocation")) {
					upgradeGeolocation(jsonObject);
				}
				else if (type.startsWith("ddm-integer")) {
					upgradeIntegerField(jsonObject);
				}
				else if (type.startsWith("ddm-number")) {
					upgradeNumberField(jsonObject);
				}
				else if (StringUtil.equals(type, "ddm-separator")) {
					upgradeSeparatorField(jsonObject);
				}
				else if (type.startsWith("ddm-")) {
					jsonObject.put(
						"dataType", "string"
					).put(
						"type", type.substring(4)
					);
				}
				else if (StringUtil.equals(type, "text")) {
					upgradeTextField(companyId, jsonObject);
				}
				else if (StringUtil.equals(type, "textarea")) {
					upgradeTextArea(companyId, jsonObject);
				}

				if (jsonObject.has("nestedFields")) {
					jsonObject.put(
						"nestedFields",
						upgradeFields(
							companyId,
							jsonObject.getJSONArray("nestedFields")));
				}

				jsonArray.put(jsonObject);
			}
		}

		return jsonArray;
	}

	protected void upgradeGeolocation(JSONObject jsonObject) {
		jsonObject.put(
			"dataType", "string"
		).put(
			"type", "geolocation"
		);
	}

	protected void upgradeIntegerField(JSONObject jsonObject) {
		jsonObject.put(
			"type", "numeric"
		).put(
			"visibilityExpression", StringPool.BLANK
		);
	}

	protected void upgradeNumberField(JSONObject jsonObject) {
		jsonObject.put(
			"dataType", "decimal"
		).put(
			"type", "numeric"
		).put(
			"visibilityExpression", StringPool.BLANK
		);
	}

	protected void upgradeSeparatorField(JSONObject jsonObject) {
		jsonObject.put(
			"dataType", StringPool.BLANK
		).put(
			"type", "separator"
		);
	}

	protected void upgradeTextArea(long companyId, JSONObject jsonObject)
		throws Exception {

		jsonObject.put(
			"autocomplete", false
		).put(
			"dataSourceType", "manual"
		).put(
			"ddmDataProviderInstanceId", "[]"
		).put(
			"ddmDataProviderInstanceOutput", "[]"
		).put(
			"displayStyle", "multiline"
		).put(
			"fieldNamespace", StringPool.BLANK
		).put(
			"options",
			JSONUtil.put(
				JSONUtil.put(
					"label",
					JSONUtil.put(
						UpgradeProcessUtil.getDefaultLanguageId(companyId),
						GetterUtil.getString("Option"))
				).put(
					"value", "Option"
				))
		).put(
			"placeholder",
			JSONUtil.put(
				UpgradeProcessUtil.getDefaultLanguageId(companyId),
				StringPool.BLANK)
		).put(
			"tooltip",
			JSONUtil.put(
				UpgradeProcessUtil.getDefaultLanguageId(companyId),
				StringPool.BLANK)
		).put(
			"type", "text"
		).put(
			"visibilityExpression", StringPool.BLANK
		);
	}

	protected void upgradeTextField(long companyId, JSONObject jsonObject)
		throws Exception {

		jsonObject.put(
			"autocomplete", false
		).put(
			"dataSourceType", "manual"
		).put(
			"ddmDataProviderInstanceId", "[]"
		).put(
			"ddmDataProviderInstanceOutput", "[]"
		).put(
			"displayStyle", "singleline"
		).put(
			"fieldNamespace", StringPool.BLANK
		).put(
			"options",
			JSONUtil.put(
				JSONUtil.put(
					"label",
					JSONUtil.put(
						UpgradeProcessUtil.getDefaultLanguageId(companyId),
						GetterUtil.getString("Option"))
				).put(
					"value", "Option"
				))
		).put(
			"placeholder",
			JSONUtil.put(
				UpgradeProcessUtil.getDefaultLanguageId(companyId),
				StringPool.BLANK)
		).put(
			"tooltip",
			JSONUtil.put(
				UpgradeProcessUtil.getDefaultLanguageId(companyId),
				StringPool.BLANK)
		).put(
			"type", "text"
		).put(
			"visibilityExpression", StringPool.BLANK
		);
	}

	private final DDMFormDeserializer _ddmFormJSONDeserializer;
	private final DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;
	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final DDMFormDeserializer _ddmFormXSDDeserializer;

}