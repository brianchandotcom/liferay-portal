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

import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionSavePermissionsRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public List<String> getRoleNames() {
		return Collections.unmodifiableList(_roleNames);
	}

	public long getScopedGroupId() {
		return _scopedGroupId;
	}

	public boolean isAddDataRecordCollection() {
		return _addDataRecordCollection;
	}

	public boolean isDefinePermissions() {
		return _definePermissions;
	}

	public static final class Builder {

		public Builder(
			long companyId, long scopedGroupId, String... roleNames) {

			_deDataRecordCollectionSavePermissionsRequest._companyId =
				companyId;
			_deDataRecordCollectionSavePermissionsRequest._scopedGroupId =
				scopedGroupId;
			_deDataRecordCollectionSavePermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
		}

		public Builder allowAddDataRecordCollection() {
			_deDataRecordCollectionSavePermissionsRequest.
				_addDataRecordCollection = true;

			return this;
		}

		public Builder allowDefinePermissions() {
			_deDataRecordCollectionSavePermissionsRequest._definePermissions =
				true;

			return this;
		}

		public DEDataRecordCollectionSavePermissionsRequest build() {
			return _deDataRecordCollectionSavePermissionsRequest;
		}

		private final DEDataRecordCollectionSavePermissionsRequest
			_deDataRecordCollectionSavePermissionsRequest =
				new DEDataRecordCollectionSavePermissionsRequest();

	}

	private boolean _addDataRecordCollection;
	private long _companyId;
	private boolean _definePermissions;
	private List<String> _roleNames;
	private long _scopedGroupId;

}