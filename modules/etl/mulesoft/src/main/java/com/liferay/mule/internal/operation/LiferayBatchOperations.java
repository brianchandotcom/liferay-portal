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

package com.liferay.mule.internal.operation;

import com.liferay.mule.internal.error.provider.LiferayResponseErrorProvider;

import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.operation.Result;

/**
 * @author Matija Petanjek
 */
@Throws(LiferayResponseErrorProvider.class)
public class LiferayBatchOperations {

	@DisplayName("Batch - Import records - Create")
	@MediaType(MediaType.APPLICATION_JSON)
	public Result<String, Void> executeCreateImportTask() {
		return null;
	}

	@DisplayName("Batch - Import records - Delete")
	@MediaType(MediaType.APPLICATION_JSON)
	public Result<String, Void> executeDeleteImportTask() {
		return null;
	}

	@DisplayName("Batch - Export records")
	@MediaType(MediaType.APPLICATION_OCTET_STREAM)
	public Result<String, Void> executeExportTask() {
		return null;
	}

	@DisplayName("Batch - Import records - Update")
	@MediaType(MediaType.APPLICATION_JSON)
	public Result<String, Void> executeUpdateImportTask() {
		return null;
	}

}