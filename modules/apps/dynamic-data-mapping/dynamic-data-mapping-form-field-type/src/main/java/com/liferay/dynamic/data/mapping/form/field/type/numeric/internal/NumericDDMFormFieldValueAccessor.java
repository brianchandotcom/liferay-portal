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

package com.liferay.dynamic.data.mapping.form.field.type.numeric.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.math.BigDecimal;

import java.text.NumberFormat;
import java.text.ParseException;

import java.util.Locale;
import java.util.function.IntFunction;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=numeric",
	service = {
		DDMFormFieldValueAccessor.class, NumericDDMFormFieldValueAccessor.class
	}
)
public class NumericDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<BigDecimal> {

	@Override
	public IntFunction<BigDecimal[]> getArrayGeneratorFunction() {
		return BigDecimal[]::new;
	}

	@Override
	public BigDecimal getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		try {
			NumberFormat formatter = NumericDDMFormFieldUtil.getNumberFormat(
				locale);

			return (BigDecimal)formatter.parse(value.getString(locale));
		}
		catch (ParseException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NumericDDMFormFieldValueAccessor.class);

}