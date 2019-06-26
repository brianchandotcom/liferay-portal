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

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import javax.ws.rs.HttpMethod;

import org.apache.avro.Schema;
import org.apache.avro.generic.IndexedRecord;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class ResourceNodeConverterTest {

	@Before
	public void setUp() throws Exception {
		if (_oasJsonObject == null) {
			_oasJsonObject = _readObject("openapi.json");
		}

		if (_productJsonObject == null) {
			_productJsonObject = _readObject("productRequestContent.json");
		}
	}

	@Test
	public void testInferSchemaForGetOperation() {
		String endpoint = "/v1.0/catalogs/{siteId}/products";

		Schema schema = _getSchema(endpoint, HttpMethod.GET);

		ResourceNodeConverter resourceNodeConverter = new ResourceNodeConverter(
			schema);

		IndexedRecord indexedRecord = resourceNodeConverter.convertToAvro(
			_productJsonObject);

		Assert.assertNotNull(
			"product is converted to indexed record", indexedRecord);

		Assert.assertEquals("sku is '3 pcs'", "3 pcs", indexedRecord.get(13));

		Object id = indexedRecord.get(17);

		Assert.assertEquals("id class is Long", Long.class, id.getClass());

		Assert.assertEquals("id value is 19770907", Long.valueOf(19770709), id);

		Object productId = indexedRecord.get(24);

		Assert.assertEquals(
			"productId class is long 20111101", Long.class,
			productId.getClass());

		Assert.assertEquals(
			"productId value is 20111101", Long.valueOf(20111101), productId);
	}

	private Schema _getSchema(String endpoint, String operation) {
		JsonObject endpointsJsonObject = _oasJsonObject.getJsonObject(
			OASConstants.PATHS);

		Assert.assertTrue(endpointsJsonObject.containsKey(endpoint));

		return _endpointSchemaInferrer.inferSchema(
			endpoint, operation, _oasJsonObject);
	}

	private JsonObject _readObject(String fileName) {
		Class<ResourceNodeConverterTest> resourceNodeConverterTestClass =
			ResourceNodeConverterTest.class;

		InputStream resourceAsStream =
			resourceNodeConverterTestClass.getResourceAsStream(fileName);

		JsonReader jsonReader = Json.createReader(resourceAsStream);

		return jsonReader.readObject();
	}

	private final EndpointSchemaInferrer _endpointSchemaInferrer =
		new EndpointSchemaInferrer();
	private JsonObject _oasJsonObject;
	private JsonObject _productJsonObject;

}