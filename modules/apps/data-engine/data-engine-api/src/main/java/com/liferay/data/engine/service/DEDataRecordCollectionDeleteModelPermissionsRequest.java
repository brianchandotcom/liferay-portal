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
 * This class represents a request to remove Data Record Collection model
 * permission to one or more roles
 *
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionDeleteModelPermissionsRequest {

	/**
	 * Returns the action ID list of the Model Permission request.
	 *
	 * @return companyId
	 * @review
	 */
	public List<String> getActionIds() {
		return Collections.unmodifiableList(_actionIds);
	}

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
	 * Constructs the Delete Data Record Collections Model Permissions request.
	 * The company ID, the scoped group ID, the data record collection ID, the
	 * role names list, and the actions ID list must be an argument in the
	 * request.
	 *
	 * @return {@link DEDataRecordCollectionDeleteModelPermissionsRequest}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Delete Data Record Collections Model Permission
		 * request.
		 *
		 * @param companyId the primary key of the portal instance
		 * @param scopedGroupId the primary key of the group adding the
		 * resources
		 * @param deDataRecordCollectionId the primary key of the data record
		 * collection that want to related to the model permission in the
		 * request
		 * @param roleNames the role names list that that will have the revoked
		 * permission
		 * @param actionIds the actions id list that will be revoked
		 * @return {@link Builder}
		 * @review
		 */
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

		/**
		 * Constructs the Delete Data Record Collections Model Permission
		 * request.
		 *
		 * @return {@link DEDataRecordCollectionDeleteModelPermissionsRequest}
		 * @review
		 */
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