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

package com.liferay.portal.dao.orm.hibernate;

import java.sql.Types;

import org.hibernate.LockMode;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author Shuyang Zhou
 */
public class SybaseASE157Dialect
	extends org.hibernate.dialect.SybaseASE157Dialect {

	public SybaseASE157Dialect() {
		registerColumnType(Types.TIMESTAMP, "bigdatetime");
		registerFunction(
			"datetime",
			new SQLFunctionTemplate(
				StandardBasicTypes.TIMESTAMP, "bigdatetime"));
	}

	@Override
	public String appendLockHint(LockMode mode, String tableName) {
		if (mode.greaterThan(LockMode.READ)) {
			return tableName + " holdlock";
		}

		return tableName;
	}

}