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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.talend.openapi.constants.OpenAPIConstants;

import java.io.InputStream;

import java.util.List;

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
		if (_openAPISpecJsonNode != null) {
			return;
		}

		Class<EndpointSchemaInferrerTest> endpointSchemaInferrerTestClass =
			EndpointSchemaInferrerTest.class;

		InputStream resourceAsStream =
			endpointSchemaInferrerTestClass.getResourceAsStream("openapi.json");

		_openAPISpecJsonNode = _objectMapper.readTree(resourceAsStream);
	}

	@Test
	public void testBooleanSchemaFieldsForProducts() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, HttpMethod.POST);

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
		Assert.assertTrue("Test", _openAPISpecJsonNode.has("openapi"));
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

	private Schema _getSchema(String endpoint, String operation) {
		JsonNode endpointsJsonNode = _openAPISpecJsonNode.path(
			OpenAPIConstants.PATHS);

		Assert.assertTrue(endpointsJsonNode.has(endpoint));

		return EndpointSchemaInferrer.inferSchema(
			endpoint, operation, _openAPISpecJsonNode);
	}

	private final ObjectMapper _objectMapper = new ObjectMapper();
	private JsonNode _openAPISpecJsonNode;

}