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
public final class DEDataDefinitionSaveModelPermissionsRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getScopedGroupId() {
		return _scopedGroupId;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isDelete() {
		return _delete;
	}

	public boolean isUpdate() {
		return _update;
	}

	public boolean isView() {
		return _view;
	}

	public static final class Builder {

		public Builder(
			long companyId, long scopedGroupId, long deDataDefinitionId) {

			_deDataDefinitionSaveModelPermissionsRequest._companyId = companyId;
			_deDataDefinitionSaveModelPermissionsRequest._scopedGroupId =
				scopedGroupId;
			_deDataDefinitionSaveModelPermissionsRequest._deDataDefinitionId =
				deDataDefinitionId;
		}

		public Builder allowDelete() {
			_deDataDefinitionSaveModelPermissionsRequest._delete = true;

			return this;
		}

		public Builder allowUpdate() {
			_deDataDefinitionSaveModelPermissionsRequest._update = true;

			return this;
		}

		public Builder allowView() {
			_deDataDefinitionSaveModelPermissionsRequest._view = true;

			return this;
		}

		public DEDataDefinitionSaveModelPermissionsRequest build() {
			return _deDataDefinitionSaveModelPermissionsRequest;
		}

		public Builder grantTo(long userId) {
			_deDataDefinitionSaveModelPermissionsRequest._userId = userId;

			return this;
		}

		public Builder inGroup(long groupId) {
			_deDataDefinitionSaveModelPermissionsRequest._groupId = groupId;

			return this;
		}

		private final DEDataDefinitionSaveModelPermissionsRequest
			_deDataDefinitionSaveModelPermissionsRequest =
				new DEDataDefinitionSaveModelPermissionsRequest();

	}

	private long _companyId;
	private long _deDataDefinitionId;
	private boolean _delete;
	private long _groupId;
	private long _scopedGroupId;
	private boolean _update;
	private long _userId;
	private boolean _view;

}