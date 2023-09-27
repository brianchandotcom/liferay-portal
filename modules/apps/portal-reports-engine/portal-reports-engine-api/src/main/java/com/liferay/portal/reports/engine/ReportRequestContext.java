/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class ReportRequestContext implements Serializable {

	public static final String DATA_SOURCE_BYTE_ARRAY = "dataSource.byteArray";

	public static final String DATA_SOURCE_CHARSET = "dataSource.charset";

	public static final String DATA_SOURCE_COLUMN_NAMES =
		"dataSource.columnNames";

	public static final String JDBC_DRIVER_CLASS = "jdbc.driverClassName";

	public static final String JDBC_PASSWORD = "jdbc.password";

	public static final String JDBC_URL = "jdbc.url";

	public static final String JDBC_USER_NAME = "jdbc.userName";

	public ReportRequestContext(ReportDataSourceType reportDataSourceType) {
		_reportDataSourceType = reportDataSourceType;
	}

	public Serializable getAttribute(String key) {
		return _attributes.get(key);
	}

	public ReportDataSourceType getReportDataSourceType() {
		return _reportDataSourceType;
	}

	public void setAttribute(String key, Serializable value) {
		_attributes.put(key, value);
	}

	private final Map<String, Serializable> _attributes = new HashMap<>();
	private final ReportDataSourceType _reportDataSourceType;

}