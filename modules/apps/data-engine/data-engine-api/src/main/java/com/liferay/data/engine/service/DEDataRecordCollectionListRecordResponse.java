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

import java.util.List;

/**
 * This class represents the response of the list {@link DEDataRecord}
 * request.
 *
 * @author Marcela Cunha
 * @review
 */
public class DEDataRecordCollectionListRecordResponse {

	/**
	 * Returns the Data Record list result of the request
	 *
	 * @return deDataRecords
	 * @review
	 */
	public List<DEDataRecord> getDEDataRecords() {
		return _deDataRecords;
	}

	/**
	 * Inner builder that assembles the response.
	 *
	 * @review
	 */
	public static final class Builder {

		/**
		 * Instantiate the builder providing a {@link DEDataRecord} list
		 *
		 * @param deDataRecords
		 * @return {@link Builder}
		 * @review
		 */
		public static Builder newBuilder(List<DEDataRecord> deDataRecords) {
			return new Builder(deDataRecords);
		}

		/**
		 * Builds a response directly from a {@link DEDataRecord} list.
		 *
		 * @param deDataRecords the list returned by the executor method
		 * @return The response object
		 * @review
		 */
		public static DEDataRecordCollectionListRecordResponse of(
			List<DEDataRecord> deDataRecords) {

			return newBuilder(
				deDataRecords
			).build();
		}

		/**
		 * Builds the response and returns the
		 * {@link DEDataRecordCollectionListRecordResponse} object.
		 *
		 * @return The response object.
		 * @review
		 */
		public DEDataRecordCollectionListRecordResponse build() {
			return _deDataRecordCollectionListRecordResponse;
		}

		private Builder(List<DEDataRecord> deDataRecords) {
			_deDataRecordCollectionListRecordResponse._deDataRecords =
				deDataRecords;
		}

		private final DEDataRecordCollectionListRecordResponse
			_deDataRecordCollectionListRecordResponse =
				new DEDataRecordCollectionListRecordResponse();

	}

	private DEDataRecordCollectionListRecordResponse() {
	}

	private List<DEDataRecord> _deDataRecords;

}