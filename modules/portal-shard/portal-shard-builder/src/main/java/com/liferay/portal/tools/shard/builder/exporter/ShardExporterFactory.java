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

package com.liferay.portal.tools.shard.builder.exporter;

import com.liferay.portal.tools.shard.builder.db.mysql.MySQLProvider;
import com.liferay.portal.tools.shard.builder.db.postgresql.PostgreSQLProvider;
import com.liferay.portal.tools.shard.builder.exporter.exception.DBProviderNotAvailableException;

import java.util.Properties;

/**
 * @author Manuel de la Peña
 */
public class ShardExporterFactory {

	public static ShardExporter getShardExporter(Properties properties) {
		String dataSourceClassName = properties.getProperty(
			"dataSourceClassName", "");

		if (dataSourceClassName.equals(
				"com.mysql.jdbc.jdbc2.optional.MysqlDataSource")) {

			return new MySQLProvider(properties);
		}
		else if (dataSourceClassName.equals(
					"com.impossibl.postgres.jdbc.PGDataSource") ||
				 dataSourceClassName.equals(
					 "org.postgresql.ds.PGSimpleDataSource")) {

			return new PostgreSQLProvider(properties);
		}

		throw new DBProviderNotAvailableException();
	}

}