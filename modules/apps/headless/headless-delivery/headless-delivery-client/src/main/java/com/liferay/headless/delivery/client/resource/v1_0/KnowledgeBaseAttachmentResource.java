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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseAttachmentSerDes;

import java.io.File;

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
public interface KnowledgeBaseAttachmentResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<KnowledgeBaseAttachment>
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
				Long knowledgeBaseArticleId)
		throws Exception;

	public HttpInvoker.HttpResponse
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPageHttpResponse(
				Long knowledgeBaseArticleId)
		throws Exception;

	public KnowledgeBaseAttachment
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				Long knowledgeBaseArticleId,
				KnowledgeBaseAttachment knowledgeBaseAttachment,
				Map<String, File> multipartFiles)
		throws Exception;

	public HttpInvoker.HttpResponse
			postKnowledgeBaseArticleKnowledgeBaseAttachmentHttpResponse(
				Long knowledgeBaseArticleId,
				KnowledgeBaseAttachment knowledgeBaseAttachment,
				Map<String, File> multipartFiles)
		throws Exception;

	public void deleteKnowledgeBaseAttachment(Long knowledgeBaseAttachmentId)
		throws Exception;

	public HttpInvoker.HttpResponse deleteKnowledgeBaseAttachmentHttpResponse(
			Long knowledgeBaseAttachmentId)
		throws Exception;

	public KnowledgeBaseAttachment getKnowledgeBaseAttachment(
			Long knowledgeBaseAttachmentId)
		throws Exception;

	public HttpInvoker.HttpResponse getKnowledgeBaseAttachmentHttpResponse(
			Long knowledgeBaseAttachmentId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public KnowledgeBaseAttachmentResource build() {
			return new KnowledgeBaseAttachmentResourceImpl(this);
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

	public static class KnowledgeBaseAttachmentResourceImpl
		implements KnowledgeBaseAttachmentResource {

		public Page<KnowledgeBaseAttachment>
				getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
					Long knowledgeBaseArticleId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getKnowledgeBaseArticleKnowledgeBaseAttachmentsPageHttpResponse(
					knowledgeBaseArticleId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, KnowledgeBaseAttachmentSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getKnowledgeBaseArticleKnowledgeBaseAttachmentsPageHttpResponse(
					Long knowledgeBaseArticleId)
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
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments",
				knowledgeBaseArticleId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public KnowledgeBaseAttachment
				postKnowledgeBaseArticleKnowledgeBaseAttachment(
					Long knowledgeBaseArticleId,
					KnowledgeBaseAttachment knowledgeBaseAttachment,
					Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postKnowledgeBaseArticleKnowledgeBaseAttachmentHttpResponse(
					knowledgeBaseArticleId, knowledgeBaseAttachment,
					multipartFiles);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return KnowledgeBaseAttachmentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postKnowledgeBaseArticleKnowledgeBaseAttachmentHttpResponse(
					Long knowledgeBaseArticleId,
					KnowledgeBaseAttachment knowledgeBaseAttachment,
					Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.multipart();

			httpInvoker.part(
				"knowledgeBaseAttachment",
				KnowledgeBaseAttachmentSerDes.toJSON(knowledgeBaseAttachment));

			for (Map.Entry<String, File> entry : multipartFiles.entrySet()) {
				httpInvoker.part(entry.getKey(), entry.getValue());
			}

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments",
				knowledgeBaseArticleId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteKnowledgeBaseAttachment(
				Long knowledgeBaseAttachmentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteKnowledgeBaseAttachmentHttpResponse(
					knowledgeBaseAttachmentId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				deleteKnowledgeBaseAttachmentHttpResponse(
					Long knowledgeBaseAttachmentId)
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
						"/o/headless-delivery/v1.0/knowledge-base-attachments/{knowledgeBaseAttachmentId}",
				knowledgeBaseAttachmentId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public KnowledgeBaseAttachment getKnowledgeBaseAttachment(
				Long knowledgeBaseAttachmentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getKnowledgeBaseAttachmentHttpResponse(
					knowledgeBaseAttachmentId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return KnowledgeBaseAttachmentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getKnowledgeBaseAttachmentHttpResponse(
				Long knowledgeBaseAttachmentId)
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
						"/o/headless-delivery/v1.0/knowledge-base-attachments/{knowledgeBaseAttachmentId}",
				knowledgeBaseAttachmentId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private KnowledgeBaseAttachmentResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			KnowledgeBaseAttachmentResource.class.getName());

		private Builder _builder;

	}

}