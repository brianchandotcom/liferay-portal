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

package com.liferay.talend.runtime.writer;

import com.liferay.talend.BaseTestCase;
import com.liferay.talend.avro.JsonObjectIndexedRecordConverter;
import com.liferay.talend.properties.output.LiferayOutputProperties;
import com.liferay.talend.properties.resource.Operation;
import com.liferay.talend.runtime.LiferayRequestContentAggregatorSink;
import com.liferay.talend.runtime.LiferaySink;

import java.io.IOException;

import java.math.BigDecimal;

import java.util.Arrays;

import javax.json.JsonNumber;
import javax.json.JsonObject;

import org.apache.avro.Schema;
import org.apache.avro.generic.IndexedRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class LiferayWriterTest extends BaseTestCase {

	@Test
	public void testWrite() throws Exception {
		String openApiModule = "/headless-openapi-module/v1.0";
		String endpoint = "/catalogs/{siteId}/product";

		LiferayRequestContentAggregatorSink
			liferayRequestContentAggregatorSink =
				new LiferayRequestContentAggregatorSink();

		LiferayOutputProperties testLiferayOutputProperties =
			_getLiferayOutputProperties(
				Operation.Insert, openApiModule, _OAS_URL, endpoint, "siteId");

		JsonObject oasJsonObject = readObject("openapi.json");

		Schema postContentSchema = getSchema(
			"/v1.0/catalogs/{siteId}/product", "POST", oasJsonObject);

		testLiferayOutputProperties.resource.entitySchemaProperties.schema.
			setValue(postContentSchema);
		testLiferayOutputProperties.resource.outboundSchemaProperties.schema.
			setValue(postContentSchema);

		LiferayWriter liferayWriter = new LiferayWriter(
			new LiferayWriteOperation(
				liferayRequestContentAggregatorSink,
				testLiferayOutputProperties),
			testLiferayOutputProperties);

		liferayWriter.open("aaaa-bbbb-cccc-dddd");

		liferayWriter.write(
			_createIndexedRecordFromFile(
				"product_content.json", postContentSchema));

		JsonObject outputJsonObject =
			liferayRequestContentAggregatorSink.getOutputJsonObject();

		Assert.assertTrue(
			"Output has name", outputJsonObject.containsKey("name"));

		JsonObject nameJsonObject = outputJsonObject.getJsonObject("name");

		Assert.assertNotNull("name is json object", nameJsonObject);

		Assert.assertTrue(
			"Name has key hr_HR", nameJsonObject.containsKey("hr_HR"));
	}

	@Test
	public void testWriteBigDecimal() throws Exception {
		String openApiModule = "/headless-openapi-module/v1.0";
		String endpoint = "/bigdecimal/{id}";

		LiferayRequestContentAggregatorSink
			liferayRequestContentAggregatorSink =
				new LiferayRequestContentAggregatorSink();

		LiferayOutputProperties testLiferayOutputProperties =
			_getLiferayOutputProperties(
				Operation.Update, openApiModule, _OAS_URL, endpoint, "id");

		JsonObject oasJsonObject = readObject("openapi_data_types.json");

		Schema patchContentSchema = getSchema(
			"/v1.0/bigdecimal/{id}", "PATCH", oasJsonObject);

		testLiferayOutputProperties.resource.entitySchemaProperties.schema.
			setValue(patchContentSchema);
		testLiferayOutputProperties.resource.outboundSchemaProperties.schema.
			setValue(patchContentSchema);

		LiferayWriter liferayWriter = new LiferayWriter(
			new LiferayWriteOperation(
				liferayRequestContentAggregatorSink,
				testLiferayOutputProperties),
			testLiferayOutputProperties);

		liferayWriter.open("aaaa-bbbb-cccc-dddd");

		liferayWriter.write(
			_createIndexedRecordFromFile(
				"bigdecimal_content.json", patchContentSchema));

		JsonObject outputJsonObject =
			liferayRequestContentAggregatorSink.getOutputJsonObject();

		Assert.assertTrue(
			"Output has bigDecimal1",
			outputJsonObject.containsKey("bigDecimal1"));

		JsonNumber bigDecimalNumber = outputJsonObject.getJsonNumber(
			"bigDecimal1");

		Assert.assertEquals(
			"Field bigDecimal1 value", new BigDecimal("1.97797"),
			bigDecimalNumber.bigDecimalValue());

		bigDecimalNumber = outputJsonObject.getJsonNumber("bigDecimal2");

		Assert.assertEquals(
			"Field bigDecimal2 value",
			new BigDecimal("0.0000000000000000197797"),
			bigDecimalNumber.bigDecimalValue());
	}

	@Test(expected = IOException.class)
	public void testWriteNullIndexedRecord() throws Exception {
		String openApiModule = "/headless-openapi-module/v1.0";
		String endpoint = "/bigdecimal/{id}";

		LiferayOutputProperties testLiferayOutputProperties =
			_getLiferayOutputProperties(
				Operation.Update, openApiModule, _OAS_URL, endpoint, "id");

		testLiferayOutputProperties.setupProperties();

		LiferayWriter liferayWriter = new LiferayWriter(
			new LiferayWriteOperation(
				new LiferaySink(), testLiferayOutputProperties),
			testLiferayOutputProperties);

		liferayWriter.write(null);
	}

	private IndexedRecord _createIndexedRecordFromFile(
		String name, Schema schema) {

		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			new JsonObjectIndexedRecordConverter(schema);

		return jsonObjectIndexedRecordConverter.toIndexedRecord(
			readObject(name));
	}

	private LiferayOutputProperties _getLiferayOutputProperties(
		Operation operation, String openAPIModule, String apiSpecURL,
		String endpoint, String parameterName) {

		return new LiferayOutputProperties(
			"testLiferayOutputProperties", operation, openAPIModule, apiSpecURL,
			endpoint, Arrays.asList(parameterName), Arrays.asList("path"),
			Arrays.asList("1977"));
	}

	private static final String _OAS_URL =
		"http://localhost:8080/o/headless-commerce-admin-catalog/v1.0" +
			"/openapi.json";

}