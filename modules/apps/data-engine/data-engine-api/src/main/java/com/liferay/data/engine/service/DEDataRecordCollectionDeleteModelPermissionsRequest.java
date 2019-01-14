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
public final class DEDataRecordCollectionDeleteModelPermissionsRequest {

	public List<String> getActionIds() {
		return Collections.unmodifiableList(_actionIds);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getDEDataRecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	public List<String> getRoleNames() {
		return Collections.unmodifiableList(_roleNames);
	}

	public long getScopedGroupId() {
		return _scopedGroupId;
	}

	public static final class Builder {

		public Builder(
			long companyId, long scopedGroupId, long deDataRecordCollectionId,
			String[] roleNames, String[] actionIds) {

			_deDataRecordCollectionDeleteModelPermissionsRequest._companyId =
				companyId;
			_deDataRecordCollectionDeleteModelPermissionsRequest.
				_scopedGroupId = scopedGroupId;
			_deDataRecordCollectionDeleteModelPermissionsRequest.
				_deDataRecordCollectionId = deDataRecordCollectionId;
			_deDataRecordCollectionDeleteModelPermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
			_deDataRecordCollectionDeleteModelPermissionsRequest._actionIds =
				ListUtil.fromArray(actionIds);
		}

		public DEDataRecordCollectionDeleteModelPermissionsRequest build() {
			return _deDataRecordCollectionDeleteModelPermissionsRequest;
		}

		private final DEDataRecordCollectionDeleteModelPermissionsRequest
			_deDataRecordCollectionDeleteModelPermissionsRequest =
				new DEDataRecordCollectionDeleteModelPermissionsRequest();

	}

	private List<String> _actionIds;
	private long _companyId;
	private long _deDataRecordCollectionId;
	private List<String> _roleNames;
	private long _scopedGroupId;

}