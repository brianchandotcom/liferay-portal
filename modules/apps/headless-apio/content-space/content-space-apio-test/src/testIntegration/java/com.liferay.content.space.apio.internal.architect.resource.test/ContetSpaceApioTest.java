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

package com.liferay.content.space.apio.internal.architect.resource.test;

import io.restassured.RestAssured;

import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunAsClient
@RunWith(Arquillian.class)
public class ContetSpaceApioTest {

	@Before
	public void setUp() throws MalformedURLException {
		_rootEndpointURL = new URL(_url, "/o/api");
	}

	@Test
	public void testContentSpaceLiferayExists() {
		RestAssured.given(
		).auth(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			(String)RestAssured.given(
			).auth(
			).basic(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).when(
			).get(
				_rootEndpointURL.toExternalForm()
			).then(
			).log(
			).ifError(
			).extract(
			).path(
				"_links.content-space.href"
			)
		).then(
		).body(
			"_embedded.ContentSpace.find { it.name == 'Liferay' }",
			IsNull.notNullValue()
		).log(
		).ifError();
	}

	@Test
	public void testContentSpaceLinkExistsInRootEndpoint() {
		RestAssured.given(
		).auth(
		).basic(
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
			"_links.content-space.href", IsNull.notNullValue()
		).log(
		).ifError();
	}

	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}