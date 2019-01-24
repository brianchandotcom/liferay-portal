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

package com.liferay.data.engine.service;

/**
 * Response class used as a return value for the save operation
 * @review
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutSaveResponse {

	/**
	 * Returns the id of the saved {@link com.liferay.data.engine.model.DEDataLayout}
	 * @review
	 * @return the id of the DEDataLayout
	 */
	public Long getDEDataLayoutId() {
		return _deDataLayoutId;
	}

	/**
	 * Inner builder that assembles the response.
	 * @review
	 */
	public static final class Builder {

		/**
		 * Instantiate the builder providing a deDataLayoutId
		 * @review
		 * @param deDataLayoutId
		 * @return The builder
		 */
		public static Builder newBuilder(long deDataLayoutId) {
			return new Builder(deDataLayoutId);
		}

		/**
		 * Builds a response directly from a
		 * {@link com.liferay.data.engine.model.DEDataLayout} id.
		 * @review
		 * @param deDataLayoutId the layout id
		 * @return The response object
		 */
		public static DEDataLayoutSaveResponse of(long deDataLayoutId) {
			return newBuilder(
				deDataLayoutId
			).build();
		}

		/**
		 * Builds the response and returns the {@link DEDataLayoutSaveResponse}
		 * object.
		 * @review
		 * @return The response object.
		 */
		public DEDataLayoutSaveResponse build() {
			return _deDataLayoutSaveResponse;
		}

		private Builder(long deDataLayoutId) {
			_deDataLayoutSaveResponse._deDataLayoutId = deDataLayoutId;
		}

		private final DEDataLayoutSaveResponse _deDataLayoutSaveResponse =
			new DEDataLayoutSaveResponse();

	}

	private DEDataLayoutSaveResponse() {
	}

	private Long _deDataLayoutId;

}