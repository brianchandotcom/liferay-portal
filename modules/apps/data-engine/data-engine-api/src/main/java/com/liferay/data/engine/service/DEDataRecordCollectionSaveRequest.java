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

import com.liferay.data.engine.model.DEDataRecordCollection;

/**
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionSaveRequest {

	public DEDataRecordCollection getDEDataRecordCollection() {
		return _deDataRecordCollection;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getUserId() {
		return _userId;
	}

	public static final class Builder {

		public Builder(DEDataRecordCollection deDataRecordCollection) {
			_deDataRecordCollectionSaveRequest._deDataRecordCollection =
				deDataRecordCollection;
		}

		public DEDataRecordCollectionSaveRequest build() {
			return _deDataRecordCollectionSaveRequest;
		}

		public Builder inGroup(long groupId) {
			_deDataRecordCollectionSaveRequest._groupId = groupId;

			return this;
		}

		public Builder onBehalfOf(long userId) {
			_deDataRecordCollectionSaveRequest._userId = userId;

			return this;
		}

		private final DEDataRecordCollectionSaveRequest
			_deDataRecordCollectionSaveRequest =
				new DEDataRecordCollectionSaveRequest();

	}

	private DEDataRecordCollectionSaveRequest() {
	}

	private DEDataRecordCollection _deDataRecordCollection;
	private long _groupId;
	private long _userId;

}