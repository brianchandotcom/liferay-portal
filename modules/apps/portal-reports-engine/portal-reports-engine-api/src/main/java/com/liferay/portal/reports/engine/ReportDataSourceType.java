/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine;

import java.util.Objects;

/**
 * @author Gavin Wan
 */
public enum ReportDataSourceType {

	CSV("csv"), EMPTY("empty"), JDBC("jdbc"), PORTAL("portal"), XLS("xls"),
	XML("xml");

	public static ReportDataSourceType parse(String value) {
		if (Objects.equals(CSV.getValue(), value)) {
			return CSV;
		}
		else if (Objects.equals(EMPTY.getValue(), value)) {
			return EMPTY;
		}
		else if (Objects.equals(JDBC.getValue(), value)) {
			return JDBC;
		}
		else if (Objects.equals(PORTAL.getValue(), value)) {
			return PORTAL;
		}
		else if (Objects.equals(XLS.getValue(), value)) {
			return XLS;
		}
		else if (Objects.equals(XML.getValue(), value)) {
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