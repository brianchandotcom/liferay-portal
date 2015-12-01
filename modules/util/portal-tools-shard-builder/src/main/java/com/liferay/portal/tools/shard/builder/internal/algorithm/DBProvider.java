/*
 * *
 *  * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *  *
 *  * This library is free software; you can redistribute it and/or modify it under
 *  * the terms of the GNU Lesser General Public License as published by the Free
 *  * Software Foundation; either version 2.1 of the License, or (at your option)
 *  * any later version.
 *  *
 *  * This library is distributed in the hope that it will be useful, but WITHOUT
 *  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 *  * details.
 *
 */

package com.liferay.portal.tools.shard.builder.internal.algorithm;

import java.sql.SQLException;

import java.util.List;

import javax.sql.DataSource;

/**
 * @author Manuel de la Peña
 */
public interface DBProvider {

	public List<Long> getCompanyIds() throws SQLException;

	public DataSource getDataSource();

	public String getSchemaName();

	public String getTableNameFieldName();

	public List<String> getTableNamesWithoutCompanyId() throws SQLException;

	public String getTableRows(String tableName) throws SQLException;

	public String serializeTableField(Object field);

}