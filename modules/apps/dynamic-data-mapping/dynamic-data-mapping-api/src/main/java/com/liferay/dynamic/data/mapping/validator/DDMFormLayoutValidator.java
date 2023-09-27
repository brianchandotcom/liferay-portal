/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.validator;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;

/**
 * @author Pablo Carvalho
 */
public interface DDMFormLayoutValidator {

	public void validate(DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException, DDMFormValidationException;

}