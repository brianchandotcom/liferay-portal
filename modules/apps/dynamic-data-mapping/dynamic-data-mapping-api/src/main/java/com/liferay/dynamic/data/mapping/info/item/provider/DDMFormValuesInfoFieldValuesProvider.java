/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.info.item.provider;

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.localized.InfoLocalizedValue;

import java.util.List;

/**
 * @author Alejandro Tardín
 */
public interface DDMFormValuesInfoFieldValuesProvider<T> {

	public List<InfoFieldValue<InfoLocalizedValue<Object>>> getInfoFieldValues(
		T t, DDMFormValues ddmFormValues);

}