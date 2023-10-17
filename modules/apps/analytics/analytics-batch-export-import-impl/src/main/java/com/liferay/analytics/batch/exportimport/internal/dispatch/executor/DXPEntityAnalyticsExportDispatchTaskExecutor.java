/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.dispatch.executor;

import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;

import java.util.Arrays;
import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
	extends BaseAnalyticsDXPEntityExportDispatchTaskExecutor {

	public static final String KEY = "export-analytics-dxp-entities";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		if (!shouldExport(dispatchTrigger.getCompanyId())) {
			return;
		}

		DispatchLog dispatchLog =
			dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTrigger.getDispatchTriggerId(),
				DispatchTaskStatus.IN_PROGRESS);

		DispatchLog latestSuccessfulDispatchLog =
			dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTrigger.getDispatchTriggerId(),
				DispatchTaskStatus.SUCCESSFUL);

		Date resourceLastModifiedDate = null;

		if (latestSuccessfulDispatchLog != null) {
			resourceLastModifiedDate = latestSuccessfulDispatchLog.getEndDate();
		}

		analyticsBatchExportImportManager.exportToAnalyticsCloud(
			Arrays.asList(
				"account-group-analytics-dxp-entities",
				"analytics-association-analytics-dxp-entities",
				"analytics-delete-message-analytics-dxp-entities",
				"expando-column-analytics-dxp-entities",
				"group-analytics-dxp-entities",
				"organization-analytics-dxp-entities",
				"role-analytics-dxp-entities", "team-analytics-dxp-entities",
				"user-analytics-dxp-entities",
				"user-group-analytics-dxp-entities"),
			dispatchTrigger.getCompanyId(),
			message -> updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				message),
			resourceLastModifiedDate, DXPEntity.class.getName(),
			dispatchTrigger.getUserId());
	}

	@Override
	public String getName() {
		return KEY;
	}

	@Override
	protected String getBatchEngineExportTaskItemDelegateName() {
		return "analytics-dxp-entities";
	}

	@Override
	protected boolean shouldExport(long companyId) {
		return _analyticsConfigurationRegistry.isActive();
	}

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

}