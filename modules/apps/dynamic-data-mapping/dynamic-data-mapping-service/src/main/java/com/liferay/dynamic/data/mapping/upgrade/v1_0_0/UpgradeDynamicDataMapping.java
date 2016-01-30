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

package com.liferay.dynamic.data.mapping.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesTransformer;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.dynamic.data.mapping.util.impl.DDMFieldsCounter;
import com.liferay.dynamic.data.mapping.util.impl.DDMImpl;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.ResourceActionLocalService;
import com.liferay.portal.service.ResourcePermissionLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoRowLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;

import java.io.File;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class UpgradeDynamicDataMapping extends UpgradeProcess {

	public UpgradeDynamicDataMapping(
		AssetEntryLocalService assetEntryLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFolderLocalService dlFolderLocalService,
		ExpandoRowLocalService expandoRowLocalService,
		ExpandoTableLocalService expandoTableLocalService,
		ExpandoValueLocalService expandoValueLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_dlFileEntryLocalService = dlFileEntryLocalService;
		_dlFileVersionLocalService = dlFileVersionLocalService;
		_dlFolderLocalService = dlFolderLocalService;
		_expandoRowLocalService = expandoRowLocalService;
		_expandoTableLocalService = expandoTableLocalService;
		_expandoValueLocalService = expandoValueLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	protected void addDDMContent(
			String uuid_, long contentId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, String name, String description,
			String data)
		throws Exception {

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(4);

			sb.append("insert into DDMContent (uuid_, contentId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, name, description, data_) values (?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setString(1, uuid_);
			ps.setLong(2, contentId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, modifiedDate);
			ps.setString(9, name);
			ps.setString(10, description);
			ps.setString(11, data);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error("Unable to add dynamic data mapping content", e);

			throw e;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void addDynamicContentElements(
		Element dynamicElementElement, String name, String data) {

		Map<Locale, String> localizationMap =
			LocalizationUtil.getLocalizationMap(data);

		for (Map.Entry<Locale, String> entry : localizationMap.entrySet()) {
			String[] values = StringUtil.split(entry.getValue());

			if (name.startsWith(StringPool.UNDERLINE)) {
				values = new String[] {entry.getValue()};
			}

			for (String value : values) {
				Element dynamicContentElement =
					dynamicElementElement.addElement("dynamic-content");

				dynamicContentElement.addAttribute(
					"language-id", LanguageUtil.getLanguageId(entry.getKey()));
				dynamicContentElement.addCDATA(value.trim());
			}
		}
	}

	protected void addStructureLayout(
			String uuid_, long structureLayoutId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, long structureVersionId, String definition)
		throws Exception {

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(5);

			sb.append("insert into DDMStructureLayout (uuid_, ");
			sb.append("structureLayoutId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, ");
			sb.append("structureVersionId, definition) values (?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

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
			DataAccess.cleanUp(ps);
		}
	}

	protected void addStructureVersion(
			long structureVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long structureId,
			long parentStructureId, String name, String description,
			String definition, String storageType, int type, int status,
			long statusByUserId, String statusByUserName, Timestamp statusDate)
		throws Exception {

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMStructureVersion (structureVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("structureId, version, parentStructureId, name, ");
			sb.append("description, definition, storageType, type_, status, ");
			sb.append("statusByUserId, statusByUserName, statusDate) values ");
			sb.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setLong(1, structureVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setLong(7, structureId);
			ps.setString(8, DDMStructureConstants.VERSION_DEFAULT);
			ps.setLong(9, parentStructureId);
			ps.setString(10, name);
			ps.setString(11, description);
			ps.setString(12, definition);
			ps.setString(13, storageType);
			ps.setInt(14, type);
			ps.setInt(15, status);
			ps.setLong(16, statusByUserId);
			ps.setString(17, statusByUserName);
			ps.setTimestamp(18, statusDate);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure version " +
					"with structure ID " + structureId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void addTemplateVersion(
			long templateVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long classNameId,
			long classPK, long templateId, String name, String description,
			String language, String script, int status, long statusByUserId,
			String statusByUserName, Timestamp statusDate)
		throws Exception {

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMTemplateVersion (templateVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("classNameId, classPK, templateId, version, name, ");
			sb.append("description, language, script, status, ");
			sb.append("statusByUserId, statusByUserName, statusDate) values (");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setLong(1, templateVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setLong(7, classNameId);
			ps.setLong(8, classPK);
			ps.setLong(9, templateId);
			ps.setString(10, DDMStructureConstants.VERSION_DEFAULT);
			ps.setString(11, name);
			ps.setString(12, description);
			ps.setString(13, language);
			ps.setString(14, script);
			ps.setInt(15, status);
			ps.setLong(16, statusByUserId);
			ps.setString(17, statusByUserName);
			ps.setTimestamp(18, statusDate);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping template version " +
					"with template ID " + templateId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected String createNewDDMFormFieldName(
			String fieldName, Set<String> existingFieldNames)
		throws Exception {

		String newFieldName = fieldName.replaceAll(
			_INVALID_FIELD_NAME_CHARS_REGEX, StringPool.BLANK);

		if (Validator.isNotNull(newFieldName) &&
			!existingFieldNames.contains(newFieldName)) {

			return newFieldName;
		}

		throw new UpgradeException(
			String.format(
				"Unable to automatically update field name \"%s\" because it " +
					"only contains invalid characters or the updated value " +
						"\"%s\" conflicts with a previous field name",
				fieldName, newFieldName));
	}

	protected void deleteExpandoData(Set<Long> expandoRowIds)
		throws PortalException {

		Set<Long> expandoTableIds = new HashSet<>();

		for (long expandoRowId : expandoRowIds) {
			ExpandoRow expandoRow = _expandoRowLocalService.fetchExpandoRow(
				expandoRowId);

			if (expandoRow != null) {
				expandoTableIds.add(expandoRow.getTableId());
			}
		}

		for (long expandoTableId : expandoTableIds) {
			try {
				_expandoTableLocalService.deleteTable(expandoTableId);
			}
			catch (PortalException pe) {
				_log.error("Unable delete expando table", pe);

				throw pe;
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		setUpClassNameIds();

		upgradeExpandoStorageAdapter();

		upgradeStructuresAndAddStructureVersionsAndLayouts();
		upgradeTemplatesAndAddTemplateVersions();
		upgradeXMLStorageAdapter();

		upgradeFieldTypeReferences();

		upgradeStructuresPermissions();
		upgradeTemplatesPermissions();
	}

	protected DDMForm getDDMForm(long structureId) throws Exception {
		DDMForm ddmForm = _ddmForms.get(structureId);

		if (ddmForm != null) {
			return ddmForm;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select parentStructureId, definition, storageType from " +
					"DDMStructure where structureId = ?" );

			ps.setLong(1, structureId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long parentStructureId = rs.getLong("parentStructureId");
				String definition = rs.getString("definition");
				String storageType = rs.getString("storageType");

				if (storageType.equals("expando") ||
					storageType.equals("xml")) {

					ddmForm = DDMFormXSDDeserializerUtil.deserialize(
						definition);
				}
				else {
					ddmForm = DDMFormJSONDeserializerUtil.deserialize(
						definition);
				}

				if (parentStructureId > 0) {
					DDMForm parentDDMForm = getDDMForm(parentStructureId);

					Set<String> commonDDMFormFieldNames = SetUtil.intersect(
						getDDMFormFieldsNames(parentDDMForm),
						getDDMFormFieldsNames(ddmForm));

					if (!commonDDMFormFieldNames.isEmpty()) {
						throw new UpgradeException(
							"Duplicate DDM form field names: " +
								StringUtil.merge(commonDDMFormFieldNames));
					}
				}

				_ddmForms.put(structureId, ddmForm);

				return ddmForm;
			}

			throw new UpgradeException(
				"Unable to find dynamic data mapping structure with ID " +
					structureId);
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected Set<String> getDDMFormFieldsNames(DDMForm ddmForm) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Set<String> ddmFormFieldsNames = new HashSet<>(ddmFormFieldsMap.size());

		for (String ddmFormFieldName : ddmFormFieldsMap.keySet()) {
			ddmFormFieldsNames.add(StringUtil.toLowerCase(ddmFormFieldName));
		}

		return ddmFormFieldsNames;
	}

	protected DDMFormValues getDDMFormValues(
			long companyId, DDMForm ddmForm, String xml)
		throws Exception {

		DDMFormValuesXSDDeserializer ddmFormValuesXSDDeserializer =
			new DDMFormValuesXSDDeserializer(companyId);

		return ddmFormValuesXSDDeserializer.deserialize(ddmForm, xml);
	}

	protected String getDefaultDDMFormLayoutDefinition(DDMForm ddmForm) {
		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return DDMFormLayoutJSONSerializerUtil.serialize(ddmFormLayout);
	}

	protected Map<String, String> getExpandoValuesMap(long expandoRowId)
		throws PortalException {

		Map<String, String> fieldsMap = new HashMap<>();

		List<ExpandoValue> expandoValues =
			_expandoValueLocalService.getRowValues(expandoRowId);

		for (ExpandoValue expandoValue : expandoValues) {
			ExpandoColumn expandoColumn = expandoValue.getColumn();

			fieldsMap.put(expandoColumn.getName(), expandoValue.getData());
		}

		return fieldsMap;
	}

	protected String getStructureModelResourceName(long classNameId)
		throws UpgradeException {

		String className = PortalUtil.getClassName(classNameId);

		String structureModelResourceName = _structureModelResourceNames.get(
			className);

		if (structureModelResourceName == null) {
			throw new UpgradeException(
				"Model " + className + " does not support DDM structure " +
					"permission checking");
		}

		return structureModelResourceName;
	}

	protected String getTemplateModelResourceName(long classNameId)
		throws UpgradeException {

		String className = PortalUtil.getClassName(classNameId);

		String templateModelResourceName = _templateModelResourceNames.get(
			className);

		if (templateModelResourceName == null) {
			throw new UpgradeException(
				"Model " + className + " does not support DDM template " +
					"permission checking");
		}

		return templateModelResourceName;
	}

	protected long getTemplateResourceClassNameId(
		long classNameId, long classPK) {

		if (classNameId != PortalUtil.getClassNameId(DDMStructure.class)) {
			return PortalUtil.getClassNameId(
				"com.liferay.portlet.display.template.PortletDisplayTemplate");
		}

		if (classPK == 0) {
			return PortalUtil.getClassNameId(
				"com.liferay.journal.model.JournalArticle");
		}

		return _structureClassNameIds.get(classPK);
	}

	protected boolean hasStructureVersion(long structureId, String version)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select * from DDMStructureVersion where structureId = ? and " +
					"version = ?");

			ps.setLong(1, structureId);
			ps.setString(2, version);

			rs = ps.executeQuery();

			return rs.next();
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected boolean hasTemplateVersion(long templateId, String version)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select * from DDMTemplateVersion where templateId = ? and " +
					"version = ?");

			ps.setLong(1, templateId);
			ps.setString(2, version);

			rs = ps.executeQuery();

			return rs.next();
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected boolean isInvalidFieldName(String fieldName) {
		Matcher matcher = _invalidFieldNameCharsPattern.matcher(fieldName);

		return matcher.find();
	}

	protected void populateStructureInvalidDDMFormFieldNamesMap(
		long structureId, DDMForm ddmForm) {

		Map<String, String> ddmFormFieldNamesMap = new HashMap<>();

		Map<String, DDMFormField> ddmFormFieldMap = ddmForm.getDDMFormFieldsMap(
			true);

		for (DDMFormField ddmFormField : ddmFormFieldMap.values()) {
			String oldName = (String)ddmFormField.getProperty("oldName");

			if (oldName == null) {
				continue;
			}

			ddmFormFieldNamesMap.put(oldName, ddmFormField.getName());
		}

		_structureInvalidDDMFormFieldNamesMap.put(
			structureId, ddmFormFieldNamesMap);
	}

	protected String renameInvalidDDMFormFieldNames(
		long structureId, String string) {

		Map<String, String> ddmFormFieldNamesMap =
			_structureInvalidDDMFormFieldNamesMap.get(structureId);

		if ((ddmFormFieldNamesMap == null) || ddmFormFieldNamesMap.isEmpty()) {
			return string;
		}

		Set<String> oldFieldNames = ddmFormFieldNamesMap.keySet();

		String[] oldSub = oldFieldNames.toArray(
			new String[oldFieldNames.size()]);

		String[] newSub = new String[oldFieldNames.size()];

		for (int i = 0; i < oldSub.length; i++) {
			newSub[i] = ddmFormFieldNamesMap.get(oldSub[i]);
		}

		return StringUtil.replace(string, oldSub, newSub);
	}

	protected void setUpClassNameIds() {
		_ddmContentClassNameId = PortalUtil.getClassNameId(DDMContent.class);

		_expandoStorageAdapterClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.dynamicdatamapping.storage." +
				"ExpandoStorageAdapter");
	}

	protected String toJSON(DDMForm ddmForm) {
		return DDMFormJSONSerializerUtil.serialize(ddmForm);
	}

	protected String toJSON(DDMFormValues ddmFormValues) {
		return DDMFormValuesJSONSerializerUtil.serialize(ddmFormValues);
	}

	protected String toXML(Map<String, String> expandoValuesMap) {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		for (Map.Entry<String, String> entry : expandoValuesMap.entrySet()) {
			Element dynamicElementElement = rootElement.addElement(
				"dynamic-element");

			String name = entry.getKey();
			String data = entry.getValue();

			dynamicElementElement.addAttribute("name", name);
			dynamicElementElement.addAttribute(
				"default-language-id",
				LocalizationUtil.getDefaultLanguageId(data));

			addDynamicContentElements(dynamicElementElement, name, data);
		}

		return document.asXML();
	}

	protected void transformFieldTypeDDMFormFields(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long entryId, String entryVersion,
			DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new FileUploadDDMFormFieldValueTransformer(
				groupId, companyId, userId, userName, createDate, entryId,
				entryVersion));

		ddmFormValuesTransformer.addTransformer(
			new DateDDMFormFieldValueTransformer());

		ddmFormValuesTransformer.transform();
	}

	protected void updateContent(
			long structureId, long contentId, DDMForm ddmForm)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select companyId, data_ from DDMContent where contentId = ?");

			ps.setLong(1, contentId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long companyId = rs.getLong("companyId");
				String xml = rs.getString("data_");

				DDMFormValues ddmFormValues = getDDMFormValues(
					companyId, ddmForm, xml);

				String content = toJSON(ddmFormValues);

				content = renameInvalidDDMFormFieldNames(structureId, content);

				updateContent(contentId, content);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void updateContent(long contentId, String data_)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"update DDMContent set data_= ? where contentId = ?");

			ps.setString(1, data_);
			ps.setLong(2, contentId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected DDMForm updateDDMFormFields(DDMForm ddmForm) throws Exception {
		DDMForm copyDDMForm = new DDMForm(ddmForm);

		Map<String, DDMFormField> ddmFormFieldsMap =
			copyDDMForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			String fieldName = ddmFormField.getName();

			if (isInvalidFieldName(fieldName)) {
				String newFieldName = createNewDDMFormFieldName(
					fieldName, ddmFormFieldsMap.keySet());

				ddmFormField.setName(newFieldName);
				ddmFormField.setProperty("oldName", fieldName);
			}

			String dataType = ddmFormField.getDataType();

			if (Validator.equals(dataType, "file-upload")) {
				ddmFormField.setDataType("document-library");
				ddmFormField.setType("ddm-documentlibrary");
			}
			else if (Validator.equals(dataType, "image")) {
				ddmFormField.setFieldNamespace("ddm");
				ddmFormField.setType("ddm-image");
			}
		}

		return copyDDMForm;
	}

	protected void updateDDMStorageLink(
			long oldClassNameId, long classPK, long newClassNameId)
		throws Exception {

		runSQL(
			"update DDMStorageLink set classNameId = " + newClassNameId +
				" where classNameId = " +
					oldClassNameId + " and classPK = " + classPK);
	}

	protected void updateDDMStructureStorageType() throws Exception {
		runSQL(
			"update DDMStructure set storageType = 'xml' where storageType = " +
				"'expando'");
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

	protected void updateTemplateScript(long templateId, String script)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update DDMTemplate set script = ? where templateId = ?");

			ps.setString(1, script);
			ps.setLong(2, templateId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to update dynamic data mapping template with " +
					"template ID " + templateId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void upgradeDDLFieldTypeReferences() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(7);

			sb.append("select DDLRecordVersion.*, DDMContent.data_, ");
			sb.append("DDMStructure.structureId from DDLRecordVersion inner ");
			sb.append("join DDLRecordSet on DDLRecordVersion.recordSetId = ");
			sb.append("DDLRecordSet.recordSetId inner join DDMContent on  ");
			sb.append("DDLRecordVersion.DDMStorageId = DDMContent.contentId ");
			sb.append("inner join DDMStructure on DDLRecordSet.");
			sb.append("DDMStructureId = DDMStructure.structureId");

			ps = connection.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				long entryId = rs.getLong("recordId");
				String entryVersion = rs.getString("version");
				long contentId = rs.getLong("ddmStorageId");
				String data_ = rs.getString("data_");
				long ddmStructureId = rs.getLong("structureId");

				DDMForm ddmForm = getDDMForm(ddmStructureId);

				DDMFormValues ddmFormValues =
					DDMFormValuesJSONDeserializerUtil.deserialize(
						ddmForm, data_);

				transformFieldTypeDDMFormFields(
					groupId, companyId, userId, userName, createDate, entryId,
					entryVersion, ddmFormValues);

				updateContent(contentId, toJSON(ddmFormValues));
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void upgradeDLFieldTypeReferences() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(10);

			sb.append("select DLFileVersion.*, DDMContent.contentId, ");
			sb.append("DDMContent.data_, DDMStructure.structureId from ");
			sb.append("DLFileEntryMetadata inner join DDMContent on ");
			sb.append("DLFileEntryMetadata.DDMStorageId = DDMContent.");
			sb.append("contentId inner join DDMStructure on ");
			sb.append("DLFileEntryMetadata.DDMStructureId = DDMStructure.");
			sb.append("structureId inner join DLFileVersion on ");
			sb.append("DLFileEntryMetadata.fileVersionId = DLFileVersion.");
			sb.append("fileVersionId and DLFileEntryMetadata.fileEntryId = ");
			sb.append("DLFileVersion.fileEntryId");

			ps = connection.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				long entryId = rs.getLong("fileEntryId");
				String entryVersion = rs.getString("version");
				long contentId = rs.getLong("contentId");
				String data_ = rs.getString("data_");
				long ddmStructureId = rs.getLong("structureId");

				DDMForm ddmForm = getDDMForm(ddmStructureId);

				DDMFormValues ddmFormValues =
					DDMFormValuesJSONDeserializerUtil.deserialize(
						ddmForm, data_);

				transformFieldTypeDDMFormFields(
					groupId, companyId, userId, userName, createDate, entryId,
					entryVersion, ddmFormValues);

				updateContent(contentId, toJSON(ddmFormValues));
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void upgradeExpandoStorageAdapter() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(5);

			sb.append("select DDMStructure.*, DDMStorageLink.* from ");
			sb.append("DDMStorageLink inner join DDMStructure on ");
			sb.append("DDMStorageLink.structureId = DDMStructure.structureId ");
			sb.append("where DDMStructure.storageType = 'expando'");

			ps = connection.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			Set<Long> expandoRowIds = new HashSet<>();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				long expandoRowId = rs.getLong("classPK");

				Map<String, String> expandoValuesMap = getExpandoValuesMap(
					expandoRowId);

				String xml = toXML(expandoValuesMap);

				addDDMContent(
					PortalUUIDUtil.generate(), expandoRowId, groupId, companyId,
					userId, userName, createDate, createDate,
					DDMStorageLink.class.getName(), null, xml);

				updateDDMStorageLink(
					_expandoStorageAdapterClassNameId, expandoRowId,
					_ddmContentClassNameId);

				expandoRowIds.add(expandoRowId);
			}

			if (expandoRowIds.isEmpty()) {
				return;
			}

			updateDDMStructureStorageType();

			deleteExpandoData(expandoRowIds);
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void upgradeFieldTypeReferences() throws Exception {
		upgradeDDLFieldTypeReferences();
		upgradeDLFieldTypeReferences();
	}

	protected void upgradeStructureDefinition(
			long structureId, String definition)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update DDMStructure set definition = ? where structureId = ?");

			ps.setString(1, definition);
			ps.setLong(2, structureId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure with " +
					"structure ID " + structureId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void upgradeStructurePermissions(long companyId, long structureId)
		throws Exception {

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				companyId, DDMStructure.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				StringUtil.valueOf(structureId));

		for (ResourcePermission resourcePermission : resourcePermissions) {
			Long classNameId = _structureClassNameIds.get(
				Long.valueOf(resourcePermission.getPrimKey()));

			if (classNameId == null) {
				continue;
			}

			String resourceName = getStructureModelResourceName(classNameId);

			resourcePermission.setName(resourceName);

			_resourcePermissionLocalService.updateResourcePermission(
				resourcePermission);
		}
	}

	protected void upgradeStructuresAndAddStructureVersionsAndLayouts()
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement("select * from DDMStructure");

			rs = ps.executeQuery();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long parentStructureId = rs.getLong("parentStructureId");
				long classNameId = rs.getLong("classNameId");
				String version = rs.getString("version");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String storageType = rs.getString("storageType");
				int type = rs.getInt("type_");

				_structureClassNameIds.put(structureId, classNameId);

				// Structure content

				DDMForm ddmForm = getDDMForm(structureId);

				ddmForm = updateDDMFormFields(ddmForm);

				populateStructureInvalidDDMFormFieldNamesMap(
					structureId, ddmForm);

				String definition = toJSON(ddmForm);

				upgradeStructureDefinition(structureId, definition);

				// Structure version

				if (hasStructureVersion(structureId, version)) {
					continue;
				}

				long structureVersionId = increment();

				addStructureVersion(
					structureVersionId, groupId, companyId, userId, userName,
					modifiedDate, structureId, parentStructureId, name,
					description, definition, storageType, type,
					WorkflowConstants.STATUS_APPROVED, userId, userName,
					modifiedDate);

				// Structure layout

				String ddmFormLayoutDefinition =
					getDefaultDDMFormLayoutDefinition(ddmForm);

				addStructureLayout(
					PortalUUIDUtil.generate(), increment(), groupId, companyId,
					userId, userName, modifiedDate, modifiedDate,
					structureVersionId, ddmFormLayoutDefinition);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void upgradeStructuresPermissions() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement("select * from DDMStructure");

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long structureId = rs.getLong("structureId");

				upgradeStructurePermissions(companyId, structureId);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void upgradeTemplatePermissions(long companyId, long templateId)
		throws Exception {

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				companyId, DDMTemplate.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				StringUtil.valueOf(templateId));

		for (ResourcePermission resourcePermission : resourcePermissions) {
			Long classNameId = _templateResourceClassNameIds.get(
				Long.valueOf(resourcePermission.getPrimKey()));

			if (classNameId == null) {
				continue;
			}

			String resourceName = getTemplateModelResourceName(classNameId);

			resourcePermission.setName(resourceName);

			_resourcePermissionLocalService.updateResourcePermission(
				resourcePermission);
		}
	}

	protected void upgradeTemplateResourceClassNameId(
			long templateId, long resourceClassNameId)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update DDMTemplate set resourceClassNameId = ? where " +
					"templateId = ?");

			ps.setLong(1, resourceClassNameId);
			ps.setLong(2, templateId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void upgradeTemplatesAndAddTemplateVersions() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement("select * from DDMTemplate");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");
				long templateId = rs.getLong("templateId");
				String version = rs.getString("version");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String language = rs.getString("language");
				String script = rs.getString("script");

				// Template resource class name ID

				long resourceClassNameId = getTemplateResourceClassNameId(
					classNameId, classPK);

				upgradeTemplateResourceClassNameId(
					templateId, resourceClassNameId);

				_templateResourceClassNameIds.put(
					templateId, resourceClassNameId);

				// Template content

				String updatedScript = renameInvalidDDMFormFieldNames(
					classPK, script);

				if (language.equals("xsd")) {
					DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(
						updatedScript);

					ddmForm = updateDDMFormFields(ddmForm);

					updatedScript = toJSON(ddmForm);
				}

				if (!script.equals(updatedScript)) {
					updateTemplateScript(templateId, updatedScript);
				}

				// Template version

				if (!hasTemplateVersion(templateId, version)) {
					addTemplateVersion(
						increment(), groupId, companyId, userId, userName,
						modifiedDate, classNameId, classPK, templateId, name,
						description, language, updatedScript,
						WorkflowConstants.STATUS_APPROVED, userId, userName,
						modifiedDate);
				}
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void upgradeTemplateScript(long templateId, String script)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update DDMTemplate set language = ?, script = ? where " +
					"templateId = ?");

			ps.setString(1, "json");
			ps.setString(2, script);
			ps.setLong(3, templateId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping template with " +
					"template ID " + templateId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void upgradeTemplatesPermissions() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement("select * from DDMTemplate");

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long templateId = rs.getLong("templateId");

				upgradeTemplatePermissions(companyId, templateId);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void upgradeXMLStorageAdapter() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(5);

			sb.append("select DDMStorageLink.classPK, DDMStorageLink.");
			sb.append("structureId from DDMStorageLink inner join ");
			sb.append("DDMStructure on (DDMStorageLink.structureId = ");
			sb.append("DDMStructure.structureId) where DDMStorageLink.");
			sb.append("classNameId = ? and DDMStructure.storageType = ?");

			ps = connection.prepareStatement(sb.toString());

			ps.setLong(1, _ddmContentClassNameId);
			ps.setString(2, "xml");

			rs = ps.executeQuery();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long classPK = rs.getLong("classPK");

				DDMForm ddmForm = getDDMForm(structureId);

				updateContent(structureId, classPK, ddmForm);
			}

			updateStructureStorageType();
			updateStructureVersionStorageType();
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	private static final String _INVALID_FIELD_NAME_CHARS_REGEX =
		"([\\p{Punct}&&[^_]]|\\p{Space})+";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDynamicDataMapping.class);

	private static final Pattern _invalidFieldNameCharsPattern =
		Pattern.compile(_INVALID_FIELD_NAME_CHARS_REGEX);
	private static final Map<String, String> _structureModelResourceNames =
		new HashMap<>();
	private static final Map<String, String> _templateModelResourceNames =
		new HashMap<>();

	static {
		_structureModelResourceNames.put(
			"com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata",
			"com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata-" +
				DDMStructure.class.getName());

		_structureModelResourceNames.put(
			"com.liferay.portlet.documentlibrary.util.RawMetadataProcessor",
			DDMStructure.class.getName());

		_structureModelResourceNames.put(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordSet",
			"com.liferay.dynamic.data.lists.model.DDLRecordSet-" +
				DDMStructure.class.getName());

		_structureModelResourceNames.put(
			"com.liferay.portlet.journal.model.JournalArticle",
			"com.liferay.journal.model.JournalArticle-" +
				DDMStructure.class.getName());

		_templateModelResourceNames.put(
			"com.liferay.portlet.display.template.PortletDisplayTemplate",
			DDMTemplate.class.getName());

		_templateModelResourceNames.put(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordSet",
			"com.liferay.dynamic.data.lists.model.DDLRecordSet-" +
				DDMTemplate.class.getName());

		_templateModelResourceNames.put(
			"com.liferay.portlet.journal.model.JournalArticle",
			"com.liferay.journal.model.JournalArticle-" +
				DDMTemplate.class.getName());
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private long _ddmContentClassNameId;
	private final Map<Long, DDMForm> _ddmForms = new HashMap<>();
	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private final DLFileVersionLocalService _dlFileVersionLocalService;
	private final DLFolderLocalService _dlFolderLocalService;
	private final ExpandoRowLocalService _expandoRowLocalService;
	private long _expandoStorageAdapterClassNameId;
	private final ExpandoTableLocalService _expandoTableLocalService;
	private final ExpandoValueLocalService _expandoValueLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final Map<Long, Long> _structureClassNameIds = new HashMap<>();
	private final Map<Long, Map<String, String>>
		_structureInvalidDDMFormFieldNamesMap = new HashMap<>();
	private final Map<Long, Long> _templateResourceClassNameIds =
		new HashMap<>();

	private static class DateDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		@Override
		public String getFieldType() {
			return DDMImpl.TYPE_DDM_DATE;
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				if (Validator.isNull(valueString) ||
					!Validator.isNumber(valueString)) {

					continue;
				}

				Date dateValue = new Date(GetterUtil.getLong(valueString));

				value.addString(locale, _dateFormat.format(dateValue));
			}
		}

		private final DateFormat _dateFormat =
			DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");

	}

	private static class DDMFormValuesXSDDeserializer {

		public DDMFormValuesXSDDeserializer(long companyId) {
			_companyId = companyId;
		}

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
			if (dynamicElementElement == null) {
				String locale = null;

				try {
					locale = UpgradeProcessUtil.getDefaultLanguageId(
						_companyId);
				}
				catch (SQLException sqle) {
					_log.error(
						"Unable to get default locale for company " +
							_companyId,
						sqle);

					throw new RuntimeException(sqle);
				}

				return LocaleUtil.fromLanguageId(locale);
			}

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

			for (Element dynamicContentElement :
					dynamicElementElement.elements("dynamic-content")) {

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

			if (Validator.isNotNull(ddmFormField.getDataType()) &&
				(dynamicElement != null)) {

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

		private long _companyId;

	}

	private class FileUploadDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		public FileUploadDDMFormFieldValueTransformer(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long entryId, String entryVersion) {

			_groupId = groupId;
			_companyId = companyId;
			_userId = userId;
			_userName = userName;
			_createDate = createDate;
			_entryId = entryId;
			_entryVersion = entryVersion;
		}

		@Override
		public String getFieldType() {
			return "ddm-fileupload";
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				if (Validator.isNull(valueString)) {
					continue;
				}

				String fileEntryUuid = PortalUUIDUtil.generate();

				upgradeFileUploadReference(
					fileEntryUuid, ddmFormFieldValue.getName(), valueString);

				value.addString(locale, toJSON(_groupId, fileEntryUuid));
			}
		}

		protected void addAssetEntry(
				long entryId, long groupId, long companyId, long userId,
				String userName, Timestamp createDate, Timestamp modifiedDate,
				long classNameId, long classPK, String classUuid,
				long classTypeId, boolean visible, Timestamp startDate,
				Timestamp endDate, Timestamp publishDate,
				Timestamp expirationDate, String mimeType, String title,
				String description, String summary, String url,
				String layoutUuid, int height, int width, double priority,
				int viewCount)
			throws Exception {

			AssetEntry assetEntry = _assetEntryLocalService.createAssetEntry(
				entryId);

			assetEntry.setGroupId(groupId);
			assetEntry.setCompanyId(companyId);
			assetEntry.setUserId(userId);
			assetEntry.setUserName(userName);
			assetEntry.setCreateDate(createDate);
			assetEntry.setModifiedDate(modifiedDate);
			assetEntry.setClassNameId(classNameId);
			assetEntry.setClassPK(classPK);
			assetEntry.setClassUuid(classUuid);
			assetEntry.setClassTypeId(classTypeId);
			assetEntry.setVisible(visible);
			assetEntry.setStartDate(startDate);
			assetEntry.setEndDate(endDate);
			assetEntry.setPublishDate(publishDate);
			assetEntry.setExpirationDate(expirationDate);
			assetEntry.setMimeType(mimeType);
			assetEntry.setTitle(title);
			assetEntry.setDescription(description);
			assetEntry.setSummary(summary);
			assetEntry.setUrl(url);
			assetEntry.setLayoutUuid(layoutUuid);
			assetEntry.setHeight(height);
			assetEntry.setWidth(width);
			assetEntry.setPriority(priority);
			assetEntry.setViewCount(viewCount);

			_assetEntryLocalService.updateAssetEntry(assetEntry);
		}

		protected long addDDMDLFolder() throws Exception {
			long ddmFolderId = getDLFolderId(
				_groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "DDM");

			if (ddmFolderId > 0) {
				return ddmFolderId;
			}

			ddmFolderId = increment();

			addDLFolder(
				PortalUUIDUtil.generate(), ddmFolderId, _groupId, _companyId,
				_userId, _userName, _now, _now, _groupId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "DDM",
				StringPool.BLANK, _now);

			return ddmFolderId;
		}

		protected void addDLFileEntry(
				String uuid, long fileEntryId, long groupId, long companyId,
				long userId, String userName, Timestamp createDate,
				Timestamp modifiedDate, long classNameId, long classPK,
				long repositoryId, long folderId, String treePath, String name,
				String fileName, String extension, String mimeType,
				String title, String description, String extraSettings,
				long fileEntryTypeId, String version, long size, int readCount,
				long smallImageId, long largeImageId, long custom1ImageId,
				long custom2ImageId, boolean manualCheckInRequired)
			throws Exception {

			DLFileEntry dlFileEntry =
				_dlFileEntryLocalService.createDLFileEntry(fileEntryId);

			dlFileEntry.setUuid(uuid);
			dlFileEntry.setGroupId(groupId);
			dlFileEntry.setCompanyId(companyId);
			dlFileEntry.setUserId(userId);
			dlFileEntry.setUserName(userName);
			dlFileEntry.setCreateDate(createDate);
			dlFileEntry.setModifiedDate(modifiedDate);
			dlFileEntry.setClassNameId(classNameId);
			dlFileEntry.setClassPK(classPK);
			dlFileEntry.setRepositoryId(repositoryId);
			dlFileEntry.setFolderId(folderId);
			dlFileEntry.setTreePath(treePath);
			dlFileEntry.setName(name);
			dlFileEntry.setFileName(fileName);
			dlFileEntry.setExtension(extension);
			dlFileEntry.setMimeType(mimeType);
			dlFileEntry.setTitle(title);
			dlFileEntry.setDescription(description);
			dlFileEntry.setExtraSettings(extraSettings);
			dlFileEntry.setFileEntryTypeId(fileEntryTypeId);
			dlFileEntry.setVersion(version);
			dlFileEntry.setSize(size);
			dlFileEntry.setReadCount(readCount);
			dlFileEntry.setSmallImageId(smallImageId);
			dlFileEntry.setLargeImageId(largeImageId);
			dlFileEntry.setCustom1ImageId(custom1ImageId);
			dlFileEntry.setCustom2ImageId(custom2ImageId);
			dlFileEntry.setManualCheckInRequired(manualCheckInRequired);

			_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);
		}

		protected void addDLFileVersion(
				String uuid, long fileVersionId, long groupId, long companyId,
				long userId, String userName, Timestamp createDate,
				Timestamp modifiedDate, long repositoryId, long folderId,
				long fileEntryId, String treePath, String fileName,
				String extension, String mimeType, String title,
				String description, String changeLog, String extraSettings,
				long fileEntryTypeId, String version, long size,
				String checksum, int status, long statusByUserId,
				String statusByUserName, Timestamp statusDate)
			throws Exception {

			DLFileVersion dlFileVersion =
				_dlFileVersionLocalService.createDLFileVersion(fileVersionId);

			dlFileVersion.setUuid(uuid);
			dlFileVersion.setGroupId(groupId);
			dlFileVersion.setCompanyId(companyId);
			dlFileVersion.setUserId(userId);
			dlFileVersion.setUserName(userName);
			dlFileVersion.setCreateDate(createDate);
			dlFileVersion.setModifiedDate(modifiedDate);
			dlFileVersion.setRepositoryId(repositoryId);
			dlFileVersion.setFolderId(folderId);
			dlFileVersion.setFileEntryId(fileEntryId);
			dlFileVersion.setTreePath(treePath);
			dlFileVersion.setFileName(fileName);
			dlFileVersion.setExtension(extension);
			dlFileVersion.setMimeType(mimeType);
			dlFileVersion.setTitle(title);
			dlFileVersion.setDescription(description);
			dlFileVersion.setChangeLog(changeLog);
			dlFileVersion.setExtraSettings(extraSettings);
			dlFileVersion.setFileEntryTypeId(fileEntryTypeId);
			dlFileVersion.setVersion(version);
			dlFileVersion.setSize(size);
			dlFileVersion.setChecksum(checksum);
			dlFileVersion.setStatus(status);
			dlFileVersion.setStatusByUserId(statusByUserId);
			dlFileVersion.setStatusByUserName(statusByUserName);
			dlFileVersion.setStatusDate(statusDate);

			_dlFileVersionLocalService.updateDLFileVersion(dlFileVersion);
		}

		protected void addDLFolder(
				String uuid, long folderId, long groupId, long companyId,
				long userId, String userName, Timestamp createDate,
				Timestamp modifiedDate, long repositoryId, long parentFolderId,
				String name, String description, Timestamp lastPostDate)
			throws Exception {

			DLFolder dlFolder = _dlFolderLocalService.createDLFolder(folderId);

			dlFolder.setUuid(uuid);
			dlFolder.setGroupId(groupId);
			dlFolder.setCompanyId(companyId);
			dlFolder.setUserId(userId);
			dlFolder.setUserName(userName);
			dlFolder.setCreateDate(createDate);
			dlFolder.setModifiedDate(modifiedDate);
			dlFolder.setRepositoryId(repositoryId);
			dlFolder.setMountPoint(false);
			dlFolder.setParentFolderId(parentFolderId);
			dlFolder.setName(name);
			dlFolder.setDescription(description);
			dlFolder.setLastPostDate(lastPostDate);
			dlFolder.setDefaultFileEntryTypeId(0);
			dlFolder.setHidden(false);
			dlFolder.setStatus(WorkflowConstants.STATUS_APPROVED);
			dlFolder.setStatusByUserId(0);
			dlFolder.setStatusByUserName(StringPool.BLANK);
			dlFolder.setRestrictionType(0);

			_dlFolderLocalService.updateDLFolder(dlFolder);
		}

		protected long addDLFolderTree(String ddmFormFieldName)
			throws Exception {

			long ddmFolderId = addDDMDLFolder();

			long entryIdFolderId = addEntryIdDLFolder(ddmFolderId);

			long entryVersionFolderId = addEntryVersionDLFolder(
				entryIdFolderId);

			long fieldNameFolderId = increment();

			addDLFolder(
				PortalUUIDUtil.generate(), fieldNameFolderId, _groupId,
				_companyId, _userId, _userName, _now, _now, _groupId,
				entryVersionFolderId, ddmFormFieldName, StringPool.BLANK, _now);

			return fieldNameFolderId;
		}

		protected long addEntryIdDLFolder(long ddmFolderId) throws Exception {
			long entryIdFolderId = getDLFolderId(
				_groupId, ddmFolderId, String.valueOf(_entryId));

			if (entryIdFolderId > 0) {
				return entryIdFolderId;
			}

			entryIdFolderId = increment();

			addDLFolder(
				PortalUUIDUtil.generate(), entryIdFolderId, _groupId,
				_companyId, _userId, _userName, _now, _now, _groupId,
				ddmFolderId, String.valueOf(_entryId), StringPool.BLANK, _now);

			return entryIdFolderId;
		}

		protected long addEntryVersionDLFolder(long entryIdFolderId)
			throws Exception {

			long entryVersionFolderId = getDLFolderId(
				_groupId, entryIdFolderId, _entryVersion);

			if (entryVersionFolderId > 0) {
				return entryVersionFolderId;
			}

			entryVersionFolderId = increment();

			addDLFolder(
				PortalUUIDUtil.generate(), entryVersionFolderId, _groupId,
				_companyId, _userId, _userName, _now, _now, _groupId,
				entryIdFolderId, _entryVersion, StringPool.BLANK, _now);

			return entryVersionFolderId;
		}

		protected long getActionBitwiseValue(String actionId)throws Exception {
			ResourceAction resourceAction =
				_resourceActionLocalService.getResourceAction(
					DLFileEntry.class.getName(), actionId);

			return resourceAction.getBitwiseValue();
		}

		protected long getActionIdsLong(String[] actions) throws Exception {
			long actionIdsLong = 0;

			for (String action : actions) {
				actionIdsLong |= getActionBitwiseValue(action);
			}

			return actionIdsLong;
		}

		protected long getDLFolderId(
			long groupId, long parentFolderId, String name) {

			try {
				DLFolder dlFolder = _dlFolderLocalService.getFolder(
					groupId, parentFolderId, name);

				return dlFolder.getFolderId();
			}
			catch (PortalException pe) {
				_log.error("Unable to get DLfolder ID " + pe);

				return 0;
			}
		}

		protected String getExtension(String fileName) {
			String extension = StringPool.BLANK;

			int pos = fileName.lastIndexOf(CharPool.PERIOD);

			if (pos > 0) {
				extension = fileName.substring(pos + 1, fileName.length());
			}

			return StringUtil.toLowerCase(extension);
		}

		protected String toJSON(long groupId, String fileEntryUuid) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("groupId", groupId);
			jsonObject.put("uuid", fileEntryUuid);

			return jsonObject.toString();
		}

		protected String upgradeFileUploadReference(
				String fileEntryUuid, String ddmFormFieldName,
				String valueString)
			throws PortalException {

			try {
				long dlFolderId = addDLFolderTree(ddmFormFieldName);

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				String name = String.valueOf(
					increment(DLFileEntry.class.getName()));

				String fileName = jsonObject.getString("name");
				String filePath = jsonObject.getString("path");

				// File entry

				long fileEntryId = increment();

				String extension = getExtension(fileName);

				File file = DLStoreUtil.getFile(
					_companyId, CompanyConstants.SYSTEM, filePath);

				addDLFileEntry(
					fileEntryUuid, fileEntryId, _groupId, _companyId, _userId,
					_userName, _createDate, _createDate, 0, 0, _groupId,
					dlFolderId, StringPool.BLANK, name, fileName, extension,
					MimeTypesUtil.getContentType(fileName), fileName,
					StringPool.BLANK, StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					DLFileEntryConstants.VERSION_DEFAULT, file.length(),
					DLFileEntryConstants.DEFAULT_READ_COUNT, 0, 0, 0, 0, false);

				// Resource permissions

				_resourcePermissionLocalService.addResourcePermissions(
					DLFileEntry.class.getName(), RoleConstants.OWNER,
					ResourceConstants.SCOPE_INDIVIDUAL,
					getActionIdsLong(_ownerPermissions));

				if (_groupId > 0) {
					_resourcePermissionLocalService.addResourcePermissions(
						DLFileEntry.class.getName(), RoleConstants.SITE_MEMBER,
						ResourceConstants.SCOPE_INDIVIDUAL,
						getActionIdsLong(_groupPermissions));
				}

				_resourcePermissionLocalService.addResourcePermissions(
					DLFileEntry.class.getName(), RoleConstants.GUEST,
					ResourceConstants.SCOPE_INDIVIDUAL,
					getActionIdsLong(_guestPermissions));

				// File version

				addDLFileVersion(
					fileEntryUuid, increment(), _groupId, _companyId, _userId,
					_userName, _createDate, _createDate, _groupId, dlFolderId,
					fileEntryId, StringPool.BLANK, fileName, extension,
					MimeTypesUtil.getContentType(fileName), fileName,
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					DLFileEntryConstants.VERSION_DEFAULT, file.length(),
					StringPool.BLANK, WorkflowConstants.STATUS_APPROVED,
					_userId, _userName, _createDate);

				// Asset entry

				addAssetEntry(
					increment(), _groupId, _companyId, _userId, _userName,
					_createDate, _createDate,
					PortalUtil.getClassNameId(DLFileEntry.class), fileEntryId,
					fileEntryUuid,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					false, null, null, null, null,
					MimeTypesUtil.getContentType(fileName), fileName,
					StringPool.BLANK, StringPool.BLANK, null, null, 0, 0, 0, 0);

				// File

				DLStoreUtil.addFile(_companyId, dlFolderId, name, file);

				return fileEntryUuid;
			}
			catch (Exception e) {
				throw new UpgradeException(e);
			}
		}

		private final long _companyId;
		private final Timestamp _createDate;
		private final long _entryId;
		private final String _entryVersion;
		private final long _groupId;
		private final String[] _groupPermissions = {"ADD_DISCUSSION", "VIEW"};
		private final String[] _guestPermissions = {"ADD_DISCUSSION", "VIEW"};
		private final Timestamp _now = new Timestamp(
			System.currentTimeMillis());
		private final String[] _ownerPermissions = {
			"ADD_DISCUSSION", "DELETE", "DELETE_DISCUSSION",
			"OVERRIDE_CHECKOUT", "PERMISSIONS", "UPDATE", "UPDATE_DISCUSSION",
			"VIEW"
		};
		private final long _userId;
		private final String _userName;

	}

}