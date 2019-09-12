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
import com.liferay.talend.common.exception.MalformedURLException;
import com.liferay.talend.common.json.JsonFinder;
import com.liferay.talend.common.oas.OASParameter;
import com.liferay.talend.common.oas.constants.OASConstants;
import com.liferay.talend.common.util.StringUtil;
import com.liferay.talend.common.util.URIUtil;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.properties.ExceptionUtils;
import com.liferay.talend.runtime.client.RESTClient;
import com.liferay.talend.runtime.client.ResponseHandler;
import com.liferay.talend.runtime.client.exception.ResponseContentClientException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.json.JsonArray;
import javax.json.JsonObject;

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
 * @author Ivica Cardic
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

		return _responseHandler.asJsonObject(restClient.executeDeleteRequest());
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

		return _responseHandler.asJsonObject(restClient.executeGetRequest());
	}

	public JsonObject doGetRequest(String resourceURL) {
		return doGetRequest(null, resourceURL);
	}

	public JsonObject doPatchRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonObject jsonObject) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executePatchRequest(jsonObject);

		if (!_responseHandler.isSuccess(response)) {
			throw new ResponseContentClientException(
				"Request did not succeed", response.getStatus(), null);
		}

		if (!_responseHandler.isApplicationJsonContentType(response)) {
			throw new ResponseContentClientException(
				"Unable to decode response content type " +
					_responseHandler.getContentType(response),
				response.getStatus(), null);
		}

		return _responseHandler.asJsonObject(response);
	}

	public JsonObject doPostRequest(
		RuntimeContainer runtimeContainer, JsonObject jsonObject) {

		return doPostRequest(runtimeContainer, null, jsonObject);
	}

	public JsonObject doPostRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonObject jsonObject) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		return _responseHandler.asJsonObject(
			restClient.executePostRequest(jsonObject));
	}

	public JsonObject doPostRequest(String resourceURL, JsonObject jsonObject)
		throws IOException {

		return doPostRequest(null, resourceURL, jsonObject);
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
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		JsonObject oasJsonObject = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		return _extractEndpoints(operation, oasJsonObject);
	}

	@Override
	public Map<String, String> getEndpointMap(String operation) {
		Map<String, String> endpointMap = new TreeMap<>();

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		JsonObject oasJsonObject = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		Set<String> endpoints = _extractEndpoints(operation, oasJsonObject);

		for (String endpoint : endpoints) {
			endpointMap.put(
				endpoint,
				_endpointSchemaInferrer.extractEndpointSchemaName(
					endpoint, operation, oasJsonObject));
		}

		return endpointMap;
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public Schema getEndpointSchema(
		RuntimeContainer runtimeContainer, String endpoint) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Schema getEndpointSchema(String endpoint, String operation) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		JsonObject oasJsonObject = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		return _endpointSchemaInferrer.inferSchema(
			endpoint, operation, oasJsonObject);
	}

	@Override
	public List<OASParameter> getParameters(String endpoint, String operation) {
		List<OASParameter> oasParameters = new ArrayList<>();

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		String apiSpecURLHref = liferayConnectionProperties.getApiSpecURL();

		JsonObject oasJsonObject = doGetRequest(apiSpecURLHref);

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

		JsonObject oasJsonObject = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		String jsonFinderPath = StringUtil.replace(
			OASConstants.PATH_ENDPOINT_PATTERN, "ENDPOINT_TPL", endpoint);

		JsonObject endpointJsonObject = _jsonFinder.getDescendantJsonObject(
			jsonFinderPath, oasJsonObject);

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
			URIUtil.validateOpenAPISpecURL(
				liferayConnectionProperties.getApiSpecURL());
		}
		catch (MalformedURLException murle) {
			return new ValidationResult(
				ValidationResult.Result.ERROR, murle.getMessage());
		}

		String target = liferayConnectionProperties.getApiSpecURL();

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Validate API spec URL: {}",
				liferayConnectionProperties.getApiSpecURL());
			_logger.debug(
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
			_logger.error(tre.getMessage(), tre);

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				i18nMessages.getMessage(
					"error.validation.connection.testconnection",
					tre.getLocalizedMessage()));
		}
		catch (ProcessingException pe) {
			_logger.error(pe.getMessage(), pe);

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				i18nMessages.getMessage(
					"error.validation.connection.testconnection.jersey",
					pe.getLocalizedMessage()));
		}
		catch (Throwable t) {
			_logger.error(t.getMessage(), t);

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

	private Set<String> _extractEndpoints(
		String operation, JsonObject oasJsonObject) {

		Set<String> endpoints = new TreeSet<>();

		JsonObject pathsJsonObject = oasJsonObject.getJsonObject(
			OASConstants.PATHS);

		pathsJsonObject.forEach(
			(path, operationsJsonValue) -> {
				JsonObject operationsJsonObject =
					operationsJsonValue.asJsonObject();

				operationsJsonObject.forEach(
					(operationName, operationJsonValue) -> {
						if (!Objects.equals(operation, operationName)) {
							return;
						}

						if (!Objects.equals(
								operation, OASConstants.OPERATION_GET)) {

							endpoints.add(path);

							return;
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

	private OASParameter _toParameter(JsonObject jsonObject) {
		OASParameter oasParameter = new OASParameter(
			jsonObject.getString("name"), jsonObject.getString("in"));

		if (jsonObject.containsKey("required")) {
			oasParameter.setRequired(jsonObject.getBoolean("required"));
		}

		return oasParameter;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferaySourceOrSink.class);

	private static final long serialVersionUID = 3109815759807236523L;

	private final EndpointSchemaInferrer _endpointSchemaInferrer =
		new EndpointSchemaInferrer();
	private final JsonFinder _jsonFinder = new JsonFinder();
	private final ResponseHandler _responseHandler = new ResponseHandler();

}