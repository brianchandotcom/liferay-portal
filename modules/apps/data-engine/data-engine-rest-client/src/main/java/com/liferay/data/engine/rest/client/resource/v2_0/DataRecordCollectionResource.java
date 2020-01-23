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

package com.liferay.data.engine.rest.client.resource.v2_0;

import com.liferay.data.engine.rest.client.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.permission.Permission;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataRecordCollectionSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public interface DataRecordCollectionResource {

	public static Builder builder() {
		return new Builder();
	}

	public DataRecordCollection getDataDefinitionDataRecordCollection(
			Long dataDefinitionId)
		throws Exception;

	public HttpInvoker.HttpResponse
			getDataDefinitionDataRecordCollectionHttpResponse(
				Long dataDefinitionId)
		throws Exception;

	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getDataDefinitionDataRecordCollectionsPageHttpResponse(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception;

	public DataRecordCollection postDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception;

	public HttpInvoker.HttpResponse
			postDataDefinitionDataRecordCollectionHttpResponse(
				Long dataDefinitionId,
				DataRecordCollection dataRecordCollection)
		throws Exception;

	public void deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception;

	public HttpInvoker.HttpResponse deleteDataRecordCollectionHttpResponse(
			Long dataRecordCollectionId)
		throws Exception;

	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception;

	public HttpInvoker.HttpResponse getDataRecordCollectionHttpResponse(
			Long dataRecordCollectionId)
		throws Exception;

	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception;

	public HttpInvoker.HttpResponse putDataRecordCollectionHttpResponse(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception;

	public Page<Permission> getDataRecordCollectionPermissionsPage(
			Long dataRecordCollectionId, String roleNames)
		throws Exception;

	public HttpInvoker.HttpResponse
			getDataRecordCollectionPermissionsPageHttpResponse(
				Long dataRecordCollectionId, String roleNames)
		throws Exception;

	public void putDataRecordCollectionPermission(
			Long dataRecordCollectionId, Permission[] permissions)
		throws Exception;

	public HttpInvoker.HttpResponse
			putDataRecordCollectionPermissionHttpResponse(
				Long dataRecordCollectionId, Permission[] permissions)
		throws Exception;

	public String getDataRecordCollectionPermissionByCurrentUser(
			Long dataRecordCollectionId)
		throws Exception;

	public HttpInvoker.HttpResponse
			getDataRecordCollectionPermissionByCurrentUserHttpResponse(
				Long dataRecordCollectionId)
		throws Exception;

	public DataRecordCollection
			getSiteDataRecordCollectionByDataRecordCollectionKey(
				Long siteId, String dataRecordCollectionKey)
		throws Exception;

	public HttpInvoker.HttpResponse
			getSiteDataRecordCollectionByDataRecordCollectionKeyHttpResponse(
				Long siteId, String dataRecordCollectionKey)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public DataRecordCollectionResource build() {
			return new DataRecordCollectionResourceImpl(this);
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

	public static class DataRecordCollectionResourceImpl
		implements DataRecordCollectionResource {

		public DataRecordCollection getDataDefinitionDataRecordCollection(
				Long dataDefinitionId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDataDefinitionDataRecordCollectionHttpResponse(
					dataDefinitionId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataRecordCollectionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				getDataDefinitionDataRecordCollectionHttpResponse(
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
						"/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collection",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<DataRecordCollection>
				getDataDefinitionDataRecordCollectionsPage(
					Long dataDefinitionId, String keywords,
					Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDataDefinitionDataRecordCollectionsPageHttpResponse(
					dataDefinitionId, keywords, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, DataRecordCollectionSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getDataDefinitionDataRecordCollectionsPageHttpResponse(
					Long dataDefinitionId, String keywords,
					Pagination pagination)
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

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataRecordCollection postDataDefinitionDataRecordCollection(
				Long dataDefinitionId,
				DataRecordCollection dataRecordCollection)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postDataDefinitionDataRecordCollectionHttpResponse(
					dataDefinitionId, dataRecordCollection);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataRecordCollectionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postDataDefinitionDataRecordCollectionHttpResponse(
					Long dataDefinitionId,
					DataRecordCollection dataRecordCollection)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				dataRecordCollection.toString(), "application/json");

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
						"/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections",
				dataDefinitionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteDataRecordCollection(Long dataRecordCollectionId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteDataRecordCollectionHttpResponse(dataRecordCollectionId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse deleteDataRecordCollectionHttpResponse(
				Long dataRecordCollectionId)
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
						"/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}",
				dataRecordCollectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataRecordCollection getDataRecordCollection(
				Long dataRecordCollectionId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDataRecordCollectionHttpResponse(dataRecordCollectionId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataRecordCollectionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getDataRecordCollectionHttpResponse(
				Long dataRecordCollectionId)
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
						"/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}",
				dataRecordCollectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataRecordCollection putDataRecordCollection(
				Long dataRecordCollectionId,
				DataRecordCollection dataRecordCollection)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putDataRecordCollectionHttpResponse(
					dataRecordCollectionId, dataRecordCollection);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataRecordCollectionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse putDataRecordCollectionHttpResponse(
				Long dataRecordCollectionId,
				DataRecordCollection dataRecordCollection)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				dataRecordCollection.toString(), "application/json");

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
						"/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}",
				dataRecordCollectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> getDataRecordCollectionPermissionsPage(
				Long dataRecordCollectionId, String roleNames)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDataRecordCollectionPermissionsPageHttpResponse(
					dataRecordCollectionId, roleNames);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, Permission::toDTO);
		}

		public HttpInvoker.HttpResponse
				getDataRecordCollectionPermissionsPageHttpResponse(
					Long dataRecordCollectionId, String roleNames)
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

			if (roleNames != null) {
				httpInvoker.parameter("roleNames", String.valueOf(roleNames));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions",
				dataRecordCollectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putDataRecordCollectionPermission(
				Long dataRecordCollectionId, Permission[] permissions)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putDataRecordCollectionPermissionHttpResponse(
					dataRecordCollectionId, permissions);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				putDataRecordCollectionPermissionHttpResponse(
					Long dataRecordCollectionId, Permission[] permissions)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				Stream.of(
					permissions
				).map(
					value -> String.valueOf(value)
				).collect(
					Collectors.toList()
				).toString(),
				"application/json");

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
						"/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions",
				dataRecordCollectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public String getDataRecordCollectionPermissionByCurrentUser(
				Long dataRecordCollectionId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDataRecordCollectionPermissionByCurrentUserHttpResponse(
					dataRecordCollectionId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return content;
		}

		public HttpInvoker.HttpResponse
				getDataRecordCollectionPermissionByCurrentUserHttpResponse(
					Long dataRecordCollectionId)
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
						"/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions/by-current-user",
				dataRecordCollectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DataRecordCollection
				getSiteDataRecordCollectionByDataRecordCollectionKey(
					Long siteId, String dataRecordCollectionKey)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteDataRecordCollectionByDataRecordCollectionKeyHttpResponse(
					siteId, dataRecordCollectionKey);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return DataRecordCollectionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				getSiteDataRecordCollectionByDataRecordCollectionKeyHttpResponse(
					Long siteId, String dataRecordCollectionKey)
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
						"/o/data-engine/v2.0/sites/{siteId}/data-record-collections/by-data-record-collection-key/{dataRecordCollectionKey}",
				siteId, dataRecordCollectionKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private DataRecordCollectionResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			DataRecordCollectionResource.class.getName());

		private Builder _builder;

	}

}