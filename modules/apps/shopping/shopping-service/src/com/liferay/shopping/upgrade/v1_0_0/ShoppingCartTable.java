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

package com.liferay.shopping.upgrade.v1_0_0;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ShoppingCartTable {

	public static final String TABLE_NAME = "ShoppingCart";

	public static final Object[][] TABLE_COLUMNS = {
		{"cartId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"itemIds", Types.CLOB},
		{"couponCodes", Types.VARCHAR},
		{"altShipping", Types.INTEGER},
		{"insure", Types.BOOLEAN}
	};

	public static final String TABLE_SQL_CREATE = "create table ShoppingCart (cartId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,itemIds TEXT null,couponCodes VARCHAR(75) null,altShipping INTEGER,insure BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table ShoppingCart";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_FC46FE16 on ShoppingCart (groupId, userId)",
		"create index IX_54101CC8 on ShoppingCart (userId)"
	};

}