/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public static final String TABLE_SQL_CREATE = "create table ClusterGroup (mvccVersion LONG default 0 not null,clusterGroupId LONG not null primary key,name VARCHAR(75) null,clusterNodeIds VARCHAR(75) null,wholeCluster BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table ClusterGroup";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}