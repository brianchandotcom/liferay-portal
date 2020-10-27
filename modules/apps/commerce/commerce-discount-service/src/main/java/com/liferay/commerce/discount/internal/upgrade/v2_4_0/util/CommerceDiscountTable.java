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

package com.liferay.commerce.discount.internal.upgrade.v2_4_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceDiscountTable {

	public static final String TABLE_NAME = "CommerceDiscount";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"commerceDiscountId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"title", Types.VARCHAR}, {"target", Types.VARCHAR},
		{"useCouponCode", Types.BOOLEAN}, {"couponCode", Types.VARCHAR},
		{"usePercentage", Types.BOOLEAN},
		{"maximumDiscountAmount", Types.DECIMAL}, {"levelType", Types.VARCHAR},
		{"level1", Types.DECIMAL}, {"level2", Types.DECIMAL},
		{"level3", Types.DECIMAL}, {"level4", Types.DECIMAL},
		{"limitationType", Types.VARCHAR}, {"limitationTimes", Types.INTEGER},
		{"limitationTimesPerAccount", Types.INTEGER},
		{"numberOfUse", Types.INTEGER}, {"rulesConjunction", Types.BOOLEAN},
		{"active_", Types.BOOLEAN}, {"displayDate", Types.TIMESTAMP},
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

TABLE_COLUMNS_MAP.put("commerceDiscountId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("target", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("useCouponCode", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("couponCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("usePercentage", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("maximumDiscountAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("levelType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("level1", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("level2", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("level3", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("level4", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("limitationType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("limitationTimes", Types.INTEGER);

TABLE_COLUMNS_MAP.put("limitationTimesPerAccount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("numberOfUse", Types.INTEGER);

TABLE_COLUMNS_MAP.put("rulesConjunction", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("displayDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceDiscount (uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,commerceDiscountId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(75) null,target VARCHAR(75) null,useCouponCode BOOLEAN,couponCode VARCHAR(75) null,usePercentage BOOLEAN,maximumDiscountAmount DECIMAL(30, 16) null,levelType VARCHAR(75) null,level1 DECIMAL(30, 16) null,level2 DECIMAL(30, 16) null,level3 DECIMAL(30, 16) null,level4 DECIMAL(30, 16) null,limitationType VARCHAR(75) null,limitationTimes INTEGER,limitationTimesPerAccount INTEGER,numberOfUse INTEGER,rulesConjunction BOOLEAN,active_ BOOLEAN,displayDate DATE null,expirationDate DATE null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CommerceDiscount";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_A7A710FC on CommerceDiscount (companyId, couponCode[$COLUMN_LENGTH:75$], active_)",
		"create index IX_D294CDB7 on CommerceDiscount (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_122C15C4 on CommerceDiscount (displayDate, status)",
		"create index IX_2FBF0739 on CommerceDiscount (expirationDate, status)",
		"create index IX_687F1796 on CommerceDiscount (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}