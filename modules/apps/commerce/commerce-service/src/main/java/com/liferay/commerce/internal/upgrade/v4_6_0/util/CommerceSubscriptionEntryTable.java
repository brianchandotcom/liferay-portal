/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v4_6_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceSubscriptionEntryTable {

	public static final String TABLE_NAME = "CommerceSubscriptionEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"commerceSubscriptionEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"CPInstanceUuid", Types.VARCHAR}, {"CProductId", Types.BIGINT},
		{"commerceOrderItemId", Types.BIGINT},
		{"subscriptionLength", Types.INTEGER},
		{"subscriptionType", Types.VARCHAR},
		{"subscriptionTypeSettings", Types.CLOB},
		{"currentCycle", Types.BIGINT}, {"maxSubscriptionCycles", Types.BIGINT},
		{"subscriptionStatus", Types.INTEGER},
		{"lastIterationDate", Types.TIMESTAMP},
		{"nextIterationDate", Types.TIMESTAMP}, {"startDate", Types.TIMESTAMP},
		{"deliverySubscriptionLength", Types.INTEGER},
		{"deliverySubscriptionType", Types.VARCHAR},
		{"deliverySubTypeSettings", Types.VARCHAR},
		{"deliveryCurrentCycle", Types.BIGINT},
		{"deliveryMaxSubscriptionCycles", Types.BIGINT},
		{"deliverySubscriptionStatus", Types.INTEGER},
		{"deliveryLastIterationDate", Types.TIMESTAMP},
		{"deliveryNextIterationDate", Types.TIMESTAMP},
		{"deliveryStartDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceSubscriptionEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("CPInstanceUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("CProductId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceOrderItemId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("subscriptionLength", Types.INTEGER);

TABLE_COLUMNS_MAP.put("subscriptionType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("subscriptionTypeSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("currentCycle", Types.BIGINT);

TABLE_COLUMNS_MAP.put("maxSubscriptionCycles", Types.BIGINT);

TABLE_COLUMNS_MAP.put("subscriptionStatus", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastIterationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("nextIterationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("startDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("deliverySubscriptionLength", Types.INTEGER);

TABLE_COLUMNS_MAP.put("deliverySubscriptionType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("deliverySubTypeSettings", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("deliveryCurrentCycle", Types.BIGINT);

TABLE_COLUMNS_MAP.put("deliveryMaxSubscriptionCycles", Types.BIGINT);

TABLE_COLUMNS_MAP.put("deliverySubscriptionStatus", Types.INTEGER);

TABLE_COLUMNS_MAP.put("deliveryLastIterationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("deliveryNextIterationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("deliveryStartDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceSubscriptionEntry (uuid_ VARCHAR(75) null,commerceSubscriptionEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPInstanceUuid VARCHAR(75) null,CProductId LONG,commerceOrderItemId LONG,subscriptionLength INTEGER,subscriptionType VARCHAR(75) null,subscriptionTypeSettings TEXT null,currentCycle LONG,maxSubscriptionCycles LONG,subscriptionStatus INTEGER,lastIterationDate DATE null,nextIterationDate DATE null,startDate DATE null,deliverySubscriptionLength INTEGER,deliverySubscriptionType VARCHAR(75) null,deliverySubTypeSettings VARCHAR(75) null,deliveryCurrentCycle LONG,deliveryMaxSubscriptionCycles LONG,deliverySubscriptionStatus INTEGER,deliveryLastIterationDate DATE null,deliveryNextIterationDate DATE null,deliveryStartDate DATE null)";

	public static final String TABLE_SQL_DROP =
"drop table CommerceSubscriptionEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_D7D137B1 on CommerceSubscriptionEntry (commerceOrderItemId)",
		"create index IX_43E6F382 on CommerceSubscriptionEntry (companyId, userId)",
		"create index IX_B99DE058 on CommerceSubscriptionEntry (groupId, companyId, userId)",
		"create index IX_6D080A04 on CommerceSubscriptionEntry (groupId, userId)",
		"create index IX_B496E103 on CommerceSubscriptionEntry (subscriptionStatus)",
		"create index IX_4363DED4 on CommerceSubscriptionEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_943E0A56 on CommerceSubscriptionEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}