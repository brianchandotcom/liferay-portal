/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.render;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;

import java.util.List;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public interface DDMFormFieldValueRenderer {

	public String getSupportedDDMFormFieldType();

	public String render(DDMFormFieldValue ddmFormFieldValues, Locale locale);

	public String render(
		List<DDMFormFieldValue> ddmFormFieldValue, Locale locale);

}