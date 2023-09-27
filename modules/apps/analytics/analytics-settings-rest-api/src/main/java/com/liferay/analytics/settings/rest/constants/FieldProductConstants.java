/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.constants;

/**
 * @author Riccardo Ferrari
 */
public class FieldProductConstants {

	public static final String[] FIELD_CATEGORY_EXAMPLES = {
		"AB-34098-789-N", "30130", "Some Category", "12345",
		"Default Vocabulary"
	};

	public static final String[] FIELD_CATEGORY_NAMES = {
		"externalReferenceCode", "id", "name", "siteId", "vocabulary"
	};

	public static final String[] FIELD_CATEGORY_REQUIRED_NAMES = {
		"externalReferenceCode", "id", "name", "siteId", "vocabulary"
	};

	public static final String[] FIELD_CATEGORY_TYPES = {
		"String", "Integer", "String", "Integer", "String"
	};

	public static final String[] FIELD_PRODUCT_CHANNEL_EXAMPLES = {
		"EUR", "EU-1234", "12345", "European Mobile Channel", "site"
	};

	public static final String[] FIELD_PRODUCT_CHANNEL_NAMES = {
		"currencyCode", "externalReferenceCode", "id", "name", "type"
	};

	public static final String[] FIELD_PRODUCT_CHANNEL_REQUIRED_NAMES = {
		"currencyCode", "externalReferenceCode", "id", "name", "type"
	};

	public static final String[] FIELD_PRODUCT_CHANNEL_TYPES = {
		"String", "String", "Integer", "String", "String"
	};

	public static final String[] FIELD_PRODUCT_EXAMPLES = {
		"30054", "12345, 12346, ...", "13.5", "2017-07-21",
		"key1=value1,key2=value2,...",
		"Professional hand stainless steel saw for wood. Made to last and " +
			"saw forever. Made of best steel",
		"false", "2017-07-21", "2017-08-21", "2018-08-21", "AB-34098-789-N",
		"AB-34098-789-N", "30130", "OptionA", "AB-34098-789-N",
		"Meta description EN", "Meta keyword EN", "Meta title EN", "2017-08-21",
		"Hand Saw", "12345, 12346, ...", "optiona", "12345, 12346, ...",
		"30000", "ProductOption, ProductOption, ...",
		"ProductSpecification, ProductSpecification, ...", "simple", "true",
		"true", "tt12345", "tt12345, tt34556", "quantity", "0", "false",
		"tag1, tag2, ...", "product-url-us, ...", "10pz", "Values EN"
	};

	public static final String[] FIELD_PRODUCT_NAMES = {
		"catalogId", "categoryIds", "cost", "createDate", "customFields",
		"description", "discontinued", "displayDate", "expirationDate",
		"expirationDate", "externalReferenceCode", "gtin", "id", "key",
		"manufacturerPartNumber", "metaDescription", "metaKeyword", "metaTitle",
		"modifiedDate", "name", "optionCategoryId", "optionKey",
		"productChannelIds", "productId", "productOptions",
		"productSpecifications", "productType", "published", "purchasable",
		"sku", "skus", "specificationKey", "status", "subscriptionEnabled",
		"tags", "urls", "value", "values"
	};

	public static final String[] FIELD_PRODUCT_REQUIRED_NAMES = {
		"catalogId", "categoryIds", "cost", "createDate", "customFields",
		"description", "discontinued", "displayDate", "expirationDate",
		"expirationDate", "externalReferenceCode", "gtin", "id", "key",
		"manufacturerPartNumber", "metaDescription", "metaKeyword", "metaTitle",
		"modifiedDate", "name", "optionCategoryId", "optionKey",
		"productChannelIds", "productId", "productOptions",
		"productSpecifications", "productType", "published", "purchasable",
		"sku", "skus", "specificationKey", "status", "subscriptionEnabled",
		"tags", "urls", "value", "values"
	};

	public static final String[] FIELD_PRODUCT_TYPES = {
		"Integer", "Array", "Number", "String", "Object", "Object", "Boolean",
		"String", "String", "String", "String", "String", "Integer", "String",
		"String", "Object", "Object", "Object", "String", "Object", "Integer",
		"String", "Array", "Integer", "Array", "Array", "String", "Boolean",
		"Boolean", "String", "Array", "String", "Integer", "boolean", "Array",
		"Object", "String", "Object"
	};

}