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