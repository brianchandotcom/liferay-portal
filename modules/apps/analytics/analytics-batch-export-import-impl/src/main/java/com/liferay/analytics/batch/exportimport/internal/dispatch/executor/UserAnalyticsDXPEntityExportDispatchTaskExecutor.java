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

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + UserAnalyticsDXPEntityExportDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + UserAnalyticsDXPEntityExportDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class UserAnalyticsDXPEntityExportDispatchTaskExecutor
	extends BaseAnalyticsDXPEntityExportDispatchTaskExecutor {

	public static final String KEY = "export-user-analytics-dxp-entities";

	@Override
	public String getName() {
		return KEY;
	}

	@Override
	protected String getBatchEngineExportTaskItemDelegateName() {
		return "user-analytics-dxp-entities";
	}

	@Override
	protected boolean shouldExport(long companyId) {
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		if (analyticsConfiguration.syncAllContacts() ||
			!ArrayUtil.isEmpty(analyticsConfiguration.syncedUserGroupIds()) ||
			!ArrayUtil.isEmpty(
				analyticsConfiguration.syncedOrganizationIds())) {

			return true;
		}

		return false;
	}

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

}