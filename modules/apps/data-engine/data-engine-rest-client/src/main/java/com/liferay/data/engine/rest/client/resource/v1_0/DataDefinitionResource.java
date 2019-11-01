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

package com.liferay.data.engine.rest.client.resource.v1_0;

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataDefinitionSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public interface DataDefinitionResource {

	public static Builder builder() {
		return new Builder();
	}

	public String getDataDefinitionDataDefinitionFieldFieldTypes()
		throws Exception;

	public HttpInvoker.HttpResponse
			getDataDefinitionDataDefinitionFieldFieldTypesHttpResponse()
		throws Exception;

	public void deleteDataDefinition(Long dataDefinitionId) throws Exception;

	public HttpInvoker.HttpResponse deleteDataDefinitionHttpResponse(
			Long dataDefinitionId)
		throws Exception;

	public DataDefinition getDataDefinition(Long dataDefinitionId)
		throws Exception;

	public HttpInvoker.HttpResponse getDataDefinitionHttpResponse(
			Long dataDefinitionId)
		throws Exception;

	public DataDefinition putDataDefinition(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception;

	public HttpInvoker.HttpResponse putDataDefinitionHttpResponse(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception;

	public String getDataDefinitionDataDefinitionFieldLinks(
			Long dataDefinitionId, String fieldName)
		throws Exception;

	public HttpInvoker.HttpResponse
			getDataDefinitionDataDefinitionFieldLinksHttpResponse(
				Long dataDefinitionId, String fieldName)
		throws Exception;

	public void postDataDefinitionDataDefinitionPermission(
			Long dataDefinitionId, String operation,
			com.liferay.data.engine.rest.client.dto.v1_0.
				DataDefinitionPermission dataDefinitionPermission)
		throws Exception;

	public HttpInvoker.HttpResponse
			postDataDefinitionDataDefinitionPermissionHttpResponse(
				Long dataDefinitionId, String operation,
				com.liferay.data.engine.rest.client.dto.v1_0.
					DataDefinitionPermission dataDefinitionPermission)
		throws Exception;

	public void postSiteDataDefinitionPermission(
			Long siteId, String operation,
			com.liferay.data.engine.rest.client.dto.v1_0.
				DataDefinitionPermission dataDefinitionPermission)
		throws Exception;

	public HttpInvoker.HttpResponse
			postSiteDataDefinitionPermissionHttpResponse(
				Long siteId, String operation,
				com.liferay.data.engine.rest.client.dto.v1_0.
					DataDefinitionPermission dataDefinitionPermission)
		throws Exception;

	public Page<DataDefinition> getSiteDataDefinitionsPage(
			Long siteId, String keywords, Pagination pagination,
			String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getSiteDataDefinitionsPageHttpResponse(
			Long siteId, String keywords, Pagination pagination,
			String sortString)
		throws Exception;

	public DataDefinition postSiteDataDefinition(
			Long siteId, DataDefinition dataDefinition)
		throws Exception;

	public HttpInvoker.HttpResponse postSiteDataDefinitionHttpResponse(
			Long siteId, DataDefinition dataDefinition)
		throws Exception;

	public DataDefinition getSiteDataDefinition(
			Long siteId, String dataDefinitionKey)
		throws Exception;

	public HttpInvoker.HttpResponse getSiteDataDefinitionHttpResponse(
			Long siteId, String dataDefinitionKey)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public DataDefinitionResource build() {
			return new DataDefinitionResourceImpl(this);
		}

		public Builder endpoint(String host, int port, String scheme) {
			_host = host;
			_port = port;
			_scheme = scheme;

			return this;
		}

		public Builder header(String key, String value) {
			_headers.put(key, value);

			return this;
		}

		public Builder locale(Locale locale) {
			_locale = locale;

			return this;
		}

		public Builder parameter(String key, String value) {
			_parameters.put(key, value);

			return this;
		}

		private Builder() {
		}

		private Map<String, String> _headers = new LinkedHashMap<>();
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "test@liferay.com";
		private String _password = "test";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class DataDefinitionResourceImpl
		implements DataDefinitionResource {

		public String getDataDefinitionDataDefinitionFieldFieldTypes()
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDataDefinitionDataDefinitionFieldFieldTypesHttpResponse();

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return content;
		}

		public HttpInvoker.HttpResponse
				getDataDefinitionDataDefinitionFieldFieldTypesHttpResponse()
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/data-definitions/data-definition-fields/field-types");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteDataDefinition(Long dataDefinitionId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteDataDefinitionHttpResponse(dataDefinitionId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse deleteDataDefinitionHttpResponse(
				Long dataDefinitionId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/data-definitions/{dataDefinitionId}",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataDefinition getDataDefinition(Long dataDefinitionId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDataDefinitionHttpResponse(dataDefinitionId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataDefinitionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getDataDefinitionHttpResponse(
				Long dataDefinitionId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/data-definitions/{dataDefinitionId}",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataDefinition putDataDefinition(
				Long dataDefinitionId, DataDefinition dataDefinition)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putDataDefinitionHttpResponse(dataDefinitionId, dataDefinition);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataDefinitionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse putDataDefinitionHttpResponse(
				Long dataDefinitionId, DataDefinition dataDefinition)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(dataDefinition.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/data-definitions/{dataDefinitionId}",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public String getDataDefinitionDataDefinitionFieldLinks(
				Long dataDefinitionId, String fieldName)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDataDefinitionDataDefinitionFieldLinksHttpResponse(
					dataDefinitionId, fieldName);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return content;
		}

		public HttpInvoker.HttpResponse
				getDataDefinitionDataDefinitionFieldLinksHttpResponse(
					Long dataDefinitionId, String fieldName)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (fieldName != null) {
				httpInvoker.parameter("fieldName", String.valueOf(fieldName));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/data-definitions/{dataDefinitionId}/data-definition-field-links",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postDataDefinitionDataDefinitionPermission(
				Long dataDefinitionId, String operation,
				com.liferay.data.engine.rest.client.dto.v1_0.
					DataDefinitionPermission dataDefinitionPermission)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postDataDefinitionDataDefinitionPermissionHttpResponse(
					dataDefinitionId, operation, dataDefinitionPermission);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				postDataDefinitionDataDefinitionPermissionHttpResponse(
					Long dataDefinitionId, String operation,
					com.liferay.data.engine.rest.client.dto.v1_0.
						DataDefinitionPermission dataDefinitionPermission)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				dataDefinitionPermission.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			if (operation != null) {
				httpInvoker.parameter("operation", String.valueOf(operation));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/data-definitions/{dataDefinitionId}/data-definition-permissions",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postSiteDataDefinitionPermission(
				Long siteId, String operation,
				com.liferay.data.engine.rest.client.dto.v1_0.
					DataDefinitionPermission dataDefinitionPermission)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postSiteDataDefinitionPermissionHttpResponse(
					siteId, operation, dataDefinitionPermission);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				postSiteDataDefinitionPermissionHttpResponse(
					Long siteId, String operation,
					com.liferay.data.engine.rest.client.dto.v1_0.
						DataDefinitionPermission dataDefinitionPermission)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				dataDefinitionPermission.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			if (operation != null) {
				httpInvoker.parameter("operation", String.valueOf(operation));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/sites/{siteId}/data-definition-permissions",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<DataDefinition> getSiteDataDefinitionsPage(
				Long siteId, String keywords, Pagination pagination,
				String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteDataDefinitionsPageHttpResponse(
					siteId, keywords, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, DataDefinitionSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getSiteDataDefinitionsPageHttpResponse(
				Long siteId, String keywords, Pagination pagination,
				String sortString)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (keywords != null) {
				httpInvoker.parameter("keywords", String.valueOf(keywords));
			}

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			if (sortString != null) {
				httpInvoker.parameter("sort", sortString);
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/sites/{siteId}/data-definitions",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataDefinition postSiteDataDefinition(
				Long siteId, DataDefinition dataDefinition)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postSiteDataDefinitionHttpResponse(siteId, dataDefinition);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataDefinitionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse postSiteDataDefinitionHttpResponse(
				Long siteId, DataDefinition dataDefinition)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(dataDefinition.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/sites/{siteId}/data-definitions",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataDefinition getSiteDataDefinition(
				Long siteId, String dataDefinitionKey)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteDataDefinitionHttpResponse(siteId, dataDefinitionKey);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataDefinitionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getSiteDataDefinitionHttpResponse(
				Long siteId, String dataDefinitionKey)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v1.0/sites/{siteId}/data-definitions/{dataDefinitionKey}",
				siteId, dataDefinitionKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private DataDefinitionResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			DataDefinitionResource.class.getName());

		private Builder _builder;

	}

}