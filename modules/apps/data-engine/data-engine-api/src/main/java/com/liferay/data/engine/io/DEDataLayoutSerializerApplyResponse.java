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

package com.liferay.data.engine.io;

/**
 * Serialize {@link com.liferay.data.engine.model.DEDataLayout} response class.
 * @author Jeyvison Nascimento
 * @review
 */
public class DEDataLayoutSerializerApplyResponse {

	/**
	 * Return the serialized {@link com.liferay.data.engine.model.DEDataLayout}
	 * string content.
	 * @return The serialized layout.
	 * @review
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * Inner builder that assembles the response
	 * @review
	 */
	public static final class Builder {

		/**
		 * Creates a new builder receiving the serialized content.
		 * @param content The serialized content String
		 * @return the builder object
		 * @review
		 */
		public static DEDataLayoutSerializerApplyResponse.Builder newBuilder(
			String content) {

			return new DEDataLayoutSerializerApplyResponse.Builder(content);
		}

		/**
		 * Creates a new response out of a serialized string.
		 * @param content The {@link com.liferay.data.engine.model.DEDataLayout}
		 * serialized string.
		 * @return the {@link DEDataLayoutSerializerApplyResponse} object
		 * @review
		 */
		public static DEDataLayoutSerializerApplyResponse of(String content) {
			return newBuilder(
				content
			).build();
		}

		/**
		 * Builds the response object
		 * @return the {@link DEDataLayoutSerializerApplyResponse} object.
		 * @review
		 */
		public DEDataLayoutSerializerApplyResponse build() {
			return _deDataLayoutSerializerApplyResponse;
		}

		private Builder(String content) {
			_deDataLayoutSerializerApplyResponse._content = content;
		}

		private final DEDataLayoutSerializerApplyResponse
			_deDataLayoutSerializerApplyResponse =
				new DEDataLayoutSerializerApplyResponse();

	}

	private DEDataLayoutSerializerApplyResponse() {
	}

	private String _content;

}