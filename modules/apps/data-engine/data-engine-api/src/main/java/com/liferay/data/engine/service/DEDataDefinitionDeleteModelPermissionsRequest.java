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
 * This class represents a request to remove Data Definition model permission
 * to one or more roles
 *
 * @author Marcela Cunha
 */
public class DEDataDefinitionDeleteModelPermissionsRequest {

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
	 * Returns the Data Definition ID of the Model Permission request.
	 *
	 * @return deDataRecordCollectionId
	 * @review
	 */
	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
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
	 * Constructs the Delete Data Definition Model Permissions request.
	 * The company ID, the scoped group ID, the data record collection ID, the
	 * role names list, and the actions ID list must be an argument in the
	 * request.
	 *
	 * @return {@link DEDataDefinitionDeleteModelPermissionsRequest}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Delete Data Definition Model Permission request.
		 *
		 * @param companyId the primary key of the portal instance
		 * @param scopedGroupId the primary key of the group adding the
		 * resources
		 * @param deDataDefinitionId the primary key of the data definition
		 * that want to related to the model permission in the request
		 * @param actionIds the actions id list that will be revoked
		 * @param roleNames the role names list that that will have the revoked
		 * permission
		 * @return {@link Builder}
		 * @review
		 */
		public Builder(
			long companyId, long scopedGroupId, long deDataDefinitionId,
			String[] actionIds, String[] roleNames) {

			_deDataDefinitionDeleteModelPermissionsRequest._companyId =
				companyId;
			_deDataDefinitionDeleteModelPermissionsRequest.
				_scopedGroupId = scopedGroupId;
			_deDataDefinitionDeleteModelPermissionsRequest.
				_deDataDefinitionId = deDataDefinitionId;
			_deDataDefinitionDeleteModelPermissionsRequest._actionIds =
				ListUtil.fromArray(actionIds);
			_deDataDefinitionDeleteModelPermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
		}

		/**
		 * Constructs the Delete Data Definition Model Permission request.
		 *
		 * @return {@link DEDataDefinitionDeleteModelPermissionsRequest}
		 * @review
		 */
		public DEDataDefinitionDeleteModelPermissionsRequest build() {
			return _deDataDefinitionDeleteModelPermissionsRequest;
		}

		private final DEDataDefinitionDeleteModelPermissionsRequest
			_deDataDefinitionDeleteModelPermissionsRequest =
				new DEDataDefinitionDeleteModelPermissionsRequest();

	}

	private List<String> _actionIds;
	private long _companyId;
	private long _deDataDefinitionId;
	private List<String> _roleNames;
	private long _scopedGroupId;

}