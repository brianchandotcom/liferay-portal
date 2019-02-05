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

import com.liferay.data.engine.model.DEDataRecord;

/**
 * Request class used to save a data record
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordCollectionSaveRecordRequest {

	/**
	 * Returns the data record that will be saved.
	 * @review
	 * @return the data record
	 */
	public DEDataRecord getDEDataRecord() {
		return _deDataRecord;
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
	public static final class Builder {

		/**
		 * Builder constructor that receives the deDataRecord as parameter.
		 * @param deDataRecord The {@link DEDataRecord} object
		 * @review
		 */
		public Builder(DEDataRecord deDataRecord) {
			_deDataRecordCollectionSaveRecordRequest._deDataRecord =
				deDataRecord;
		}

		/**
		 * Builds the request and return the {@link DEDataRecordCollectionSaveRecordRequest}
		 * object.
		 * @return the {@link DEDataRecordCollectionSaveRecordRequest} object.
		 * @review
		 */
		public DEDataRecordCollectionSaveRecordRequest build() {
			return _deDataRecordCollectionSaveRecordRequest;
		}

		/**
		 * The group id responsible for the save operation.
		 * @review
		 * @param groupId
		 * @return the builder
		 */
		public Builder inGroup(long groupId) {
			_deDataRecordCollectionSaveRecordRequest._groupId = groupId;

			return this;
		}

		/**
		 * The user responsible for the save operation
		 * @param userId
		 * @return the builder
		 * @review
		 */
		public Builder onBehalfOf(long userId) {
			_deDataRecordCollectionSaveRecordRequest._userId = userId;

			return this;
		}

		private final DEDataRecordCollectionSaveRecordRequest
			_deDataRecordCollectionSaveRecordRequest =
				new DEDataRecordCollectionSaveRecordRequest();

	}

	private DEDataRecordCollectionSaveRecordRequest() {
	}

	private DEDataRecord _deDataRecord;
	private long _groupId;
	private long _userId;

}