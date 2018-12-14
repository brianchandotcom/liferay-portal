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

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.apio.test.util.ApioClientBuilder;
import com.liferay.site.apio.client.test.activator.WebSiteApioTestBundleActivator;

import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Before;
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
		_rootEndpointURL = new URL(_url, "/o/api");
	}

	@Test
	public void testWebSite() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "es-ES"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.web-site.href"
		).follow(
			"_embedded.WebSite.find { it.name == '" +
				WebSiteApioTestBundleActivator.WEB_SITE_NAME +
					"' }._links.self.href"
		).then(
		).statusCode(
			200
		).body(
			"availableLanguages", IsNull.notNullValue()
		).body(
			"description", IsNull.notNullValue()
		).body(
			"membershipType", IsNull.notNullValue()
		).body(
			"name", IsNull.notNullValue()
		).body(
			"privateUrl", IsNull.notNullValue()
		).body(
			"publicUrl", IsNull.notNullValue()
		).body(
			"_links.contentSpace.href", IsNull.notNullValue()
		).body(
			"_links.creator.href", IsNull.notNullValue()
		).body(
			"_links.embeddedWebPages.href", IsNull.notNullValue()
		).body(
			"_links.self.href", IsNull.notNullValue()
		).body(
			"_links.webSites.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testWebSiteExists() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.web-site.href"
		).then(
		).statusCode(
			200
		).body(
			"_embedded.WebSite.find {it.name == '" +
				WebSiteApioTestBundleActivator.WEB_SITE_NAME +
					"'}._links.self.href",
			IsNull.notNullValue()
		);
	}

	@Test
	public void testWebSiteLinkExistsInRootEndpoint() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).then(
		).statusCode(
			200
		).body(
			"_links.web-site.href", IsNull.notNullValue()
		);
	}

	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}