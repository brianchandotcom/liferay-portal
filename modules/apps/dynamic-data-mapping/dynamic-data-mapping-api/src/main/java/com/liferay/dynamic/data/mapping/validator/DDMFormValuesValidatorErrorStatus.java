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

/**
 * @author Leonardo Barros
 */
public enum DDMFormValuesValidatorErrorStatus {

	MUST_NOT_SET_VALUE_EXCEPTION, MUST_SET_VALID_AVAILABLE_LOCALES_EXCEPTION,
	MUST_SET_VALID_DEFAULT_LOCALE_EXCEPTION, MUST_SET_VALID_FIELD_EXCEPTION,
	MUST_SET_VALID_VALUE_EXCEPTION, MUST_SET_VALID_VALUES_SIZE_EXCEPTION,
	REQUIRED_VALUE_EXCEPTION

}