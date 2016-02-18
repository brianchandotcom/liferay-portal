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

package com.liferay.dynamic.data.mapping.storage.impl;

import com.liferay.dynamic.data.mapping.storage.BaseFieldRenderer;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Manuel de la Peña
 */
public class DateFieldRenderer extends BaseFieldRenderer {

	@Override
	protected String doRender(Field field, Locale locale) throws Exception {
		List<String> values = new ArrayList<>();

		for (Serializable value : field.getValues(locale)) {
			if (Validator.isNull(value)) {
				continue;
			}

			values.add(format(value, locale));
		}

		return StringUtil.merge(values, StringPool.COMMA_AND_SPACE);
	}

	@Override
	protected String doRender(Field field, Locale locale, int valueIndex) {
		Serializable value = field.getValue(locale, valueIndex);

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return format(value, locale);
	}

	private String format(Serializable value, Locale locale) {
		try {
			return DateUtil.formatDate("yyyy-MM-dd", value.toString(), locale);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return LanguageUtil.format(
				locale, "is-temporarily-unavailable", "content");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DateFieldRenderer.class);

}