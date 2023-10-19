/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.dispatch.executor;

import com.liferay.analytics.batch.exportimport.manager.AnalyticsBatchExportImportManager;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	extends BaseDispatchTaskExecutor {

	public static final String KEY = "export-analytics-dxp-entities";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		if (!_analyticsConfigurationRegistry.isActive()) {
			return;
		}

		analyticsBatchExportImportManager.exportToAnalyticsCloud(
			_batchEngineExportTaskItemDelegateNames,
			dispatchTrigger.getCompanyId(),
			getNotificationUnsafeConsumer(
				dispatchTrigger.getDispatchTriggerId(),
				dispatchTaskExecutorOutput),
			getResourceLastModifiedDate(dispatchTrigger.getDispatchTriggerId()),
			DXPEntity.class.getName(), dispatchTrigger.getUserId());
	}

	@Override
	public String getName() {
		return KEY;
	}

	protected UnsafeConsumer<String, Exception> getNotificationUnsafeConsumer(
		long dispatchTriggerId,
		DispatchTaskExecutorOutput dispatchTaskExecutorOutput) {

		DispatchLog dispatchLog =
			dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTriggerId, DispatchTaskStatus.IN_PROGRESS);

		return message -> updateDispatchLog(
			dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
			message);
	}

	protected Date getResourceLastModifiedDate(long dispatchTriggerId) {
		DispatchLog latestSuccessfulDispatchLog =
			dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTriggerId, DispatchTaskStatus.SUCCESSFUL);

		if (latestSuccessfulDispatchLog == null) {
			return null;
		}

		return latestSuccessfulDispatchLog.getEndDate();
	}

	protected void updateDispatchLog(
			long dispatchLogId,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput,
			String message)
		throws PortalException {

		StringBundler sb = new StringBundler(5);

		if (dispatchTaskExecutorOutput.getOutput() != null) {
			sb.append(dispatchTaskExecutorOutput.getOutput());
		}

		sb.append(_dateFormat.format(new Date()));
		sb.append(StringPool.SPACE);
		sb.append(message);
		sb.append(StringPool.NEW_LINE);

		dispatchTaskExecutorOutput.setOutput(sb.toString());

		dispatchLogLocalService.updateDispatchLog(
			dispatchLogId, new Date(), dispatchTaskExecutorOutput.getError(),
			dispatchTaskExecutorOutput.getOutput(),
			DispatchTaskStatus.IN_PROGRESS);
	}

	@Reference
	protected AnalyticsBatchExportImportManager
		analyticsBatchExportImportManager;

	@Reference
	protected DispatchLogLocalService dispatchLogLocalService;

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
	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

}