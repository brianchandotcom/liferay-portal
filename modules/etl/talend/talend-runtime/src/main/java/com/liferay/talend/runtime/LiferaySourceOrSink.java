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

import com.liferay.talend.avro.ExpectedFormSchemaInferrer;
import com.liferay.talend.avro.ResourceCollectionSchemaInferrer;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayProvideConnectionProperties;
import com.liferay.talend.runtime.apio.ApioException;
import com.liferay.talend.runtime.apio.ApioResult;
import com.liferay.talend.runtime.apio.jsonld.ApioForm;
import com.liferay.talend.runtime.apio.jsonld.ApioResourceCollection;
import com.liferay.talend.runtime.apio.jsonld.JSONLDConstants;
import com.liferay.talend.runtime.apio.operation.Operation;
import com.liferay.talend.runtime.client.RestClient;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.ProcessingException;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.i18n.TranslatableImpl;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResult.Result;
import org.talend.daikon.properties.ValidationResultMutable;

/**
 * @author Zoltán Takács
 */
public class LiferaySourceOrSink
	extends TranslatableImpl
	implements LiferaySourceOrSinkRuntime, SourceOrSink {

	public JsonNode doApioDeleteRequest(RuntimeContainer runtimeContainer)
		throws IOException {

		return doApioDeleteRequest(runtimeContainer, null);
	}

	public JsonNode doApioDeleteRequest(
			RuntimeContainer runtimeContainer, String resourceURL)
		throws IOException {

		RestClient restClient = null;
		ApioResult apioResult = null;

		try {
			restClient = getRestClient(runtimeContainer, resourceURL);

			apioResult = restClient.executeDeleteRequest();
		}
		catch (ApioException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae.toString());
			}

			throw new IOException(ae);
		}

		return _deserializeJsonContent(apioResult);
	}

	public JsonNode doApioDeleteRequest(String resourceURL) throws IOException {
		return doApioDeleteRequest(null, resourceURL);
	}

	public JsonNode doApioGetRequest(RuntimeContainer runtimeContainer)
		throws IOException {

		return doApioGetRequest(runtimeContainer, null);
	}

	public JsonNode doApioGetRequest(
			RuntimeContainer runtimeContainer, String resourceURL)
		throws IOException {

		RestClient restClient = null;
		ApioResult apioResult = null;

		try {
			restClient = getRestClient(runtimeContainer, resourceURL);

			apioResult = restClient.executeGetRequest();
		}
		catch (ApioException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae.toString());
			}

			throw new IOException(ae);
		}

		return _deserializeJsonContent(apioResult);
	}

	public JsonNode doApioGetRequest(String resourceURL) throws IOException {
		return doApioGetRequest(null, resourceURL);
	}

	public JsonNode doApioPostRequest(
			RuntimeContainer runtimeContainer, JsonNode apioForm)
		throws IOException {

		return doApioPostRequest(runtimeContainer, null, apioForm);
	}

	public JsonNode doApioPostRequest(
			RuntimeContainer runtimeContainer, String resourceURL,
			JsonNode apioForm)
		throws IOException {

		RestClient restClient = null;
		ApioResult apioResult = null;

		try {
			restClient = getRestClient(runtimeContainer, resourceURL);

			apioResult = restClient.executePostRequest(apioForm);
		}
		catch (ApioException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae.toString());
			}

			throw new IOException(ae);
		}

		return _deserializeJsonContent(apioResult);
	}

	public JsonNode doApioPostRequest(String resourceURL, JsonNode apioForm)
		throws IOException {

		return doApioPostRequest(null, resourceURL, apioForm);
	}

	public JsonNode doApioPutRequest(
			RuntimeContainer runtimeContainer, JsonNode apioForm)
		throws IOException {

		return doApioPutRequest(runtimeContainer, null, apioForm);
	}

	public JsonNode doApioPutRequest(
			RuntimeContainer runtimeContainer, String resourceURL,
			JsonNode apioForm)
		throws IOException {

		RestClient restClient = null;
		ApioResult apioResult = null;

		try {
			restClient = getRestClient(runtimeContainer, resourceURL);

			apioResult = restClient.executePutRequest(apioForm);
		}
		catch (ApioException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae.toString());
			}

			throw new IOException(ae);
		}

		return _deserializeJsonContent(apioResult);
	}

	public JsonNode doApioPutRequest(String resourceURL, JsonNode apioForm)
		throws IOException {

		return doApioPutRequest(null, resourceURL, apioForm);
	}

	public Map<String, String> getApioResourceEndpointsMap(
		RuntimeContainer runtimeContainer) {

		JsonNode jsonNode;

		try {
			jsonNode = doApioGetRequest(runtimeContainer);
		}
		catch (IOException ioe) {
			return Collections.emptyMap();
		}

		return _getResourceCollectionsDescriptor(jsonNode);
	}

	public LiferayConnectionProperties getConnectionProperties() {
		LiferayConnectionProperties liferayConnectionProperties =
			properties.getConnectionProperties();

		if (liferayConnectionProperties.getReferencedComponentId() != null) {
			liferayConnectionProperties =
				liferayConnectionProperties.getReferencedConnectionProperties();
		}

		return liferayConnectionProperties;
	}

	/**
	 * If referenceComponentId is not <code>null</code>, it should return the
	 * reference connection properties
	 */
	public LiferayConnectionProperties getEffectiveConnection(
		RuntimeContainer runtimeContainer) {

		LiferayConnectionProperties liferayConnectionProperties =
			properties.getConnectionProperties();

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
	public Schema getEndpointSchema(
			RuntimeContainer runtimeContainer, String resourceURL)
		throws IOException {

		return getInputResourceCollectionSchema(resourceURL);
	}

	@Override
	public Schema getExpectedFormSchema(NamedThing operation)
		throws IOException {

		JsonNode jsonNode = doApioGetRequest(operation.getTitle());

		ApioForm apioForm = new ApioForm(jsonNode);

		return ExpectedFormSchemaInferrer.inferSchemaByFormProperties(apioForm);
	}

	@Override
	public Schema getInputResourceCollectionSchema(String resourceURL)
		throws IOException {

		return _getResourceCollectionSchema(resourceURL);
	}

	@Override
	public List<NamedThing> getResourceSupportedOperations(String resourceURL)
		throws IOException {

		JsonNode jsonNode = doApioGetRequest(resourceURL);

		ApioResourceCollection apioResourceCollection =
			new ApioResourceCollection(jsonNode);

		List<Operation> collectionOperations =
			apioResourceCollection.getResourceCollectionOperations();

		Stream<Operation> stream = collectionOperations.stream();

		return stream.map(
			operation -> new SimpleNamedThing(
				operation.getId(), operation.getMethod(),
				operation.getExpects())
		).collect(
			Collectors.toList()
		);
	}

	public RestClient getRestClient(RuntimeContainer runtimeContainer)
		throws ApioException {

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		if (client == null) {
			client = new RestClient(liferayConnectionProperties);
		}
		else {
			String endpoint = client.getEndpoint();

			if (!endpoint.equals(
					liferayConnectionProperties.endpoint.getValue())) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Endpoint has been changed, initialize a new " +
							"RestClient");
				}

				client = new RestClient(liferayConnectionProperties);

				return client;
			}
		}

		return client;
	}

	public RestClient getRestClient(
			RuntimeContainer runtimeContainer, String resourceURL)
		throws ApioException {

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

		return new RestClient(resourceURL, liferayConnectionProperties);
	}

	@Override
	public List<NamedThing> getSchemaNames(RuntimeContainer runtimeContainer)
		throws IOException {

		List<NamedThing> schemaNames = new ArrayList<>();

		Map<String, String> resourceCollections = getApioResourceEndpointsMap(
			runtimeContainer);

		for (Map.Entry<String, String> entry : resourceCollections.entrySet()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"resource name: {}, href: {} ", entry.getKey(),
					entry.getValue());
			}

			schemaNames.add(
				new SimpleNamedThing(entry.getKey(), entry.getValue()));
		}

		return schemaNames;
	}

	@Override
	public ValidationResult initialize(
		RuntimeContainer runtimeContainer,
		ComponentProperties componentProperties) {

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		properties = (LiferayProvideConnectionProperties)componentProperties;

		validationResultMutable.setStatus(ValidationResult.Result.OK);

		try {
			getRestClient(runtimeContainer);
		}
		catch (ApioException ae) {
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);
		}

		return validationResultMutable;
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		String endpoint = liferayConnectionProperties.endpoint.getValue();
		String userId = liferayConnectionProperties.userId.getValue();
		String password = liferayConnectionProperties.password.getValue();
		boolean anonymousLogin =
			liferayConnectionProperties.anonymousLogin.getValue();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Validate Endpoint: {}",
				liferayConnectionProperties.endpoint.getValue());
			_log.debug(
				"Validate UserID: {}",
				liferayConnectionProperties.userId.getValue());
		}

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		if ((endpoint == null) || endpoint.isEmpty()) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.endpoint"));

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

		return validateConnection(liferayConnectionProperties);
	}

	@Override
	public ValidationResult validateConnection(
		LiferayProvideConnectionProperties liferayProvideConnectionProperties) {

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		validationResultMutable.setStatus(Result.OK);

		try {
			LiferaySourceOrSink liferaySourceOrSink = new LiferaySourceOrSink();

			liferaySourceOrSink.initialize(
				null,
				(LiferayConnectionProperties)
					liferayProvideConnectionProperties);

			RestClient restClient = liferaySourceOrSink.getRestClient(null);

			restClient.executeGetRequest();

			validationResultMutable.setMessage(
				i18nMessages.getMessage("success.validation.connection"));
		}
		catch (ApioException ae) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection",
					ae.getLocalizedMessage(), ae.getCode()));
			validationResultMutable.setStatus(Result.ERROR);
		}
		catch (ProcessingException pe) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection.jersey",
					pe.getLocalizedMessage()));
			validationResultMutable.setStatus(Result.ERROR);
		}

		return validationResultMutable;
	}

	protected static final String KEY_CONNECTION_PROPERTIES = "Connection";

	protected static final I18nMessages i18nMessages =
		GlobalI18N.getI18nMessageProvider().getI18nMessages(
			LiferaySourceOrSink.class);

	protected RestClient client;
	protected final ObjectMapper objectMapper = new ObjectMapper();
	protected volatile LiferayProvideConnectionProperties properties;

	private JsonNode _deserializeJsonContent(ApioResult apioResult)
		throws IOException {

		JsonNode jsonNode = null;

		if (_log.isDebugEnabled()) {
			_log.debug(apioResult.getBody());
		}

		try {
			jsonNode = objectMapper.readTree(apioResult.getBody());
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read JSON object", ioe);
			}

			throw ioe;
		}

		return jsonNode;
	}

	private Schema _getResourceCollectionSchema(String resourceURL)
		throws IOException {

		JsonNode jsonNode = doApioGetRequest(resourceURL);

		ApioResourceCollection apioResourceCollection =
			new ApioResourceCollection(jsonNode);

		return ResourceCollectionSchemaInferrer.inferSchemaByResourceFields(
			apioResourceCollection);
	}

	private Map<String, String> _getResourceCollectionsDescriptor(
		JsonNode jsonNode) {

		Map<String, String> resourcesMap = new HashMap<>();

		JsonNode resourcesJsonNode = jsonNode.findPath(
			JSONLDConstants.RESOURCES);

		Iterator<String> fieldNames = resourcesJsonNode.fieldNames();

		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();

			JsonNode fieldValue = resourcesJsonNode.get(fieldName);

			if (fieldValue.has(JSONLDConstants.HREF)) {
				JsonNode hrefJsonNode = fieldValue.get(JSONLDConstants.HREF);

				resourcesMap.put(hrefJsonNode.asText(), fieldName);
			}
		}

		return resourcesMap;
	}

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

			return;
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferaySourceOrSink.class);

	private static final long serialVersionUID = 3109815759807236523L;

}