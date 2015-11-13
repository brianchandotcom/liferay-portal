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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ClusterGroupTable {

	public static final String TABLE_NAME = "ClusterGroup";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"clusterGroupId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"clusterNodeIds", Types.VARCHAR},
		{"wholeCluster", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("clusterGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("clusterNodeIds", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("wholeCluster", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table ClusterGroup (mvccVersion LONG default 0,clusterGroupId LONG not null primary key,name VARCHAR(75) null,clusterNodeIds VARCHAR(75) null,wholeCluster BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table ClusterGroup";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}