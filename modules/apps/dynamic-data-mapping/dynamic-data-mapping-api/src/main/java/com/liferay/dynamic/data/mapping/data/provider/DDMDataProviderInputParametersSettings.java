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

package com.liferay.dynamic.data.mapping.data.provider;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Leonardo Barros
 */
@DDMForm
@ProviderType
public interface DDMDataProviderInputParametersSettings {

	@DDMFormField(label = "%label", properties = "placeholder=%enter-a-label")
	public String inputParameterLabel();

	@DDMFormField(
		label = "%parameter", properties = "placeholder=%enter-the-parameter"
	)
	public String inputParameterName();

	@DDMFormField(label = "%required", type = "checkbox")
	public boolean inputParameterRequired();

	@DDMFormField(
		label = "%type", optionLabels = {"%text", "%number"},
		optionValues = {"text", "number"}, type = "select"
	)
	public String inputParameterType();

}