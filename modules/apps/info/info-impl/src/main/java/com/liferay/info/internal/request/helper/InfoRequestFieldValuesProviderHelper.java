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

package com.liferay.info.internal.request.helper;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class InfoRequestFieldValuesProviderHelper {

	public InfoRequestFieldValuesProviderHelper(
		InfoItemServiceTracker infoItemServiceTracker) {

		_infoItemServiceTracker = infoItemServiceTracker;
	}

	public <T> List<InfoFieldValue<Object>> getInfoFieldValues(
		HttpServletRequest httpServletRequest) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		String className = PortalUtil.getClassName(
			ParamUtil.getLong(httpServletRequest, "classNameId"));

		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

		for (InfoField infoField : _getInfoFields(className)) {
			if (ArrayUtil.isNotEmpty(parameterMap.get(infoField.getName()))) {
				String[] values = parameterMap.get(infoField.getName());

				for (String value : values) {
					infoFieldValues.add(_getInfoFieldValue(infoField, value));
				}
			}
		}

		return infoFieldValues;
	}

	private InfoFieldValue<Object> _getDateInfoFieldValue(
		InfoField<DateInfoFieldType> infoField, String value) {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		Date date;

		try {
			date = DateUtil.parseDate("yyyy-MM-dd", value, locale);
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException);
			}

			return null;
		}

		return _getInfoFieldValue(infoField, locale, date);
	}

	private <T> List<InfoField> _getInfoFields(String className) {
		InfoItemFormProvider<T> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, className);

		InfoForm infoForm = infoItemFormProvider.getInfoForm();

		return infoForm.getAllInfoFields();
	}

	private InfoFieldValue<Object> _getInfoFieldValue(
		InfoField infoField, Locale locale, Object object) {

		if (infoField.isLocalizable()) {
			return new InfoFieldValue<>(
				infoField,
				InfoLocalizedValue.builder(
				).defaultLocale(
					locale
				).value(
					locale, object
				).build());
		}

		return new InfoFieldValue<>(infoField, object);
	}

	private InfoFieldValue<Object> _getInfoFieldValue(
		InfoField infoField, String value) {

		if (infoField.getInfoFieldType() instanceof DateInfoFieldType) {
			return _getDateInfoFieldValue(infoField, value);
		}

		if (infoField.getInfoFieldType() instanceof TextInfoFieldType) {
			return _getTextInfoFieldValue(infoField, value);
		}

		return null;
	}

	private InfoFieldValue<Object> _getTextInfoFieldValue(
		InfoField<TextInfoFieldType> infoField, String value) {

		return _getInfoFieldValue(
			infoField, LocaleThreadLocal.getThemeDisplayLocale(), value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoRequestFieldValuesProviderHelper.class);

	private final InfoItemServiceTracker _infoItemServiceTracker;

}