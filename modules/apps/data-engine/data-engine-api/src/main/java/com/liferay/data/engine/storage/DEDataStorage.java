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

package com.liferay.data.engine.storage;

import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordValue;

/**
 * Provides the remote service interface for the Data Storage.
 *
 * @author Leonardo Barros
 * @review
 */
public interface DEDataStorage {

	/**
	 * Deletes a data record on a data storage using the id passed
	 * on the delete request.
	 *
	 * @param dataStorageId
	 * @return
	 * @throws Exception
	 */
	public long delete(long dataStorageId) throws Exception;

	/**
	 * Retrieves a data record on a data storage using an id
	 * and a data definition passed on the get request.
	 *
	 * @param dataDefinitionId
	 * @param dataStorageId
	 * @return
	 * @throws Exception
	 */
	public DataRecordValue[] get(long dataDefinitionId, long dataStorageId)
		throws Exception;

	/**
	 * Saves a data record on a data storage using the data record
	 * passed on the get request.
	 *
	 * @param groupId
	 * @param dataRecord
	 * @return dataStorageId
	 * @throws Exception
	 */
	public long save(long groupId, DataRecord dataRecord) throws Exception;

}