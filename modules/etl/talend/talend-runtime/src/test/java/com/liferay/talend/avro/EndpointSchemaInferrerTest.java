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

import static org.hamcrest.CoreMatchers.equalTo;

import com.liferay.talend.common.oas.constants.OASConstants;

import java.io.InputStream;

import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import javax.ws.rs.HttpMethod;

import org.apache.avro.Schema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.talend.daikon.avro.AvroUtils;

/**
 * @author Zoltán Takács
 */
public class EndpointSchemaInferrerTest {

	@Before
	public void setUp() throws Exception {
		if (_oasJsonObject != null) {
			return;
		}

		Class<EndpointSchemaInferrerTest> endpointSchemaInferrerTestClass =
			EndpointSchemaInferrerTest.class;

		InputStream resourceAsStream =
			endpointSchemaInferrerTestClass.getResourceAsStream("openapi.json");

		JsonReader jsonReader = Json.createReader(resourceAsStream);

		_oasJsonObject = jsonReader.readObject();
	}

	@Test
	public void testBooleanSchemaFieldsForProducts() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, HttpMethod.POST);

		_assertValidProductSchema(schema);
	}

	@Test
	public void testInferSchemaForDeleteOperation() {
		String endpoint =
			"/v1.0/products/by-externalReferenceCode/{externalReferenceCode}";

		Schema schema = _getSchema(endpoint, HttpMethod.DELETE);

		Assert.assertFalse(AvroUtils.isSchemaEmpty(schema));
	}

	@Test
	public void testInferSchemaForGetOperation() {
		String endpoint = "/v1.0/catalogs/{siteId}/products";

		Schema schema = _getSchema(endpoint, HttpMethod.GET);

		Assert.assertFalse(AvroUtils.isSchemaEmpty(schema));

		_assertValidProductSchema(schema);
	}

	@Test
	public void testInferSchemaForInsertOperation() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, HttpMethod.POST);

		Assert.assertFalse(AvroUtils.isSchemaEmpty(schema));
	}

	@Test
	public void testInferSchemaForUpdateOperation() {
		String endpoint =
			"/v1.0/products/by-externalReferenceCode/{externalReferenceCode}";

		Schema schema = _getSchema(endpoint, HttpMethod.PATCH);

		Assert.assertFalse(AvroUtils.isSchemaEmpty(schema));
	}

	@Test
	public void testIntegerSchemaFieldsForProducts() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, HttpMethod.POST);

		List<Schema.Field> fields = schema.getFields();

		Assert.assertThat(fields.size(), equalTo(48));

		Schema.Field field = schema.getField("configuration_minStockQuantity");

		Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"Integer type was expected for nested field: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._int()));
	}

	@Test
	public void testLongSchemaFieldsForProducts() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, HttpMethod.POST);

		List<Schema.Field> fields = schema.getFields();

		Assert.assertThat(fields.size(), equalTo(48));

		Schema.Field field = schema.getField("id");

		Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"Long type was expected: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._long()));

		field = schema.getField("subscriptionConfiguration_numberOfLength");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"Long type was expected for nested field: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._long()));
	}

	@Test
	public void testOpenAPISpecification() {
		Assert.assertTrue("Test", _oasJsonObject.containsKey("openapi"));
	}

	@Test
	public void testStringSchemaFieldsForProducts() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, HttpMethod.POST);

		List<Schema.Field> fields = schema.getFields();

		Assert.assertThat(fields.size(), equalTo(48));

		Schema.Field field = schema.getField("productType");

		Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"String type was expected: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));

		field = schema.getField("taxConfiguration_taxCategory");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"String type was expected for nested field: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));

		field = schema.getField("description");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"String type was expected for dictionary field: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));

		field = schema.getField("expando");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"String type was expected for free form object field: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));

		field = schema.getField("categories");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"String type was expected for arrays: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));
	}

	private void _assertValidProductSchema(Schema schema) {
		List<Schema.Field> fields = schema.getFields();

		Assert.assertThat(fields.size(), equalTo(48));

		Schema.Field field = schema.getField("active");

		Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"Boolean type was expected: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._boolean()));

		field = schema.getField("subscriptionConfiguration_enable");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"Boolean type was expected for nested field: ",
			AvroUtils.isSameType(fieldSchema, AvroUtils._boolean()));
	}

	private Schema _getSchema(String endpoint, String operation) {
		JsonObject endpointsJsonObject = _oasJsonObject.getJsonObject(
			OASConstants.PATHS);

		Assert.assertTrue(endpointsJsonObject.containsKey(endpoint));

		return _endpointSchemaInferrer.inferSchema(
			endpoint, operation, _oasJsonObject);
	}

	private final EndpointSchemaInferrer _endpointSchemaInferrer =
		new EndpointSchemaInferrer();
	private JsonObject _oasJsonObject;

}