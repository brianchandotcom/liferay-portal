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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 * @deprecated As of 7.0.1, with no direct replacement
 */
@Deprecated
public class ShoppingItemTable {

	public static final String TABLE_NAME = "ShoppingItem";

	public static final Object[][] TABLE_COLUMNS = {
		{"itemId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"categoryId", Types.BIGINT},
		{"sku", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"properties", Types.VARCHAR},
		{"fields_", Types.BOOLEAN},
		{"fieldsQuantities", Types.VARCHAR},
		{"minQuantity", Types.INTEGER},
		{"maxQuantity", Types.INTEGER},
		{"price", Types.DOUBLE},
		{"discount", Types.DOUBLE},
		{"taxable", Types.BOOLEAN},
		{"shipping", Types.DOUBLE},
		{"useShippingFormula", Types.BOOLEAN},
		{"requiresShipping", Types.BOOLEAN},
		{"stockQuantity", Types.INTEGER},
		{"featured_", Types.BOOLEAN},
		{"sale_", Types.BOOLEAN},
		{"smallImage", Types.BOOLEAN},
		{"smallImageId", Types.BIGINT},
		{"smallImageURL", Types.VARCHAR},
		{"mediumImage", Types.BOOLEAN},
		{"mediumImageId", Types.BIGINT},
		{"mediumImageURL", Types.VARCHAR},
		{"largeImage", Types.BOOLEAN},
		{"largeImageId", Types.BIGINT},
		{"largeImageURL", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table ShoppingItem (itemId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,categoryId LONG,sku VARCHAR(75) null,name VARCHAR(200) null,description STRING null,properties STRING null,fields_ BOOLEAN,fieldsQuantities STRING null,minQuantity INTEGER,maxQuantity INTEGER,price DOUBLE,discount DOUBLE,taxable BOOLEAN,shipping DOUBLE,useShippingFormula BOOLEAN,requiresShipping BOOLEAN,stockQuantity INTEGER,featured_ BOOLEAN,sale_ BOOLEAN,smallImage BOOLEAN,smallImageId LONG,smallImageURL STRING null,mediumImage BOOLEAN,mediumImageId LONG,mediumImageURL STRING null,largeImage BOOLEAN,largeImageId LONG,largeImageURL STRING null)";

	public static final String TABLE_SQL_DROP = "drop table ShoppingItem";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_1C717CA6 on ShoppingItem (companyId, sku)",
		"create index IX_FEFE7D76 on ShoppingItem (groupId, categoryId)",
		"create index IX_903DC750 on ShoppingItem (largeImageId)",
		"create index IX_D217AB30 on ShoppingItem (mediumImageId)",
		"create index IX_FF203304 on ShoppingItem (smallImageId)"
	};

}