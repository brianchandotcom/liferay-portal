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

import com.liferay.data.engine.model.DEDataDefinition;

/**
 * Request class used to retrieve a data record on a data storage
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataStorageGetRequest {

	/**
	 * Returns the {@link DEDataDefinition} associated to that data storage
	 * @review
	 * @return the {@link DEDataDefinition}
	 */
	public DEDataDefinition getDEDataDefinition() {
		return _deDataDefinition;
	}

	/**
	 * Returns the id from the data storage to be retrieved
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
	public static class Builder {

		/**
		 * Builder constructor that receives the data storage id and a data definition as parameters
		 * @param deDataStorageId The id from the data storage to be retrieved
		 * @param deDataDefinition The {@link DEDataDefinition} associated to that data storage
		 * @review
		 */
		public Builder(
			long deDataStorageId, DEDataDefinition deDataDefinition) {

			_deDataStorageGetRequest._deDataStorageId = deDataStorageId;
			_deDataStorageGetRequest._deDataDefinition = deDataDefinition;
		}

		/**
		 * Builds the request and return the {@link DEDataStorageGetRequest}
		 * object.
		 * @return the {@link DEDataStorageGetRequest} object.
		 * @review
		 */
		public DEDataStorageGetRequest build() {
			return _deDataStorageGetRequest;
		}

		private final DEDataStorageGetRequest _deDataStorageGetRequest =
			new DEDataStorageGetRequest();

	}

	private DEDataStorageGetRequest() {
	}

	private DEDataDefinition _deDataDefinition;
	private long _deDataStorageId;

}