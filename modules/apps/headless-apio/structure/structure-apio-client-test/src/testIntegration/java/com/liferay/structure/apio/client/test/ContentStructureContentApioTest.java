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

package com.liferay.structure.apio.client.test;

import com.jayway.jsonpath.JsonPath;

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;
import com.liferay.structure.apio.client.test.internal.activator.ContentStructureApioTestBundleActivator;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunAsClient
@RunWith(Arquillian.class)
public class ContentStructureContentApioTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			ContentStructureApioTestBundleActivator.class);
	}

	@Before
	public void setUp() throws MalformedURLException {
		_rootEndpointURL = new URL(_url, "/o/api");
	}

	@Test
	public void testBooleanFieldDataTypeIsDisplayed() throws Exception {
		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				ContentStructureApioTestBundleActivator.SITE_NAME +
					"')]._links.contentStructures.href");

		Map<String, String> headers = _getHeaders();

		headers.put("Accept-Language", "en-US");

		List<String> dataTypes = JsonPath.read(
			_toStringAsGuest(hrefs.get(0), headers),
			"$._embedded.Structure[0]._embedded.formPages._embedded[0]." +
				"_embedded.fields._embedded[?(@.name=='MyBoolean')].dataType");

		Assert.assertEquals(dataTypes.toString(), 1, dataTypes.size());
		Assert.assertTrue(dataTypes.contains("boolean"));
	}

	@Test
	public void testBooleanFieldInputControlIsDisplayed() throws Exception {
		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				ContentStructureApioTestBundleActivator.SITE_NAME +
					"')]._links.contentStructures.href");

		Map<String, String> headers = _getHeaders();

		headers.put("Accept-Language", "en-US");

		List<String> inputControls = JsonPath.read(
			_toStringAsGuest(hrefs.get(0), headers),
			"$._embedded.Structure[0]._embedded.formPages._embedded[0]." +
				"_embedded.fields._embedded[?(@.name=='MyBoolean')]." +
					"inputControl");

		Assert.assertEquals(inputControls.toString(), 1, inputControls.size());
		Assert.assertTrue(inputControls.contains("checkbox"));
	}

	private JSONWebServiceClient _getGuestJSONWebServiceClient() {
		JSONWebServiceClient jsonWebServiceClient =
			new JSONWebServiceClientImpl();

		jsonWebServiceClient.setHostName(_rootEndpointURL.getHost());
		jsonWebServiceClient.setHostPort(_rootEndpointURL.getPort());
		jsonWebServiceClient.setProtocol(_rootEndpointURL.getProtocol());

		return jsonWebServiceClient;
	}

	private Map<String, String> _getHeaders() {
		return new HashMap<String, String>() {
			{
				put("Accept", "application/hal+json");
			}
		};
	}

	private JSONWebServiceClient _getJSONWebServiceClient(
		String login, String password) {

		JSONWebServiceClient jsonWebServiceClient =
			_jsonWebServiceClientMap.get(login);

		if (jsonWebServiceClient == null) {
			jsonWebServiceClient = _getGuestJSONWebServiceClient();

			jsonWebServiceClient.setLogin(login);
			jsonWebServiceClient.setPassword(password);

			_jsonWebServiceClientMap.put(login, jsonWebServiceClient);
		}

		return jsonWebServiceClient;
	}

	private String _toString(
			JSONWebServiceClient jsonWebServiceClient, String url)
		throws Exception {

		return _toString(
			jsonWebServiceClient, url,
			Collections.singletonMap("Accept", "application/hal+json"));
	}

	private String _toString(
			JSONWebServiceClient jsonWebServiceClient, String url,
			Map<String, String> headers)
		throws Exception {

		return jsonWebServiceClient.doGet(url, Collections.emptyMap(), headers);
	}

	private String _toStringAsAdmin(String url) throws Exception {
		return _toStringAsUser(url, "test@liferay.com", "test");
	}

	private String _toStringAsGuest(String url, Map<String, String> headers)
		throws Exception {

		return _toString(_getGuestJSONWebServiceClient(), url, headers);
	}

	private String _toStringAsUser(String url, String login, String password)
		throws Exception {

		JSONWebServiceClient jsonWebServiceClient = _getJSONWebServiceClient(
			login, password);

		return _toString(jsonWebServiceClient, url);
	}

	private final Map<String, JSONWebServiceClient> _jsonWebServiceClientMap =
		new HashMap<>();
	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}