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

package com.liferay.lcs.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Igor Beslic
 */
@RunWith(PowerMockRunner.class)
public class RESTErrorTest extends PowerMockito {

	@Test
	public void testToJSON() throws Exception {
		String restErrorJSONString =
			RESTError.NO_SUCH_LCS_SUBSCRIPTION_ENTRY.toJSON(
				"Test Message", 400, "arg1", "10000", "arg2", 20000);

		ObjectMapper objectMapper = new ObjectMapper();

		ObjectNode objectNode = objectMapper.readValue(
			restErrorJSONString, ObjectNode.class);

		Assert.assertTrue(objectNode.has("args"));
		Assert.assertTrue(objectNode.has("errorCode"));
		Assert.assertTrue(objectNode.has("message"));
		Assert.assertTrue(objectNode.has("status"));

		JsonNode errorCodeJsonNode = objectNode.get("errorCode");

		Assert.assertEquals(
			RESTError.NO_SUCH_LCS_SUBSCRIPTION_ENTRY.getErrorCode(),
			errorCodeJsonNode.asInt());

		JsonNode statusJsonNode = objectNode.get("status");

		Assert.assertEquals(400, statusJsonNode.asInt());

		restErrorJSONString = RESTError.NO_SUCH_LCS_SUBSCRIPTION_ENTRY.toJSON(
			"Test Message", 404);

		objectNode = objectMapper.readValue(
			restErrorJSONString, ObjectNode.class);

		Assert.assertFalse(objectNode.has("args"));
		Assert.assertTrue(objectNode.has("errorCode"));
		Assert.assertTrue(objectNode.has("message"));
		Assert.assertTrue(objectNode.has("status"));

		statusJsonNode = objectNode.get("status");

		Assert.assertEquals(404, statusJsonNode.asInt());
	}

	@Test
	public void testToRESTError() {
		for (RESTError restError : RESTError.values()) {
			Assert.assertEquals(
				restError, RESTError.toRESTError(restError.getErrorCode()));
		}
	}

}