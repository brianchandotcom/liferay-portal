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

package com.liferay.portal.kernel.bi.reporting;

/**
 * @author Gavin Wan
 */
public enum ReportDataSourceType {

	CSV("csv"), EMPTY("empty"), JDBC("jdbc"), PORTAL("portal"), XLS("xls"),
	XML("xml");

	public static ReportDataSourceType parse(String value) {
		if (CSV.getValue().equals(value)) {
			return CSV;
		}
		else if (EMPTY.getValue().equals(value)) {
			return EMPTY;
		}
		else if (JDBC.getValue().equals(value)) {
			return JDBC;
		}
		else if (PORTAL.getValue().equals(value)) {
			return PORTAL;
		}
		else if (XLS.getValue().equals(value)) {
			return XLS;
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

	private ReportDataSourceType(String value) {
		_value = value;
	}

	private final String _value;

}