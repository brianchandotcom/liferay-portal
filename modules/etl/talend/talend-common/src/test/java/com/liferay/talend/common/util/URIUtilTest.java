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

package com.liferay.talend.common.util;

import com.liferay.talend.common.exception.MalformedURLException;

import java.net.URI;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class URIUtilTest {

	@Test
	public void testUpdateWithQueryParameters() {
		String url = "https://localhost:1977/o/headless/v1.0/lcs-cluster-nodes";

		Map<String, String> parameters = new HashMap<>();

		parameters.put("archive", "true");
		parameters.put("key", "197797");
		parameters.put("subscription", "true");

		URI uri = URIUtil.updateWithQueryParameters(url, parameters);

		String uriString = uri.toString();

		Assert.assertTrue(
			"URI has archive query parameter",
			uriString.contains("archive=true"));
		Assert.assertTrue(
			"URI has archive key parameter", uriString.contains("key=197797"));
		Assert.assertTrue(
			"URI has archive subscription parameter",
			uriString.contains("subscription=true"));
	}

	@Test(expected = MalformedURLException.class)
	public void testValidateIfOpenAPISpecURLIsInvalid() {
		URIUtil.validateOpenAPISpecURL("http://localhost:8080/o/test/wrong");
	}

	@Test(expected = MalformedURLException.class)
	public void testValidateIfOpenAPISpecURLIsNull() {
		URIUtil.validateOpenAPISpecURL(null);
	}

	@Test
	public void testValidateIfOpenAPISpecURLIsValid() {
		URIUtil.validateOpenAPISpecURL(
			"http://localhost:8080/o/headless/v1.0/openapi.json");
	}

}