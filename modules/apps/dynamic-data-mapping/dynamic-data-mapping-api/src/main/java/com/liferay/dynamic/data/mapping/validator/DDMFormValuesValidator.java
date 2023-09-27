/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.validator;

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public interface DDMFormValuesValidator {

	public void validate(DDMFormValues ddmFormValues)
		throws DDMFormValuesValidationException;

	public void validate(DDMFormValues ddmFormValues, String timeZoneId)
		throws DDMFormValuesValidationException;

}