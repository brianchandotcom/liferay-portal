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

package com.liferay.commerce.internal.upgrade.v4_9_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceOrderItemTable {

	public static final String TABLE_NAME = "CommerceOrderItem";

	public static final Object[][] TABLE_COLUMNS = {
		{"externalReferenceCode", Types.VARCHAR},
		{"commerceOrderItemId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"commerceOrderId", Types.BIGINT},
		{"commercePriceListId", Types.BIGINT}, {"CProductId", Types.BIGINT},
		{"CPInstanceId", Types.BIGINT},
		{"parentCommerceOrderItemId", Types.BIGINT},
		{"quantity", Types.INTEGER}, {"shippedQuantity", Types.INTEGER},
		{"json", Types.CLOB}, {"name", Types.VARCHAR}, {"sku", Types.VARCHAR},
		{"unitPrice", Types.DECIMAL}, {"promoPrice", Types.DECIMAL},
		{"discountAmount", Types.DECIMAL}, {"finalPrice", Types.DECIMAL},
		{"discountPercentageLevel1", Types.DECIMAL},
		{"discountPercentageLevel2", Types.DECIMAL},
		{"discountPercentageLevel3", Types.DECIMAL},
		{"discountPercentageLevel4", Types.DECIMAL},
		{"unitPriceWithTaxAmount", Types.DECIMAL},
		{"promoPriceWithTaxAmount", Types.DECIMAL},
		{"discountWithTaxAmount", Types.DECIMAL},
		{"finalPriceWithTaxAmount", Types.DECIMAL},
		{"discountPctLevel1WithTaxAmount", Types.DECIMAL},
		{"discountPctLevel2WithTaxAmount", Types.DECIMAL},
		{"discountPctLevel3WithTaxAmount", Types.DECIMAL},
		{"discountPctLevel4WithTaxAmount", Types.DECIMAL},
		{"subscription", Types.BOOLEAN}, {"deliveryGroup", Types.VARCHAR},
		{"shippingAddressId", Types.BIGINT}, {"printedNote", Types.VARCHAR},
		{"requestedDeliveryDate", Types.TIMESTAMP},
		{"bookedQuantityId", Types.BIGINT}, {"manuallyAdjusted", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceOrderItemId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commerceOrderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commercePriceListId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("CProductId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("CPInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentCommerceOrderItemId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("quantity", Types.INTEGER);

TABLE_COLUMNS_MAP.put("shippedQuantity", Types.INTEGER);

TABLE_COLUMNS_MAP.put("json", Types.CLOB);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("sku", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("unitPrice", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("promoPrice", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("finalPrice", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountPercentageLevel1", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountPercentageLevel2", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountPercentageLevel3", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountPercentageLevel4", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("unitPriceWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("promoPriceWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("finalPriceWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountPctLevel1WithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountPctLevel2WithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountPctLevel3WithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("discountPctLevel4WithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subscription", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("deliveryGroup", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("shippingAddressId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("printedNote", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("requestedDeliveryDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("bookedQuantityId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("manuallyAdjusted", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceOrderItem (externalReferenceCode VARCHAR(75) null,commerceOrderItemId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceOrderId LONG,commercePriceListId LONG,CProductId LONG,CPInstanceId LONG,parentCommerceOrderItemId LONG,quantity INTEGER,shippedQuantity INTEGER,json TEXT null,name STRING null,sku VARCHAR(75) null,unitPrice DECIMAL(30, 16) null,promoPrice DECIMAL(30, 16) null,discountAmount DECIMAL(30, 16) null,finalPrice DECIMAL(30, 16) null,discountPercentageLevel1 DECIMAL(30, 16) null,discountPercentageLevel2 DECIMAL(30, 16) null,discountPercentageLevel3 DECIMAL(30, 16) null,discountPercentageLevel4 DECIMAL(30, 16) null,unitPriceWithTaxAmount DECIMAL(30, 16) null,promoPriceWithTaxAmount DECIMAL(30, 16) null,discountWithTaxAmount DECIMAL(30, 16) null,finalPriceWithTaxAmount DECIMAL(30, 16) null,discountPctLevel1WithTaxAmount DECIMAL(30, 16) null,discountPctLevel2WithTaxAmount DECIMAL(30, 16) null,discountPctLevel3WithTaxAmount DECIMAL(30, 16) null,discountPctLevel4WithTaxAmount DECIMAL(30, 16) null,subscription BOOLEAN,deliveryGroup VARCHAR(75) null,shippingAddressId LONG,printedNote STRING null,requestedDeliveryDate DATE null,bookedQuantityId LONG,manuallyAdjusted BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table CommerceOrderItem";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_2E1BB39D on CommerceOrderItem (CPInstanceId)",
		"create index IX_F9E8D927 on CommerceOrderItem (CProductId)",
		"create index IX_2D8339EE on CommerceOrderItem (bookedQuantityId)",
		"create index IX_415AF3E3 on CommerceOrderItem (commerceOrderId, CPInstanceId)",
		"create index IX_15B37023 on CommerceOrderItem (commerceOrderId, subscription)",
		"create index IX_12257E21 on CommerceOrderItem (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_8E1472FB on CommerceOrderItem (parentCommerceOrderItemId)"
	};

}