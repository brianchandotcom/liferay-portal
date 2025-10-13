/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.openapi.contributor.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.vulcan.internal.test.util.URLConnectionUtil;

import java.io.InputStream;

import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jan Brychta
 */
@RunWith(Arquillian.class)
public class FilterableFieldsOpenAPIContributorTest {

	@Test
	public void testXFilterableFields() throws Exception {
		String apiURL = new StringBuilder(
		).append(
			"http://localhost:8080"
		).append(
			"/o"
		).append(
			"/headless-commerce-delivery-order/v1.0/openapi.json"
		).toString();

		JsonNode schemasJsonNode = _getJsonNode(
			URLConnectionUtil.getInputStream(apiURL), "/components/schemas");

		Iterator<Map.Entry<String, JsonNode>> schemasNodeFieldsIterator =
			schemasJsonNode.fields();

		while (schemasNodeFieldsIterator.hasNext()) {
			Map.Entry<String, JsonNode> componentNode =
				schemasNodeFieldsIterator.next();

			JsonNode componentChildrenJsonNode = componentNode.getValue();

			Assert.assertNotNull(componentChildrenJsonNode);
			Assert.assertNotNull(componentChildrenJsonNode.get("x-filterable"));
		}
	}

	private JsonNode _getJsonNode(InputStream inputStream, String path)
		throws Exception {

		JsonNode jsonNode = _objectMapper.readTree(inputStream);

		return jsonNode.at(path);
	}

	private final ObjectMapper _objectMapper = new ObjectMapper();

}