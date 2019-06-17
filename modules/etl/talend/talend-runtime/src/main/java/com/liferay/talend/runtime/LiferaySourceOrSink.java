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

package com.liferay.talend.runtime;

import com.liferay.talend.avro.EndpointSchemaInferrer;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.exception.ExceptionUtils;
import com.liferay.talend.exception.MalformedURLException;
import com.liferay.talend.openapi.Parameter;
import com.liferay.talend.openapi.constants.OpenAPIConstants;
import com.liferay.talend.runtime.client.RESTClient;
import com.liferay.talend.utils.URIUtils;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.i18n.TranslatableImpl;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class LiferaySourceOrSink
	extends TranslatableImpl
	implements LiferaySourceOrSinkRuntime, SourceOrSink {

	public JsonObject doDeleteRequest(RuntimeContainer runtimeContainer) {
		return doDeleteRequest(runtimeContainer, null);
	}

	public JsonObject doDeleteRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executeDeleteRequest();

		return response.readEntity(JsonObject.class);
	}

	public JsonObject doDeleteRequest(String resourceURL) {
		return doDeleteRequest(null, resourceURL);
	}

	public JsonObject doGetRequest(RuntimeContainer runtimeContainer) {
		return doGetRequest(runtimeContainer, null);
	}

	public JsonObject doGetRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executeGetRequest();

		return response.readEntity(JsonObject.class);
	}

	public JsonObject doGetRequest(String resourceURL) {
		return doGetRequest(null, resourceURL);
	}

	public JsonObject doPatchRequest(
			RuntimeContainer runtimeContainer, JsonObject jsonNode)
		throws IOException {

		return doPatchRequest(runtimeContainer, null, jsonNode);
	}

	public JsonObject doPatchRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonObject jsonNode) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executePatchRequest(jsonNode);

		return response.readEntity(JsonObject.class);
	}

	public JsonObject doPatchRequest(String resourceURL, JsonObject jsonNode) {
		return doPatchRequest(null, resourceURL, jsonNode);
	}

	public JsonObject doPostRequest(
		RuntimeContainer runtimeContainer, JsonObject jsonNode) {

		return doPostRequest(runtimeContainer, null, jsonNode);
	}

	public JsonObject doPostRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonObject jsonNode) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executePostRequest(jsonNode);

		return response.readEntity(JsonObject.class);
	}

	public JsonObject doPostRequest(String resourceURL, JsonObject jsonNode)
		throws IOException {

		return doPostRequest(null, resourceURL, jsonNode);
	}

	public LiferayConnectionProperties getConnectionProperties() {
		LiferayConnectionProperties liferayConnectionProperties =
			liferayConnectionPropertiesProvider.
				getLiferayConnectionProperties();

		if (liferayConnectionProperties.getReferencedComponentId() != null) {
			liferayConnectionProperties =
				liferayConnectionProperties.getReferencedConnectionProperties();
		}

		return liferayConnectionProperties;
	}

	public LiferayConnectionProperties getEffectiveConnection(
		RuntimeContainer runtimeContainer) {

		LiferayConnectionProperties liferayConnectionProperties =
			liferayConnectionPropertiesProvider.
				getLiferayConnectionProperties();

		String referencedComponentId =
			liferayConnectionProperties.getReferencedComponentId();

		// Using another component's connection

		if (referencedComponentId != null) {

			// In a runtime container

			if (runtimeContainer != null) {
				LiferayConnectionProperties sharedLiferayConnectionProperties =
					(LiferayConnectionProperties)
						runtimeContainer.getComponentData(
							referencedComponentId, KEY_CONNECTION_PROPERTIES);

				if (sharedLiferayConnectionProperties != null) {
					return sharedLiferayConnectionProperties;
				}
			}

			// Design time

			liferayConnectionProperties =
				liferayConnectionProperties.getReferencedConnectionProperties();
		}

		if (runtimeContainer != null) {
			runtimeContainer.setComponentData(
				runtimeContainer.getCurrentComponentId(),
				KEY_CONNECTION_PROPERTIES, liferayConnectionProperties);
		}

		return liferayConnectionProperties;
	}

	@Override
	public Set<String> getEndpointList(String operation) {
		Set<String> endpoints = new TreeSet<>();

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		JsonObject apiSpecJsonObject = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		JsonObject pathsJsonObject = apiSpecJsonObject.getJsonObject(
			OpenAPIConstants.PATHS);

		pathsJsonObject.forEach(
			(path, operationJsonValue) -> {
				JsonObject operationsJsonObject =
					operationJsonValue.asJsonObject();

				operationsJsonObject.forEach(
					(operationName, operationJsonObject) -> {
						if (!Objects.equals(
								operation, _toUpperCase(operationName))) {

							return;
						}

						if (!Objects.equals(operation, HttpMethod.GET)) {
							endpoints.add(path);

							return;
						}

						if (_hasPath(
								OpenAPIConstants.PATH_RESPONSE_SCHEMA_REFERENCE,
								operationsJsonObject) ||
							_hasPath(
								OpenAPIConstants.
									PATH_RESPONSE_SCHEMA_ITEMS_REFERENCE,
								operationsJsonObject)) {

							endpoints.add(path);
						}
					});
			});

		return endpoints;
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public Schema getEndpointSchema(
			RuntimeContainer runtimeContainer, String endpoint)
		throws IOException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Schema getEndpointSchema(String endpoint, String operation)
		throws IOException {

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		JsonObject apiSpecJsonNode = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		return _endpointSchemaInferrer.inferSchema(
			endpoint, operation, apiSpecJsonNode);
	}

	@Override
	public List<Parameter> getParameters(String endpoint, String operation) {
		List<Parameter> parameters = new ArrayList<>();

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		String apiSpecURLHref = liferayConnectionProperties.getApiSpecURL();

		JsonObject apiSpecJsonObject = doGetRequest(apiSpecURLHref);

		JsonArray parametersArrayNode = _getDescendantArray(
			OpenAPIConstants.PATHS + ">" + endpoint + ">" +
				_toLowerCase(operation) + ">" + OpenAPIConstants.PARAMETRERS,
			apiSpecJsonObject);

		for (int i = 0; i < parametersArrayNode.size(); i++) {
			parameters.add(_toParameter(parametersArrayNode.getJsonObject(i)));
		}

		return parameters;
	}

	public RESTClient getRestClient(RuntimeContainer runtimeContainer) {
		return getRestClient(runtimeContainer, null);
	}

	public RESTClient getRestClient(
		RuntimeContainer runtimeContainer, String resourceURL) {

		if ((resourceURL == null) || resourceURL.isEmpty()) {
			if (restClient != null) {
				return restClient;
			}

			restClient = new RESTClient(
				getEffectiveConnection(runtimeContainer));

			return restClient;
		}

		if ((restClient != null) && restClient.matches(resourceURL)) {
			return restClient;
		}

		return new RESTClient(
			getEffectiveConnection(runtimeContainer), resourceURL);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public List<NamedThing> getSchemaNames(RuntimeContainer runtimeContainer)
		throws IOException {

		throw new UnsupportedOperationException();
	}

	public Set<String> getSupportedOperations(String endpoint) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		JsonObject apiSpecJsonObject = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		JsonObject endpointJsonObject = apiSpecJsonObject.getJsonObject(
			OpenAPIConstants.PATHS
		).getJsonObject(
			endpoint
		);

		return endpointJsonObject.keySet();
	}

	@Override
	public ValidationResult initialize(
		RuntimeContainer runtimeContainer,
		ComponentProperties componentProperties) {

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		liferayConnectionPropertiesProvider =
			(LiferayConnectionPropertiesProvider)componentProperties;

		validationResultMutable.setStatus(ValidationResult.Result.OK);

		try {
			getRestClient(runtimeContainer);
		}
		catch (TalendRuntimeException tre) {
			return ExceptionUtils.exceptionToValidationResult(tre);
		}

		return validationResultMutable;
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		try {
			URIUtils.validateOpenAPISpecURL(
				liferayConnectionProperties.getApiSpecURL());
		}
		catch (MalformedURLException murle) {
			return new ValidationResult(
				ValidationResult.Result.ERROR, murle.getMessage());
		}

		String target = liferayConnectionProperties.getApiSpecURL();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Validate API spec URL: {}",
				liferayConnectionProperties.getApiSpecURL());
			_log.debug(
				"Validate user ID: {}",
				liferayConnectionProperties.getUserId());
		}

		if (_isNullString(target)) {
			return new ValidationResult(
				ValidationResult.Result.ERROR,
				i18nMessages.getMessage(
					"error.validation.connection.apiSpecURL"));
		}

		if (_isNullString(liferayConnectionProperties.getUserId()) ||
			_isNullString(liferayConnectionProperties.getPassword())) {

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				i18nMessages.getMessage(
					"error.validation.connection.credentials"));
		}

		return validateConnection(
			liferayConnectionProperties, runtimeContainer);
	}

	@Override
	public ValidationResult validateConnection(
		LiferayConnectionPropertiesProvider liferayConnectionPropertiesProvider,
		RuntimeContainer runtimeContainer) {

		try {
			doGetRequest(runtimeContainer);

			return new ValidationResult(
				ValidationResult.Result.OK,
				i18nMessages.getMessage("success.validation.connection"));
		}
		catch (TalendRuntimeException tre) {
			_log.error(tre.getMessage(), tre);

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				i18nMessages.getMessage(
					"error.validation.connection.testconnection",
					tre.getLocalizedMessage()));
		}
		catch (ProcessingException pe) {
			_log.error(pe.getMessage(), pe);

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				i18nMessages.getMessage(
					"error.validation.connection.testconnection.jersey",
					pe.getLocalizedMessage()));
		}
		catch (Throwable t) {
			_log.error(t.getMessage(), t);

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				i18nMessages.getMessage(
					"error.validation.connection.testconnection.general",
					t.getLocalizedMessage()));
		}
	}

	protected static final String KEY_CONNECTION_PROPERTIES = "Connection";

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferaySourceOrSink.class);
	}

	protected volatile LiferayConnectionPropertiesProvider
		liferayConnectionPropertiesProvider;
	protected RESTClient restClient;

	private JsonArray _getDescendantArray(String path, JsonObject jsonObject) {
		if (!path.contains(">")) {
			if (jsonObject.containsKey(path)) {
				return jsonObject.getJsonArray(path);
			}
		}

		int subpathEndIdx = path.indexOf(">");

		String subpath = path.substring(0, subpathEndIdx);

		if (jsonObject.containsKey(subpath)) {
			return _getDescendantArray(
				path.substring(subpathEndIdx + 1),
				jsonObject.getJsonObject(subpath));
		}

		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		return jsonArrayBuilder.build();
	}

	private boolean _hasPath(String path, JsonObject jsonNode) {
		if (!path.contains(">")) {
			return jsonNode.containsKey(path);
		}

		int subpathEndIdx = path.indexOf(">");

		String subpath = path.substring(0, subpathEndIdx);

		if (jsonNode.containsKey(subpath)) {
			return _hasPath(
				path.substring(subpathEndIdx + 1),
				jsonNode.getJsonObject(subpath));
		}

		return false;
	}

	private boolean _isNullString(String value) {
		if (value == null) {
			return true;
		}

		value = value.trim();

		if (value.isEmpty()) {
			return true;
		}

		return false;
	}

	private String _toLowerCase(String value) {
		return value.toLowerCase(Locale.US);
	}

	private Parameter _toParameter(JsonObject jsonObject) {
		Parameter parameter = new Parameter();

		parameter.setType(
			Parameter.Type.valueOf(_toUpperCase(jsonObject.getString("in"))));

		parameter.setName(jsonObject.getString("name"));

		if (!jsonObject.isNull("required")) {
			parameter.setRequired(jsonObject.getBoolean("required"));
		}

		return parameter;
	}

	private String _toUpperCase(String value) {
		return value.toUpperCase(Locale.getDefault());
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferaySourceOrSink.class);

	private static final long serialVersionUID = 3109815759807236523L;

	private final EndpointSchemaInferrer _endpointSchemaInferrer =
		new EndpointSchemaInferrer();

}