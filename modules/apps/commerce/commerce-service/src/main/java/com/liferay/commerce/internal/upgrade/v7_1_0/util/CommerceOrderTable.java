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

package com.liferay.commerce.internal.upgrade.v7_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceOrderTable {

	public static final String TABLE_NAME = "CommerceOrder";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"commerceOrderId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"commerceAccountId", Types.BIGINT},
		{"commerceCurrencyId", Types.BIGINT},
		{"billingAddressId", Types.BIGINT}, {"shippingAddressId", Types.BIGINT},
		{"commercePaymentMethodKey", Types.VARCHAR},
		{"transactionId", Types.CLOB},
		{"commerceShippingMethodId", Types.BIGINT},
		{"shippingOptionName", Types.VARCHAR},
		{"commerceOrderTypeId", Types.BIGINT},
		{"purchaseOrderNumber", Types.VARCHAR}, {"couponCode", Types.VARCHAR},
		{"lastPriceUpdateDate", Types.TIMESTAMP}, {"subtotal", Types.DECIMAL},
		{"subtotalDiscountAmount", Types.DECIMAL},
		{"subtotalDiscountPercentLevel1", Types.DECIMAL},
		{"subtotalDiscountPercentLevel2", Types.DECIMAL},
		{"subtotalDiscountPercentLevel3", Types.DECIMAL},
		{"subtotalDiscountPercentLevel4", Types.DECIMAL},
		{"shippingAmount", Types.DECIMAL},
		{"shippingDiscountAmount", Types.DECIMAL},
		{"shippingDiscountPercentLevel1", Types.DECIMAL},
		{"shippingDiscountPercentLevel2", Types.DECIMAL},
		{"shippingDiscountPercentLevel3", Types.DECIMAL},
		{"shippingDiscountPercentLevel4", Types.DECIMAL},
		{"taxAmount", Types.DECIMAL}, {"total", Types.DECIMAL},
		{"totalDiscountAmount", Types.DECIMAL},
		{"totalDiscountPercentageLevel1", Types.DECIMAL},
		{"totalDiscountPercentageLevel2", Types.DECIMAL},
		{"totalDiscountPercentageLevel3", Types.DECIMAL},
		{"totalDiscountPercentageLevel4", Types.DECIMAL},
		{"subtotalWithTaxAmount", Types.DECIMAL},
		{"subtotalDiscountWithTaxAmount", Types.DECIMAL},
		{"subtotalDiscountPctLev1WithTax", Types.DECIMAL},
		{"subtotalDiscountPctLev2WithTax", Types.DECIMAL},
		{"subtotalDiscountPctLev3WithTax", Types.DECIMAL},
		{"subtotalDiscountPctLev4WithTax", Types.DECIMAL},
		{"shippingWithTaxAmount", Types.DECIMAL},
		{"shippingDiscountWithTaxAmount", Types.DECIMAL},
		{"shippingDiscountPctLev1WithTax", Types.DECIMAL},
		{"shippingDiscountPctLev2WithTax", Types.DECIMAL},
		{"shippingDiscountPctLev3WithTax", Types.DECIMAL},
		{"shippingDiscountPctLev4WithTax", Types.DECIMAL},
		{"totalWithTaxAmount", Types.DECIMAL},
		{"totalDiscountWithTaxAmount", Types.DECIMAL},
		{"totalDiscountPctLev1WithTax", Types.DECIMAL},
		{"totalDiscountPctLev2WithTax", Types.DECIMAL},
		{"totalDiscountPctLev3WithTax", Types.DECIMAL},
		{"totalDiscountPctLev4WithTax", Types.DECIMAL},
		{"advanceStatus", Types.VARCHAR}, {"paymentStatus", Types.INTEGER},
		{"orderDate", Types.TIMESTAMP}, {"orderStatus", Types.INTEGER},
		{"printedNote", Types.VARCHAR},
		{"requestedDeliveryDate", Types.TIMESTAMP},
		{"manuallyAdjusted", Types.BOOLEAN}, {"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT}, {"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceOrderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commerceAccountId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commerceCurrencyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("billingAddressId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("shippingAddressId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("commercePaymentMethodKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("transactionId", Types.CLOB);

TABLE_COLUMNS_MAP.put("commerceShippingMethodId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("shippingOptionName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceOrderTypeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("purchaseOrderNumber", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("couponCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPriceUpdateDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("subtotal", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountPercentLevel1", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountPercentLevel2", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountPercentLevel3", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountPercentLevel4", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountPercentLevel1", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountPercentLevel2", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountPercentLevel3", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountPercentLevel4", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("taxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("total", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountPercentageLevel1", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountPercentageLevel2", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountPercentageLevel3", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountPercentageLevel4", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountPctLev1WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountPctLev2WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountPctLev3WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("subtotalDiscountPctLev4WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountPctLev1WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountPctLev2WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountPctLev3WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("shippingDiscountPctLev4WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountWithTaxAmount", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountPctLev1WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountPctLev2WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountPctLev3WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("totalDiscountPctLev4WithTax", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("advanceStatus", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("paymentStatus", Types.INTEGER);

TABLE_COLUMNS_MAP.put("orderDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("orderStatus", Types.INTEGER);

TABLE_COLUMNS_MAP.put("printedNote", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("requestedDeliveryDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("manuallyAdjusted", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceOrder (uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,commerceOrderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceAccountId LONG,commerceCurrencyId LONG,billingAddressId LONG,shippingAddressId LONG,commercePaymentMethodKey VARCHAR(75) null,transactionId TEXT null,commerceShippingMethodId LONG,shippingOptionName VARCHAR(255) null,commerceOrderTypeId LONG,purchaseOrderNumber VARCHAR(75) null,couponCode VARCHAR(75) null,lastPriceUpdateDate DATE null,subtotal DECIMAL(30, 16) null,subtotalDiscountAmount DECIMAL(30, 16) null,subtotalDiscountPercentLevel1 DECIMAL(30, 16) null,subtotalDiscountPercentLevel2 DECIMAL(30, 16) null,subtotalDiscountPercentLevel3 DECIMAL(30, 16) null,subtotalDiscountPercentLevel4 DECIMAL(30, 16) null,shippingAmount DECIMAL(30, 16) null,shippingDiscountAmount DECIMAL(30, 16) null,shippingDiscountPercentLevel1 DECIMAL(30, 16) null,shippingDiscountPercentLevel2 DECIMAL(30, 16) null,shippingDiscountPercentLevel3 DECIMAL(30, 16) null,shippingDiscountPercentLevel4 DECIMAL(30, 16) null,taxAmount DECIMAL(30, 16) null,total DECIMAL(30, 16) null,totalDiscountAmount DECIMAL(30, 16) null,totalDiscountPercentageLevel1 DECIMAL(30, 16) null,totalDiscountPercentageLevel2 DECIMAL(30, 16) null,totalDiscountPercentageLevel3 DECIMAL(30, 16) null,totalDiscountPercentageLevel4 DECIMAL(30, 16) null,subtotalWithTaxAmount DECIMAL(30, 16) null,subtotalDiscountWithTaxAmount DECIMAL(30, 16) null,subtotalDiscountPctLev1WithTax DECIMAL(30, 16) null,subtotalDiscountPctLev2WithTax DECIMAL(30, 16) null,subtotalDiscountPctLev3WithTax DECIMAL(30, 16) null,subtotalDiscountPctLev4WithTax DECIMAL(30, 16) null,shippingWithTaxAmount DECIMAL(30, 16) null,shippingDiscountWithTaxAmount DECIMAL(30, 16) null,shippingDiscountPctLev1WithTax DECIMAL(30, 16) null,shippingDiscountPctLev2WithTax DECIMAL(30, 16) null,shippingDiscountPctLev3WithTax DECIMAL(30, 16) null,shippingDiscountPctLev4WithTax DECIMAL(30, 16) null,totalWithTaxAmount DECIMAL(30, 16) null,totalDiscountWithTaxAmount DECIMAL(30, 16) null,totalDiscountPctLev1WithTax DECIMAL(30, 16) null,totalDiscountPctLev2WithTax DECIMAL(30, 16) null,totalDiscountPctLev3WithTax DECIMAL(30, 16) null,totalDiscountPctLev4WithTax DECIMAL(30, 16) null,advanceStatus VARCHAR(75) null,paymentStatus INTEGER,orderDate DATE null,orderStatus INTEGER,printedNote STRING null,requestedDeliveryDate DATE null,manuallyAdjusted BOOLEAN,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CommerceOrder";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_12131FC1 on CommerceOrder (billingAddressId)",
		"create index IX_7DD246EA on CommerceOrder (commerceAccountId, groupId, orderStatus)",
		"create index IX_81097E4C on CommerceOrder (commerceAccountId, orderStatus)",
		"create index IX_DFF1932E on CommerceOrder (companyId, commerceAccountId)",
		"create index IX_48EEEDEE on CommerceOrder (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_9ACAF78A on CommerceOrder (createDate, commerceAccountId, orderStatus)",
		"create index IX_4F4CAEE4 on CommerceOrder (groupId, commerceAccountId, orderStatus)",
		"create index IX_9C04F6F8 on CommerceOrder (groupId, commercePaymentMethodKey[$COLUMN_LENGTH:75$])",
		"create index IX_67E0AF05 on CommerceOrder (groupId, userId, orderStatus)",
		"create index IX_4B11FAD8 on CommerceOrder (shippingAddressId)",
		"create index IX_75679B1F on CommerceOrder (userId, createDate, orderStatus)",
		"create index IX_5AF685CD on CommerceOrder (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_58101B8F on CommerceOrder (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}