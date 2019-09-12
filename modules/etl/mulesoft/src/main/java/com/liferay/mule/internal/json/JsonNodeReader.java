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

package com.liferay.mule.internal.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
public class JsonNodeReader {

	public JsonNode fromHttpResponse(HttpResponse httpResponse)
		throws IOException {

		HttpEntity httpEntity = httpResponse.getEntity();

		return _objectMapper.readTree(httpEntity.getContent());
	}

	public JsonNode getDescendantJsonNode(JsonNode jsonNode, String path) {
		String[] pathParts = path.split(">");

		for (String pathPart : pathParts) {
			jsonNode = jsonNode.get(pathPart);
		}

		return jsonNode;
	}

	private final ObjectMapper _objectMapper = new ObjectMapper();

}