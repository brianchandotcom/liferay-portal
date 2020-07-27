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

package com.liferay.info.internal.display.contributor;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.info.item.provider.InfoItemClassDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class InfoDisplayContributorWrapper
	implements InfoItemCapabilitiesProvider<Object>,
			   InfoItemClassDetailsProvider<Object>,
			   InfoItemFieldValuesProvider<Object>,
			   InfoItemFormProvider<Object>,
			   InfoItemFormVariationsProvider<Object>,
			   InfoItemObjectProvider<Object> {

	public InfoDisplayContributorWrapper(
		InfoDisplayContributor<Object> infoDisplayContributor) {

		_infoDisplayContributor = infoDisplayContributor;
	}

	@Override
	public InfoForm getInfoForm() {
		Locale locale = _getLocale();

		try {
			return _convertToInfoForm(
				_infoDisplayContributor.getInfoDisplayFields(0, locale),
				_infoDisplayContributor.getClassName());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(long itemClassTypeId) {
		Locale locale = _getLocale();

		try {
			return _convertToInfoForm(
				_infoDisplayContributor.getInfoDisplayFields(
					itemClassTypeId, locale),
				_infoDisplayContributor.getClassName());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get info form with item class type ID " +
					itemClassTypeId,
				portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(Object itemObject) {
		Locale locale = _getLocale();

		try {
			return _convertToInfoForm(
				_infoDisplayContributor.getInfoDisplayFields(
					itemObject, locale),
				_infoDisplayContributor.getClassName());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public Object getInfoItem(InfoItemReference infoItemReference)
		throws NoSuchInfoItemException {

		try {
			InfoDisplayObjectProvider<?> infoDisplayObjectProvider =
				_infoDisplayContributor.getInfoDisplayObjectProvider(
					infoItemReference.getClassPK());

			return infoDisplayObjectProvider.getDisplayObject();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public Object getInfoItem(long classPK) throws NoSuchInfoItemException {
		InfoItemReference infoItemReference = new InfoItemReference(classPK);

		return getInfoItem(infoItemReference);
	}

	@Override
	public List<InfoItemCapability> getInfoItemCapabilities() {
		ArrayList<InfoItemCapability> infoItemCapabilities = new ArrayList<>();

		infoItemCapabilities.add(DisplayPageInfoItemCapability.INSTANCE);

		return infoItemCapabilities;
	}

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(
			_infoDisplayContributor.getClassName(),
			(InfoLocalizedValue<String>)InfoLocalizedValue.function(
				locale -> _infoDisplayContributor.getLabel(locale)));
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(Object itemObject) {
		Locale locale = _getLocale();

		try {
			return _convertToInfoItemFieldValues(
				_infoDisplayContributor.getInfoDisplayFieldsValues(
					itemObject, locale),
				new InfoItemClassPKReference(
					_infoDisplayContributor.getClassName(),
					_infoDisplayContributor.getInfoDisplayObjectClassPK(
						itemObject)));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long groupId) {

		Collection<InfoItemFormVariation> itemFormVariations = new HashSet<>();

		try {
			List<ClassType> classTypes = _infoDisplayContributor.getClassTypes(
				groupId, _getLocale());

			if (classTypes == null) {
				return itemFormVariations;
			}

			for (ClassType classType : classTypes) {
				itemFormVariations.add(
					new InfoItemFormVariation(
						String.valueOf(classType.getClassTypeId()),
						InfoLocalizedValue.singleValue(classType.getName())));
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get item form variations for class " +
					_infoDisplayContributor.getClassName(),
				portalException);
		}

		return itemFormVariations;
	}

	private InfoForm _convertToInfoForm(
		Set<InfoDisplayField> infoDisplayFields, String name) {

		return InfoForm.builder(
		).infoFieldSetEntry(
			consumer -> {
				for (InfoDisplayField infoDisplayField : infoDisplayFields) {
					consumer.accept(
						InfoField.builder(
						).infoFieldType(
							_getInfoFieldTypeType(infoDisplayField.getType())
						).name(
							infoDisplayField.getKey()
						).labelInfoLocalizedValue(
							InfoLocalizedValue.<String>builder(
							).value(
								_getLocale(), infoDisplayField.getLabel()
							).build()
						).build());
				}
			}
		).name(
			name
		).build();
	}

	private InfoItemFieldValues _convertToInfoItemFieldValues(
		Map<String, Object> infoDisplayFieldsValues,
		InfoItemClassPKReference infoItemClassPKReference) {

		return InfoItemFieldValues.builder(
		).infoFieldValue(
			consumer -> {
				for (Map.Entry<String, Object> entry :
						infoDisplayFieldsValues.entrySet()) {

					String fieldName = entry.getKey();

					InfoLocalizedValue<String> fieldLabelLocalizedValue =
						InfoLocalizedValue.<String>builder(
						).value(
							_getLocale(), fieldName
						).build();

					InfoField infoField = InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).name(
						fieldName
					).labelInfoLocalizedValue(
						fieldLabelLocalizedValue
					).build();

					consumer.accept(
						new InfoFieldValue<>(infoField, entry.getValue()));
				}
			}
		).infoItemClassPKReference(
			infoItemClassPKReference
		).build();
	}

	private InfoFieldType _getInfoFieldTypeType(String infoDisplayFieldType) {
		if (Objects.equals(
				infoDisplayFieldType,
				InfoDisplayContributorFieldType.IMAGE.getValue()) ||
			Objects.equals(infoDisplayFieldType, "ddm-image")) {

			return ImageInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					infoDisplayFieldType,
					InfoDisplayContributorFieldType.URL.getValue())) {

			return URLInfoFieldType.INSTANCE;
		}

		return TextInfoFieldType.INSTANCE;
	}

	private Locale _getLocale() {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		return locale;
	}

	private final InfoDisplayContributor<Object> _infoDisplayContributor;

}