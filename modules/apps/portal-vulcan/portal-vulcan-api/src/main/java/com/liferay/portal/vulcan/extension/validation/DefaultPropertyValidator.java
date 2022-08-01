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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.extension.PropertyDefinition;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Map;
import java.util.Set;

import javax.validation.ValidationException;

/**
 * @author Carlos Correa
 */
public class DefaultPropertyValidator implements PropertyValidator {

	@Override
	public void validate(
		PropertyDefinition propertyDefinition, Object propertyValue) {

		boolean valid = false;

		Set<Class<?>> classes = propertyDefinition.getPropertyClasses();

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
					if (_log.isDebugEnabled()) {
						_log.debug(parseException);
					}
				}
			}
		}
		else if (propertyType ==
					PropertyDefinition.PropertyType.MULTIPLE_ELEMENT) {

			Class<?> propertyValueClass = propertyValue.getClass();

			if ((classes != null) && propertyValueClass.isArray()) {
				valid = true;

				for (Object object : (Object[])propertyValue) {
					if (!_isReadable(classes, object)) {
						valid = false;

						break;
					}
				}
			}
		}
		else if (propertyType ==
					PropertyDefinition.PropertyType.SINGLE_ELEMENT) {

			if ((classes != null) && (propertyValue instanceof Map) &&
				_isReadable(classes, propertyValue)) {

				valid = true;
			}
		}
		else if (classes != null) {
			for (Class<?> clazz : classes) {
				if (clazz.isInstance(propertyValue)) {
					valid = true;

					break;
				}
			}
		}

		if (!valid) {
			throw new ValidationException(
				StringBundler.concat(
					"The property name \"",
					propertyDefinition.getPropertyName(),
					"\" is invalid for property type ", propertyType));
		}
	}

	private boolean _isReadable(Set<Class<?>> classes, Object object) {
		for (Class<?> clazz : classes) {
			if (ObjectMapperUtil.readValue(clazz, object) != null) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultPropertyValidator.class);

}