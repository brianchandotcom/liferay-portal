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

import com.liferay.data.engine.model.DEDataLayout;

/**
 * {@link DEDataLayout} deserializer response
 * @author Jeyvison Nascimento
 * @review
 */
public class DEDataLayoutDeserializerApplyResponse {

	/**
	 * Returns the deserialized {@link DEDataLayout}
	 * @return the DEDataLayout object.
	 * @review
	 */
	public DEDataLayout getDEDataLayout() {
		return _deDataLayout;
	}

	/**
	 * Inner Builder
	 * @review
	 */
	public static final class Builder {

		/**
		 * Creates a new Builder with the deserialized content.
		 * @param deDataLayout The deserialized
		 * {@link DEDataLayout} object.
		 * @return the builder
		 * @review
		 */
		public static Builder newBuilder(DEDataLayout deDataLayout) {
			return new Builder(deDataLayout);
		}

		/**
		 * Creates a {@link DEDataLayoutDeserializerApplyResponse} object
		 * out of a {@link DEDataLayout} object.
		 * @param deDataLayout The {@link DEDataLayout} object.
		 * @return the response o object.
		 * @review
		 */
		public static DEDataLayoutDeserializerApplyResponse of(
			DEDataLayout deDataLayout) {

			return newBuilder(
				deDataLayout
			).build();
		}

		/**
		 * Creates the builder.
		 * @return The builder Object
		 * @review
		 */
		public DEDataLayoutDeserializerApplyResponse build() {
			return _deDataLayoutDeserializerApplyResponse;
		}

		private Builder(DEDataLayout deDataLayout) {
			_deDataLayoutDeserializerApplyResponse._deDataLayout = deDataLayout;
		}

		private final DEDataLayoutDeserializerApplyResponse
			_deDataLayoutDeserializerApplyResponse =
				new DEDataLayoutDeserializerApplyResponse();

	}

	private DEDataLayoutDeserializerApplyResponse() {
	}

	private DEDataLayout _deDataLayout;

}