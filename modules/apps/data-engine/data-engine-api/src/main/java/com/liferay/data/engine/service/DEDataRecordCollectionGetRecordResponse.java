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
 * This class represents a response of the get {@link DEDataRecord} response.
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordCollectionGetRecordResponse {

	/**
	 * Returns the Data Record ID of the Get response.
	 *
	 * @return deDataRecordId
	 * @review
	 */
	public DEDataRecord getDEDataRecord() {
		return _deDataRecord;
	}

	/**
	 * Constructs the Get Data Record response using the Data Record ID as
	 * an argument.
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the Data Record Collection builder
		 *
		 * @param deDataRecordId the primary key of the {@link DEDataRecord}
		 * instance
		 * @return {@link Builder}
		 * @review
		 */
		public static Builder newBuilder(DEDataRecord deDataRecord) {
			return new Builder(deDataRecord);
		}

		/**
		 * Includes a Data Record ID in the Get response.
		 *
		 * @param deDataRecordId the primary key of the {@link DEDataRecord}
		 * instance
		 * @return {@link DEDataRecordCollectionGetRecordResponse}
		 * @review
		 */
		public static DEDataRecordCollectionGetRecordResponse of(
			DEDataRecord deDataRecord) {

			return newBuilder(
				deDataRecord
			).build();
		}

		/**
		 * Constructs the Get Data Record response.
		 *
		 * @return {@link DEDataRecordCollectionGetRecordResponseØ}
		 * @review
		 */
		public DEDataRecordCollectionGetRecordResponse build() {
			return _deDataRecordCollectionGetRecordResponse;
		}

		private Builder(DEDataRecord deDataRecord) {
			_deDataRecordCollectionGetRecordResponse._deDataRecord =
				deDataRecord;
		}

		private final DEDataRecordCollectionGetRecordResponse
			_deDataRecordCollectionGetRecordResponse =
				new DEDataRecordCollectionGetRecordResponse();

	}

	private DEDataRecordCollectionGetRecordResponse() {
	}

	private DEDataRecord _deDataRecord;

}