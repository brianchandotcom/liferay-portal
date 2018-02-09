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

import com.liferay.talend.runtime.apio.operation.Operation;

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
 * resource Note: It must NOT be the entry point or a single resource, it's for
 * easier traversing the ResourceCollections.
 *
 * @author Zoltán Takács
 */
public class ApioJsonLDResource {

	public ApioJsonLDResource(JsonNode resourceCollectionJsonNode)
		throws IOException {

		_resourceCollectionJsonNode = resourceCollectionJsonNode;

		_validateResourceCollection();
	}

	public JsonNode getContextNode() {
		return _findJsonNode(
			_resourceCollectionJsonNode, ApioJsonLDConstants.CONTEXT);
	}

	/**
	 * Parses the actual jsonNode (Resource Collection) e.g people,
	 * blog-postings and looks for the members array node.
	 *
	 * @return <code>JsonNode</code> The ArrayNode which contains the resource
	 *         entries of a given (partial)collection (Members) or MissingNode
	 *         if it's not present
	 */
	public JsonNode getMembersNode() {
		if (_membersJsonNode == null) {
			_membersJsonNode = _findJsonNode(
				_resourceCollectionJsonNode,
				ApioJsonLDConstants.COLLECTION_MEMBERS);
		}

		return _membersJsonNode;
	}

	public int getNumberOfItems() {
		JsonNode jsonNode = _resourceCollectionJsonNode.path(
			ApioJsonLDConstants.COLLECTION_NUMBER_OF_ITEMS);

		return jsonNode.asInt();
	}

	/**
	 * Parses the actual jsonNode (Resource Collection) e.g people,
	 * blog-postings and looks for the operation node.
	 *
	 * @return <code>JsonNode</code> The JsonNode for the operation section or
	 *         MissingNode if it's not present
	 */
	public JsonNode getOperationNode() {
		return _findJsonNode(
			_resourceCollectionJsonNode, ApioJsonLDConstants.OPERATION);
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @return actual collection page or empty string if not present in the
	 *         JsonNode
	 */
	public String getResourceActualPage() {
		JsonNode viewJsonNode = getViewNode();

		JsonNode jsonNode = viewJsonNode.path(ApioJsonLDConstants.ID);

		return jsonNode.asText();
	}

	/**
	 * Determines the supported operations of the resource collection and
	 * retruns them in a List
	 *
	 * @return <code>List</code> of <code>Operation</code>, empty List otherwise
	 */
	public List<Operation> getResourceCollectionOperations() {
		JsonNode operationJsonNode = getOperationNode();

		if (!operationJsonNode.isArray() || (operationJsonNode.size() == 0)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to fetch the resource's operations");
			}

			return Collections.<Operation>emptyList();
		}

		List<Operation> operations = new ArrayList<>();

		for (final JsonNode jsonNode : operationJsonNode) {
			JsonNode expectsJsonNode = jsonNode.path(
				ApioJsonLDConstants.EXPECTS);
			JsonNode methodJsonNode = jsonNode.path(ApioJsonLDConstants.METHOD);

			try {
				Operation operation = new Operation(
					methodJsonNode.asText(), expectsJsonNode.asText(), false);

				operations.add(operation);
			}
			catch (UnsupportedOperationException uoe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						String.format(
							"Unsupported operation: %s", uoe.getMessage()),
						uoe);
				}
			}
		}

		return Collections.unmodifiableList(operations);
	}

	/**
	 * Determines the resource collection type based on the members node in the
	 * Apio architect response
	 *
	 * @return String the type of the resource collection. E.g. Person,
	 *         BlogPosting. <code>null</code> if the resource type cannot be
	 *         determined
	 */
	public String getResourceCollectionType() {
		JsonNode membersJsonNode = getMembersNode();

		if (!membersJsonNode.isArray() || (membersJsonNode.size() == 0)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to fetch the resource fields");
			}

			return null;
		}

		JsonNode resourceJsonNode = membersJsonNode.get(0);

		JsonNode typeJsonNode = resourceJsonNode.path(ApioJsonLDConstants.TYPE);

		if (typeJsonNode.isArray()) {
			JsonNode jsonNode = typeJsonNode.get(0);

			return jsonNode.asText();
		}

		return null;
	}

	/**
	 * Parses the given jsonNode (Resource Collection) e.g people, blog-postings
	 * and returns the name of the resource element fields based on the first
	 * entry
	 *
	 * @return <code>List<String></code> Name of the resource fields, empty
	 *         collection otherwise
	 */
	public List<String> getResourceElementFieldNames() {
		JsonNode membersJsonNode = getMembersNode();

		List<String> fieldNames = new ArrayList<>();

		if (!membersJsonNode.isArray() || (membersJsonNode.size() == 0)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to fetch the resource fields");
			}

			return Collections.emptyList();
		}

		JsonNode firstItemJsonNode = membersJsonNode.get(0);

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
	 *         JsonNode
	 */
	public String getResourceFirstPage() {
		JsonNode viewJsonNode = getViewNode();

		JsonNode jsonNode = viewJsonNode.path(ApioJsonLDConstants.VIEW_FIRST);

		return jsonNode.asText();
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @return last collection page or empty string if not present in the
	 *         JsonNode
	 */
	public String getResourceLastPage() {
		JsonNode viewJsonNode = getViewNode();

		JsonNode jsonNode = viewJsonNode.path(ApioJsonLDConstants.VIEW_LAST);

		return jsonNode.asText();
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @return relative upcoming collection page or empty string if not present
	 *         in the JsonNode
	 */
	public String getResourceNextPage() {
		JsonNode viewJsonNode = getViewNode();

		JsonNode jsonNode = viewJsonNode.path(ApioJsonLDConstants.VIEW_NEXT);

		return jsonNode.asText();
	}

	/**
	 * Parses the view JsonNode of the resource
	 *
	 * @return relative previous collection page or empty string if not present
	 *         in the JsonNode
	 */
	public String getResourcePreviousPage() {
		JsonNode viewJsonNode = getViewNode();

		JsonNode jsonNode = viewJsonNode.path(
			ApioJsonLDConstants.VIEW_PREVIOUS);

		return jsonNode.asText();
	}

	public int getTotalItems() {
		JsonNode jsonNode = _resourceCollectionJsonNode.path(
			ApioJsonLDConstants.COLLECTION_TOTAL_ITEMS);

		return jsonNode.asInt();
	}

	public JsonNode getTypeNode() {
		return _findJsonNode(
			_resourceCollectionJsonNode, ApioJsonLDConstants.TYPE);
	}

	/**
	 * Parses the actual jsonNode (Resource Collection) e.g people,
	 * blog-postings and looks for the view node.
	 *
	 * @return <code>JsonNode</code> The JsonNode for the view section or
	 *         MissingNode if it's not present
	 */
	public JsonNode getViewNode() {
		return _findJsonNode(
			_resourceCollectionJsonNode, ApioJsonLDConstants.COLLECTION_VIEW);
	}

	/**
	 * Parses the given JsonNode which is a <code>@context</code> node and find
	 * the value of the <code>@vocab</code> node.
	 *
	 * @param  contextJsonNode
	 * @return <code>String</code> the Vocab's value e.g "@vocab":
	 *         "http://schema.org" otherwise empty String
	 */
	public String getVocabulary(JsonNode contextJsonNode) {
		JsonNode jsonNode = contextJsonNode.path(ApioJsonLDConstants.VOCAB);

		return jsonNode.asText();
	}

	private JsonNode _findJsonNode(JsonNode resource, String nodeName) {
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

	private void _validateResourceCollection() throws IOException {
		JsonNode typeJsonNode = getTypeNode();

		boolean collection = false;

		if (typeJsonNode.isArray()) {
			Iterator<JsonNode> iterator = typeJsonNode.elements();

			while (iterator.hasNext() && (collection == false)) {
				JsonNode jsonNode = iterator.next();

				if (ApioJsonLDConstants.COLLECTION.equals(jsonNode.asText())) {
					collection = true;
				}
			}
		}
		else {
			throw new IOException(
				"The given resource does not contain ArrayNode for its type");
		}

		if (!collection) {
			throw new IOException(
				"The given resource's type is not a collection");
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ApioJsonLDResource.class);

	/**
	 * Store the 'member' JsonNode as its the most resource intensive task to
	 * collect and determine, so do it only once.
	 *
	 * @see #getMembersNode()
	 */
	private JsonNode _membersJsonNode;

	private final JsonNode _resourceCollectionJsonNode;

}