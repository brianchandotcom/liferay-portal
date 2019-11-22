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
import com.liferay.talend.common.util.StringUtil;
import com.liferay.talend.common.util.URIUtil;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.runtime.client.RESTClient;
import com.liferay.talend.runtime.client.ResponseHandler;
import com.liferay.talend.runtime.client.exception.ResponseContentClientException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 * @author Ivica Cardic
 */
public class LiferaySourceOrSink implements OASSource, SourceOrSink {

	public JsonObject doDeleteRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		return _getResponseContentJsonObject(restClient.executeDeleteRequest());
	}

	public JsonObject doGetRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		return _getResponseContentJsonObject(restClient.executeGetRequest());
	}

	public JsonObject doGetRequest(String resourceURL) {
		return doGetRequest(null, resourceURL);
	}

	public JsonObject doPatchRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonObject jsonObject) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		return _getResponseContentJsonObject(
			restClient.executePatchRequest(jsonObject));
	}

	public JsonObject doPostRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonObject jsonObject) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		return _responseHandler.asJsonObject(
			restClient.executePostRequest(jsonObject));
	}

	@Override
	public Schema getEndpointSchema(
		RuntimeContainer runtimeContainer, String endpoint) {

		return null;
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

			restClient = new RESTClient(_liferayConnectionProperties);

			return restClient;
		}

		if ((restClient != null) && restClient.matches(resourceURL)) {
			return restClient;
		}

		return new RESTClient(_liferayConnectionProperties, resourceURL);
	}

	@Override
	public List<NamedThing> getSchemaNames(RuntimeContainer runtimeContainer) {
		return Collections.emptyList();
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

		Objects.requireNonNull(componentProperties);

		LiferayConnectionProperties liferayConnectionProperties = null;

		if (componentProperties instanceof LiferayConnectionProperties) {
			liferayConnectionProperties =
				(LiferayConnectionProperties)componentProperties;
		}
		else {
			Properties aggregatedProperties = componentProperties.getProperties(
				"connection");

			if (aggregatedProperties instanceof LiferayConnectionProperties) {
				liferayConnectionProperties =
					(LiferayConnectionProperties)aggregatedProperties;
			}
		}

		if (liferayConnectionProperties == null) {
			return new ValidationResult(
				ValidationResult.Result.ERROR,
				"Unable to locate connection properties");
		}

		_liferayConnectionProperties =
			liferayConnectionProperties.
				getEffectiveLiferayConnectionProperties();

		try {
			getRestClient(runtimeContainer);

			return ValidationResult.OK;
		}
		catch (TalendRuntimeException tre) {
			return new ValidationResult(
				ValidationResult.Result.ERROR, tre.getMessage());
		}
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		String target = _liferayConnectionProperties.getApiSpecURL();

		try {
			URIUtil.validateOpenAPISpecURL(target);
		}
		catch (MalformedURLException murle) {
			return new ValidationResult(
				ValidationResult.Result.ERROR, murle.getMessage());
		}

		if (_liferayConnectionProperties.isBasicAuthorization()) {
			if (StringUtil.isEmpty(_liferayConnectionProperties.getUserId()) ||
				StringUtil.isEmpty(
					_liferayConnectionProperties.getPassword())) {

				return new ValidationResult(
					ValidationResult.Result.ERROR,
					i18nMessages.getMessage(
						"error.validation.connection.credentials"));
			}
		}
		else {
			if (StringUtil.isEmpty(
					_liferayConnectionProperties.getOAuthClientId()) ||
				StringUtil.isEmpty(
					_liferayConnectionProperties.getOAuthClientSecret())) {

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
			doGetRequest(runtimeContainer, null);

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

	protected LiferayConnectionProperties getLiferayConnectionProperties() {
		return _liferayConnectionProperties;
	}

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferaySourceOrSink.class);
	}

	protected RESTClient restClient;

	private JsonObject _getResponseContentJsonObject(Response response) {
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

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferaySourceOrSink.class);

	private static final long serialVersionUID = 3109815759807236523L;

	private transient LiferayConnectionProperties _liferayConnectionProperties;
	private final ResponseHandler _responseHandler = new ResponseHandler();

}