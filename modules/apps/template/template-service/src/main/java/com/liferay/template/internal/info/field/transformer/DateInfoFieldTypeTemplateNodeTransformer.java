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

package com.liferay.template.internal.info.field.transformer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.template.info.field.transformer.BaseTemplateNodeTransformer;
import com.liferay.template.info.field.transformer.TemplateNodeTransformer;

import java.text.DateFormat;
import java.text.ParseException;

import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

import java.util.Collections;
import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "info.field.type.class.name=com.liferay.info.field.type.DateInfoFieldType",
	service = TemplateNodeTransformer.class
)
public class DateInfoFieldTypeTemplateNodeTransformer
	extends BaseTemplateNodeTransformer {

	@Override
	public TemplateNode transform(
		InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		String stringValue = StringPool.BLANK;

		Object value = infoFieldValue.getValue(themeDisplay.getLocale());

		if (value instanceof Date) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				DateTimeFormatterBuilder.getLocalizedDateTimePattern(
					FormatStyle.SHORT, FormatStyle.SHORT,
					IsoChronology.INSTANCE, themeDisplay.getLocale()),
				themeDisplay.getLocale());

			stringValue = dateFormat.format((Date)value);
		}
		else if (value instanceof String) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"MM/dd/yy hh:mm a", themeDisplay.getLocale());

			try {
				Date date = dateFormat.parse(value.toString());

				dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
					DateTimeFormatterBuilder.getLocalizedDateTimePattern(
						FormatStyle.SHORT, null, IsoChronology.INSTANCE,
						themeDisplay.getLocale()),
					themeDisplay.getLocale());

				stringValue = dateFormat.format(date);
			}
			catch (ParseException parseException1) {
				if (_log.isDebugEnabled()) {
					_log.debug(parseException1);
				}

				dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
					"MM/dd/yy", themeDisplay.getLocale());

				try {
					Date date = dateFormat.parse(value.toString());

					dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
						DateTimeFormatterBuilder.getLocalizedDateTimePattern(
							FormatStyle.SHORT, null, IsoChronology.INSTANCE,
							themeDisplay.getLocale()),
						themeDisplay.getLocale());

					stringValue = dateFormat.format(date);
				}
				catch (ParseException parseException2) {
					if (_log.isDebugEnabled()) {
						_log.debug(parseException2);
					}

					stringValue = value.toString();
				}
			}
		}

		InfoField infoField = infoFieldValue.getInfoField();

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		return new TemplateNode(
			themeDisplay, infoField.getName(), stringValue,
			infoFieldType.getName(), Collections.emptyMap());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DateInfoFieldTypeTemplateNodeTransformer.class);

}