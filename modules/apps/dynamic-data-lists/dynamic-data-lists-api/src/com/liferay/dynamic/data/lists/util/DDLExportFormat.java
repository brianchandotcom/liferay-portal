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

package com.liferay.portlet.dynamicdatalists.util;

/**
 * @author Marcellus Tavares
 */
public enum DDLExportFormat {

	CSV("csv"), XML("xml");

	public static DDLExportFormat parse(String value) {
		if (CSV.getValue().equals(value)) {
			return CSV;
		}
		else if (XML.getValue().equals(value)) {
			return XML;
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private DDLExportFormat(String value) {
		_value = value;
	}

	private final String _value;

}