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

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represent the Json-LD+Hydra response of the Apio Architect for a given
 * resource
 * Note: It must NOT be the entry point or a single resource, it's for easier
 * traversing the ResourceCollections.
 *
 * @author Zoltán Takács
 */
public class ApioJsonLDResource {

	public ApioJsonLDResource(JsonNode resourceCollectionJsonNode)
		throws IOException {

		_resourceCollection = resourceCollectionJsonNode;

		_validateResourceCollection();
	}

	public JsonNode getContextNode(JsonNode jsonNode) {
		return _findJsonNode(_resourceCollection, ApioJsonLDConstants.CONTEXT);
	}

	/**
	 * Parses the given jsonNode (Resource Collection) e.g people, blog-postings
	 * and looks for the members array node.
	 *
	 * @return <code>JsonNode</code> The ArrayNode which contains the
	 * resource entries of a given (partial)collection (Members) or MissingNode
	 * if it's not present
	 */
	public JsonNode getMembersNode() {
		if (_membersJsonNode == null) {
			_membersJsonNode = _findJsonNode(
				_resourceCollection, ApioJsonLDConstants.COLLECTION_MEMBERS);
		}

		return _membersJsonNode;
	}

	public int getNumberOfItems() {
		return _resourceCollection.path(
			ApioJsonLDConstants.COLLECTION_NUMBER_OF_ITEMS).asInt();
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @return actual collection page or empty string if not present in the
	 * JsonNode
	 */
	public String getResourceActualPage() {
		JsonNode jsonNode = getViewNode().path(ApioJsonLDConstants.ID);

		return jsonNode.asText();
	}

	/**
	 * Parses the given jsonNode (Resource Collection) e.g people, blog-postings
	 * and returns the name of the resource element fields based on the first
	 * entry
	 *
	 * @return <code>List<String></code> Name of the resource fields, empty
	 * collection otherwise
	 */
	public List<String> getResourceElementFieldNames() {
		JsonNode members = getMembersNode();

		List<String> fieldNames = new ArrayList<>();

		if (!members.isArray() || (members.size() == 0)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Not able to fetch the resource fields");
			}

			return Collections.<String>emptyList();
		}

		JsonNode firstItemJsonNode = members.get(0);

		Iterator<String> iterator = firstItemJsonNode.fieldNames();

		while (iterator.hasNext()) {
			fieldNames.add(iterator.next());
		}

		return fieldNames;
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @return first collection page or empty string if not present in the
	 * JsonNode
	 */
	public String getResourceFirstPage() {
		JsonNode jsonNode = getViewNode().path(ApioJsonLDConstants.VIEW_FIRST);

		return jsonNode.asText();
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @return last collection page or empty string if not present in the
	 * JsonNode
	 */
	public String getResourceLastPage() {
		JsonNode jsonNode = getViewNode().path(ApioJsonLDConstants.VIEW_LAST);

		return jsonNode.asText();
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @return relative upcoming collection page or empty string if not present
	 * in the JsonNode
	 */
	public String getResourceNextPage() {
		JsonNode jsonNode = getViewNode().path(ApioJsonLDConstants.VIEW_NEXT);

		return jsonNode.asText();
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @return relative previous collection page or empty string if not present
	 * in the JsonNode
	 */
	public String getResourcePreviousPage() {
		JsonNode jsonNode = getViewNode().path(
			ApioJsonLDConstants.VIEW_PREVIOUS);

		return jsonNode.asText();
	}

	public int getTotalItems() {
		return _resourceCollection.path(
			ApioJsonLDConstants.COLLECTION_TOTAL_ITEMS).asInt();
	}

	public JsonNode getTypeNode() {
		return _findJsonNode(_resourceCollection, ApioJsonLDConstants.TYPE);
	}

	/**
	 * Parses the given jsonNode (Resource Collection) e.g people, blog-postings
	 * and looks for the view node.
	 *
	 * @return <code>JsonNode</code> The JsonNode for the view section or
	 * MissingNode if it's not present
	 */
	public JsonNode getViewNode() {
		return _findJsonNode(
			_resourceCollection, ApioJsonLDConstants.COLLECTION_VIEW);
	}

	/**
	 * Parses the given JsonNode which is a <code>@context</code> node and find
	 * the value of the <code>@vocab</code> node.
	 *
	 * @param contextJsonNode
	 * @return <code>String</code> the Vocab's value
	 * e.g "@vocab": "http://schema.org" otherwise empty String
	 */
	public String getVocabulary(JsonNode contextJsonNode) {
		return contextJsonNode.path(ApioJsonLDConstants.VOCAB).asText();
	}

	private JsonNode _findJsonNode(JsonNode resource, String nodeName) {
		JsonNode jsonNode = resource.path(nodeName);

		if (_log.isDebugEnabled()) {
			if (jsonNode.isMissingNode()) {
				_log.debug("Cannot find the \"{}\" node!", nodeName);
			}

			if (jsonNode.isArray() && (jsonNode.size() == 0)) {
				_log.debug("The \"{}\" ArrayNode is empty!", jsonNode);
			}
		}

		return jsonNode;
	}

	private void _validateResourceCollection() throws IOException {
		JsonNode typeNode = getTypeNode();

		boolean collection = false;

		if (typeNode.isArray()) {
			Iterator<JsonNode> elements = typeNode.elements();

			while (elements.hasNext() && (collection == false)) {
				JsonNode node = elements.next();

				if (ApioJsonLDConstants.COLLECTION.equals(node.asText())) {
					collection = true;
				}
			}
		}
		else {
			throw new IOException(
				"The given resource doesn't contain ArrayNode for it's type");
		}

		if (!collection) {
			throw new IOException(
				"The given resource's type is not collection");
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ApioJsonLDResource.class);

	/**
	 * Store the 'member' JsonNode as it's the most resource intensive task to
	 * collect and determine, so do it only once.
	 * @see #getMembersNode()
	 */
	private JsonNode _membersJsonNode;

	private final JsonNode _resourceCollection;

}