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

import com.liferay.talend.BaseTest;
import com.liferay.talend.common.oas.constants.OASConstants;

import java.util.List;

import javax.json.JsonObject;

import org.apache.avro.Schema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.talend.daikon.avro.AvroUtils;

/**
 * @author Zoltán Takács
 */
public class EndpointSchemaInferrerTest extends BaseTest {

	@Before
	public void setUp() {
		if (_oasJsonObject != null) {
			return;
		}

		_oasJsonObject = readObject("openapi.json");
	}

	@Test
	public void testBooleanSchemaFieldsForProducts() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, OASConstants.OPERATION_POST);

		_assertValidProductSchema(schema);
	}

	@Test
	public void testInferSchemaForDeleteOperation() {
		String endpoint =
			"/v1.0/products/by-externalReferenceCode/{externalReferenceCode}";

		Schema schema = _getSchema(endpoint, OASConstants.OPERATION_DELETE);

		Assert.assertFalse(AvroUtils.isSchemaEmpty(schema));
	}

	@Test
	public void testInferSchemaForGetOperation() {
		String endpoint = "/v1.0/catalogs/{siteId}/products";

		Schema schema = _getSchema(endpoint, OASConstants.OPERATION_GET);

		Assert.assertFalse(AvroUtils.isSchemaEmpty(schema));

		_assertValidProductSchema(schema);
	}

	@Test
	public void testInferSchemaForInsertOperation() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, OASConstants.OPERATION_POST);

		Assert.assertFalse(AvroUtils.isSchemaEmpty(schema));
	}

	@Test
	public void testInferSchemaForUpdateOperation() {
		String endpoint =
			"/v1.0/products/by-externalReferenceCode/{externalReferenceCode}";

		Schema schema = _getSchema(endpoint, OASConstants.OPERATION_PATCH);

		Assert.assertFalse(AvroUtils.isSchemaEmpty(schema));
	}

	@Test
	public void testIntegerSchemaFieldsForProducts() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, OASConstants.OPERATION_POST);

		List<Schema.Field> fields = schema.getFields();

		Assert.assertThat(fields.size(), equalTo(48));

		Schema.Field field = schema.getField("configuration_minStockQuantity");

		Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS integer in nested object maps to AVRO integer",
			AvroUtils.isSameType(fieldSchema, AvroUtils._int()));
	}

	@Test
	public void testLongSchemaFieldsForProducts() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, OASConstants.OPERATION_POST);

		List<Schema.Field> fields = schema.getFields();

		Assert.assertThat(fields.size(), equalTo(48));

		Schema.Field field = schema.getField("id");

		Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS long maps to AVRO long",
			AvroUtils.isSameType(fieldSchema, AvroUtils._long()));

		field = schema.getField("subscriptionConfiguration_numberOfLength");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS long in nested object maps to AVRO long",
			AvroUtils.isSameType(fieldSchema, AvroUtils._long()));
	}

	@Test
	public void testOpenAPISpecification() {
		Assert.assertTrue("Test", _oasJsonObject.containsKey("openapi"));
	}

	@Test
	public void testStringSchemaFieldsForProducts() {
		String endpoint = "/v1.0/catalogs/{siteId}/product";

		Schema schema = _getSchema(endpoint, OASConstants.OPERATION_POST);

		List<Schema.Field> fields = schema.getFields();

		Assert.assertThat(fields.size(), equalTo(48));

		Schema.Field field = schema.getField("productType");

		Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS string maps to AVRO string",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));

		field = schema.getField("taxConfiguration_taxCategory");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS string in nested object maps to AVRO string",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));

		field = schema.getField("description");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS dictionary maps to AVRO string",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));

		field = schema.getField("expando");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS free form object maps to AVRO string",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));

		field = schema.getField("categories");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS string maps to AVRO string",
			AvroUtils.isSameType(fieldSchema, AvroUtils._string()));
	}

	private void _assertValidProductSchema(Schema schema) {
		List<Schema.Field> fields = schema.getFields();

		Assert.assertThat(fields.size(), equalTo(48));

		Schema.Field field = schema.getField("active");

		Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS boolean maps to AVRO boolean",
			AvroUtils.isSameType(fieldSchema, AvroUtils._boolean()));

		field = schema.getField("subscriptionConfiguration_enable");
		fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS boolean in nested object maps to AVRO boolean",
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