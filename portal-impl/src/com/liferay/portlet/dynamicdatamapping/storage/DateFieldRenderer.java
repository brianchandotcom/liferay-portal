/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.Format;

import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Manuel de la Peña
 */
public class DateFieldRenderer extends BaseFieldRenderer {

	@Override
	protected String doRender(Field field, Locale locale) throws Exception {
		Format format = FastDateFormatFactoryUtil.getDate(locale);

		List<Serializable> values = field.getValues();

		StringBundler sb = new StringBundler(values.size() * 2);

		for (int i = 0; i < values.size(); i++) {
			String value = String.valueOf(values.get(i));

			if (Validator.isNull(value)) {
				continue;
			}

			sb.append(format.format(value));

			if ((i + 1) < values.size()) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

	@Override
	protected String doRender(Field field, Locale locale, int valueIndex) {
		String value = String.valueOf(field.getValue(valueIndex));

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		Format format = FastDateFormatFactoryUtil.getDate(locale);

		return format.format(value);
	}

}