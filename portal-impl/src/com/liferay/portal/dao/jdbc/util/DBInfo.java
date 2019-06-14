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

package com.liferay.portal.dao.jdbc.util;

/**
 * @author Shuyang Zhou
 */
public class DBInfo {

	public DBInfo(
		String name, String driverName, int majorVersion, int minorVersion) {

		_name = name;
		_driverName = driverName;
		_majorVersion = majorVersion;
		_minorVersion = minorVersion;
	}

	public String getDriverName() {
		return _driverName;
	}

	public int getMajorVersion() {
		return _majorVersion;
	}

	public int getMinorVersion() {
		return _minorVersion;
	}

	public String getName() {
		return _name;
	}

	private final String _driverName;
	private final int _majorVersion;
	private final int _minorVersion;
	private final String _name;

}