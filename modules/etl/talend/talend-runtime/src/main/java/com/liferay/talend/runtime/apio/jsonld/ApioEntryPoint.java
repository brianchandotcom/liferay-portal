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

import com.liferay.talend.runtime.apio.constants.HydraConstants;
import com.liferay.talend.runtime.apio.constants.JSONLDConstants;

import java.io.IOException;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the Json-LD+Hydra response of the Apio Architect for a given
 * RootEndpoint
 *
 * @author Zoltán Takács
 */
public class ApioEntryPoint extends ApioBaseResponse {

	public ApioEntryPoint(JsonNode responseJsonNode) throws IOException {
		super(responseJsonNode);

		_validateEntryPoint();
	}

	public JsonNode getCollectionJsonNode() {
		return findJsonNode(HydraConstants.FieldNames.COLLECTION);
	}

	/**
	 * Returns the exposed entry points in a Map. The key is the ID of a given
	 * resource collection and the value its managed type
	 *
	 * @return Map<String, String> Resource ID / Managed type, empty otherwise
	 */
	public Map<String, String> getRootEndpointMap() {
		JsonNode collectionArrayJsonNode = getCollectionJsonNode();

		if (!collectionArrayJsonNode.isArray() ||
			(collectionArrayJsonNode.size() == 0)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to fetch the resources from the entry point");
			}

			return Collections.emptyMap();
		}

		Map<String, String> rootEndpointMap = new TreeMap<>();

		for (final JsonNode collectionJsonNode : collectionArrayJsonNode) {
			JsonNode idJsonNode = collectionJsonNode.path(JSONLDConstants.ID);
			String managedType = ApioUtils.getManagedType(collectionJsonNode);

			rootEndpointMap.put(idJsonNode.asText(), managedType);
		}

		return Collections.unmodifiableMap(rootEndpointMap);
	}

	private void _validateEntryPoint() throws IOException {
		if (!hasValueOf(
				HydraConstants.FieldTypes.ENTRY_POINT, getTypeJsonNode())) {

			throw new IOException("The given resource is not an entry point");
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ApioEntryPoint.class);

}