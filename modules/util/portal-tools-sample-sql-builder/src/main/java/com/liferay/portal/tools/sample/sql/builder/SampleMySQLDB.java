/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.portal.dao.db.MySQLDB;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

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
	public String buildSQL(String template) throws IOException {
		if (template.contains("create table") || template.contains(" on ")) {
			return super.buildSQL(template);
		}

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