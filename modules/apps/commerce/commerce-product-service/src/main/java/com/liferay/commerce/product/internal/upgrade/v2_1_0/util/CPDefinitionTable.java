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

package com.liferay.commerce.product.internal.upgrade.v2_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CPDefinitionTable {

	public static final String TABLE_NAME = "CPDefinition";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"defaultLanguageId", Types.VARCHAR},
		{"CPDefinitionId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"CProductId", Types.BIGINT},
		{"CPTaxCategoryId", Types.BIGINT}, {"productTypeName", Types.VARCHAR},
		{"availableIndividually", Types.BOOLEAN},
		{"ignoreSKUCombinations", Types.BOOLEAN}, {"shippable", Types.BOOLEAN},
		{"freeShipping", Types.BOOLEAN}, {"shipSeparately", Types.BOOLEAN},
		{"shippingExtraPrice", Types.DOUBLE}, {"width", Types.DOUBLE},
		{"height", Types.DOUBLE}, {"depth", Types.DOUBLE},
		{"weight", Types.DOUBLE}, {"taxExempt", Types.BOOLEAN},
		{"telcoOrElectronics", Types.BOOLEAN},
		{"DDMStructureKey", Types.VARCHAR}, {"published", Types.BOOLEAN},
		{"displayDate", Types.TIMESTAMP}, {"expirationDate", Types.TIMESTAMP},
		{"lastPublishDate", Types.TIMESTAMP},
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
		{"accountGroupFilterEnabled", Types.BOOLEAN},
		{"channelFilterEnabled", Types.BOOLEAN}, {"version", Types.INTEGER},
		{"status", Types.INTEGER}, {"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR}, {"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("defaultLanguageId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("CPDefinitionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("CProductId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("CPTaxCategoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("productTypeName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("availableIndividually", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("ignoreSKUCombinations", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("shippable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("freeShipping", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("shipSeparately", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("shippingExtraPrice", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("width", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("height", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("depth", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("weight", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("taxExempt", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("telcoOrElectronics", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("DDMStructureKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("published", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("displayDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

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

TABLE_COLUMNS_MAP.put("accountGroupFilterEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("channelFilterEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("version", Types.INTEGER);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CPDefinition (uuid_ VARCHAR(75) null,defaultLanguageId VARCHAR(75) null,CPDefinitionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CProductId LONG,CPTaxCategoryId LONG,productTypeName VARCHAR(75) null,availableIndividually BOOLEAN,ignoreSKUCombinations BOOLEAN,shippable BOOLEAN,freeShipping BOOLEAN,shipSeparately BOOLEAN,shippingExtraPrice DOUBLE,width DOUBLE,height DOUBLE,depth DOUBLE,weight DOUBLE,taxExempt BOOLEAN,telcoOrElectronics BOOLEAN,DDMStructureKey VARCHAR(75) null,published BOOLEAN,displayDate DATE null,expirationDate DATE null,lastPublishDate DATE null,subscriptionEnabled BOOLEAN,subscriptionLength INTEGER,subscriptionType VARCHAR(75) null,subscriptionTypeSettings TEXT null,maxSubscriptionCycles LONG,deliverySubscriptionEnabled BOOLEAN,deliverySubscriptionLength INTEGER,deliverySubscriptionType VARCHAR(75) null,deliverySubTypeSettings VARCHAR(75) null,deliveryMaxSubscriptionCycles LONG,accountGroupFilterEnabled BOOLEAN,channelFilterEnabled BOOLEAN,version INTEGER,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CPDefinition";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_3D5A0021 on CPDefinition (CPTaxCategoryId)",
		"create index IX_1F4B9C67 on CPDefinition (CProductId, status)",
		"create index IX_F1AEC8A7 on CPDefinition (CProductId, version)",
		"create index IX_217AF702 on CPDefinition (companyId)",
		"create index IX_A465D100 on CPDefinition (displayDate, status)",
		"create index IX_419350EA on CPDefinition (groupId, status)",
		"create index IX_99C4ED10 on CPDefinition (groupId, subscriptionEnabled)",
		"create index IX_8EA585DA on CPDefinition (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_BA9BADC on CPDefinition (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}