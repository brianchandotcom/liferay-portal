/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.petra.string.StringPool;

/**
 * @author Marcellus Tavares
 */
@DDMForm
@DDMFormLayout(
	{
		@DDMFormLayoutPage(
			title = "properties",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"dataType", "name", "fieldReference", "type"
							}
						)
					}
				)
			}
		)
	}
)
public interface DDMFormFieldTypeSettings {

	@DDMFormField(
		predefinedValue = "string", required = true,
		visibilityExpression = "FALSE"
	)
	public String dataType();

	@DDMFormField(
		label = "%field-reference",
		properties = {
			"normalizeField=true",
			"tooltip=%field-reference-serves-as-a-frienldy-identifier"
		}
	)
	public default String fieldReference() {
		return StringPool.BLANK;
	}

	@DDMFormField(
		label = "%field-name", properties = "normalizeField=true",
		required = true, visibilityExpression = "FALSE"
	)
	public String name();

	@DDMFormField(required = true, visibilityExpression = "FALSE")
	public String type();

}