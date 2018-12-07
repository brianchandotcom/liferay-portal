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

package com.liferay.site.apio.client.test;

import com.jayway.jsonpath.JsonPath;

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;
import com.liferay.site.apio.client.test.activator.WebSiteApioTestBundleActivator;

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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunAsClient
@RunWith(Arquillian.class)
public class WebSiteApioTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			WebSiteApioTestBundleActivator.class);
	}

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

	@Ignore
	@Test
	public void testWebSiteLiferayExists() throws Exception {
		List<String> hrefs = JsonPath.read(
			_toString(
				JsonPath.read(
					_toString(_rootEndpointURL.toExternalForm()),
					"$._links.web-site.href")),
			"$._embedded.WebSite[?(@.name == 'Liferay')]");

		Assert.assertNotNull(hrefs.get(0));
	}

	@Test
	public void testWebSiteLinkExistsInRootEndpoint() throws Exception {
		Assert.assertNotNull(
			JsonPath.read(
				_toString(_rootEndpointURL.toExternalForm()),
				"$._links.web-site.href"));
	}

	private String _toString(String url) throws Exception {
		return _jsonWebServiceClient.doGet(
			url, Collections.emptyMap(),
			Collections.singletonMap("Accept", "application/hal+json"));
	}

	private JSONWebServiceClient _jsonWebServiceClient;
	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}