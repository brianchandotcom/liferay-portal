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

package com.liferay.commerce.price.list.internal.upgrade.v2_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceTierPriceEntryTable {

	public static final String TABLE_NAME = "CommerceTierPriceEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"commerceTierPriceEntryId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"commercePriceEntryId", Types.BIGINT}, {"price", Types.DECIMAL},
		{"promoPrice", Types.DECIMAL}, {"discountDiscovery", Types.BOOLEAN},
		{"discountLevel1", Types.DECIMAL}, {"discountLevel2", Types.DECIMAL},
		{"discountLevel3", Types.DECIMAL}, {"discountLevel4", Types.DECIMAL},
		{"minQuantity", Types.INTEGER}, {"displayDate", Types.TIMESTAMP},
		{"expirationDate", Types.TIMESTAMP},
		{"lastPublishDate", Types.TIMESTAMP}, {"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT}, {"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceTierPriceEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commercePriceEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("price", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("promoPrice", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountDiscovery", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("discountLevel1", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountLevel2", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountLevel3", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountLevel4", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("minQuantity", Types.INTEGER);

TABLE_COLUMNS_MAP.put("displayDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceTierPriceEntry (uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,commerceTierPriceEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commercePriceEntryId LONG,price DECIMAL(30, 16) null,promoPrice DECIMAL(30, 16) null,discountDiscovery BOOLEAN,discountLevel1 DECIMAL(30, 16) null,discountLevel2 DECIMAL(30, 16) null,discountLevel3 DECIMAL(30, 16) null,discountLevel4 DECIMAL(30, 16) null,minQuantity INTEGER,displayDate DATE null,expirationDate DATE null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP =
"drop table CommerceTierPriceEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_A622C8AE on CommerceTierPriceEntry (commercePriceEntryId, minQuantity)",
		"create index IX_95D59361 on CommerceTierPriceEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_B6C47140 on CommerceTierPriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}