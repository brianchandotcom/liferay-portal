/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.util;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.RelatedInfoFieldValue;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.info.item.util.ObjectEntryInfoItemUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.bag.ObjectFieldBag;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryFolderLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Victor
 */
public class ObjectEntryCMSInfoItemFieldValuesUtil {

	public static InfoItemFieldValues normalizeInfoItemFieldValues(
			InfoItemFieldValues infoItemFieldValues, ObjectEntry objectEntry,
			ObjectEntryManagerRegistry objectEntryManagerRegistry,
			ObjectScopeProviderRegistry objectScopeProviderRegistry,
			ServiceContext serviceContext)
		throws Exception {

		ObjectDefinition objectDefinition = objectEntry.getObjectDefinition();

		if ((objectDefinition == null) || !objectDefinition.isCMS()) {
			return infoItemFieldValues;
		}

		InfoItemFieldValues.Builder builder = InfoItemFieldValues.builder();

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			builder.infoFieldValue(
				_normalizeInfoFieldValue(
					infoFieldValue, objectDefinition, objectEntry,
					objectEntryManagerRegistry, objectScopeProviderRegistry,
					serviceContext));
		}

		return builder.infoItemReference(
			infoItemFieldValues.getInfoItemReference()
		).build();
	}

	private static long _createCMSBasicDocumentFileEntryId(
			ObjectEntryManagerRegistry objectEntryManagerRegistry,
			ObjectField objectField,
			ObjectScopeProviderRegistry objectScopeProviderRegistry,
			ObjectEntry sourceObjectEntry, ServiceContext serviceContext,
			long tempFileEntryId)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_DOCUMENT", sourceObjectEntry.getCompanyId());

		ObjectEntryManager objectEntryManager =
			objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getCompanyId(),
				objectDefinition.getStorageType());

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			tempFileEntryId);
		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
			objectEntryManager.addObjectEntry(
				new DefaultDTOConverterContext(
					false, null, null, null, null, themeDisplay.getLocale(),
					null, themeDisplay.getUser()),
				objectDefinition,
				new com.liferay.object.rest.dto.v1_0.ObjectEntry() {
					{
						setObjectEntryFolderExternalReferenceCode(
							() -> _getObjectEntryFolderExternalReferenceCode(
								sourceObjectEntry, objectField,
								serviceContext));
						setProperties(
							() -> HashMapBuilder.<String, Object>put(
								"file", tempFileEntryId
							).put(
								"title_i18n",
								HashMapBuilder.put(
									LocaleUtil.toLanguageId(
										LocaleUtil.getSiteDefault()),
									TempFileEntryUtil.getOriginalTempFileName(
										fileEntry.getFileName())
								).build()
							).build());
						setStatus(
							() -> new Status() {
								{
									setCode(
										() ->
											WorkflowConstants.STATUS_APPROVED);
								}
							});
					}
				},
				ObjectEntryInfoItemUtil.getScopeKey(
					sourceObjectEntry.getGroupId(), objectDefinition,
					objectScopeProviderRegistry));

		Map<String, Serializable> values =
			ObjectEntryLocalServiceUtil.getValues(
				GetterUtil.getLong(objectEntry.getId()));

		return GetterUtil.getLong(values.get("file"));
	}

	private static String _getObjectEntryFolderExternalReferenceCode(
			ObjectEntry objectEntry, ObjectField objectField,
			ServiceContext serviceContext)
		throws Exception {

		String storageDLFolderPath = ObjectFieldSettingUtil.getValue(
			ObjectFieldSettingConstants.NAME_STORAGE_DL_FOLDER_PATH,
			objectField.getObjectFieldSettings());

		if (Validator.isNull(storageDLFolderPath)) {
			return ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES;
		}

		long companyId = objectEntry.getCompanyId();
		long groupId = objectEntry.getGroupId();

		ObjectEntryFolder objectEntryFolder =
			ObjectEntryFolderLocalServiceUtil.getOrAddEmptyObjectEntryFolder(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES,
				groupId, companyId, objectEntry.getUserId(), serviceContext);

		for (String name :
				StringUtil.split(storageDLFolderPath, CharPool.FORWARD_SLASH)) {

			if (Validator.isNull(name)) {
				continue;
			}

			ObjectEntryFolder childObjectEntryFolder =
				ObjectEntryFolderLocalServiceUtil.fetchObjectEntryFolder(
					groupId, companyId,
					objectEntryFolder.getObjectEntryFolderId(), name);

			if (childObjectEntryFolder == null) {
				childObjectEntryFolder =
					ObjectEntryFolderLocalServiceUtil.addObjectEntryFolder(
						PortalUUIDUtil.generate(), groupId,
						serviceContext.getUserId(),
						objectEntryFolder.getObjectEntryFolderId(),
						StringPool.BLANK, null, name, serviceContext);
			}

			objectEntryFolder = childObjectEntryFolder;
		}

		return objectEntryFolder.getExternalReferenceCode();
	}

	private static ObjectField _getObjectField(
			InfoField<?> infoField, ObjectDefinition objectDefinition)
		throws Exception {

		if (StringUtil.startsWith(
				infoField.getUniqueId(),
				ObjectRelationship.class.getSimpleName() + StringPool.POUND)) {

			String[] parts = StringUtil.split(
				StringUtil.removeLast(
					infoField.getUniqueId(),
					StringPool.UNDERLINE + infoField.getName()),
				StringPool.POUND);

			ObjectDefinition relatedObjectDefinition =
				ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
					objectDefinition.getCompanyId(), parts[1]);

			if (relatedObjectDefinition == null) {
				return null;
			}

			ObjectFieldBag objectFieldBag =
				relatedObjectDefinition.getObjectFieldBag();

			return objectFieldBag.getObjectField(infoField.getName());
		}

		ObjectFieldBag objectFieldBag = objectDefinition.getObjectFieldBag();

		return objectFieldBag.getObjectField(infoField.getName());
	}

	private static boolean _isTempFileEntryId(long fileEntryId)
		throws Exception {

		if (fileEntryId <= 0) {
			return false;
		}

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

		return !StringUtil.equals(
			TempFileEntryUtil.getOriginalTempFileName(fileEntry.getFileName()),
			fileEntry.getFileName());
	}

	private static Object _normalizeFileFieldValue(
			ObjectEntry objectEntry,
			ObjectEntryManagerRegistry objectEntryManagerRegistry,
			ObjectField objectField,
			ObjectScopeProviderRegistry objectScopeProviderRegistry,
			ServiceContext serviceContext, Object value)
		throws Exception {

		if (!_showFilesInLibrary(objectField)) {
			return value;
		}

		if (value instanceof InfoLocalizedValue) {
			InfoLocalizedValue<Object> infoLocalizedValue =
				(InfoLocalizedValue<Object>)value;

			InfoLocalizedValue.Builder<Object> builder =
				InfoLocalizedValue.builder(
				).defaultLocale(
					infoLocalizedValue.getDefaultLocale()
				);

			for (Map.Entry<Locale, Object> entry :
					infoLocalizedValue.getValues(
					).entrySet()) {

				long tempFileEntryId = GetterUtil.getLong(entry.getValue());

				if (!_isTempFileEntryId(tempFileEntryId)) {
					builder.value(entry.getKey(), entry.getValue());

					continue;
				}

				builder.value(
					entry.getKey(),
					_createCMSBasicDocumentFileEntryId(
						objectEntryManagerRegistry, objectField,
						objectScopeProviderRegistry, objectEntry,
						serviceContext, tempFileEntryId));
			}

			return builder.build();
		}

		long tempFileEntryId = GetterUtil.getLong(value);

		if ((tempFileEntryId <= 0) || !_isTempFileEntryId(tempFileEntryId)) {
			return value;
		}

		return _createCMSBasicDocumentFileEntryId(
			objectEntryManagerRegistry, objectField,
			objectScopeProviderRegistry, objectEntry, serviceContext,
			tempFileEntryId);
	}

	private static InfoFieldValue<Object> _normalizeInfoFieldValue(
			InfoFieldValue<Object> infoFieldValue,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			ObjectEntryManagerRegistry objectEntryManagerRegistry,
			ObjectScopeProviderRegistry objectScopeProviderRegistry,
			ServiceContext serviceContext)
		throws Exception {

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Object value = infoFieldValue.getValue();

		if (value instanceof RelatedInfoFieldValue) {
			return new InfoFieldValue<>(
				infoField,
				_normalizeRelatedInfoFieldValue(
					objectDefinition, objectEntry, objectEntryManagerRegistry,
					objectScopeProviderRegistry,
					(RelatedInfoFieldValue<?>)value, infoField,
					serviceContext));
		}

		if (!(infoField.getInfoFieldType() instanceof FileInfoFieldType)) {
			return infoFieldValue;
		}

		ObjectField objectField = _getObjectField(infoField, objectDefinition);

		if (objectField == null) {
			return infoFieldValue;
		}

		return new InfoFieldValue<>(
			infoField,
			_normalizeFileFieldValue(
				objectEntry, objectEntryManagerRegistry, objectField,
				objectScopeProviderRegistry, serviceContext, value));
	}

	private static RelatedInfoFieldValue<Object>
			_normalizeRelatedInfoFieldValue(
				ObjectDefinition objectDefinition, ObjectEntry objectEntry,
				ObjectEntryManagerRegistry objectEntryManagerRegistry,
				ObjectScopeProviderRegistry objectScopeProviderRegistry,
				RelatedInfoFieldValue<?> relatedInfoFieldValue,
				InfoField<?> relationshipInfoField,
				ServiceContext serviceContext)
		throws Exception {

		String[] parts = StringUtil.split(
			StringUtil.removeLast(
				relationshipInfoField.getUniqueId(),
				StringPool.UNDERLINE + relationshipInfoField.getName()),
			StringPool.POUND);

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				objectDefinition.getCompanyId(), parts[1]);

		if (relatedObjectDefinition == null) {
			return (RelatedInfoFieldValue<Object>)relatedInfoFieldValue;
		}

		Map
			<RelatedInfoFieldValue.RelatedInfoFieldValueIdentifier,
			 InfoFieldValue<Object>> relatedInfoFieldValues = new HashMap<>();

		for (Map.Entry
				<RelatedInfoFieldValue.RelatedInfoFieldValueIdentifier,
				 ? extends InfoFieldValue<?>> entry :
					relatedInfoFieldValue.getRelatedInfoFieldValues(
					).entrySet()) {

			relatedInfoFieldValues.put(
				entry.getKey(),
				_normalizeInfoFieldValue(
					(InfoFieldValue<Object>)entry.getValue(),
					relatedObjectDefinition, objectEntry,
					objectEntryManagerRegistry, objectScopeProviderRegistry,
					serviceContext));
		}

		return new RelatedInfoFieldValue<>(relatedInfoFieldValues);
	}

	private static boolean _showFilesInLibrary(ObjectField objectField) {
		return GetterUtil.getBoolean(
			ObjectFieldSettingUtil.getValue(
				ObjectFieldSettingConstants.NAME_SHOW_FILES_IN_LIBRARY,
				objectField.getObjectFieldSettings()));
	}

}