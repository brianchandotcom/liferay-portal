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

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.BaseDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.ValueAccessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class CheckboxDDMFormFieldValueRenderer
	extends BaseDDMFormFieldValueRenderer {

	@Override
	public String getSupportedDDMFormFieldType() {
		return DDMFormFieldType.CHECKBOX;
	}

	@Override
	protected ValueAccessor getValueAcessor(Locale locale) {
		return new ValueAccessor(locale) {

			@Override
			public String get(DDMFormFieldValue ddmFormFieldValue) {
				Value value = ddmFormFieldValue.getValue();

				boolean valueBoolean = Boolean.parseBoolean(
					value.getString(locale));

				if (valueBoolean) {
					return LanguageUtil.get(locale, "yes");
				}

				return LanguageUtil.get(locale, "no");
			}

		};
	}

}