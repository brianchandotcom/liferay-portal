/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	protected ValueAccessor getValueAccessor(Locale locale) {
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
					catch (ParseException parseException) {
						if (_log.isWarnEnabled()) {
							_log.warn(parseException);
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