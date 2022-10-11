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
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;
import com.liferay.object.web.internal.util.ObjectFieldDBTypeUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class ExternalObjectEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<ObjectEntry> {

	public ExternalObjectEntryInfoItemFieldValuesProvider(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		InfoItemFieldReaderFieldSetProvider infoItemFieldReaderFieldSetProvider,
		JSONFactory jsonFactory,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectEntryManagerTracker objectEntryManagerTracker,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		TemplateInfoItemFieldSetProvider templateInfoItemFieldSetProvider) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_infoItemFieldReaderFieldSetProvider =
			infoItemFieldReaderFieldSetProvider;
		_jsonFactory = jsonFactory;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectEntryManagerTracker = objectEntryManagerTracker;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_templateInfoItemFieldSetProvider = templateInfoItemFieldSetProvider;
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(ObjectEntry objectEntry) {
		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getObjectEntryInfoFieldValues(objectEntry)
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
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			objectEntry.getModelClassName(), objectEntry.getObjectEntryId(),
			themeDisplay);
	}

	private List<InfoFieldValue<Object>> _getObjectEntryInfoFieldValues(
		ObjectEntry serviceBuilderObjectEntry) {

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = _getThemeDisplay();

			ObjectDefinition objectDefinition =
				ObjectDefinitionLocalServiceUtil.getObjectDefinition(
					serviceBuilderObjectEntry.getObjectDefinitionId());

			ObjectEntryManager objectEntryManager =
				_objectEntryManagerTracker.getObjectEntryManager(
					objectDefinition.getStorageType());

			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
				objectEntryManager.getObjectEntry(
					new DefaultDTOConverterContext(
						false, null, null, null, null,
						serviceContext.getLocale(), null,
						themeDisplay.getUser()),
					serviceBuilderObjectEntry.getExternalReferenceCode(),
					serviceContext.getCompanyId(), objectDefinition, null);

			List<InfoFieldValue<Object>> objectEntryFieldValues =
				new ArrayList<>();

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

			if (themeDisplay != null) {
				objectEntryFieldValues.add(
					new InfoFieldValue<>(
						ObjectEntryInfoItemFields.displayPageURLInfoField,
						_getDisplayPageURL(
							serviceBuilderObjectEntry, themeDisplay)));
			}

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
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
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
			ObjectField objectField, Map<String, Object> values)
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

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;
	private final JSONFactory _jsonFactory;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManagerTracker _objectEntryManagerTracker;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final TemplateInfoItemFieldSetProvider
		_templateInfoItemFieldSetProvider;

}