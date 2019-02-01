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

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.apio.test.util.ApioClientBuilder;
import com.liferay.portal.apio.test.util.ContentSpaceApioTestUtil;
import com.liferay.structured.content.apio.client.test.internal.activator.StructuredContentApioTestBundleActivator;

import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.Matchers;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
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
		URL rootEndpointURL = new URL(_url, "/o/api");

		_contentSpaceURL = new URL(
			ContentSpaceApioTestUtil.getContentSpaceHref(
				rootEndpointURL.toExternalForm(),
				StructuredContentApioTestBundleActivator.SITE_NAME));

		_structuredContentEndpointURL = new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).when(
			).get(
				_contentSpaceURL.toExternalForm()
			).then(
			).extract(
			).path(
				"_links.structuredContents.href"
			));
	}

	@After
	public void tearDown() throws Exception {
		ContentSpaceApioTestUtil.deleteAllStructuredContents(_contentSpaceURL);
	}

	@Test
	public void testStructuredContentsMatchesSelfLink() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_structuredContentEndpointURL.toExternalForm()
		).then(
		).statusCode(
			200
		).body(
			"_links.self.href",
			Matchers.startsWith(_structuredContentEndpointURL.toExternalForm())
		);
	}

	private URL _contentSpaceURL;
	private URL _structuredContentEndpointURL;

	@ArquillianResource
	private URL _url;

}