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

package com.liferay.dynamic.data.mapping.internal.render;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.BaseDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.ValueAccessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.text.NumberFormat;
import java.text.ParseException;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseNumberDDMFormFieldValueRenderer
	extends BaseDDMFormFieldValueRenderer {

	@Override
	protected ValueAccessor getValueAcessor(Locale locale) {
		return new ValueAccessor(locale) {

			@Override
			public String get(DDMFormFieldValue ddmFormFieldValue) {
				Value value = ddmFormFieldValue.getValue();

				String valueString = value.getString(locale);

				Number number = GetterUtil.getNumber(valueString);

				NumberFormat numberFormat = NumberFormat.getNumberInstance(
					locale);

				if (!valueString.equals(number.toString())) {
					try {
						number = numberFormat.parse(valueString);
					}
					catch (ParseException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(pe, pe);
						}
					}
				}

				return numberFormat.format(number);
			}

		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseNumberDDMFormFieldValueRenderer.class);

}