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
 * Request class used to retrieve a
 * {@link com.liferay.data.engine.model.DEDataLayout}
 * @author Jeyvison Nascimento
 * @review
 */
public class DEDataLayoutGetRequest {

	/**
	 * Returns the layout id
	 * @return The Long layout id
	 * @review
	 */
	public Long getDEDataLayoutId() {
		return _deDataLayoutId;
	}

	/**
	 * Inner Builder
	 * @review
	 */
	public static final class Builder {

		public DEDataLayoutGetRequest build() {
			return _deDataLayoutGetRequest;
		}

		/**
		 * The id of the layout
		 * @param deDataLayoutId The id of the
		 * {@link com.liferay.data.engine.model.DEDataLayout}
		 * @return The builder object
		 * @review
		 */
		public Builder byId(long deDataLayoutId) {
			_deDataLayoutGetRequest._deDataLayoutId = deDataLayoutId;

			return this;
		}

		private final DEDataLayoutGetRequest _deDataLayoutGetRequest =
			new DEDataLayoutGetRequest();

	}

	private DEDataLayoutGetRequest() {
	}

	private Long _deDataLayoutId;

}