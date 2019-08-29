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
 * @author Igor Beslic
 */
public class SchemaBuilderTest extends BaseTest {

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

		Assert.assertThat(fields.size(), equalTo(26));

		Schema.Field field = schema.getField("configuration");

		Schema nestedSchema = AvroUtils.unwrapIfNullable(field.schema());

		Schema.Field fieldSchema = nestedSchema.getField("minStockQuantity");

		Assert.assertTrue(
			"OAS integer in nested object maps to AVRO integer",
			AvroUtils.isSameType(
				AvroUtils.unwrapIfNullable(fieldSchema.schema()),
				AvroUtils._int()));
	}

	private void _assertValidProductSchema(Schema schema) {
		List<Schema.Field> fields = schema.getFields();

		Assert.assertThat(fields.size(), equalTo(26));

		Schema.Field field = schema.getField("active");

		Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

		Assert.assertTrue(
			"OAS boolean maps to AVRO boolean type",
			AvroUtils.isSameType(fieldSchema, AvroUtils._boolean()));

		Schema.Field nestedField = schema.getField("subscriptionConfiguration");

		Schema nestedFieldSchema = AvroUtils.unwrapIfNullable(
			nestedField.schema());

		Schema.Field enabledField = nestedFieldSchema.getField("enable");

		Assert.assertTrue(
			"OAS boolean maps to AVRO boolean type",
			AvroUtils.isSameType(
				AvroUtils.unwrapIfNullable(enabledField.schema()),
				AvroUtils._boolean()));
	}

	private Schema _getSchema(String endpoint, String operation) {
		SchemaBuilder schemaBuilder = new SchemaBuilder();

		JsonObject endpointsJsonObject = _oasJsonObject.getJsonObject(
			OASConstants.PATHS);

		Assert.assertTrue(endpointsJsonObject.containsKey(endpoint));

		return schemaBuilder.build(endpoint, operation, _oasJsonObject);
	}

	private JsonObject _oasJsonObject;

}