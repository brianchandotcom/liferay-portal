/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author James Lefeu
 * @auther Peter Shin
 */
public class SBIndexFactoryUtil {

	public static SBIndex create(
		String tableName, List<String> columnNames, boolean unique) {

		String indexSpec = getIndexSpec(tableName, columnNames);

		String indexName = getIndexName(indexSpec);

		StringBundler sb = new StringBundler(5);

		if (!unique) {
			sb.append("create ");
		}
		else {
			sb.append("create unique ");
		}

		sb.append("index ");
		sb.append(indexName);
		sb.append(" on ");
		sb.append(indexSpec);

		String sql = sb.toString();

		return new SBIndex(indexName, tableName, indexSpec, sql, unique);
	}

	protected static String getIndexName(String indexSpec) {
		String indexHash = StringUtil.toHexString(
			indexSpec.hashCode()).toUpperCase();

		return _INDEX_PREFIX.concat(indexHash);
	}

	protected static String getIndexSpec(
		String table, List<String> columnNames) {

		StringBundler sb = new StringBundler(6);

		sb.append(table);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);

		String s = StringPool.BLANK;

		if ((columnNames != null) && !columnNames.isEmpty()) {
			s = StringUtil.merge(columnNames, StringPool.COMMA_AND_SPACE);
		}

		sb.append(s);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	private static final String _INDEX_PREFIX = "IX_";

}