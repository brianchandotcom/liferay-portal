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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.dynamic.data.mapping.internal.util.DDMImpl;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMFieldsCounter;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesTransformer;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustNotDuplicateFieldName;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

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
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class UpgradeDynamicDataMapping extends UpgradeProcess {

	public UpgradeDynamicDataMapping(
		AssetEntryLocalService assetEntryLocalService, DDM ddm,
		DDMFormDeserializer ddmFormJSONDeserializer,
		DDMFormDeserializer ddmFormXSDDeserializer,
		DDMFormLayoutSerializer ddmFormLayoutSerializer,
		DDMFormSerializer ddmFormSerializer,
		DDMFormValuesDeserializer ddmFormValuesDeserializer,
		DDMFormValuesSerializer ddmFormValuesSerializer,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFolderLocalService dlFolderLocalService,
		ExpandoRowLocalService expandoRowLocalService,
		ExpandoTableLocalService expandoTableLocalService,
		ExpandoValueLocalService expandoValueLocalService,
		ResourceActions resourceActions,
		ResourceLocalService resourceLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		Store store) {

		_assetEntryLocalService = assetEntryLocalService;
		_ddm = ddm;
		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
		_ddmFormXSDDeserializer = ddmFormXSDDeserializer;
		_ddmFormLayoutSerializer = ddmFormLayoutSerializer;
		_ddmFormSerializer = ddmFormSerializer;
		_ddmFormValuesDeserializer = ddmFormValuesDeserializer;
		_ddmFormValuesSerializer = ddmFormValuesSerializer;
		_dlFileEntryLocalService = dlFileEntryLocalService;
		_dlFileVersionLocalService = dlFileVersionLocalService;
		_dlFolderLocalService = dlFolderLocalService;
		_expandoRowLocalService = expandoRowLocalService;
		_expandoTableLocalService = expandoTableLocalService;
		_expandoValueLocalService = expandoValueLocalService;
		_resourceLocalService = resourceLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_store = store;

		_dlFolderModelPermissions = ModelPermissionsFactory.create(
			_DLFOLDER_GROUP_PERMISSIONS, _DLFOLDER_GUEST_PERMISSIONS);

		_dlFolderModelPermissions.addRolePermissions(
			RoleConstants.OWNER, _DLFOLDER_OWNER_PERMISSIONS);

		_initModelResourceNames(resourceActions);
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

	protected String createNewDDMFormFieldName(
			String fieldName, Set<String> existingFieldNames)
		throws Exception {

		String newFieldName = fieldName.replaceAll(
			_INVALID_FIELD_NAME_CHARS_REGEX, StringPool.BLANK);

		if (Validator.isNotNull(newFieldName)) {
			String updatedFieldName = newFieldName;

			for (int i = 1; existingFieldNames.contains(updatedFieldName);
				 i++) {

				updatedFieldName = newFieldName + i;
			}

			return updatedFieldName;
		}

		throw new UpgradeException(
			String.format(
				"Unable to automatically update field name \"%s\" because it " +
					"only contains invalid characters",
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

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
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

	protected List<String> getDDMDateFieldNames(DDMForm ddmForm)
		throws Exception {

		List<String> ddmDateFieldNames = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			String dataType = ddmFormField.getType();

			if (dataType.equals("ddm-date")) {
				ddmDateFieldNames.add(ddmFormField.getName());
			}
		}

		return ddmDateFieldNames;
	}

	protected DDMForm getDDMForm(long structureId) throws Exception {
		DDMForm ddmForm = _ddmForms.get(structureId);

		if (ddmForm != null) {
			return ddmForm;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"select parentStructureId, definition, storageType from " +
					"DDMStructure where structureId = ?")) {

			ps.setLong(1, structureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String definition = rs.getString("definition");
					String storageType = rs.getString("storageType");

					if (storageType.equals("expando") ||
						storageType.equals("xml")) {

						ddmForm = deserialize(definition, "xsd");
					}
					else {
						ddmForm = deserialize(definition, "json");
					}

					try {
						validateDDMFormFieldNames(ddmForm);
					}
					catch (MustNotDuplicateFieldName mndfn) {
						throw new UpgradeException(
							String.format(
								"The field name '%s' from structure ID %d is " +
									"defined more than once",
								mndfn.getFieldName(), structureId));
					}

					long parentStructureId = rs.getLong("parentStructureId");

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
			}

			throw new UpgradeException(
				"Unable to find dynamic data mapping structure with ID " +
					structureId);
		}
	}

	protected Set<String> getDDMFormFieldsNames(DDMForm ddmForm) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Set<String> ddmFormFieldsNames = new HashSet<>();

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

	protected Map<String, String> getDDMTemplateScriptMap(long structureId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from DDMTemplate where classPK = ? and type_ = ?")) {

			ps.setLong(1, structureId);
			ps.setString(2, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

			try (ResultSet rs = ps.executeQuery()) {
				Map<String, String> ddmTemplateScriptMap = new HashMap<>();

				while (rs.next()) {
					long templateId = rs.getLong("templateId");
					String language = rs.getString("language");
					String script = rs.getString("script");

					String key = templateId + StringPool.DOLLAR + language;

					ddmTemplateScriptMap.put(key, script);
				}

				return ddmTemplateScriptMap;
			}
		}
	}

	protected String getDefaultDDMFormLayoutDefinition(DDMForm ddmForm) {
		DDMFormLayout ddmFormLayout = _ddm.getDefaultDDMFormLayout(ddmForm);

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				ddmFormLayout);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
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

	protected DDMForm getFullHierarchyDDMForm(long structureId)
		throws Exception {

		DDMForm fullHierarchyDDMForm = _fullHierarchyDDMForms.get(structureId);

		if (fullHierarchyDDMForm != null) {
			return fullHierarchyDDMForm;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"select parentStructureId from DDMStructure where " +
					"structureId = ?")) {

			ps.setLong(1, structureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					long parentStructureId = rs.getLong("parentStructureId");

					fullHierarchyDDMForm = getDDMForm(structureId);

					if (parentStructureId > 0) {
						DDMForm parentDDMForm = getFullHierarchyDDMForm(
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

			throw new UpgradeException(
				"Unable to find dynamic data mapping structure with ID " +
					structureId);
		}
	}

	protected String getStructureModelResourceName(long classNameId)
		throws UpgradeException {

		String className = PortalUtil.getClassName(classNameId);

		String structureModelResourceName = _structureModelResourceNames.get(
			className);

		if (structureModelResourceName == null) {
			throw new UpgradeException(
				StringBundler.concat(
					"Model ", className, " does not support DDM structure ",
					"permission checking"));
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
				StringBundler.concat(
					"Model ", className, " does not support DDM template ",
					"permission checking"));
		}

		return templateModelResourceName;
	}

	protected Long getTemplateResourceClassNameId(
		long classNameId, long classPK) {

		if (classNameId != PortalUtil.getClassNameId(DDMStructure.class)) {
			return PortalUtil.getClassNameId(
				"com.liferay.portlet.display.template.PortletDisplayTemplate");
		}

		if (classPK == 0) {
			return PortalUtil.getClassNameId(
				"com.liferay.portlet.journal.model.JournalArticle");
		}

		return _structureClassNameIds.get(classPK);
	}

	protected boolean hasStructureVersion(long structureId, String version)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from DDMStructureVersion where structureId = ? and " +
					"version = ?")) {

			ps.setLong(1, structureId);
			ps.setString(2, version);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	protected boolean hasTemplateVersion(long templateId, String version)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from DDMTemplateVersion where templateId = ? and " +
					"version = ?")) {

			ps.setLong(1, templateId);
			ps.setString(2, version);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
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

		String[] oldSub = oldFieldNames.toArray(new String[0]);

		String[] newSub = new String[oldFieldNames.size()];

		for (int i = 0; i < oldSub.length; i++) {
			newSub[i] = ddmFormFieldNamesMap.get(oldSub[i]);
		}

		return StringUtil.replace(string, oldSub, newSub);
	}

	protected void setUpClassNameIds() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ddmContentClassNameId = PortalUtil.getClassNameId(
				DDMContent.class);

			_expandoStorageAdapterClassNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.dynamicdatamapping.storage." +
					"ExpandoStorageAdapter");
		}
	}

	protected String toJSON(DDMForm ddmForm) {
		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	protected String toJSON(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_ddmFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
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
			String entryModelName, DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new FileUploadDDMFormFieldValueTransformer(
				groupId, companyId, userId, userName, createDate, entryId,
				entryVersion, entryModelName));

		ddmFormValuesTransformer.addTransformer(
			new DateDDMFormFieldValueTransformer());

		ddmFormValuesTransformer.transform();
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

			if (Objects.equals(dataType, "file-upload")) {
				ddmFormField.setDataType("document-library");
				ddmFormField.setType("ddm-documentlibrary");
			}
			else if (Objects.equals(dataType, "image")) {
				ddmFormField.setFieldNamespace("ddm");
				ddmFormField.setType("ddm-image");
			}
		}

		return copyDDMForm;
	}

	protected void updateDDMStructureStorageType() throws Exception {
		runSQL(
			"update DDMStructure set storageType = 'xml' where storageType = " +
				"'expando'");
	}

	protected void updateStructureStorageType() throws Exception {
		runSQL(
			"update DDMStructure set storageType='json' where storageType = " +
				"'xml'");
	}

	protected void updateStructureVersionStorageType() throws Exception {
		runSQL(
			"update DDMStructureVersion set storageType='json' where " +
				"storageType = 'xml'");
	}

	protected void updateTemplateScript(long templateId, String script)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update DDMTemplate set script = ? where templateId = ?")) {

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
	}

	protected String updateTemplateScriptDateAssignStatement(
		String dateFieldName, String language, String script) {

		StringBundler oldTemplateScriptSB = new StringBundler(7);
		StringBundler newTemplateScriptSB = new StringBundler(5);

		if (language.equals("ftl")) {
			oldTemplateScriptSB.append("<#assign\\s+");
			oldTemplateScriptSB.append(dateFieldName);
			oldTemplateScriptSB.append("_Data\\s*=\\s*getterUtil\\s*.");
			oldTemplateScriptSB.append("\\s*getLong\\s*\\(\\s*");
			oldTemplateScriptSB.append(dateFieldName);
			oldTemplateScriptSB.append(".\\s*getData\\s*\\(\\s*\\)");
			oldTemplateScriptSB.append("\\s*\\)\\s*/?>");

			newTemplateScriptSB.append("<#assign ");
			newTemplateScriptSB.append(dateFieldName);
			newTemplateScriptSB.append("_Data = getterUtil.getString(");
			newTemplateScriptSB.append(dateFieldName);
			newTemplateScriptSB.append(".getData()) />");
		}
		else if (language.equals("vm")) {
			dateFieldName =
				StringPool.BACK_SLASH + StringPool.DOLLAR + dateFieldName;

			oldTemplateScriptSB.append("#set\\s+\\(\\s*");
			oldTemplateScriptSB.append(dateFieldName);
			oldTemplateScriptSB.append("_Data\\s*=\\s*\\$getterUtil.");
			oldTemplateScriptSB.append("getLong\\(\\s*");
			oldTemplateScriptSB.append(dateFieldName);
			oldTemplateScriptSB.append(".getData\\(\\)\\s*\\)\\s*\\)");

			newTemplateScriptSB.append("#set (");
			newTemplateScriptSB.append(dateFieldName);
			newTemplateScriptSB.append("_Data = \\$getterUtil.getString(");
			newTemplateScriptSB.append(dateFieldName);
			newTemplateScriptSB.append(".getData()))");
		}

		return script.replaceAll(
			oldTemplateScriptSB.toString(), newTemplateScriptSB.toString());
	}

	protected void updateTemplateScriptDateFields(
			long structureId, DDMForm ddmForm)
		throws Exception {

		List<String> ddmDateFieldNames = getDDMDateFieldNames(ddmForm);

		if (ddmDateFieldNames.isEmpty()) {
			return;
		}

		Map<String, String> ddmTemplateScriptMap = getDDMTemplateScriptMap(
			structureId);

		for (Map.Entry<String, String> entrySet :
				ddmTemplateScriptMap.entrySet()) {

			String[] templateIdAndLanguage = StringUtil.split(
				entrySet.getKey(), StringPool.DOLLAR);

			long ddmTemplateId = GetterUtil.getLong(templateIdAndLanguage[0]);
			String language = templateIdAndLanguage[1];

			String script = entrySet.getValue();

			for (String ddmDateFieldName : ddmDateFieldNames) {
				script = updateTemplateScriptDateAssignStatement(
					ddmDateFieldName, language, script);

				script = updateTemplateScriptDateIfStatement(
					ddmDateFieldName, language, script);

				script = updateTemplateScriptDateParseStatement(
					ddmDateFieldName, language, script);

				script = updateTemplateScriptDateGetDateStatement(
					language, script);
			}

			updateTemplateScript(ddmTemplateId, script);
		}
	}

	protected String updateTemplateScriptDateGetDateStatement(
		String language, String script) {

		StringBundler oldTemplateScriptSB = new StringBundler(3);
		String newTemplateScript = null;

		if (language.equals("ftl")) {
			oldTemplateScriptSB.append("dateUtil.getDate\\((.*)");
			oldTemplateScriptSB.append("locale[,\\s]*timeZoneUtil.");
			oldTemplateScriptSB.append("getTimeZone\\(\"UTC\"\\)\\s*\\)");

			newTemplateScript = "dateUtil.getDate($1locale)";
		}
		else if (language.equals("vm")) {
			oldTemplateScriptSB.append("dateUtil.getDate\\((.*)");
			oldTemplateScriptSB.append("\\$locale[,\\s]*\\$timeZoneUtil.");
			oldTemplateScriptSB.append("getTimeZone\\(\"UTC\"\\)\\s*\\)");

			newTemplateScript = "dateUtil.getDate($1\\$locale)";
		}

		return script.replaceAll(
			oldTemplateScriptSB.toString(), newTemplateScript);
	}

	protected String updateTemplateScriptDateIfStatement(
		String dateFieldName, String language, String script) {

		String oldTemplateScript = StringPool.BLANK;
		String newTemplateScript = StringPool.BLANK;

		if (language.equals("ftl")) {
			oldTemplateScript = StringBundler.concat(
				"<#if\\s*\\(?\\s*", dateFieldName, "_Data\\s*>\\s*0\\s*\\)?",
				"\\s*>");

			newTemplateScript =
				"<#if validator.isNotNull(" + dateFieldName + "_Data)>";
		}
		else if (language.equals("vm")) {
			dateFieldName =
				StringPool.BACK_SLASH + StringPool.DOLLAR + dateFieldName;

			oldTemplateScript =
				"#if\\s*\\(\\s*" + dateFieldName + "_Data\\s*>\\s*0\\s*\\)";

			newTemplateScript =
				"#if (\\$validator.isNotNull(" + dateFieldName + "_Data))";
		}

		return script.replaceAll(oldTemplateScript, newTemplateScript);
	}

	protected String updateTemplateScriptDateParseStatement(
		String dateFieldName, String language, String script) {

		StringBundler oldTemplateScriptSB = new StringBundler(6);
		StringBundler newTemplateScriptSB = new StringBundler(5);

		if (language.equals("ftl")) {
			oldTemplateScriptSB.append("<#assign\\s+");
			oldTemplateScriptSB.append(dateFieldName);
			oldTemplateScriptSB.append("_DateObj\\s*=\\s*dateUtil\\s*.");
			oldTemplateScriptSB.append("\\s*newDate\\(\\s*");
			oldTemplateScriptSB.append(dateFieldName);
			oldTemplateScriptSB.append("_Data\\s*\\)\\s*/?>");

			newTemplateScriptSB.append("<#assign ");
			newTemplateScriptSB.append(dateFieldName);
			newTemplateScriptSB.append(
				"_DateObj = dateUtil.parseDate(\"yyyy-MM-dd\", ");
			newTemplateScriptSB.append(dateFieldName);
			newTemplateScriptSB.append("_Data, locale) />");
		}
		else if (language.equals("vm")) {
			dateFieldName =
				StringPool.BACK_SLASH + StringPool.DOLLAR + dateFieldName;

			oldTemplateScriptSB.append("#set\\s*\\(");
			oldTemplateScriptSB.append(dateFieldName);
			oldTemplateScriptSB.append("_DateObj\\s*=\\s*\\$dateUtil.");
			oldTemplateScriptSB.append("newDate\\(\\s*");
			oldTemplateScriptSB.append(dateFieldName);
			oldTemplateScriptSB.append("_Data\\s*\\)\\s*\\)");

			newTemplateScriptSB.append("#set (");
			newTemplateScriptSB.append(dateFieldName);
			newTemplateScriptSB.append(
				"_DateObj = \\$dateUtil.parseDate(\"yyyy-MM-dd\", ");
			newTemplateScriptSB.append(dateFieldName);
			newTemplateScriptSB.append("_Data, \\$locale))");
		}

		return script.replaceAll(
			oldTemplateScriptSB.toString(), newTemplateScriptSB.toString());
	}

	protected void upgradeDDLFieldTypeReferences() throws Exception {
		StringBundler sb = new StringBundler(7);

		sb.append("select DDLRecordVersion.*, DDMContent.data_, ");
		sb.append("DDMStructure.structureId from DDLRecordVersion inner join ");
		sb.append("DDLRecordSet on DDLRecordVersion.recordSetId = ");
		sb.append("DDLRecordSet.recordSetId inner join DDMContent on  ");
		sb.append("DDLRecordVersion.DDMStorageId = DDMContent.contentId ");
		sb.append("inner join DDMStructure on DDLRecordSet.DDMStructureId = ");
		sb.append("DDMStructure.structureId");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMContent set data_= ? where contentId = ?");
			ResultSet rs = ps1.executeQuery()) {

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

				DDMForm ddmForm = getFullHierarchyDDMForm(ddmStructureId);

				DDMFormValues ddmFormValues = deserialize(data_, ddmForm);

				transformFieldTypeDDMFormFields(
					groupId, companyId, userId, userName, createDate, entryId,
					entryVersion, "DDLRecord", ddmFormValues);

				ps2.setString(1, toJSON(ddmFormValues));

				ps2.setLong(2, contentId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void upgradeDLFieldTypeReferences() throws Exception {
		StringBundler sb = new StringBundler(10);

		sb.append("select DLFileVersion.*, DDMContent.contentId, ");
		sb.append("DDMContent.data_, DDMStructure.structureId from ");
		sb.append("DLFileEntryMetadata inner join DDMContent on ");
		sb.append("DLFileEntryMetadata.DDMStorageId = DDMContent.contentId ");
		sb.append("inner join DDMStructure on ");
		sb.append("DLFileEntryMetadata.DDMStructureId = DDMStructure.");
		sb.append("structureId inner join DLFileVersion on ");
		sb.append("DLFileEntryMetadata.fileVersionId = DLFileVersion.");
		sb.append("fileVersionId and DLFileEntryMetadata.fileEntryId = ");
		sb.append("DLFileVersion.fileEntryId");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMContent set data_= ? where contentId = ?");
			ResultSet rs = ps1.executeQuery()) {

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

				DDMForm ddmForm = getFullHierarchyDDMForm(ddmStructureId);

				DDMFormValues ddmFormValues = deserialize(data_, ddmForm);

				transformFieldTypeDDMFormFields(
					groupId, companyId, userId, userName, createDate, entryId,
					entryVersion, "DLFileEntry", ddmFormValues);

				ps2.setString(1, toJSON(ddmFormValues));

				ps2.setLong(2, contentId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void upgradeExpandoStorageAdapter() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb1 = new StringBundler(5);

			sb1.append("select DDMStructure.*, DDMStorageLink.* from ");
			sb1.append("DDMStorageLink inner join DDMStructure on ");
			sb1.append("DDMStorageLink.structureId = ");
			sb1.append("DDMStructure.structureId where ");
			sb1.append("DDMStructure.storageType = 'expando'");

			StringBundler sb2 = new StringBundler(4);

			sb2.append("insert into DDMContent (uuid_, contentId, groupId, ");
			sb2.append("companyId, userId, userName, createDate, ");
			sb2.append("modifiedDate, name, description, data_) values (?, ");
			sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			StringBundler sb3 = new StringBundler(2);

			sb3.append("update DDMStorageLink set classNameId = ? where ");
			sb3.append("classNameId = ? and classPK = ?");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb1.toString());
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sb2.toString());
				PreparedStatement ps3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sb3.toString());
				ResultSet rs = ps1.executeQuery()) {

				Set<Long> expandoRowIds = new HashSet<>();

				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp createDate = rs.getTimestamp("createDate");

					long expandoRowId = rs.getLong("classPK");

					String xml = toXML(getExpandoValuesMap(expandoRowId));

					ps2.setString(1, PortalUUIDUtil.generate());
					ps2.setLong(2, expandoRowId);
					ps2.setLong(3, groupId);
					ps2.setLong(4, companyId);
					ps2.setLong(5, userId);
					ps2.setString(6, userName);
					ps2.setTimestamp(7, createDate);
					ps2.setTimestamp(8, createDate);
					ps2.setString(9, DDMStorageLink.class.getName());
					ps2.setString(10, null);
					ps2.setString(11, xml);

					ps2.addBatch();

					ps3.setLong(1, _ddmContentClassNameId);
					ps3.setLong(2, _expandoStorageAdapterClassNameId);
					ps3.setLong(3, expandoRowId);

					ps3.addBatch();

					expandoRowIds.add(expandoRowId);
				}

				if (expandoRowIds.isEmpty()) {
					return;
				}

				ps2.executeBatch();

				ps3.executeBatch();

				updateDDMStructureStorageType();

				deleteExpandoData(expandoRowIds);
			}
		}
	}

	protected void upgradeFieldTypeReferences() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			upgradeDDLFieldTypeReferences();
			upgradeDLFieldTypeReferences();
		}
	}

	protected void upgradeStructurePermissions(long companyId, long structureId)
		throws Exception {

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				companyId, DDMStructure.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(structureId));

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

		StringBundler sb1 = new StringBundler(6);

		sb1.append("insert into DDMStructureVersion (structureVersionId, ");
		sb1.append("groupId, companyId, userId, userName, createDate, ");
		sb1.append("structureId, version, parentStructureId, name, ");
		sb1.append("description, definition, storageType, type_, status, ");
		sb1.append("statusByUserId, statusByUserName, statusDate) values (?, ");
		sb1.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		StringBundler sb2 = new StringBundler(4);

		sb2.append("insert into DDMStructureLayout (uuid_, ");
		sb2.append("structureLayoutId, groupId, companyId, userId, userName, ");
		sb2.append("createDate, modifiedDate, structureVersionId, ");
		sb2.append("definition) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select * from DDMStructure");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb1.toString());
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long classNameId = rs.getLong("classNameId");
				String version = rs.getString("version");

				_structureClassNameIds.put(structureId, classNameId);

				// Structure content

				DDMForm ddmForm = getDDMForm(structureId);

				ddmForm = updateDDMFormFields(ddmForm);

				populateStructureInvalidDDMFormFieldNamesMap(
					structureId, ddmForm);

				String definition = toJSON(ddmForm);

				ps2.setString(1, definition);

				ps2.setLong(2, structureId);

				ps2.addBatch();

				updateTemplateScriptDateFields(structureId, ddmForm);

				// Structure version

				if (hasStructureVersion(structureId, version)) {
					continue;
				}

				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long parentStructureId = rs.getLong("parentStructureId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String storageType = rs.getString("storageType");
				int type = rs.getInt("type_");

				long structureVersionId = increment();

				ps3.setLong(1, structureVersionId);

				ps3.setLong(2, groupId);
				ps3.setLong(3, companyId);
				ps3.setLong(4, userId);
				ps3.setString(5, userName);
				ps3.setTimestamp(6, modifiedDate);
				ps3.setLong(7, structureId);
				ps3.setString(8, DDMStructureConstants.VERSION_DEFAULT);
				ps3.setLong(9, parentStructureId);
				ps3.setString(10, name);
				ps3.setString(11, description);
				ps3.setString(12, definition);
				ps3.setString(13, storageType);
				ps3.setInt(14, type);
				ps3.setInt(15, WorkflowConstants.STATUS_APPROVED);
				ps3.setLong(16, userId);
				ps3.setString(17, userName);
				ps3.setTimestamp(18, modifiedDate);

				ps3.addBatch();

				// Structure layout

				String ddmFormLayoutDefinition =
					getDefaultDDMFormLayoutDefinition(ddmForm);

				ps4.setString(1, PortalUUIDUtil.generate());
				ps4.setLong(2, increment());
				ps4.setLong(3, groupId);
				ps4.setLong(4, companyId);
				ps4.setLong(5, userId);
				ps4.setString(6, userName);
				ps4.setTimestamp(7, modifiedDate);
				ps4.setTimestamp(8, modifiedDate);
				ps4.setLong(9, structureVersionId);
				ps4.setString(10, ddmFormLayoutDefinition);

				ps4.addBatch();
			}

			ps2.executeBatch();

			ps3.executeBatch();

			ps4.executeBatch();
		}
	}

	protected void upgradeStructuresPermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select * from DDMStructure");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long structureId = rs.getLong("structureId");

				upgradeStructurePermissions(companyId, structureId);
			}
		}
	}

	protected void upgradeTemplatePermissions(long companyId, long templateId)
		throws Exception {

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				companyId, DDMTemplate.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(templateId));

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

	protected void upgradeTemplatesAndAddTemplateVersions() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("insert into DDMTemplateVersion (templateVersionId, ");
		sb.append("groupId, companyId, userId, userName, createDate, ");
		sb.append("classNameId, classPK, templateId, version, name, ");
		sb.append("description, language, script, status, statusByUserId, ");
		sb.append("statusByUserName, statusDate) values (?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select * from DDMTemplate");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMTemplate set resourceClassNameId = ? where " +
						"templateId = ?");
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMTemplate set language = ?, script = ? where " +
						"templateId = ?");
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");
				long templateId = rs.getLong("templateId");

				// Template resource class name ID

				Long resourceClassNameId = getTemplateResourceClassNameId(
					classNameId, classPK);

				if (resourceClassNameId == null) {
					_log.error("Orphaned DDM template " + templateId);

					continue;
				}

				String version = rs.getString("version");
				String language = rs.getString("language");
				String script = rs.getString("script");

				ps2.setLong(1, resourceClassNameId);

				ps2.setLong(2, templateId);

				ps2.addBatch();

				_templateResourceClassNameIds.put(
					templateId, resourceClassNameId);

				// Template content

				String updatedScript = renameInvalidDDMFormFieldNames(
					classPK, script);

				if (language.equals("xsd")) {
					DDMForm ddmForm = deserialize(updatedScript, "xsd");

					ddmForm = updateDDMFormFields(ddmForm);

					updatedScript = toJSON(ddmForm);

					language = "json";
				}

				if (!script.equals(updatedScript)) {
					ps3.setString(1, language);
					ps3.setString(2, updatedScript);
					ps3.setLong(3, templateId);

					ps3.addBatch();
				}

				// Template version

				if (!hasTemplateVersion(templateId, version)) {
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp modifiedDate = rs.getTimestamp("modifiedDate");

					ps4.setLong(1, increment());
					ps4.setLong(2, rs.getLong("groupId"));
					ps4.setLong(3, rs.getLong("companyId"));
					ps4.setLong(4, userId);
					ps4.setString(5, userName);
					ps4.setTimestamp(6, modifiedDate);
					ps4.setLong(7, classNameId);
					ps4.setLong(8, classPK);
					ps4.setLong(9, templateId);
					ps4.setString(10, DDMStructureConstants.VERSION_DEFAULT);
					ps4.setString(11, rs.getString("name"));
					ps4.setString(12, rs.getString("description"));
					ps4.setString(13, language);
					ps4.setString(14, updatedScript);
					ps4.setInt(15, WorkflowConstants.STATUS_APPROVED);
					ps4.setLong(16, userId);
					ps4.setString(17, userName);
					ps4.setTimestamp(18, modifiedDate);

					ps4.addBatch();
				}
			}

			ps2.executeBatch();

			ps3.executeBatch();

			ps4.executeBatch();
		}
	}

	protected void upgradeTemplatesPermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select * from DDMTemplate");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long templateId = rs.getLong("templateId");

				upgradeTemplatePermissions(companyId, templateId);
			}
		}
	}

	protected void upgradeXMLStorageAdapter() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(5);

			sb.append("select DDMStorageLink.classPK, DDMStorageLink.");
			sb.append("structureId from DDMStorageLink inner join ");
			sb.append("DDMStructure on (DDMStorageLink.structureId = ");
			sb.append("DDMStructure.structureId) where DDMStorageLink.");
			sb.append("classNameId = ? and DDMStructure.storageType = ?");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString());
				PreparedStatement ps2 = connection.prepareStatement(
					"select companyId, data_ from DDMContent where contentId " +
						"= ? and data_ like '<%'");
				PreparedStatement ps3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update DDMContent set data_= ? where contentId = ?")) {

				ps1.setLong(1, _ddmContentClassNameId);
				ps1.setString(2, "xml");

				try (ResultSet rs = ps1.executeQuery()) {
					while (rs.next()) {
						long structureId = rs.getLong("structureId");
						long classPK = rs.getLong("classPK");

						DDMForm ddmForm = getFullHierarchyDDMForm(structureId);

						ps2.setLong(1, classPK);

						try (ResultSet rs2 = ps2.executeQuery()) {
							if (rs2.next()) {
								long companyId = rs2.getLong("companyId");
								String xml = rs2.getString("data_");

								DDMFormValues ddmFormValues = getDDMFormValues(
									companyId, ddmForm, xml);

								String content = toJSON(ddmFormValues);

								content = renameInvalidDDMFormFieldNames(
									structureId, content);

								ps3.setString(1, content);

								ps3.setLong(2, classPK);

								ps3.addBatch();
							}
						}
					}

					ps3.executeBatch();
				}
			}

			updateStructureStorageType();
			updateStructureVersionStorageType();
		}
	}

	protected void validateDDMFormFieldName(
			DDMFormField ddmFormField, Set<String> ddmFormFieldNames)
		throws MustNotDuplicateFieldName {

		if (ddmFormFieldNames.contains(
				StringUtil.toLowerCase(ddmFormField.getName()))) {

			throw new MustNotDuplicateFieldName(ddmFormField.getName());
		}

		ddmFormFieldNames.add(StringUtil.toLowerCase(ddmFormField.getName()));

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			validateDDMFormFieldName(nestedDDMFormField, ddmFormFieldNames);
		}
	}

	protected void validateDDMFormFieldNames(DDMForm ddmForm)
		throws MustNotDuplicateFieldName {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Set<String> ddmFormFieldNames = new HashSet<>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			validateDDMFormFieldName(ddmFormField, ddmFormFieldNames);
		}
	}

	private void _initModelResourceNames(ResourceActions resourceActions) {
		_structureModelResourceNames.put(
			"com.liferay.document.library.kernel.model.DLFileEntry",
			resourceActions.getCompositeModelName(
				"com.liferay.document.library.kernel.model.DLFileEntry",
				_CLASS_NAME_DDM_STRUCTURE));

		_structureModelResourceNames.put(
			"com.liferay.document.library.kernel.model.DLFileEntryMetadata",
			resourceActions.getCompositeModelName(
				"com.liferay.document.library.kernel.model.DLFileEntryMetadata",
				_CLASS_NAME_DDM_STRUCTURE));

		_structureModelResourceNames.put(
			"com.liferay.document.library.kernel.util.RawMetadataProcessor",
			_CLASS_NAME_DDM_STRUCTURE);

		_structureModelResourceNames.put(
			"com.liferay.dynamic.data.lists.model.DDLRecordSet",
			resourceActions.getCompositeModelName(
				"com.liferay.dynamic.data.lists.model.DDLRecordSet",
				_CLASS_NAME_DDM_STRUCTURE));

		_structureModelResourceNames.put(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordSet",
			resourceActions.getCompositeModelName(
				"com.liferay.dynamic.data.lists.model.DDLRecordSet",
				_CLASS_NAME_DDM_STRUCTURE));

		_structureModelResourceNames.put(
			"com.liferay.portlet.journal.model.JournalArticle",
			resourceActions.getCompositeModelName(
				"com.liferay.journal.model.JournalArticle",
				_CLASS_NAME_DDM_STRUCTURE));

		_templateModelResourceNames.put(
			"com.liferay.dynamic.data.lists.model.DDLRecordSet",
			resourceActions.getCompositeModelName(
				"com.liferay.dynamic.data.lists.model.DDLRecordSet",
				_CLASS_NAME_DDM_TEMPLATE));

		_templateModelResourceNames.put(
			"com.liferay.portlet.display.template.PortletDisplayTemplate",
			_CLASS_NAME_DDM_TEMPLATE);

		_templateModelResourceNames.put(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordSet",
			resourceActions.getCompositeModelName(
				"com.liferay.dynamic.data.lists.model.DDLRecordSet",
				_CLASS_NAME_DDM_TEMPLATE));

		_templateModelResourceNames.put(
			"com.liferay.portlet.journal.model.JournalArticle",
			resourceActions.getCompositeModelName(
				"com.liferay.journal.model.JournalArticle",
				_CLASS_NAME_DDM_TEMPLATE));
	}

	private static final String _CLASS_NAME_DDM_STRUCTURE =
		"com.liferay.dynamic.data.mapping.model.DDMStructure";

	private static final String _CLASS_NAME_DDM_TEMPLATE =
		"com.liferay.dynamic.data.mapping.model.DDMTemplate";

	private static final String[] _DLFOLDER_GROUP_PERMISSIONS = {
		"ADD_DOCUMENT", "ADD_SHORTCUT", "ADD_SUBFOLDER", "SUBSCRIBE", "VIEW"
	};

	private static final String[] _DLFOLDER_GUEST_PERMISSIONS = {"VIEW"};

	private static final String[] _DLFOLDER_OWNER_PERMISSIONS = {
		"ACCESS", "ADD_DOCUMENT", "ADD_SHORTCUT", "ADD_SUBFOLDER", "DELETE",
		"PERMISSIONS", "SUBSCRIBE", "UPDATE", "VIEW"
	};

	private static final String _INVALID_FIELD_NAME_CHARS_REGEX =
		"([\\p{Punct}&&[^_]]|\\p{Space})+";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDynamicDataMapping.class);

	private static final Pattern _invalidFieldNameCharsPattern =
		Pattern.compile(_INVALID_FIELD_NAME_CHARS_REGEX);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final DDM _ddm;
	private long _ddmContentClassNameId;
	private final DDMFormDeserializer _ddmFormJSONDeserializer;
	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final Map<Long, DDMForm> _ddmForms = new HashMap<>();
	private final DDMFormSerializer _ddmFormSerializer;
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer;
	private final DDMFormValuesSerializer _ddmFormValuesSerializer;
	private final DDMFormDeserializer _ddmFormXSDDeserializer;
	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private final DLFileVersionLocalService _dlFileVersionLocalService;
	private final DLFolderLocalService _dlFolderLocalService;
	private final ModelPermissions _dlFolderModelPermissions;
	private final ExpandoRowLocalService _expandoRowLocalService;
	private long _expandoStorageAdapterClassNameId;
	private final ExpandoTableLocalService _expandoTableLocalService;
	private final ExpandoValueLocalService _expandoValueLocalService;
	private final Map<Long, DDMForm> _fullHierarchyDDMForms = new HashMap<>();
	private final ResourceLocalService _resourceLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final Store _store;
	private final Map<Long, Long> _structureClassNameIds = new HashMap<>();
	private final Map<Long, Map<String, String>>
		_structureInvalidDDMFormFieldNamesMap = new HashMap<>();
	private final Map<String, String> _structureModelResourceNames =
		new HashMap<>();
	private final Map<String, String> _templateModelResourceNames =
		new HashMap<>();
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

			if (value != null) {
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
		}

		private final DateFormat _dateFormat =
			DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd", TimeZoneUtil.getTimeZone("UTC"));

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

			for (String fieldDisplayName : fieldsDisplayValues) {
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

				if (fieldsDisplayDynamicContent != null) {
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
			}

			return ddmFieldsDisplayValues.toArray(new String[0]);
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

			if (nodes.isEmpty()) {
				dynamicContentXPath = SAXReaderUtil.createXPath(
					"dynamic-content");

				nodes = dynamicContentXPath.selectNodes(dynamicElementElement);

				Element element = null;

				if (nodes.isEmpty()) {
					element = dynamicElementElement.addElement(
						"dynamic-content");
				}
				else {
					element = (Element)nodes.get(index);
				}

				element.addAttribute("language-id", languageId);

				return element;
			}

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
					languageId, localizedContentIndex + 1);
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
			Timestamp createDate, long entryId, String entryVersion,
			String entryModelName) {

			_groupId = groupId;
			_companyId = companyId;
			_userId = userId;
			_userName = userName;
			_createDate = createDate;
			_entryId = entryId;
			_entryVersion = entryVersion;
			_entryModelName = entryModelName;

			_modelPermissions = ModelPermissionsFactory.create(
				_groupPermissions, _guestPermissions);

			_modelPermissions.addRolePermissions(
				RoleConstants.OWNER, _ownerPermissions);
		}

		@Override
		public String getFieldType() {
			return "ddm-fileupload";
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			if (value != null) {
				for (Locale locale : value.getAvailableLocales()) {
					String valueString = value.getString(locale);

					if (Validator.isNull(valueString)) {
						continue;
					}

					String fileEntryUuid = PortalUUIDUtil.generate();

					upgradeFileUploadReference(
						fileEntryUuid, ddmFormFieldValue.getName(),
						valueString);

					value.addString(locale, toJSON(_groupId, fileEntryUuid));
				}
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

		protected DLFileEntry addDLFileEntry(
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

			return dlFileEntry;
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
			dlFolder.setRestrictionType(0);
			dlFolder.setStatus(WorkflowConstants.STATUS_APPROVED);
			dlFolder.setStatusByUserId(0);
			dlFolder.setStatusByUserName(StringPool.BLANK);

			_dlFolderLocalService.updateDLFolder(dlFolder);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setModelPermissions(_dlFolderModelPermissions);

			_resourceLocalService.addModelResources(dlFolder, serviceContext);
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

		protected File fetchFile(String filePath) throws PortalException {
			try {
				return _store.getFile(
					_companyId, CompanyConstants.SYSTEM, filePath);
			}
			catch (PortalException pe) {
				_log.error(
					String.format(
						"Unable to find the binary file with path \"%s\" " +
							"referenced by %s",
						filePath, getModelInfo()));

				throw pe;
			}
		}

		protected long getDLFolderId(
			long groupId, long parentFolderId, String name) {

			DLFolder dlFolder = _dlFolderLocalService.fetchFolder(
				groupId, parentFolderId, name);

			if (dlFolder == null) {
				return 0;
			}

			return dlFolder.getFolderId();
		}

		protected String getExtension(String fileName) {
			String extension = StringPool.BLANK;

			int pos = fileName.lastIndexOf(CharPool.PERIOD);

			if (pos > 0) {
				extension = fileName.substring(pos + 1);
			}

			return StringUtil.toLowerCase(extension);
		}

		protected String getModelInfo() {
			return String.format(
				"%s {primaryKey: %d, version: %s}", _entryModelName, _entryId,
				_entryVersion);
		}

		protected String toJSON(long groupId, String fileEntryUuid) {
			JSONObject jsonObject = JSONUtil.put(
				"groupId", groupId
			).put(
				"uuid", fileEntryUuid
			);

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

				File file = fetchFile(filePath);

				DLFileEntry dlFileEntry = addDLFileEntry(
					fileEntryUuid, fileEntryId, _groupId, _companyId, _userId,
					_userName, _createDate, _createDate, 0, 0, _groupId,
					dlFolderId, StringPool.BLANK, name, fileName, extension,
					MimeTypesUtil.getContentType(fileName), fileName,
					StringPool.BLANK, StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					DLFileEntryConstants.VERSION_DEFAULT, file.length(),
					DLFileEntryConstants.DEFAULT_READ_COUNT, 0, 0, 0, 0, false);

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

				_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

				// Resources

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setModelPermissions(_modelPermissions);

				_resourceLocalService.addModelResources(
					dlFileEntry, serviceContext);

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

				_store.addFile(_companyId, dlFolderId, name, file);

				return fileEntryUuid;
			}
			catch (Exception e) {
				throw new UpgradeException(e);
			}
		}

		private final long _companyId;
		private final Timestamp _createDate;
		private final long _entryId;
		private final String _entryModelName;
		private final String _entryVersion;
		private final long _groupId;
		private final String[] _groupPermissions = {"ADD_DISCUSSION", "VIEW"};
		private final String[] _guestPermissions = {"ADD_DISCUSSION", "VIEW"};
		private final ModelPermissions _modelPermissions;
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