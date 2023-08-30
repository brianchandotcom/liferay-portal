/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.constants;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;

/**
 * @author Lino Alves
 */
public class DDMConstants {

	public static final String AVAILABLE_FIELDS =
		"Liferay.FormBuilder.AVAILABLE_FIELDS.DDM_STRUCTURE";

	public static final String EXPRESSION_FUNCTION_FACTORY_NAME =
		"com.liferay.dynamic.data.mapping.expression." +
			"DDMExpressionFunctionFactory";

	public static final String RESOURCE_NAME =
		"com.liferay.dynamic.data.mapping";

	public static final String SERVICE_NAME =
		"com.liferay.dynamic.data.mapping";

	public static final String[] SUPPORTED_DDM_FORM_FIELD_TYPES = {
		DDMFormFieldType.CHECKBOX, DDMFormFieldType.CHECKBOX_MULTIPLE,
		DDMFormFieldType.COLOR, DDMFormFieldType.DATE, DDMFormFieldType.DECIMAL,
		DDMFormFieldType.DOCUMENT_LIBRARY, DDMFormFieldType.FIELDSET,
		DDMFormFieldType.GEOLOCATION, DDMFormFieldType.GRID,
		DDMFormFieldType.IMAGE, DDMFormFieldType.INTEGER,
		DDMFormFieldType.JOURNAL_ARTICLE, DDMFormFieldType.LINK_TO_PAGE,
		DDMFormFieldType.LOCALIZABLE_TEXT, DDMFormFieldType.NUMBER,
		DDMFormFieldType.NUMERIC, DDMFormFieldType.PARAGRAPH,
		DDMFormFieldType.PASSWORD, DDMFormFieldType.RADIO,
		DDMFormFieldType.SELECT, DDMFormFieldType.SEPARATOR,
		DDMFormFieldType.TEXT, DDMFormFieldType.TEXT_AREA,
		DDMFormFieldType.TEXT_HTML
	};

}