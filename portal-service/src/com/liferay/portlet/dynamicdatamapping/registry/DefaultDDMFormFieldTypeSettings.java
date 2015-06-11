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

package com.liferay.portlet.dynamicdatamapping.registry;

import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.registry.annotations.DDMForm;
import com.liferay.portlet.dynamicdatamapping.registry.annotations.DDMFormField;

/**
 * @author Marcellus Tavares
 */
@DDMForm
public interface DefaultDDMFormFieldTypeSettings
	extends DDMFormFieldTypeSettings {

	@DDMFormField
	public String fieldNamespace();

	@DDMFormField
	public String indexType();

	@DDMFormField(type = "text")
	public LocalizedValue label();

	@DDMFormField
	public boolean localizable();

	@DDMFormField(type = "text")
	public LocalizedValue predefinedValue();

	@DDMFormField
	public boolean readOnly();

	@DDMFormField
	public boolean repeatable();

	@DDMFormField
	public boolean required();

	@DDMFormField
	public boolean showLabel();

	@DDMFormField(type = "text")
	public LocalizedValue tip();

	@DDMFormField
	public String visibilityExpression();

}