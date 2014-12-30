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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.upgrade.v7_0_0.util.DDMContentTable;
import com.liferay.portal.upgrade.v7_0_0.util.DDMStructureTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

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

	protected void addStructureVersions() throws Exception {
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

				addStructureVersion(
					increment(), groupId, companyId, userId, userName,
					modifiedDate, structureId, name, description, definition,
					storageType, type);
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

		addStructureVersions();
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

	protected String toJSON(DDMForm ddmForm, String xml) {
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

	protected void updateStructureStorageType(long structureId)
		throws Exception {

		runSQL(
			"update DDMStructure set storageType='json' where structureId = " +
				structureId);
	}

	protected void updateStructureVersionStorageType(long structureId)
		throws Exception {

		runSQL(
			"update DDMStructureVersion set storageType='json' where " +
				"structureId = " + structureId);
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

			Map<Long, DDMForm> ddmFormMap = new HashMap<Long, DDMForm>();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long classPK = rs.getLong("classPK");

				DDMForm ddmForm = ddmFormMap.get(structureId);

				if (ddmForm == null) {
					ddmForm = getDDMForm(structureId);

					ddmFormMap.put(structureId, ddmForm);
				}

				updateContent(ddmForm, classPK);

				updateStructureStorageType(structureId);

				updateStructureVersionStorageType(structureId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDynamicDataMapping.class);

	private class DDMFormValuesXSDDeserializer {

		public DDMFormValues deserialize(DDMForm ddmForm, String xml) {
			DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

			try {
				Document document = SAXReaderUtil.read(xml);

				Element rootElement = document.getRootElement();

				// TODO(pablo.carvalho): set ddmFormValues available and default
				// languages

				ddmFormValues.setDDMFormFieldValues(
					getDDMFormFieldValues(
						rootElement.elements("dynamic-element")));

				updateLocales(ddmFormValues);
			}
			catch (DocumentException e) {

				// TODO(pablo.carvalho): decide what to do with this exception.
				// throw again?

			}

			return ddmFormValues;
		}

		protected DDMFormFieldValue getDDMFormFieldValue(
			Element dynamicElement) {

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(dynamicElement.attributeValue("name"));

			List<Element> dynamicContentElements = dynamicElement.elements(
				"dynamic-content");

			ddmFormFieldValue.setValue(getValue(dynamicContentElements));

			ddmFormFieldValue.setNestedDDMFormFields(
				getDDMFormFieldValues(
					dynamicElement.elements("dynamic-element")));

			return ddmFormFieldValue;
		}

		protected List<DDMFormFieldValue> getDDMFormFieldValues(
			List<Element> dynamicElements) {

			if (dynamicElements == null) {
				return null;
			}

			List<DDMFormFieldValue> ddmFormFieldValues =
				new ArrayList<DDMFormFieldValue>();

			for (Element dynamicElement : dynamicElements) {
				ddmFormFieldValues.add(getDDMFormFieldValue(dynamicElement));
			}

			return ddmFormFieldValues;
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

		protected void updateLocales(DDMFormValues ddmFormValues) {
			List<DDMFormFieldValue> ddmFormFieldValues =
				ddmFormValues.getDDMFormFieldValues();

			Set<Locale> availableLocales = new LinkedHashSet<Locale>();

			Locale defaultLocale = null;

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				Value value = ddmFormFieldValue.getValue();

				for (Locale availableLocale : value.getAvailableLocales()) {
					availableLocales.add(availableLocale);
				}

				if (defaultLocale == null) {
					defaultLocale = value.getDefaultLocale();
				}
			}

			ddmFormValues.setAvailableLocales(availableLocales);
			ddmFormValues.setDefaultLocale(defaultLocale);
		}

	}

}