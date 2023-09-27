/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.ActionInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.info.type.WebImage;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;
import com.liferay.object.web.internal.util.ObjectEntryUtil;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<ObjectEntry> {

	public ObjectEntryInfoItemFieldValuesProvider(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		DisplayPageInfoItemFieldSetProvider displayPageInfoItemFieldSetProvider,
		DLAppLocalService dlAppLocalService, DLURLHelper dlURLHelper,
		InfoItemFieldReaderFieldSetProvider infoItemFieldReaderFieldSetProvider,
		JSONFactory jsonFactory,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManagerRegistry objectEntryManagerRegistry,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		TemplateInfoItemFieldSetProvider templateInfoItemFieldSetProvider,
		UserLocalService userLocalService) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_displayPageInfoItemFieldSetProvider =
			displayPageInfoItemFieldSetProvider;
		_dlAppLocalService = dlAppLocalService;
		_dlURLHelper = dlURLHelper;
		_infoItemFieldReaderFieldSetProvider =
			infoItemFieldReaderFieldSetProvider;
		_jsonFactory = jsonFactory;
		_objectActionLocalService = objectActionLocalService;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManagerRegistry = objectEntryManagerRegistry;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_templateInfoItemFieldSetProvider = templateInfoItemFieldSetProvider;
		_userLocalService = userLocalService;
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(ObjectEntry objectEntry) {
		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getInfoFieldValues(objectEntry)
			).infoFieldValues(
				_displayPageInfoItemFieldSetProvider.getInfoFieldValues(
					_getInfoItemReference(objectEntry), StringPool.BLANK,
					ObjectEntry.class.getSimpleName(), _getThemeDisplay())
			).infoFieldValues(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
					objectEntry.getModelClassName(), objectEntry)
			).infoFieldValues(
				_templateInfoItemFieldSetProvider.getInfoFieldValues(
					objectEntry.getModelClassName(), objectEntry)
			).infoItemReference(
				_getInfoItemReference(objectEntry)
			).build();
		}
		catch (Exception exception) {
			throw new RuntimeException("Unexpected exception", exception);
		}
	}

	private List<InfoFieldValue<Object>> _getAttachmentInfoFieldValues(
		ObjectField objectField, Object value) {

		if (!objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			return Collections.emptyList();
		}

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		try {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(
				GetterUtil.getLong(value));

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
						ImageInfoFieldType.INSTANCE
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
			_getInfoItemReference(objectEntry), themeDisplay);
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

		if ((themeDisplay != null) &&
			!FeatureFlagManagerUtil.isEnabled("LPS-195205")) {

			objectEntryFieldValues.add(
				new InfoFieldValue<>(
					ObjectEntryInfoItemFields.displayPageURLInfoField,
					_getDisplayPageURL(objectEntry, themeDisplay)));
		}

		if (themeDisplay != null) {
			objectEntryFieldValues.addAll(
				_getObjectFieldsInfoFieldValues(
					_getObjectEntry(
						objectEntry.getExternalReferenceCode(),
						_objectDefinition, themeDisplay),
					_objectFieldLocalService.getObjectFields(
						objectEntry.getObjectDefinitionId(), false),
					themeDisplay));
		}

		objectEntryFieldValues.addAll(
			TransformUtil.transform(
				_objectActionLocalService.getObjectActions(
					_objectDefinition.getObjectDefinitionId(),
					ObjectActionTriggerConstants.KEY_STANDALONE),
				objectAction -> {
					InfoLocalizedValue<String> actionLabelLocalizedValue =
						InfoLocalizedValue.<String>builder(
						).defaultLocale(
							LocaleUtil.fromLanguageId(
								objectAction.getDefaultLanguageId())
						).values(
							objectAction.getLabelMap()
						).build();

					return new InfoFieldValue<>(
						InfoField.builder(
						).infoFieldType(
							ActionInfoFieldType.INSTANCE
						).namespace(
							ObjectAction.class.getSimpleName()
						).name(
							objectAction.getName()
						).labelInfoLocalizedValue(
							actionLabelLocalizedValue
						).build(),
						actionLabelLocalizedValue);
				}));

		return objectEntryFieldValues;
	}

	private List<InfoFieldValue<Object>>
			_getInfoFieldValuesByObjectEntryManager(
				ObjectEntry serviceBuilderObjectEntry)
		throws Exception {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay == null) {
			return Collections.emptyList();
		}

		List<InfoFieldValue<Object>> objectEntryFieldValues = new ArrayList<>();

		com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
			_getObjectEntry(
				serviceBuilderObjectEntry.getExternalReferenceCode(),
				_objectDefinition, themeDisplay);

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
				objectEntry,
				_objectFieldLocalService.getObjectFields(
					serviceBuilderObjectEntry.getObjectDefinitionId(), false),
				themeDisplay));

		return objectEntryFieldValues;
	}

	private InfoItemReference _getInfoItemReference(ObjectEntry objectEntry) {
		if (_objectDefinition.isDefaultStorageType()) {
			return new InfoItemReference(
				objectEntry.getModelClassName(),
				new ClassPKInfoItemIdentifier(objectEntry.getObjectEntryId()));
		}

		return new InfoItemReference(
			_objectDefinition.getClassName(),
			new ERCInfoItemIdentifier(objectEntry.getExternalReferenceCode()));
	}

	private KeyLocalizedLabelPair _getKeyLocalizedLabelPair(
		ListTypeEntry listTypeEntry, Locale locale) {

		return new KeyLocalizedLabelPair(
			listTypeEntry.getName(locale),
			InfoLocalizedValue.<String>builder(
			).defaultLocale(
				LocaleUtil.fromLanguageId(listTypeEntry.getDefaultLanguageId())
			).values(
				listTypeEntry.getNameMap()
			).build());
	}

	private com.liferay.object.rest.dto.v1_0.ObjectEntry _getObjectEntry(
		String externalReferenceCode, ObjectDefinition objectDefinition,
		ThemeDisplay themeDisplay) {

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getStorageType());

		try {
			return objectEntryManager.getObjectEntry(
				themeDisplay.getCompanyId(),
				new DefaultDTOConverterContext(
					false, null, null, null, null, themeDisplay.getLocale(),
					null, themeDisplay.getUser()),
				externalReferenceCode, objectDefinition,
				ObjectEntryUtil.getScopeKey(
					themeDisplay.getScopeGroupId(), objectDefinition,
					_objectScopeProviderRegistry));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private List<InfoFieldValue<Object>> _getObjectFieldsInfoFieldValues(
			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry,
			List<ObjectField> objectFields, ThemeDisplay themeDisplay)
		throws Exception {

		if (objectEntry == null) {
			return Collections.emptyList();
		}

		List<InfoFieldValue<Object>> objectFieldsInfoFieldValues =
			new ArrayList<>();

		for (ObjectField objectField : objectFields) {
			Object value = _getValue(objectEntry, objectField, themeDisplay);

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
					value));
			objectFieldsInfoFieldValues.addAll(
				_getAttachmentInfoFieldValues(objectField, value));

			objectFieldsInfoFieldValues.addAll(
				_getRelatedObjectEntryFieldValues(
					objectField, themeDisplay, objectEntry.getProperties()));
		}

		return objectFieldsInfoFieldValues;
	}

	private List<InfoFieldValue<Object>> _getRelatedObjectEntryFieldValues(
			ObjectField objectField, ThemeDisplay themeDisplay,
			Map<String, Object> values)
		throws Exception {

		if (!objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

			return Collections.emptyList();
		}

		ObjectEntry serviceBuilderObjectEntry =
			_objectEntryLocalService.fetchObjectEntry(
				GetterUtil.getLong(values.get(objectField.getName())));

		if (serviceBuilderObjectEntry == null) {
			return Collections.emptyList();
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				serviceBuilderObjectEntry.getObjectDefinitionId());

		com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
			_getObjectEntry(
				serviceBuilderObjectEntry.getExternalReferenceCode(),
				objectDefinition, themeDisplay);

		if (objectEntry == null) {
			return Collections.emptyList();
		}

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());

		return TransformUtil.transform(
			_objectFieldLocalService.getObjectFields(
				serviceBuilderObjectEntry.getObjectDefinitionId(), false),
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
				_getValue(objectEntry, relatedObjectField, themeDisplay)));
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
			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry,
			ObjectField objectField, ThemeDisplay themeDisplay)
		throws Exception {

		Object value = ObjectEntryUtil.getValue(
			themeDisplay.getLocale(), objectField, themeDisplay.getTimeZone(),
			objectEntry.getProperties());

		if (value == null) {
			return StringPool.BLANK;
		}

		if (Objects.equals(
				ObjectFieldDBTypeUtil.getInfoFieldType(objectField),
				ImageInfoFieldType.INSTANCE)) {

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				new String((byte[])value));

			WebImage webImage = new WebImage(jsonObject.getString("url"));

			webImage.setAlt(jsonObject.getString("alt"));

			return webImage;
		}

		if (objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			com.liferay.object.rest.dto.v1_0.FileEntry fileEntry =
				(com.liferay.object.rest.dto.v1_0.FileEntry)value;

			return fileEntry.getId();
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

			if (ListUtil.isEmpty((List<ListTypeEntry>)value)) {
				return StringPool.BLANK;
			}

			return ListUtil.toList(
				(List<ListTypeEntry>)value,
				listTypeEntry -> _getKeyLocalizedLabelPair(
					listTypeEntry, themeDisplay.getLocale()));
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			return ListUtil.fromArray(
				_getKeyLocalizedLabelPair(
					(ListTypeEntry)value, themeDisplay.getLocale()));
		}

		return value;
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
	private final DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;
	private final DLAppLocalService _dlAppLocalService;
	private final DLURLHelper _dlURLHelper;
	private final InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;
	private final JSONFactory _jsonFactory;
	private final ObjectActionLocalService _objectActionLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final TemplateInfoItemFieldSetProvider
		_templateInfoItemFieldSetProvider;
	private final UserLocalService _userLocalService;

}