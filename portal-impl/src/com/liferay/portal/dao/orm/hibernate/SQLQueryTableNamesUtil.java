/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class SQLQueryTableNamesUtil {

	public static String[] getTableNames(String sql) {
		String[] tableNames = _portalCache.get(sql);

		if (tableNames != null) {
			return tableNames;
		}

		String lowerCaseSQL = StringUtil.toLowerCase(sql);

		Set<String> tableNameSet = new HashSet<String>();

		// Find table name from "from" clause

		int index = 0;

		while ((index = lowerCaseSQL.indexOf(" from ", index)) != -1) {
			index += 6;

			int[] indexes = findWord(lowerCaseSQL, index);

			if (indexes != null) {
				tableNameSet.add(sql.substring(indexes[0], indexes[1]));
			}
		}

		// Find table name from "join" clause

		index = 0;

		while ((index = lowerCaseSQL.indexOf(" join ", index)) != -1) {
			index += 6;

			int[] indexes = findWord(lowerCaseSQL, index);

			if (indexes != null) {
				tableNameSet.add(sql.substring(indexes[0], indexes[1]));
			}
		}

		tableNames = tableNameSet.toArray(new String[tableNameSet.size()]);

		_portalCache.put(sql, tableNames);

		return tableNames;
	}

	private static int[] findWord(String sql, int index) {
		int start = -1;
		int end = sql.length();

		for (int i = index; i < sql.length(); i++) {
			char c = sql.charAt(i);

			if (c == CharPool.OPEN_PARENTHESIS) {

				// Subquery in clause, no need to parse table name.

				break;
			}
			else if ((c == CharPool.SPACE) ||
					 (c == CharPool.CLOSE_PARENTHESIS)) {

				if (start != -1) {
					end = i;

					break;
				}
			}
			else if (start == -1) {
				start = i;
			}
		}

		if (start == -1) {
			return null;
		}

		return new int[] {start, end};
	}

	private static PortalCache<String, String[]> _portalCache =
		SingleVMPoolUtil.getCache(SQLQueryTableNamesUtil.class.getName());

}