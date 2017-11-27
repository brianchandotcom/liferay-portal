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

package com.liferay.vulcan.request;

import com.liferay.vulcan.language.Language;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.url.ServerURL;

import java.util.Optional;
import java.util.function.Function;

import javax.ws.rs.core.HttpHeaders;

/**
 * Instances of this class represent the information the server has about a
 * request.
 *
 * @author Alejandro Hernández
 * @review
 */
public class RequestInfo {

	/**
	 * This method can be used to create a new {@code RequestInfo} object,
	 * without creating the builder.
	 *
	 * @param  function the function that transforms a builder into a {@code
	 *         RequestInfo}
	 * @return the {@code RequestInfo} instance
	 */
	public static RequestInfo create(Function<Builder, RequestInfo> function) {
		return function.apply(new Builder());
	}

	/**
	 * Returns the information about embedded resources, if present. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @return the information about embedded resources, if present; {@code
	 *         Optional#empty()} otherwise
	 * @review
	 */
	public Optional<Embedded> getEmbeddedOptional() {
		return Optional.ofNullable(_embedded);
	}

	/**
	 * Returns the information about selected fields, if present. Returns {@code
	 * Optional#empty()} otherwise.
	 *
	 * @return the information about selected fields, if present; {@code
	 *         Optional#empty()} otherwise
	 * @review
	 */
	public Optional<Fields> getFieldsOptional() {
		return Optional.ofNullable(_fields);
	}

	/**
	 * Returns the HTTP headers.
	 *
	 * @return the HTTP headers.
	 * @review
	 */
	public HttpHeaders getHttpHeaders() {
		return _httpHeaders;
	}

	/**
	 * Returns the information about the language, if present. Returns {@code
	 * Optional#empty()} otherwise.
	 *
	 * @return the information about the language, if present; {@code
	 *         Optional#empty()} otherwise
	 * @review
	 */
	public Optional<Language> getLanguageOptional() {
		return Optional.ofNullable(_language);
	}

	/**
	 * Returns the server URL.
	 *
	 * @return the server URL.
	 * @review
	 */
	public ServerURL getServerURL() {
		return _serverURL;
	}

	/**
	 * Use instances of this builder to create {@link RequestInfo} instances.
	 *
	 * @review
	 */
	public static class Builder {

		/**
		 * Add information about the HTTP headers to the builder.
		 *
		 * @param  httpHeaders the request HTTP headers.
		 * @return the following step of the builder.
		 * @review
		 */
		public ServerURLStep httpHeaders(HttpHeaders httpHeaders) {
			_httpHeaders = httpHeaders;

			return new ServerURLStep();
		}

		public class OptionalStep {

			/**
			 * Constructs and returns a {@link RequestInfo} instance with the
			 * information provided to the builder.
			 *
			 * @return the {@code RequestInfo} instance
			 * @review
			 */
			public RequestInfo build() {
				return new RequestInfo(Builder.this);
			}

			/**
			 * Add information about embedded resources to the builder.
			 *
			 * @param  embedded the information about embedded resources.
			 * @return the next step of the builder.
			 * @review
			 */
			public OptionalStep embedded(Embedded embedded) {
				_embedded = embedded;

				return this;
			}

			/**
			 * Add information about selected fields to the builder.
			 *
			 * @param  fields the information about selected fields.
			 * @return the next step of the builder.
			 * @review
			 */
			public OptionalStep fields(Fields fields) {
				_fields = fields;

				return this;
			}

			/**
			 * Add information about the language to the builder.
			 *
			 * @param  language the request selected language.
			 * @return the next step of the builder.
			 * @review
			 */
			public OptionalStep language(Language language) {
				_language = language;

				return this;
			}

		}

		public class ServerURLStep {

			/**
			 * Add information about the server URL to the builder.
			 *
			 * @param  serverURL the server URL.
			 * @return the next step of the builder.
			 * @review
			 */
			public OptionalStep serverURL(ServerURL serverURL) {
				_serverURL = serverURL;

				return new OptionalStep();
			}

		}

		private Embedded _embedded;
		private Fields _fields;
		private HttpHeaders _httpHeaders;
		private Language _language;
		private ServerURL _serverURL;

	}

	private RequestInfo(Builder builder) {
		_language = builder._language;
		_fields = builder._fields;
		_httpHeaders = builder._httpHeaders;
		_serverURL = builder._serverURL;
		_embedded = builder._embedded;
	}

	private Embedded _embedded;
	private Fields _fields;
	private HttpHeaders _httpHeaders;
	private Language _language;
	private ServerURL _serverURL;

}