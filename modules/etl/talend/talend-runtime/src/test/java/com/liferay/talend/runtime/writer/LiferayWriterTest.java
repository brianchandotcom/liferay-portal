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

import com.liferay.talend.BaseTest;
import com.liferay.talend.avro.JsonObjectIndexedRecordConverter;
import com.liferay.talend.runtime.LiferayRequestContentAggregatorSink;
import com.liferay.talend.runtime.LiferaySink;
import com.liferay.talend.tliferayoutput.Action;
import com.liferay.talend.tliferayoutput.TLiferayOutputProperties;

import java.io.IOException;

import java.math.BigDecimal;

import javax.json.JsonNumber;
import javax.json.JsonObject;

import org.apache.avro.Schema;
import org.apache.avro.generic.IndexedRecord;

import org.junit.Assert;
import org.junit.Test;

import org.talend.components.common.SchemaProperties;

/**
 * @author Igor Beslic
 */
public class LiferayWriterTest extends BaseTest {

	@Test
	public void testWrite() throws Exception {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		LiferayRequestContentAggregatorSink
			liferayRequestContentAggregatorSink =
				new LiferayRequestContentAggregatorSink();

		TLiferayOutputProperties testLiferayOutputProperties =
			_getTLiferayOutputProperties(Action.Insert, _OAS_URL, endpoint);

		_setResourceParametersTableValues(
			"path", "siteId", "", testLiferayOutputProperties);

		JsonObject oasJsonObject = readObject("openapi.json");

		Schema postContentSchema = getSchema(
			"/v1.0/catalogs/{siteId}/product", "POST", oasJsonObject);

		testLiferayOutputProperties.setSchema(postContentSchema);

		LiferayWriter liferayWriter = new LiferayWriter(
			new LiferayWriteOperation(
				liferayRequestContentAggregatorSink,
				testLiferayOutputProperties),
			null, testLiferayOutputProperties);

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
		String endpoint = "/v1.0/price/{id}";

		LiferayRequestContentAggregatorSink
			liferayRequestContentAggregatorSink =
				new LiferayRequestContentAggregatorSink();

		TLiferayOutputProperties testLiferayOutputProperties =
			_getTLiferayOutputProperties(Action.Update, _OAS_URL, endpoint);

		_setResourceParametersTableValues(
			"path", "id", "1977", testLiferayOutputProperties);

		JsonObject oasJsonObject = readObject("openapi_bigdecimal.json");

		Schema patchContentSchema = getSchema(
			"/v1.0/price/{id}", "PATCH", oasJsonObject);

		testLiferayOutputProperties.setSchema(patchContentSchema);

		LiferayWriter liferayWriter = new LiferayWriter(
			new LiferayWriteOperation(
				liferayRequestContentAggregatorSink,
				testLiferayOutputProperties),
			null, testLiferayOutputProperties);

		liferayWriter.open("aaaa-bbbb-cccc-dddd");

		liferayWriter.write(
			_createIndexedRecordFromFile(
				"price_content.json", patchContentSchema));

		JsonObject outputJsonObject =
			liferayRequestContentAggregatorSink.getOutputJsonObject();

		Assert.assertTrue(
			"Output has priceBigDecimal1",
			outputJsonObject.containsKey("priceBigDecimal1"));

		JsonNumber bigDecimalNumber = outputJsonObject.getJsonNumber(
			"priceBigDecimal1");

		Assert.assertEquals(
			"Field priceBigDecimal1 value", new BigDecimal("1.97797"),
			bigDecimalNumber.bigDecimalValue());

		bigDecimalNumber = outputJsonObject.getJsonNumber("priceBigDecimal2");

		Assert.assertEquals(
			"Field priceBigDecimal2 value",
			new BigDecimal("0.0000000000000000197797"),
			bigDecimalNumber.bigDecimalValue());
	}

	@Test(expected = IOException.class)
	public void testWriteNullIndexedRecord() throws Exception {
		TLiferayOutputProperties testLiferayOutputProperties =
			new TLiferayOutputProperties("testLiferayOutputProperties");

		testLiferayOutputProperties.setSchema(SchemaProperties.EMPTY_SCHEMA);

		testLiferayOutputProperties.init();

		testLiferayOutputProperties.setupProperties();

		LiferayWriter liferayWriter = new LiferayWriter(
			new LiferayWriteOperation(
				new LiferaySink(), testLiferayOutputProperties),
			null, testLiferayOutputProperties);

		liferayWriter.write(null);
	}

	private IndexedRecord _createIndexedRecordFromFile(
		String name, Schema schema) {

		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			new JsonObjectIndexedRecordConverter(schema);

		return jsonObjectIndexedRecordConverter.toIndexedRecord(
			readObject(name));
	}

	private TLiferayOutputProperties _getTLiferayOutputProperties(
		Action action, String apiSpecURL, String endpoint) {

		TLiferayOutputProperties liferayOutputProperties =
			new TLiferayOutputProperties("testLiferayOutputProperties");

		liferayOutputProperties.init();

		liferayOutputProperties.setConnectionApiSpecURLValue(apiSpecURL);
		liferayOutputProperties.setResourceEndpointValue(endpoint);
		liferayOutputProperties.setResourceOperationsValue(action);
		liferayOutputProperties.setSchema(SchemaProperties.EMPTY_SCHEMA);

		return liferayOutputProperties;
	}

	private void _setResourceParametersTableValues(
		String parameterLocation, String parameterName, String parameterValue,
		TLiferayOutputProperties tLiferayOutputProperties) {

		tLiferayOutputProperties.setResourceParametersTableColumnNameValue(
			parameterName);
		tLiferayOutputProperties.setResourceParametersTableTypeColumnNameValue(
			parameterLocation);
		tLiferayOutputProperties.setResourceParametersTableValueColumnNameValue(
			parameterValue);
	}

	private static final String _OAS_URL =
		"http://localhost:8080/o/headless-commerce-admin-catalog/v1.0" +
			"/openapi.json";

}