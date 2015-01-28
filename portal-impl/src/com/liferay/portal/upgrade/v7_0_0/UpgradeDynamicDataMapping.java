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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.upgrade.v7_0_0.util.DDMContentTable;
import com.liferay.portal.upgrade.v7_0_0.util.DDMStructureTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormLayoutJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.util.DDMFieldsCounter;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class UpgradeDynamicDataMapping extends UpgradeProcess {

	protected void addStructureLayout(
			String uuid_, long structureLayoutId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, long structureVersionId, String definition)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into DDMStructureLayout (uuid_, ");
			sb.append("structureLayoutId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, ");
			sb.append("structureVersionId, definition) values (?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, uuid_);
			ps.setLong(2, structureLayoutId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, modifiedDate);
			ps.setLong(9, structureVersionId);
			ps.setString(10, definition);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure layout " +
					"with structure version ID " + structureVersionId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addStructureVersion(
			long structureVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long structureId,
			String name, String description, String definition,
			String storageType, int type)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMStructureVersion (structureVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("structureId, version, name, description, definition, ");
			sb.append("storageType, type_) values (?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, structureVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setLong(7, structureId);
			ps.setString(8, DDMStructureConstants.VERSION_DEFAULT);
			ps.setString(9, name);
			ps.setString(10, description);
			ps.setString(11, definition);
			ps.setString(12, storageType);
			ps.setInt(13, type);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure version " +
					"with structure ID " + structureId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addStructureVersionsAndLayouts() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DDMStructure");

			rs = ps.executeQuery();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String definition = rs.getString("definition");
				String storageType = rs.getString("storageType");
				int type = rs.getInt("type_");

				long structureVersionId = increment();

				addStructureVersion(
					structureVersionId, groupId, companyId, userId, userName,
					modifiedDate, structureId, name, description, definition,
					storageType, type);

				String ddmFormLayoutDefinition =
					getDefaultDDMFormLayoutDefinition(structureId);

				addStructureLayout(
					PortalUUIDUtil.generate(), increment(), groupId, companyId,
					userId, userName, modifiedDate, modifiedDate,
					structureVersionId, ddmFormLayoutDefinition);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void addTemplateVersion(
			long templateVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long templateId, String name,
			String description, String language, String script)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into DDMTemplateVersion (templateVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("templateId, version, name, description, language, ");
			sb.append("script) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, templateVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setLong(7, templateId);
			ps.setString(8, DDMStructureConstants.VERSION_DEFAULT);
			ps.setString(9, name);
			ps.setString(10, description);
			ps.setString(11, language);
			ps.setString(12, script);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping template version " +
					"with template ID " + templateId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addTemplateVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DDMTemplate");

			rs = ps.executeQuery();

			while (rs.next()) {
				long templateId = rs.getLong("templateId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String language = rs.getString("language");
				String script = rs.getString("script");

				addTemplateVersion(
					increment(), groupId, companyId, userId, userName,
					modifiedDate, templateId, name, description, language,
					script);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_name DDMContent xml data_ TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMContentTable.TABLE_NAME, DDMContentTable.TABLE_COLUMNS,
				DDMContentTable.TABLE_SQL_CREATE,
				DDMContentTable.TABLE_SQL_ADD_INDEXES);
		}

		try {
			runSQL("alter_column_name DDMStructure xsd definition TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMStructureTable.TABLE_NAME, DDMStructureTable.TABLE_COLUMNS,
				DDMStructureTable.TABLE_SQL_CREATE,
				DDMStructureTable.TABLE_SQL_ADD_INDEXES);
		}

		addStructureVersionsAndLayouts();
		addTemplateVersions();

		upgradeXMLStorageAdapter();
	}

	protected DDMForm getDDMForm(long structureId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select parentStructureId, definition from DDMStructure " +
					"where structureId = ?" );

			ps.setLong(1, structureId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long parentStructureId = rs.getLong("parentStructureId");
				String definition = rs.getString("definition");

				DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(
					definition);

				if (parentStructureId > 0) {
					DDMForm parentDDMForm = getDDMForm(parentStructureId);

					List<DDMFormField> ddmFormFields =
						ddmForm.getDDMFormFields();

					ddmFormFields.addAll(parentDDMForm.getDDMFormFields());
				}

				return ddmForm;
			}

			throw new UpgradeException(
				"Unable to find dynamic data mapping structure with ID " +
					structureId);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String getDefaultDDMFormLayoutDefinition(DDMForm ddmForm) {
		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return DDMFormLayoutJSONSerializerUtil.serialize(ddmFormLayout);
	}

	protected String getDefaultDDMFormLayoutDefinition(long structureId)
		throws Exception {

		DDMForm ddmForm = getDDMForm(structureId);

		return getDefaultDDMFormLayoutDefinition(ddmForm);
	}

	protected String toJSON(DDMForm ddmForm, String xml)
		throws PortalException {

		DDMFormValuesXSDDeserializer ddmFormValuesXSDDeserializer =
			new DDMFormValuesXSDDeserializer();

		DDMFormValues ddmFormValues = ddmFormValuesXSDDeserializer.deserialize(
			ddmForm, xml);

		return DDMFormValuesJSONSerializerUtil.serialize(ddmFormValues);
	}

	protected void updateContent(DDMForm ddmForm, long contentId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select data_ from DDMContent where contentId = ?");

			ps.setLong(1, contentId);

			rs = ps.executeQuery();

			if (rs.next()) {
				String xml = rs.getString("data_");

				updateContent(contentId, toJSON(ddmForm, xml));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateContent(long contentId, String data_)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DDMContent set data_= ? where contentId = ?");

			ps.setString(1, data_);
			ps.setLong(2, contentId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateStructureStorageType() throws Exception {
		runSQL(
			"update DDMStructure set storageType='json' where " +
				"storageType = 'xml'");
	}

	protected void updateStructureVersionStorageType() throws Exception {
		runSQL(
			"update DDMStructureVersion set storageType='json' where " +
				"storageType = 'xml'");
	}

	protected void upgradeXMLStorageAdapter() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("select DDMStorageLink.classPK, DDMStorageLink.");
			sb.append("structureId from DDMStorageLink inner join ");
			sb.append("DDMStructure on (DDMStorageLink.structureId = ");
			sb.append("DDMStructure.structureId) where DDMStorageLink.");
			sb.append("classNameId = ? and DDMStructure.storageType = ?");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, PortalUtil.getClassNameId(DDMContent.class));
			ps.setString(2, "xml");

			rs = ps.executeQuery();

			Map<Long, DDMForm> ddmFormMap = new HashMap<>();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long classPK = rs.getLong("classPK");

				DDMForm ddmForm = ddmFormMap.get(structureId);

				if (ddmForm == null) {
					ddmForm = getDDMForm(structureId);

					ddmFormMap.put(structureId, ddmForm);
				}

				updateContent(ddmForm, classPK);
			}

			updateStructureStorageType();
			updateStructureVersionStorageType();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDynamicDataMapping.class);

	private class DDMFormValuesXSDDeserializer {

		public DDMFormValues deserialize(DDMForm ddmForm, String xml)
			throws PortalException {

			try {
				DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

				Document document = SAXReaderUtil.read(xml);

				Element rootElement = document.getRootElement();

				setDDMFormValuesAvailableLocales(ddmFormValues, rootElement);
				setDDMFormValuesDefaultLocale(ddmFormValues, rootElement);

				DDMFieldsCounter ddmFieldsCounter = new DDMFieldsCounter();

				for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
					String fieldName = ddmFormField.getName();

					int repetitions = countDDMFieldRepetitions(
						rootElement, fieldName, null, -1);

					for (int i = 0; i < repetitions; i++) {
						DDMFormFieldValue ddmFormFieldValue =
							createDDMFormFieldValue(fieldName);

						setDDMFormFieldValueProperties(
							ddmFormFieldValue, ddmFormField, rootElement,
							ddmFieldsCounter);

						ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
					}
				}

				return ddmFormValues;
			}
			catch (DocumentException de) {
				throw new UpgradeException(de);
			}
		}

		protected int countDDMFieldRepetitions(
			Element rootElement, String fieldName, String parentFieldName,
			int parentOffset) {

			String[] ddmFieldsDisplayValues = getDDMFieldsDisplayValues(
				rootElement, true);

			if (ddmFieldsDisplayValues.length != 0) {
				return countDDMFieldRepetitions(
					ddmFieldsDisplayValues, fieldName, parentFieldName,
					parentOffset);
			}

			Element dynamicElementElement = getDynamicElementElementByName(
				rootElement, fieldName);

			if (dynamicElementElement != null) {
				return 1;
			}

			return 0;
		}

		protected int countDDMFieldRepetitions(
			String[] fieldsDisplayValues, String fieldName,
			String parentFieldName, int parentOffset) {

			int offset = -1;

			int repetitions = 0;

			for (int i = 0; i < fieldsDisplayValues.length; i++) {
				String fieldDisplayName = fieldsDisplayValues[i];

				if (offset > parentOffset) {
					break;
				}

				if (fieldDisplayName.equals(parentFieldName)) {
					offset++;
				}

				if (fieldDisplayName.equals(fieldName) &&
					(offset == parentOffset)) {

					repetitions++;
				}
			}

			return repetitions;
		}

		protected DDMFormFieldValue createDDMFormFieldValue(String name) {
			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(name);

			return ddmFormFieldValue;
		}

		protected Set<Locale> getAvailableLocales(
			Element dynamicElementElement) {

			List<Element> dynamicContentElements =
				dynamicElementElement.elements("dynamic-content");

			Set<Locale> availableLocales = new LinkedHashSet<>();

			for (Element dynamicContentElement : dynamicContentElements) {
				String languageId = dynamicContentElement.attributeValue(
					"language-id");

				availableLocales.add(LocaleUtil.fromLanguageId(languageId));
			}

			return availableLocales;
		}

		protected Set<Locale> getAvailableLocales(
			List<Element> dynamicElementElements) {

			Set<Locale> availableLocales = new LinkedHashSet<>();

			for (Element dynamicElementElement : dynamicElementElements) {
				availableLocales.addAll(
					getAvailableLocales(dynamicElementElement));
			}

			return availableLocales;
		}

		protected String getDDMFieldInstanceId(
			Element rootElement, String fieldName, int index) {

			String[] ddmFieldsDisplayValues = getDDMFieldsDisplayValues(
				rootElement, false);

			if (ddmFieldsDisplayValues.length == 0) {
				return StringUtil.randomString();
			}

			String prefix = fieldName.concat(DDMImpl.INSTANCE_SEPARATOR);

			for (String ddmFieldsDisplayValue : ddmFieldsDisplayValues) {
				if (ddmFieldsDisplayValue.startsWith(prefix)) {
					index--;

					if (index < 0) {
						return StringUtil.extractLast(
							ddmFieldsDisplayValue, DDMImpl.INSTANCE_SEPARATOR);
					}
				}
			}

			return null;
		}

		protected String[] getDDMFieldsDisplayValues(
			Element rootElement, boolean extractFieldName) {

			Element fieldsDisplayElement = getDynamicElementElementByName(
				rootElement, "_fieldsDisplay");

			List<String> ddmFieldsDisplayValues = new ArrayList<>();

			if (fieldsDisplayElement != null) {
				Element fieldsDisplayDynamicContent =
					fieldsDisplayElement.element("dynamic-content");

				String fieldsDisplayText =
					fieldsDisplayDynamicContent.getText();

				for (String fieldDisplayValue :
						StringUtil.split(fieldsDisplayText)) {

					if (extractFieldName) {
						fieldDisplayValue = StringUtil.extractFirst(
							fieldDisplayValue, DDMImpl.INSTANCE_SEPARATOR);
					}

					ddmFieldsDisplayValues.add(fieldDisplayValue);
				}
			}

			return ddmFieldsDisplayValues.toArray(
				new String[ddmFieldsDisplayValues.size()]);
		}

		protected DDMFormFieldValue getDDMFormFieldValue(
			Element dynamicElementElement) {

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(
				dynamicElementElement.attributeValue("name"));

			List<Element> dynamicContentElements =
				dynamicElementElement.elements("dynamic-content");

			ddmFormFieldValue.setValue(getValue(dynamicContentElements));

			ddmFormFieldValue.setNestedDDMFormFields(
				getDDMFormFieldValues(
					dynamicElementElement.elements("dynamic-element")));

			return ddmFormFieldValue;
		}

		protected List<DDMFormFieldValue> getDDMFormFieldValues(
			List<Element> dynamicElementElements) {

			if (dynamicElementElements == null) {
				return null;
			}

			List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

			for (Element dynamicElement : dynamicElementElements) {
				ddmFormFieldValues.add(getDDMFormFieldValue(dynamicElement));
			}

			return ddmFormFieldValues;
		}

		protected String getDDMFormFieldValueValueString(
			Element dynamicElementElement, Locale locale, int index) {

			Element dynamicContentElement = getDynamicContentElement(
				dynamicElementElement, locale, index);

			return dynamicContentElement.getTextTrim();
		}

		protected Locale getDefaultLocale(Element dynamicElementElement) {
			String defaultLanguageId = dynamicElementElement.attributeValue(
				"default-language-id");

			return LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		protected Locale getDefaultLocale(
			List<Element> dynamicElementElements) {

			for (Element dynamicElement : dynamicElementElements) {
				String defaultLanguageId = dynamicElement.attributeValue(
					"default-language-id");

				if (defaultLanguageId != null) {
					return LocaleUtil.fromLanguageId(defaultLanguageId);
				}
			}

			return null;
		}

		protected Element getDynamicContentElement(
			Element dynamicElementElement, Locale locale, int index) {

			String languageId = LocaleUtil.toLanguageId(locale);

			XPath dynamicContentXPath = SAXReaderUtil.createXPath(
				"dynamic-content[(@language-id='" + languageId + "')]");

			List<Node> nodes = dynamicContentXPath.selectNodes(
				dynamicElementElement);

			return (Element)nodes.get(index);
		}

		protected Element getDynamicElementElementByName(
			Element rootElement, String fieldName) {

			XPath dynamicElementXPath = SAXReaderUtil.createXPath(
				"//dynamic-element[(@name=\"" + fieldName + "\")]");

			if (dynamicElementXPath.booleanValueOf(rootElement)) {
				return (Element)dynamicElementXPath.evaluate(rootElement);
			}

			return null;
		}

		protected Value getValue(List<Element> dynamicContentElements) {
			Value value = new LocalizedValue();

			for (Element dynamicContentElement : dynamicContentElements) {
				String fieldValue = dynamicContentElement.getText();

				String languageId = dynamicContentElement.attributeValue(
					"language-id");

				Locale locale = LocaleUtil.fromLanguageId(languageId);

				value.addString(locale, fieldValue);
			}

			return value;
		}

		protected void setDDMFormFieldValueInstanceId(
			DDMFormFieldValue ddmFormFieldValue, Element rootElement,
			DDMFieldsCounter ddmFieldsCounter) {

			String name = ddmFormFieldValue.getName();

			String instanceId = getDDMFieldInstanceId(
				rootElement, name, ddmFieldsCounter.get(name));

			ddmFormFieldValue.setInstanceId(instanceId);
		}

		protected void setDDMFormFieldValueLocalizedValue(
			DDMFormFieldValue ddmFormFieldValue, Element dynamicElementElement,
			int index) {

			Value value = new LocalizedValue(
				getDefaultLocale(dynamicElementElement));

			Map<String, Integer> dynamicContentValuesMap = new HashMap<>();

			for (Element dynamicContentElement : dynamicElementElement.elements(
				"dynamic-content")) {

				String languageId = dynamicContentElement.attributeValue(
					"language-id");

				int localizedContentIndex = 0;

				if (dynamicContentValuesMap.containsKey(languageId)) {
					localizedContentIndex = dynamicContentValuesMap.get(
						languageId);
				}

				if (localizedContentIndex == index) {
					Locale locale = LocaleUtil.fromLanguageId(languageId);

					String content = dynamicContentElement.getText();

					value.addString(locale, content);
				}

				dynamicContentValuesMap.put(
					languageId, localizedContentIndex+1);
			}

			ddmFormFieldValue.setValue(value);
		}

		protected void setDDMFormFieldValueProperties(
				DDMFormFieldValue ddmFormFieldValue, DDMFormField ddmFormField,
				Element rootElement, DDMFieldsCounter ddmFieldsCounter)
			throws PortalException {

			setDDMFormFieldValueInstanceId(
				ddmFormFieldValue, rootElement, ddmFieldsCounter);

			setNestedDDMFormFieldValues(
				ddmFormFieldValue, ddmFormField, rootElement, ddmFieldsCounter);

			setDDMFormFieldValueValues(
				ddmFormFieldValue, ddmFormField, rootElement, ddmFieldsCounter);
		}

		protected void setDDMFormFieldValueUnlocalizedValue(
			DDMFormFieldValue ddmFormFieldValue, Element dynamicElement,
			int index) {

			String valueString = getDDMFormFieldValueValueString(
				dynamicElement, getDefaultLocale(dynamicElement), index);

			Value value = new UnlocalizedValue(valueString);

			ddmFormFieldValue.setValue(value);
		}

		protected void setDDMFormFieldValueValues(
			DDMFormFieldValue ddmFormFieldValue, DDMFormField ddmFormField,
			Element rootElement, DDMFieldsCounter ddmFieldsCounter) {

			String fieldName = ddmFormFieldValue.getName();

			Element dynamicElement = getDynamicElementElementByName(
				rootElement, fieldName);

			if (Validator.isNotNull(ddmFormField.getDataType())) {
				if (ddmFormField.isLocalizable()) {
					setDDMFormFieldValueLocalizedValue(
						ddmFormFieldValue, dynamicElement,
						ddmFieldsCounter.get(fieldName));
				}
				else {
					setDDMFormFieldValueUnlocalizedValue(
						ddmFormFieldValue, dynamicElement,
						ddmFieldsCounter.get(fieldName));
				}
			}

			ddmFieldsCounter.incrementKey(fieldName);
		}

		protected void setDDMFormValuesAvailableLocales(
			DDMFormValues ddmFormValues, Element rootElement) {

			Set<Locale> availableLocales = getAvailableLocales(
				rootElement.elements("dynamic-element"));

			ddmFormValues.setAvailableLocales(availableLocales);
		}

		protected void setDDMFormValuesDefaultLocale(
			DDMFormValues ddmFormValues, Element rootElement) {

			Locale defaultLocale = getDefaultLocale(
				rootElement.elements("dynamic-element"));

			ddmFormValues.setDefaultLocale(defaultLocale);
		}

		protected void setNestedDDMFormFieldValues(
				DDMFormFieldValue ddmFormFieldValue, DDMFormField ddmFormField,
				Element rootElement, DDMFieldsCounter ddmFieldsCounter)
			throws PortalException {

			String fieldName = ddmFormFieldValue.getName();

			int parentOffset = ddmFieldsCounter.get(fieldName);

			Map<String, DDMFormField> nestedDDMFormFieldsMap =
				ddmFormField.getNestedDDMFormFieldsMap();

			String[] ddmFieldsDisplayValues = getDDMFieldsDisplayValues(
				rootElement, true);

			for (Map.Entry<String, DDMFormField> nestedDDMFormFieldEntry :
					nestedDDMFormFieldsMap.entrySet()) {

				String nestedDDMFormFieldName =
					nestedDDMFormFieldEntry.getKey();

				DDMFormField nestedDDMFormField =
					nestedDDMFormFieldEntry.getValue();

				int repetitions = countDDMFieldRepetitions(
					ddmFieldsDisplayValues, nestedDDMFormFieldName, fieldName,
					parentOffset);

				for (int i = 0; i < repetitions; i++) {
					DDMFormFieldValue nestedDDMFormFieldValue =
						createDDMFormFieldValue(nestedDDMFormFieldName);

					setDDMFormFieldValueProperties(
						nestedDDMFormFieldValue, nestedDDMFormField,
						rootElement, ddmFieldsCounter);

					ddmFormFieldValue.addNestedDDMFormFieldValue(
						nestedDDMFormFieldValue);
				}
			}
		}

	}

}