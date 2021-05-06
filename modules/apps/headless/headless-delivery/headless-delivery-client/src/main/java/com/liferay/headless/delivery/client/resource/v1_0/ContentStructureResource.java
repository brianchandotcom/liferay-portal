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

package com.liferay.headless.delivery.client.resource.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.problem.Problem;
import com.liferay.headless.delivery.client.serdes.v1_0.ContentStructureSerDes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface ContentStructureResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<ContentStructure> getAssetLibraryContentStructuresPage(
			Long assetLibraryId, String search, List<String> aggregations,
			String filterString, Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getAssetLibraryContentStructuresPageHttpResponse(
				Long assetLibraryId, String search, List<String> aggregations,
				String filterString, Pagination pagination, String sortString)
		throws Exception;

	public Page<Permission> getAssetLibraryContentStructurePermissionsPage(
			Long assetLibraryId, String roleNames)
		throws Exception;

	public HttpInvoker.HttpResponse
			getAssetLibraryContentStructurePermissionsPageHttpResponse(
				Long assetLibraryId, String roleNames)
		throws Exception;

	public Page<Permission> putAssetLibraryContentStructurePermission(
			Long assetLibraryId, Permission[] permissions)
		throws Exception;

	public HttpInvoker.HttpResponse
			putAssetLibraryContentStructurePermissionHttpResponse(
				Long assetLibraryId, Permission[] permissions)
		throws Exception;

	public ContentStructure getContentStructure(Long contentStructureId)
		throws Exception;

	public HttpInvoker.HttpResponse getContentStructureHttpResponse(
			Long contentStructureId)
		throws Exception;

	public Page<Permission> getContentStructurePermissionsPage(
			Long contentStructureId, String roleNames)
		throws Exception;

	public HttpInvoker.HttpResponse
			getContentStructurePermissionsPageHttpResponse(
				Long contentStructureId, String roleNames)
		throws Exception;

	public Page<Permission> putContentStructurePermission(
			Long contentStructureId, Permission[] permissions)
		throws Exception;

	public HttpInvoker.HttpResponse putContentStructurePermissionHttpResponse(
			Long contentStructureId, Permission[] permissions)
		throws Exception;

	public Page<ContentStructure> getSiteContentStructuresPage(
			Long siteId, String search, List<String> aggregations,
			String filterString, Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getSiteContentStructuresPageHttpResponse(
			Long siteId, String search, List<String> aggregations,
			String filterString, Pagination pagination, String sortString)
		throws Exception;

	public Page<Permission> getSiteContentStructurePermissionsPage(
			Long siteId, String roleNames)
		throws Exception;

	public HttpInvoker.HttpResponse
			getSiteContentStructurePermissionsPageHttpResponse(
				Long siteId, String roleNames)
		throws Exception;

	public Page<Permission> putSiteContentStructurePermission(
			Long siteId, Permission[] permissions)
		throws Exception;

	public HttpInvoker.HttpResponse
			putSiteContentStructurePermissionHttpResponse(
				Long siteId, Permission[] permissions)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public ContentStructureResource build() {
			return new ContentStructureResourceImpl(this);
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

		public Builder parameters(String... parameters) {
			if ((parameters.length % 2) != 0) {
				throw new IllegalArgumentException(
					"Parameters length is not an even number");
			}

			for (int i = 0; i < parameters.length; i += 2) {
				String parameterName = String.valueOf(parameters[i]);
				String parameterValue = String.valueOf(parameters[i + 1]);

				_parameters.put(parameterName, parameterValue);
			}

			return this;
		}

		private Builder() {
		}

		private Map<String, String> _headers = new LinkedHashMap<>();
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "";
		private String _password = "";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class ContentStructureResourceImpl
		implements ContentStructureResource {

		public Page<ContentStructure> getAssetLibraryContentStructuresPage(
				Long assetLibraryId, String search, List<String> aggregations,
				String filterString, Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAssetLibraryContentStructuresPageHttpResponse(
					assetLibraryId, search, aggregations, filterString,
					pagination, sortString);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return Page.of(content, ContentStructureSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getAssetLibraryContentStructuresPageHttpResponse(
					Long assetLibraryId, String search,
					List<String> aggregations, String filterString,
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

			if (search != null) {
				httpInvoker.parameter("search", String.valueOf(search));
			}

			if (filterString != null) {
				httpInvoker.parameter("filter", filterString);
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
						"/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-structures");

			httpInvoker.path("assetLibraryId", assetLibraryId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> getAssetLibraryContentStructurePermissionsPage(
				Long assetLibraryId, String roleNames)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAssetLibraryContentStructurePermissionsPageHttpResponse(
					assetLibraryId, roleNames);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return Page.of(content, Permission::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getAssetLibraryContentStructurePermissionsPageHttpResponse(
					Long assetLibraryId, String roleNames)
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
						"/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-structures/permissions");

			httpInvoker.path("assetLibraryId", assetLibraryId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> putAssetLibraryContentStructurePermission(
				Long assetLibraryId, Permission[] permissions)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putAssetLibraryContentStructurePermissionHttpResponse(
					assetLibraryId, permissions);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return Page.of(content, Permission::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				putAssetLibraryContentStructurePermissionHttpResponse(
					Long assetLibraryId, Permission[] permissions)
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
						"/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-structures/permissions");

			httpInvoker.path("assetLibraryId", assetLibraryId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ContentStructure getContentStructure(Long contentStructureId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getContentStructureHttpResponse(contentStructureId);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return ContentStructureSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getContentStructureHttpResponse(
				Long contentStructureId)
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
						"/o/headless-delivery/v1.0/content-structures/{contentStructureId}");

			httpInvoker.path("contentStructureId", contentStructureId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> getContentStructurePermissionsPage(
				Long contentStructureId, String roleNames)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getContentStructurePermissionsPageHttpResponse(
					contentStructureId, roleNames);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return Page.of(content, Permission::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getContentStructurePermissionsPageHttpResponse(
					Long contentStructureId, String roleNames)
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
						"/o/headless-delivery/v1.0/content-structures/{contentStructureId}/permissions");

			httpInvoker.path("contentStructureId", contentStructureId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> putContentStructurePermission(
				Long contentStructureId, Permission[] permissions)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putContentStructurePermissionHttpResponse(
					contentStructureId, permissions);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return Page.of(content, Permission::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				putContentStructurePermissionHttpResponse(
					Long contentStructureId, Permission[] permissions)
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
						"/o/headless-delivery/v1.0/content-structures/{contentStructureId}/permissions");

			httpInvoker.path("contentStructureId", contentStructureId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<ContentStructure> getSiteContentStructuresPage(
				Long siteId, String search, List<String> aggregations,
				String filterString, Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteContentStructuresPageHttpResponse(
					siteId, search, aggregations, filterString, pagination,
					sortString);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return Page.of(content, ContentStructureSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getSiteContentStructuresPageHttpResponse(
					Long siteId, String search, List<String> aggregations,
					String filterString, Pagination pagination,
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

			if (search != null) {
				httpInvoker.parameter("search", String.valueOf(search));
			}

			if (filterString != null) {
				httpInvoker.parameter("filter", filterString);
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
						"/o/headless-delivery/v1.0/sites/{siteId}/content-structures");

			httpInvoker.path("siteId", siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> getSiteContentStructurePermissionsPage(
				Long siteId, String roleNames)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteContentStructurePermissionsPageHttpResponse(
					siteId, roleNames);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return Page.of(content, Permission::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getSiteContentStructurePermissionsPageHttpResponse(
					Long siteId, String roleNames)
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
						"/o/headless-delivery/v1.0/sites/{siteId}/content-structures/permissions");

			httpInvoker.path("siteId", siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> putSiteContentStructurePermission(
				Long siteId, Permission[] permissions)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putSiteContentStructurePermissionHttpResponse(
					siteId, permissions);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return Page.of(content, Permission::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				putSiteContentStructurePermissionHttpResponse(
					Long siteId, Permission[] permissions)
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
						"/o/headless-delivery/v1.0/sites/{siteId}/content-structures/permissions");

			httpInvoker.path("siteId", siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private ContentStructureResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			ContentStructureResource.class.getName());

		private Builder _builder;

	}

}