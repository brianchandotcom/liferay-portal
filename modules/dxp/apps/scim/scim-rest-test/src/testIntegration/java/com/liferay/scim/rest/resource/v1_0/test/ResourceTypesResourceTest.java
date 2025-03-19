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
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class ResourceTypesResourceTest
	extends BaseResourceTypesResourceTestCase {

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
	public void testGetV2ResourceTypeById() throws Exception {
		assertHttpResponseStatusCode(
			404,
			resourceTypesResource.getV2ResourceTypeByIdHttpResponse(
				RandomTestUtil.randomString()));

		for (String resourceTypeId : _resourceTypeIds) {
			HttpInvoker.HttpResponse httpResponse =
				resourceTypesResource.getV2ResourceTypeByIdHttpResponse(
					resourceTypeId);

			assertHttpResponseStatusCode(200, httpResponse);

			JSONObject schemaJSONObject = _jsonFactory.createJSONObject(
				httpResponse.getContent());

			Assert.assertEquals(
				resourceTypeId, schemaJSONObject.getString("id"));

			JSONArray schemasJSONArray = schemaJSONObject.getJSONArray(
				"schemas");

			Assert.assertEquals(
				"urn:ietf:params:scim:schemas:core:2.0:ResourceType",
				schemasJSONArray.get(0));
		}

		ConfigurationTestUtil.deleteConfiguration(_pid);

		assertHttpResponseStatusCode(
			404,
			resourceTypesResource.getV2ResourceTypeByIdHttpResponse(
				RandomTestUtil.randomString()));
	}

	@Override
	@Test
	public void testGetV2ResourceTypes() throws Exception {
		HttpInvoker.HttpResponse httpResponse =
			resourceTypesResource.getV2ResourceTypesHttpResponse();

		Assert.assertEquals(200, httpResponse.getStatusCode());

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			httpResponse.getContent());

		JSONArray schemasJSONArray = jsonObject.getJSONArray("schemas");

		Assert.assertEquals(
			"urn:ietf:params:scim:api:messages:2.0:ListResponse",
			schemasJSONArray.get(0));

		Assert.assertEquals(2, jsonObject.getLong("totalResults"));
		Assert.assertEquals(2, jsonObject.getLong("itemsPerPage"));

		JSONArray resourcesJSONArray = jsonObject.getJSONArray("Resources");

		Assert.assertEquals(2, resourcesJSONArray.length());

		Iterator<JSONObject> iterator = resourcesJSONArray.iterator();

		while (iterator.hasNext()) {
			JSONObject schemaJSONObject = iterator.next();

			Assert.assertTrue(
				_schemaIds.contains(schemaJSONObject.getString("schema")));
		}

		ConfigurationTestUtil.deleteConfiguration(_pid);

		assertHttpResponseStatusCode(
			404, resourceTypesResource.getV2ResourceTypesHttpResponse());
	}

	private static String _pid;

	@Inject
	private JSONFactory _jsonFactory;

	private final List<String> _resourceTypeIds = List.of("Group", "User");
	private final List<String> _schemaIds = List.of(
		"urn:ietf:params:scim:schemas:core:2.0:Group",
		"urn:ietf:params:scim:schemas:core:2.0:User");

}