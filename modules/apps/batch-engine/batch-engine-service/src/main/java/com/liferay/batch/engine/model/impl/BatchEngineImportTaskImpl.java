/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model.impl;

import com.liferay.batch.engine.model.BatchEngineImportTaskError;
import com.liferay.batch.engine.service.BatchEngineImportTaskErrorLocalServiceUtil;

import java.util.List;

/**
 * The extended model implementation for the BatchEngineImportTask service.
 * Represents a row in the &quot;BatchEngineImportTask&quot; database table,
 * with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class.
 * Whenever methods are added, rerun ServiceBuilder to copy their definitions
 * into the <code>com.liferay.batch.engine.model.BatchEngineImportTask</code>
 * interface.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class BatchEngineImportTaskImpl extends BatchEngineImportTaskBaseImpl {

	public List<BatchEngineImportTaskError> getBatchEngineImportTaskErrors() {
		return BatchEngineImportTaskErrorLocalServiceUtil.
			getBatchEngineImportTaskErrors(getBatchEngineImportTaskId());
	}

	public int getBatchEngineImportTaskErrorsCount() {
		return BatchEngineImportTaskErrorLocalServiceUtil.
			getBatchEngineImportTaskErrorsCount(getBatchEngineImportTaskId());
	}

}