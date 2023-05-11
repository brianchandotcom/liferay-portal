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

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.info.type.WebImage;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;
import com.liferay.object.web.internal.util.ObjectFieldDBTypeUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<ObjectEntry> {

	public ObjectEntryInfoItemFieldValuesProvider(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		DLAppLocalService dlAppLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLURLHelper dlURLHelper,
		InfoItemFieldReaderFieldSetProvider infoItemFieldReaderFieldSetProvider,
		JSONFactory jsonFactory,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManagerRegistry objectEntryManagerRegistry,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		TemplateInfoItemFieldSetProvider templateInfoItemFieldSetProvider,
		UserLocalService userLocalService) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_dlAppLocalService = dlAppLocalService;
		_dlFileEntryLocalService = dlFileEntryLocalService;
		_dlURLHelper = dlURLHelper;
		_infoItemFieldReaderFieldSetProvider =
			infoItemFieldReaderFieldSetProvider;
		_jsonFactory = jsonFactory;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManagerRegistry = objectEntryManagerRegistry;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_templateInfoItemFieldSetProvider = templateInfoItemFieldSetProvider;
		_userLocalService = userLocalService;
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(ObjectEntry objectEntry) {
		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getInfoFieldValues(objectEntry)
		).infoFieldValues(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				objectEntry.getModelClassName(), objectEntry)
		).infoFieldValues(
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				objectEntry.getModelClassName(), objectEntry)
		).infoItemReference(
			new InfoItemReference(
				objectEntry.getModelClassName(), objectEntry.getObjectEntryId())
		).build();
	}

	private List<InfoFieldValue<Object>> _getAttachmentInfoFieldValues(
		ObjectField objectField, Map<String, ?> values) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-176083") ||
			!Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			return Collections.emptyList();
		}

		long fileEntryId = GetterUtil.getLong(
			values.get(objectField.getName()));

		if (fileEntryId == GetterUtil.DEFAULT_LONG) {
			return Collections.emptyList();
		}

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		try {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			if (fileEntry == null) {
				return Collections.emptyList();
			}

			infoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						URLInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#downloadURL"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "download-url")
					).build(),
					_dlURLHelper.getDownloadURL(
						fileEntry, fileEntry.getFileVersion(), null,
						StringPool.BLANK)));
			infoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#fileName"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "file-name")
					).build(),
					fileEntry.getFileName()));
			infoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#mimeType"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "mime-type")
					).build(),
					fileEntry.getMimeType()));
			infoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						URLInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#previewURL"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "preview-url")
					).build(),
					_dlURLHelper.getPreviewURL(
						fileEntry, fileEntry.getFileVersion(), null,
						StringPool.BLANK)));
			infoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#size"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "size")
					).build(),
					fileEntry.getSize()));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return infoFieldValues;
	}

	private String _getDisplayPageURL(
			ObjectEntry objectEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			objectEntry.getModelClassName(), objectEntry.getObjectEntryId(),
			themeDisplay);
	}

	private List<InfoFieldValue<Object>> _getInfoFieldValues(
		ObjectEntry objectEntry) {

		try {
			if (_objectDefinition.isDefaultStorageType()) {
				return _getInfoFieldValuesByDefaultStorageType(objectEntry);
			}

			return _getInfoFieldValuesByObjectEntryManager(objectEntry);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private List<InfoFieldValue<Object>>
			_getInfoFieldValuesByDefaultStorageType(ObjectEntry objectEntry)
		throws Exception {

		List<InfoFieldValue<Object>> objectEntryFieldValues = new ArrayList<>();

		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.authorInfoField,
				objectEntry.getUserName()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.createDateInfoField,
				objectEntry.getCreateDate()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.externalReferenceCodeInfoField,
				objectEntry.getExternalReferenceCode()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.modifiedDateInfoField,
				objectEntry.getModifiedDate()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.objectEntryIdInfoField,
				objectEntry.getObjectEntryId()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.publishDateInfoField,
				objectEntry.getLastPublishDate()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.statusInfoField,
				WorkflowConstants.getStatusLabel(objectEntry.getStatus())));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.userProfileImageInfoField,
				_getWebImage(objectEntry.getUserId())));

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			objectEntryFieldValues.add(
				new InfoFieldValue<>(
					ObjectEntryInfoItemFields.displayPageURLInfoField,
					_getDisplayPageURL(objectEntry, themeDisplay)));
		}

		objectEntryFieldValues.addAll(
			_getObjectFieldsInfoFieldValues(
				_objectFieldLocalService.getObjectFields(
					objectEntry.getObjectDefinitionId(), false),
				objectEntry.getValues()));

		return objectEntryFieldValues;
	}

	private List<InfoFieldValue<Object>>
			_getInfoFieldValuesByObjectEntryManager(
				ObjectEntry serviceBuilderObjectEntry)
		throws Exception {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			return Collections.emptyList();
		}

		List<InfoFieldValue<Object>> objectEntryFieldValues = new ArrayList<>();

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				_objectDefinition.getStorageType());

		com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
			objectEntryManager.getObjectEntry(
				themeDisplay.getCompanyId(),
				new DefaultDTOConverterContext(
					false, null, null, null, null, themeDisplay.getLocale(),
					null, themeDisplay.getUser()),
				serviceBuilderObjectEntry.getExternalReferenceCode(),
				_objectDefinition, null);

		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.createDateInfoField,
				objectEntry.getDateCreated()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.modifiedDateInfoField,
				objectEntry.getDateModified()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.publishDateInfoField,
				objectEntry.getDateModified()));

		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.displayPageURLInfoField,
				_getDisplayPageURL(serviceBuilderObjectEntry, themeDisplay)));
		objectEntryFieldValues.addAll(
			_getObjectFieldsInfoFieldValues(
				_objectFieldLocalService.getObjectFields(
					serviceBuilderObjectEntry.getObjectDefinitionId(), false),
				objectEntry.getProperties()));

		return objectEntryFieldValues;
	}

	private List<InfoFieldValue<Object>> _getObjectFieldsInfoFieldValues(
			List<ObjectField> objectFields, Map<String, ?> values)
		throws Exception {

		List<InfoFieldValue<Object>> objectFieldsInfoFieldValues =
			new ArrayList<>();

		for (ObjectField objectField : objectFields) {
			objectFieldsInfoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						ObjectFieldDBTypeUtil.getInfoFieldType(objectField)
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.<String>builder(
						).defaultLocale(
							LocaleUtil.fromLanguageId(
								objectField.getDefaultLanguageId())
						).values(
							objectField.getLabelMap()
						).build()
					).build(),
					_getValue(objectField, values)));

			List<InfoFieldValue<Object>> attachmentInfoFieldValues =
				_getAttachmentInfoFieldValues(objectField, values);

			if (ListUtil.isNotEmpty(attachmentInfoFieldValues)) {
				objectFieldsInfoFieldValues.addAll(attachmentInfoFieldValues);
			}

			List<InfoFieldValue<Object>> relatedObjectEntryInfoFieldValues =
				_getRelatedObjectEntryFieldValues(objectField, values);

			if (ListUtil.isNotEmpty(relatedObjectEntryInfoFieldValues)) {
				objectFieldsInfoFieldValues.addAll(
					relatedObjectEntryInfoFieldValues);
			}
		}

		return objectFieldsInfoFieldValues;
	}

	private List<InfoFieldValue<Object>> _getRelatedObjectEntryFieldValues(
			ObjectField objectField, Map<String, ?> values)
		throws Exception {

		Object value = values.get(objectField.getName());

		if (!FeatureFlagManagerUtil.isEnabled("LPS-176083") ||
			Validator.isNull(objectField.getRelationshipType()) ||
			(GetterUtil.getLong(value) == 0)) {

			return Collections.emptyList();
		}

		ObjectEntry relatedObjectEntry =
			_objectEntryLocalService.fetchObjectEntry((Long)value);

		if (relatedObjectEntry == null) {
			return Collections.emptyList();
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				relatedObjectEntry.getObjectDefinitionId());
		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());
		Map<String, ?> relatedObjectEntryValues =
			relatedObjectEntry.getValues();

		return TransformUtil.transform(
			_objectFieldLocalService.getObjectFields(
				relatedObjectEntry.getObjectDefinitionId(), false),
			relatedObjectField -> new InfoFieldValue<>(
				InfoField.builder(
				).infoFieldType(
					ObjectFieldDBTypeUtil.getInfoFieldType(relatedObjectField)
				).namespace(
					StringBundler.concat(
						ObjectRelationship.class.getSimpleName(),
						StringPool.POUND, objectDefinition.getName(),
						StringPool.POUND, objectRelationship.getName())
				).name(
					relatedObjectField.getName()
				).labelInfoLocalizedValue(
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							relatedObjectField.getDefaultLanguageId())
					).values(
						relatedObjectField.getLabelMap()
					).build()
				).build(),
				_getValue(relatedObjectField, relatedObjectEntryValues)));
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	private Object _getValue(
			ObjectField objectField, Map<String, ? extends Object> values)
		throws Exception {

		Object value = values.get(objectField.getName());

		if ((value == null) ||
			(Validator.isNotNull(objectField.getRelationshipType()) &&
			 (value instanceof Long) && Objects.equals(value, 0L))) {

			return StringPool.BLANK;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (Objects.equals(
				ObjectFieldDBTypeUtil.getInfoFieldType(objectField),
				ImageInfoFieldType.INSTANCE)) {

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				new String((byte[])values.get(objectField.getName())));

			WebImage webImage = new WebImage(jsonObject.getString("url"));

			webImage.setAlt(jsonObject.getString("alt"));

			return webImage;
		}
		else if (objectField.getListTypeDefinitionId() != 0) {
			if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				List<KeyLocalizedLabelPair> keyLocalizedLabelPairs =
					new ArrayList<>();

				for (String key :
						StringUtil.split(
							(String)values.get(objectField.getName()),
							StringPool.COMMA_AND_SPACE)) {

					ListTypeEntry listTypeEntry =
						_listTypeEntryLocalService.fetchListTypeEntry(
							objectField.getListTypeDefinitionId(), key);

					if (listTypeEntry == null) {
						continue;
					}

					keyLocalizedLabelPairs.add(
						new KeyLocalizedLabelPair(
							listTypeEntry.getName(serviceContext.getLocale()),
							InfoLocalizedValue.<String>builder(
							).defaultLocale(
								LocaleUtil.fromLanguageId(
									listTypeEntry.getDefaultLanguageId())
							).values(
								listTypeEntry.getNameMap()
							).build()));
				}

				return keyLocalizedLabelPairs;
			}

			ListTypeEntry listTypeEntry =
				_listTypeEntryLocalService.fetchListTypeEntry(
					objectField.getListTypeDefinitionId(),
					(String)values.get(objectField.getName()));

			if (listTypeEntry == null) {
				return StringPool.BLANK;
			}

			return ListUtil.fromArray(
				new KeyLocalizedLabelPair(
					listTypeEntry.getName(serviceContext.getLocale()),
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							listTypeEntry.getDefaultLanguageId())
					).values(
						listTypeEntry.getNameMap()
					).build()));
		}
		else if (Validator.isNotNull(objectField.getRelationshipType()) &&
				 (GetterUtil.getLong(value) > 0)) {

			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectFieldId2(
						objectField.getObjectFieldId());

			return _objectEntryLocalService.getTitleValue(
				objectRelationship.getObjectDefinitionId1(),
				(Long)values.get(objectField.getName()));
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			long dlFileEntryId = GetterUtil.getLong(
				values.get(objectField.getName()));

			if (dlFileEntryId == GetterUtil.DEFAULT_LONG) {
				return StringPool.BLANK;
			}

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
				dlFileEntryId);

			if (dlFileEntry == null) {
				return StringPool.BLANK;
			}

			return dlFileEntry.getFileName();
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_DATE)) {

			Format dateFormat = FastDateFormatFactoryUtil.getDate(
				serviceContext.getLocale());

			Object dateValue = values.get(objectField.getName());

			Date date = DateUtil.parseDate(
				"yyyy-MM-dd", dateValue.toString(), serviceContext.getLocale());

			return dateFormat.format(date);
		}

		return values.get(objectField.getName());
	}

	private WebImage _getWebImage(long userId) throws Exception {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			return null;
		}

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			WebImage webImage = new WebImage(user.getPortraitURL(themeDisplay));

			webImage.setAlt(user.getFullName());

			return webImage;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemFieldValuesProvider.class);

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final DLAppLocalService _dlAppLocalService;
	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private final DLURLHelper _dlURLHelper;
	private final InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;
	private final JSONFactory _jsonFactory;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final TemplateInfoItemFieldSetProvider
		_templateInfoItemFieldSetProvider;
	private final UserLocalService _userLocalService;

}