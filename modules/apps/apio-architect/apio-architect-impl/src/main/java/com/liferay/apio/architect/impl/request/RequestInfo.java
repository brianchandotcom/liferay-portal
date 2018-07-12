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

package com.liferay.apio.architect.impl.request;

import com.liferay.apio.architect.impl.response.control.Embedded;
import com.liferay.apio.architect.impl.response.control.Fields;
import com.liferay.apio.architect.impl.url.ApplicationURL;
import com.liferay.apio.architect.impl.url.ServerURL;
import com.liferay.apio.architect.language.AcceptLanguage;

import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

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
	 * Returns the information about the accept language
	 *
	 * @return the information about the accept language
	 */
	public AcceptLanguage getAcceptLanguage() {
		return _acceptLanguage;
	}

	/**
	 * Returns the application server URL.
	 *
	 * @return the application server URL
	 */
	public ApplicationURL getApplicationURL() {
		return _applicationURL;
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

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
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

		public class ApplicationURLStep {

			/**
			 * Add information about the application server URL to the builder.
			 *
			 * @param  applicationURL the application server URL
			 * @return the builder's next step
			 * @review
			 */
			public EmbeddedStep applicationURL(ApplicationURL applicationURL) {
				_applicationURL = applicationURL;

				return new EmbeddedStep();
			}

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

		public class LanguageStep {

			/**
			 * Adds information about the accept language to the builder.
			 *
			 * @param  acceptLanguage the request's selected accept language
			 * @return the builder's next step
			 */
			public BuildStep language(AcceptLanguage acceptLanguage) {
				_acceptLanguage = acceptLanguage;

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
			public ApplicationURLStep serverURL(ServerURL serverURL) {
				_serverURL = serverURL;

				return new ApplicationURLStep();
			}

		}

		private AcceptLanguage _acceptLanguage;
		private ApplicationURL _applicationURL;
		private Embedded _embedded;
		private Fields _fields;
		private HttpServletRequest _httpServletRequest;
		private ServerURL _serverURL;

	}

	private RequestInfo(Builder builder) {
		_acceptLanguage = builder._acceptLanguage;
		_fields = builder._fields;
		_serverURL = builder._serverURL;
		_applicationURL = builder._applicationURL;
		_embedded = builder._embedded;
		_httpServletRequest = builder._httpServletRequest;
	}

	private final AcceptLanguage _acceptLanguage;
	private final ApplicationURL _applicationURL;
	private final Embedded _embedded;
	private final Fields _fields;
	private final HttpServletRequest _httpServletRequest;
	private final ServerURL _serverURL;

}