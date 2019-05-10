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

package com.liferay.talend.service;

import java.util.List;
import java.util.Objects;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.talend.sdk.component.api.record.Schema;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;
import org.talend.sdk.component.runtime.record.RecordBuilderFactoryImpl;

/**
 * @author Matija Petanjek
 */
public class TalendServiceTest {

	@Before
	public void setUp() {
		RecordBuilderFactory recordBuilderFactory =
			new RecordBuilderFactoryImpl("");

		JsonObject schemaJsonObject = _getSchemaJsonObject();

		TalendService talendService = new TalendService();

		_schema = talendService.getTalendSchema(
			schemaJsonObject, recordBuilderFactory);
	}

	@Test
	public void testGetTalendSchemaIfOpenAPIRequiredJsonArrayPresent() {
		for (Schema.Entry entry : _schema.getEntries()) {
			if (Objects.equals(entry.getName(), "propertyName1") ||
				Objects.equals(entry.getName(), "propertyName3")) {

				Assert.assertFalse(entry.isNullable());
			}
			else {
				Assert.assertTrue(entry.isNullable());
			}
		}
	}

	@Test
	public void testGetTalendSchemaIfOpenApiTypeArray() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName7");

		Assert.assertEquals(Schema.Type.STRING, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeBoolean() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName10");

		Assert.assertEquals(Schema.Type.BOOLEAN, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeIntegerAndFormatInt32() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName2");

		Assert.assertEquals(Schema.Type.INT, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeIntegerAndFormatInt64() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName8");

		Assert.assertEquals(Schema.Type.LONG, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeNumberAndFormatDouble() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName5");

		Assert.assertEquals(Schema.Type.DOUBLE, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeNumberAndFormatFloat() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName9");

		Assert.assertEquals(Schema.Type.FLOAT, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeObject() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName1");

		Assert.assertEquals(Schema.Type.STRING, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeObjectWithAdditionalProperties() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName6");

		Assert.assertEquals(Schema.Type.STRING, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeString() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName3");

		Assert.assertEquals(Schema.Type.STRING, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeStringAndFormatBinary() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName11");

		Assert.assertEquals(Schema.Type.BYTES, entry.getType());
	}

	@Test
	public void testGetTalendSchemaIfOpenAPITypeStringAndFormatDateTime() {
		Schema.Entry entry = _getSchemaEntry(
			_schema.getEntries(), "propertyName4");

		Assert.assertEquals(Schema.Type.DATETIME, entry.getType());
	}

	private Schema.Entry _getSchemaEntry(
		List<Schema.Entry> schemaEntries, String name) {

		for (Schema.Entry schemaEntry : schemaEntries) {
			if (name.equals(schemaEntry.getName())) {
				return schemaEntry;
			}
		}

		return null;
	}

	private JsonObject _getSchemaJsonObject() {
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

		jsonObjectBuilder.add(
			"properties",
			Json.createObjectBuilder(
			).add(
				"propertyName1",
				Json.createObjectBuilder(
				).add(
					"type", "object"
				)
			).add(
				"propertyName2",
				Json.createObjectBuilder(
				).add(
					"type", "integer"
				).add(
					"format", "int32"
				)
			).add(
				"propertyName3",
				Json.createObjectBuilder(
				).add(
					"type", "string"
				)
			).add(
				"propertyName4",
				Json.createObjectBuilder(
				).add(
					"type", "string"
				).add(
					"format", "date-time"
				)
			).add(
				"propertyName5",
				Json.createObjectBuilder(
				).add(
					"type", "number"
				).add(
					"format", "double"
				).add(
					"readOnly", "true"
				)
			).add(
				"propertyName6",
				Json.createObjectBuilder(
				).add(
					"type", "object"
				).add(
					"additionalProperties",
					Json.createObjectBuilder(
					).add(
						"type", "string"
					)
				)
			).add(
				"propertyName7",
				Json.createObjectBuilder(
				).add(
					"type", "array"
				).add(
					"items",
					Json.createObjectBuilder(
					).add(
						"$ref", "#/components/schemas/propertyName1"
					)
				)
			).add(
				"propertyName8",
				Json.createObjectBuilder(
				).add(
					"type", "integer"
				).add(
					"format", "int64"
				)
			).add(
				"propertyName9",
				Json.createObjectBuilder(
				).add(
					"type", "number"
				).add(
					"format", "float"
				)
			).add(
				"propertyName10",
				Json.createObjectBuilder(
				).add(
					"type", "boolean"
				)
			).add(
				"propertyName11",
				Json.createObjectBuilder(
				).add(
					"type", "string"
				).add(
					"format", "binary"
				)
			));

		jsonObjectBuilder.add(
			"required",
			Json.createArrayBuilder(
			).add(
				"propertyName1"
			).add(
				"propertyName3"
			));

		return jsonObjectBuilder.build();
	}

	private Schema _schema;

}