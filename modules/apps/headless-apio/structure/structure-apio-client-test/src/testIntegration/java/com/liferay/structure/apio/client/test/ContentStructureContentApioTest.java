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

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.apio.test.util.ApioClientBuilder;
import com.liferay.structure.apio.client.test.internal.activator.ContentStructureApioTestBundleActivator;

import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.Matchers;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

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
		URL rootEndpointURL = new URL(_url, "/o/api");

		_contentStructureEndpointURL = new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).when(
			).get(
				rootEndpointURL.toExternalForm()
			).follow(
				"_links.content-space.href"
			).then(
			).extract(
			).path(
				"_embedded.ContentSpace.find {it.name == '" +
					ContentStructureApioTestBundleActivator.SITE_NAME +
						"'}._links.contentStructures.href"
			));
	}

	@Test
	public void testGetContentStructure() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_contentStructureEndpointURL.toExternalForm()
		).then(
		).statusCode(
			200
		).root(
			"_embedded.Structure[0]._embedded.formPages._embedded[0]." +
				"_embedded.fields._embedded.find {it.name = 'MyBoolean'}"
		).body(
			"dataType", Matchers.equalTo("boolean")
		).body(
			"inputControl", Matchers.equalTo("checkbox")
		);
	}

	private URL _contentStructureEndpointURL;

	@ArquillianResource
	private URL _url;

}