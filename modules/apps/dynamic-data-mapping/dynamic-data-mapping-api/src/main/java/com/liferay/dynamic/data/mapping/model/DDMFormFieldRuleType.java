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

/**
 * @author     Leonardo Barros
 * @deprecated As of 3.5.0, with no direct replacement
 */
@Deprecated
public enum DDMFormFieldRuleType implements Serializable {

	DATA_PROVIDER("DATA_PROVIDER"), READ_ONLY("READ_ONLY"),
	VALIDATION("VALIDATION"), VALUE("VALUE"), VISIBILITY("VISIBILITY");

	public static DDMFormFieldRuleType parse(String value) {
		if (DATA_PROVIDER.getValue().equals(value)) {
			return DATA_PROVIDER;
		}
		else if (READ_ONLY.getValue().equals(value)) {
			return READ_ONLY;
		}
		else if (VALUE.getValue().equals(value)) {
			return VALUE;
		}
		else if (VALIDATION.getValue().equals(value)) {
			return VALIDATION;
		}
		else if (VISIBILITY.getValue().equals(value)) {
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