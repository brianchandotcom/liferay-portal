/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.info.field.converter.ObjectFieldInfoFieldConverter;
import com.liferay.object.info.item.ObjectEntryInfoItemFields;
import com.liferay.object.info.item.provider.util.ObjectEntryInfoItemValuesProviderUtil;
import com.liferay.object.info.item.util.ObjectEntryInfoItemUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.model.ProxyObjectEntry;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<ObjectEntry> {

	public ObjectEntryInfoItemFieldValuesProvider(
		DisplayPageInfoItemFieldSetProvider displayPageInfoItemFieldSetProvider,
		DLAppLocalService dlAppLocalService, DLURLHelper dlURLHelper,
		InfoItemFieldReaderFieldSetProvider infoItemFieldReaderFieldSetProvider,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldInfoFieldConverter objectFieldInfoFieldConverter,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManagerRegistry objectEntryManagerRegistry,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		TemplateInfoItemFieldSetProvider templateInfoItemFieldSetProvider,
		UserLocalService userLocalService) {

		_displayPageInfoItemFieldSetProvider =
			displayPageInfoItemFieldSetProvider;
		_dlAppLocalService = dlAppLocalService;
		_dlURLHelper = dlURLHelper;
		_infoItemFieldReaderFieldSetProvider =
			infoItemFieldReaderFieldSetProvider;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectActionLocalService = objectActionLocalService;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldInfoFieldConverter = objectFieldInfoFieldConverter;
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
					ObjectEntry.class.getSimpleName(), objectEntry,
					_getThemeDisplay())
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

		ThemeDisplay themeDisplay = ObjectEntryInfoItemUtil.getThemeDisplay();

		Map<String, Object> properties = new HashMap<>();

		com.liferay.object.rest.dto.v1_0.ObjectEntry dtoObjectEntry =
			_getObjectEntry(_objectDefinition, objectEntry, themeDisplay);

		if (dtoObjectEntry != null) {
			properties = dtoObjectEntry.getProperties();
		}

		objectEntryFieldValues.addAll(
			ObjectEntryInfoItemValuesProviderUtil.getInfoFieldValues(
				_dlAppLocalService, _dlURLHelper, _listTypeEntryLocalService,
				_objectActionLocalService, _objectDefinition,
				_objectDefinitionLocalService, _objectEntryLocalService,
				_objectEntryManagerRegistry, _objectFieldInfoFieldConverter,
				_objectFieldLocalService,
				_objectFieldLocalService.getObjectFields(
					objectEntry.getObjectDefinitionId()),
				_objectRelationshipLocalService, _objectScopeProviderRegistry,
				themeDisplay, properties));

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
				_objectDefinition, serviceBuilderObjectEntry, themeDisplay);

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
		objectEntryFieldValues.addAll(
			ObjectEntryInfoItemValuesProviderUtil.getInfoFieldValues(
				_dlAppLocalService, _dlURLHelper, _listTypeEntryLocalService,
				_objectActionLocalService, _objectDefinition,
				_objectDefinitionLocalService, _objectEntryLocalService,
				_objectEntryManagerRegistry, _objectFieldInfoFieldConverter,
				_objectFieldLocalService,
				_objectFieldLocalService.getObjectFields(
					serviceBuilderObjectEntry.getObjectDefinitionId()),
				_objectRelationshipLocalService, _objectScopeProviderRegistry,
				themeDisplay, objectEntry.getProperties()));

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

	private com.liferay.object.rest.dto.v1_0.ObjectEntry _getObjectEntry(
		ObjectDefinition objectDefinition, ObjectEntry objectEntry,
		ThemeDisplay themeDisplay) {

		if (objectEntry instanceof ProxyObjectEntry) {
			ProxyObjectEntry proxyObjectEntry = (ProxyObjectEntry)objectEntry;

			com.liferay.object.rest.dto.v1_0.ObjectEntry dtoObjectEntry =
				proxyObjectEntry.getDTOObjectEntry();

			if (dtoObjectEntry != null) {
				return dtoObjectEntry;
			}
		}

		return ObjectEntryInfoItemUtil.getObjectEntry(
			objectDefinition, _objectEntryManagerRegistry,
			_objectScopeProviderRegistry, objectEntry, themeDisplay);
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
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

	private final DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;
	private final DLAppLocalService _dlAppLocalService;
	private final DLURLHelper _dlURLHelper;
	private final InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectActionLocalService _objectActionLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;
	private final ObjectFieldInfoFieldConverter _objectFieldInfoFieldConverter;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final TemplateInfoItemFieldSetProvider
		_templateInfoItemFieldSetProvider;
	private final UserLocalService _userLocalService;

}