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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class LiferayServiceTest {

	@Test
	public void testGetReadableEndpoints() {
		LiferayService liferayService = new LiferayService();

		List<String> readableEndpoints = liferayService.getReadableEndpoints(
			_getOpenAPIJsonObject());

		Assert.assertFalse(
			"Pageable endpoints are empty", readableEndpoints.isEmpty());

		Assert.assertTrue(
			"Pageable endpoints contain path",
			readableEndpoints.contains("/test/path1"));
		Assert.assertTrue(
			"Pageable endpoints contain path",
			readableEndpoints.contains("/test/path2"));
	}

	@Test
	public void testIsValidEndpointURL() {
		LiferayService liferayService = new LiferayService();

		Assert.assertTrue(
			liferayService.isValidOpenAPISpecURL(
				"http://localhost:8080/o/test-endpoint/v1.0/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidOpenAPISpecURL(
				"https://localhost:8080/o/test-endpoint/v1.0/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidOpenAPISpecURL(
				"https://localhost.com/o/test-endpoint/v1.0/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidOpenAPISpecURL(
				"https://test.com/o/test-endpoint/v1/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidOpenAPISpecURL(
				"https://test.com/o/test-endpoint/v1.0.23/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidOpenAPISpecURL(
				"http://test.com:8080/o/test-endpoint/v1.0/openapi.json"));
	}

	private JsonObject _getOpenAPIJsonObject() {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

		objectBuilder.add("key1", "value1");
		objectBuilder.add("key2", "value2");

		objectBuilder.add(
			"components",
			Json.createObjectBuilder(
			).add(
				"schemas",
				Json.createObjectBuilder(
				).add(
					"TestEntityUno",
					Json.createObjectBuilder(
					).add(
						"key", "value"
					)
				).add(
					"TestEntityDue",
					Json.createObjectBuilder(
					).add(
						"key", "value"
					)
				).add(
					"TestEntity1",
					Json.createObjectBuilder(
					).add(
						"properties",
						Json.createObjectBuilder(
						).add(
							"items",
							Json.createObjectBuilder(
							).add(
								"type", "array"
							)
						).add(
							"page",
							Json.createObjectBuilder(
							).add(
								"type", "integer"
							)
						)
					)
				).add(
					"TestEntity2",
					Json.createObjectBuilder(
					).add(
						"properties",
						Json.createObjectBuilder(
						).add(
							"name",
							Json.createObjectBuilder(
							).add(
								"type", "string"
							).add(
								"format", "date"
							)
						).add(
							"id",
							Json.createObjectBuilder(
							).add(
								"type", "integer"
							).add(
								"format", "int64"
							)
						)
					)
				)
			));

		objectBuilder.add(
			"paths",
			Json.createObjectBuilder(
			).add(
				"/test/invalid/path/1",
				Json.createObjectBuilder(
				).add(
					"key", "value"
				)
			).add(
				"/test/invalid/path/3",
				Json.createObjectBuilder(
				).add(
					"key", "value"
				)
			).add(
				"/test/path1",
				Json.createObjectBuilder(
				).add(
					"get",
					Json.createObjectBuilder(
					).add(
						"responses",
						Json.createObjectBuilder(
						).add(
							"default",
							Json.createObjectBuilder(
							).add(
								"content",
								Json.createObjectBuilder(
								).add(
									"application/json",
									Json.createObjectBuilder(
									).add(
										"schema",
										Json.createObjectBuilder(
										).add(
											"$ref",
											"#/components/schemas/TestEntity1"
										)
									)
								)
							)
						)
					)
				)
			).add(
				"/test/path2",
				Json.createObjectBuilder(
				).add(
					"get",
					Json.createObjectBuilder(
					).add(
						"responses",
						Json.createObjectBuilder(
						).add(
							"default",
							Json.createObjectBuilder(
							).add(
								"content",
								Json.createObjectBuilder(
								).add(
									"application/json",
									Json.createObjectBuilder(
									).add(
										"schema",
										Json.createObjectBuilder(
										).add(
											"$ref",
											"#/components/schemas/TestEntity2"
										)
									)
								)
							)
						)
					)
				)
			));

		return objectBuilder.build();
	}

}