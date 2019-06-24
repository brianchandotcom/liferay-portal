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

import com.liferay.change.tracking.rest.client.constant.v1_0.CollectionType;
import com.liferay.change.tracking.rest.client.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.serdes.v1_0.CollectionSerDes;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public interface CollectionResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<Collection> getCollectionsPage(
			Long companyId, CollectionType type, Long userId,
			Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getCollectionsPageHttpResponse(
			Long companyId, CollectionType type, Long userId,
			Pagination pagination, String sortString)
		throws Exception;

	public Collection postCollection(
			Long companyId, Long userId,
			com.liferay.change.tracking.rest.client.dto.v1_0.CollectionUpdate
				collectionUpdate)
		throws Exception;

	public HttpInvoker.HttpResponse postCollectionHttpResponse(
			Long companyId, Long userId,
			com.liferay.change.tracking.rest.client.dto.v1_0.CollectionUpdate
				collectionUpdate)
		throws Exception;

	public void deleteCollection(Long companyId, Long collectionId)
		throws Exception;

	public HttpInvoker.HttpResponse deleteCollectionHttpResponse(
			Long companyId, Long collectionId)
		throws Exception;

	public Collection getCollection(Long companyId, Long collectionId)
		throws Exception;

	public HttpInvoker.HttpResponse getCollectionHttpResponse(
			Long companyId, Long collectionId)
		throws Exception;

	public void postCollectionCheckout(Long collectionId, Long userId)
		throws Exception;

	public HttpInvoker.HttpResponse postCollectionCheckoutHttpResponse(
			Long collectionId, Long userId)
		throws Exception;

	public void postCollectionPublish(
			Long collectionId, Boolean ignoreCollision, Long userId)
		throws Exception;

	public HttpInvoker.HttpResponse postCollectionPublishHttpResponse(
			Long collectionId, Boolean ignoreCollision, Long userId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public CollectionResource build() {
			return new CollectionResourceImpl(this);
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

	public static class CollectionResourceImpl implements CollectionResource {

		public Page<Collection> getCollectionsPage(
				Long companyId, CollectionType type, Long userId,
				Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getCollectionsPageHttpResponse(
					companyId, type, userId, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, CollectionSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getCollectionsPageHttpResponse(
				Long companyId, CollectionType type, Long userId,
				Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (companyId != null) {
				httpInvoker.parameter("companyId", String.valueOf(companyId));
			}

			if (type != null) {
				httpInvoker.parameter("type", String.valueOf(type));
			}

			if (userId != null) {
				httpInvoker.parameter("userId", String.valueOf(userId));
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
					_builder._port + "/o/change-tracking/v1.0/collections");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Collection postCollection(
				Long companyId, Long userId,
				com.liferay.change.tracking.rest.client.dto.v1_0.
					CollectionUpdate collectionUpdate)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = postCollectionHttpResponse(
				companyId, userId, collectionUpdate);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return CollectionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse postCollectionHttpResponse(
				Long companyId, Long userId,
				com.liferay.change.tracking.rest.client.dto.v1_0.
					CollectionUpdate collectionUpdate)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(collectionUpdate.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			if (companyId != null) {
				httpInvoker.parameter("companyId", String.valueOf(companyId));
			}

			if (userId != null) {
				httpInvoker.parameter("userId", String.valueOf(userId));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port + "/o/change-tracking/v1.0/collections");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteCollection(Long companyId, Long collectionId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteCollectionHttpResponse(companyId, collectionId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse deleteCollectionHttpResponse(
				Long companyId, Long collectionId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/change-tracking/v1.0/collections/{collectionId}",
				companyId, collectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Collection getCollection(Long companyId, Long collectionId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = getCollectionHttpResponse(
				companyId, collectionId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return CollectionSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getCollectionHttpResponse(
				Long companyId, Long collectionId)
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
						"/o/change-tracking/v1.0/collections/{collectionId}",
				companyId, collectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postCollectionCheckout(Long collectionId, Long userId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postCollectionCheckoutHttpResponse(collectionId, userId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse postCollectionCheckoutHttpResponse(
				Long collectionId, Long userId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(userId.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			if (userId != null) {
				httpInvoker.parameter("userId", String.valueOf(userId));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/change-tracking/v1.0/collections/{collectionId}/checkout",
				collectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postCollectionPublish(
				Long collectionId, Boolean ignoreCollision, Long userId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postCollectionPublishHttpResponse(
					collectionId, ignoreCollision, userId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse postCollectionPublishHttpResponse(
				Long collectionId, Boolean ignoreCollision, Long userId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(userId.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			if (ignoreCollision != null) {
				httpInvoker.parameter(
					"ignoreCollision", String.valueOf(ignoreCollision));
			}

			if (userId != null) {
				httpInvoker.parameter("userId", String.valueOf(userId));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/change-tracking/v1.0/collections/{collectionId}/publish",
				collectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private CollectionResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			CollectionResource.class.getName());

		private Builder _builder;

	}

}