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
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.runtime.client.RESTClient;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import org.apache.avro.Schema;
import org.apache.commons.lang3.StringUtils;

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

	public JsonNode doApioDeleteRequest(RuntimeContainer runtimeContainer) {
		return doApioDeleteRequest(runtimeContainer, null);
	}

	public JsonNode doApioDeleteRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executeDeleteRequest();

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doApioDeleteRequest(String resourceURL) {
		return doApioDeleteRequest(null, resourceURL);
	}

	public JsonNode doApioGetRequest(RuntimeContainer runtimeContainer) {
		return doApioGetRequest(runtimeContainer, null);
	}

	public JsonNode doApioGetRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executeGetRequest();

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doApioGetRequest(String resourceURL) {
		return doApioGetRequest(null, resourceURL);
	}

	public JsonNode doApioPostRequest(
		RuntimeContainer runtimeContainer, JsonNode apioForm) {

		return doApioPostRequest(runtimeContainer, null, apioForm);
	}

	public JsonNode doApioPostRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonNode apioForm) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executePostRequest(apioForm);

		return response.readEntity(JsonNode.class);
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
		JsonNode apioForm) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executePutRequest(apioForm);

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doApioPutRequest(String resourceURL, JsonNode apioForm) {
		return doApioPutRequest(null, resourceURL, apioForm);
	}

	@Override
	public String getActualWebSiteName(String webSiteURL) throws IOException {
		JsonNode webSiteNameJsonNode = doApioGetRequest(webSiteURL);

		webSiteNameJsonNode = webSiteNameJsonNode.path("name");

		return webSiteNameJsonNode.asText();
	}

	public Map<String, String> getApioResourceEndpointsMap(
		RuntimeContainer runtimeContainer) {

		JsonNode jsonNode = doApioGetRequest(runtimeContainer);

		if (jsonNode.size() == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to find any exposed resources");
			}

			return Collections.emptyMap();
		}

		return Collections.emptyMap();
	}

	@Override
	public List<NamedThing> getAvailableWebSites() throws IOException {
		String webSitesEndpointURL = null;
		List<NamedThing> webSitesList = new ArrayList<>();

		try {
			webSitesEndpointURL = _getWebSitesEndpointURL();
		}
		catch (NoSuchElementException nsee) {
			return webSitesList;
		}

		JsonNode resourceCollectionJsonNode = doApioGetRequest(
			webSitesEndpointURL);

		webSitesList.add(new SimpleNamedThing("siteID", "12345"));

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
	public Schema getEndpointSchema(
		RuntimeContainer runtimeContainer, String resourceURL) {

		JsonNode jsonNode = doApioGetRequest(resourceURL);

		return getResourceSchemaByType("type");
	}

	@Override
	public Schema getExpectedFormSchema(String endpoint) throws IOException {
		return ExpectedFormSchemaInferrer.inferSchemaByFormOperation(
			"operation", "endpoint");
	}

	@Override
	public String getResourceCollectionType(String resourceURL)
		throws IOException {

		JsonNode jsonNode = doApioGetRequest(resourceURL);

		return "type";
	}

	@Override
	public List<NamedThing> getResourceList(String webSiteURL)
		throws IOException {

		if (StringUtils.isEmpty(webSiteURL)) {
			return getSchemaNames(null);
		}

		List<NamedThing> resourceNames = new ArrayList<>();

		Map<String, String> resourceCollections = Collections.emptyMap();
		//_getWebSiteResourceEndpointsMap(webSiteURL);

		for (Map.Entry<String, String> entry : resourceCollections.entrySet()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"resource: {}, href: {} ", entry.getValue(),
					entry.getKey());
			}

			resourceNames.add(
				new SimpleNamedThing(
					entry.getValue(), entry.getValue(), entry.getKey()));
		}

		return resourceNames;
	}

	@Override
	public Schema getResourceSchemaByType(String resourceType) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		String endpointURL = liferayConnectionProperties.endpoint.getValue();

		JsonNode apiDocumentationJsonNode = doApioGetRequest(
			apiSpecURL.concat("/doc"));

		return ResourceCollectionSchemaInferrer.inferSchemaByResourceType(
			apiDocumentationJsonNode);
	}

	public RESTClient getRestClient(RuntimeContainer runtimeContainer) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		if (restClient == null) {
			restClient = new RESTClient(liferayConnectionProperties);
		}
		else {
			String endpoint = restClient.getEndpoint();

			if (!endpoint.equals(
					liferayConnectionProperties.endpoint.getValue())) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Endpoint has been changed, initialize a new " +
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

	@Override
	public List<NamedThing> getSchemaNames(RuntimeContainer runtimeContainer)
		throws IOException {

		List<NamedThing> schemaNames = new ArrayList<>();

		Map<String, String> resourceCollections = getApioResourceEndpointsMap(
			runtimeContainer);

		for (Map.Entry<String, String> entry : resourceCollections.entrySet()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"resource: {}, href: {} ", entry.getValue(),
					entry.getKey());
			}

			schemaNames.add(
				new SimpleNamedThing(
					entry.getValue(), entry.getValue(), entry.getKey()));
		}

		return schemaNames;
	}

	@Override
	public boolean hasWebSiteResource() {
		Map<String, String> apioResourceEndpointsMap =
			getApioResourceEndpointsMap(null);

		Set<Map.Entry<String, String>> resourceCollectionEntrySet =
			apioResourceEndpointsMap.entrySet();

		Stream<Map.Entry<String, String>> stream =
			resourceCollectionEntrySet.stream();

		return stream.anyMatch(entry -> true);
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

		getRestClient(runtimeContainer);

		// TODO: Check against API spec

		//validationResultMutable.setStatus(ValidationResult.Result.ERROR);

		return validationResultMutable;
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		boolean anonymousLogin =
			liferayConnectionProperties.anonymousLogin.getValue();
		String endpoint = liferayConnectionProperties.endpoint.getValue();
		String password = liferayConnectionProperties.password.getValue();
		String userId = liferayConnectionProperties.userId.getValue();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Validate endpoint: {}",
				liferayConnectionProperties.endpoint.getValue());
			_log.debug(
				"Validate user ID: {}",
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
		LiferayConnectionPropertiesProvider
			liferayConnectionPropertiesProvider) {

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		validationResultMutable.setStatus(ValidationResult.Result.OK);

		try {
			LiferaySourceOrSink liferaySourceOrSink = new LiferaySourceOrSink();

			liferaySourceOrSink.initialize(
				null,
				(LiferayConnectionProperties)
					liferayConnectionPropertiesProvider);

			doApioGetRequest((RuntimeContainer)null);

			validationResultMutable.setMessage(
				i18nMessages.getMessage("success.validation.connection"));
		}
		catch (TalendRuntimeException tre) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection",
					tre.getLocalizedMessage()));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);
		}
		catch (ProcessingException pe) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection.jersey",
					pe.getLocalizedMessage()));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);
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

	private String _getWebSitesEndpointURL() throws IOException {
		Map<String, String> apioResourceEndpointsMap =
			getApioResourceEndpointsMap(null);

		Set<Map.Entry<String, String>> resourceCollectionEntrySet =
			apioResourceEndpointsMap.entrySet();

		Stream<Map.Entry<String, String>> stream =
			resourceCollectionEntrySet.stream();

		Optional<String> webSiteHrefOptional = stream.filter(
			entry -> true
		).map(
			Map.Entry::getKey
		).findFirst();

		return webSiteHrefOptional.get();
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