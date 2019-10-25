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

package com.liferay.talend.common.oas;

import com.liferay.talend.common.json.JsonFinder;
import com.liferay.talend.common.oas.constants.OASConstants;
import com.liferay.talend.common.util.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * @author Igor Beslic
 */
public class OASExplorer {

	public Set<String> getEndpointList(
		JsonObject oasJsonObject, String... operations) {

		if (operations.length == 0) {
			return _extractEndpoints(oasJsonObject, null);
		}

		Set<String> endpoints = new HashSet<>();

		for (String operation : operations) {
			endpoints.addAll(_extractEndpoints(oasJsonObject, operation));
		}

		return endpoints;
	}

	public Set<String> getEntitySchemaNames(JsonObject oasJsonObject) {
		Set<String> entitySchemaNames = new HashSet<>();

		JsonObject componentSchemaJsonObject =
			_jsonFinder.getDescendantJsonObject(
				OASConstants.PATH_COMPONENTS_SCHEMAS, oasJsonObject);

		componentSchemaJsonObject.forEach(
			(entityName, entityDefinition) -> {
				if (entityName.startsWith("Page")) {
					return;
				}

				entitySchemaNames.add(entityName);
			});

		return entitySchemaNames;
	}

	public List<OASParameter> getParameters(
		String endpoint, String operation, JsonObject oasJsonObject) {

		List<OASParameter> oasParameters = new ArrayList<>();

		String jsonFinderPath = StringUtil.replace(
			OASConstants.PATH_ENDPOINT_OPERATION_PARAMETERS_PATTERN,
			"ENDPOINT_TPL", endpoint, "OPERATION_TPL", operation);

		JsonArray parametersJsonArray = _jsonFinder.getDescendantJsonArray(
			jsonFinderPath, oasJsonObject);

		for (int i = 0; i < parametersJsonArray.size(); i++) {
			oasParameters.add(
				_toParameter(parametersJsonArray.getJsonObject(i)));
		}

		return oasParameters;
	}

	public Set<String> getSupportedOperations(
		String endpoint, JsonObject oasJsonObject) {

		String jsonFinderPath = StringUtil.replace(
			OASConstants.PATH_ENDPOINT_PATTERN, "ENDPOINT_TPL", endpoint);

		JsonObject endpointJsonObject = _jsonFinder.getDescendantJsonObject(
			jsonFinderPath, oasJsonObject);

		return endpointJsonObject.keySet();
	}

	private Set<String> _extractEndpoints(
		JsonObject oasJsonObject, String operation) {

		Set<String> endpoints = new TreeSet<>();

		JsonObject pathsJsonObject = oasJsonObject.getJsonObject(
			OASConstants.PATHS);

		pathsJsonObject.forEach(
			(path, operationsJsonValue) -> {
				JsonObject operationsJsonObject =
					operationsJsonValue.asJsonObject();

				operationsJsonObject.forEach(
					(operationName, operationJsonValue) -> {
						if (operation != null) {
							if (!Objects.equals(operation, operationName)) {
								return;
							}

							if (!Objects.equals(
									operation, OASConstants.OPERATION_GET)) {

								endpoints.add(path);

								return;
							}
						}

						if (_jsonFinder.hasPath(
								OASConstants.PATH_RESPONSE_SCHEMA_REFERENCE,
								operationJsonValue.asJsonObject()) ||
							_jsonFinder.hasPath(
								OASConstants.
									PATH_RESPONSE_SCHEMA_ITEMS_REFERENCE,
								operationJsonValue.asJsonObject())) {

							endpoints.add(path);
						}
					});
			});

		return endpoints;
	}

	private OASParameter _toParameter(JsonObject jsonObject) {
		OASParameter oasParameter = new OASParameter(
			jsonObject.getString("name"), jsonObject.getString("in"));

		if (jsonObject.containsKey("required")) {
			oasParameter.setRequired(jsonObject.getBoolean("required"));
		}

		return oasParameter;
	}

	private final JsonFinder _jsonFinder = new JsonFinder();

}