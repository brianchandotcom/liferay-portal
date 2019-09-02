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

package com.liferay.talend.avro;

import com.liferay.talend.common.oas.constants.OASConstants;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.avro.generic.IndexedRecord;

import org.junit.Assert;
import org.junit.Test;

import org.talend.components.api.component.runtime.Result;

/**
 * @author Igor Beslic
 */
public class IndexedRecordJsonObjectConverterTest extends BaseConverterTest {

	@Test
	public void testToJsonObjectWithArrayProperties() throws Exception {
		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			getJsonObjectIndexedRecordConverter(
				"/v1.0/catalogs/{siteId}/products", OASConstants.OPERATION_GET,
				readObject("openapi.json"));

		IndexedRecord indexedRecord =
			jsonObjectIndexedRecordConverter.toIndexedRecord(
				readObject("product_with_nested_images_content.json"));

		Object imagesArray = indexedRecord.get(18);

		Assert.assertEquals(
			"Images field type", String.class, imagesArray.getClass());

		IndexedRecordJsonObjectConverter indexedRecordJsonObjectConverter =
			new IndexedRecordJsonObjectConverter(
				true,
				getSchema(
					"/v1.0/catalogs/{siteId}/product",
					OASConstants.OPERATION_POST, readObject("openapi.json")),
				null, new Result());

		JsonObject jsonObject = indexedRecordJsonObjectConverter.toJsonObject(
			indexedRecord);

		JsonArray imagesJsonArray = jsonObject.getJsonArray("images");

		Assert.assertNotNull(imagesJsonArray);

		JsonValue jsonValue = imagesJsonArray.get(0);

		Assert.assertTrue(
			"Array item is JSON object", jsonValue instanceof JsonObject);

		JsonObject itemJsonObject = jsonValue.asJsonObject();

		Assert.assertEquals(
			"attachment value", "U29tZXRleHQsIHRoaXMgaXMgbm90IGltYWdl",
			itemJsonObject.getString("attachment"));

		JsonNumber idJsonNumber = itemJsonObject.getJsonNumber("id");

		Assert.assertEquals("id value", 1977L, idJsonNumber.longValue());

		JsonValue titleJsonValue = itemJsonObject.get("title");

		Assert.assertTrue(
			"Title field  type is JsonObject",
			titleJsonValue instanceof JsonObject);

		JsonObject titleJsonObject = titleJsonValue.asJsonObject();

		Assert.assertEquals(
			"Title field value", "Image of me",
			titleJsonObject.getString("en_US"));
	}

	@Test
	public void testToJsonObjectWithDictionaryProperties() throws Exception {
		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			getJsonObjectIndexedRecordConverter(
				"/v1.0/catalogs/{siteId}/products", OASConstants.OPERATION_GET,
				readObject("openapi.json"));

		IndexedRecord indexedRecord =
			jsonObjectIndexedRecordConverter.toIndexedRecord(
				readObject("product_content.json"));

		IndexedRecordJsonObjectConverter indexedRecordJsonObjectConverter =
			new IndexedRecordJsonObjectConverter(
				true,
				getSchema(
					"/v1.0/catalogs/{siteId}/product",
					OASConstants.OPERATION_POST, readObject("openapi.json")),
				null, new Result());

		JsonObject jsonObject = indexedRecordJsonObjectConverter.toJsonObject(
			indexedRecord);

		JsonObject descriptionJsonObject = jsonObject.getJsonObject(
			"description");

		Assert.assertNotNull(descriptionJsonObject);

		Assert.assertEquals(
			"dictionary value", "Description of DXP Subscription",
			descriptionJsonObject.getString("en_US"));
	}

}