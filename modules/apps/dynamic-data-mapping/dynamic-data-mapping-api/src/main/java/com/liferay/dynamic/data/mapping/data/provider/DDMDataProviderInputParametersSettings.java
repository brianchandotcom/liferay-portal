/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@DDMFormField(
		label = "%required", type = "checkbox", visibilityExpression = "FALSE"
	)
	public boolean inputParameterRequired();

	@DDMFormField(
		label = "%type", optionLabels = {"%text", "%number"},
		optionValues = {"text", "number"}, type = "select"
	)
	public String inputParameterType();

}