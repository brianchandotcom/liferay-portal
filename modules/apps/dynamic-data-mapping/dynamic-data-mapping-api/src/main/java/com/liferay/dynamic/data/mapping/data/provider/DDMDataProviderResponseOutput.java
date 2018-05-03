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

package com.liferay.dynamic.data.mapping.data.provider;

/**
 * @author Marcellus Tavares
 */
public class DDMDataProviderResponseOutput {

	public static DDMDataProviderResponseOutput of(
		String name, String type, Object value) {

		return new DDMDataProviderResponseOutput(name, type, value);
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public <T> T getValue(Class<T> valueType) {
		return valueType.cast(_value);
	}

	private DDMDataProviderResponseOutput(
		String name, String type, Object value) {

		_name = name;
		_type = type;
		_value = value;
	}

	private final String _name;
	private final String _type;
	private final Object _value;

}