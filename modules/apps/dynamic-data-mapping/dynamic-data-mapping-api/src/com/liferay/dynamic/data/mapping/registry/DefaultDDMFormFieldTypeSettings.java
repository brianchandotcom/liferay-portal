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

package com.liferay.dynamic.data.mapping.registry;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.registry.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.registry.annotations.DDMFormField;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Marcellus Tavares
 */
@DDMForm
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
		properties = {"setting.category=advanced", "setting.weight=0"},
		type = "select"
	)
	public String indexType();

	@DDMFormField(
		label = "%label",
		properties = {"setting.category=basic", "setting.weight=4"},
		type = "text"
	)
	public LocalizedValue label();

	@DDMFormField(
		label = "%localizable",
		properties = {"setting.category=advanced", "setting.weight=0"}
	)
	public boolean localizable();

	@DDMFormField(
		label = "%predefined-value",
		properties = {"setting.category=advanced", "setting.weight=0"},
		type = "text"
	)
	public LocalizedValue predefinedValue();

	@DDMFormField(
		label = "%read-only",
		properties = {"setting.category=advanced", "setting.weight=0"}
	)
	public boolean readOnly();

	@DDMFormField(
		label = "%repeatable",
		properties = {"setting.category=advanced", "setting.weight=0"}
	)
	public boolean repeatable();

	@DDMFormField(
		label = "%required",
		properties = {"setting.category=basic", "setting.weight=1"}
	)
	public boolean required();

	@DDMFormField(
		label = "%show-label",
		properties = {"setting.category=advanced", "setting.weight=0"}
	)
	public boolean showLabel();

	@DDMFormField(
		label = "%tip",
		properties = {"setting.category=basic", "setting.weight=2"},
		type = "text"
	)
	public LocalizedValue tip();

	@DDMFormField(
		dataType = "ddm-validation",
		label = "%validation",
		properties = {"setting.category=advanced", "setting.weight=0"},
		type = "validation"
	)
	public DDMFormFieldValidation validation();

	@DDMFormField(
		label = "%validation-message",
		properties = {"setting.category=advanced", "setting.weight=0"}
	)
	public String validationMessage();

	@DDMFormField(
		label = "%visibility",
		properties = {"setting.category=advanced", "setting.weight=0"}
	)
	public String visibilityExpression();

}