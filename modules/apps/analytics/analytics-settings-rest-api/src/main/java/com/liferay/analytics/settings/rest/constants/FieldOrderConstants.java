/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.constants;

/**
 * @author Riccardo Ferrari
 */
public class FieldOrderConstants {

	public static final String[] FIELD_ORDER_EXAMPLES = {
		"30130", "30130", "2017-07-21", "USD", "key1=value1, key2=value2, ...",
		"AB-34098-789-N", "30130", "2017-08-21", "2017-07-21",
		"[item, item,...]", "0", "AB-34098-789-N", "30130", "paypal", "0", "0",
		"113", "12345"
	};

	public static final String[] FIELD_ORDER_ITEM_EXAMPLES = {
		"12345", "2017-07-21", "key1=value1, key2=value2, ...",
		"AB-34098-789-N", "200", "30130", "2017-07-21", "Hand Saw", "", "30128",
		"30128", "2", "12341234", "True", "pc", "101", "12345"
	};

	public static final String[] FIELD_ORDER_ITEM_NAMES = {
		"cpDefinitionId", "createDate", "customFields", "externalReferenceCode",
		"finalPrice", "id", "modifiedDate", "name", "options", "orderId",
		"parentOrderItemId", "quantity", "sku", "subscription", "unitOfMeasure",
		"unitPrice", "userId"
	};

	public static final String[] FIELD_ORDER_ITEM_REQUIRED_NAMES = {
		"cpDefinitionId", "createDate", "customFields", "externalReferenceCode",
		"finalPrice", "id", "modifiedDate", "name", "options", "orderId",
		"parentOrderItemId", "quantity", "sku", "subscription", "unitOfMeasure",
		"unitPrice", "userId"
	};

	public static final String[] FIELD_ORDER_ITEM_TYPES = {
		"Integer", "String", "Object", "String", "Number", "Integer", "String",
		"Object", "String", "Integer", "Integer", "Integer", "String",
		"Boolean", "String", "Number", "Integer"
	};

	public static final String[] FIELD_ORDER_NAMES = {
		"accountId", "channelId", "createDate", "currencyCode", "customFields",
		"externalReferenceCode", "id", "modifiedDate", "orderDate",
		"orderItems", "orderStatus", "orderTypeExternalReferenceCode",
		"orderTypeId", "paymentMethod", "paymentStatus", "status", "total",
		"userId"
	};

	public static final String[] FIELD_ORDER_REQUIRED_NAMES = {
		"accountId", "channelId", "createDate", "currencyCode", "customFields",
		"externalReferenceCode", "id", "modifiedDate", "orderDate",
		"orderItems", "orderStatus", "orderTypeExternalReferenceCode",
		"orderTypeId", "paymentMethod", "paymentStatus", "status", "total",
		"userId"
	};

	public static final String[] FIELD_ORDER_TYPES = {
		"Integer", "Integer", "String", "String", "Object", "String", "Integer",
		"String", "String", "Array", "Integer", "String", "Integer", "String",
		"Integer", "Integer", "BigDecimal", "Integer"
	};

}