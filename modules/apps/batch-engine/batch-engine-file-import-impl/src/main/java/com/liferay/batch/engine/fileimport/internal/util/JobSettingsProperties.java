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

package com.liferay.batch.engine.fileimport.internal.util;

import com.liferay.batch.engine.core.constants.BatchConstants;
import com.liferay.batch.engine.fileimport.constants.BatchFileImportConstants;
import com.liferay.batch.engine.model.BatchFileImport;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Ivica Cardic
 */
public class JobSettingsProperties {

	public static UnicodeProperties getJobSettingsProperties(
		BatchFileImport batchFileImport, FileEntry fileEntry) {

		UnicodeProperties jobSettingsProperties = new UnicodeProperties();

		if (Validator.isNotNull(batchFileImport.getCallbackURL())) {
			jobSettingsProperties.put(
				BatchFileImportConstants.CALLBACK_URL,
				batchFileImport.getCallbackURL());
		}

		if (Validator.isNotNull(batchFileImport.getColumnNames())) {
			jobSettingsProperties.put(
				BatchConstants.COLUMN_NAMES, batchFileImport.getColumnNames());
		}

		jobSettingsProperties.put(
			BatchConstants.BATCH_FILE_IMPORT_ID,
			String.valueOf(batchFileImport.getBatchFileImportId()));
		jobSettingsProperties.put(
			BatchConstants.FILE_ENTRY_ID,
			String.valueOf(fileEntry.getFileEntryId()));

		return jobSettingsProperties;
	}

}