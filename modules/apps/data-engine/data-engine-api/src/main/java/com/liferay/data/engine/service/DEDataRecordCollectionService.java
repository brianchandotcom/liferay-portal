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

import com.liferay.data.engine.exception.DEDataRecordCollectionException;

/**
 * Provides the remote service interface for DEDataRecordCollection.
 *
 * @author Leonardo Barros
 * @review
 */
public interface DEDataRecordCollectionService {

	/**
	 * Execute the Delete Model Permissions Request which can revoke a
	 * permission to a role to not perform actions involving a Data Record
	 * Collection model
	 *
	 * @param deDataRecordCollectionSaveModelPermissionsRequest
	 * @return {@link DEDataRecordCollectionSaveModelPermissionsResponse}
	 * @review
	 */
	public DEDataRecordCollectionDeleteModelPermissionsResponse execute(
			DEDataRecordCollectionDeleteModelPermissionsRequest
				deDataRecordCollectionDeleteModelPermissionsRequest)
		throws DEDataRecordCollectionException;

	/**
	 * Execute the Delete Permissions Request which can revoke permission to
	 * a role to not perform actions involving a Data Record Collection
	 *
	 * @param deDataRecordCollectionSavePermissionsRequest
	 * @return {@link DEDataRecordCollectionSavePermissionsResponse}
	 * @review
	 */
	public DEDataRecordCollectionDeletePermissionsResponse execute(
			DEDataRecordCollectionDeletePermissionsRequest
				deDataRecordCollectionDeletePermissionsRequest)
		throws DEDataRecordCollectionException;

	/**
	 * Removes a {@link DEDataRecord} from the database corresponding to the
	 * ID passed as a parameter on the request.
	 * @param deDataRecordCollectionDeleteRecordRequest request to delete
	 * a {@link DEDataRecord}
	 * @return {@link DEDataRecordCollectionDeleteRecordResponse} Response
	 * of the delete request
	 * @review
	 */
	public DEDataRecordCollectionDeleteRecordResponse execute(
			DEDataRecordCollectionDeleteRecordRequest
				deDataRecordCollectionDeleteRecordRequest)
		throws DEDataRecordCollectionException;

	/**
	 * Removes a {@link DEDataRecordCollection} from the database corresponding to the
	 * ID passed as a parameter on the request.
	 * @param deDataRecordCollectionDeleteRequest request to delete
	 * a {@link DEDataRecordCollection }
	 * @return {@link DEDataRecordCollectionDeleteResponse } Response
	 * of the delete request
	 * @review
	 */
	public DEDataRecordCollectionDeleteResponse execute(
			DEDataRecordCollectionDeleteRequest
				deDataRecordCollectionDeleteRequest)
		throws DEDataRecordCollectionException;

	/**
	 * Get a {@link DEDataRecord} from the database corresponding to the
	 * ID passed as a parameter on the request.
	 * @param DEDataRecordCollectionGetRecordRequest request to get a
	 * {@link DEDataRecord}
	 * @return {@link DEDataRecordCollectionGetRecordResponse} Response
	 * of the get request
	 * @review
	 */
	public DEDataRecordCollectionGetRecordResponse execute(
			DEDataRecordCollectionGetRecordRequest
				deDataRecordCollectionGetRecordRequest)
		throws DEDataRecordCollectionException;

	/**
	 * Retrieves a {@link DEDataRecordCollection } from the database corresponding to the
	 * ID passed as a parameter on the request.
	 *
	 * @param deDataRecordCollectionGetRequest request to retrieve
	 * a {@link DEDataRecordCollection }
	 * @return {@link DEDataRecordCollectionGetResponse } Response
	 * of the get request
	 * @review
	 */
	public DEDataRecordCollectionGetResponse execute(
			DEDataRecordCollectionGetRequest deDataRecordCollectionGetRequest)
		throws DEDataRecordCollectionException;

	/**
	 * List {@link DEDataRecordCollection} from the database corresponding to the
	 * group ID passed as a parameter on the request.
	 *
	 * @param deDataRecordCollectionListRequest request to list
	 * {@link DEDataRecordCollection}
	 * @return {@link DEDataRecordCollectionListResponse } Response
	 * of the list request
	 * @review
	 */
	public DEDataRecordCollectionListResponse execute(
			DEDataRecordCollectionListRequest deDataRecordCollectionListRequest)
		throws DEDataRecordCollectionException;

	/**
	 * Execute the Save Model Permissions Request which can grant permission
	 * to a role to perform actions involving a Data Record Collection model
	 *
	 * @param deDataRecordCollectionSaveModelPermissionsRequest
	 * @return {@link DEDataRecordCollectionSaveModelPermissionsResponse}
	 * @review
	 */
	public DEDataRecordCollectionSaveModelPermissionsResponse execute(
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest)
		throws DEDataRecordCollectionException;

	/**
	 * Execute the Save Permissions Request which can grant permission to a
	 * role to perform actions involving a Data Record Collection
	 *
	 * @param deDataRecordCollectionSavePermissionsRequest
	 * @return {@link DEDataRecordCollectionSavePermissionsResponse}
	 * @review
	 */
	public DEDataRecordCollectionSavePermissionsResponse execute(
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest)
		throws DEDataRecordCollectionException;

	public DEDataRecordCollectionSaveRecordResponse execute(
			DEDataRecordCollectionSaveRecordRequest
				deDataRecordCollectionSaveRecordRequest)
		throws DEDataRecordCollectionException;

	public DEDataRecordCollectionSaveResponse execute(
			DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest)
		throws DEDataRecordCollectionException;

}