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
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionExportRecordsRequest {

	public long getDEDataRecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	public String getFormat() {
		return _format;
	}

	public static final class Builder {

		public Builder(long deDataRecordCollectionId, String format) {
			_deDataRecordCollectionExportRecordsRequest.
				_deDataRecordCollectionId = deDataRecordCollectionId;
			_deDataRecordCollectionExportRecordsRequest._format = format;
		}

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