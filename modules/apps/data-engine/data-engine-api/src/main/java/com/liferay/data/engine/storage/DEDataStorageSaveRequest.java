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

package com.liferay.data.engine.storage;

import com.liferay.data.engine.model.DEDataRecord;

/**
 * @author Leonardo Barros
 */
public final class DEDataStorageSaveRequest {

	public DEDataRecord getDEDataRecord() {
		return _deDataRecord;
	}

	public long getDEDataStorageId() {
		return _deDataStorageId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getUserId() {
		return _userId;
	}

	public static class Builder {

		public Builder(DEDataRecord deDataRecord) {
			_deDataStorageSaveRequest._deDataRecord = deDataRecord;
		}

		public DEDataStorageSaveRequest build() {
			return _deDataStorageSaveRequest;
		}

		public Builder inGroup(long groupId) {
			_deDataStorageSaveRequest._groupId = groupId;

			return this;
		}

		public Builder onBehalfOf(long userId) {
			_deDataStorageSaveRequest._userId = userId;

			return this;
		}

		private final DEDataStorageSaveRequest _deDataStorageSaveRequest =
			new DEDataStorageSaveRequest();

	}

	private DEDataStorageSaveRequest() {
	}

	private DEDataRecord _deDataRecord;
	private long _deDataStorageId;
	private long _groupId;
	private long _userId;

}