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

import com.liferay.data.engine.exception.DEDataLayoutException;

/**
 * Service interface for the {@link com.liferay.data.engine.model.DEDataLayout}
 * operations.
 * @review
 * @author Jeyvison Nascimento
 */
@ProviderType
public interface DEDataLayoutService {

	public DEDataLayoutCountResponse execute(
			DEDataLayoutCountRequest deDataLayoutCountRequest)
		throws DEDataLayoutException;

	/**
	 * Execute the Delete Model Permissions Request which can revoke a
	 * permission to a role to not perform actions involving a Data Layout model
	 *
	 * @param deDataLayoutDeleteModelPermissionsRequest
	 * @return {@link DEDataLayoutDeleteModelPermissionsResponse}
	 * @review
	 */
	public DEDataLayoutDeleteModelPermissionsResponse execute(
			DEDataLayoutDeleteModelPermissionsRequest
				deDataLayoutDeleteModelPermissionsRequest)
		throws DEDataLayoutException;

	/**
	 * Execute the Delete Permissions Request which can revoke permission to
	 * a role to not perform actions involving a Data Layout
	 *
	 * @param deDataLayoutDeletePermissionsRequest
	 * @return {@link DEDataLayoutDeletePermissionsResponse}
	 * @review
	 */
	public DEDataLayoutDeletePermissionsResponse execute(
			DEDataLayoutDeletePermissionsRequest
				deDataLayoutDeletePermissionsRequest)
		throws DEDataLayoutException;

	/**
	 * Delete a DEDataLayout
	 * @param deDataLayoutDeleteRequest
	 * @return The {@link DEDataLayoutDeleteResponse} object
	 * @throws DEDataLayoutException
	 * @review
	 */
	public DEDataLayoutDeleteResponse execute(
			DEDataLayoutDeleteRequest deDataLayoutDeleteRequest)
		throws DEDataLayoutException;

	/**
	 * Retrive a DEDataLayout
	 * @param deDataLayoutGetRequest
	 * @return The {@link DEDataLayoutGetResponse} object
	 * @throws DEDataLayoutException
	 * @review
	 */
	public DEDataLayoutGetResponse execute(
			DEDataLayoutGetRequest deDataLayoutGetRequest)
		throws DEDataLayoutException;

	/**
	 * List DEDataLayouts
	 * @param deDataLayoutListRequest
	 * @return The {@link DEDataLayoutListResponse} object
	 * @throws DEDataLayoutException
	 * @review
	 */
	public DEDataLayoutListResponse execute(
			DEDataLayoutListRequest deDataLayoutListRequest)
		throws DEDataLayoutException;

	/**
	 * Execute the Save Model Permissions Request which can grant permission
	 * to a role to perform actions involving a Data Layout model
	 *
	 * @param deDataLayoutSaveModelPermissionsRequest
	 * @return {@link DEDataLayoutSaveModelPermissionsResponse}
	 * @review
	 */
	public DEDataLayoutSaveModelPermissionsResponse execute(
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest)
		throws DEDataLayoutException;

	/**
	 * Execute the Save Permissions Request which can grant permission to a
	 * role to perform actions involving a Data Layout
	 *
	 * @param deDataLayoutSavePermissionsRequest
	 * @return {@link DEDataLayoutSavePermissionsResponse}
	 * @review
	 */
	public DEDataLayoutSavePermissionsResponse execute(
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest)
		throws DEDataLayoutException;

	/**
	 * Saves a DEDataLayout
	 * @param deDataLayoutSaveRequest
	 * @return The {@link DEDataLayoutSaveResponse} object
	 * @throws DEDataLayoutException
	 * @review
	 */
	public DEDataLayoutSaveResponse execute(
			DEDataLayoutSaveRequest deDataLayoutSaveRequest)
		throws DEDataLayoutException;

}