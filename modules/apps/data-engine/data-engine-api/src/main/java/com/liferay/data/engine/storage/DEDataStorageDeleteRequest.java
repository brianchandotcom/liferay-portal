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
 * Request class used to delete a data record on a data storage
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataStorageDeleteRequest {

	/**
	 * Returns the data storage id to be deleted.
	 * @review
	 * @return the data storage id
	 */
	public long getDEDataStorageId() {
		return _deDataStorageId;
	}

	/**
	 * Inner builder that assembles the request
	 * @review
	 */
	public static final class Builder {

		/**
		 * Builder constructor that receives the data storage id as parameter
		 * @param deDataStorageId The data storage id
		 * @review
		 */
		public Builder(long deDataStorageId) {
			_deDataDefinitionDeleteRequest._deDataStorageId = deDataStorageId;
		}

		/**
		 * Builds the request and return the {@link DEDataStorageDeleteRequest}
		 * object.
		 * @return the {@link DEDataStorageDeleteRequest} object.
		 * @review
		 */
		public DEDataStorageDeleteRequest build() {
			return _deDataDefinitionDeleteRequest;
		}

		private final DEDataStorageDeleteRequest
			_deDataDefinitionDeleteRequest = new DEDataStorageDeleteRequest();

	}

	private DEDataStorageDeleteRequest() {
	}

	private long _deDataStorageId;

}