/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.dispatch.executor;

import com.liferay.analytics.batch.exportimport.manager.AnalyticsBatchExportImportManager;
import com.liferay.analytics.machine.learning.internal.recommendation.constants.RecommendationDestinationNames;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseRecommendationDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	@Override
	public boolean isHiddenInUI() {
		return !FeatureFlagManagerUtil.isEnabled("LRAC-14771");
	}

	protected Date getLatestSuccessfulDispatchLogEndDate(
		long dispatchTriggerId) {

		DispatchLog dispatchLog =
			dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTriggerId, DispatchTaskStatus.SUCCESSFUL);

		if (dispatchLog != null) {
			return dispatchLog.getEndDate();
		}

		return null;
	}

	protected abstract int getRecommendationCode();

	protected void scheduleNotification(
		long companyId, long dispatchTriggerId) {

		try {
			Trigger trigger = triggerFactory.createTrigger(
				_getJobName(companyId, dispatchTriggerId),
				_getGroupName(companyId, dispatchTriggerId), new Date(), null,
				"0 * * * * ?");

			Message message = new Message();

			message.setPayload(
				JSONUtil.put(
					"dispatchTriggerId", dispatchTriggerId
				).put(
					"groupName", trigger.getGroupName()
				).put(
					"jobName", trigger.getJobName()
				).put(
					"recommendationCode", getRecommendationCode()
				).toString());

			schedulerEngineHelper.schedule(
				trigger, StorageType.PERSISTED, null,
				RecommendationDestinationNames.
					NOTIFY_RECOMMENDATIONS_JOBS_STATUS_CHANGED,
				message);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
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

	@Reference
	protected SchedulerEngineHelper schedulerEngineHelper;

	@Reference
	protected TriggerFactory triggerFactory;

	private String _getGroupName(long companyId, long dispatchTriggerId) {
		return StringBundler.concat(
			"DISPATCH_GROUP_", dispatchTriggerId, StringPool.AT, companyId);
	}

	private String _getJobName(long companyId, long dispatchTriggerId) {
		return StringBundler.concat(
			"DISPATCH_JOB_", dispatchTriggerId, StringPool.AT, companyId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRecommendationDispatchTaskExecutor.class);

	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

}