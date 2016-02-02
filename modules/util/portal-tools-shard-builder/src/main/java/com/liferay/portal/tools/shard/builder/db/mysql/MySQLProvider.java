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

package com.liferay.portal.tools.shard.builder.db.mysql;

import com.liferay.portal.tools.shard.builder.internal.algorithm.BaseDBProvider;

import java.util.Properties;

/**
 * @author Manuel de la Peña
 */
public class MySQLProvider extends BaseDBProvider {

	public MySQLProvider(Properties dbProperties) {
		super(dbProperties);
	}

	@Override
	public String getControlTableNamesSQL(String schema) {
		String tableNameFieldName = getTableNameFieldName();

		return
			"select c." + tableNameFieldName + " from " +
				"information_schema.columns c where c.table_schema = '" +
				schema + "' and c." + tableNameFieldName + " not in (" +
				getPartitionedTableNamesSQL(schema)+ ") group by c." +
				tableNameFieldName + " order by c." + tableNameFieldName;
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
	public String getPartitionedTableNamesSQL(String schema) {
		String tableNameFieldName = getTableNameFieldName();

		return
			"select c1." + tableNameFieldName + " from " +
				"information_schema.columns c1 where c1.table_schema='" +
				schema + "' and c1.column_name='companyId' group by " +
				"c1." + tableNameFieldName + " order by c1." +
				tableNameFieldName;
	}

	@Override
	public String getTableNameFieldName() {
		return "table_name";
	}

}