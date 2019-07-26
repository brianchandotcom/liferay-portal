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

import com.liferay.headless.delivery.client.dto.v1_0.WikiPageAttachment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.WikiPageAttachmentSerDes;

import java.io.File;

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
public interface WikiPageAttachmentResource {

	public static Builder builder() {
		return new Builder();
	}

	public void deleteWikiPageAttachment(Long wikiPageAttachmentId)
		throws Exception;

	public HttpInvoker.HttpResponse deleteWikiPageAttachmentHttpResponse(
			Long wikiPageAttachmentId)
		throws Exception;

	public WikiPageAttachment getWikiPageAttachment(Long wikiPageAttachmentId)
		throws Exception;

	public HttpInvoker.HttpResponse getWikiPageAttachmentHttpResponse(
			Long wikiPageAttachmentId)
		throws Exception;

	public Page<WikiPageAttachment> getWikiPageWikiPageAttachmentsPage(
			Long wikiPageId)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWikiPageWikiPageAttachmentsPageHttpResponse(Long wikiPageId)
		throws Exception;

	public WikiPageAttachment postWikiPageWikiPageAttachment(
			Long wikiPageId, WikiPageAttachment wikiPageAttachment,
			Map<String, File> multipartFiles)
		throws Exception;

	public HttpInvoker.HttpResponse postWikiPageWikiPageAttachmentHttpResponse(
			Long wikiPageId, WikiPageAttachment wikiPageAttachment,
			Map<String, File> multipartFiles)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public WikiPageAttachmentResource build() {
			return new WikiPageAttachmentResourceImpl(this);
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

	public static class WikiPageAttachmentResourceImpl
		implements WikiPageAttachmentResource {

		public void deleteWikiPageAttachment(Long wikiPageAttachmentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteWikiPageAttachmentHttpResponse(wikiPageAttachmentId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse deleteWikiPageAttachmentHttpResponse(
				Long wikiPageAttachmentId)
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
						"/o/headless-delivery/v1.0/wiki-page-attachments/{wikiPageAttachmentId}",
				wikiPageAttachmentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WikiPageAttachment getWikiPageAttachment(
				Long wikiPageAttachmentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWikiPageAttachmentHttpResponse(wikiPageAttachmentId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WikiPageAttachmentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getWikiPageAttachmentHttpResponse(
				Long wikiPageAttachmentId)
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
						"/o/headless-delivery/v1.0/wiki-page-attachments/{wikiPageAttachmentId}",
				wikiPageAttachmentId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WikiPageAttachment> getWikiPageWikiPageAttachmentsPage(
				Long wikiPageId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWikiPageWikiPageAttachmentsPageHttpResponse(wikiPageId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, WikiPageAttachmentSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getWikiPageWikiPageAttachmentsPageHttpResponse(Long wikiPageId)
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
						"/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments",
				wikiPageId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WikiPageAttachment postWikiPageWikiPageAttachment(
				Long wikiPageId, WikiPageAttachment wikiPageAttachment,
				Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postWikiPageWikiPageAttachmentHttpResponse(
					wikiPageId, wikiPageAttachment, multipartFiles);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WikiPageAttachmentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postWikiPageWikiPageAttachmentHttpResponse(
					Long wikiPageId, WikiPageAttachment wikiPageAttachment,
					Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.multipart();

			httpInvoker.part(
				"wikiPageAttachment",
				WikiPageAttachmentSerDes.toJSON(wikiPageAttachment));

			for (Map.Entry<String, File> entry : multipartFiles.entrySet()) {
				httpInvoker.part(entry.getKey(), entry.getValue());
			}

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
						"/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments",
				wikiPageId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private WikiPageAttachmentResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			WikiPageAttachmentResource.class.getName());

		private Builder _builder;

	}

}