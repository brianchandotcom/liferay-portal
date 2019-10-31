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

import com.liferay.talend.common.exception.MalformedURLException;
import com.liferay.talend.common.oas.OASSource;
import com.liferay.talend.common.util.URIUtil;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.properties.ExceptionUtils;
import com.liferay.talend.runtime.client.RESTClient;
import com.liferay.talend.runtime.client.ResponseHandler;
import com.liferay.talend.runtime.client.exception.ResponseContentClientException;

import java.io.IOException;

import java.util.List;

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

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 * @author Ivica Cardic
 */
public class LiferaySourceOrSink
	extends TranslatableImpl implements OASSource, SourceOrSink {

	public JsonObject doDeleteRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		return _responseHandler.asJsonObject(restClient.executeDeleteRequest());
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
			if (response.getStatus() == 204) {
				return null;
			}

			throw new ResponseContentClientException(
				"Unable to decode response content type " +
					_responseHandler.getContentType(response),
				response.getStatus(), null);
		}

		return _responseHandler.asJsonObject(response);
	}

	public JsonObject doPostRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonObject jsonObject) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		return _responseHandler.asJsonObject(
			restClient.executePostRequest(jsonObject));
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
	public JsonObject getOASJsonObject() {
		return doGetRequest((String)null);
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

	@Override
	public ValidationResult initialize(
		ComponentProperties componentProperties) {

		ValidationResult validationResult = initialize(
			null, componentProperties);

		if (validationResult.getStatus() == ValidationResult.Result.ERROR) {
			return validationResult;
		}

		return validate(null);
	}

	@Override
	public ValidationResult initialize(
		RuntimeContainer runtimeContainer,
		ComponentProperties componentProperties) {

		if (!(componentProperties instanceof
				LiferayConnectionPropertiesProvider)) {

			return ValidationResult.OK;
		}

		liferayConnectionPropertiesProvider =
			(LiferayConnectionPropertiesProvider)componentProperties;

		try {
			getRestClient(runtimeContainer);

			return ValidationResult.OK;
		}
		catch (TalendRuntimeException tre) {
			return ExceptionUtils.exceptionToValidationResult(tre);
		}
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		String target = liferayConnectionProperties.getApiSpecURL();

		try {
			URIUtil.validateOpenAPISpecURL(target);
		}
		catch (MalformedURLException murle) {
			return new ValidationResult(
				ValidationResult.Result.ERROR, murle.getMessage());
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Validate API spec URL: {}",
				liferayConnectionProperties.getApiSpecURL());
			_logger.debug(
				"Validate user ID: {}",
				liferayConnectionProperties.getUserId());
		}

		if (liferayConnectionProperties.isBasicAuthorization()) {
			if (_isNullString(liferayConnectionProperties.getUserId()) ||
				_isNullString(liferayConnectionProperties.getPassword())) {

				return new ValidationResult(
					ValidationResult.Result.ERROR,
					i18nMessages.getMessage(
						"error.validation.connection.credentials"));
			}
		}
		else {
			if (_isNullString(liferayConnectionProperties.getOAuthClientId()) ||
				_isNullString(
					liferayConnectionProperties.getOAuthClientSecret())) {

				return new ValidationResult(
					ValidationResult.Result.ERROR,
					i18nMessages.getMessage(
						"error.validation.connection.credentials"));
			}
		}

		return validateConnection(runtimeContainer);
	}

	public ValidationResult validateConnection(
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

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferaySourceOrSink.class);

	private static final long serialVersionUID = 3109815759807236523L;

	private final ResponseHandler _responseHandler = new ResponseHandler();

}