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

package com.liferay.headless.builder.application.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class APIApplicationProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAPIApplicationProvider() throws Exception {
		HTTPTestUtil.invoke(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				JSONUtil.put(
					JSONUtil.put(
						"description", "description"
					).put(
						"externalReferenceCode", "ENDPOINT"
					).put(
						"httpMethod", "get"
					).put(
						"name", "name"
					).put(
						"path", "path"
					).put(
						"scope", "company"
					))
			).put(
				"apiApplicationToAPISchemas",
				JSONUtil.put(
					JSONUtil.put(
						"apiSchemaToAPIProperties",
						JSONUtil.put(
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "name"
							).put(
								"objectFieldERC", "APPLICATION_STATUS"
							))
					).put(
						"description", "description"
					).put(
						"externalReferenceCode", "SCHEMA"
					).put(
						"mainObjectDefinitionERC", "MSOD_API_APPLICATION"
					).put(
						"name", "name"
					))
			).put(
				"applicationStatus", "published"
			).put(
				"baseURL", "test"
			).put(
				"title", "title"
			).toString(),
			"headless-builder/applications", Http.Method.POST);
		HTTPTestUtil.invoke(
			null,
			"headless-builder/schemas/by-external-reference-code/SCHEMA" +
				"/requestAPISchemaToAPIEndpoints/ENDPOINT",
			Http.Method.PUT);
		HTTPTestUtil.invoke(
			null,
			"headless-builder/schemas/by-external-reference-code/SCHEMA" +
				"/responseAPISchemaToAPIEndpoints/ENDPOINT",
			Http.Method.PUT);

		APIApplication apiApplication =
			_apiApplicationProvider.getAPIApplication(
				"test", TestPropsValues.getCompanyId());

		Assert.assertEquals("test", apiApplication.getBaseURL());

		List<APIApplication.Schema> schemas = apiApplication.getSchemas();

		Assert.assertEquals(schemas.toString(), 1, schemas.size());

		APIApplication.Schema schema = schemas.get(0);

		Assert.assertEquals("description", schema.getDescription());
		Assert.assertNotNull(schema.getExternalReferenceCode());
		Assert.assertEquals("name", schema.getName());

		List<APIApplication.Endpoint> endpoints = apiApplication.getEndpoints();

		Assert.assertEquals(endpoints.toString(), 1, endpoints.size());

		APIApplication.Endpoint endpoint = endpoints.get(0);

		Assert.assertEquals(Http.Method.GET, endpoint.getMethod());
		Assert.assertEquals("path", endpoint.getPath());
		Assert.assertEquals(schema, endpoint.getRequestSchema());
		Assert.assertEquals(schema, endpoint.getResponseSchema());
		Assert.assertEquals(
			APIApplication.Endpoint.Scope.COMPANY, endpoint.getScope());

		List<APIApplication.Property> properties = schema.getProperties();

		Assert.assertEquals(properties.toString(), 1, properties.size());

		APIApplication.Property property = properties.get(0);

		Assert.assertEquals("description", property.getDescription());
		Assert.assertEquals("name", property.getName());
		Assert.assertEquals("Picklist", property.getType());
	}

	@Inject
	private APIApplicationProvider _apiApplicationProvider;

}