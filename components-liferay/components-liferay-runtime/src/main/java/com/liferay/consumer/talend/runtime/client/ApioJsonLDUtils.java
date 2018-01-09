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
	 *
	 * @param resource
	 * @return <code>JsonNode</code> The ArrayNode which contains the
	 * resource entries of a given (partial)collection (Members) or MissingNode
	 * if it's not present
	 */
	public static JsonNode getCollectionMemberNode(JsonNode resource) {
		return _findCollectionNode(
			resource, ApioJsonLDConstants.COLLECTION_MEMBERS);
	}

	/**
	 * Parses the given jsonNode (Resource Collection) e.g people, blog-postings
	 * and looks for the view node.
	 *
	 * @param resource
	 * @return <code>JsonNode</code> The JsonNode for the view section or
	 * MissingNode if it's not present
	 */
	public static JsonNode getCollectionViewNode(JsonNode resource) {
		return _findCollectionNode(
			resource, ApioJsonLDConstants.COLLECTION_VIEW);
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @param resourceViewJsonNode
	 * @return actual collection page or empty string if not present in the
	 * JsonNode
	 */
	public static String getResourceActualPage(JsonNode resourceViewJsonNode) {
		JsonNode node = resourceViewJsonNode.findValue(ApioJsonLDConstants.ID);

		return _safeReturnValue(node);
	}

	/**
	 * Parses the given jsonNode (Resource Collection) e.g people, blog-postings
	 * and returns the name of the resource fields
	 *
	 * @param resource
	 * @return <code>List<String></code> Name of the resource fields
	 */
	public static List<String> getResourceFieldNames(JsonNode resource) {
		JsonNode members = getCollectionMemberNode(resource);

		List<String> fieldNames = new ArrayList<>();

		if (!members.isArray()) {
			_log.error("Not able to fetch the resource fields");
		}

		JsonNode firstItem = members.get(0);

		Iterator<String> fieldIter = firstItem.fieldNames();

		while (fieldIter.hasNext()) {
			fieldNames.add(fieldIter.next());
		}

		return fieldNames;
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @param resourceViewJsonNode
	 * @return first collection page or empty string if not present in the
	 * JsonNode
	 */
	public static String getResourceFirstPage(JsonNode resourceViewJsonNode) {
		JsonNode node = resourceViewJsonNode.findValue(
			ApioJsonLDConstants.VIEW_FIRST);

		return _safeReturnValue(node);
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @param resourceViewJsonNode
	 * @return last collection page or empty string if not present in the
	 * JsonNode
	 */
	public static String getResourceLastPage(JsonNode resourceViewJsonNode) {
		JsonNode node = resourceViewJsonNode.findValue(
			ApioJsonLDConstants.VIEW_LAST);

		return _safeReturnValue(node);
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @param resourceViewJsonNode
	 * @return relative upcoming collection page or empty string if not present
	 * in the JsonNode
	 */
	public static String getResourceNextPage(JsonNode resourceViewJsonNode) {
		JsonNode node = resourceViewJsonNode.findValue(
			ApioJsonLDConstants.VIEW_NEXT);

		return _safeReturnValue(node);
	}

	private static JsonNode _findCollectionNode(
		JsonNode resource, String nodeName) {

		JsonNode jsonNode = resource.findPath(nodeName);

		if (jsonNode.isMissingNode()) {
			_log.error("Cannot find the \"{}\" node!", nodeName);

			return jsonNode;
		}

		if (jsonNode.isArray() && (jsonNode.size() == 0)) {
			_log.error("The \"{}\" ArrayNode is empty!", jsonNode);
		}

		return jsonNode;
	}

	private static String _safeReturnValue(JsonNode jsonNode) {
		if (jsonNode == null) {
			return "";
		}

		return jsonNode.asText();
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ApioJsonLDUtils.class);

}