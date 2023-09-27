/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.render;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.BaseDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.ValueAccessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Marcellus Tavares
 */
public class DateDDMFormFieldValueRenderer
	extends BaseDDMFormFieldValueRenderer {

	@Override
	public String getSupportedDDMFormFieldType() {
		return DDMFormFieldType.DATE;
	}

	@Override
	protected ValueAccessor getValueAccessor(Locale locale) {
		return new ValueAccessor(locale) {

			@Override
			public String get(DDMFormFieldValue ddmFormFieldValue) {
				Value value = ddmFormFieldValue.getValue();

				String valueString = value.getString(locale);

				if (Validator.isNull(valueString)) {
					return StringPool.BLANK;
				}

				return _format(valueString, locale);
			}

		};
	}

	private String _format(Serializable value, Locale locale) {
		try {
			SimpleDateFormat simpleDateFormat =
				(SimpleDateFormat)DateFormat.getDateInstance(
					SimpleDateFormat.SHORT, locale);

			String pattern = simpleDateFormat.toPattern();

			return DateUtil.getDate(
				DateUtil.parseDate("yyyy-MM-dd", value.toString(), locale),
				pattern.replaceAll("\\byy\\b", "yyyy"), locale);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return LanguageUtil.format(
				locale, "is-temporarily-unavailable", "content");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DateDDMFormFieldValueRenderer.class);

}