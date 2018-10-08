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

package com.liferay.structured.content.apio.client.test;

import com.jayway.jsonpath.JsonPath;

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;
import com.liferay.structured.content.apio.client.test.activator.StructuredContentApioTestBundleActivator;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.List;

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
 * @author Ruben Pulido
 */
@RunAsClient
@RunWith(Arquillian.class)
public class StructuredContentApioTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			StructuredContentApioTestBundleActivator.class);
	}

	@Before
	public void setUp() throws MalformedURLException {
		_rootEndpointURL = new URL(_url, "/o/api");
	}

	@Test
	public void testAdminUserSeeAllStructuredContents() throws Exception {
		JSONWebServiceClient jsonWebServiceClient = _getJSONWebServiceClient(
			"test@liferay.com");

		List<String> hrefs = JsonPath.read(
			_get(
				JsonPath.read(
					_get(
						_rootEndpointURL.toExternalForm(),
						jsonWebServiceClient),
					"$._links.content-space.href"),
				jsonWebServiceClient),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		List<String> titles = JsonPath.read(
			_get(hrefs.get(0), jsonWebServiceClient),
			"$._embedded.StructuredContent[*].title");

		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_NO_GROUP));
		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_YES_GROUP));
		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_YES_GUEST_YES_GROUP));
	}



	}

	@Test
	public void testStructuredContentsExistsInContentSpaceEndpoint()
		throws Exception {

		JSONWebServiceClient jsonWebServiceClient = _getJSONWebServiceClient();

		List<String> hrefs = JsonPath.read(
			_get(
				JsonPath.read(
					_get(
						_rootEndpointURL.toExternalForm(),
						jsonWebServiceClient),
					"$._links.content-space.href"),
				jsonWebServiceClient),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		Assert.assertNotNull(hrefs.get(0));
	}

	@Test
	public void testStructuredContentsMatchesSelfLink() throws Exception {
		JSONWebServiceClient jsonWebServiceClient = _getJSONWebServiceClient();

		List<String> hrefs = JsonPath.read(
			_get(
				JsonPath.read(
					_get(
						_rootEndpointURL.toExternalForm(),
						jsonWebServiceClient),
					"$._links.content-space.href"),
				jsonWebServiceClient),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		String href = JsonPath.read(
			_get(hrefs.get(0), jsonWebServiceClient), "$._links.self.href");

		Assert.assertTrue(href.startsWith(hrefs.get(0)));
	}

	private String _get(String url, JSONWebServiceClient jsonWebServiceClient)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return jsonWebServiceClient.doGet(
			url, Collections.emptyMap(),
			Collections.singletonMap("Accept", "application/hal+json"));
	}

	private JSONWebServiceClient _getJSONWebServiceClient() {
		JSONWebServiceClient jsonWebServiceClient =
			new JSONWebServiceClientImpl();

		jsonWebServiceClient.setHostName(_rootEndpointURL.getHost());
		jsonWebServiceClient.setHostPort(_rootEndpointURL.getPort());
		jsonWebServiceClient.setProtocol(_rootEndpointURL.getProtocol());

		return jsonWebServiceClient;
	}

	private JSONWebServiceClient _getJSONWebServiceClient(String login) {
		JSONWebServiceClient jsonWebServiceClient = _getJSONWebServiceClient();

		jsonWebServiceClient.setLogin(login);
		jsonWebServiceClient.setPassword("test");

		return jsonWebServiceClient;
	}

	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}