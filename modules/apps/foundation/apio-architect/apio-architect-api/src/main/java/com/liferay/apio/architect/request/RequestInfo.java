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

package com.liferay.apio.architect.request;

import com.liferay.apio.architect.language.Language;
import com.liferay.apio.architect.response.control.Embedded;
import com.liferay.apio.architect.response.control.Fields;
import com.liferay.apio.architect.url.ServerURL;

import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.HttpHeaders;

/**
 * Represents the information the server has about a request.
 *
 * @author Alejandro Hernández
 */
public class RequestInfo {

	/**
	 * Creates a new {@code RequestInfo} object without creating the builder.
	 *
	 * @param  function the function that transforms a builder into a {@code
	 *         RequestInfo}
	 * @return the new {@code RequestInfo} instance
	 */
	public static RequestInfo create(Function<Builder, RequestInfo> function) {
		return function.apply(new Builder());
	}

	/**
	 * Returns the information about embedded resources.
	 *
	 * @return the information about embedded resources
	 */
	public Embedded getEmbedded() {
		return _embedded;
	}

	/**
	 * Returns the information about selected fields.
	 *
	 * @return the information about selected fields
	 */
	public Fields getFields() {
		return _fields;
	}

	/**
	 * Returns the HTTP headers.
	 *
	 * @return the HTTP headers
	 */
	public HttpHeaders getHttpHeaders() {
		return _httpHeaders;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	/**
	 * Returns the information about the language
	 *
	 * @return the information about the language
	 */
	public Language getLanguage() {
		return _language;
	}

	/**
	 * Returns the server URL.
	 *
	 * @return the server URL
	 */
	public ServerURL getServerURL() {
		return _serverURL;
	}

	/**
	 * Creates {@link RequestInfo} instances.
	 */
	public static class Builder {

		/**
		 * Adds information about the HTTP headers to the builder.
		 *
		 * @param  httpHeaders the request's HTTP headers
		 * @return the builder's following step
		 */
		public HttpServletRequestStep httpHeaders(HttpHeaders httpHeaders) {
			_httpHeaders = httpHeaders;

			return new HttpServletRequestStep();
		}

		public class BuildStep {

			/**
			 * Constructs and returns a {@link RequestInfo} instance with the
			 * information provided to the builder.
			 *
			 * @return the {@code RequestInfo} instance
			 */
			public RequestInfo build() {
				return new RequestInfo(Builder.this);
			}

		}

		public class EmbeddedStep {

			/**
			 * Adds information about embedded resources to the builder.
			 *
			 * @param  embedded the information about embedded resources
			 * @return the builder's next step
			 */
			public FieldsStep embedded(Embedded embedded) {
				_embedded = embedded;

				return new FieldsStep();
			}

		}

		public class FieldsStep {

			/**
			 * Adds information about selected fields to the builder.
			 *
			 * @param  fields the information about selected fields
			 * @return the builder's next step
			 */
			public LanguageStep fields(Fields fields) {
				_fields = fields;

				return new LanguageStep();
			}

		}

		public class HttpServletRequestStep {

			/**
			 * Adds information about the HTTP request to the builder.
			 *
			 * @param  httpServletRequest the HTTP request
			 * @return the builder's following step
			 */
			public ServerURLStep httpServletRequest(
				HttpServletRequest httpServletRequest) {

				_httpServletRequest = httpServletRequest;

				return new ServerURLStep();
			}

		}

		public class LanguageStep {

			/**
			 * Adds information about the language to the builder.
			 *
			 * @param  language the request's selected language
			 * @return the builder's next step
			 */
			public BuildStep language(Language language) {
				_language = language;

				return new BuildStep();
			}

		}

		public class ServerURLStep {

			/**
			 * Add information about the server URL to the builder.
			 *
			 * @param  serverURL the server URL
			 * @return the builder's next step
			 */
			public EmbeddedStep serverURL(ServerURL serverURL) {
				_serverURL = serverURL;

				return new EmbeddedStep();
			}

		}

		private Embedded _embedded;
		private Fields _fields;
		private HttpHeaders _httpHeaders;
		private HttpServletRequest _httpServletRequest;
		private Language _language;
		private ServerURL _serverURL;

	}

	private RequestInfo(Builder builder) {
		_language = builder._language;
		_fields = builder._fields;
		_httpHeaders = builder._httpHeaders;
		_serverURL = builder._serverURL;
		_embedded = builder._embedded;
		_httpServletRequest = builder._httpServletRequest;
	}

	private final Embedded _embedded;
	private final Fields _fields;
	private final HttpHeaders _httpHeaders;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final ServerURL _serverURL;

}