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
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Map;

import javax.validation.ValidationException;

/**
 * @author Carlos Correa
 */
public class DefaultPropertyValidator implements PropertyValidator {

	@Override
	public void validate(
		PropertyDefinition propertyDefinition, Object propertyValue) {

		boolean valid = false;

		Class<?> clazz = propertyDefinition.getPropertyClass();

		PropertyDefinition.PropertyType propertyType =
			propertyDefinition.getPropertyType();

		if (propertyType == PropertyDefinition.PropertyType.DATE_TIME) {
			if (propertyValue instanceof String) {
				DateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");

				try {
					dateFormat.parse((String)propertyValue);
					valid = true;
				}
				catch (ParseException parseException) {
				}
			}
		}
		else if (propertyType ==
					PropertyDefinition.PropertyType.MULTIPLE_ELEMENT) {

			Class<?> propertyValueClass = propertyValue.getClass();

			if ((clazz != null) && propertyValueClass.isArray()) {
				valid = true;

				for (Object object : (Object[])propertyValue) {
					if (ObjectMapperUtil.readValue(clazz, object) == null) {
						valid = false;

						break;
					}
				}
			}
		}
		else if (propertyType ==
					PropertyDefinition.PropertyType.SINGLE_ELEMENT) {

			if ((clazz != null) && (propertyValue instanceof Map) &&
				(ObjectMapperUtil.readValue(clazz, propertyValue) != null)) {

				valid = true;
			}
		}
		else if ((clazz != null) && clazz.isInstance(propertyValue)) {
			valid = true;
		}

		if (!valid) {
			throw new ValidationException(
				StringBundler.concat(
					"The property name \"",
					propertyDefinition.getPropertyName(),
					"\" is invalid for property type ", propertyType));
		}
	}

}