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
public final class DEDataRecordCollectionSaveResponse {

	public long getDEDataRecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	public static final class Builder {

		public static Builder newBuilder(long deDataRecordCollectionId) {
			return new Builder(deDataRecordCollectionId);
		}

		public static DEDataRecordCollectionSaveResponse of(
			long deDataRecordCollectionId) {

			return newBuilder(
				deDataRecordCollectionId
			).build();
		}

		public DEDataRecordCollectionSaveResponse build() {
			return _deDataRecordCollectionSaveResponse;
		}

		private Builder(long deDataRecordCollectionId) {
			_deDataRecordCollectionSaveResponse._deDataRecordCollectionId =
				deDataRecordCollectionId;
		}

		private final DEDataRecordCollectionSaveResponse
			_deDataRecordCollectionSaveResponse =
				new DEDataRecordCollectionSaveResponse();

	}

	private DEDataRecordCollectionSaveResponse() {
	}

	private long _deDataRecordCollectionId;

}