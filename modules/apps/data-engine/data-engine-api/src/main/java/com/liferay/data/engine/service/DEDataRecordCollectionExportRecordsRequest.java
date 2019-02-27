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
 * This class represents a request to export {@link DEDataRecord}s to an specific format
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordCollectionExportRecordsRequest {

	/**
	 * Returns the Data Record Collection ID of the export request.
	 *
	 * @return deDataRecordCollectionId
	 * @review
	 */
	public long getDEDataRecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	/**
	 * Returns the format that the Records will be exported
	 *
	 * @return format
	 * @review
	 */
	public String getFormat() {
		return _format;
	}

	/**
	 * Inner builder that assembles the response.
	 * @review
	 */
	public static final class Builder {

		/**
		 * Instantiate the builder providing the Data Record Collection ID and the export format
		 *
		 * @param deDataRecordCollectionId
		 * @param format
		 * @return The builder
		 * @review
		 */
		public Builder(long deDataRecordCollectionId, String format) {
			_deDataRecordCollectionExportRecordsRequest.
				_deDataRecordCollectionId = deDataRecordCollectionId;
			_deDataRecordCollectionExportRecordsRequest._format = format;
		}

		/**
		 * Builds the request and returns the {@link DEDataRecordCollectionExportRecordsRequest}
		 * object.
		 * @review
		 * @return the {@link DEDataRecordCollectionExportRecordsRequest} object.
		 */
		public DEDataRecordCollectionExportRecordsRequest build() {
			return _deDataRecordCollectionExportRecordsRequest;
		}

		private final DEDataRecordCollectionExportRecordsRequest
			_deDataRecordCollectionExportRecordsRequest =
				new DEDataRecordCollectionExportRecordsRequest();

	}

	private DEDataRecordCollectionExportRecordsRequest() {
	}

	private long _deDataRecordCollectionId;
	private String _format;

}