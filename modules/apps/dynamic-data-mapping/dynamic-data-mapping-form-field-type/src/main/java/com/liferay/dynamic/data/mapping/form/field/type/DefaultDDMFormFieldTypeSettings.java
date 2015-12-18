/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Marcellus Tavares
 */
@DDMForm
@DDMFormLayout( {
	@DDMFormLayoutPage(title = "basic", value = {
		@DDMFormLayoutRow(
			{
				@DDMFormLayoutColumn(
					size = 12,
					value = {"label", "predefinedValue", "required", "tip"}
				)
			}
		)
	}),
	@DDMFormLayoutPage(title = "advanced", value = {
		@DDMFormLayoutRow(
			{
				@DDMFormLayoutColumn(
					size = 12,
					value = {
						"repeatable", "showLabel", "validation",
						"visibilityExpression"
					}
				)
			}
		)
	})
})
public interface DefaultDDMFormFieldTypeSettings
	extends DDMFormFieldTypeSettings {

	@DDMFormField(visibilityExpression = "false")
	public String fieldNamespace();

	@DDMFormField(
		label = "%indexable",
		optionLabels = {
			"%not-indexable", "%indexable-keyword", "%indexable-text"
		},
		optionValues = {StringPool.BLANK, "keyword", "text"},
		type = "select", visibilityExpression = "false"
	)
	public String indexType();

	@DDMFormField(
		label = "%question", properties = {"placeholder=%type-your-question"},
		required = true, tip = "%type-what-you-want-to-ask", type = "key-value"
	)
	public LocalizedValue label();

	@DDMFormField(label = "%localizable", visibilityExpression = "false")
	public boolean localizable();

	@DDMFormField(
		label = "%predefined-value", tip = "%set-the-default-value-of-a-field",
		type = "text"
	)
	public LocalizedValue predefinedValue();

	@DDMFormField(label = "%read-only", visibilityExpression = "false")
	public boolean readOnly();

	@DDMFormField(label = "%repeatable", properties = {"showAsSwitcher=true"})
	public boolean repeatable();

	@DDMFormField(label = "%required", properties = {"showAsSwitcher=true"})
	public boolean required();

	@DDMFormField(label = "%show-label", properties = {"showAsSwitcher=true"})
	public boolean showLabel();

	@DDMFormField(
		label = "%help-text",
		properties = {
			"placeholder=%add-text-to-help-users-better-understand-what-you-want"
		},
		tip = "%type-a-short-comment-to-help-users-understand-the-question",
		type = "text"
	)
	public LocalizedValue tip();

	@DDMFormField(
		dataType = "ddm-validation", label = "%validation", type = "validation"
	)
	public DDMFormFieldValidation validation();

	@DDMFormField(label = "%visibility")
	public String visibilityExpression();

}