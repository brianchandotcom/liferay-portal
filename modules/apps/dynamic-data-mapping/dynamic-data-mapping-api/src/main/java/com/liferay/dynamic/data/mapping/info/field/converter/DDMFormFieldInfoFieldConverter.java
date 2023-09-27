/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.info.field.converter;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.info.field.InfoField;

/**
 * @author Alejandro Tardín
 */
@ProviderType
public interface DDMFormFieldInfoFieldConverter {

	public InfoField convert(DDMFormField ddmFormField);

}