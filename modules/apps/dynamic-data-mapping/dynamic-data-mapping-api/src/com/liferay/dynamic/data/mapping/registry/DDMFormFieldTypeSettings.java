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

import com.liferay.dynamic.data.mapping.registry.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.registry.annotations.DDMFormField;

/**
 * @author Marcellus Tavares
 */
@DDMForm(localization = "content/Language")
public interface DDMFormFieldTypeSettings {

	@DDMFormField(required = true, visibilityExpression = "false")
	public String dataType();

	@DDMFormField(
		label = "%name",
		properties = {"setting.category=basic", "setting.weight=3"},
		required = true
	)
	public String name();

	@DDMFormField(required = true, visibilityExpression = "false")
	public String type();

}