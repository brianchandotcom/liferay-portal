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
 * Response class used as a return value for the data record save operation
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordCollectionSaveRecordResponse {

	/**
	 * Returns the saved data record.
	 * @review
	 * @return the data record
	 */
	public DEDataRecord getDEDataRecord() {
		return _deDataRecord;
	}

	/**
	 * Inner builder that assembles the response.
	 * @review
	 */
	public static final class Builder {

		/**
		 * Instantiate the builder providing a deDataRecord
		 * @review
		 * @param deDataRecord
		 * @return The builder
		 */
		public static Builder newBuilder(DEDataRecord deDataRecord) {
			return new Builder(deDataRecord);
		}

		/**
		 * Builds a response directly from a {@link DEDataRecord}.
		 * @review
		 * @param deDataRecord the saved data record
		 * @return The response object
		 */
		public static DEDataRecordCollectionSaveRecordResponse of(
			DEDataRecord deDataRecord) {

			return newBuilder(
				deDataRecord
			).build();
		}

		/**
		 * Builds the response and returns the {@link DEDataRecordCollectionSaveRecordResponse}
		 * object.
		 * @review
		 * @return The response object.
		 */
		public DEDataRecordCollectionSaveRecordResponse build() {
			return _deDataRecordCollectionSaveRecordResponse;
		}

		private Builder(DEDataRecord deDataRecord) {
			_deDataRecordCollectionSaveRecordResponse._deDataRecord =
				deDataRecord;
		}

		private final DEDataRecordCollectionSaveRecordResponse
			_deDataRecordCollectionSaveRecordResponse =
				new DEDataRecordCollectionSaveRecordResponse();

	}

	private DEDataRecordCollectionSaveRecordResponse() {
	}

	private DEDataRecord _deDataRecord;

}