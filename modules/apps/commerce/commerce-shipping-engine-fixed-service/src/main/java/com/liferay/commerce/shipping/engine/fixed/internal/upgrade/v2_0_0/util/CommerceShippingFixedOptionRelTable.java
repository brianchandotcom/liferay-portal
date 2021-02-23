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

package com.liferay.commerce.shipping.engine.fixed.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceShippingFixedOptionRelTable {

	public static final String TABLE_NAME = "CShippingFixedOptionRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"CShippingFixedOptionRelId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"commerceShippingMethodId", Types.BIGINT},
		{"commerceShippingFixedOptionId", Types.BIGINT},
		{"commerceInventoryWarehouseId", Types.BIGINT},
		{"commerceCountryId", Types.BIGINT}, {"commerceRegionId", Types.BIGINT},
		{"zip", Types.VARCHAR}, {"weightFrom", Types.DOUBLE},
		{"weightTo", Types.DOUBLE}, {"fixedPrice", Types.DECIMAL},
		{"rateUnitWeightPrice", Types.DECIMAL}, {"ratePercentage", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("CShippingFixedOptionRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commerceShippingMethodId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceShippingFixedOptionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceInventoryWarehouseId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceCountryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceRegionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("zip", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("weightFrom", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("weightTo", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("fixedPrice", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("rateUnitWeightPrice", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("ratePercentage", Types.DOUBLE);

}
	public static final String TABLE_SQL_CREATE =
"create table CShippingFixedOptionRel (CShippingFixedOptionRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceShippingMethodId LONG,commerceShippingFixedOptionId LONG,commerceInventoryWarehouseId LONG,commerceCountryId LONG,commerceRegionId LONG,zip VARCHAR(75) null,weightFrom DOUBLE,weightTo DOUBLE,fixedPrice DECIMAL(30, 16) null,rateUnitWeightPrice DECIMAL(30, 16) null,ratePercentage DOUBLE)";

	public static final String TABLE_SQL_DROP =
"drop table CShippingFixedOptionRel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}