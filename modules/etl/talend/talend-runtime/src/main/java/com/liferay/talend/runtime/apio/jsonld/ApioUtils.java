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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zoltán Takács
 */
public class ApioUtils {

	/**
	 * Finds the context node in the given JsonNode if there is any.
	 *
	 * @param  jsonNode Input JsonNode
	 * @return JsonNode for the context node, otherwise MissingNode
	 */
	public static JsonNode getContextJsonNode(JsonNode jsonNode) {
		return _findJsonNode(jsonNode, JSONLDConstants.CONTEXT);
	}

	/**
	 * Parses the given JsonNode which is a <code>@context</code> node and find
	 * the value of the <code>@vocab</code> node.
	 *
	 * @param  contextJsonNode
	 * @return <code>String</code> the Vocab's value e.g "@vocab":
	 *         "http://schema.org" otherwise empty String
	 */
	public static String getVocabulary(JsonNode contextJsonNode) {
		JsonNode jsonNode = contextJsonNode.path(JSONLDConstants.VOCAB);

		return jsonNode.asText();
	}

	private static JsonNode _findJsonNode(JsonNode resource, String nodeName) {
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

	private ApioUtils() {
	}

	private static final Logger _log = LoggerFactory.getLogger(ApioUtils.class);

}