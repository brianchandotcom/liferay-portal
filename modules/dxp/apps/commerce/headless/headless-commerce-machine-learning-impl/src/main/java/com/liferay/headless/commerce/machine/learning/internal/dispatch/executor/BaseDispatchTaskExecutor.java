/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.internal.dispatch.executor;

import com.liferay.analytics.batch.exportimport.manager.AnalyticsBatchExportImportManager;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseDispatchTaskExecutor
	extends com.liferay.dispatch.executor.BaseDispatchTaskExecutor {

	protected String getCommerceChannelFilter(
			long companyId, Function<Long, String> filterFunction)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			analyticsSettingsManager.getAnalyticsConfiguration(companyId);

		return Stream.of(
			analyticsConfiguration.commerceSyncEnabledAnalyticsChannelIds()
		).map(
			analyticsChannelId -> {
				try {
					return analyticsSettingsManager.getCommerceChannelIds(
						analyticsChannelId, companyId);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}
				}

				return null;
			}
		).filter(
			Objects::nonNull
		).flatMap(
			Stream::of
		).map(
			filterFunction
		).collect(
			Collectors.joining(" or ")
		);
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
	protected AnalyticsSettingsManager analyticsSettingsManager;

	@Reference
	protected DispatchLogLocalService dispatchLogLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDispatchTaskExecutor.class);

	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

}