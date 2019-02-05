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

package com.liferay.data.engine.storage;

/**
 * Response class used as a return value for the data storage delete operation
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataStorageDeleteResponse {

	/**
	 * Returns the id of the deleted data storage.
	 * @review
	 * @return deleted data storage id
	 */
	public long getDEDataStorageId() {
		return _deDataStorageId;
	}

	/**
	 * Inner builder that assembles the response.
	 * @review
	 */
	public static final class Builder {

		/**
		 * Builder constructor that receives the data storage id as parameter.
		 * @param deDataStorageId The data storage id
		 * @review
		 */
		public Builder(long deDataStorageId) {
			_deDataStorageDeleteResponse._deDataStorageId = deDataStorageId;
		}

		/**
		 * Builds the response and returns the {@link DEDataStorageDeleteResponse}
		 * object.
		 * @review
		 * @return The response object.
		 */
		public DEDataStorageDeleteResponse build() {
			return _deDataStorageDeleteResponse;
		}

		private final DEDataStorageDeleteResponse _deDataStorageDeleteResponse =
			new DEDataStorageDeleteResponse();

	}

	private DEDataStorageDeleteResponse() {
	}

	private long _deDataStorageId;

}