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

import com.liferay.talend.data.store.GenericDataStore;
import com.liferay.talend.dataset.InputDataSet;
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

import org.talend.sdk.component.api.record.Schema;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 * @author Matija Petanjek
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
			inputDataSet);

		Map<String, String> pathResponseEntities = _mapKeysToPatternEvaluations(
			_GET_METHOD_RESPONSE_PATTERN,
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

	public List<String> getPageableEndpoints(InputDataSet inputDataSet) {
		JsonObject openAPISpecJsonObject = _getOpenAPISpecJsonObject(
			inputDataSet);

		if (openAPISpecJsonObject == null) {
			return Collections.emptyList();
		}

		return getPageableEndpoints(openAPISpecJsonObject);
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

	protected List<String> getPageableEndpoints(
		JsonObject openAPISpecJsonObject) {

		Map<String, String> pathResponseEntities = _mapKeysToPatternEvaluations(
			_GET_METHOD_RESPONSE_PATTERN,
			openAPISpecJsonObject.getJsonObject("paths"));

		return _filterPageableEndpoints(
			pathResponseEntities, openAPISpecJsonObject);
	}

	/**
	 * Gets string representation of value of single json attribute from given
	 * <code>jsonObject</code>.
	 *
	 * Method recursively walks through json hierarchy and searches for the
	 * attribute names chain that matches given <code>pattern</code>. Once
	 * matching
	 * <code>pattern</code>
	 * is detected, method extracts string value of json entry whose attribute
	 * name corresponds to last
	 * element in <code>pattern</code>.
	 *
	 * <code>pattern</code> must match <code>key1>key2>...>keyN</code> syntax
	 * where key1 to keyN are json attribute names and <code>></code> is
	 * delimiter character.
	 *
	 * For <code>jsonObject</code> with structure:
	 * <pre>{
	 * 		"attr1": {
	 * 			"attr2": {
	 * 				"attr3": {
	 * 					"attrA": "value A",
	 * 					"attrB": {
	 * 						"attrB1": "value B"
	 * 					}
	 * 				}
	 * 			}
	 * 		},
	 * 		"attr4": "value C"
	 * }</pre>
	 * results of invocation are:
	 * <pre>_evaluatePattern("attr1>attr2>attr3>attrA", jsonObject)</pre>
	 * returns <code>value A</code>
	 * <pre>_evaluatePattern("attr1>attr2>attr3>attrB>attrB1", jsonObject)</pre>
	 * returns <code>value B</code>
	 * <pre>_evaluatePattern("attr4", jsonObject)</pre>
	 * returns <code>value C</code>
	 *
	 * @param  pattern string expression that represents chain of attribute
	 *         names delimited with <code>></code>
	 * @param  jsonObject json object whose hierarchy contains searched
	 *         attribute value
	 * @return string representation of value of <code>jsonObject</code>
	 *         attribute reachable with expression <code>pattern</code>,
	 *         <code>null</code> if attribute doesn't exist
	 */
	private String _evaluatePattern(String pattern, JsonObject jsonObject) {
		int delimiterIdx = pattern.indexOf(">");

		if (delimiterIdx == -1) {
			if (jsonObject.containsKey(pattern)) {
				return jsonObject.getString(pattern);
			}

			return null;
		}

		String substring = pattern.substring(0, delimiterIdx);

		if (!jsonObject.containsKey(substring)) {
			return null;
		}

		return _evaluatePattern(
			pattern.substring(delimiterIdx + 1),
			jsonObject.getJsonObject(substring));
	}

	private List<String> _filterPageableEndpoints(
		Map<String, String> patternEvaluations,
		JsonObject openAPISpecJsonObject) {

		List<String> pageableEndpoints = new ArrayList<>();

		patternEvaluations.forEach(
			(endpoint, entity) -> {
				String typeValue = _evaluatePattern(
					String.format(_SCHEMA_PROPERTY_ITEMS_TYPE_PATTERN, entity),
					openAPISpecJsonObject);

				if (!"array".equals(typeValue)) {
					return;
				}

				typeValue = _evaluatePattern(
					String.format(_SCHEMA_PROPERTY_PAGE_TYPE_PATTERN, entity),
					openAPISpecJsonObject);

				if (!"integer".equals(typeValue)) {
					return;
				}

				pageableEndpoints.add(endpoint);
			});

		Collections.sort(pageableEndpoints);

		return pageableEndpoints;
	}

	private JsonObject _getOpenAPISpecJsonObject(InputDataSet inputDataSet) {
		GenericDataStore genericDataStore = inputDataSet.getGenericDataStore();

		URL openAPISpecURL = genericDataStore.getOpenAPISpecURL();

		validateOpenAPISpecURL(openAPISpecURL.toExternalForm());

		String endpoint = extractEndpointPathSegment(openAPISpecURL);

		return _connectionService.getResponseJsonObject(inputDataSet, endpoint);
	}

	/**
	 * Gets key, value map where each key is attribute name and corresponding
	 * value is value contained in given <code>jsonObject</code>.
	 *
	 * Method collects keys from <code>jsonObject</code> attribute names and
	 * searches for particular value in nested json objects
	 * using expression given by <code>pattern</code>
	 *
	 * <code>pattern</code> must match <code>key1>key2>...>keyN</code> syntax
	 * where key1 to keyN are json attribute names and <code>></code> is
	 * delimiter character.
	 *
	 * For <code>jsonObject</code> with structure:
	 * <pre>{
	 * 		"attrA": {
	 * 			"attr1": {
	 * 				"attr2": "value X"
	 * 				}
	 * 			},
	 * 			"attr3": "value Z"
	 * 		},
	 * 		"attrB": {
	 * 			"attr1": {
	 * 				"attr2": "value Y",
	 * 				"attr4": "value W"
	 * 				}
	 * 			}
	 * }</pre>
	 * results of invocation are:
	 * <pre>_evaluatePattern("attr1>attr2", jsonObject)</pre>
	 * returns map <code>{"attrA": "value X", "attrB": "value Y"}</code>
	 *
	 * @param  pattern string expression that represents chain of attribute
	 *         names delimited with <code>></code>
	 * @param  jsonObject json object whose hierarchy contains attribute names
	 *         and searched attribute values
	 * @return key, values map where keys point to resolvable values according
	 *         given pattern
	 */
	private Map<String, String> _mapKeysToPatternEvaluations(
		final String pattern, JsonObject jsonObject) {

		final Map<String, String> evaluatedPatterns = new HashMap<>();

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

	private static final String _GET_METHOD_RESPONSE_PATTERN =
		"get>responses>default>content>application/json>schema>$ref";

	private static final String _SCHEMA_PROPERTY_ITEMS_TYPE_PATTERN =
		"components>schemas>%s>properties>items>type";

	private static final String _SCHEMA_PROPERTY_PAGE_TYPE_PATTERN =
		"components>schemas>%s>properties>page>type";

	private static final Pattern _openAPISpecURLPattern = Pattern.compile(
		"(https?://.+(:\\d+)?)(/o/(.+)/)(v\\d+(.\\d+)*)/openapi\\.(yaml|json)");

	@Service
	private ConnectionService _connectionService;

	@Service
	private TalendService _talendService;

}