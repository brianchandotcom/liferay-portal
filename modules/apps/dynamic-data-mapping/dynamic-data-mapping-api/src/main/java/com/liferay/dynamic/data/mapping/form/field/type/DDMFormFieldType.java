/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.petra.string.StringPool;

/**
 * @author Marcellus Tavares
 */
public interface DDMFormFieldType {

	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings();

	public default String getModuleName() {
		return StringPool.BLANK;
	}

	public String getName();

	public default boolean isCustomDDMFormFieldType() {
		return false;
	}

}