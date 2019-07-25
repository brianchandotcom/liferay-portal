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

package com.liferay.headless.admin.user.client.resource.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.SegmentUser;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.serdes.v1_0.SegmentUserSerDes;

import java.util.Locale;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface SegmentUserResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<SegmentUser> getSegmentUserAccountsPage(
			Long segmentId, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse getSegmentUserAccountsPageHttpResponse(
			Long segmentId, Pagination pagination)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public SegmentUserResource build() {
			return new SegmentUserResourceImpl(this);
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

	public static class SegmentUserResourceImpl implements SegmentUserResource {

		public Page<SegmentUser> getSegmentUserAccountsPage(
				Long segmentId, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSegmentUserAccountsPageHttpResponse(segmentId, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, SegmentUserSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getSegmentUserAccountsPageHttpResponse(
				Long segmentId, Pagination pagination)
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
						"/o/headless-admin-user/v1.0/segments/{segmentId}/user-accounts",
				segmentId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private SegmentUserResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			SegmentUserResource.class.getName());

		private Builder _builder;

	}

}