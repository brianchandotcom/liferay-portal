/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc.util;

/**
 * @author Shuyang Zhou
 */
public class DBInfo {

	public DBInfo(String name, int majorVersion, int minorVersion) {
		_name = name;
		_majorVersion = majorVersion;
		_minorVersion = minorVersion;
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

	private final int _majorVersion;
	private final int _minorVersion;
	private final String _name;

}