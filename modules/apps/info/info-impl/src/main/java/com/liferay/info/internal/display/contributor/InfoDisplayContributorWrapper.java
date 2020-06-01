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

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.InfoFormValues;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class InfoDisplayContributorWrapper
	implements InfoItemFormProvider, InfoItemObjectProvider {

	public InfoDisplayContributorWrapper(
		InfoDisplayContributor<Object> infoDisplayContributor) {

		_infoDisplayContributor = infoDisplayContributor;
	}

	@Override
	public InfoForm getInfoForm() {
		Locale locale = _getLocale();

		try {
			Set<InfoDisplayField> infoDisplayFields =
				_infoDisplayContributor.getInfoDisplayFields(0, locale);

			return _convertToInfoForm(infoDisplayFields);
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
					itemClassTypeId, locale));
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
					itemObject, locale));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public InfoFormValues getInfoFormValues(Object itemObject) {
		Locale locale = _getLocale();

		try {
			return _convertToInfoFormValues(
				_infoDisplayContributor.getInfoDisplayFieldsValues(
					itemObject, locale));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public Object getInfoItem(long itemClassPK) throws NoSuchInfoItemException {
		try {
			InfoDisplayObjectProvider<?> infoDisplayObjectProvider =
				_infoDisplayContributor.getInfoDisplayObjectProvider(
					itemClassPK);

			return infoDisplayObjectProvider.getDisplayObject();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private InfoForm _convertToInfoForm(
		Set<InfoDisplayField> infoDisplayFields) {

		Locale locale = _getLocale();

		InfoForm infoForm = new InfoForm("fields");

		for (InfoDisplayField infoDisplayField : infoDisplayFields) {
			InfoFieldType infoFieldType = _getInfoFieldTypeType(
				infoDisplayField.getType());

			InfoLocalizedValue labelInfoLocalizedValue =
				InfoLocalizedValue.builder(
				).addValue(
					locale, infoDisplayField.getLabel()
				).build();

			InfoField infoField = new InfoField(
				infoFieldType, labelInfoLocalizedValue,
				infoDisplayField.getKey());

			infoForm.add(infoField);
		}

		return infoForm;
	}

	private InfoFormValues _convertToInfoFormValues(
		Map<String, Object> infoDisplayFieldsValues) {

		Locale locale = _getLocale();

		InfoFormValues infoFormValues = new InfoFormValues();

		for (Map.Entry<String, Object> entry :
				infoDisplayFieldsValues.entrySet()) {

			String fieldName = entry.getKey();

			InfoLocalizedValue<String> fieldLabelLocalizedValue =
				InfoLocalizedValue.builder(
				).addValue(
					locale, fieldName
				).build();

			InfoField infoField = new InfoField(
				TextInfoFieldType.INSTANCE, fieldLabelLocalizedValue,
				fieldName);

			InfoFieldValue infoFormValue = new InfoFieldValue(
				infoField, entry.getValue());

			infoFormValues.add(infoFormValue);
		}

		return infoFormValues;
	}

	private InfoFieldType _getInfoFieldTypeType(String infoDisplayFieldType) {
		if (Objects.equals(
				infoDisplayFieldType,
				InfoDisplayContributorFieldType.IMAGE.getValue())) {

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