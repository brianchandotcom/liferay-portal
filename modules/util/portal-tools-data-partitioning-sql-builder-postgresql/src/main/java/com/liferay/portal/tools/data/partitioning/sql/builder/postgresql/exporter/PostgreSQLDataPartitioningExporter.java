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

package com.liferay.portal.tools.data.partitioning.sql.builder.postgresql.exporter;

import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.BaseDataPartitioningExporter;
import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.InsertSQLBuilder;
import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.context.ExportContext;
import com.liferay.portal.tools.data.partitioning.sql.builder.postgresql.exporter.serializer.PostgreSQLFieldSerializer;

/**
 * @author Manuel de la Peña
 */
public class PostgreSQLDataPartitioningExporter
	extends BaseDataPartitioningExporter {

	public PostgreSQLDataPartitioningExporter() {
		super(new InsertSQLBuilder(new PostgreSQLFieldSerializer()));
	}

	@Override
	public String getControlTableNamesSQL(ExportContext exportContext) {
		StringBuilder sb = new StringBuilder(11);

		sb.append("select c1.");
		sb.append(getTableNameFieldName());
		sb.append(
			" from information_schema.columns c1 where c1.table_catalog = '");
		sb.append(exportContext.getSchemaName());
		sb.append("' and c1.table_schema = 'public' and c1.");
		sb.append(getTableNameFieldName());
		sb.append(" not in (");
		sb.append(getPartitionedTableNamesSQL(exportContext));
		sb.append(") group by c1.");
		sb.append(getTableNameFieldName());
		sb.append(" order by c1.table_name");

		return sb.toString();
	}

	@Override
	public String getPartitionedTableNamesSQL(ExportContext exportContext) {
		StringBuilder sb = new StringBuilder(9);

		sb.append("select c2.");
		sb.append(getTableNameFieldName());
		sb.append(
			" from information_schema.columns c2 where c2.table_catalog = '");
		sb.append(exportContext.getSchemaName());
		sb.append("' and c2.table_schema = 'public' and c2.column_name = ");
		sb.append("'companyid' group by c2.");
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