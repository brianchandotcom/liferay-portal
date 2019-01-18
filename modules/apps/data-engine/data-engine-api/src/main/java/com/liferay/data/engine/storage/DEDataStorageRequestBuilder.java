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
 * @author Leonardo Barros
 */
public class DEDataStorageRequestBuilder {

	public static DEDataStorageDeleteRequest.Builder deleteBuilder(
		long deDataStorageId) {

		return new DEDataStorageDeleteRequest.Builder(deDataStorageId);
	}

	public static DEDataStorageGetRequest.Builder getBuilder(
		long deDataStorageId, DEDataDefinition deDataDefinition) {

		return new DEDataStorageGetRequest.Builder(
			deDataStorageId, deDataDefinition);
	}

	public static DEDataStorageSaveRequest.Builder saveBuilder(
		DEDataRecord deDataRecord) {

		return new DEDataStorageSaveRequest.Builder(deDataRecord);
	}

}