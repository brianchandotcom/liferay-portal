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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.problem.Problem;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardThreadSerDes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface MessageBoardThreadResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<MessageBoardThread>
			getMessageBoardSectionMessageBoardThreadsPage(
				Long messageBoardSectionId, String search, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getMessageBoardSectionMessageBoardThreadsPageHttpResponse(
				Long messageBoardSectionId, String search, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public MessageBoardThread postMessageBoardSectionMessageBoardThread(
			Long messageBoardSectionId, MessageBoardThread messageBoardThread)
		throws Exception;

	public HttpInvoker.HttpResponse
			postMessageBoardSectionMessageBoardThreadHttpResponse(
				Long messageBoardSectionId,
				MessageBoardThread messageBoardThread)
		throws Exception;

	public Page<MessageBoardThread> getMessageBoardThreadsRankedPage(
			java.util.Date dateCreated, java.util.Date dateModified,
			Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getMessageBoardThreadsRankedPageHttpResponse(
				java.util.Date dateCreated, java.util.Date dateModified,
				Pagination pagination, String sortString)
		throws Exception;

	public void deleteMessageBoardThread(Long messageBoardThreadId)
		throws Exception;

	public HttpInvoker.HttpResponse deleteMessageBoardThreadHttpResponse(
			Long messageBoardThreadId)
		throws Exception;

	public MessageBoardThread getMessageBoardThread(Long messageBoardThreadId)
		throws Exception;

	public HttpInvoker.HttpResponse getMessageBoardThreadHttpResponse(
			Long messageBoardThreadId)
		throws Exception;

	public MessageBoardThread patchMessageBoardThread(
			Long messageBoardThreadId, MessageBoardThread messageBoardThread)
		throws Exception;

	public HttpInvoker.HttpResponse patchMessageBoardThreadHttpResponse(
			Long messageBoardThreadId, MessageBoardThread messageBoardThread)
		throws Exception;

	public MessageBoardThread putMessageBoardThread(
			Long messageBoardThreadId, MessageBoardThread messageBoardThread)
		throws Exception;

	public HttpInvoker.HttpResponse putMessageBoardThreadHttpResponse(
			Long messageBoardThreadId, MessageBoardThread messageBoardThread)
		throws Exception;

	public void deleteMessageBoardThreadMyRating(Long messageBoardThreadId)
		throws Exception;

	public HttpInvoker.HttpResponse
			deleteMessageBoardThreadMyRatingHttpResponse(
				Long messageBoardThreadId)
		throws Exception;

	public com.liferay.headless.delivery.client.dto.v1_0.Rating
			getMessageBoardThreadMyRating(Long messageBoardThreadId)
		throws Exception;

	public HttpInvoker.HttpResponse getMessageBoardThreadMyRatingHttpResponse(
			Long messageBoardThreadId)
		throws Exception;

	public com.liferay.headless.delivery.client.dto.v1_0.Rating
			postMessageBoardThreadMyRating(
				Long messageBoardThreadId,
				com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
		throws Exception;

	public HttpInvoker.HttpResponse postMessageBoardThreadMyRatingHttpResponse(
			Long messageBoardThreadId,
			com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
		throws Exception;

	public com.liferay.headless.delivery.client.dto.v1_0.Rating
			putMessageBoardThreadMyRating(
				Long messageBoardThreadId,
				com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
		throws Exception;

	public HttpInvoker.HttpResponse putMessageBoardThreadMyRatingHttpResponse(
			Long messageBoardThreadId,
			com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
		throws Exception;

	public void putMessageBoardThreadSubscribe(Long messageBoardThreadId)
		throws Exception;

	public HttpInvoker.HttpResponse putMessageBoardThreadSubscribeHttpResponse(
			Long messageBoardThreadId)
		throws Exception;

	public void putMessageBoardThreadUnsubscribe(Long messageBoardThreadId)
		throws Exception;

	public HttpInvoker.HttpResponse
			putMessageBoardThreadUnsubscribeHttpResponse(
				Long messageBoardThreadId)
		throws Exception;

	public Page<MessageBoardThread> getSiteMessageBoardThreadsPage(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getSiteMessageBoardThreadsPageHttpResponse(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception;

	public MessageBoardThread postSiteMessageBoardThread(
			Long siteId, MessageBoardThread messageBoardThread)
		throws Exception;

	public HttpInvoker.HttpResponse postSiteMessageBoardThreadHttpResponse(
			Long siteId, MessageBoardThread messageBoardThread)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public MessageBoardThreadResource build() {
			return new MessageBoardThreadResourceImpl(this);
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

	public static class MessageBoardThreadResourceImpl
		implements MessageBoardThreadResource {

		public Page<MessageBoardThread>
				getMessageBoardSectionMessageBoardThreadsPage(
					Long messageBoardSectionId, String search,
					String filterString, Pagination pagination,
					String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getMessageBoardSectionMessageBoardThreadsPageHttpResponse(
					messageBoardSectionId, search, filterString, pagination,
					sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, MessageBoardThreadSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getMessageBoardSectionMessageBoardThreadsPageHttpResponse(
					Long messageBoardSectionId, String search,
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
						"/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads",
				messageBoardSectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public MessageBoardThread postMessageBoardSectionMessageBoardThread(
				Long messageBoardSectionId,
				MessageBoardThread messageBoardThread)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postMessageBoardSectionMessageBoardThreadHttpResponse(
					messageBoardSectionId, messageBoardThread);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return MessageBoardThreadSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postMessageBoardSectionMessageBoardThreadHttpResponse(
					Long messageBoardSectionId,
					MessageBoardThread messageBoardThread)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(messageBoardThread.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads",
				messageBoardSectionId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<MessageBoardThread> getMessageBoardThreadsRankedPage(
				java.util.Date dateCreated, java.util.Date dateModified,
				Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getMessageBoardThreadsRankedPageHttpResponse(
					dateCreated, dateModified, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, MessageBoardThreadSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getMessageBoardThreadsRankedPageHttpResponse(
					java.util.Date dateCreated, java.util.Date dateModified,
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

			DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");

			if (dateCreated != null) {
				httpInvoker.parameter(
					"dateCreated", liferayToJSONDateFormat.format(dateCreated));
			}

			if (dateModified != null) {
				httpInvoker.parameter(
					"dateModified",
					liferayToJSONDateFormat.format(dateModified));
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
						"/o/headless-delivery/v1.0/message-board-threads/ranked");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteMessageBoardThread(Long messageBoardThreadId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteMessageBoardThreadHttpResponse(messageBoardThreadId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

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

		public HttpInvoker.HttpResponse deleteMessageBoardThreadHttpResponse(
				Long messageBoardThreadId)
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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public MessageBoardThread getMessageBoardThread(
				Long messageBoardThreadId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getMessageBoardThreadHttpResponse(messageBoardThreadId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return MessageBoardThreadSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getMessageBoardThreadHttpResponse(
				Long messageBoardThreadId)
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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public MessageBoardThread patchMessageBoardThread(
				Long messageBoardThreadId,
				MessageBoardThread messageBoardThread)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				patchMessageBoardThreadHttpResponse(
					messageBoardThreadId, messageBoardThread);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return MessageBoardThreadSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse patchMessageBoardThreadHttpResponse(
				Long messageBoardThreadId,
				MessageBoardThread messageBoardThread)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(messageBoardThread.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public MessageBoardThread putMessageBoardThread(
				Long messageBoardThreadId,
				MessageBoardThread messageBoardThread)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putMessageBoardThreadHttpResponse(
					messageBoardThreadId, messageBoardThread);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return MessageBoardThreadSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse putMessageBoardThreadHttpResponse(
				Long messageBoardThreadId,
				MessageBoardThread messageBoardThread)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(messageBoardThread.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteMessageBoardThreadMyRating(Long messageBoardThreadId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteMessageBoardThreadMyRatingHttpResponse(
					messageBoardThreadId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

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
				deleteMessageBoardThreadMyRatingHttpResponse(
					Long messageBoardThreadId)
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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public com.liferay.headless.delivery.client.dto.v1_0.Rating
				getMessageBoardThreadMyRating(Long messageBoardThreadId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getMessageBoardThreadMyRatingHttpResponse(messageBoardThreadId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return com.liferay.headless.delivery.client.serdes.v1_0.
					RatingSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getMessageBoardThreadMyRatingHttpResponse(
					Long messageBoardThreadId)
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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public com.liferay.headless.delivery.client.dto.v1_0.Rating
				postMessageBoardThreadMyRating(
					Long messageBoardThreadId,
					com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postMessageBoardThreadMyRatingHttpResponse(
					messageBoardThreadId, rating);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return com.liferay.headless.delivery.client.serdes.v1_0.
					RatingSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postMessageBoardThreadMyRatingHttpResponse(
					Long messageBoardThreadId,
					com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public com.liferay.headless.delivery.client.dto.v1_0.Rating
				putMessageBoardThreadMyRating(
					Long messageBoardThreadId,
					com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putMessageBoardThreadMyRatingHttpResponse(
					messageBoardThreadId, rating);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return com.liferay.headless.delivery.client.serdes.v1_0.
					RatingSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				putMessageBoardThreadMyRatingHttpResponse(
					Long messageBoardThreadId,
					com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putMessageBoardThreadSubscribe(Long messageBoardThreadId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putMessageBoardThreadSubscribeHttpResponse(
					messageBoardThreadId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

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
				putMessageBoardThreadSubscribeHttpResponse(
					Long messageBoardThreadId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				messageBoardThreadId.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/subscribe",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putMessageBoardThreadUnsubscribe(Long messageBoardThreadId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putMessageBoardThreadUnsubscribeHttpResponse(
					messageBoardThreadId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

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
				putMessageBoardThreadUnsubscribeHttpResponse(
					Long messageBoardThreadId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				messageBoardThreadId.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/unsubscribe",
				messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<MessageBoardThread> getSiteMessageBoardThreadsPage(
				Long siteId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteMessageBoardThreadsPageHttpResponse(
					siteId, flatten, search, filterString, pagination,
					sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, MessageBoardThreadSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getSiteMessageBoardThreadsPageHttpResponse(
					Long siteId, Boolean flatten, String search,
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
						"/o/headless-delivery/v1.0/sites/{siteId}/message-board-threads",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public MessageBoardThread postSiteMessageBoardThread(
				Long siteId, MessageBoardThread messageBoardThread)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postSiteMessageBoardThreadHttpResponse(
					siteId, messageBoardThread);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return MessageBoardThreadSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postSiteMessageBoardThreadHttpResponse(
				Long siteId, MessageBoardThread messageBoardThread)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(messageBoardThread.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/sites/{siteId}/message-board-threads",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private MessageBoardThreadResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			MessageBoardThreadResource.class.getName());

		private Builder _builder;

	}

}