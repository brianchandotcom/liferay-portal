/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v3_6_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CPInstanceTable {

	public static final String TABLE_NAME = "CPInstance";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"externalReferenceCode", Types.VARCHAR},
		{"CPInstanceId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"CPDefinitionId", Types.BIGINT},
		{"CPInstanceUuid", Types.VARCHAR}, {"sku", Types.VARCHAR},
		{"gtin", Types.VARCHAR}, {"manufacturerPartNumber", Types.VARCHAR},
		{"purchasable", Types.BOOLEAN}, {"width", Types.DOUBLE},
		{"height", Types.DOUBLE}, {"depth", Types.DOUBLE},
		{"weight", Types.DOUBLE}, {"price", Types.DECIMAL},
		{"promoPrice", Types.DECIMAL}, {"cost", Types.DECIMAL},
		{"published", Types.BOOLEAN}, {"displayDate", Types.TIMESTAMP},
		{"expirationDate", Types.TIMESTAMP},
		{"lastPublishDate", Types.TIMESTAMP},
		{"overrideSubscriptionInfo", Types.BOOLEAN},
		{"subscriptionEnabled", Types.BOOLEAN},
		{"subscriptionLength", Types.INTEGER},
		{"subscriptionType", Types.VARCHAR},
		{"subscriptionTypeSettings", Types.CLOB},
		{"maxSubscriptionCycles", Types.BIGINT},
		{"deliverySubscriptionEnabled", Types.BOOLEAN},
		{"deliverySubscriptionLength", Types.INTEGER},
		{"deliverySubscriptionType", Types.VARCHAR},
		{"deliverySubTypeSettings", Types.VARCHAR},
		{"deliveryMaxSubscriptionCycles", Types.BIGINT},
		{"unspsc", Types.VARCHAR}, {"discontinued", Types.BOOLEAN},
		{"discontinuedDate", Types.TIMESTAMP},
		{"replacementCPInstanceUuid", Types.VARCHAR},
		{"replacementCProductId", Types.BIGINT}, {"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT}, {"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("CPInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("CPDefinitionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("CPInstanceUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("sku", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("gtin", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("manufacturerPartNumber", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("purchasable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("width", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("height", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("depth", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("weight", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("price", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("promoPrice", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("cost", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("published", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("displayDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("overrideSubscriptionInfo", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("subscriptionEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("subscriptionLength", Types.INTEGER);

TABLE_COLUMNS_MAP.put("subscriptionType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("subscriptionTypeSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("maxSubscriptionCycles", Types.BIGINT);

TABLE_COLUMNS_MAP.put("deliverySubscriptionEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("deliverySubscriptionLength", Types.INTEGER);

TABLE_COLUMNS_MAP.put("deliverySubscriptionType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("deliverySubTypeSettings", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("deliveryMaxSubscriptionCycles", Types.BIGINT);

TABLE_COLUMNS_MAP.put("unspsc", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("discontinued", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("discontinuedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("replacementCPInstanceUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("replacementCProductId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CPInstance (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,CPInstanceId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPDefinitionId LONG,CPInstanceUuid VARCHAR(75) null,sku VARCHAR(75) null,gtin VARCHAR(75) null,manufacturerPartNumber VARCHAR(75) null,purchasable BOOLEAN,width DOUBLE,height DOUBLE,depth DOUBLE,weight DOUBLE,price BIGDECIMAL null,promoPrice BIGDECIMAL null,cost BIGDECIMAL null,published BOOLEAN,displayDate DATE null,expirationDate DATE null,lastPublishDate DATE null,overrideSubscriptionInfo BOOLEAN,subscriptionEnabled BOOLEAN,subscriptionLength INTEGER,subscriptionType VARCHAR(75) null,subscriptionTypeSettings TEXT null,maxSubscriptionCycles LONG,deliverySubscriptionEnabled BOOLEAN,deliverySubscriptionLength INTEGER,deliverySubscriptionType VARCHAR(75) null,deliverySubTypeSettings VARCHAR(75) null,deliveryMaxSubscriptionCycles LONG,unspsc VARCHAR(75) null,discontinued BOOLEAN,discontinuedDate DATE null,replacementCPInstanceUuid VARCHAR(75) null,replacementCProductId LONG,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CPInstance";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_25BEB828 on CPInstance (CPDefinitionId, CPInstanceUuid[$COLUMN_LENGTH:75$])",
		"create index IX_C399720F on CPInstance (CPDefinitionId, displayDate, status)",
		"create unique index IX_7E830576 on CPInstance (CPDefinitionId, sku[$COLUMN_LENGTH:75$])",
		"create index IX_F4C9CDD on CPInstance (CPDefinitionId, status)",
		"create index IX_34763899 on CPInstance (CPInstanceUuid[$COLUMN_LENGTH:75$])",
		"create index IX_E2C3A97D on CPInstance (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_9FB1144D on CPInstance (companyId, sku[$COLUMN_LENGTH:75$])",
		"create index IX_7C65903E on CPInstance (displayDate, status)",
		"create index IX_FF605F28 on CPInstance (groupId, status)",
		"create index IX_845761A5 on CPInstance (sku[$COLUMN_LENGTH:75$])",
		"create index IX_8A7A3F5C on CPInstance (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_F902ECDE on CPInstance (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}