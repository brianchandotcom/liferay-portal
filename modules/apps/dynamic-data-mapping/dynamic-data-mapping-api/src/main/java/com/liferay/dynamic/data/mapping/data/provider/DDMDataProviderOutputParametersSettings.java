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
public interface DDMDataProviderOutputParametersSettings {

	@DDMFormField(properties = "random=true", visibilityExpression = "FALSE")
	public String outputParameterId();

	@DDMFormField(label = "%label", properties = "placeholder=%enter-a-label")
	public String outputParameterName();

	@DDMFormField(label = "%path", properties = "placeholder=%enter-the-path")
	public String outputParameterPath();

	@DDMFormField(
		label = "%type", optionLabels = {"%text", "%number", "%list"},
		optionValues = {"text", "number", "list"}, type = "select"
	)
	public String outputParameterType();

}