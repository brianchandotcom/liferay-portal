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

import aQute.bnd.annotation.ProviderType;

import com.liferay.data.engine.exception.DEDataDefinitionException;

/**
 * @author Leonardo Barros
 */
@ProviderType
public interface DEDataDefinitionService {

	public DEDataDefinitionCountResponse execute(
		DEDataDefinitionCountRequest deDataDefinitionCountRequest);

	/**
	 * Execute the Delete Model Permissions Request which can revoke a
	 * permission to a role to not perform actions involving a Data
	 * Definition model
	 *
	 * @param deDataDefinitionDeleteModelPermissionsRequest
	 * @return {@link DEDataDefinitionDeleteModelPermissionsResponse}
	 * @review
	 */
	public DEDataDefinitionDeleteModelPermissionsResponse execute(
			DEDataDefinitionDeleteModelPermissionsRequest
				deDataDefinitionDeleteModelPermissionsRequest)
		throws DEDataDefinitionException;

	/**
	 * Execute the Delete Permissions Request which can revoke permission to
	 * a role to not perform actions involving a Data Definition
	 *
	 * @param deDataDefinitionDeletePermissionsRequest
	 * @return {@link DEDataDefinitionDeletePermissionsResponse}
	 * @review
	 */
	public DEDataDefinitionDeletePermissionsResponse execute(
			DEDataDefinitionDeletePermissionsRequest
				deDataDefinitionDeletePermissionsRequest)
		throws DEDataDefinitionException;

	public DEDataDefinitionDeleteResponse execute(
			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest)
		throws DEDataDefinitionException;

	public DEDataDefinitionGetResponse execute(
			DEDataDefinitionGetRequest deDataDefinitionGetRequest)
		throws DEDataDefinitionException;

	public DEDataDefinitionListResponse execute(
			DEDataDefinitionListRequest deDataDefinitionListRequest)
		throws DEDataDefinitionException;

	/**
	 * Execute the Save Model Permissions Request which can grant permission
	 * to a role to perform actions involving a Data Definition model
	 *
	 * @param deDataDefinitionSaveModelPermissionsRequest
	 * @return {@link DEDataDefinitionSaveModelPermissionsResponse}
	 * @review
	 */
	public DEDataDefinitionSaveModelPermissionsResponse execute(
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest)
		throws DEDataDefinitionException;

	/**
	 * Execute the Save Permissions Request which can grant permission to a
	 * role to perform actions involving a Data Definition
	 *
	 * @param deDataDefinitionSavePermissionsRequest
	 * @return {@link DEDataDefinitionSavePermissionsResponse}
	 * @review
	 */
	public DEDataDefinitionSavePermissionsResponse execute(
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest)
		throws DEDataDefinitionException;

	public DEDataDefinitionSaveResponse execute(
			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest)
		throws DEDataDefinitionException;

	public DEDataDefinitionSearchCountResponse execute(
		DEDataDefinitionSearchCountRequest deDataDefinitionSearchCountRequest);

	public DEDataDefinitionSearchResponse execute(
			DEDataDefinitionSearchRequest deDataDefinitionSearchRequest)
		throws DEDataDefinitionException;

}