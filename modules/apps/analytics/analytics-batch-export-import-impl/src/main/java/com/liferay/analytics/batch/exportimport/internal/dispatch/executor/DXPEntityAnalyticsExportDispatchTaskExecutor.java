/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.dispatch.executor;

import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.dispatch.executor.DispatchTaskExecutor;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + DXPEntityAnalyticsExportDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + DXPEntityAnalyticsExportDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class DXPEntityAnalyticsExportDispatchTaskExecutor
	extends BaseAnalyticsDispatchTaskExecutor {

	public static final String KEY = "export-analytics-dxp-entities";

	@Override
	public String getName() {
		return KEY;
	}

	@Override
	protected List<String> getBatchEngineExportTaskItemDelegateNames() {
		return _batchEngineExportTaskItemDelegateNames;
	}

	@Override
	protected String getResourceName() {
		return DXPEntity.class.getName();
	}

	private static final List<String> _batchEngineExportTaskItemDelegateNames =
		Arrays.asList(
			"account-entry-analytics-dxp-entities",
			"account-group-analytics-dxp-entities",
			"analytics-association-analytics-dxp-entities",
			"analytics-delete-message-analytics-dxp-entities",
			"expando-column-analytics-dxp-entities",
			"group-analytics-dxp-entities",
			"organization-analytics-dxp-entities",
			"role-analytics-dxp-entities", "team-analytics-dxp-entities",
			"user-analytics-dxp-entities", "user-group-analytics-dxp-entities");

}