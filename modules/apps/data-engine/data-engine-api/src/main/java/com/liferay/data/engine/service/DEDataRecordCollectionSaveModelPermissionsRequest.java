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
 * This class represents a request to grant Data Record Collection model
 * permission to one or more roles
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordCollectionSaveModelPermissionsRequest {

	/**
	 * Returns the company ID of the Model Permission request.
	 *
	 * @return companyId
	 * @review
	 */
	public long getCompanyId() {
		return _companyId;
	}

	/**
	 * Returns the Data Record Collection ID of the Model Permission request.
	 *
	 * @return deDataRecordCollectionId
	 * @review
	 */
	public long getDEDataRecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	/**
	 * Returns the group ID of the Model Permission request.
	 *
	 * @return scopedGroupId
	 * @review
	 */
	public long getGroupId() {
		return _groupId;
	}

	/**
	 * Returns the role names of the Model Permission request.
	 *
	 * @return a list of roleNames
	 * @review
	 */
	public List<String> getRoleNames() {
		return Collections.unmodifiableList(_roleNames);
	}

	/**
	 * Returns the scoped group ID of the Model Permission request.
	 *
	 * @return scopedGroupId
	 * @review
	 */
	public long getScopedGroupId() {
		return _scopedGroupId;
	}

	/**
	 * Returns the scoped user ID of the Model Permission request.
	 *
	 * @return scopedUserId
	 * @review
	 */
	public long getScopedUserId() {
		return _scopedUserId;
	}

	/**
	 * Returns true or false to inform if one of the permissions to grant is
	 * the one that allows the user to delete the
	 * {@link DEDataRecordCollection} related to the Data Record Collection ID
	 * set in the request
	 *
	 * @return delete
	 * @review
	 */
	public boolean isDelete() {
		return _delete;
	}

	/**
	 * Returns true or false to inform if one of the permissions to grant is
	 * the one that allows the user to update the
	 * {@link DEDataRecordCollection} related to the Data Record Collection ID
	 * set in the request
	 *
	 * @return update
	 * @review
	 */
	public boolean isUpdate() {
		return _update;
	}

	/**
	 * Returns true or false to inform if one of the permissions to grant is
	 * the one that allows the user to view the
	 * {@link DEDataRecordCollection} related to the Data Record Collection ID
	 * set in the request
	 *
	 * @return addDataRecordCollection
	 * @review
	 */
	public boolean isView() {
		return _view;
	}

	/**
	 * Constructs the Save Data Record Collections Model Permissions request.
	 * The company ID, the scoped group ID, the scoped user ID, the data record
	 * collection ID, and the role names list must be an argument in the request.
	 * The permission to allow view, update, or delete a Data Record Collection
	 * can be used as an alternative parameter
	 *
	 * @return {@link DEDataRecordCollectionSaveModelPermissionsRequest}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Save Data Record Collections Model Permission
		 * request.
		 *
		 * @param companyId the primary key of the portal instance
		 * @param groupId the primary key of the group
		 * @param scopedUserId the primary key of the user adding the
		 * resources
		 * @param scopedGroupId the primary key of the group adding the
		 * resources
		 * @param deDataRecordCollectionId the primary key of the
		 * {@link DEDataRecordCollection} collection that want to related to the
		 * model permission in the request
		 * @param roleNames the role names list that are going to receive the
		 * permissions
		 * @return {@link Builder}
		 * @review
		 */
		public Builder(
			long companyId, long groupId, long scopedUserId, long scopedGroupId,
			long deDataRecordCollectionId, String[] roleNames) {

			_deDataRecordCollectionSaveModelPermissionsRequest._companyId =
				companyId;
			_deDataRecordCollectionSaveModelPermissionsRequest._groupId =
				groupId;
			_deDataRecordCollectionSaveModelPermissionsRequest._scopedUserId =
				scopedUserId;
			_deDataRecordCollectionSaveModelPermissionsRequest._scopedGroupId =
				scopedGroupId;
			_deDataRecordCollectionSaveModelPermissionsRequest.
				_deDataRecordCollectionId = deDataRecordCollectionId;
			_deDataRecordCollectionSaveModelPermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to delete
		 * {@link DEDataRecordCollection} included in the request
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowDelete() {
			_deDataRecordCollectionSaveModelPermissionsRequest._delete = true;

			return this;
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to update {@link DEDataRecordCollection}
		 * included in the request.
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowUpdate() {
			_deDataRecordCollectionSaveModelPermissionsRequest._update = true;

			return this;
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to view {@link DEDataRecordCollection}
		 * included in the request.
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowView() {
			_deDataRecordCollectionSaveModelPermissionsRequest._view = true;

			return this;
		}

		/**
		 * Constructs the Save Data Record Collections Model Permission
		 * request.
		 *
		 * @return {@link DEDataRecordCollectionSaveModelPermissionsRequest}
		 * @review
		 */
		public DEDataRecordCollectionSaveModelPermissionsRequest build() {
			return _deDataRecordCollectionSaveModelPermissionsRequest;
		}

		private final DEDataRecordCollectionSaveModelPermissionsRequest
			_deDataRecordCollectionSaveModelPermissionsRequest =
				new DEDataRecordCollectionSaveModelPermissionsRequest();

	}

	private long _companyId;
	private long _deDataRecordCollectionId;
	private boolean _delete;
	private long _groupId;
	private List<String> _roleNames;
	private long _scopedGroupId;
	private long _scopedUserId;
	private boolean _update;
	private boolean _view;

}