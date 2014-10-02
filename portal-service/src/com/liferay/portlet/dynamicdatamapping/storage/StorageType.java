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

package com.liferay.portlet.dynamicdatamapping.storage;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public enum StorageType {

	EXPANDO("expando"), JSON("json"), XML("xml");

	public static StorageType parse(String value) {
		if (EXPANDO.getValue().equals(value)) {
			return EXPANDO;
		}
		else if (JSON.getValue().equals(value)) {
			return JSON;
		}
		else if (XML.getValue().equals(value)) {
			return XML;
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

	private StorageType(String value) {
		_value = value;
	}

	private final String _value;

}