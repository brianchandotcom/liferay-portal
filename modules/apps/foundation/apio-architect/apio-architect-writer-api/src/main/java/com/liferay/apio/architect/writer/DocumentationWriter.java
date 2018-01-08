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

package com.liferay.apio.architect.writer;

import com.google.gson.JsonObject;

import com.liferay.apio.architect.alias.RequestFunction;
import com.liferay.apio.architect.documentation.Documentation;
import com.liferay.apio.architect.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.request.RequestInfo;

import java.util.Optional;
import java.util.function.Function;

/**
 * Writes the documentation.
 *
 * @author Alejandro Hernández
 */
public class DocumentationWriter {

	/**
	 * Creates a new {@code DocumentationWriter} object, without creating the
	 * builder.
	 *
	 * @param  function the function that transforms a builder into the {@code
	 *         DocumentationWriter}
	 * @return the {@code DocumentationWriter} instance
	 */
	public static DocumentationWriter create(
		Function<Builder, DocumentationWriter> function) {

		return function.apply(new Builder());
	}

	public DocumentationWriter(Builder builder) {
		_documentation = builder._documentation;
		_documentationMessageMapper = builder._documentationMessageMapper;
		_requestInfo = builder._requestInfo;
	}

	/**
	 * Writes the {@link Documentation} to a string.
	 *
	 * @return the JSON representation of the {@code Documentation}
	 */
	public String write() {
		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilder();

		RequestFunction<Optional<String>> apiTitleRequestFunction =
			_documentation.getAPITitleRequestFunction();

		apiTitleRequestFunction.apply(
			_requestInfo.getHttpServletRequest()
		).ifPresent(
			title -> _documentationMessageMapper.mapTitle(
				jsonObjectBuilder, title)
		);

		RequestFunction<Optional<String>> apiDescriptionRequestFunction =
			_documentation.getAPIDescriptionRequestFunction();

		apiDescriptionRequestFunction.apply(
			_requestInfo.getHttpServletRequest()
		).ifPresent(
			description -> _documentationMessageMapper.mapDescription(
				jsonObjectBuilder, description)
		);

		_documentationMessageMapper.onStart(
			jsonObjectBuilder, _documentation, _requestInfo.getHttpHeaders());

		_documentationMessageMapper.onFinish(
			jsonObjectBuilder, _documentation, _requestInfo.getHttpHeaders());

		JsonObject jsonObject = jsonObjectBuilder.build();

		return jsonObject.toString();
	}

	/**
	 * Creates {@code DocumentationWriter} instances.
	 */
	public static class Builder {

		/**
		 * Add information about the documentation being written to the builder.
		 *
		 * @param  documentation the documentation being written
		 * @return the updated builder
		 */
		public DocumentationMessageMapperStep documentation(
			Documentation documentation) {

			_documentation = documentation;

			return new DocumentationMessageMapperStep();
		}

		public class BuildStep {

			/**
			 * Constructs and returns a {@code DocumentationWriter} instance
			 * with the information provided to the builder.
			 *
			 * @return the {@code DocumentationWriter} instance
			 */
			public DocumentationWriter build() {
				return new DocumentationWriter(Builder.this);
			}

		}

		public class DocumentationMessageMapperStep {

			/**
			 * Adds information to the builder about the {@link
			 * DocumentationMessageMapper}.
			 *
			 * @param  documentationMessageMapper the {@code
			 *         DocumentationMessageMapper}
			 * @return the updated builder
			 */
			public RequestInfoStep documentationMessageMapper(
				DocumentationMessageMapper documentationMessageMapper) {

				_documentationMessageMapper = documentationMessageMapper;

				return new RequestInfoStep();
			}

		}

		public class RequestInfoStep {

			/**
			 * Adds information to the builder about the request.
			 *
			 * @param  requestInfo the information obtained from the request. It
			 *         can be created by using a {@link RequestInfo.Builder}.
			 * @return the updated builder
			 */
			public BuildStep requestInfo(RequestInfo requestInfo) {
				_requestInfo = requestInfo;

				return new BuildStep();
			}

		}

		private Documentation _documentation;
		private DocumentationMessageMapper _documentationMessageMapper;
		private RequestInfo _requestInfo;

	}

	private final Documentation _documentation;
	private final DocumentationMessageMapper _documentationMessageMapper;
	private final RequestInfo _requestInfo;

}