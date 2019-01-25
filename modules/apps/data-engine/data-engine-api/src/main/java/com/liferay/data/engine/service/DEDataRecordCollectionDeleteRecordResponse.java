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
 * This class represents a response of the delete {@link DEDataRecord} request.
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordCollectionDeleteRecordResponse {

	/**
	 * Returns the Data Record ID of the Delete response.
	 *
	 * @return deDataRecordId
	 * @review
	 */
	public long getDEDataRecordId() {
		return _deDataRecordId;
	}

	/**
	 * Constructs the Delete Data Record response using the Data Record ID as
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
		public static Builder newBuilder(long deDataRecordId) {
			return new Builder(deDataRecordId);
		}

		/**
		 * Includes a a Data Record ID in the Delete response.
		 *
		 * @param deDataRecordId the primary key of the {@link DEDataRecord}
		 * instance
		 * @return {@link DEDataRecordCollectionDeleteRecordResponse}
		 * @review
		 */
		public static DEDataRecordCollectionDeleteRecordResponse of(
			long deDataRecordId) {

			return newBuilder(
				deDataRecordId
			).build();
		}

		/**
		 * Constructs the Delete Data Record response.
		 *
		 * @return {@link DEDataRecordCollectionDeleteRecordResponse}
		 * @review
		 */
		public DEDataRecordCollectionDeleteRecordResponse build() {
			return _deDataRecordCollectionDeleteRecordResponse;
		}

		private Builder(long deDataRecordId) {
			_deDataRecordCollectionDeleteRecordResponse._deDataRecordId =
				deDataRecordId;
		}

		private final DEDataRecordCollectionDeleteRecordResponse
			_deDataRecordCollectionDeleteRecordResponse =
				new DEDataRecordCollectionDeleteRecordResponse();

	}

	private DEDataRecordCollectionDeleteRecordResponse() {
	}

	private long _deDataRecordId;

}