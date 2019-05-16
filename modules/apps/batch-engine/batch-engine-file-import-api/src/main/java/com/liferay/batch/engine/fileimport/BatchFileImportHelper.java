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

package com.liferay.batch.engine.fileimport;

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.model.BatchFileImport;

import java.io.InputStream;

/**
 * @author Ivica Cardic
 */
public interface BatchFileImportHelper {

	public BatchFileImport process(
			String fileName, InputStream inputStream, String domainName,
			String version, BatchFileImportOperation batchFileImportOperation,
			String callbackURL, String columnNames)
		throws Exception;

	public BatchFileImport queue(
			String fileName, InputStream inputStream, String domainName,
			String version, BatchFileImportOperation operation,
			String callbackURL, String columnNames)
		throws Exception;

}