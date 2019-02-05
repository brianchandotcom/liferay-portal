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

import java.util.Map;

/**
 * Provides the response builders from the data storage API
 *
 * @author Leonardo Barros
 * @review
 */
public class DEDataStorageResponseBuilder {

	/**
	 * Returns the delete response builder of the data storage
	 *
	 * @param deDataStorageId The id of the deleted data storage.
	 * @return the response builder
	 * @review
	 */
	public static DEDataStorageDeleteResponse.Builder deleteBuilder(
		long deDataStorageId) {

		return new DEDataStorageDeleteResponse.Builder(deDataStorageId);
	}

	/**
	 * Returns the get response builder of the data storage
	 *
	 * @param values The field values of the retrieved data storage.
	 * @return the response builder
	 * @review
	 */
	public static DEDataStorageGetResponse.Builder getBuilder(
		Map<String, Object> values) {

		return new DEDataStorageGetResponse.Builder(values);
	}

	/**
	 * Returns the save response builder of the data storage
	 *
	 * @param deDataStorageId The id of the saved data storage.
	 * @return the response builder
	 * @review
	 */
	public static DEDataStorageSaveResponse.Builder saveBuilder(
		long deDataStorageId) {

		return new DEDataStorageSaveResponse.Builder(deDataStorageId);
	}

}