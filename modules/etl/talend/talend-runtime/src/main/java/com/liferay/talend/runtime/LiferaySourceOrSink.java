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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import com.liferay.talend.avro.EndpointSchemaInferrer;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.exception.ExceptionUtils;
import com.liferay.talend.exception.MalformedURLException;
import com.liferay.talend.openapi.constants.OpenApiConstants;
import com.liferay.talend.runtime.client.RESTClient;
import com.liferay.talend.utils.URIUtils;

import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.i18n.TranslatableImpl;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;

/**
 * @author Zoltán Takács
 */
public class LiferaySourceOrSink
	extends TranslatableImpl
	implements LiferaySourceOrSinkRuntime, SourceOrSink {

	public JsonNode doDeleteRequest(RuntimeContainer runtimeContainer) {
		return doDeleteRequest(runtimeContainer, null);
	}

	public JsonNode doDeleteRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executeDeleteRequest();

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doDeleteRequest(String resourceURL) {
		return doDeleteRequest(null, resourceURL);
	}

	public JsonNode doGetRequest(RuntimeContainer runtimeContainer) {
		return doGetRequest(runtimeContainer, null);
	}

	public JsonNode doGetRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executeGetRequest();

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doGetRequest(String resourceURL) {
		return doGetRequest(null, resourceURL);
	}

	public JsonNode doPatchRequest(
			RuntimeContainer runtimeContainer, JsonNode jsonNode)
		throws IOException {

		return doPatchRequest(runtimeContainer, null, jsonNode);
	}

	public JsonNode doPatchRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonNode jsonNode) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executePatchRequest(jsonNode);

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doPatchRequest(String resourceURL, JsonNode jsonNode) {
		return doPatchRequest(null, resourceURL, jsonNode);
	}

	public JsonNode doPostRequest(
		RuntimeContainer runtimeContainer, JsonNode jsonNode) {

		return doPostRequest(runtimeContainer, null, jsonNode);
	}

	public JsonNode doPostRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonNode jsonNode) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executePostRequest(jsonNode);

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doPostRequest(String resourceURL, JsonNode jsonNode)
		throws IOException {

		return doPostRequest(null, resourceURL, jsonNode);
	}

	@Override
	public List<NamedThing> getAvailableWebSites() throws IOException {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		URL serverURL = liferayConnectionProperties.getServerURL();

		UriBuilder uriBuilder = UriBuilder.fromPath(serverURL.toExternalForm());

		URI myUserAccountURI = uriBuilder.path(
			"o/headless-admin-user/v1.0/my-user-account"
		).build();

		JsonNode myUserAccountJsonNode = doGetRequest(
			myUserAccountURI.toASCIIString());

		JsonNode siteBriefsJsonNode = myUserAccountJsonNode.path("siteBriefs");

		List<NamedThing> webSitesList = new ArrayList<>();

		if (!siteBriefsJsonNode.isArray() &&
			(((ArrayNode)siteBriefsJsonNode).size() == 0)) {

			return webSitesList;
		}

		for (JsonNode siteBriefJsonNode : siteBriefsJsonNode) {
			JsonNode idJsonNode = siteBriefJsonNode.path("id");
			JsonNode nameJsonNode = siteBriefJsonNode.path("name");

			webSitesList.add(
				new SimpleNamedThing(
					idJsonNode.asText(), nameJsonNode.asText()));
		}

		Comparator<NamedThing> comparator = Comparator.comparing(
			NamedThing::getDisplayName);

		Collections.sort(webSitesList, comparator);

		return webSitesList;
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

	/**
	 * If referenceComponentId is not <code>null</code>, it should return the
	 * reference connection properties
	 *
	 * @review
	 */
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

		String apiSpecURLHref =
			liferayConnectionProperties.apiSpecURL.getValue();

		JsonNode apiSpecJsonNode = doGetRequest(apiSpecURLHref);

		JsonNode pathsJsonNode = apiSpecJsonNode.path(OpenApiConstants.PATHS);

		Iterator<Map.Entry<String, JsonNode>> fields = pathsJsonNode.fields();

		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> endpoint = fields.next();

			JsonNode endpointJsonNode = endpoint.getValue();

			boolean checkForSchemaReference = HttpMethod.GET.equals(operation);

			if (endpointJsonNode.has(operation.toLowerCase(Locale.US))) {
				boolean hasSchemaReference = endpointJsonNode.path(
					operation.toLowerCase(Locale.US)
				).path(
					OpenApiConstants.RESPONSES
				).path(
					OpenApiConstants.DEFAULT
				).path(
					OpenApiConstants.CONTENT
				).path(
					OpenApiConstants.APPLICATION_JSON
				).path(
					OpenApiConstants.SCHEMA
				).has(
					OpenApiConstants.REF
				);

				if (checkForSchemaReference) {
					if (hasSchemaReference) {
						endpoints.add(endpoint.getKey());
					}
				}
				else {
					endpoints.add(endpoint.getKey());
				}
			}
		}

		return endpoints;
	}

	/**
	 * This method is not used in Liferay Component family
	 * @deprecated As of Mueller (7.2.x), beginning, see {@link #getEndpointSchema(String, String)} for
	 * implementation details
	 *
	 * @param runtimeContainer
	 * @param endpoint
	 * @return
	 * @throws IOException
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

		String apiSpecURLHref =
			liferayConnectionProperties.apiSpecURL.getValue();

		JsonNode apiSpecJsonNode = doGetRequest(apiSpecURLHref);

		return EndpointSchemaInferrer.inferSchema(
			endpoint, operation, apiSpecJsonNode);
	}

	public RESTClient getRestClient(RuntimeContainer runtimeContainer) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		if (restClient == null) {
			restClient = new RESTClient(liferayConnectionProperties);
		}
		else {
			String target = restClient.getTarget();

			if (!target.equals(
					liferayConnectionProperties.apiSpecURL.getValue())) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Target URI has been changed, initialize a new " +
							"RESTClient");
				}

				restClient = new RESTClient(liferayConnectionProperties);

				return restClient;
			}
		}

		return restClient;
	}

	public RESTClient getRestClient(
		RuntimeContainer runtimeContainer, String resourceURL) {

		if ((resourceURL == null) || resourceURL.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Resource URL was null or empty value, fall back to the " +
						"connection property");
			}

			return getRestClient(runtimeContainer);
		}

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		if (_log.isDebugEnabled()) {
			_log.debug("New REST Client with \"{}\" endpoint", resourceURL);
		}

		return new RESTClient(resourceURL, liferayConnectionProperties);
	}

	/**
	 * This method is not used in Liferay Component family
	 * @deprecated As of Mueller (7.2.x), beginning, see {@link #getEndpointList(String)} for
	 * implementation details
	 *
	 * @param runtimeContainer
	 * @return
	 * @throws IOException
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

		String apiSpecURLHref =
			liferayConnectionProperties.apiSpecURL.getValue();

		JsonNode apiSpecJsonNode = doGetRequest(apiSpecURLHref);

		JsonNode endpointJsonNode = apiSpecJsonNode.path(
			OpenApiConstants.PATHS
		).path(
			endpoint
		);

		Iterator<String> operationsIterator = endpointJsonNode.fieldNames();

		Set<String> supportedOperations = new TreeSet<>();

		operationsIterator.forEachRemaining(supportedOperations::add);

		return supportedOperations;
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

		String apiSpecHref = liferayConnectionProperties.apiSpecURL.getValue();

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		try {
			URIUtils.validateOpenAPISpecURL(apiSpecHref);
		}
		catch (MalformedURLException murle) {
			validationResultMutable.setMessage(murle.getMessage());
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			return validationResultMutable;
		}

		boolean anonymousLogin =
			liferayConnectionProperties.anonymousLogin.getValue();
		String target = liferayConnectionProperties.apiSpecURL.getValue();
		String password = liferayConnectionProperties.password.getValue();

		String userId = liferayConnectionProperties.userId.getValue();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Validate API Spec URL: {}",
				liferayConnectionProperties.apiSpecURL.getValue());
			_log.debug(
				"Validate user ID: {}",
				liferayConnectionProperties.userId.getValue());
		}

		if ((target == null) || target.isEmpty()) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.apiSpecURL"));

			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			return validationResultMutable;
		}

		if (!anonymousLogin) {
			_validateCredentials(userId, password, validationResultMutable);

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.ERROR) {

				return validationResultMutable;
			}
		}

		return validateConnection(
			liferayConnectionProperties, runtimeContainer);
	}

	@Override
	public ValidationResult validateConnection(
		LiferayConnectionPropertiesProvider liferayConnectionPropertiesProvider,
		RuntimeContainer runtimeContainer) {

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		validationResultMutable.setStatus(ValidationResult.Result.OK);

		try {
			doGetRequest(runtimeContainer);

			validationResultMutable.setMessage(
				i18nMessages.getMessage("success.validation.connection"));
		}
		catch (TalendRuntimeException tre) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection",
					tre.getLocalizedMessage()));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			_log.error(tre.getMessage(), tre);
		}
		catch (ProcessingException pe) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection.jersey",
					pe.getLocalizedMessage()));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			_log.error(pe.getMessage(), pe);
		}
		catch (Throwable t) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection.general",
					t.getLocalizedMessage()));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			_log.error(t.getMessage(), t);
		}

		return validationResultMutable;
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
	protected final ObjectMapper objectMapper = new ObjectMapper();
	protected RESTClient restClient;

	private void _validateCredentials(
		String userId, String password,
		ValidationResultMutable validationResultMutable) {

		if (_log.isDebugEnabled()) {
			_log.debug("Validating credentials...");
		}

		if ((userId == null) || userId.isEmpty()) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage("error.validation.connection.userId"));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			return;
		}

		if ((password == null) || password.isEmpty()) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.password"));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferaySourceOrSink.class);

	private static final long serialVersionUID = 3109815759807236523L;

}