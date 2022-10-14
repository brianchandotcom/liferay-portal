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

package com.liferay.analytics.batch.exportimport.internal.dispatch.executor;

import com.liferay.dispatch.executor.DispatchTaskExecutor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + ExpandoColumnAnalyticsDXPEntityExportDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + ExpandoColumnAnalyticsDXPEntityExportDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class ExpandoColumnAnalyticsDXPEntityExportDispatchTaskExecutor
	extends BaseAnalyticsDXPEntityExportDispatchTaskExecutor {

	public static final String KEY =
		"export-expando-column-analytics-dxp-entities";

	@Override
	public String getName() {
		return KEY;
	}

	@Override
	protected String getBatchEngineExportTaskItemDelegateName() {
		return "expando-column-analytics-dxp-entities";
	}

}