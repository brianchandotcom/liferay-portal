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

package com.liferay.mule.internal.oas;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matija Petanjek
 */
public class OASURLParserTest {

	@Test
	public void testGetAuthorityWithScheme() throws MalformedURLException {
		OASURLParser oasURLParser = new OASURLParser(_OPEN_API_URL);

		Assert.assertEquals(
			"http://localhost:8080", oasURLParser.getAuthorityWithScheme());
	}

	@Test
	public void testGetHost() throws MalformedURLException {
		OASURLParser oasURLParser = new OASURLParser(_OPEN_API_URL);

		Assert.assertEquals("localhost", oasURLParser.getHost());
	}

	@Test
	public void testGetJaxRSAppBase() throws MalformedURLException {
		OASURLParser oasURLParser = new OASURLParser(_OPEN_API_URL);

		Assert.assertEquals(
			"headless-commerce-admin-catalog", oasURLParser.getJaxRSAppBase());
	}

	@Test
	public void testGetPort() throws MalformedURLException {
		OASURLParser oasURLParser = new OASURLParser(_OPEN_API_URL);

		Assert.assertEquals("8080", oasURLParser.getPort());
	}

	@Test
	public void testGetScheme() throws MalformedURLException {
		OASURLParser oasURLParser = new OASURLParser(_OPEN_API_URL);

		Assert.assertEquals("http", oasURLParser.getScheme());
	}

	@Test
	public void testGetServerBaseURL() throws MalformedURLException {
		OASURLParser oasURLParser = new OASURLParser(_OPEN_API_URL);

		Assert.assertEquals(
			"http://localhost:8080/o/headless-commerce-admin-catalog",
			oasURLParser.getServerBaseURL());
	}

	@Test(expected = MalformedURLException.class)
	public void testOASURLParserWithMalformedURL()
		throws MalformedURLException {

		new OASURLParser(
			"http://localhost:8080/o/headless-commerce-admin-catalog/1.0" +
				"/openapi.json");
	}

	private static final String _OPEN_API_URL =
		"http://localhost:8080/o/headless-commerce-admin-catalog/v1.0" +
			"/openapi.json";

}