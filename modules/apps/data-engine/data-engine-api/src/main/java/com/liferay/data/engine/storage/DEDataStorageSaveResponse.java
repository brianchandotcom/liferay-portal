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
 * Response class used as a return value for the data storage save operation
 *
 * @author Leonardo Barros
 *
 * @review
 */
public final class DEDataStorageSaveResponse {

	/**
	 * Returns the id of the saved data storage.
	 * @review
	 * @return saved data storage id
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
			_deDataStorageSaveResponse._deDataStorageId = deDataStorageId;
		}

		/**
		 * Builds the response and returns the {@link DEDataStorageSaveResponse}
		 * object.
		 * @review
		 * @return The response object.
		 */
		public DEDataStorageSaveResponse build() {
			return _deDataStorageSaveResponse;
		}

		private final DEDataStorageSaveResponse _deDataStorageSaveResponse =
			new DEDataStorageSaveResponse();

	}

	private DEDataStorageSaveResponse() {
	}

	private long _deDataStorageId;

}