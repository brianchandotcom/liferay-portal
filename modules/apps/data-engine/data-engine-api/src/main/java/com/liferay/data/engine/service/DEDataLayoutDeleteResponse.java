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
 * Response class used to retrieve a {@link DEDataLayout}
 * @author Marcelo Mello
 * @review
 */
public final class DEDataLayoutDeleteResponse {

	/**
	 * Retriver the layout
	 * @return The {@link DEDataLayout} object
	 * @review
	 */
	public long getDEDataLayoutId() {
		return _deDataLayoutId;
	}

	/**
	 * Inner Builder
	 * @review
	 */
	public static final class Builder {

		/**
		 * Creates a new Builder receiving a {@link DEDataLayout} object.
		 * @param deDataLayoutId The {@link DEDataLayout} object
		 * @return The builder Object
		 * @review
		 */
		public static Builder newBuilder(long deDataLayoutId) {
			return new Builder(deDataLayoutId);
		}

		/**
		 * Creates a {@link DEDataLayoutDeleteResponse} object out of a
		 * {@link DEDataLayout}
		 * @param deDataLayoutId
		 * @return The response object.
		 * @review
		 */
		public static DEDataLayoutDeleteResponse of(long deDataLayoutId) {
			return newBuilder(
				deDataLayoutId
			).build();
		}

		/**
		 * Builder the response
		 * @return The {@link DEDataLayoutDeleteResponse} object
		 * @review
		 */
		public DEDataLayoutDeleteResponse build() {
			return _deDataLayoutDeleteResponse;
		}

		private Builder(long deDataLayoutId) {
			_deDataLayoutDeleteResponse._deDataLayoutId = deDataLayoutId;
		}

		private final DEDataLayoutDeleteResponse _deDataLayoutDeleteResponse =
			new DEDataLayoutDeleteResponse();

	}

	private DEDataLayoutDeleteResponse() {
	}

	private long _deDataLayoutId;

}