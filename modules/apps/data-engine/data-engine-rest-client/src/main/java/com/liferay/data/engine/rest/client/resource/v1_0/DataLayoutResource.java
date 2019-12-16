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

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataLayoutSerDes;

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
public interface DataLayoutResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<DataLayout> getDataDefinitionDataLayoutsPage(
			Long dataDefinitionId, String keywords, Pagination pagination,
			String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getDataDefinitionDataLayoutsPageHttpResponse(
				Long dataDefinitionId, String keywords, Pagination pagination,
				String sortString)
		throws Exception;

	public DataLayout postDataDefinitionDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception;

	public HttpInvoker.HttpResponse postDataDefinitionDataLayoutHttpResponse(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception;

	public void deleteDataLayout(Long dataLayoutId) throws Exception;

	public HttpInvoker.HttpResponse deleteDataLayoutHttpResponse(
			Long dataLayoutId)
		throws Exception;

	public DataLayout getDataLayout(Long dataLayoutId) throws Exception;

	public HttpInvoker.HttpResponse getDataLayoutHttpResponse(Long dataLayoutId)
		throws Exception;

	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception;

	public HttpInvoker.HttpResponse putDataLayoutHttpResponse(
			Long dataLayoutId, DataLayout dataLayout)
		throws Exception;

	public void postDataLayoutDataLayoutPermission(
			Long dataLayoutId, String operation,
			com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutPermission
				dataLayoutPermission)
		throws Exception;

	public HttpInvoker.HttpResponse
			postDataLayoutDataLayoutPermissionHttpResponse(
				Long dataLayoutId, String operation,
				com.liferay.data.engine.rest.client.dto.v1_0.
					DataLayoutPermission dataLayoutPermission)
		throws Exception;

	public void postSiteDataLayoutPermission(
			Long siteId, String operation,
			com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutPermission
				dataLayoutPermission)
		throws Exception;

	public HttpInvoker.HttpResponse postSiteDataLayoutPermissionHttpResponse(
			Long siteId, String operation,
			com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutPermission
				dataLayoutPermission)
		throws Exception;

	public Page<DataLayout> getSiteDataLayoutsPage(
			Long siteId, String keywords, Pagination pagination,
			String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getSiteDataLayoutsPageHttpResponse(
			Long siteId, String keywords, Pagination pagination,
			String sortString)
		throws Exception;

	public DataLayout getSiteDataLayout(Long siteId, String dataLayoutKey)
		throws Exception;

	public HttpInvoker.HttpResponse getSiteDataLayoutHttpResponse(
			Long siteId, String dataLayoutKey)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public DataLayoutResource build() {
			return new DataLayoutResourceImpl(this);
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

	public static class DataLayoutResourceImpl implements DataLayoutResource {

		public Page<DataLayout> getDataDefinitionDataLayoutsPage(
				Long dataDefinitionId, String keywords, Pagination pagination,
				String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDataDefinitionDataLayoutsPageHttpResponse(
					dataDefinitionId, keywords, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, DataLayoutSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getDataDefinitionDataLayoutsPageHttpResponse(
					Long dataDefinitionId, String keywords,
					Pagination pagination, String sortString)
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
						"/o/data-engine/v1.0/data-definitions/{dataDefinitionId}/data-layouts",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataLayout postDataDefinitionDataLayout(
				Long dataDefinitionId, DataLayout dataLayout)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postDataDefinitionDataLayoutHttpResponse(
					dataDefinitionId, dataLayout);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataLayoutSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postDataDefinitionDataLayoutHttpResponse(
					Long dataDefinitionId, DataLayout dataLayout)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(dataLayout.toString(), "application/json");

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
						"/o/data-engine/v1.0/data-definitions/{dataDefinitionId}/data-layouts",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteDataLayout(Long dataLayoutId) throws Exception {
			HttpInvoker.HttpResponse httpResponse =
				deleteDataLayoutHttpResponse(dataLayoutId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse deleteDataLayoutHttpResponse(
				Long dataLayoutId)
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
						"/o/data-engine/v1.0/data-layouts/{dataLayoutId}",
				dataLayoutId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
			HttpInvoker.HttpResponse httpResponse = getDataLayoutHttpResponse(
				dataLayoutId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataLayoutSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getDataLayoutHttpResponse(
				Long dataLayoutId)
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
						"/o/data-engine/v1.0/data-layouts/{dataLayoutId}",
				dataLayoutId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataLayout putDataLayout(
				Long dataLayoutId, DataLayout dataLayout)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = putDataLayoutHttpResponse(
				dataLayoutId, dataLayout);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataLayoutSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse putDataLayoutHttpResponse(
				Long dataLayoutId, DataLayout dataLayout)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(dataLayout.toString(), "application/json");

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
						"/o/data-engine/v1.0/data-layouts/{dataLayoutId}",
				dataLayoutId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postDataLayoutDataLayoutPermission(
				Long dataLayoutId, String operation,
				com.liferay.data.engine.rest.client.dto.v1_0.
					DataLayoutPermission dataLayoutPermission)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postDataLayoutDataLayoutPermissionHttpResponse(
					dataLayoutId, operation, dataLayoutPermission);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				postDataLayoutDataLayoutPermissionHttpResponse(
					Long dataLayoutId, String operation,
					com.liferay.data.engine.rest.client.dto.v1_0.
						DataLayoutPermission dataLayoutPermission)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				dataLayoutPermission.toString(), "application/json");

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
						"/o/data-engine/v1.0/data-layouts/{dataLayoutId}/data-layout-permissions",
				dataLayoutId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postSiteDataLayoutPermission(
				Long siteId, String operation,
				com.liferay.data.engine.rest.client.dto.v1_0.
					DataLayoutPermission dataLayoutPermission)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postSiteDataLayoutPermissionHttpResponse(
					siteId, operation, dataLayoutPermission);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				postSiteDataLayoutPermissionHttpResponse(
					Long siteId, String operation,
					com.liferay.data.engine.rest.client.dto.v1_0.
						DataLayoutPermission dataLayoutPermission)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				dataLayoutPermission.toString(), "application/json");

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
						"/o/data-engine/v1.0/sites/{siteId}/data-layout-permissions",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<DataLayout> getSiteDataLayoutsPage(
				Long siteId, String keywords, Pagination pagination,
				String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteDataLayoutsPageHttpResponse(
					siteId, keywords, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, DataLayoutSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getSiteDataLayoutsPageHttpResponse(
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
						"/o/data-engine/v1.0/sites/{siteId}/data-layouts",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataLayout getSiteDataLayout(Long siteId, String dataLayoutKey)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteDataLayoutHttpResponse(siteId, dataLayoutKey);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataLayoutSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getSiteDataLayoutHttpResponse(
				Long siteId, String dataLayoutKey)
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
						"/o/data-engine/v1.0/sites/{siteId}/data-layouts/{dataLayoutKey}",
				siteId, dataLayoutKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private DataLayoutResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			DataLayoutResource.class.getName());

		private Builder _builder;

	}

}