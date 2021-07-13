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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

/**
 * @author Rodrigo Paulino
 */
@DDMForm
public interface TestDDMForm {

	@DDMFormField(
		label = "%label",
		properties = {
			"autoFocus=true", "placeholder=%enter-a-field-label",
			"tooltip=%enter-a-descriptive-field-label-that-guides-users-to-enter-the-information-you-want",
			"visualProperty=true"
		},
		type = "text"
	)
	public LocalizedValue label();

	@DDMFormField(label = "%read-only", visibilityExpression = "FALSE")
	public boolean readOnly();

	@DDMFormField(dataType = "")
	public void voidField();

}