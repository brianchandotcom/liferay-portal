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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.Map;
import java.util.Set;

import org.apache.http.util.EntityUtils;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class EmbeddedElasticsearchConnectionHttpTest {

	@Before
	public void setUp() throws Exception {
		setUpJSONFactoryUtil();

		MockitoAnnotations.initMocks(this);

		_clusterName = RandomTestUtil.randomString();

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"clusterName", _clusterName
		).put(
			"networkHost", "_site_"
		).build();

		_elasticsearchFixture = new ElasticsearchFixture(
			EmbeddedElasticsearchConnectionHttpTest.class.getSimpleName(),
			properties);

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testHttpLocallyAvailableRegardlessOfNetworkHost()
		throws Exception {

		String status = toString(new URL("http://localhost:" + getHttpPort()));

		Assert.assertThat(
			status,
			CoreMatchers.containsString(
				"\"cluster_name\" : \"" + _clusterName));
	}

	protected int getHttpPort() throws Exception {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		RestClient restClient = restHighLevelClient.getLowLevelClient();

		String endpoint = "/_nodes";

		Request request = new Request("GET", endpoint);

		Response response = restClient.performRequest(request);

		String responseBody = EntityUtils.toString(response.getEntity());

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
			responseBody);

		JSONObject nodesJSONObject = responseJSONObject.getJSONObject("nodes");

		Set<String> nodes = nodesJSONObject.keySet();

		for (String node : nodes) {
			JSONObject nodeJSONObject = nodesJSONObject.getJSONObject(node);

			JSONObject settingsJSONObject = nodeJSONObject.getJSONObject(
				"settings");

			JSONObject httpJSONObject = settingsJSONObject.getJSONObject(
				"http");

			return httpJSONObject.getInt("port");
		}

		return 0;
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(_jsonFactory);
	}

	protected String toString(URL url) throws Exception {
		try (InputStream inputStream = url.openStream()) {
			return StringUtil.read(inputStream);
		}
	}

	private String _clusterName;
	private ElasticsearchFixture _elasticsearchFixture;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}