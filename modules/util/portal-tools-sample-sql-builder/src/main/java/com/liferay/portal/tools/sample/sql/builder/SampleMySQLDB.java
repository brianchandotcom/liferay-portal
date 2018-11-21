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

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.portal.dao.db.MySQLDB;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * A simplified version of MySQLDB for sample SQL generation. This should not be
 * used for any other purposes.
 *
 * @author Shuyang Zhou
 */
public class SampleMySQLDB extends MySQLDB {

	public SampleMySQLDB(int majorVersion, int minorVersion) {
		super(majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) {
		return StringUtil.replace(template, _GENERIC_TEMPLATE, _MYSQL_TEMPLATE);
	}

	private static final String[] _GENERIC_TEMPLATE = {
		"TRUE", "FALSE", "'01/01/1970'", "CURRENT_TIMESTAMP",
		"COMMIT_TRANSACTION"
	};

	private static final String[] _MYSQL_TEMPLATE = {
		"1", "0", "'1970-01-01'", "now()", "commit"
	};

}