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
 * {@link com.liferay.data.engine.model.DEDataLayout} deserializer request
 * @author Jeyvison Nascimento
 * @review
 */
public class DEDataLayoutDeserializerApplyRequest {

	/**
	 * The serialized {@link com.liferay.data.engine.model.DEDataLayout}.
	 * @return The serialized content
	 * @review
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * Inner Builder
	 * @review
	 */
	public static final class Builder {

		/**
		 * Creates a new Builder with the serialized content.
		 * @param content The serialized {@link com.liferay.data.engine.model.DEDataLayout}
		 * @return the builder
		 * @review
		 */
		public static Builder newBuilder(String content) {
			return new Builder(content);
		}

		/**
		 * Creates a {@link DEDataLayoutDeserializerApplyRequest} object out of
		 * a serialized content.
		 * @param content the serialized content
		 * @return The request object
		 * @review
		 */
		public static DEDataLayoutDeserializerApplyRequest of(String content) {
			return newBuilder(
				content
			).build();
		}

		/**
		 * Creates the builder.
		 * @return the builder object.
		 * @review
		 */
		public DEDataLayoutDeserializerApplyRequest build() {
			return _deDataLayoutDeserializerApplyRequest;
		}

		private Builder(String content) {
			_deDataLayoutDeserializerApplyRequest._content = content;
		}

		private DEDataLayoutDeserializerApplyRequest
			_deDataLayoutDeserializerApplyRequest =
				new DEDataLayoutDeserializerApplyRequest();

	}

	private DEDataLayoutDeserializerApplyRequest() {
	}

	private String _content;

}