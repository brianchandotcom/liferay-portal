/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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