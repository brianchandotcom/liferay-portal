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

package com.liferay.commerce.price.list.internal.upgrade.v2_1_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommercePriceListTable {

	public static final String TABLE_NAME = "CommercePriceList";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"commercePriceListId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"commerceCurrencyId", Types.BIGINT},
		{"parentCommercePriceListId", Types.BIGINT},
		{"catalogBasePriceList", Types.BOOLEAN}, {"netPrice", Types.BOOLEAN},
		{"type_", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"priority", Types.DOUBLE}, {"displayDate", Types.TIMESTAMP},
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

TABLE_COLUMNS_MAP.put("commercePriceListId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commerceCurrencyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentCommercePriceListId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("catalogBasePriceList", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("netPrice", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("displayDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CommercePriceList (uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,commercePriceListId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceCurrencyId LONG,parentCommercePriceListId LONG,catalogBasePriceList BOOLEAN,netPrice BOOLEAN,type_ VARCHAR(75) null,name VARCHAR(75) null,priority DOUBLE,displayDate DATE null,expirationDate DATE null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CommercePriceList";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_473B4D8D on CommercePriceList (commerceCurrencyId)",
		"create index IX_328B5D27 on CommercePriceList (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_31913054 on CommercePriceList (displayDate, status)",
		"create index IX_354C658C on CommercePriceList (groupId, catalogBasePriceList, type_[$COLUMN_LENGTH:75$])",
		"create index IX_B61658B6 on CommercePriceList (groupId, companyId, status)",
		"create index IX_863045BB on CommercePriceList (parentCommercePriceListId)",
		"create index IX_FCE28706 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_554D1708 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}