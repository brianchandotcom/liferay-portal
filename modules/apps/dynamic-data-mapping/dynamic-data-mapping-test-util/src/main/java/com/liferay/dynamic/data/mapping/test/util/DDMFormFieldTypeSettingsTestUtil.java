/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTypeSettingsTestUtil {

	public static Class<? extends DDMFormFieldTypeSettings> getSettings() {
		return AllBasicPropertiesSettings.class;
	}

	@DDMForm
	@DDMFormLayout(
		{
			@DDMFormLayoutPage(
				{
					@DDMFormLayoutRow(
						{
							@DDMFormLayoutColumn(
								{
									"dataType", "fieldNamespace", "indexType",
									"label", "localizable", "name", "multiple",
									"options", "predefinedValue", "readOnly",
									"repeatable", "required", "showLabel",
									"tip", "type", "validation",
									"visibilityExpression"
								}
							)
						}
					)
				}
			)
		}
	)
	private interface AllBasicPropertiesSettings
		extends DefaultDDMFormFieldTypeSettings {

		@DDMFormField
		public boolean multiple();

		@DDMFormField(dataType = "ddm-options", type = "select")
		public DDMFormFieldOptions options();

	}

}