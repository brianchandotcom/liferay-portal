/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		validationErrorMessage = "errorMessage",
		validationExpression = "expression",
		validationExpressionName = "expressionName",
		validationParameter = "parameter"
	)
	public void fieldWithAllValidationParameters();

	@DDMFormField(
		validationErrorMessage = "errorMessage",
		validationExpression = "expression"
	)
	public void fieldWithPartialValidationParameters();

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