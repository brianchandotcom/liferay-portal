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

import com.liferay.headless.delivery.client.dto.v1_0.ContentSetElement;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.serdes.v1_0.ContentSetElementSerDes;

import java.util.Locale;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface ContentSetElementResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<ContentSetElement> getContentSetContentSetElementsPage(
			Long contentSetId, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getContentSetContentSetElementsPageHttpResponse(
				Long contentSetId, Pagination pagination)
		throws Exception;

	public Page<ContentSetElement> getSiteContentSetByKeyContentSetElementsPage(
			Long siteId, String key, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getSiteContentSetByKeyContentSetElementsPageHttpResponse(
				Long siteId, String key, Pagination pagination)
		throws Exception;

	public Page<ContentSetElement>
			getSiteContentSetByUuidContentSetElementsPage(
				Long siteId, String uuid, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getSiteContentSetByUuidContentSetElementsPageHttpResponse(
				Long siteId, String uuid, Pagination pagination)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public ContentSetElementResource build() {
			return new ContentSetElementResourceImpl(this);
		}

		public Builder cookieAuthentication(String cookie) {
			_cookie = cookie;

			return this;
		}

		public Builder endpoint(String host, int port, String scheme) {
			_host = host;
			_port = port;
			_scheme = scheme;

			return this;
		}

		public Builder locale(Locale locale) {
			_locale = locale;

			return this;
		}

		private Builder() {
		}

		private String _cookie;
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "test@liferay.com";
		private String _password = "test";
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class ContentSetElementResourceImpl
		implements ContentSetElementResource {

		public Page<ContentSetElement> getContentSetContentSetElementsPage(
				Long contentSetId, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getContentSetContentSetElementsPageHttpResponse(
					contentSetId, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ContentSetElementSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getContentSetContentSetElementsPageHttpResponse(
					Long contentSetId, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/content-sets/{contentSetId}/content-set-elements",
				contentSetId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<ContentSetElement>
				getSiteContentSetByKeyContentSetElementsPage(
					Long siteId, String key, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteContentSetByKeyContentSetElementsPageHttpResponse(
					siteId, key, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ContentSetElementSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getSiteContentSetByKeyContentSetElementsPageHttpResponse(
					Long siteId, String key, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/sites/{siteId}/content-sets/by-key/{key}/content-set-elements",
				siteId, key);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<ContentSetElement>
				getSiteContentSetByUuidContentSetElementsPage(
					Long siteId, String uuid, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteContentSetByUuidContentSetElementsPageHttpResponse(
					siteId, uuid, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ContentSetElementSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getSiteContentSetByUuidContentSetElementsPageHttpResponse(
					Long siteId, String uuid, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/sites/{siteId}/content-sets/by-uuid/{uuid}/content-set-elements",
				siteId, uuid);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private ContentSetElementResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			ContentSetElementResource.class.getName());

		private Builder _builder;

	}

}