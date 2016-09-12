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

package com.liferay.portal.tools.data.partitioning.sql.builder.mysql.exporter;

import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.BaseDataPartitioningExporter;
import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.InsertSQLBuilder;
import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.context.ExportContext;
import com.liferay.portal.tools.data.partitioning.sql.builder.internal.serializer.DefaultFieldSerializer;

import java.text.SimpleDateFormat;

/**
 * @author Manuel de la Peña
 */
public class MySQLDataPartitioningExporter
	extends BaseDataPartitioningExporter {

	public MySQLDataPartitioningExporter() {
		super(
			new InsertSQLBuilder(
				new DefaultFieldSerializer(
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))));
	}

	@Override
	public String getControlTableNamesSQL(ExportContext exportContext) {
		StringBuilder sb = new StringBuilder();

		sb.append("select c1.");
		sb.append(getTableNameFieldName());
		sb.append(" from information_schema.columns c1 where ");
		sb.append("c1.table_schema = '");
		sb.append(exportContext.getSchemaName());
		sb.append("' and c1.");
		sb.append(getTableNameFieldName());
		sb.append(" not in (");
		sb.append(getPartitionedTableNamesSQL(exportContext));
		sb.append(") group by c1.");
		sb.append(getTableNameFieldName());
		sb.append(" order by c1.");
		sb.append(getTableNameFieldName());

		return sb.toString();
	}

	@Override
	public int getFetchSize() {
		return Integer.MIN_VALUE;
	}

	@Override
	public String getPartitionedTableNamesSQL(ExportContext exportContext) {
		StringBuilder sb = new StringBuilder();

		sb.append("select c2.");
		sb.append(getTableNameFieldName());
		sb.append(" from information_schema.columns c2 where ");
		sb.append("c2.table_schema = '");
		sb.append(exportContext.getSchemaName());
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