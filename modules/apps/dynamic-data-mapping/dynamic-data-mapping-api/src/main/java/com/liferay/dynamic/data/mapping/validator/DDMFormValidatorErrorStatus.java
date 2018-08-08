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
public enum DDMFormValidatorErrorStatus {

	MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION,
	MUST_SET_AVAILABLE_LOCALES_EXCEPTION,
	MUST_SET_DEFAULT_LOCALE_AS_AVAILABLE_LOCALE_EXCEPTION,
	MUST_SET_DEFAULT_LOCALE_EXCEPTION, MUST_SET_FIELD_TYPE_EXCEPTION,
	MUST_SET_FIELDS_FOR_FORM_EXCEPTION, MUST_SET_OPTIONS_FOR_FIELD_EXCEPTION,
	MUST_SET_VALID_AVAILABLE_LOCALES_FOR_PROPERTY_EXCEPTION,
	MUST_SET_VALID_CHARACTERS_FOR_FIELD_NAME_EXCEPTION,
	MUST_SET_VALID_CHARACTERS_FOR_FIELD_TYPE_EXCEPTION,
	MUST_SET_VALID_DEFAULT_LOCALE_FOR_PROPERTY_EXCEPTION,
	MUST_SET_VALID_FORM_FIELD_EXPRESSION_EXCEPTION,
	MUST_SET_VALID_FORM_RULE_EXPRESSION_EXCEPTION,
	MUST_SET_VALID_INDEX_TYPE_EXCEPTION,
	MUST_SET_VALID_VALIDATION_EXPRESSION_EXCEPTION,
	MUST_SET_VALID_VISIBILITY_EXPRESSION_EXCEPTION

}