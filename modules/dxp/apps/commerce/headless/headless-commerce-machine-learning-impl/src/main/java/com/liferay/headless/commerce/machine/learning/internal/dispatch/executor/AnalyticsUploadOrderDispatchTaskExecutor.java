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

import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Order;
import com.liferay.headless.commerce.machine.learning.internal.batch.engine.v1_0.OrderBatchEngineTaskItemDelegate;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + AnalyticsUploadOrderDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + AnalyticsUploadOrderDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class AnalyticsUploadOrderDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	public static final String KEY = "analytics-upload-order";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		DispatchLog dispatchLog =
			dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTrigger.getDispatchTriggerId(),
				DispatchTaskStatus.IN_PROGRESS);

		analyticsBatchExportImportManager.exportToAnalyticsCloud(
			OrderBatchEngineTaskItemDelegate.KEY,
			dispatchTrigger.getCompanyId(), null,
			message -> updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				message),
			getLatestSuccessfulDispatchLogEndDate(
				dispatchTrigger.getDispatchTriggerId()),
			Order.class.getName(), dispatchTrigger.getUserId());
	}

	@Override
	public String getName() {
		return KEY;
	}

}