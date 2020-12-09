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

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
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
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.GroupUrlTitleInfoItemIdentifier;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

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
			   InfoItemDetailsProvider<Object>,
			   InfoItemFieldValuesProvider<Object>,
			   InfoItemFormProvider<Object>,
			   InfoItemFormVariationsProvider<Object>,
			   InfoItemObjectProvider<Object> {

	public InfoDisplayContributorWrapper(
		AssetEntryInfoItemFieldSetProvider assetEntryInfoItemFieldSetProvider,
		AssetEntryLocalService assetEntryLocalService,
		InfoDisplayContributor<Object> infoDisplayContributor,
		List<InfoItemCapability> infoItemCapabilities) {

		_assetEntryInfoItemFieldSetProvider =
			assetEntryInfoItemFieldSetProvider;
		_assetEntryLocalService = assetEntryLocalService;
		_infoDisplayContributor = infoDisplayContributor;
		_infoItemCapabilities = infoItemCapabilities;
	}

	@Override
	public InfoForm getInfoForm() {
		Locale locale = _getLocale();

		try {
			return _convertToInfoForm(
				_infoDisplayContributor.getInfoDisplayFields(0, locale),
				_infoDisplayContributor.getClassName(), null);
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
				_infoDisplayContributor.getClassName(), null);
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
				_infoDisplayContributor.getClassName(), itemObject);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public Object getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof GroupUrlTitleInfoItemIdentifier)) {

			throw new NoSuchInfoItemException(
				"Unsupported info item identifier type " + infoItemIdentifier);
		}

		InfoDisplayObjectProvider<?> infoDisplayObjectProvider = null;

		try {
			if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
				ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
					(ClassPKInfoItemIdentifier)infoItemIdentifier;

				infoDisplayObjectProvider =
					_infoDisplayContributor.getInfoDisplayObjectProvider(
						classPKInfoItemIdentifier.getClassPK());
			}
			else if (infoItemIdentifier instanceof
						GroupUrlTitleInfoItemIdentifier) {

				GroupUrlTitleInfoItemIdentifier
					groupURLTitleInfoItemIdentifier =
						(GroupUrlTitleInfoItemIdentifier)infoItemIdentifier;

				infoDisplayObjectProvider =
					_infoDisplayContributor.getInfoDisplayObjectProvider(
						groupURLTitleInfoItemIdentifier.getGroupId(),
						groupURLTitleInfoItemIdentifier.getUrlTitle());
			}

			return infoDisplayObjectProvider.getDisplayObject();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public Object getInfoItem(long classPK) throws NoSuchInfoItemException {
		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			classPK);

		return getInfoItem(infoItemIdentifier);
	}

	@Override
	public List<InfoItemCapability> getInfoItemCapabilities() {
		return _infoItemCapabilities;
	}

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(
			_infoDisplayContributor.getClassName(),
			(InfoLocalizedValue<String>)InfoLocalizedValue.function(
				locale -> _infoDisplayContributor.getLabel(locale)));
	}

	@Override
	public InfoItemDetails getInfoItemDetails(Object itemObject) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				_infoDisplayContributor.getClassName(),
				_infoDisplayContributor.getInfoDisplayObjectClassPK(
					itemObject)));
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(Object itemObject) {
		Locale locale = _getLocale();

		try {
			return _convertToInfoItemFieldValues(
				_infoDisplayContributor.getInfoDisplayFieldsValues(
					itemObject, locale),
				new InfoItemReference(
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
		Set<InfoDisplayField> infoDisplayFields, String name, Object object) {

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		InfoLocalizedValue.Builder infoLocalizedValueBuilder =
			InfoLocalizedValue.builder();

		for (Locale locale : availableLocales) {
			infoLocalizedValueBuilder.value(
				locale, ResourceActionsUtil.getModelResource(locale, name));
		}

		InfoForm.Builder infoFormBuilder = InfoForm.builder(
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
		).labelInfoLocalizedValue(
			infoLocalizedValueBuilder.build()
		).name(
			name
		);

		if (Objects.equals(name, FileEntry.class.getName())) {
			long classPK = _infoDisplayContributor.getInfoDisplayObjectClassPK(
				object);

			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				DLFileEntry.class.getName(), classPK);

			if (assetEntry != null) {
				infoFormBuilder.infoFieldSetEntry(
					_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
						assetEntry));
			}
		}

		return infoFormBuilder.build();
	}

	private InfoItemFieldValues _convertToInfoItemFieldValues(
		Map<String, Object> infoDisplayFieldsValues,
		InfoItemReference infoItemReference) {

		InfoItemFieldValues.Builder infoItemFieldValuesBuilder =
			InfoItemFieldValues.builder(
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
			);

		if (Objects.equals(
				infoItemReference.getClassName(), FileEntry.class.getName())) {

			try {
				infoItemFieldValuesBuilder.infoFieldValues(
					_assetEntryInfoItemFieldSetProvider.getInfoFieldValues(
						DLFileEntry.class.getName(),
						infoItemReference.getClassPK()));
			}
			catch (NoSuchInfoItemException noSuchInfoItemException) {
				throw new RuntimeException(
					"Caught unexpected exception", noSuchInfoItemException);
			}
		}

		return infoItemFieldValuesBuilder.infoItemReference(
			infoItemReference
		).build();
	}

	private InfoFieldType _getInfoFieldTypeType(String infoDisplayFieldType) {
		if (Objects.equals(
				infoDisplayFieldType,
				InfoDisplayContributorFieldType.IMAGE.getValue()) ||
			Objects.equals(infoDisplayFieldType, DDMFormFieldType.IMAGE) ||
			Objects.equals(infoDisplayFieldType, "image")) {

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

	private final AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;
	private final AssetEntryLocalService _assetEntryLocalService;
	private final InfoDisplayContributor<Object> _infoDisplayContributor;
	private final List<InfoItemCapability> _infoItemCapabilities;

}