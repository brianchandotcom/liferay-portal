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

package com.liferay.portal.tools.data.partitioning.sql.builder.db.mysql;

import com.liferay.portal.tools.data.partitioning.sql.builder.internal.algorithm.BaseDBProvider;

import java.util.Properties;

/**
 * @author Manuel de la Peña
 */
public class MySQLProvider extends BaseDBProvider {

	public MySQLProvider(Properties properties) {
		super(properties);
	}

	@Override
	public String getControlTableNamesSQL(String schemaName) {
		StringBuilder sb = new StringBuilder();

		sb.append("select c1.");
		sb.append(getTableNameFieldName());
		sb.append(" from information_schema.columns c1 where ");
		sb.append("c1.table_schema = '");
		sb.append(schemaName);
		sb.append("' and c1.");
		sb.append(getTableNameFieldName());
		sb.append(" not in (");
		sb.append(getPartitionedTableNamesSQL(schemaName));
		sb.append(") group by c1.");
		sb.append(getTableNameFieldName());
		sb.append(" order by c1.");
		sb.append(getTableNameFieldName());

		return sb.toString();
	}

	@Override
	public String getDateTimeFormat() {
		return "yyyy-MM-dd HH:mm:ss";
	}

	@Override
	public int getFetchSize() {
		return Integer.MIN_VALUE;
	}

	@Override
	public String getPartitionedTableNamesSQL(String schemaName) {
		StringBuilder sb = new StringBuilder();

		sb.append("select c2.");
		sb.append(getTableNameFieldName());
		sb.append(" from information_schema.columns c2 where ");
		sb.append("c2.table_schema = '");
		sb.append(schemaName);
		sb.append("' and c2.column_name = 'companyId' group by c2.");
		sb.append(getTableNameFieldName());
		sb.append(" order by c2.");
		sb.append(getTableNameFieldName());

		return sb.toString();
	}

	@Override
	public String getTableNameFieldName() {
		return "table_name";
	}

}