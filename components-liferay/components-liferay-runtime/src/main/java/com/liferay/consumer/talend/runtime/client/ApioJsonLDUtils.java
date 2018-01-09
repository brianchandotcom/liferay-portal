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

package com.liferay.consumer.talend.runtime.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zoltán Takács
 */
public class ApioJsonLDUtils {

	/**
	 * Parses the given jsonNode (Resource Collection) e.g people, blog-postings
	 * and looks for the members array node.
	 * If it's located then the first entry will be processed to get the fields
	 * of the resource.
	 *
	 * @param collectionJson
	 * @return <code>JsonNode</code> The ArrayNode which contains the
	 * resource entries of a given (partial)collection (Members)
	 */
	public static JsonNode getResourceMemberArrayNode(JsonNode collectionJson) {
		JsonNode members = collectionJson.findPath("members");

		if (members.isMissingNode()) {
			_log.error("Cannot find the \"members\" ArrayNode!");

			return JsonNodeFactory.instance.arrayNode();
		}

		if (!members.isArray() || (members.size() == 0)) {
			_log.error("The \"members\" ArrayNode is empty!");

			return JsonNodeFactory.instance.arrayNode();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Size of the \"members\" ArrayNode: {}", members.size());
		}

		return members;
	}

	/**
	 * Parses the given jsonNode (Resource Collection) e.g people, blog-postings
	 * and returns the name of the resource fields
	 *
	 * @param collectionJson
	 * @return <code>List<String></code> Name of the resource fields
	 */
	public static List<String> getResourceFieldNames(JsonNode collectionJson) {
		JsonNode members = getResourceMemberArrayNode(collectionJson);

		List<String> fieldNames = new ArrayList<>();

		JsonNode firstItem = members.get(0);

		Iterator<String> fieldIter = firstItem.fieldNames();

		while (fieldIter.hasNext()) {
			fieldNames.add(fieldIter.next());
		}

		return fieldNames;
	}

	private static final Logger _log =
		LoggerFactory.getLogger(ApioJsonLDUtils.class);

}
