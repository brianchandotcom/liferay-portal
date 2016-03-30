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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.dao.db.DBType;

/**
 * @author Shuyang Zhou
 */
public enum ExpectedDBType {

	DB2(DBType.DB2), HYPERSONIC(DBType.HYPERSONIC), MYSQL(DBType.MYSQL),
	NONE(null), ORACLE(DBType.ORACLE), POSTGRESQL(DBType.POSTGRESQL),
	SQLSERVER(DBType.SQLSERVER), SYBASE(DBType.SYBASE);

	public DBType getDBType() {
		return _dbType;
	}

	private ExpectedDBType(DBType dbType) {
		_dbType = dbType;
	}

	private final DBType _dbType;

}