/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.manager;

import com.liferay.petra.function.UnsafeConsumer;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public interface AnalyticsBatchExportImportManager {

	public void exportToAnalyticsCloud(
			String batchEngineExportTaskItemDelegateName, long companyId,
			List<String> fieldNamesList, String filterString,
			UnsafeConsumer<String, Exception> notificationUnsafeConsumer,
			Date resourceLastModifiedDate, String resourceName, long userId)
		throws Exception;

	public void importFromAnalyticsCloud(
			String batchEngineImportTaskItemDelegateName, long companyId,
			Map<String, String> fieldMapping,
			UnsafeConsumer<String, Exception> notificationUnsafeConsumer,
			Date resourceLastModifiedDate, String resourceName, long userId)
		throws Exception;

}