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
 * This class represents a request to get a {@link DEDataRecord} from the
 * database.
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordCollectionGetRecordRequest {

	/**
	 * Returns the Data Record ID of the Get request.
	 *
	 * @return deDataRecordCollectionId
	 * @review
	 */
	public long getDEDataRecordId() {
		return _deDataRecordId;
	}

	/**
	 * Constructs the Get Data Record request using the Data Record ID as
	 * an argument.
	 *
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Get Data Record request.
		 *
		 * @param deDataRecordId the primary key of the {@link DEDataRecord}
		 * instance
		 * @return {@link Builder}
		 * @review
		 */
		public Builder(long deDataRecordId) {
			_deDataRecordCollectionGetRecordRequest._deDataRecordId =
				deDataRecordId;
		}

		/**
		 * Constructs the Get Data Record request.
		 *
		 * @return {@link DEDataRecordCollectionGetRecordRequest}
		 * @review
		 */
		public DEDataRecordCollectionGetRecordRequest build() {
			return _deDataRecordCollectionGetRecordRequest;
		}

		private final DEDataRecordCollectionGetRecordRequest
			_deDataRecordCollectionGetRecordRequest =
				new DEDataRecordCollectionGetRecordRequest();

	}

	private DEDataRecordCollectionGetRecordRequest() {
	}

	private long _deDataRecordId;

}