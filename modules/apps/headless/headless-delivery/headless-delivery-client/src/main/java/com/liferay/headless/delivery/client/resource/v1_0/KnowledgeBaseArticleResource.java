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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.problem.Problem;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseArticleSerDes;

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
public interface KnowledgeBaseArticleResource {

	public static Builder builder() {
		return new Builder();
	}

	public void deleteKnowledgeBaseArticle(Long knowledgeBaseArticleId)
		throws Exception;

	public HttpInvoker.HttpResponse deleteKnowledgeBaseArticleHttpResponse(
			Long knowledgeBaseArticleId)
		throws Exception;

	public void deleteKnowledgeBaseArticleBatch(
			String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse deleteKnowledgeBaseArticleBatchHttpResponse(
			String callbackURL, Object object)
		throws Exception;

	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			Long knowledgeBaseArticleId)
		throws Exception;

	public HttpInvoker.HttpResponse getKnowledgeBaseArticleHttpResponse(
			Long knowledgeBaseArticleId)
		throws Exception;

	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public HttpInvoker.HttpResponse patchKnowledgeBaseArticleHttpResponse(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public HttpInvoker.HttpResponse putKnowledgeBaseArticleHttpResponse(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public void putKnowledgeBaseArticleBatch(String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse putKnowledgeBaseArticleBatchHttpResponse(
			String callbackURL, Object object)
		throws Exception;

	public void deleteKnowledgeBaseArticleMyRating(Long knowledgeBaseArticleId)
		throws Exception;

	public HttpInvoker.HttpResponse
			deleteKnowledgeBaseArticleMyRatingHttpResponse(
				Long knowledgeBaseArticleId)
		throws Exception;

	public Rating getKnowledgeBaseArticleMyRating(Long knowledgeBaseArticleId)
		throws Exception;

	public HttpInvoker.HttpResponse getKnowledgeBaseArticleMyRatingHttpResponse(
			Long knowledgeBaseArticleId)
		throws Exception;

	public Rating postKnowledgeBaseArticleMyRating(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception;

	public HttpInvoker.HttpResponse
			postKnowledgeBaseArticleMyRatingHttpResponse(
				Long knowledgeBaseArticleId, Rating rating)
		throws Exception;

	public Rating putKnowledgeBaseArticleMyRating(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception;

	public HttpInvoker.HttpResponse putKnowledgeBaseArticleMyRatingHttpResponse(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception;

	public void putKnowledgeBaseArticleSubscribe(Long knowledgeBaseArticleId)
		throws Exception;

	public HttpInvoker.HttpResponse
			putKnowledgeBaseArticleSubscribeHttpResponse(
				Long knowledgeBaseArticleId)
		throws Exception;

	public void putKnowledgeBaseArticleUnsubscribe(Long knowledgeBaseArticleId)
		throws Exception;

	public HttpInvoker.HttpResponse
			putKnowledgeBaseArticleUnsubscribeHttpResponse(
				Long knowledgeBaseArticleId)
		throws Exception;

	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				Long parentKnowledgeBaseArticleId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getKnowledgeBaseArticleKnowledgeBaseArticlesPageHttpResponse(
				Long parentKnowledgeBaseArticleId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception;

	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			Long parentKnowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public HttpInvoker.HttpResponse
			postKnowledgeBaseArticleKnowledgeBaseArticleHttpResponse(
				Long parentKnowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				Long knowledgeBaseFolderId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getKnowledgeBaseFolderKnowledgeBaseArticlesPageHttpResponse(
				Long knowledgeBaseFolderId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception;

	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			Long knowledgeBaseFolderId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public HttpInvoker.HttpResponse
			postKnowledgeBaseFolderKnowledgeBaseArticleHttpResponse(
				Long knowledgeBaseFolderId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public void postKnowledgeBaseFolderKnowledgeBaseArticleBatch(
			Long knowledgeBaseFolderId, String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse
			postKnowledgeBaseFolderKnowledgeBaseArticleBatchHttpResponse(
				Long knowledgeBaseFolderId, String callbackURL, Object object)
		throws Exception;

	public Page<KnowledgeBaseArticle> getSiteKnowledgeBaseArticlesPage(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getSiteKnowledgeBaseArticlesPageHttpResponse(
				Long siteId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception;

	public KnowledgeBaseArticle postSiteKnowledgeBaseArticle(
			Long siteId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public HttpInvoker.HttpResponse postSiteKnowledgeBaseArticleHttpResponse(
			Long siteId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public void postSiteKnowledgeBaseArticleBatch(
			Long siteId, String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse
			postSiteKnowledgeBaseArticleBatchHttpResponse(
				Long siteId, String callbackURL, Object object)
		throws Exception;

	public void putSiteKnowledgeBaseArticleSubscribe(Long siteId)
		throws Exception;

	public HttpInvoker.HttpResponse
			putSiteKnowledgeBaseArticleSubscribeHttpResponse(Long siteId)
		throws Exception;

	public void putSiteKnowledgeBaseArticleUnsubscribe(Long siteId)
		throws Exception;

	public HttpInvoker.HttpResponse
			putSiteKnowledgeBaseArticleUnsubscribeHttpResponse(Long siteId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public KnowledgeBaseArticleResource build() {
			return new KnowledgeBaseArticleResourceImpl(this);
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
		private String _login = "";
		private String _password = "";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class KnowledgeBaseArticleResourceImpl
		implements KnowledgeBaseArticleResource {

		public void deleteKnowledgeBaseArticle(Long knowledgeBaseArticleId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteKnowledgeBaseArticleHttpResponse(knowledgeBaseArticleId);

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

		public HttpInvoker.HttpResponse deleteKnowledgeBaseArticleHttpResponse(
				Long knowledgeBaseArticleId)
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteKnowledgeBaseArticleBatch(
				String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteKnowledgeBaseArticleBatchHttpResponse(
					callbackURL, object);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				deleteKnowledgeBaseArticleBatchHttpResponse(
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/batch");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public KnowledgeBaseArticle getKnowledgeBaseArticle(
				Long knowledgeBaseArticleId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getKnowledgeBaseArticleHttpResponse(knowledgeBaseArticleId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return KnowledgeBaseArticleSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getKnowledgeBaseArticleHttpResponse(
				Long knowledgeBaseArticleId)
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public KnowledgeBaseArticle patchKnowledgeBaseArticle(
				Long knowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				patchKnowledgeBaseArticleHttpResponse(
					knowledgeBaseArticleId, knowledgeBaseArticle);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return KnowledgeBaseArticleSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse patchKnowledgeBaseArticleHttpResponse(
				Long knowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				knowledgeBaseArticle.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public KnowledgeBaseArticle putKnowledgeBaseArticle(
				Long knowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putKnowledgeBaseArticleHttpResponse(
					knowledgeBaseArticleId, knowledgeBaseArticle);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return KnowledgeBaseArticleSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse putKnowledgeBaseArticleHttpResponse(
				Long knowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				knowledgeBaseArticle.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putKnowledgeBaseArticleBatch(
				String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putKnowledgeBaseArticleBatchHttpResponse(callbackURL, object);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				putKnowledgeBaseArticleBatchHttpResponse(
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/batch");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteKnowledgeBaseArticleMyRating(
				Long knowledgeBaseArticleId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteKnowledgeBaseArticleMyRatingHttpResponse(
					knowledgeBaseArticleId);

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
				deleteKnowledgeBaseArticleMyRatingHttpResponse(
					Long knowledgeBaseArticleId)
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Rating getKnowledgeBaseArticleMyRating(
				Long knowledgeBaseArticleId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getKnowledgeBaseArticleMyRatingHttpResponse(
					knowledgeBaseArticleId);

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
				getKnowledgeBaseArticleMyRatingHttpResponse(
					Long knowledgeBaseArticleId)
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Rating postKnowledgeBaseArticleMyRating(
				Long knowledgeBaseArticleId, Rating rating)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postKnowledgeBaseArticleMyRatingHttpResponse(
					knowledgeBaseArticleId, rating);

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
				postKnowledgeBaseArticleMyRatingHttpResponse(
					Long knowledgeBaseArticleId, Rating rating)
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Rating putKnowledgeBaseArticleMyRating(
				Long knowledgeBaseArticleId, Rating rating)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putKnowledgeBaseArticleMyRatingHttpResponse(
					knowledgeBaseArticleId, rating);

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
				putKnowledgeBaseArticleMyRatingHttpResponse(
					Long knowledgeBaseArticleId, Rating rating)
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putKnowledgeBaseArticleSubscribe(
				Long knowledgeBaseArticleId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putKnowledgeBaseArticleSubscribeHttpResponse(
					knowledgeBaseArticleId);

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
				putKnowledgeBaseArticleSubscribeHttpResponse(
					Long knowledgeBaseArticleId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				knowledgeBaseArticleId.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/subscribe",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putKnowledgeBaseArticleUnsubscribe(
				Long knowledgeBaseArticleId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putKnowledgeBaseArticleUnsubscribeHttpResponse(
					knowledgeBaseArticleId);

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
				putKnowledgeBaseArticleUnsubscribeHttpResponse(
					Long knowledgeBaseArticleId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				knowledgeBaseArticleId.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/unsubscribe",
				knowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<KnowledgeBaseArticle>
				getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					Long parentKnowledgeBaseArticleId, String search,
					String filterString, Pagination pagination,
					String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getKnowledgeBaseArticleKnowledgeBaseArticlesPageHttpResponse(
					parentKnowledgeBaseArticleId, search, filterString,
					pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, KnowledgeBaseArticleSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getKnowledgeBaseArticleKnowledgeBaseArticlesPageHttpResponse(
					Long parentKnowledgeBaseArticleId, String search,
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles",
				parentKnowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public KnowledgeBaseArticle
				postKnowledgeBaseArticleKnowledgeBaseArticle(
					Long parentKnowledgeBaseArticleId,
					KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postKnowledgeBaseArticleKnowledgeBaseArticleHttpResponse(
					parentKnowledgeBaseArticleId, knowledgeBaseArticle);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return KnowledgeBaseArticleSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postKnowledgeBaseArticleKnowledgeBaseArticleHttpResponse(
					Long parentKnowledgeBaseArticleId,
					KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				knowledgeBaseArticle.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles",
				parentKnowledgeBaseArticleId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<KnowledgeBaseArticle>
				getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					Long knowledgeBaseFolderId, Boolean flatten, String search,
					String filterString, Pagination pagination,
					String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getKnowledgeBaseFolderKnowledgeBaseArticlesPageHttpResponse(
					knowledgeBaseFolderId, flatten, search, filterString,
					pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, KnowledgeBaseArticleSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getKnowledgeBaseFolderKnowledgeBaseArticlesPageHttpResponse(
					Long knowledgeBaseFolderId, Boolean flatten, String search,
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
						"/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles",
				knowledgeBaseFolderId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
				Long knowledgeBaseFolderId,
				KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postKnowledgeBaseFolderKnowledgeBaseArticleHttpResponse(
					knowledgeBaseFolderId, knowledgeBaseArticle);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return KnowledgeBaseArticleSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postKnowledgeBaseFolderKnowledgeBaseArticleHttpResponse(
					Long knowledgeBaseFolderId,
					KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				knowledgeBaseArticle.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles",
				knowledgeBaseFolderId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postKnowledgeBaseFolderKnowledgeBaseArticleBatch(
				Long knowledgeBaseFolderId, String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postKnowledgeBaseFolderKnowledgeBaseArticleBatchHttpResponse(
					knowledgeBaseFolderId, callbackURL, object);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				postKnowledgeBaseFolderKnowledgeBaseArticleBatchHttpResponse(
					Long knowledgeBaseFolderId, String callbackURL,
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
						"/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles/batch",
				knowledgeBaseFolderId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<KnowledgeBaseArticle> getSiteKnowledgeBaseArticlesPage(
				Long siteId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getSiteKnowledgeBaseArticlesPageHttpResponse(
					siteId, flatten, search, filterString, pagination,
					sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, KnowledgeBaseArticleSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getSiteKnowledgeBaseArticlesPageHttpResponse(
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
						"/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public KnowledgeBaseArticle postSiteKnowledgeBaseArticle(
				Long siteId, KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postSiteKnowledgeBaseArticleHttpResponse(
					siteId, knowledgeBaseArticle);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return KnowledgeBaseArticleSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postSiteKnowledgeBaseArticleHttpResponse(
					Long siteId, KnowledgeBaseArticle knowledgeBaseArticle)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				knowledgeBaseArticle.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postSiteKnowledgeBaseArticleBatch(
				Long siteId, String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postSiteKnowledgeBaseArticleBatchHttpResponse(
					siteId, callbackURL, object);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				postSiteKnowledgeBaseArticleBatchHttpResponse(
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
						"/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/batch",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putSiteKnowledgeBaseArticleSubscribe(Long siteId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putSiteKnowledgeBaseArticleSubscribeHttpResponse(siteId);

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
				putSiteKnowledgeBaseArticleSubscribeHttpResponse(Long siteId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(siteId.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/subscribe",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void putSiteKnowledgeBaseArticleUnsubscribe(Long siteId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putSiteKnowledgeBaseArticleUnsubscribeHttpResponse(siteId);

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
				putSiteKnowledgeBaseArticleUnsubscribeHttpResponse(Long siteId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(siteId.toString(), "application/json");

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
						"/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/unsubscribe",
				siteId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private KnowledgeBaseArticleResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			KnowledgeBaseArticleResource.class.getName());

		private Builder _builder;

	}

}