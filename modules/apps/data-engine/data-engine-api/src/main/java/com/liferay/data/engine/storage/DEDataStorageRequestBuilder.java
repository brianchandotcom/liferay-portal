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

import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataRecord;

/**
 * Provides the request builders from the data storage API
 *
 * @author Leonardo Barros
 * @review
 */
public class DEDataStorageRequestBuilder {

	/**
	 * Returns the delete request builder of the data storage
	 *
	 * @param deDataStorageId The data storage id to be deleted.
	 * @return the request builder
	 * @review
	 */
	public static DEDataStorageDeleteRequest.Builder deleteBuilder(
		long deDataStorageId) {

		return new DEDataStorageDeleteRequest.Builder(deDataStorageId);
	}

	/**
	 * Returns the get request builder of the data storage
	 *
	 * @param deDataStorageId The data storage id to be retrieved.
	 * @param deDataDefinition The data definition associated with that data storage.
	 * @return the request builder
	 * @review
	 */
	public static DEDataStorageGetRequest.Builder getBuilder(
		long deDataStorageId, DEDataDefinition deDataDefinition) {

		return new DEDataStorageGetRequest.Builder(
			deDataStorageId, deDataDefinition);
	}

	/**
	 * Returns the save request builder of the data storage
	 *
	 * @param deDataRecord The data record to be saved
	 * @return the request builder
	 * @review
	 */
	public static DEDataStorageSaveRequest.Builder saveBuilder(
		DEDataRecord deDataRecord) {

		return new DEDataStorageSaveRequest.Builder(deDataRecord);
	}

}