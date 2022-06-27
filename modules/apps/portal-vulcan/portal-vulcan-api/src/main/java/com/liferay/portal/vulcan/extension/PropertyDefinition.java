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

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.extension.validation.DefaultPropertyValidator;
import com.liferay.portal.vulcan.extension.validation.PropertyValidator;

import java.math.BigDecimal;

import java.util.Map;

/**
 * @author Carlos Correa
 */
public class PropertyDefinition {

	public PropertyDefinition(
		Class<?> propertyClass, String propertyName, PropertyType propertyType,
		PropertyValidator propertyValidator, boolean required) {

		_propertyClass = propertyClass;
		_propertyName = propertyName;
		_propertyType = propertyType;
		_propertyValidator = propertyValidator;
		_required = required;
	}

	public PropertyDefinition(
		String propertyName, PropertyType propertyType, boolean required) {

		_propertyName = propertyName;
		_propertyType = propertyType;
		_required = required;

		_propertyClass = _propertyTypeClassMap.getOrDefault(propertyType, null);
		_propertyValidator = new DefaultPropertyValidator();
	}

	public PropertyDefinition(
		String propertyName, PropertyType propertyType,
		PropertyValidator propertyValidator, boolean required) {

		_propertyName = propertyName;
		_propertyType = propertyType;
		_propertyValidator = propertyValidator;
		_required = required;

		_propertyClass = _propertyTypeClassMap.getOrDefault(propertyType, null);
	}

	public Class<?> getPropertyClass() {
		return _propertyClass;
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

		BIG_DECIMAL, BOOLEAN, DATE_TIME, DECIMAL, DOUBLE, INTEGER, LONG,
		MULTIPLE_ELEMENT, SINGLE_ELEMENT, TEXT

	}

	private static final Map<PropertyType, Class<?>> _propertyTypeClassMap =
		HashMapBuilder.<PropertyType, Class<?>>put(
			PropertyType.BIG_DECIMAL, BigDecimal.class
		).<PropertyType, Class<?>>put(
			PropertyType.BOOLEAN, Boolean.class
		).<PropertyType, Class<?>>put(
			PropertyType.DECIMAL, Float.class
		).<PropertyType, Class<?>>put(
			PropertyType.DOUBLE, Double.class
		).<PropertyType, Class<?>>put(
			PropertyType.INTEGER, Integer.class
		).<PropertyType, Class<?>>put(
			PropertyType.LONG, Long.class
		).<PropertyType, Class<?>>put(
			PropertyType.TEXT, String.class
		).build();

	private final Class<?> _propertyClass;
	private final String _propertyName;
	private final PropertyType _propertyType;
	private final PropertyValidator _propertyValidator;
	private final boolean _required;

}