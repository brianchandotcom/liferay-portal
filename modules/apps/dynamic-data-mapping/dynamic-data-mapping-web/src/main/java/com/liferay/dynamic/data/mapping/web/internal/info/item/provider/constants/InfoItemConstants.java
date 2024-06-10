/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.info.item.provider.constants;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

/**
 * @author Víctor Galán
 */
public class InfoItemConstants {

	public static final String[] SELECTABLE_DDM_STRUCTURE_FIELDS = {
		DDMFormFieldTypeConstants.CHECKBOX,
		DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE,
		DDMFormFieldTypeConstants.DATE, DDMFormFieldTypeConstants.DATE_TIME,
		DDMFormFieldTypeConstants.LINK_TO_LAYOUT,
		DDMFormFieldTypeConstants.NUMERIC, DDMFormFieldTypeConstants.IMAGE,
		DDMFormFieldTypeConstants.TEXT, DDMFormFieldTypeConstants.RADIO,
		DDMFormFieldTypeConstants.RICH_TEXT, DDMFormFieldTypeConstants.SELECT
	};

}