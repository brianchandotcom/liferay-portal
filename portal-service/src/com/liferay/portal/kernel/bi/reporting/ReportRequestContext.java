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

	private final Map<String, Serializable> _attributes =
		new HashMap<String, Serializable>();
	private final ReportDataSourceType _reportDataSourceType;

}