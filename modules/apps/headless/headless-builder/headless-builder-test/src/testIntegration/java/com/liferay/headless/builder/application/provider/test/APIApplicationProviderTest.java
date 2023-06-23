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

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
@FeatureFlags({"LPS-153117", "LPS-167253", "LPS-184413"})
public class APIApplicationProviderTest extends BaseTestCase {

	@Test
	public void test() throws Exception {
		HTTPTestUtil.invoke(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				JSONUtil.put(
					JSONUtil.put(
						"description", "description"
					).put(
						"externalReferenceCode", _API_ENDPOINT_ERC
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
						"externalReferenceCode", _API_SCHEMA_ERC
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
				"externalReferenceCode", _API_APPLICATION_ERC
			).put(
				"title", "title"
			).toString(),
			"headless-builder/applications", Http.Method.POST);
		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				_API_SCHEMA_ERC, "/requestAPISchemaToAPIEndpoints/",
				_API_ENDPOINT_ERC),
			Http.Method.PUT);
		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				_API_SCHEMA_ERC, "/responseAPISchemaToAPIEndpoints/",
				_API_ENDPOINT_ERC),
			Http.Method.PUT);

		APIApplication apiApplication =
			_apiApplicationProvider.fetchAPIApplication(
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
		Assert.assertEquals(
			APIApplication.Property.Type.PICKLIST, property.getType());
	}

	private static final String _API_APPLICATION_ERC =
		RandomTestUtil.randomString();

	private static final String _API_ENDPOINT_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_ERC = RandomTestUtil.randomString();

	@Inject
	private APIApplicationProvider _apiApplicationProvider;

}