/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.test.rule.Inject;
import com.liferay.scim.rest.client.http.HttpInvoker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

		_schemasStringList.add("urn:ietf:params:scim:schemas:core:2.0:User");
		_schemasStringList.add(
			"urn:ietf:params:scim:schemas:extension:liferay:2.0:User");
		_schemasStringList.add("urn:ietf:params:scim:schemas:core:2.0:Group");
	}

	@Override
	@Test
	public void testGetV2SchemaById() throws Exception {
		assertHttpResponseStatusCode(
			404, schemaResource.getV2SchemaByIdHttpResponse("12345"));

		for (String element : _schemasStringList) {
			_assertSchema(element);
		}
	}

	@Override
	@Test
	public void testGetV2Schemas() throws Exception {
		HttpInvoker.HttpResponse httpResponse =
			schemaResource.getV2SchemasHttpResponse();

		Assert.assertEquals(2, httpResponse.getStatusCode() / 100);

		JSONObject schemaDefinitionJSONObject = _jsonFactory.createJSONObject(
			httpResponse.getContent());

		assertEquals(3, schemaDefinitionJSONObject.getInt("totalResults"));

		JSONArray schemasParameterJSONArray =
			schemaDefinitionJSONObject.getJSONArray("schemas");

		assertEquals(
			"urn:ietf:params:scim:schemas:core:2.0:Schema",
			schemasParameterJSONArray.get(
				0
			).toString());

		JSONArray resourcesParameterJSONArray =
			schemaDefinitionJSONObject.getJSONArray("Resources");

		assertEquals(3, resourcesParameterJSONArray.length());

		Iterator<JSONObject> iterator = resourcesParameterJSONArray.iterator();

		while (iterator.hasNext()) {
			JSONObject schemaResponseJSONObject = iterator.next();

			assertEquals(
				true,
				_schemasStringList.contains(
					schemaResponseJSONObject.getString("id")));
		}
	}

	private void _assertSchema(String schemaId) throws Exception {
		HttpInvoker.HttpResponse httpResponse =
			schemaResource.getV2SchemaByIdHttpResponse(schemaId);

		assertHttpResponseStatusCode(200, httpResponse);

		JSONObject schemaDefinitionJSONObject = _jsonFactory.createJSONObject(
			httpResponse.getContent());

		assertEquals(schemaId, schemaDefinitionJSONObject.getString("id"));

		JSONArray schemasParameterJSONArray =
			schemaDefinitionJSONObject.getJSONArray("schemas");

		assertEquals(
			"urn:ietf:params:scim:schemas:core:2.0:Schema",
			schemasParameterJSONArray.get(
				0
			).toString());
	}

	@Inject
	private JSONFactory _jsonFactory;

	private final List<String> _schemasStringList = new ArrayList<>();

}