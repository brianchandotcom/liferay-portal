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

package com.liferay.consumer.talend.runtime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.consumer.talend.avro.ResourceCollectionSchemaInferrer;
import com.liferay.consumer.talend.connection.LiferayConnectionProperties;
import com.liferay.consumer.talend.connection.LiferayProvideConnectionProperties;
import com.liferay.consumer.talend.runtime.client.ApioException;
import com.liferay.consumer.talend.runtime.client.ApioResult;
import com.liferay.consumer.talend.runtime.client.RestClient;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	implements SourceOrSink, LiferaySourceOrSinkRuntime {

	public LiferayConnectionProperties getConnectionProperties() {
		LiferayConnectionProperties connectionProperties =
			properties.getConnectionProperties();

		if (connectionProperties.getReferencedComponentId() != null) {
			connectionProperties =
				connectionProperties.getReferencedConnectionProperties();
		}

		return connectionProperties;
	}

	/**
	 * If referenceComponentId is not null, it should return the reference
	 * connection properties
	 */
	public LiferayConnectionProperties getEffectiveConnection(
		RuntimeContainer container) {

		LiferayConnectionProperties connProps =
			properties.getConnectionProperties();

		String refComponentId = connProps.getReferencedComponentId();

		// Using another component's connection

		if (refComponentId != null) {

			// In a runtime container

			if (container != null) {
				LiferayConnectionProperties shared =
					(LiferayConnectionProperties)container.getComponentData(
						refComponentId, KEY_CONNECTION_PROPERTIES);

				if (shared != null) {
					return shared;
				}
			}

			// Design time

			connProps = connProps.getReferencedConnectionProperties();
		}

		if (container != null) {
			container.setComponentData(
				container.getCurrentComponentId(), KEY_CONNECTION_PROPERTIES,
				connProps);
		}

		return connProps;
	}

	@Override
	public Schema getEndpointSchema(
			RuntimeContainer container, String resourceURL)
		throws IOException {

		return guessSchema(resourceURL);
	}

	public JsonNode getResourceCollection(String resourceURL)
		throws IOException {

		final ObjectMapper mapper = new ObjectMapper();

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
			jsonNode = mapper.readTree(apioResult.getBody());
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Cannot read JSON object", ioe);
			}

			throw ioe;
		}

		return jsonNode;
	}

	public RestClient getRestClient(RuntimeContainer container)
		throws ApioException {

		LiferayConnectionProperties conn = getEffectiveConnection(container);

		if (client == null) {
			client = new RestClient(conn);
		}
		else {
			if (!client.getEndpoint().equals(conn.endpoint.getValue())) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Endpoint has been changed, initialize a new " +
							"RestClient");
				}

				client = new RestClient(conn);

				return client;
			}
		}

		return client;
	}

	public RestClient getRestClient(
			RuntimeContainer container, String resourceURL)
		throws ApioException {

		LiferayConnectionProperties conn = getEffectiveConnection(container);

		conn.endpoint.setValue(resourceURL);

		if (_log.isDebugEnabled()) {
			_log.debug("New REST Client with \"{}\" endpoint", resourceURL);
		}

		return new RestClient(conn);
	}

	@Override
	public List<NamedThing> getSchemaNames(RuntimeContainer container)
		throws IOException {

		List<NamedThing> returnList = new ArrayList<>();

		Map<String, String> map = _getResourceCollections(container);

		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"resource name: {}, href: {} ", entry.getKey(),
					entry.getValue());
			}

			returnList.add(
				new SimpleNamedThing(entry.getKey(), entry.getValue()));
		}

		return returnList;
	}

	@Override
	public Schema guessSchema(String resourceURL) throws IOException {
		return _getResourceCollectionSchema(resourceURL);
	}

	@Override
	public ValidationResult initialize(
		RuntimeContainer container, ComponentProperties properties) {

		ValidationResultMutable vr = new ValidationResultMutable();

		this.properties = (LiferayProvideConnectionProperties)properties;
		vr.setStatus(ValidationResult.Result.OK);

		try {
			getRestClient(container);
		}
		catch (ApioException ae) {
			vr.setStatus(ValidationResult.Result.ERROR);
		}

		return vr;
	}

	@Override
	public ValidationResult validate(RuntimeContainer container) {
		LiferayConnectionProperties conn = getEffectiveConnection(container);

		String endpoint = conn.endpoint.getValue();
		String userId = conn.userId.getValue();
		String password = conn.password.getValue();
		boolean anonymousLogin = conn.anonymousLogin.getValue();

		if (_log.isDebugEnabled()) {
			_log.debug("Validate Endpoint: {}", conn.endpoint.getValue());
			_log.debug("Validate UserID: {}", conn.userId.getValue());
		}

		ValidationResultMutable vr = new ValidationResultMutable();

		if ((endpoint == null) || endpoint.isEmpty()) {
			vr.setMessage(
				translations.getMessage(
					"error.validation.connection.endpoint"));

			vr.setStatus(ValidationResult.Result.ERROR);

			return vr;
		}

		if (!anonymousLogin) {
			vr.setStatus(
				_validateCredentials(userId, password, vr).getStatus());

			if (vr.getStatus() == ValidationResult.Result.ERROR) {
				return vr;
			}
		}

		return validateConnection(conn);
	}

	@Override
	public ValidationResult validateConnection(
		LiferayProvideConnectionProperties properties) {

		ValidationResultMutable vr =
			new ValidationResultMutable().setStatus(Result.OK);

		try {
			LiferaySourceOrSink sos = new LiferaySourceOrSink();

			sos.initialize(null, (LiferayConnectionProperties)properties);
			RestClient restClient = sos.getRestClient(null);

			restClient.executeGetRequest();

			vr.setMessage(
				translations.getMessage("success.validation.connection"));
		}
		catch (ApioException ae) {
			vr.setStatus(Result.ERROR);
			vr.setMessage(
				translations.getMessage(
					"error.validation.connection.testconnection",
					ae.getLocalizedMessage(), ae.getCode()));
		}

		return vr;
	}

	protected Map<String, String> getApioLdJsonResourceCollectionsDescriptor(
		JsonNode node) {

		Map<String, String> resourcesMap = new HashMap<>();

		JsonNode resources = node.findPath("resources");

		Iterator<String> fieldNames = resources.fieldNames();

		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();

			JsonNode fieldValue = resources.get(fieldName);

			if (fieldValue.has("href")) {
				String href = fieldValue.get("href").asText();

				resourcesMap.put(href, fieldName);
			}
		}

		return resourcesMap;
	}

	protected static final String KEY_CONNECTION_PROPERTIES = "Connection";

	protected static final I18nMessages translations =
		GlobalI18N.getI18nMessageProvider().getI18nMessages(
			LiferaySourceOrSink.class);

	protected RestClient client;
	protected volatile LiferayProvideConnectionProperties properties;

	private Map<String, String> _getResourceCollections(
		RuntimeContainer container) {

		final ObjectMapper mapper = new ObjectMapper();

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
			jsonNode = mapper.readTree(apioResult.getBody());
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

		return ResourceCollectionSchemaInferrer.inferSchema(jsonNode);
	}

	private ValidationResultMutable _validateCredentials(
		String userId, String password, ValidationResultMutable vr) {

		if (_log.isDebugEnabled()) {
			_log.debug("Validating credentials...");
		}

		if ((userId == null) || userId.isEmpty()) {
			vr.setMessage(
				translations.getMessage("error.validation.connection.userId"));
			vr.setStatus(ValidationResult.Result.ERROR);

			return vr;
		}

		if ((password == null) || password.isEmpty()) {
			vr.setMessage(
				translations.getMessage(
					"error.validation.connection.password"));
			vr.setStatus(ValidationResult.Result.ERROR);

			return vr;
		}

		return vr;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferaySourceOrSink.class);

	private static final long serialVersionUID = 3109815759807236523L;

}