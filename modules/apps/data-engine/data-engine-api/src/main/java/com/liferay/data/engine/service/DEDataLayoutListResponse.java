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

import java.util.List;

/**
 * This class represents the response of the list {@link DEDataLayout}
 * request.
 *
 * @author Gabriel Albuquerque
 * @review
 */
public class DEDataLayoutListResponse {

	/**
	 * Returns the Data Layout list
	 *
	 * @return List of deDataLayout
	 * @review
	 */
	public List<DEDataLayout> getDEDataLayouts() {
		return _deDataLayouts;
	}

	/**
	 * Inner builder that assembles the response.
	 *
	 * @review
	 */
	public static final class Builder {

		/**
		 * Instantiate the builder providing a {@link DEDataLayout}
		 * list
		 *
		 * @param deDataLayout
		 * @return The builder
		 * @review
		 */
		public static Builder newBuilder(List<DEDataLayout> deDataLayout) {
			return new Builder(deDataLayout);
		}

		/**
		 * Builds a response directly from a {@link DEDataLayout}
		 * list.
		 *
		 * @param deDataLayout the list returned by the executor
		 * method
		 * @return The response object
		 * @review
		 */
		public static DEDataLayoutListResponse of(
			List<DEDataLayout> deDataLayout) {

			return newBuilder(
				deDataLayout
			).build();
		}

		/**
		 * Builds the response and returns the
		 * {@link DEDataLayoutListResponse} object.
		 *
		 * @return The response object.
		 * @review
		 */
		public DEDataLayoutListResponse build() {
			return _deDataLayoutListResponse;
		}

		private Builder(List<DEDataLayout> deDataLayouts) {
			_deDataLayoutListResponse._deDataLayouts = deDataLayouts;
		}

		private final DEDataLayoutListResponse _deDataLayoutListResponse =
			new DEDataLayoutListResponse();

	}

	private DEDataLayoutListResponse() {
	}

	private List<DEDataLayout> _deDataLayouts;

}