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

package com.liferay.change.tracking.rest.client.resource.v1_0;

import com.liferay.change.tracking.rest.client.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.serdes.v1_0.EntrySerDes;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public interface EntryResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<Entry> getCollectionEntriesPage(
			String[] changeTypesFilter, String[] classNameIdsFilter,
			String[] groupIdsFilter, Long collectionId, Boolean collision,
			Integer status, String[] userIdsFilter, Pagination pagination,
			String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getCollectionEntriesPageHttpResponse(
			String[] changeTypesFilter, String[] classNameIdsFilter,
			String[] groupIdsFilter, Long collectionId, Boolean collision,
			Integer status, String[] userIdsFilter, Pagination pagination,
			String sortString)
		throws Exception;

	public Entry getEntry(Long entryId) throws Exception;

	public HttpInvoker.HttpResponse getEntryHttpResponse(Long entryId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public EntryResource build() {
			return new EntryResourceImpl(this);
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

		private String _host = "localhost";
		private Locale _locale;
		private String _login = "test@liferay.com";
		private String _password = "test";
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class EntryResourceImpl implements EntryResource {

		public Page<Entry> getCollectionEntriesPage(
				String[] changeTypesFilter, String[] classNameIdsFilter,
				String[] groupIdsFilter, Long collectionId, Boolean collision,
				Integer status, String[] userIdsFilter, Pagination pagination,
				String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getCollectionEntriesPageHttpResponse(
					changeTypesFilter, classNameIdsFilter, groupIdsFilter,
					collectionId, collision, status, userIdsFilter, pagination,
					sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, EntrySerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getCollectionEntriesPageHttpResponse(
				String[] changeTypesFilter, String[] classNameIdsFilter,
				String[] groupIdsFilter, Long collectionId, Boolean collision,
				Integer status, String[] userIdsFilter, Pagination pagination,
				String sortString)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (changeTypesFilter != null) {
				for (int i = 0; i < changeTypesFilter.length; i++) {
					httpInvoker.parameter(
						"changeTypesFilter",
						String.valueOf(changeTypesFilter[i]));
				}
			}

			if (classNameIdsFilter != null) {
				for (int i = 0; i < classNameIdsFilter.length; i++) {
					httpInvoker.parameter(
						"classNameIdsFilter",
						String.valueOf(classNameIdsFilter[i]));
				}
			}

			if (groupIdsFilter != null) {
				for (int i = 0; i < groupIdsFilter.length; i++) {
					httpInvoker.parameter(
						"groupIdsFilter", String.valueOf(groupIdsFilter[i]));
				}
			}

			if (collision != null) {
				httpInvoker.parameter("collision", String.valueOf(collision));
			}

			if (status != null) {
				httpInvoker.parameter("status", String.valueOf(status));
			}

			if (userIdsFilter != null) {
				for (int i = 0; i < userIdsFilter.length; i++) {
					httpInvoker.parameter(
						"userIdsFilter", String.valueOf(userIdsFilter[i]));
				}
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
						"/o/change-tracking/v1.0/collections/{collectionId}/entries",
				collectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Entry getEntry(Long entryId) throws Exception {
			HttpInvoker.HttpResponse httpResponse = getEntryHttpResponse(
				entryId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return EntrySerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getEntryHttpResponse(Long entryId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/change-tracking/v1.0/entries/{entryId}",
				entryId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private EntryResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			EntryResource.class.getName());

		private Builder _builder;

	}

}