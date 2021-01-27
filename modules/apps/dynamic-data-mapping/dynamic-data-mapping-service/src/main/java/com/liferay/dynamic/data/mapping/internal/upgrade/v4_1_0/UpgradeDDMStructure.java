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

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_1_0;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMDataDefinitionConverter;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class UpgradeDDMStructure extends UpgradeProcess {

	public UpgradeDDMStructure(
		DDMDataDefinitionConverter ddmDataDefinitionConverter,
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormLayoutDeserializer ddmFormLayoutDeserializer,
		DDMFormLayoutSerializer ddmFormLayoutSerializer,
		DDMFormSerializer ddmFormSerializer) {

		_ddmDataDefinitionConverter = ddmDataDefinitionConverter;
		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormLayoutDeserializer = ddmFormLayoutDeserializer;
		_ddmFormLayoutSerializer = ddmFormLayoutSerializer;
		_ddmFormSerializer = ddmFormSerializer;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeStructureDefinition();
		_upgradeStructureLayoutDefinition();
		_upgradeStructureVersionDefinition();
		_upgradeNestedFieldsStructureLayoutDefinition();
	}

	private DDMFormField _createFieldSetDDMFormField(
		Locale defaultLocale, String name, Long parentStructureId,
		Long parentStructureLayoutId) {

		return new DDMFormField(name, "fieldset") {
			{
				setDataType("string");
				setIndexType("keyword");
				setLabel(
					new LocalizedValue() {
						{
							addString(defaultLocale, StringPool.BLANK);
						}
					});
				setLocalizable(true);
				setReadOnly(false);
				setPredefinedValue(
					new LocalizedValue() {
						{
							addString(defaultLocale, StringPool.BLANK);
						}
					});
				setProperty("ddmStructureId", parentStructureId);
				setProperty("ddmStructureLayoutId", parentStructureLayoutId);
				setProperty("upgradedStructure", true);
				setRepeatable(false);
				setRequired(false);
				setShowLabel(false);
				setTip(
					new LocalizedValue() {
						{
							addString(defaultLocale, StringPool.BLANK);
						}
					});
				setVisibilityExpression(StringPool.BLANK);
			}
		};
	}

	private String _upgradeDDMFormLayoutDefinition(String content)
		throws Exception {

		DDMFormLayout ddmFormLayout = DDMFormLayoutDeserializeUtil.deserialize(
			_ddmFormLayoutDeserializer, content);

		ddmFormLayout.setDefinitionSchemaVersion("2.0");
		ddmFormLayout.setPaginationMode(DDMFormLayout.SINGLE_PAGE_MODE);

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			LocalizedValue localizedValue = ddmFormLayoutPage.getTitle();

			if (localizedValue == null) {
				localizedValue = new LocalizedValue();

				localizedValue.addString(
					ddmFormLayout.getDefaultLocale(),
					LanguageUtil.get(ddmFormLayout.getDefaultLocale(), "page"));

				for (Locale locale : ddmFormLayout.getAvailableLocales()) {
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

				for (Locale locale : ddmFormLayout.getAvailableLocales()) {
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

	private String _upgradeDDMFormLayoutDefinition(
			String definition, Long structureId)
		throws Exception {

		DDMFormLayout ddmFormLayout = DDMFormLayoutDeserializeUtil.deserialize(
			_ddmFormLayoutDeserializer, definition);

		DDMFormLayoutPage ddmFormLayoutPage =
			ddmFormLayout.getDDMFormLayoutPage(0);

		List<DDMFormLayoutRow> ddmFormLayoutRows =
			ddmFormLayoutPage.getDDMFormLayoutRows();

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		DDMFormField ddmFormField = _fieldSetMap.get(structureId);

		ddmFormLayoutRow.addDDMFormLayoutColumn(
			new DDMFormLayoutColumn(
				DDMFormLayoutColumn.FULL, ddmFormField.getName()));

		ddmFormLayoutRows.add(0, ddmFormLayoutRow);

		ddmFormLayoutPage.setDDMFormLayoutRows(ddmFormLayoutRows);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(
					DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
						ddmFormLayout
					).build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	private String _upgradeDefinition(
			String definition, Long parentStructureId,
			Long parentStructureLayoutId, Long structureId)
		throws Exception {

		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, definition);

		_fieldSetMap.computeIfAbsent(
			structureId,
			key -> _createFieldSetDDMFormField(
				ddmForm.getDefaultLocale(), StringUtil.randomString(),
				parentStructureId, parentStructureLayoutId));

		ddmForm.addDDMFormField(_fieldSetMap.get(structureId));

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	private void _upgradeNestedFieldsStructureLayoutDefinition()
		throws Exception {

		StringBundler sb1 = new StringBundler(13);

		sb1.append("select DDMStructureLayout.structureLayoutId, ");
		sb1.append("DDMStructureLayout.definition as ");
		sb1.append("structureLayoutDefinition, ");
		sb1.append("DDMStructureVersion.definition as ");
		sb1.append("structureVersionDefinition from DDMStructureLayout inner ");
		sb1.append("join DDMStructureVersion on ");
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
					"update DDMStructureLayout set definition = ? where " +
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
					String structureLayoutDefinition = rs.getString(
						"structureLayoutDefinition");
					String structureVersionDefinition = rs.getString(
						"structureVersionDefinition");

					ps2.setString(
						1,
						_ddmDataDefinitionConverter.
							convertDDMFormLayoutDataDefinition(
								structureLayoutDefinition,
								structureVersionDefinition));

					ps2.setLong(2, rs.getLong("structureLayoutId"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private String _upgradeParentStructureDefinition(
			String definition, Long parentStructureId, Long structureId)
		throws Exception {

		StringBundler sb1 = new StringBundler(8);

		sb1.append("select DDMStructureLayout.structureLayoutId  from ");
		sb1.append("DDMStructureLayout inner join DDMStructureVersion on ");
		sb1.append("DDMStructureVersion.structureVersionId = ");
		sb1.append("DDMStructureLayout.structureVersionId inner join ");
		sb1.append("DDMStructure on DDMStructure.structureId = ");
		sb1.append("DDMStructureVersion.structureId and DDMStructure.version ");
		sb1.append("= DDMStructureVersion.version where ");
		sb1.append("DDMStructure.structureId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString())) {

			ps1.setLong(1, parentStructureId);

			try (ResultSet rs = ps1.executeQuery()) {
				if (rs.next()) {
					return _upgradeDefinition(
						definition, parentStructureId,
						rs.getLong("structureLayoutId"), structureId);
				}
			}
		}

		return definition;
	}

	private void _upgradeStructureDefinition() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select * from DDMStructure where classNameId = ? or " +
					"classNameId = ? order by createDate");
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
					String definition = rs.getString("definition");

					if (Validator.isNotNull(rs.getLong("parentStructureId"))) {
						definition = _upgradeParentStructureDefinition(
							definition, rs.getLong("parentStructureId"),
							rs.getLong("structureId"));
					}

					long companyId = rs.getLong("companyId");

					Locale locale = LocaleUtil.fromLanguageId(
						UpgradeProcessUtil.getDefaultLanguageId(companyId));

					ps2.setString(
						1,
						_ddmDataDefinitionConverter.convert(
							definition, locale));

					ps2.setLong(2, rs.getLong("structureId"));
					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private void _upgradeStructureLayoutDefinition() throws Exception {
		StringBundler sb1 = new StringBundler(13);

		sb1.append("select DDMStructure.structureId, ");
		sb1.append("DDMStructure.parentStructureId, DDMStructure.classNameId ");
		sb1.append(", DDMStructure.structureKey, ");
		sb1.append("DDMStructureLayout.structureLayoutId, ");
		sb1.append("DDMStructureLayout.definition from DDMStructureLayout ");
		sb1.append("inner join DDMStructureVersion on ");
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
					String definition = rs.getString("definition");

					if (Validator.isNotNull(rs.getLong("parentStructureId"))) {
						definition = _upgradeDDMFormLayoutDefinition(
							definition, rs.getLong("structureId"));
					}

					ps2.setString(
						1, _upgradeDDMFormLayoutDefinition(definition));
					ps2.setLong(2, rs.getLong("classNameId"));
					ps2.setString(3, rs.getString("structureKey"));
					ps2.setLong(4, rs.getLong("structureLayoutId"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private void _upgradeStructureVersionDefinition() throws Exception {
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
					String definition = rs.getString("definition");

					if (Validator.isNotNull(rs.getLong("parentStructureId"))) {
						definition = _upgradeParentStructureDefinition(
							definition, rs.getLong("parentStructureId"),
							rs.getLong("structureId"));
					}

					long companyId = rs.getLong("companyId");

					Locale locale = LocaleUtil.fromLanguageId(
						UpgradeProcessUtil.getDefaultLanguageId(companyId));

					ps2.setString(
						1,
						_ddmDataDefinitionConverter.convert(
							definition, locale));

					ps2.setLong(2, rs.getLong("structureVersionId"));
					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private final DDMDataDefinitionConverter _ddmDataDefinitionConverter;
	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;
	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final DDMFormSerializer _ddmFormSerializer;
	private final Map<Long, DDMFormField> _fieldSetMap = new HashMap<>();

}