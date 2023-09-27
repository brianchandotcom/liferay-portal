/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Rafael Praxedes
 */
@DDMForm
@ProviderType
public interface DDMDataProviderParameterSettings {

	@DDMFormField(
		label = "%inputs",
		properties = "nestedFieldNames=inputParameterLabel,inputParameterName,inputParameterType,inputParameterRequired"
	)
	public DDMDataProviderInputParametersSettings[] inputParameters();

	@DDMFormField(
		label = "%outputs",
		properties = "nestedFieldNames=outputParameterId,outputParameterName,outputParameterPath,outputParameterType"
	)
	public DDMDataProviderOutputParametersSettings[] outputParameters();

}