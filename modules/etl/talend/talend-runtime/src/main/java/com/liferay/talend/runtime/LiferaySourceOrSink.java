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

import com.liferay.talend.avro.ResourceCollectionSchemaInferrer;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayProvideConnectionProperties;
import com.liferay.talend.runtime.apio.ApioException;
import com.liferay.talend.runtime.apio.ApioResult;
import com.liferay.talend.runtime.apio.jsonld.ApioJsonLDConstants;
import com.liferay.talend.runtime.apio.jsonld.ApioJsonLDResource;
import com.liferay.talend.runtime.client.RestClient;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	 * If referenceComponentId is not null, it should return the reference
	 * connection properties
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

		return guessSchema(resourceURL);
	}

	public JsonNode getResourceCollection(String resourceURL)
		throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();

		RestClient restClient = null;
		ApioResult apioResult = null;

		try {
			restClient = getRestClient(null, resourceURL);

			apioResult = restClient.executeGetRequest();
		}
		catch (ApioException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae.toString());
			}

			throw new IOException(ae);
		}

		JsonNode jsonNode = null;

		if (_log.isDebugEnabled()) {
			_log.debug(apioResult.getBody());
		}

		try {
			jsonNode = objectMapper.readTree(apioResult.getBody());
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Cannot read JSON object", ioe);
			}

			throw ioe;
		}

		return jsonNode;
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

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		liferayConnectionProperties.endpoint.setValue(resourceURL);

		if (_log.isDebugEnabled()) {
			_log.debug("New REST Client with \"{}\" endpoint", resourceURL);
		}

		return new RestClient(liferayConnectionProperties);
	}

	@Override
	public List<NamedThing> getSchemaNames(RuntimeContainer runtimeContainer)
		throws IOException {

		List<NamedThing> schemaNames = new ArrayList<>();

		Map<String, String> resourceCollections = _getExposedResourcesMap(
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
	public Schema guessSchema(String resourceURL) throws IOException {
		return _getResourceCollectionSchema(resourceURL);
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
		LiferayConnectionProperties conn = getEffectiveConnection(
			runtimeContainer);

		String endpoint = conn.endpoint.getValue();
		String userId = conn.userId.getValue();
		String password = conn.password.getValue();
		boolean anonymousLogin = conn.anonymousLogin.getValue();

		if (_log.isDebugEnabled()) {
			_log.debug("Validate Endpoint: {}", conn.endpoint.getValue());
			_log.debug("Validate UserID: {}", conn.userId.getValue());
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

		return validateConnection(conn);
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

	protected Map<String, String> getApioLdJsonResourceCollectionsDescriptor(
		JsonNode jsonNode) {

		Map<String, String> resourcesMap = new HashMap<>();

		JsonNode resourcesJsonNode = jsonNode.findPath(
			ApioJsonLDConstants.RESOURCES);

		Iterator<String> fieldNames = resourcesJsonNode.fieldNames();

		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();

			JsonNode fieldValue = resourcesJsonNode.get(fieldName);

			if (fieldValue.has(ApioJsonLDConstants.HREF)) {
				JsonNode hrefJsonNode = fieldValue.get(
					ApioJsonLDConstants.HREF);

				resourcesMap.put(hrefJsonNode.asText(), fieldName);
			}
		}

		return resourcesMap;
	}

	protected static final String KEY_CONNECTION_PROPERTIES = "Connection";

	protected static final I18nMessages i18nMessages =
		GlobalI18N.getI18nMessageProvider().getI18nMessages(
			LiferaySourceOrSink.class);

	protected RestClient client;
	protected volatile LiferayProvideConnectionProperties properties;

	private Map<String, String> _getExposedResourcesMap(
		RuntimeContainer container) {

		ObjectMapper objectMapper = new ObjectMapper();

		RestClient restClient = null;
		ApioResult apioResult = null;

		try {
			restClient = getRestClient(container);

			apioResult = restClient.executeGetRequest();
		}
		catch (ApioException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae.toString());
			}

			return Collections.emptyMap();
		}

		JsonNode jsonNode = null;

		try {
			jsonNode = objectMapper.readTree(apioResult.getBody());
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Cannot read JSON object", ioe);
			}

			return Collections.emptyMap();
		}

		return getApioLdJsonResourceCollectionsDescriptor(jsonNode);
	}

	private Schema _getResourceCollectionSchema(String resourceURL)
		throws IOException {

		JsonNode jsonNode = getResourceCollection(resourceURL);

		ApioJsonLDResource resource = new ApioJsonLDResource(jsonNode);

		return ResourceCollectionSchemaInferrer.inferSchema(resource);
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