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
			List<String> fieldNamesList,
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