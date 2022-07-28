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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSuspiciousActivity;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.problem.Problem;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardSuspiciousActivitySerDes;

import java.util.LinkedHashMap;
import java.util.List;
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
public interface MessageBoardSuspiciousActivityResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<MessageBoardSuspiciousActivity>
			getMessageBoardMessageMessageBoardSuspiciousActivityPage(
				Long messageBoardMessageId, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getMessageBoardMessageMessageBoardSuspiciousActivityPageHttpResponse(
				Long messageBoardMessageId, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public Page<MessageBoardSuspiciousActivity>
			postMessageBoardMessageMessageBoardSuspiciousActivityPage(
				Long messageBoardMessageId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception;

	public HttpInvoker.HttpResponse
			postMessageBoardMessageMessageBoardSuspiciousActivityPageHttpResponse(
				Long messageBoardMessageId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception;

	public void
			deleteMessageBoardSuspiciousActivityMessageBoardSuspiciousActivity(
				Long suspiciousActivityId)
		throws Exception;

	public HttpInvoker.HttpResponse
			deleteMessageBoardSuspiciousActivityMessageBoardSuspiciousActivityHttpResponse(
				Long suspiciousActivityId)
		throws Exception;

	public Page<MessageBoardSuspiciousActivity>
			getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
				Long suspiciousActivityId, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageHttpResponse(
				Long suspiciousActivityId, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public Page<MessageBoardSuspiciousActivity>
			getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage(
				Long suspiciousActivityId)
		throws Exception;

	public HttpInvoker.HttpResponse
			getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPageHttpResponse(
				Long suspiciousActivityId)
		throws Exception;

	public Page<MessageBoardSuspiciousActivity>
			getMessageBoardThreadMessageBoardSuspiciousActivityPage(
				Long messageBoardThreadId, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getMessageBoardThreadMessageBoardSuspiciousActivityPageHttpResponse(
				Long messageBoardThreadId, String search,
				List<String> aggregations, String filterString,
				Pagination pagination, String sortString)
		throws Exception;

	public MessageBoardSuspiciousActivity
			postMessageBoardThreadMessageBoardSuspiciousActivity(
				Long messageBoardThreadId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception;

	public HttpInvoker.HttpResponse
			postMessageBoardThreadMessageBoardSuspiciousActivityHttpResponse(
				Long messageBoardThreadId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception;

	public void postMessageBoardThreadMessageBoardSuspiciousActivityBatch(
			Long messageBoardThreadId, String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse
			postMessageBoardThreadMessageBoardSuspiciousActivityBatchHttpResponse(
				Long messageBoardThreadId, String callbackURL, Object object)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public MessageBoardSuspiciousActivityResource build() {
			return new MessageBoardSuspiciousActivityResourceImpl(this);
		}

		public Builder contextPath(String contextPath) {
			_contextPath = contextPath;

			return this;
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

		private String _contextPath = "";
		private Map<String, String> _headers = new LinkedHashMap<>();
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "";
		private String _password = "";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class MessageBoardSuspiciousActivityResourceImpl
		implements MessageBoardSuspiciousActivityResource {

		public Page<MessageBoardSuspiciousActivity>
				getMessageBoardMessageMessageBoardSuspiciousActivityPage(
					Long messageBoardMessageId, String search,
					List<String> aggregations, String filterString,
					Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getMessageBoardMessageMessageBoardSuspiciousActivityPageHttpResponse(
					messageBoardMessageId, search, aggregations, filterString,
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
				return Page.of(
					content, MessageBoardSuspiciousActivitySerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getMessageBoardMessageMessageBoardSuspiciousActivityPageHttpResponse(
					Long messageBoardMessageId, String search,
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
					_builder._port + _builder._contextPath +
						"/o/headless-delivery/v1.0/message-board-message/{messageBoardMessageId}/message-board-suspicious-activity");

			httpInvoker.path("messageBoardMessageId", messageBoardMessageId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<MessageBoardSuspiciousActivity>
				postMessageBoardMessageMessageBoardSuspiciousActivityPage(
					Long messageBoardMessageId,
					MessageBoardSuspiciousActivity
						messageBoardSuspiciousActivity)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postMessageBoardMessageMessageBoardSuspiciousActivityPageHttpResponse(
					messageBoardMessageId, messageBoardSuspiciousActivity);

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
				return Page.of(
					content, MessageBoardSuspiciousActivitySerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postMessageBoardMessageMessageBoardSuspiciousActivityPageHttpResponse(
					Long messageBoardMessageId,
					MessageBoardSuspiciousActivity
						messageBoardSuspiciousActivity)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				messageBoardSuspiciousActivity.toString(), "application/json");

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
					_builder._port + _builder._contextPath +
						"/o/headless-delivery/v1.0/message-board-message/{messageBoardMessageId}/message-board-suspicious-activity");

			httpInvoker.path("messageBoardMessageId", messageBoardMessageId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void
				deleteMessageBoardSuspiciousActivityMessageBoardSuspiciousActivity(
					Long suspiciousActivityId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteMessageBoardSuspiciousActivityMessageBoardSuspiciousActivityHttpResponse(
					suspiciousActivityId);

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
				deleteMessageBoardSuspiciousActivityMessageBoardSuspiciousActivityHttpResponse(
					Long suspiciousActivityId)
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
					_builder._port + _builder._contextPath +
						"/o/headless-delivery/v1.0/message-board-suspicious-activity/{suspiciousActivityId}/message-board-suspicious-activity");

			httpInvoker.path("suspiciousActivityId", suspiciousActivityId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<MessageBoardSuspiciousActivity>
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
					Long suspiciousActivityId, String search,
					List<String> aggregations, String filterString,
					Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageHttpResponse(
					suspiciousActivityId, search, aggregations, filterString,
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
				return Page.of(
					content, MessageBoardSuspiciousActivitySerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageHttpResponse(
					Long suspiciousActivityId, String search,
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
					_builder._port + _builder._contextPath +
						"/o/headless-delivery/v1.0/message-board-suspicious-activity/{suspiciousActivityId}/message-board-suspicious-activity");

			httpInvoker.path("suspiciousActivityId", suspiciousActivityId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<MessageBoardSuspiciousActivity>
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage(
					Long suspiciousActivityId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPageHttpResponse(
					suspiciousActivityId);

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
				return Page.of(
					content, MessageBoardSuspiciousActivitySerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPageHttpResponse(
					Long suspiciousActivityId)
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
					_builder._port + _builder._contextPath +
						"/o/headless-delivery/v1.0/message-board-suspicious-activity/{suspiciousActivityId}/message-board-suspicious-activity-update-validated");

			httpInvoker.path("suspiciousActivityId", suspiciousActivityId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<MessageBoardSuspiciousActivity>
				getMessageBoardThreadMessageBoardSuspiciousActivityPage(
					Long messageBoardThreadId, String search,
					List<String> aggregations, String filterString,
					Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getMessageBoardThreadMessageBoardSuspiciousActivityPageHttpResponse(
					messageBoardThreadId, search, aggregations, filterString,
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
				return Page.of(
					content, MessageBoardSuspiciousActivitySerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getMessageBoardThreadMessageBoardSuspiciousActivityPageHttpResponse(
					Long messageBoardThreadId, String search,
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
					_builder._port + _builder._contextPath +
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-suspicious-activity");

			httpInvoker.path("messageBoardThreadId", messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public MessageBoardSuspiciousActivity
				postMessageBoardThreadMessageBoardSuspiciousActivity(
					Long messageBoardThreadId,
					MessageBoardSuspiciousActivity
						messageBoardSuspiciousActivity)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postMessageBoardThreadMessageBoardSuspiciousActivityHttpResponse(
					messageBoardThreadId, messageBoardSuspiciousActivity);

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
				return MessageBoardSuspiciousActivitySerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postMessageBoardThreadMessageBoardSuspiciousActivityHttpResponse(
					Long messageBoardThreadId,
					MessageBoardSuspiciousActivity
						messageBoardSuspiciousActivity)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				messageBoardSuspiciousActivity.toString(), "application/json");

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
					_builder._port + _builder._contextPath +
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-suspicious-activity");

			httpInvoker.path("messageBoardThreadId", messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postMessageBoardThreadMessageBoardSuspiciousActivityBatch(
				Long messageBoardThreadId, String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postMessageBoardThreadMessageBoardSuspiciousActivityBatchHttpResponse(
					messageBoardThreadId, callbackURL, object);

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
				postMessageBoardThreadMessageBoardSuspiciousActivityBatchHttpResponse(
					Long messageBoardThreadId, String callbackURL,
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
					_builder._port + _builder._contextPath +
						"/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-suspicious-activity/batch");

			httpInvoker.path("messageBoardThreadId", messageBoardThreadId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private MessageBoardSuspiciousActivityResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			MessageBoardSuspiciousActivityResource.class.getName());

		private Builder _builder;

	}

}