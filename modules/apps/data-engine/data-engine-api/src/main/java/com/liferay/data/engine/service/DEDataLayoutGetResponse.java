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

import com.liferay.data.engine.model.DEDataLayout;

/**
 * Response class used to retrieve a {@link DEDataLayout}
 * @author Jeyvison Nascimento
 * @review
 */
public class DEDataLayoutGetResponse {

	/**
	 * Retriver the layout
	 * @return The {@link DEDataLayout} object
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
		 * Creates a new Builder receiving a {@link DEDataLayout} object.
		 * @param deDataLayout The {@link DEDataLayout} object
		 * @return The builder Object
		 * @review
		 */
		public static Builder newBuilder(DEDataLayout deDataLayout) {
			return new Builder(deDataLayout);
		}

		/**
		 * Creates a {@link DEDataLayoutGetResponse} object out of a
		 * {@link DEDataLayout}
		 * @param deDataLayout
		 * @return The response object.
		 * @review
		 */
		public static DEDataLayoutGetResponse of(DEDataLayout deDataLayout) {
			return newBuilder(
				deDataLayout
			).build();
		}

		/**
		 * Builder the response
		 * @return The {@link DEDataLayoutGetResponse} object
		 * @review
		 */
		public DEDataLayoutGetResponse build() {
			return _deDataLayoutGetResponse;
		}

		private Builder(DEDataLayout deDataLayout) {
			_deDataLayoutGetResponse._deDataLayout = deDataLayout;
		}

		private final DEDataLayoutGetResponse _deDataLayoutGetResponse =
			new DEDataLayoutGetResponse();

	}

	private DEDataLayoutGetResponse() {
	}

	private DEDataLayout _deDataLayout;

}