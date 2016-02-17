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

package com.liferay.shopping.upgrade.v1_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ShoppingOrderTable {

	public static final String TABLE_NAME = "ShoppingOrder";

	public static final Object[][] TABLE_COLUMNS = {
		{"orderId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"number_", Types.VARCHAR},
		{"tax", Types.DOUBLE},
		{"shipping", Types.DOUBLE},
		{"altShipping", Types.VARCHAR},
		{"requiresShipping", Types.BOOLEAN},
		{"insure", Types.BOOLEAN},
		{"insurance", Types.DOUBLE},
		{"couponCodes", Types.VARCHAR},
		{"couponDiscount", Types.DOUBLE},
		{"billingFirstName", Types.VARCHAR},
		{"billingLastName", Types.VARCHAR},
		{"billingEmailAddress", Types.VARCHAR},
		{"billingCompany", Types.VARCHAR},
		{"billingStreet", Types.VARCHAR},
		{"billingCity", Types.VARCHAR},
		{"billingState", Types.VARCHAR},
		{"billingZip", Types.VARCHAR},
		{"billingCountry", Types.VARCHAR},
		{"billingPhone", Types.VARCHAR},
		{"shipToBilling", Types.BOOLEAN},
		{"shippingFirstName", Types.VARCHAR},
		{"shippingLastName", Types.VARCHAR},
		{"shippingEmailAddress", Types.VARCHAR},
		{"shippingCompany", Types.VARCHAR},
		{"shippingStreet", Types.VARCHAR},
		{"shippingCity", Types.VARCHAR},
		{"shippingState", Types.VARCHAR},
		{"shippingZip", Types.VARCHAR},
		{"shippingCountry", Types.VARCHAR},
		{"shippingPhone", Types.VARCHAR},
		{"ccName", Types.VARCHAR},
		{"ccType", Types.VARCHAR},
		{"ccNumber", Types.VARCHAR},
		{"ccExpMonth", Types.INTEGER},
		{"ccExpYear", Types.INTEGER},
		{"ccVerNumber", Types.VARCHAR},
		{"comments", Types.CLOB},
		{"ppTxnId", Types.VARCHAR},
		{"ppPaymentStatus", Types.VARCHAR},
		{"ppPaymentGross", Types.DOUBLE},
		{"ppReceiverEmail", Types.VARCHAR},
		{"ppPayerEmail", Types.VARCHAR},
		{"sendOrderEmail", Types.BOOLEAN},
		{"sendShippingEmail", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("orderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("number_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("tax", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("shipping", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("altShipping", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("requiresShipping", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("insure", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("insurance", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("couponCodes", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("couponDiscount", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("billingFirstName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingLastName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingEmailAddress", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingCompany", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingStreet", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingCity", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingState", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingZip", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingCountry", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingPhone", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shipToBilling", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("shippingFirstName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingLastName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingEmailAddress", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingCompany", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingStreet", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingCity", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingState", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingZip", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingCountry", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingPhone", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("ccName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("ccType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("ccNumber", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("ccExpMonth", Types.INTEGER);

TABLE_COLUMNS_MAP.put("ccExpYear", Types.INTEGER);

TABLE_COLUMNS_MAP.put("ccVerNumber", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("comments", Types.CLOB);

TABLE_COLUMNS_MAP.put("ppTxnId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("ppPaymentStatus", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("ppPaymentGross", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("ppReceiverEmail", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("ppPayerEmail", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("sendOrderEmail", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("sendShippingEmail", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table ShoppingOrder (orderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,number_ VARCHAR(75) null,tax DOUBLE,shipping DOUBLE,altShipping VARCHAR(75) null,requiresShipping BOOLEAN,insure BOOLEAN,insurance DOUBLE,couponCodes VARCHAR(75) null,couponDiscount DOUBLE,billingFirstName VARCHAR(75) null,billingLastName VARCHAR(75) null,billingEmailAddress VARCHAR(75) null,billingCompany VARCHAR(75) null,billingStreet VARCHAR(75) null,billingCity VARCHAR(75) null,billingState VARCHAR(75) null,billingZip VARCHAR(75) null,billingCountry VARCHAR(75) null,billingPhone VARCHAR(75) null,shipToBilling BOOLEAN,shippingFirstName VARCHAR(75) null,shippingLastName VARCHAR(75) null,shippingEmailAddress VARCHAR(75) null,shippingCompany VARCHAR(75) null,shippingStreet VARCHAR(75) null,shippingCity VARCHAR(75) null,shippingState VARCHAR(75) null,shippingZip VARCHAR(75) null,shippingCountry VARCHAR(75) null,shippingPhone VARCHAR(75) null,ccName VARCHAR(75) null,ccType VARCHAR(75) null,ccNumber VARCHAR(75) null,ccExpMonth INTEGER,ccExpYear INTEGER,ccVerNumber VARCHAR(75) null,comments TEXT null,ppTxnId VARCHAR(75) null,ppPaymentStatus VARCHAR(75) null,ppPaymentGross DOUBLE,ppReceiverEmail VARCHAR(75) null,ppPayerEmail VARCHAR(75) null,sendOrderEmail BOOLEAN,sendShippingEmail BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table ShoppingOrder";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_119B5630 on ShoppingOrder (groupId, userId, ppPaymentStatus)",
		"create unique index IX_D7D6E87A on ShoppingOrder (number_)",
		"create index IX_F474FD89 on ShoppingOrder (ppTxnId)"
	};

}