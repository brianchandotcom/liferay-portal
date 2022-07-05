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

package com.liferay.portal.vulcan.extension;

import com.liferay.portal.vulcan.extension.validation.DefaultPropertyValidator;
import com.liferay.portal.vulcan.extension.validation.PropertyValidator;

/**
 * @author Carlos Correa
 */
public class PropertyDefinition {

	public PropertyDefinition(
		String propertyName, PropertyType propertyType, boolean required) {

		_propertyName = propertyName;
		_propertyType = propertyType;
		_required = required;

		_propertyValidator = new DefaultPropertyValidator(propertyType);
	}

	public PropertyDefinition(
		String propertyName, PropertyType propertyType,
		PropertyValidator propertyValidator, boolean required) {

		_propertyName = propertyName;
		_propertyType = propertyType;
		_propertyValidator = propertyValidator;
		_required = required;
	}

	public String getPropertyName() {
		return _propertyName;
	}

	public PropertyType getPropertyType() {
		return _propertyType;
	}

	public PropertyValidator getPropertyValidator() {
		return _propertyValidator;
	}

	public boolean isRequired() {
		return _required;
	}

	public enum PropertyType {

		BIG_DECIMAL, BOOLEAN, DECIMAL, DOUBLE, INTEGER, LONG, TEXT

	}

	private final String _propertyName;
	private final PropertyType _propertyType;
	private final PropertyValidator _propertyValidator;
	private final boolean _required;

}