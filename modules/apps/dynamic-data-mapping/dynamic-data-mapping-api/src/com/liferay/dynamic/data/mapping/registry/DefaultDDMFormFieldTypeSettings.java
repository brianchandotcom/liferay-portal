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
		type = "select"
	)
	public String indexType();

	@DDMFormField(label = "%label", type = "text")
	public LocalizedValue label();

	@DDMFormField(label = "%localizable")
	public boolean localizable();

	@DDMFormField(label = "%predefined-value", type = "text")
	public LocalizedValue predefinedValue();

	@DDMFormField(label = "%read-only")
	public boolean readOnly();

	@DDMFormField(label = "%repeatable")
	public boolean repeatable();

	@DDMFormField(label = "%required")
	public boolean required();

	@DDMFormField(label = "%show-label")
	public boolean showLabel();

	@DDMFormField(label = "%tip", type = "text")
	public LocalizedValue tip();

	@DDMFormField(
		label = "%validation-expression", type = "validation",
		visibilityExpression = "true"
	)
	public String validationExpression();

	@DDMFormField(label = "%validation-message", visibilityExpression = "true")
	public String validationMessage();

	@DDMFormField(
		label = "%visibility-expression", visibilityExpression = "true"
	)

}