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

import static com.liferay.talend.runtime.apio.constants.SchemaOrgConstants.Vocabulary.WEB_SITES;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;

import com.liferay.talend.avro.ExpectedFormSchemaInferrer;
import com.liferay.talend.avro.ResourceCollectionSchemaInferrer;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.runtime.apio.ApioException;
import com.liferay.talend.runtime.apio.ApioResult;
import com.liferay.talend.runtime.apio.constants.JSONLDConstants;
import com.liferay.talend.runtime.apio.constants.SchemaOrgConstants;
import com.liferay.talend.runtime.apio.jsonld.ApioForm;
import com.liferay.talend.runtime.apio.jsonld.ApioResourceCollection;
import com.liferay.talend.runtime.apio.jsonld.ApioSingleModel;
import com.liferay.talend.runtime.apio.jsonld.ApioUtils;
import com.liferay.talend.runtime.apio.operation.Operation;
import com.liferay.talend.runtime.client.RESTClient;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.ws.rs.ProcessingException;

import org.apache.avro.Schema;
import org.apache.commons.lang3.StringUtils;

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

		RESTClient restClient = null;
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

		JsonNode jsonNode = NullNode.getInstance();

		try {
			jsonNode = _toJsonNode(apioResult);
		}
		catch (JsonMappingException jme) {
			if (_log.isDebugEnabled()) {
				_log.debug("Empty body was sent by the server");
			}
		}

		return jsonNode;
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

		RESTClient restClient = null;
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

		return _toJsonNode(apioResult);
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

		RESTClient restClient = null;
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

		return _toJsonNode(apioResult);
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

		RESTClient restClient = null;
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

		return _toJsonNode(apioResult);
	}

	public JsonNode doApioPutRequest(String resourceURL, JsonNode apioForm)
		throws IOException {

		return doApioPutRequest(null, resourceURL, apioForm);
	}

	public Map<String, String> getApioResourceEndpointsMap(
		RuntimeContainer runtimeContainer) {

		JsonNode jsonNode = null;

		try {
			jsonNode = doApioGetRequest(runtimeContainer);
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to fetch resources: " + ioe.getMessage());
			}

			return Collections.emptyMap();
		}

		return _getResourceCollectionsDescriptor(jsonNode);
	}

	@Override
	public List<NamedThing> getAvailableWebSites() throws IOException {
		String webSitesEndpointURL = _getWebSitesEndpointURL();

		JsonNode resourceCollectionJsonNode = doApioGetRequest(
			webSitesEndpointURL);

		ApioResourceCollection webSitesApioResourceCollection =
			new ApioResourceCollection(resourceCollectionJsonNode);

		List<NamedThing> webSitesList = new ArrayList<>();

		String actualPage =
			webSitesApioResourceCollection.getResourceActualPage();
		String nextPage = webSitesApioResourceCollection.getResourceNextPage();
		String lastPage = webSitesApioResourceCollection.getResourceLastPage();

		do {
			JsonNode webSitesJsonNode =
				webSitesApioResourceCollection.getMemberJsonNode();

			for (JsonNode jsonNode : webSitesJsonNode) {
				JsonNode webSiteURLJsonNode = jsonNode.path(JSONLDConstants.ID);
				JsonNode webSiteNameJsonNode = jsonNode.path(
					SchemaOrgConstants.Property.NAME);

				webSitesList.add(
					new SimpleNamedThing(
						webSiteURLJsonNode.asText(),
						webSiteNameJsonNode.asText()));
			}

			actualPage = webSitesApioResourceCollection.getResourceActualPage();
			nextPage = webSitesApioResourceCollection.getResourceNextPage();

			if (StringUtils.isNotBlank(nextPage)) {
				webSitesApioResourceCollection = new ApioResourceCollection(
					doApioGetRequest(nextPage));
			}
		}
		while (StringUtils.isNotBlank(nextPage) &&
			   !lastPage.equals(actualPage));

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
			RuntimeContainer runtimeContainer, String resourceURL)
		throws IOException {

		return getInputResourceCollectionSchema(resourceURL);
	}

	@Override
	public Schema getExpectedFormSchema(Operation operation)
		throws IOException {

		ApioForm apioForm = null;

		if (StringUtils.isNotEmpty(operation.getExpects())) {
			JsonNode jsonNode = doApioGetRequest(operation.getExpects());

			apioForm = new ApioForm(jsonNode);
		}

		return ExpectedFormSchemaInferrer.inferSchemaByFormOperation(
			operation, apioForm);
	}

	@Override
	public Schema getInputResourceCollectionSchema(String resourceURL)
		throws IOException {

		return _getResourceCollectionSchema(resourceURL);
	}

	@Override
	public List<NamedThing> getResourceList(String webSiteURL)
		throws IOException {

		if (StringUtils.isEmpty(webSiteURL)) {
			return getSchemaNames(null);
		}

		List<NamedThing> resourceNames = new ArrayList<>();

		Map<String, String> resourceCollections =
			_getWebSiteResourceEndpointsMap(webSiteURL);

		for (Map.Entry<String, String> entry : resourceCollections.entrySet()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"resource name: {}, href: {} ", entry.getKey(),
					entry.getValue());
			}

			resourceNames.add(
				new SimpleNamedThing(entry.getKey(), entry.getValue()));
		}

		return resourceNames;
	}

	@Override
	public List<Operation> getResourceSupportedOperations(String resourceURL)
		throws IOException {

		List<Operation> aggregatedResourceOperations = new ArrayList<>();

		JsonNode jsonNode = doApioGetRequest(resourceURL);

		ApioResourceCollection apioResourceCollection =
			new ApioResourceCollection(jsonNode);

		List<Operation> collectionOperations =
			apioResourceCollection.getResourceOperations();

		aggregatedResourceOperations.addAll(collectionOperations);

		JsonNode resourceEntryJsonNode =
			apioResourceCollection.getFirstEntryJsonNode();

		JsonNode resourceEntryURLJsonNode = resourceEntryJsonNode.path(
			JSONLDConstants.ID);

		jsonNode = doApioGetRequest(resourceEntryURLJsonNode.asText());

		ApioSingleModel resourceEntry = new ApioSingleModel(jsonNode);

		List<Operation> resourceOperations =
			resourceEntry.getResourceOperations();

		aggregatedResourceOperations.addAll(resourceOperations);

		return aggregatedResourceOperations;
	}

	public RESTClient getRestClient(RuntimeContainer runtimeContainer)
		throws ApioException {

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
					"resource name: {}, href: {} ", entry.getKey(),
					entry.getValue());
			}

			schemaNames.add(
				new SimpleNamedThing(entry.getKey(), entry.getValue()));
		}

		return schemaNames;
	}

	@Override
	public boolean hasWebSiteResource() {
		JsonNode jsonNode = null;

		try {
			jsonNode = doApioGetRequest((RuntimeContainer)null);
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to fetch the list of exposed resources", ioe);
			}

			return false;
		}

		Set<Map.Entry<String, String>> resourceCollectionEntrySet =
			_getResourceCollectionsDescriptor(jsonNode).entrySet();

		Stream<Map.Entry<String, String>> stream =
			resourceCollectionEntrySet.stream();

		return stream.anyMatch(entry -> WEB_SITES.equals(entry.getValue()));
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
		catch (ApioException ae) {
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);
		}

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

		validationResultMutable.setStatus(Result.OK);

		try {
			LiferaySourceOrSink liferaySourceOrSink = new LiferaySourceOrSink();

			liferaySourceOrSink.initialize(
				null,
				(LiferayConnectionProperties)
					liferayConnectionPropertiesProvider);

			RESTClient restClient = liferaySourceOrSink.getRestClient(null);

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

	protected volatile LiferayConnectionPropertiesProvider
		liferayConnectionPropertiesProvider;
	protected final ObjectMapper objectMapper = new ObjectMapper();
	protected RESTClient restClient;

	private Schema _getResourceCollectionSchema(String resourceURL)
		throws IOException {

		JsonNode jsonNode = doApioGetRequest(resourceURL);

		ApioResourceCollection apioResourceCollection =
			new ApioResourceCollection(jsonNode);

		try {
			return ResourceCollectionSchemaInferrer.inferSchemaByResourceFields(
				apioResourceCollection);
		}
		catch (IOException ioe) {
			throw new IOException(
				String.format(
					"%s\nResource collection URL: %s",
					ioe.getLocalizedMessage(), resourceURL));
		}
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

	private Map<String, String> _getWebSiteResourceCollectionsDescriptor(
			JsonNode jsonNode)
		throws IOException {

		ApioSingleModel apioSingleModel = new ApioSingleModel(jsonNode);

		JsonNode contextJsonNode = apioSingleModel.getContextJsonNode();

		Map<String, String> resourcesMap = new HashMap<>();
		List<String> typeCoercionTermKeys = ApioUtils.getTypeCoercionTermKeys(
			contextJsonNode);

		for (String typeCoercionTermKey : typeCoercionTermKeys) {
			JsonNode resourceHrefJsonNode = jsonNode.path(typeCoercionTermKey);

			resourcesMap.put(
				resourceHrefJsonNode.asText(), typeCoercionTermKey);
		}

		return resourcesMap;
	}

	private Map<String, String> _getWebSiteResourceEndpointsMap(
		String webSiteURL) {

		JsonNode jsonNode = null;

		try {
			jsonNode = doApioGetRequest(webSiteURL);

			return _getWebSiteResourceCollectionsDescriptor(jsonNode);
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to fetch resources: " + ioe.getMessage());
			}
		}

		return Collections.emptyMap();
	}

	private String _getWebSitesEndpointURL() throws IOException {
		JsonNode jsonNode = null;

		try {
			jsonNode = doApioGetRequest((RuntimeContainer)null);
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to fetch the list of exposed resources", ioe);
			}

			throw ioe;
		}

		Set<Map.Entry<String, String>> resourceCollectionEntrySet =
			_getResourceCollectionsDescriptor(jsonNode).entrySet();

		Stream<Map.Entry<String, String>> stream =
			resourceCollectionEntrySet.stream();

		Optional<String> webSiteHrefOptional = stream.filter(
			entry -> WEB_SITES.equals(entry.getValue())
		).map(
			Map.Entry::getKey
		).findFirst();

		return webSiteHrefOptional.get();
	}

	private JsonNode _toJsonNode(ApioResult apioResult) throws IOException {
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