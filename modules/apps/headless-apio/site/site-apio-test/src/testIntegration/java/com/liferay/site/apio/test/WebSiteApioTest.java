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

package com.liferay.site.apio.test;

import com.jayway.jsonpath.JsonPath;

import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.List;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunAsClient
@RunWith(Arquillian.class)
public class WebSiteApioTest {

	@Before
	public void setUp() throws MalformedURLException {
		_jsonWebServiceClient = new JSONWebServiceClientImpl();

		_rootEndpointURL = new URL(_url, "/o/api");

		_jsonWebServiceClient.setHostName(_rootEndpointURL.getHost());
		_jsonWebServiceClient.setHostPort(_rootEndpointURL.getPort());

		_jsonWebServiceClient.setLogin("test@liferay.com");
		_jsonWebServiceClient.setPassword("test");
		_jsonWebServiceClient.setProtocol(_rootEndpointURL.getProtocol());
	}

	@Test
	public void testWebSiteLiferayExists() throws Exception {
		List<String> hrefs = JsonPath.read(
			_get(
				JsonPath.read(
					_get(_rootEndpointURL.toExternalForm()),
					"$._links.web-site.href")),
			"$._embedded.WebSite[?(@.name == 'Liferay')]");

		Assert.assertNotNull(hrefs.get(0));
	}

	@Test
	public void testWebSiteLinkExistsInRootEndpoint() throws Exception {
		Assert.assertNotNull(
			JsonPath.read(
				_get(_rootEndpointURL.toExternalForm()),
				"$._links.web-site.href"));
	}

	private String _get(String url)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		return _jsonWebServiceClient.doGet(
			url, Collections.emptyMap(),
			Collections.singletonMap("Accept", "application/hal+json"));
	}

	private JSONWebServiceClient _jsonWebServiceClient;
	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}