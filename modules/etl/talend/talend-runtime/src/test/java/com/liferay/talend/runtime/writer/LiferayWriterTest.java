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
			_getTLiferayOutputProperties(_OAS_URL, endpoint);

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
				"productContent.json", postContentSchema));

		JsonObject outputJsonObject =
			liferayRequestContentAggregatorSink.getOutputJsonObject();

		Assert.assertTrue(
			"output has name", outputJsonObject.containsKey("name"));

		JsonObject nameJsonObject = outputJsonObject.getJsonObject("name");

		Assert.assertNotNull("name is json object", nameJsonObject);

		Assert.assertTrue(
			"name has key hr_HR", nameJsonObject.containsKey("hr_HR"));
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
		String apiSpecURL, String endpoint) {

		TLiferayOutputProperties liferayOutputProperties =
			new TLiferayOutputProperties("testLiferayOutputProperties");

		liferayOutputProperties.init();

		liferayOutputProperties.setConnectionApiSpecURLValue(apiSpecURL);
		liferayOutputProperties.setResourceEndpointValue(endpoint);
		liferayOutputProperties.setResourceOperationsValue(Action.Insert);

		liferayOutputProperties.setResourceParametersTableColumnNameValue(
			"siteId");
		liferayOutputProperties.setResourceParametersTableTypeColumnNameValue(
			"path");
		liferayOutputProperties.setResourceParametersTableValueColumnNameValue(
			"111111");
		liferayOutputProperties.setSchema(SchemaProperties.EMPTY_SCHEMA);

		return liferayOutputProperties;
	}

	private static final String _OAS_URL =
		"http://localhost:8080/o/headless-commerce-admin-catalog/v1.0" +
			"/openapi.json";

}