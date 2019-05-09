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

package com.liferay.talend.service;

import com.liferay.talend.configuration.OperationAction;
import com.liferay.talend.data.store.GenericDataStore;
import com.liferay.talend.data.store.OutputDataStore;
import com.liferay.talend.dataset.InputDataSet;
import com.liferay.talend.dataset.OutputDataSet;
import com.liferay.talend.http.client.exception.MalformedURLException;
import com.liferay.talend.util.StringUtils;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.talend.sdk.component.api.record.Schema;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 * @author Matija Petanjek
 * @review
 */
@Service
public class LiferayService {

	/**
	 * Gets Liferay OpenAPI JSON specification location relative URL.
	 *
	 * Method strips protocol, hostname and port from URL that matches Liferay
	 * REST service open API specification location pattern {@link
	 * #_openAPISpecURLPattern}.
	 *
	 * @param  openAPISpecURL Liferay OpenAPI specification URL
	 * @return endpoint location
	 */
	public String extractEndpointPathSegment(URL openAPISpecURL) {
		String openAPISpecRef = openAPISpecURL.toExternalForm();

		Matcher serverURLMatcher = _openAPISpecURLPattern.matcher(
			openAPISpecRef);

		if (!serverURLMatcher.matches()) {
			throw new MalformedURLException(
				"Unable to extract Open API endpoint from URL " +
					openAPISpecRef);
		}

		String serverInstanceURL = serverURLMatcher.group(1);

		String endpoint = openAPISpecRef.substring(serverInstanceURL.length());

		String endpointExtension = serverURLMatcher.group(7);

		if (endpointExtension.equals("yaml")) {
			endpoint = endpoint.replace(".yaml", ".json");
		}

		return endpoint;
	}

	public String extractJaxRSAppBasePathSegment(URL openAPISpecURL) {
		String openAPISpecRef = openAPISpecURL.toExternalForm();

		Matcher serverURLMatcher = _openAPISpecURLPattern.matcher(
			openAPISpecRef);

		if (!serverURLMatcher.matches()) {
			throw new MalformedURLException(
				"Unable to extract Open API endpoint from URL " +
					openAPISpecRef);
		}

		return serverURLMatcher.group(3);
	}

	public Schema getEndpointTalendSchema(
		InputDataSet inputDataSet, RecordBuilderFactory recordBuilderFactory) {

		String endpoint = inputDataSet.getEndpoint();

		JsonObject openAPISpecJsonObject = _getOpenAPISpecJsonObject(
			inputDataSet.getGenericDataStore());

		Map<String, String> pathResponseEntities = _mapKeysToPatternEvaluations(
			_GET_METHOD_RESPONSE_REGEX,
			openAPISpecJsonObject.getJsonObject("paths"));

		String entityName = pathResponseEntities.get(endpoint);

		JsonObject componentsJsonObject = openAPISpecJsonObject.getJsonObject(
			"components");

		JsonObject schemasJsonObject = componentsJsonObject.getJsonObject(
			"schemas");

		JsonObject schemaJsonObject = schemasJsonObject.getJsonObject(
			entityName);

		JsonObject propertiesJsonObject = schemaJsonObject.getJsonObject(
			"properties");

		JsonObject itemsJsonObject = propertiesJsonObject.getJsonObject(
			"items");

		itemsJsonObject = itemsJsonObject.getJsonObject("items");

		String ref = itemsJsonObject.getString("$ref");

		ref = StringUtils.stripPath(ref);

		schemaJsonObject = schemasJsonObject.getJsonObject(ref);

		return _talendService.getTalendSchema(
			schemaJsonObject, recordBuilderFactory);
	}

	public List<String> getReadableEndpoints(InputDataSet inputDataSet) {
		JsonObject openAPISpecJsonObject = _getOpenAPISpecJsonObject(
			inputDataSet.getGenericDataStore());

		if (openAPISpecJsonObject == null) {
			return Collections.emptyList();
		}

		return getReadableEndpoints(openAPISpecJsonObject);
	}

	public List<String> getUpdatableEndpoints(OutputDataSet outputDataSet) {
		OutputDataStore outputDataStore = outputDataSet.getOutputDataStore();

		JsonObject openAPISpecJsonObject = _getOpenAPISpecJsonObject(
			outputDataStore);

		if (openAPISpecJsonObject == null) {
			return Collections.emptyList();
		}

		OperationAction operationAction = outputDataStore.getOperationAction();

		return getMethodEndpoints(
			operationAction.getHttpMethod(), openAPISpecJsonObject);
	}

	public boolean isValidOpenAPISpecURL(String endpointURL) {
		Matcher serverURLMatcher = _openAPISpecURLPattern.matcher(endpointURL);

		if (serverURLMatcher.matches()) {
			return true;
		}

		return false;
	}

	public void validateOpenAPISpecURL(String openAPISpecURL) {
		if (!isValidOpenAPISpecURL(openAPISpecURL)) {
			throw new MalformedURLException(
				"Provided Open API Specification URL does not match pattern: " +
					_openAPISpecURLPattern.pattern());
		}
	}

	protected List<String> getMethodEndpoints(
		String method, JsonObject openAPISpecJsonObject) {

		Map<String, String> pathResponseEntities = _mapKeysToPatternEvaluations(
			method, openAPISpecJsonObject.getJsonObject("paths"));

		List<String> endpoints = new ArrayList<>(pathResponseEntities.keySet());

		Collections.sort(endpoints);

		return endpoints;
	}

	protected List<String> getReadableEndpoints(
		JsonObject openAPISpecJsonObject) {

		Map<String, String> pathResponseEntities = _mapKeysToPatternEvaluations(
			_GET_METHOD_RESPONSE_REGEX,
			openAPISpecJsonObject.getJsonObject("paths"));

		return _getSortedKeys(pathResponseEntities);
	}

	/**
	 * Gets string value of json node pointed by given <code>pattern</code>.
	 *
	 * Method recursively resolves value searched by given pattern.
	 *
	 * <code>pattern</code> must match key1>key2>...>keyN syntax where key is
	 * expected key value in given json structure.
	 *
	 * @param  pattern
	 * @param  jsonObject
	 * @return keyN string value of (N-1)th <code>jsonObject</code> if keyN is
	 *         reachable through given <code>pattern</code>, <code>null</code>
	 *         otherwise
	 */
	private String _evaluatePattern(String pattern, JsonObject jsonObject) {
		JsonValue jsonValue = _findByPattern(pattern, jsonObject);

		if (jsonValue == null) {
			return null;
		}

		if (jsonValue.getValueType() != JsonValue.ValueType.STRING) {
			return jsonValue.toString();
		}

		JsonString jsonString = (JsonString)jsonValue;

		return jsonString.getString();
	}

	/**
	 * Gets json node pointed by given <code>pattern</code>.
	 *
	 * Method recursively resolves value searched by given pattern.
	 *
	 * <code>pattern</code> must match key1>key2>...>keyN syntax where key is
	 * expected key value in given json structure.
	 *
	 * @param  pattern
	 * @param  jsonObject
	 * @return keyN string value of (N-1)th <code>jsonObject</code> if keyN is
	 *         reachable through given <code>pattern</code>, <code>null</code>
	 *         otherwise
	 */
	private JsonValue _findByPattern(String pattern, JsonObject jsonObject) {
		int delimiterIdx = pattern.indexOf(">");

		if (delimiterIdx == -1) {
			if (jsonObject.containsKey(pattern)) {
				return jsonObject.get(pattern);
			}

			return null;
		}

		String substring = pattern.substring(0, delimiterIdx);

		if (!jsonObject.containsKey(substring)) {
			return null;
		}

		return _findByPattern(
			pattern.substring(delimiterIdx + 1),
			jsonObject.getJsonObject(substring));
	}

	private JsonObject _getOpenAPISpecJsonObject(
		GenericDataStore genericDataStore) {

		URL openAPISpecURL = genericDataStore.getOpenAPISpecURL();

		validateOpenAPISpecURL(openAPISpecURL.toExternalForm());

		String endpoint = extractEndpointPathSegment(openAPISpecURL);

		return _connectionService.getResponseJsonObject(
			genericDataStore, endpoint);
	}

	private List<String> _getSortedKeys(
		Map<String, String> patternEvaluations) {

		List<String> pageableEndpoints = new ArrayList<>(
			patternEvaluations.keySet());

		Collections.sort(pageableEndpoints);

		return pageableEndpoints;
	}

	/**
	 * Gets key, value map where each key is contained in given
	 * <code>jsonObject</code> and value is String value pointed by given
	 * <code>pattern</code>
	 *
	 * <code>pattern</code> must match key1>key2>...>keyN syntax
	 *
	 * @param  pattern expression used to traverse underlying
	 *         <code>jsonObject</code> hierarchy
	 * @param  jsonObject source for key values
	 * @return Map of key, values where keys point to resolvable values
	 *         according given pattern
	 */
	private Map<String, String> _mapKeysToPatternEvaluations(
		String pattern, JsonObject jsonObject) {

		Map<String, String> evaluatedPatterns = new HashMap<>();

		jsonObject.forEach(
			(key, jsonValue) -> {
				String evaluation = _evaluatePattern(
					pattern, jsonValue.asJsonObject());

				if (evaluation != null) {
					evaluatedPatterns.put(
						key, StringUtils.stripPath(evaluation));
				}
			});

		return evaluatedPatterns;
	}

	private static final String _GET_METHOD_RESPONSE_REGEX =
		"get>responses>default>content>application/json>schema>$ref";

	private static final String _SCHEMA_PROPERTY_ITEMS_TYPE_REGEX =
		"components>schemas>%s>properties>items>type";

	private static final String _SCHEMA_PROPERTY_PAGE_TYPE_REGEX =
		"components>schemas>%s>properties>page>type";

	private static final Pattern _openAPISpecURLPattern = Pattern.compile(
		"(https?://.+(:\\d+)?)(/o/(.+)/)(v\\d+(.\\d+)*)/openapi\\.(yaml|json)");

	@Service
	private ConnectionService _connectionService;

	@Service
	private TalendService _talendService;

}