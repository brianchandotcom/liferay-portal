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
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseDispatchTaskExecutor
	extends com.liferay.dispatch.executor.BaseDispatchTaskExecutor {

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
	protected DispatchLogLocalService dispatchLogLocalService;

	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

}