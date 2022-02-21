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

package com.liferay.commerce.shipping.engine.fixed.internal.upgrade.v2_3_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceShippingFixedOptionTable {

	public static final String TABLE_NAME = "CommerceShippingFixedOption";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"commerceShippingFixedOptionId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"commerceShippingMethodId", Types.BIGINT}, {"amount", Types.DECIMAL},
		{"description", Types.VARCHAR}, {"key_", Types.VARCHAR},
		{"name", Types.VARCHAR}, {"priority", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceShippingFixedOptionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commerceShippingMethodId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("amount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceShippingFixedOption (mvccVersion LONG default 0 not null,commerceShippingFixedOptionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceShippingMethodId LONG,amount DECIMAL(30, 16) null,description STRING null,key_ VARCHAR(75) null,name STRING null,priority DOUBLE)";

	public static final String TABLE_SQL_DROP =
"drop table CommerceShippingFixedOption";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_DCB21C1F on CommerceShippingFixedOption (commerceShippingMethodId)"
	};

}