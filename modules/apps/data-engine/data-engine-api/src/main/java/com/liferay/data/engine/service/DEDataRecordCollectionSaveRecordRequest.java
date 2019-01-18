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
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionSaveRecordRequest {

	public DEDataRecord getDEDataRecord() {
		return _deDataRecord;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getUserId() {
		return _userId;
	}

	public static final class Builder {

		public Builder(DEDataRecord deDataRecord) {
			_deDataRecordCollectionSaveRecordRequest._deDataRecord =
				deDataRecord;
		}

		public DEDataRecordCollectionSaveRecordRequest build() {
			return _deDataRecordCollectionSaveRecordRequest;
		}

		public Builder inGroup(long groupId) {
			_deDataRecordCollectionSaveRecordRequest._groupId = groupId;

			return this;
		}

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