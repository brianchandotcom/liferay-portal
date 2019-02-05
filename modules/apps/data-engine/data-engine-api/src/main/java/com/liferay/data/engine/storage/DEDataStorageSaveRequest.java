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

import com.liferay.data.engine.model.DEDataRecord;

/**
 * Request class used to save a data record in a data storage
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataStorageSaveRequest {

	/**
	 * Returns the data record that will be saved.
	 * @review
	 * @return the data record
	 */
	public DEDataRecord getDEDataRecord() {
		return _deDataRecord;
	}

	/**
	 * Returns the data storage id associated to that data record
	 * @review
	 * @return the data storage id
	 */
	public long getDEDataStorageId() {
		return _deDataStorageId;
	}

	/**
	 * Returns the group id responsible for the request
	 * @review
	 * @return The group id Long
	 */
	public long getGroupId() {
		return _groupId;
	}

	/**
	 * Returns the user id responsible for the request
	 * @review
	 * @return The user id Long
	 */
	public long getUserId() {
		return _userId;
	}

	/**
	 * Inner builder that assembles the request
	 * @review
	 */
	public static class Builder {

		/**
		 * Builder constructor that receives the deDataRecord as parameter.
		 * @param deDataRecord The {@link DEDataRecord} object
		 * @review
		 */
		public Builder(DEDataRecord deDataRecord) {
			_deDataStorageSaveRequest._deDataRecord = deDataRecord;
		}

		/**
		 * Builds the request and return the {@link DEDataStorageSaveRequest}
		 * object.
		 * @return the {@link DEDataStorageSaveRequest} object.
		 * @review
		 */
		public DEDataStorageSaveRequest build() {
			return _deDataStorageSaveRequest;
		}

		/**
		 * The group id responsible for the save operation.
		 * @review
		 * @param groupId
		 * @return the builder
		 */
		public Builder inGroup(long groupId) {
			_deDataStorageSaveRequest._groupId = groupId;

			return this;
		}

		/**
		 * The user responsible for the save operation
		 * @param userId
		 * @return the builder
		 * @review
		 */
		public Builder onBehalfOf(long userId) {
			_deDataStorageSaveRequest._userId = userId;

			return this;
		}

		/**
		 * The data storage id associated to that data record
		 * @param deDataStorageId
		 * @return the builder
		 * @review
		 */
		public Builder withDataStorage(long deDataStorageId) {
			_deDataStorageSaveRequest._deDataStorageId = deDataStorageId;

			return this;
		}

		private final DEDataStorageSaveRequest _deDataStorageSaveRequest =
			new DEDataStorageSaveRequest();

	}

	private DEDataStorageSaveRequest() {
	}

	private DEDataRecord _deDataRecord;
	private long _deDataStorageId;
	private long _groupId;
	private long _userId;

}