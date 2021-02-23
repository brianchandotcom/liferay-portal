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

package com.liferay.commerce.tax.engine.fixed.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceTaxFixedRateAddressRelTable {

	public static final String TABLE_NAME = "CommerceTaxFixedRateAddressRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"CTaxFixedRateAddressRelId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"commerceTaxMethodId", Types.BIGINT},
		{"CPTaxCategoryId", Types.BIGINT}, {"commerceCountryId", Types.BIGINT},
		{"commerceRegionId", Types.BIGINT}, {"zip", Types.VARCHAR},
		{"rate", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("CTaxFixedRateAddressRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commerceTaxMethodId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("CPTaxCategoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceCountryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceRegionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("zip", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("rate", Types.DOUBLE);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceTaxFixedRateAddressRel (CTaxFixedRateAddressRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceTaxMethodId LONG,CPTaxCategoryId LONG,commerceCountryId LONG,commerceRegionId LONG,zip VARCHAR(75) null,rate DOUBLE)";

	public static final String TABLE_SQL_DROP =
"drop table CommerceTaxFixedRateAddressRel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_37AE3A58 on CommerceTaxFixedRateAddressRel (CPTaxCategoryId)",
		"create index IX_CB69750D on CommerceTaxFixedRateAddressRel (commerceTaxMethodId)",
		"create index IX_DB83CD12 on CommerceTaxFixedRateAddressRel (countryId)"
	};

}