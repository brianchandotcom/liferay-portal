/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public interface DDMFormFieldValueRenderer {

	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale);

	public default String render(
		String ddmFormFieldName, DDMFormFieldValue ddmFormFieldValue,
		Locale locale) {

		return StringPool.BLANK;
	}

}