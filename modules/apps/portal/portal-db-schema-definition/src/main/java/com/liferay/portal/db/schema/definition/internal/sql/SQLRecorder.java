/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.sql;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Mariano Álvaro Sáiz
 */
public class SQLRecorder {

	public String getIndexesSQL() {
		return _indexesSB.toString();
	}

	public String getTablesSQL() {
		return _tablesSB.toString();
	}

	public void recordIndexesSQL(String sql) {
		if (sql != null) {
			_indexesSB.append(sql);
		}
	}

	public void recordSQL(String sql) {
		if (sql == null) {
			return;
		}

		sql += StringPool.NEW_LINE;

		String lowerCaseSQL = StringUtil.toLowerCase(sql);

		if (lowerCaseSQL.contains("create index") ||
			lowerCaseSQL.contains("create unique index")) {

			_indexesSB.append(sql);
		}
		else {
			_tablesSB.append(sql);
		}
	}

	public void recordTablesSQL(String sql) {
		if (sql != null) {
			_tablesSB.append(sql);
		}
	}

	private final StringBundler _indexesSB = new StringBundler();
	private final StringBundler _tablesSB = new StringBundler();

}