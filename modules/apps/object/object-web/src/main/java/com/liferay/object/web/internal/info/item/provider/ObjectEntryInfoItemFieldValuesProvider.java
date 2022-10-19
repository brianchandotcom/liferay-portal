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
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.WebImage;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerTracker;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;
import com.liferay.object.web.internal.util.ObjectFieldDBTypeUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.io.Serializable;

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
		InfoItemFieldReaderFieldSetProvider infoItemFieldReaderFieldSetProvider,
		JSONFactory jsonFactory,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManagerTracker objectEntryManagerTracker,
		ObjectFieldLocalService objectFieldLocalService,
		TemplateInfoItemFieldSetProvider templateInfoItemFieldSetProvider,
		UserLocalService userLocalService) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_infoItemFieldReaderFieldSetProvider =
			infoItemFieldReaderFieldSetProvider;
		_jsonFactory = jsonFactory;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManagerTracker = objectEntryManagerTracker;
		_objectFieldLocalService = objectFieldLocalService;
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
				ObjectEntry.class.getName(), objectEntry)
		).infoFieldValues(
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				objectEntry.getModelClassName(), objectEntry)
		).infoItemReference(
			new InfoItemReference(
				ObjectEntry.class.getName(), objectEntry.getObjectEntryId())
		).build();
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
				ObjectEntryInfoItemFields.createDateInfoField,
				objectEntry.getCreateDate()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.modifiedDateInfoField,
				objectEntry.getModifiedDate()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.publishDateInfoField,
				objectEntry.getLastPublishDate()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.userNameInfoField,
				objectEntry.getUserName()));
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

		Map<String, Serializable> values = objectEntry.getValues();

		objectEntryFieldValues.addAll(
			TransformUtil.transform(
				_objectFieldLocalService.getObjectFields(
					objectEntry.getObjectDefinitionId()),
				objectField -> new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						ObjectFieldDBTypeUtil.getInfoFieldType(objectField)
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.<String>builder(
						).values(
							objectField.getLabelMap()
						).build()
					).build(),
					_getValue(objectField, values))));

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
			_objectEntryManagerTracker.getObjectEntryManager(
				_objectDefinition.getStorageType());

		com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
			objectEntryManager.getObjectEntry(
				new DefaultDTOConverterContext(
					false, null, null, null, null, themeDisplay.getLocale(),
					null, themeDisplay.getUser()),
				serviceBuilderObjectEntry.getExternalReferenceCode(),
				themeDisplay.getCompanyId(), _objectDefinition, null);

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

		Map<String, Object> properties = objectEntry.getProperties();

		objectEntryFieldValues.addAll(
			TransformUtil.transform(
				_objectFieldLocalService.getObjectFields(
					serviceBuilderObjectEntry.getObjectDefinitionId()),
				objectField -> new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						ObjectFieldDBTypeUtil.getInfoFieldType(objectField)
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.<String>builder(
						).values(
							objectField.getLabelMap()
						).build()
					).build(),
					_getValue(objectField, properties))));

		return objectEntryFieldValues;
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
			ListTypeEntry listTypeEntry =
				_listTypeEntryLocalService.fetchListTypeEntry(
					objectField.getListTypeDefinitionId(),
					(String)values.get(objectField.getName()));

			return listTypeEntry.getName(serviceContext.getLocale());
		}
		else if (Validator.isNotNull(objectField.getRelationshipType()) &&
				 (GetterUtil.getLong(value) > 0)) {

			ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
				(Long)values.get(objectField.getName()));

			if (objectEntry != null) {
				return objectEntry.getTitleValue();
			}
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

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;
	private final JSONFactory _jsonFactory;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManagerTracker _objectEntryManagerTracker;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final TemplateInfoItemFieldSetProvider
		_templateInfoItemFieldSetProvider;
	private final UserLocalService _userLocalService;

}