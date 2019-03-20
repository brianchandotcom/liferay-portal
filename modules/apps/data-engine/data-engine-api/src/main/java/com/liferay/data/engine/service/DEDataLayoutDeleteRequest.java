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
 * Request class used to delete a
 * {@link com.liferay.data.engine.model.DEDataLayout}
 * @author Marcelo Mello
 * @review
 */
public final class DEDataLayoutDeleteRequest {

	/**
	 * Returns the layout id
	 * @return The Long layout id
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

		public DEDataLayoutDeleteRequest build() {
			return _deDataLayoutDeleteRequest;
		}

		/**
		 * The id of the layout
		 * @param deDataLayoutId The id of the
		 * {@link com.liferay.data.engine.model.DEDataLayout}
		 * @return The builder object
		 * @review
		 */
		public Builder byId(long deDataLayoutId) {
			_deDataLayoutDeleteRequest._deDataLayoutId = deDataLayoutId;

			return this;
		}

		private final DEDataLayoutDeleteRequest _deDataLayoutDeleteRequest =
			new DEDataLayoutDeleteRequest();

	}

	private DEDataLayoutDeleteRequest() {
	}

	private long _deDataLayoutId;

}