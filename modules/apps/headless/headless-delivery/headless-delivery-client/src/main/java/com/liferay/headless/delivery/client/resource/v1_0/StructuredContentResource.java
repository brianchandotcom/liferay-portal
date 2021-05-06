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

import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.problem.Problem;
import com.liferay.headless.delivery.client.serdes.v1_0.RatingSerDes;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentSerDes;

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
public interface StructuredContentResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<StructuredContent> getAssetLibraryStructuredContentsPage(
			Long assetLibraryId, Boolean flatten, String search,
			List<String> aggregations, String filterString,
			Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getAssetLibraryStructuredContentsPageHttpResponse(
				Long assetLibraryId, Boolean flatten, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public StructuredContent postAssetLibraryStructuredContent(
			Long assetLibraryId, StructuredContent structuredContent)
		throws Exception;

	public HttpInvoker.HttpResponse
			postAssetLibraryStructuredContentHttpResponse(
				Long assetLibraryId, StructuredContent structuredContent)
		throws Exception;

	public void postAssetLibraryStructuredContentBatch(
			Long assetLibraryId, String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse
			postAssetLibraryStructuredContentBatchHttpResponse(
				Long assetLibraryId, String callbackURL, Object object)
		throws Exception;

	public Page<Permission> getAssetLibraryStructuredContentPermissionsPage(
			Long assetLibraryId, String roleNames)
		throws Exception;

	public HttpInvoker.HttpResponse
			getAssetLibraryStructuredContentPermissionsPageHttpResponse(
				Long assetLibraryId, String roleNames)
		throws Exception;

	public Page<Permission> putAssetLibraryStructuredContentPermission(
			Long assetLibraryId, Permission[] permissions)
		throws Exception;

	public HttpInvoker.HttpResponse
			putAssetLibraryStructuredContentPermissionHttpResponse(
				Long assetLibraryId, Permission[] permissions)
		throws Exception;

	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			Long contentStructureId, String search, List<String> aggregations,
			String filterString, Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getContentStructureStructuredContentsPageHttpResponse(
				Long contentStructureId, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public Page<StructuredContent> getSiteStructuredContentsPage(
			Long siteId, Boolean flatten, String search,
			List<String> aggregations, String filterString,
			Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getSiteStructuredContentsPageHttpResponse(
			Long siteId, Boolean flatten, String search,
			List<String> aggregations, String filterString,
			Pagination pagination, String sortString)
		throws Exception;

	public StructuredContent postSiteStructuredContent(
			Long siteId, StructuredContent structuredContent)
		throws Exception;

	public HttpInvoker.HttpResponse postSiteStructuredContentHttpResponse(
			Long siteId, StructuredContent structuredContent)
		throws Exception;

	public void postSiteStructuredContentBatch(
			Long siteId, String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse postSiteStructuredContentBatchHttpResponse(
			Long siteId, String callbackURL, Object object)
		throws Exception;

	public StructuredContent getSiteStructuredContentByKey(
			Long siteId, String key)
		throws Exception;

	public HttpInvoker.HttpResponse getSiteStructuredContentByKeyHttpResponse(
			Long siteId, String key)
		throws Exception;

	public StructuredContent getSiteStructuredContentByUuid(
			Long siteId, String uuid)
		throws Exception;

	public HttpInvoker.HttpResponse getSiteStructuredContentByUuidHttpResponse(
			Long siteId, String uuid)
		throws Exception;

	public Page<Permission> getSiteStructuredContentPermissionsPage(
			Long siteId, String roleNames)
		throws Exception;

	public HttpInvoker.HttpResponse
			getSiteStructuredContentPermissionsPageHttpResponse(
				Long siteId, String roleNames)
		throws Exception;

	public Page<Permission> putSiteStructuredContentPermission(
			Long siteId, Permission[] permissions)
		throws Exception;

	public HttpInvoker.HttpResponse
			putSiteStructuredContentPermissionHttpResponse(
				Long siteId, Permission[] permissions)
		throws Exception;

	public Page<StructuredContent>
			getStructuredContentFolderStructuredContentsPage(
				Long structuredContentFolderId, Boolean flatten, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getStructuredContentFolderStructuredContentsPageHttpResponse(
				Long structuredContentFolderId, Boolean flatten, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public StructuredContent postStructuredContentFolderStructuredContent(
			Long structuredContentFolderId, StructuredContent structuredContent)
		throws Exception;

	public HttpInvoker.HttpResponse
			postStructuredContentFolderStructuredContentHttpResponse(
				Long structuredContentFolderId,
				StructuredContent structuredContent)
		throws Exception;

	public void postStructuredContentFolderStructuredContentBatch(
			Long structuredContentFolderId, String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse
			postStructuredContentFolderStructuredContentBatchHttpResponse(
				Long structuredContentFolderId, String callbackURL,
				Object object)
		throws Exception;

	public void deleteStructuredContent(Long structuredContentId)
		throws Exception;

	public HttpInvoker.HttpResponse deleteStructuredContentHttpResponse(
			Long structuredContentId)
		throws Exception;

	public void deleteStructuredContentBatch(String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse deleteStructuredContentBatchHttpResponse(
			String callbackURL, Object object)
		throws Exception;

	public StructuredContent getStructuredContent(Long structuredContentId)
		throws Exception;

	public HttpInvoker.HttpResponse getStructuredContentHttpResponse(
			Long structuredContentId)
		throws Exception;

	public StructuredContent patchStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception;

	public HttpInvoker.HttpResponse patchStructuredContentHttpResponse(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception;

	public StructuredContent putStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception;

	public HttpInvoker.HttpResponse putStructuredContentHttpResponse(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception;

	public void putStructuredContentBatch(String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse putStructuredContentBatchHttpResponse(
			String callbackURL, Object object)
		throws Exception;

	public void deleteStructuredContentMyRating(Long structuredContentId)
		throws Exception;

	public HttpInvoker.HttpResponse deleteStructuredContentMyRatingHttpResponse(
			Long structuredContentId)
		throws Exception;

	public Rating getStructuredContentMyRating(Long structuredContentId)
		throws Exception;

	public HttpInvoker.HttpResponse getStructuredContentMyRatingHttpResponse(
			Long structuredContentId)
		throws Exception;

	public Rating postStructuredContentMyRating(
			Long structuredContentId, Rating rating)
		throws Exception;

	public HttpInvoker.HttpResponse postStructuredContentMyRatingHttpResponse(
			Long structuredContentId, Rating rating)
		throws Exception;

	public Rating putStructuredContentMyRating(
			Long structuredContentId, Rating rating)
		throws Exception;

	public HttpInvoker.HttpResponse putStructuredContentMyRatingHttpResponse(
			Long structuredContentId, Rating rating)
		throws Exception;

	public Page<Permission> getStructuredContentPermissionsPage(
			Long structuredContentId, String roleNames)
		throws Exception;

	public HttpInvoker.HttpResponse
			getStructuredContentPermissionsPageHttpResponse(
				Long structuredContentId, String roleNames)
		throws Exception;

	public Page<Permission> putStructuredContentPermission(
			Long structuredContentId, Permission[] permissions)
		throws Exception;

	public HttpInvoker.HttpResponse putStructuredContentPermissionHttpResponse(
			Long structuredContentId, Permission[] permissions)
		throws Exception;

	public String
			getStructuredContentRenderedContentByDisplayPageDisplayPageKey(
				Long structuredContentId, String displayPageKey)
		throws Exception;

	public HttpInvoker.HttpResponse
			getStructuredContentRenderedContentByDisplayPageDisplayPageKeyHttpResponse(
				Long structuredContentId, String displayPageKey)
		throws Exception;

	public String getStructuredContentRenderedContentTemplate(
			Long structuredContentId, String contentTemplateId)
		throws Exception;

	public HttpInvoker.HttpResponse
			getStructuredContentRenderedContentTemplateHttpResponse(
				Long structuredContentId, String contentTemplateId)
		throws Exception;

	public void putStructuredContentSubscribe(Long structuredContentId)
		throws Exception;

	public HttpInvoker.HttpResponse putStructuredContentSubscribeHttpResponse(
			Long structuredContentId)
		throws Exception;

	public void putStructuredContentUnsubscribe(Long structuredContentId)
		throws Exception;

	public HttpInvoker.HttpResponse putStructuredContentUnsubscribeHttpResponse(
			Long structuredContentId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public StructuredContentResource build() {
			return new StructuredContentResourceImpl(this);
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

	public static class StructuredContentResourceImpl
		implements StructuredContentResource {

		public Page<StructuredContent> getAssetLibraryStructuredContentsPage(
				Long assetLibraryId, Boolean flatten, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAssetLibraryStructuredContentsPageHttpResponse(
					assetLibraryId, flatten, search, aggregations, filterString,
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
				return Page.of(content, StructuredContentSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getAssetLibraryStructuredContentsPageHttpResponse(
					Long assetLibraryId, Boolean flatten, String search,
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

			if (flatten != null) {
				httpInvoker.parameter("flatten", String.valueOf(flatten));
			}

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
						"/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents");

			httpInvoker.path("assetLibraryId", assetLibraryId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public StructuredContent postAssetLibraryStructuredContent(
				Long assetLibraryId, StructuredContent structuredContent)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postAssetLibraryStructuredContentHttpResponse(
					assetLibraryId, structuredContent);

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
				return StructuredContentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postAssetLibraryStructuredContentHttpResponse(
					Long assetLibraryId, StructuredContent structuredContent)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(structuredContent.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents");

			httpInvoker.path("assetLibraryId", assetLibraryId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postAssetLibraryStructuredContentBatch(
				Long assetLibraryId, String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postAssetLibraryStructuredContentBatchHttpResponse(
					assetLibraryId, callbackURL, object);

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
		}

		public HttpInvoker.HttpResponse
				postAssetLibraryStructuredContentBatchHttpResponse(
					Long assetLibraryId, String callbackURL, Object object)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(object.toString(), "application/json");

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

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/batch");

			httpInvoker.path("assetLibraryId", assetLibraryId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> getAssetLibraryStructuredContentPermissionsPage(
				Long assetLibraryId, String roleNames)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAssetLibraryStructuredContentPermissionsPageHttpResponse(
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
				getAssetLibraryStructuredContentPermissionsPageHttpResponse(
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
						"/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/permissions");

			httpInvoker.path("assetLibraryId", assetLibraryId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> putAssetLibraryStructuredContentPermission(
				Long assetLibraryId, Permission[] permissions)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putAssetLibraryStructuredContentPermissionHttpResponse(
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
				putAssetLibraryStructuredContentPermissionHttpResponse(
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
						"/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/permissions");

			httpInvoker.path("assetLibraryId", assetLibraryId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<StructuredContent>
				getContentStructureStructuredContentsPage(
					Long contentStructureId, String search,
					List<String> aggregations, String filterString,
					Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getContentStructureStructuredContentsPageHttpResponse(
					contentStructureId, search, aggregations, filterString,
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
				return Page.of(content, StructuredContentSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getContentStructureStructuredContentsPageHttpResponse(
					Long contentStructureId, String search,
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
						"/o/headless-delivery/v1.0/content-structures/{contentStructureId}/structured-contents");

			httpInvoker.path("contentStructureId", contentStructureId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<StructuredContent> getSiteStructuredContentsPage(
				Long siteId, Boolean flatten, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteStructuredContentsPageHttpResponse(
					siteId, flatten, search, aggregations, filterString,
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
				return Page.of(content, StructuredContentSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getSiteStructuredContentsPageHttpResponse(
					Long siteId, Boolean flatten, String search,
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

			if (flatten != null) {
				httpInvoker.parameter("flatten", String.valueOf(flatten));
			}

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
						"/o/headless-delivery/v1.0/sites/{siteId}/structured-contents");

			httpInvoker.path("siteId", siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public StructuredContent postSiteStructuredContent(
				Long siteId, StructuredContent structuredContent)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postSiteStructuredContentHttpResponse(
					siteId, structuredContent);

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
				return StructuredContentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postSiteStructuredContentHttpResponse(
				Long siteId, StructuredContent structuredContent)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(structuredContent.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/sites/{siteId}/structured-contents");

			httpInvoker.path("siteId", siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postSiteStructuredContentBatch(
				Long siteId, String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postSiteStructuredContentBatchHttpResponse(
					siteId, callbackURL, object);

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
		}

		public HttpInvoker.HttpResponse
				postSiteStructuredContentBatchHttpResponse(
					Long siteId, String callbackURL, Object object)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(object.toString(), "application/json");

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

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/batch");

			httpInvoker.path("siteId", siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public StructuredContent getSiteStructuredContentByKey(
				Long siteId, String key)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteStructuredContentByKeyHttpResponse(siteId, key);

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
				return StructuredContentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getSiteStructuredContentByKeyHttpResponse(
					Long siteId, String key)
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
						"/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-key/{key}");

			httpInvoker.path("siteId", siteId);
			httpInvoker.path("key", key);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public StructuredContent getSiteStructuredContentByUuid(
				Long siteId, String uuid)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteStructuredContentByUuidHttpResponse(siteId, uuid);

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
				return StructuredContentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getSiteStructuredContentByUuidHttpResponse(
					Long siteId, String uuid)
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
						"/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-uuid/{uuid}");

			httpInvoker.path("siteId", siteId);
			httpInvoker.path("uuid", uuid);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> getSiteStructuredContentPermissionsPage(
				Long siteId, String roleNames)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteStructuredContentPermissionsPageHttpResponse(
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
				getSiteStructuredContentPermissionsPageHttpResponse(
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
						"/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/permissions");

			httpInvoker.path("siteId", siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> putSiteStructuredContentPermission(
				Long siteId, Permission[] permissions)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putSiteStructuredContentPermissionHttpResponse(
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
				putSiteStructuredContentPermissionHttpResponse(
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
						"/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/permissions");

			httpInvoker.path("siteId", siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<StructuredContent>
				getStructuredContentFolderStructuredContentsPage(
					Long structuredContentFolderId, Boolean flatten,
					String search, List<String> aggregations,
					String filterString, Pagination pagination,
					String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getStructuredContentFolderStructuredContentsPageHttpResponse(
					structuredContentFolderId, flatten, search, aggregations,
					filterString, pagination, sortString);

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
				return Page.of(content, StructuredContentSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getStructuredContentFolderStructuredContentsPageHttpResponse(
					Long structuredContentFolderId, Boolean flatten,
					String search, List<String> aggregations,
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

			if (flatten != null) {
				httpInvoker.parameter("flatten", String.valueOf(flatten));
			}

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
						"/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents");

			httpInvoker.path(
				"structuredContentFolderId", structuredContentFolderId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public StructuredContent postStructuredContentFolderStructuredContent(
				Long structuredContentFolderId,
				StructuredContent structuredContent)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postStructuredContentFolderStructuredContentHttpResponse(
					structuredContentFolderId, structuredContent);

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
				return StructuredContentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postStructuredContentFolderStructuredContentHttpResponse(
					Long structuredContentFolderId,
					StructuredContent structuredContent)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(structuredContent.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents");

			httpInvoker.path(
				"structuredContentFolderId", structuredContentFolderId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postStructuredContentFolderStructuredContentBatch(
				Long structuredContentFolderId, String callbackURL,
				Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postStructuredContentFolderStructuredContentBatchHttpResponse(
					structuredContentFolderId, callbackURL, object);

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
		}

		public HttpInvoker.HttpResponse
				postStructuredContentFolderStructuredContentBatchHttpResponse(
					Long structuredContentFolderId, String callbackURL,
					Object object)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(object.toString(), "application/json");

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

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents/batch");

			httpInvoker.path(
				"structuredContentFolderId", structuredContentFolderId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteStructuredContent(Long structuredContentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteStructuredContentHttpResponse(structuredContentId);

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
				return;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse deleteStructuredContentHttpResponse(
				Long structuredContentId)
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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteStructuredContentBatch(
				String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteStructuredContentBatchHttpResponse(callbackURL, object);

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
		}

		public HttpInvoker.HttpResponse
				deleteStructuredContentBatchHttpResponse(
					String callbackURL, Object object)
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

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/structured-contents/batch");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public StructuredContent getStructuredContent(Long structuredContentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getStructuredContentHttpResponse(structuredContentId);

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
				return StructuredContentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getStructuredContentHttpResponse(
				Long structuredContentId)
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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public StructuredContent patchStructuredContent(
				Long structuredContentId, StructuredContent structuredContent)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				patchStructuredContentHttpResponse(
					structuredContentId, structuredContent);

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
				return StructuredContentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse patchStructuredContentHttpResponse(
				Long structuredContentId, StructuredContent structuredContent)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(structuredContent.toString(), "application/json");

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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public StructuredContent putStructuredContent(
				Long structuredContentId, StructuredContent structuredContent)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putStructuredContentHttpResponse(
					structuredContentId, structuredContent);

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
				return StructuredContentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse putStructuredContentHttpResponse(
				Long structuredContentId, StructuredContent structuredContent)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(structuredContent.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putStructuredContentBatch(String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putStructuredContentBatchHttpResponse(callbackURL, object);

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
		}

		public HttpInvoker.HttpResponse putStructuredContentBatchHttpResponse(
				String callbackURL, Object object)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(object.toString(), "application/json");

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

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/structured-contents/batch");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteStructuredContentMyRating(Long structuredContentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteStructuredContentMyRatingHttpResponse(
					structuredContentId);

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
				return;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				deleteStructuredContentMyRatingHttpResponse(
					Long structuredContentId)
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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Rating getStructuredContentMyRating(Long structuredContentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getStructuredContentMyRatingHttpResponse(structuredContentId);

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
				return RatingSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getStructuredContentMyRatingHttpResponse(
					Long structuredContentId)
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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Rating postStructuredContentMyRating(
				Long structuredContentId, Rating rating)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postStructuredContentMyRatingHttpResponse(
					structuredContentId, rating);

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
				return RatingSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postStructuredContentMyRatingHttpResponse(
					Long structuredContentId, Rating rating)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(rating.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Rating putStructuredContentMyRating(
				Long structuredContentId, Rating rating)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putStructuredContentMyRatingHttpResponse(
					structuredContentId, rating);

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
				return RatingSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				putStructuredContentMyRatingHttpResponse(
					Long structuredContentId, Rating rating)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(rating.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> getStructuredContentPermissionsPage(
				Long structuredContentId, String roleNames)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getStructuredContentPermissionsPageHttpResponse(
					structuredContentId, roleNames);

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
				getStructuredContentPermissionsPageHttpResponse(
					Long structuredContentId, String roleNames)
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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/permissions");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Permission> putStructuredContentPermission(
				Long structuredContentId, Permission[] permissions)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putStructuredContentPermissionHttpResponse(
					structuredContentId, permissions);

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
				putStructuredContentPermissionHttpResponse(
					Long structuredContentId, Permission[] permissions)
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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/permissions");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public String
				getStructuredContentRenderedContentByDisplayPageDisplayPageKey(
					Long structuredContentId, String displayPageKey)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getStructuredContentRenderedContentByDisplayPageDisplayPageKeyHttpResponse(
					structuredContentId, displayPageKey);

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
				return content;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getStructuredContentRenderedContentByDisplayPageDisplayPageKeyHttpResponse(
					Long structuredContentId, String displayPageKey)
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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/rendered-content-by-display-page/{displayPageKey}");

			httpInvoker.path("structuredContentId", structuredContentId);
			httpInvoker.path("displayPageKey", displayPageKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public String getStructuredContentRenderedContentTemplate(
				Long structuredContentId, String contentTemplateId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getStructuredContentRenderedContentTemplateHttpResponse(
					structuredContentId, contentTemplateId);

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
				return content;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getStructuredContentRenderedContentTemplateHttpResponse(
					Long structuredContentId, String contentTemplateId)
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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/rendered-content/{contentTemplateId}");

			httpInvoker.path("structuredContentId", structuredContentId);
			httpInvoker.path("contentTemplateId", contentTemplateId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putStructuredContentSubscribe(Long structuredContentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putStructuredContentSubscribeHttpResponse(structuredContentId);

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
				return;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				putStructuredContentSubscribeHttpResponse(
					Long structuredContentId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				structuredContentId.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/subscribe");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putStructuredContentUnsubscribe(Long structuredContentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putStructuredContentUnsubscribeHttpResponse(
					structuredContentId);

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
				return;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				putStructuredContentUnsubscribeHttpResponse(
					Long structuredContentId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				structuredContentId.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/unsubscribe");

			httpInvoker.path("structuredContentId", structuredContentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private StructuredContentResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			StructuredContentResource.class.getName());

		private Builder _builder;

	}

}