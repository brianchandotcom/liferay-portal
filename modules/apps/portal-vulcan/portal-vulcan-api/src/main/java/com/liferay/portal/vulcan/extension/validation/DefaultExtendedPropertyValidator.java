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

package com.liferay.portal.vulcan.extension.validation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.extension.ExtendedPropertyDefinition;

import java.math.BigDecimal;

import javax.validation.ValidationException;

/**
 * @author Carlos Correa
 */
public class DefaultExtendedPropertyValidator
	implements ExtendedPropertyValidator {

	public DefaultExtendedPropertyValidator(
		ExtendedPropertyDefinition.FieldType fieldType) {

		this.fieldType = fieldType;
	}

	@Override
	public void validate(String fieldName, Object fieldValue) {
		boolean valid = false;

		if (fieldType == ExtendedPropertyDefinition.FieldType.BIG_DECIMAL) {
			if (fieldValue instanceof BigDecimal) {
				valid = true;
			}
		}
		else if (fieldType == ExtendedPropertyDefinition.FieldType.BOOLEAN) {
			if (fieldValue instanceof Boolean) {
				valid = true;
			}
		}
		else if (fieldType == ExtendedPropertyDefinition.FieldType.DECIMAL) {
			if (fieldValue instanceof Float) {
				valid = true;
			}
		}
		else if (fieldType == ExtendedPropertyDefinition.FieldType.DOUBLE) {
			if (fieldValue instanceof Double) {
				valid = true;
			}
		}
		else if (fieldType == ExtendedPropertyDefinition.FieldType.INTEGER) {
			if (fieldValue instanceof Integer) {
				valid = true;
			}
		}
		else if (fieldType == ExtendedPropertyDefinition.FieldType.LONG) {
			if (fieldValue instanceof Long) {
				valid = true;
			}
		}
		else if (fieldType == ExtendedPropertyDefinition.FieldType.TEXT) {
			if (fieldValue instanceof String) {
				valid = true;
			}
		}

		if (!valid) {
			throw new ValidationException(
				StringBundler.concat(
					"The field ", fieldName, " is not valid, expected type: ",
					fieldType));
		}
	}

	protected ExtendedPropertyDefinition.FieldType fieldType;

}