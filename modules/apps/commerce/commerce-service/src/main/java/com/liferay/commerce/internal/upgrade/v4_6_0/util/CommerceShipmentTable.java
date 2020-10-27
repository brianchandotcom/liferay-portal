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

package com.liferay.commerce.internal.upgrade.v4_6_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceShipmentTable {

	public static final String TABLE_NAME = "CommerceShipment";

	public static final Object[][] TABLE_COLUMNS = {
		{"commerceShipmentId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"commerceAccountId", Types.BIGINT},
		{"commerceAddressId", Types.BIGINT},
		{"commerceShippingMethodId", Types.BIGINT},
		{"shippingOptionName", Types.CLOB}, {"carrier", Types.VARCHAR},
		{"trackingNumber", Types.VARCHAR}, {"shippingDate", Types.TIMESTAMP},
		{"expectedDate", Types.TIMESTAMP}, {"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("commerceShipmentId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commerceAccountId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceAddressId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceShippingMethodId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("shippingOptionName", Types.CLOB);

TABLE_COLUMNS_MAP.put("carrier", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("trackingNumber", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("expectedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceShipment (commerceShipmentId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceAccountId LONG,commerceAddressId LONG,commerceShippingMethodId LONG,shippingOptionName TEXT null,carrier VARCHAR(75) null,trackingNumber VARCHAR(75) null,shippingDate DATE null,expectedDate DATE null,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table CommerceShipment";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_616BDD15 on CommerceShipment (groupId, commerceAddressId)",
		"create index IX_68FBA2B5 on CommerceShipment (groupId, status)"
	};

}