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
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.math.BigDecimal;

import javax.validation.ValidationException;

/**
 * @author Carlos Correa
 */
public class DefaultPropertyValidator implements PropertyValidator {

	public DefaultPropertyValidator(
		PropertyDefinition.PropertyType propertyType) {

		this.propertyType = propertyType;
	}

	@Override
	public void validate(String propertyName, Object propertyValue) {
		boolean valid = false;

		if (propertyType == PropertyDefinition.PropertyType.BIG_DECIMAL) {
			if (propertyValue instanceof BigDecimal) {
				valid = true;
			}
		}
		else if (propertyType == PropertyDefinition.PropertyType.BOOLEAN) {
			if (propertyValue instanceof Boolean) {
				valid = true;
			}
		}
		else if (propertyType == PropertyDefinition.PropertyType.DECIMAL) {
			if (propertyValue instanceof Float) {
				valid = true;
			}
		}
		else if (propertyType == PropertyDefinition.PropertyType.DOUBLE) {
			if (propertyValue instanceof Double) {
				valid = true;
			}
		}
		else if (propertyType == PropertyDefinition.PropertyType.INTEGER) {
			if (propertyValue instanceof Integer) {
				valid = true;
			}
		}
		else if (propertyType == PropertyDefinition.PropertyType.LONG) {
			if (propertyValue instanceof Long) {
				valid = true;
			}
		}
		else if (propertyType == PropertyDefinition.PropertyType.TEXT) {
			if (propertyValue instanceof String) {
				valid = true;
			}
		}

		if (!valid) {
			throw new ValidationException(
				StringBundler.concat(
					"The property ", propertyName,
					" is not valid, expected type: ", propertyType));
		}
	}

	protected PropertyDefinition.PropertyType propertyType;

}