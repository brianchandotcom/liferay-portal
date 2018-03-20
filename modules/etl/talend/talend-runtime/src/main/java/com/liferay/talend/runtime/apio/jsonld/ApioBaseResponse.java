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

package com.liferay.talend.runtime.apio.jsonld;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.talend.runtime.apio.constants.JSONLDConstants;

import java.io.IOException;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zoltán Takács
 */
public abstract class ApioBaseResponse implements ApioResponse {

	public ApioBaseResponse(JsonNode responseJsonNode) throws IOException {
		this.responseJsonNode = responseJsonNode;
	}

	@Override
	public JsonNode getContextJsonNode() {
		return findJsonNode(JSONLDConstants.CONTEXT);
	}

	@Override
	public JsonNode getIdJsonNode() {
		return findJsonNode(JSONLDConstants.ID);
	}

	@Override
	public JsonNode getTypeJsonNode() {
		return findJsonNode(JSONLDConstants.TYPE);
	}

	protected static boolean hasValueOf(String value, JsonNode jsonNode) {
		if (jsonNode.isArray()) {
			Iterator<JsonNode> iterator = jsonNode.elements();

			while (iterator.hasNext()) {
				JsonNode entryJsonNode = iterator.next();

				String entry = entryJsonNode.asText();

				if (entry.equals(value)) {
					return true;
				}
			}
		}
		else if (jsonNode.isValueNode()) {
			String entry = jsonNode.asText();

			if (entry.equals(value)) {
				return true;
			}
		}

		return false;
	}

	protected JsonNode findJsonNode(JsonNode resource, String nodeName) {
		JsonNode jsonNode = resource.path(nodeName);

		if (_log.isDebugEnabled()) {
			if (jsonNode.isMissingNode()) {
				_log.debug("Unable to find the \"{}\" node", nodeName);
			}

			if (jsonNode.isArray() && (jsonNode.size() == 0)) {
				_log.debug("The \"{}\" array node is empty", jsonNode);
			}
		}

		return jsonNode;
	}

	protected JsonNode findJsonNode(String nodeName) {
		return findJsonNode(responseJsonNode, nodeName);
	}

	protected final JsonNode responseJsonNode;

	private static final Logger _log = LoggerFactory.getLogger(
		ApioBaseResponse.class);

}