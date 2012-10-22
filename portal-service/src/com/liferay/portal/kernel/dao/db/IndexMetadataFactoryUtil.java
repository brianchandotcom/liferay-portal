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

/**
 * @author James Lefeu
 * @auther Peter Shin
 */
public class IndexMetadataFactoryUtil {

	public static IndexMetadata create(String tableName, String[] columnNames) {
		return create(tableName, columnNames, false);
	}

	public static IndexMetadata create(
		String tableName, String[] columnNames, boolean unique) {

		String specification = getSpecification(tableName, columnNames);

		String indexName = getIndexName(specification);

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
		sb.append(specification);

		String indexSQLCreate = sb.toString();

		sb.setIndex(0);

		sb.append("drop index ");
		sb.append(indexName);
		sb.append(" on ");
		sb.append(tableName);

		String indexSQLDrop = sb.toString();

		return new IndexMetadata(
			indexName, tableName, unique, specification, indexSQLCreate,
			indexSQLDrop);
	}

	protected static String getIndexName(String specification) {
		String specificationHash = StringUtil.toHexString(
			specification.hashCode());

		specificationHash = specificationHash.toUpperCase();

		return _INDEX_NAME_PREFIX.concat(specificationHash);
	}

	protected static String getSpecification(
		String tableName, String[] columnNames) {

		StringBundler sb = new StringBundler(6);

		sb.append(tableName);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);

		if ((columnNames != null) && (columnNames.length > 0)) {
			sb.append(
				StringUtil.merge(columnNames, StringPool.COMMA_AND_SPACE));
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	private static final String _INDEX_NAME_PREFIX = "IX_";

}