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

package com.liferay.dynamic.data.mapping.validator;

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

/**
 * @author Marcellus Tavares
 */
public interface DDMFormValuesValidator {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by
	 * {@link DDMFormValuesValidator#validate(DDMFormValuesValidatorValidateRequest)}
	 */
	@Deprecated
	public default void validate(DDMFormValues ddmFormValues)
		throws DDMFormValuesValidationException {

		DDMFormValuesValidatorValidateRequest.Builder builder =
			DDMFormValuesValidatorValidateRequest.Builder.newBuilder(
				ddmFormValues);

		validate(builder.build());
	}

	public void validate(
			DDMFormValuesValidatorValidateRequest validateFormValuesRequest)
		throws DDMFormValuesValidationException;

}