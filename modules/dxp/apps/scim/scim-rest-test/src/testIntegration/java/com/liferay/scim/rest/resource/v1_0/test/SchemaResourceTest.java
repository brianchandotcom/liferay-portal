/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.scim.rest.client.http.HttpInvoker;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Olivér Kecskeméty
 */
@RunWith(Arquillian.class)
public class SchemaResourceTest extends BaseSchemaResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.scim.rest.internal.configuration." +
				"ScimClientOAuth2ApplicationConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"matcherField", "email"
			).put(
				"oAuth2ApplicationName", "scim-client-test"
			).put(
				"userId", TestPropsValues.getUserId()
			).build());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_pid);
	}

	@Override
	@Test
	public void testGetV2SchemaById() throws Exception {
		assertHttpResponseStatusCode(
			404,
			schemaResource.getV2SchemaByIdHttpResponse(
				RandomTestUtil.randomString()));

		for (String schema : _schemas) {
			HttpInvoker.HttpResponse httpResponse =
				schemaResource.getV2SchemaByIdHttpResponse(schema);

			assertHttpResponseStatusCode(200, httpResponse);

			JSONObject schemaJSONObject = _jsonFactory.createJSONObject(
				httpResponse.getContent());

			Assert.assertEquals(schema, schemaJSONObject.getString("id"));

			JSONArray schemasJSONArray = schemaJSONObject.getJSONArray(
				"schemas");

			Assert.assertEquals(
				"urn:ietf:params:scim:schemas:core:2.0:Schema",
				schemasJSONArray.get(0));
		}
	}

	@Override
	@Test
	public void testGetV2Schemas() throws Exception {
		HttpInvoker.HttpResponse httpResponse =
			schemaResource.getV2SchemasHttpResponse();

		Assert.assertEquals(200, httpResponse.getStatusCode());

		JSONObject listResponseJSONObject = _jsonFactory.createJSONObject(
			httpResponse.getContent());

		JSONArray schemasJSONArray = listResponseJSONObject.getJSONArray(
			"schemas");

		Assert.assertEquals(
			"urn:ietf:params:scim:api:messages:2.0:ListResponse",
			schemasJSONArray.get(0));

		Assert.assertEquals(3, listResponseJSONObject.getLong("totalResults"));
		Assert.assertEquals(3, listResponseJSONObject.getLong("itemsPerPage"));

		JSONArray resourcesJSONArray = listResponseJSONObject.getJSONArray(
			"Resources");

		Assert.assertEquals(3, resourcesJSONArray.length());

		Iterator<JSONObject> iterator = resourcesJSONArray.iterator();

		while (iterator.hasNext()) {
			JSONObject schemaJSONObject = iterator.next();

			Assert.assertTrue(
				_schemas.contains(schemaJSONObject.getString("id")));
		}
	}

	private static String _pid;

	@Inject
	private JSONFactory _jsonFactory;

	private final List<String> _schemas = List.of(
		"urn:ietf:params:scim:schemas:core:2.0:Group",
		"urn:ietf:params:scim:schemas:core:2.0:User",
		"urn:ietf:params:scim:schemas:extension:liferay:2.0:User");

}