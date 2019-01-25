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

	public List<String> getRoleNames() {
		return Collections.unmodifiableList(_roleNames);
	}

	public long getScopedGroupId() {
		return _scopedGroupId;
	}

	public long getScopedUserId() {
		return _scopedUserId;
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
			long companyId, long groupId, long scopedUserId, long scopedGroupId,
			long deDataDefinitionId, String[] roleNames) {

			_deDataDefinitionSaveModelPermissionsRequest._companyId = companyId;
			_deDataDefinitionSaveModelPermissionsRequest._groupId = groupId;
			_deDataDefinitionSaveModelPermissionsRequest._scopedUserId =
				scopedUserId;
			_deDataDefinitionSaveModelPermissionsRequest._scopedGroupId =
				scopedGroupId;
			_deDataDefinitionSaveModelPermissionsRequest._deDataDefinitionId =
				deDataDefinitionId;
			_deDataDefinitionSaveModelPermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
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

		private final DEDataDefinitionSaveModelPermissionsRequest
			_deDataDefinitionSaveModelPermissionsRequest =
				new DEDataDefinitionSaveModelPermissionsRequest();

	}

	private long _companyId;
	private long _deDataDefinitionId;
	private boolean _delete;
	private long _groupId;
	private List<String> _roleNames;
	private long _scopedGroupId;
	private long _scopedUserId;
	private boolean _update;
	private boolean _view;

}