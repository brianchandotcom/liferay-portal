/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.dispatch.executor;

import com.liferay.analytics.batch.exportimport.manager.AnalyticsBatchExportImportManager;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.AnalyticsUserContentRecommendation;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + AnalyticsDownloadUserContentRecommendationDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + AnalyticsDownloadUserContentRecommendationDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class AnalyticsDownloadUserContentRecommendationDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	public static final String KEY =
		"analytics-download-user-content-recommendation";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		DispatchLog dispatchLog =
			_dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTrigger.getDispatchTriggerId(),
				DispatchTaskStatus.IN_PROGRESS);

		Date resourceLastModifiedDate = _getLatestSuccessfulDispatchLogEndDate(
			dispatchTrigger.getDispatchTriggerId());

		_analyticsBatchExportImportManager.importFromAnalyticsCloud(
			null, dispatchLog.getCompanyId(),
			HashMapBuilder.put(
				"assetCategoryIds", "assetCategoryIds"
			).put(
				"createDate", "createDate"
			).put(
				"entryClassPK", "userId"
			).put(
				"jobId", "jobId"
			).put(
				"recommendedEntryClassPK", "recommendedAssetEntryId"
			).put(
				"score", "score"
			).build(),
			message -> _updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				message),
			resourceLastModifiedDate,
			AnalyticsUserContentRecommendation.class.getName(),
			dispatchLog.getUserId());
	}

	@Override
	public String getName() {
		return KEY;
	}

	@Override
	public boolean isHiddenInUI() {
		return !FeatureFlagManagerUtil.isEnabled("LRAC-14771");
	}

	private Date _getLatestSuccessfulDispatchLogEndDate(
		long dispatchTriggerId) {

		DispatchLog dispatchLog =
			_dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTriggerId, DispatchTaskStatus.SUCCESSFUL);

		if (dispatchLog != null) {
			return dispatchLog.getEndDate();
		}

		return null;
	}

	private void _updateDispatchLog(
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

		_dispatchLogLocalService.updateDispatchLog(
			dispatchLogId, new Date(), dispatchTaskExecutorOutput.getError(),
			dispatchTaskExecutorOutput.getOutput(),
			DispatchTaskStatus.IN_PROGRESS);
	}

	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@Reference
	private AnalyticsBatchExportImportManager
		_analyticsBatchExportImportManager;

	@Reference
	private DispatchLogLocalService _dispatchLogLocalService;

}