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

import com.liferay.talend.runtime.apio.constants.HydraConstants.FieldNames;
import com.liferay.talend.runtime.apio.constants.JSONLDConstants;
import com.liferay.talend.runtime.apio.operation.Operation;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zoltán Takács
 */
public class ApioSingleModel extends ApioBaseResponse {

	/**
	 * Represent the Json-LD+Hydra response of the Apio Architect for a given
	 * resource. (Single Model) Note: It must NOT be the entry point or a
	 * resource collection.
	 *
	 * @param  responseJsonNode
	 * @throws IOException
	 */
	public ApioSingleModel(JsonNode responseJsonNode) throws IOException {
		super(responseJsonNode);

		_validateSingleModel();
	}

	/**
	 * Parses the actual jsonNode (Resource Collection) e.g people,
	 * blog-postings and looks for the operation node.
	 *
	 * @return <code>JsonNode</code> The JsonNode for the operation section or
	 *         MissingNode if it's not present
	 */
	public JsonNode getOperationJsonNode() {
		return findJsonNode(FieldNames.OPERATION);
	}

	/**
	 * Determines the supported operations of the given resource and returns
	 * them in a List
	 *
	 * @return <code>List</code> of <code>Operation</code>, empty List otherwise
	 */
	public List<Operation> getResourceOperations() {
		JsonNode operationJsonNode = getOperationJsonNode();

		if (!operationJsonNode.isArray() || (operationJsonNode.size() == 0)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to fetch the resource's operations");
			}

			return Collections.<Operation>emptyList();
		}

		List<Operation> operations = new ArrayList<>();

		for (final JsonNode jsonNode : operationJsonNode) {
			JsonNode expectsJsonNode = jsonNode.path(FieldNames.EXPECTS);
			JsonNode methodIdJsonNode = jsonNode.path(JSONLDConstants.ID);
			JsonNode methodJsonNode = jsonNode.path(FieldNames.METHOD);

			try {
				Operation operation = new Operation(
					methodJsonNode.asText(), methodIdJsonNode.asText(),
					expectsJsonNode.asText(), isSingleModel());

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

	public boolean isSingleModel() {
		return true;
	}

	private void _validateSingleModel() throws IOException {
		JsonNode contextJsonNode = getContextJsonNode();

		if (contextJsonNode.isMissingNode()) {
			throw new IOException(
				"The given resource does not have context node");
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ApioSingleModel.class);

}