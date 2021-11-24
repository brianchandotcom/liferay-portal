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

package com.liferay.dynamic.data.mapping.form.field.type.internal.date;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DATE,
	service = {
		DateDDMFormFieldValueRenderer.class, DDMFormFieldValueRenderer.class
	}
)
public class DateDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Value value = ddmFormFieldValue.getValue();

		return render(value.getString(locale), locale);
	}

	protected String render(String valueString, Locale locale) {
		if (Validator.isNotNull(valueString)) {
			try {
				SimpleDateFormat simpleDateFormat =
					(SimpleDateFormat)DateFormat.getDateInstance(
						DateFormat.SHORT,
						Optional.ofNullable(
							LocaleThreadLocal.getThemeDisplayLocale()
						).orElse(
							locale
						));

				String pattern = simpleDateFormat.toPattern();

				if (StringUtils.countMatches(pattern, "d") == 1) {
					pattern = StringUtil.replace(pattern, 'd', "dd");
				}

				if (StringUtils.countMatches(pattern, "M") == 1) {
					pattern = StringUtil.replace(pattern, 'M', "MM");
				}

				if (StringUtils.countMatches(pattern, "y") == 2) {
					pattern = StringUtil.replace(pattern, 'y', "yy");
				}

				Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
					pattern);

				return format.format(
					DateUtil.parseDate("yyyy-MM-dd", valueString, locale));
			}
			catch (ParseException parseException) {
				_log.error("Unable to parse date", parseException);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DateDDMFormFieldValueRenderer.class);

}