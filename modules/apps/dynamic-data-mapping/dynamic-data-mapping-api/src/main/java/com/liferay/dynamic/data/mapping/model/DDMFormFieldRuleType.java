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

package com.liferay.dynamic.data.mapping.model;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author     Leonardo Barros
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public enum DDMFormFieldRuleType implements Serializable {

	DATA_PROVIDER("DATA_PROVIDER"), READ_ONLY("READ_ONLY"),
	VALIDATION("VALIDATION"), VALUE("VALUE"), VISIBILITY("VISIBILITY");

	public static DDMFormFieldRuleType parse(String value) {
		if (Objects.equals(DATA_PROVIDER.getValue(), value)) {
			return DATA_PROVIDER;
		}
		else if (Objects.equals(READ_ONLY.getValue(), value)) {
			return READ_ONLY;
		}
		else if (Objects.equals(VALUE.getValue(), value)) {
			return VALUE;
		}
		else if (Objects.equals(VALIDATION.getValue(), value)) {
			return VALIDATION;
		}
		else if (Objects.equals(VISIBILITY.getValue(), value)) {
			return VISIBILITY;
		}
		else {
			throw new IllegalArgumentException("Invalid value " + value);
		}
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private DDMFormFieldRuleType(String value) {
		_value = value;
	}

	private final String _value;

}