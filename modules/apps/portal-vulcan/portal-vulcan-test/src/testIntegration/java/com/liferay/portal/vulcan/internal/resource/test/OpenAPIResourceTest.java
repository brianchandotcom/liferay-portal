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

package com.liferay.portal.vulcan.internal.resource.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.internal.test.util.URLConnectionUtil;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class OpenAPIResourceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetOpenAPIServerURL() throws Exception {
		InputStream inputStream = URLConnectionUtil.getInputStream(
			"http://localhost:8080/o/headless-delivery/v1.0/openapi.json");

		String path = _getPath(inputStream, "/servers/0/url");

		Assert.assertTrue(path.startsWith("http://localhost:8080/"));

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"WEB_SERVER_PROTOCOL", "https")) {

			inputStream = URLConnectionUtil.getInputStream(
				"http://localhost:8080/o/headless-delivery/v1.0/openapi.json");

			path = _getPath(inputStream, "/servers/0/url");

			Assert.assertTrue(path.startsWith("https://localhost:8080/"));
		}
	}

	private String _getPath(InputStream inputStream, String path)
		throws Exception {

		JsonNode jsonNode = _objectMapper.readTree(inputStream);

		jsonNode = jsonNode.at(path);

		return jsonNode.asText();
	}

	private final ObjectMapper _objectMapper = new ObjectMapper();

}