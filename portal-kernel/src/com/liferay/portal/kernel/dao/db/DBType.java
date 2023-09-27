/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.db;

/**
 * @author Shuyang Zhou
 */
public enum DBType {

	DB2("db2"), HYPERSONIC("hypersonic"), MARIADB("mariadb"), MYSQL("mysql"),
	ORACLE("oracle"), POSTGRESQL("postgresql"), SQLSERVER("sqlserver"),
	SYBASE("sybase");

	public String getName() {
		return _name;
	}

	@Override
	public String toString() {
		return _name;
	}

	private DBType(String name) {
		_name = name;
	}

	private final String _name;

}