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
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Category;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Product;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductChannel;
import com.liferay.headless.commerce.machine.learning.internal.batch.engine.v1_0.CategoryBatchEngineTaskItemDelegate;
import com.liferay.headless.commerce.machine.learning.internal.batch.engine.v1_0.ProductBatchEngineTaskItemDelegate;
import com.liferay.headless.commerce.machine.learning.internal.batch.engine.v1_0.ProductChannelBatchEngineTaskItemDelegate;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + AnalyticsUploadProductDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + AnalyticsUploadProductDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class AnalyticsUploadProductDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	public static final String KEY = "analytics-upload-product";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		DispatchLog dispatchLog =
			dispatchLogLocalService.fetchLatestDispatchLog(
				dispatchTrigger.getDispatchTriggerId(),
				DispatchTaskStatus.IN_PROGRESS);

		Date resourceLastModifiedDate = getLatestSuccessfulDispatchLogEndDate(
			dispatchTrigger.getDispatchTriggerId());

		analyticsBatchExportImportManager.exportToAnalyticsCloud(
			CategoryBatchEngineTaskItemDelegate.KEY,
			dispatchTrigger.getCompanyId(), null,
			message -> updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				message),
			resourceLastModifiedDate, Category.class.getName(),
			dispatchTrigger.getUserId());

		analyticsBatchExportImportManager.exportToAnalyticsCloud(
			ProductBatchEngineTaskItemDelegate.KEY,
			dispatchTrigger.getCompanyId(), null,
			message -> updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				message),
			resourceLastModifiedDate, Product.class.getName(),
			dispatchTrigger.getUserId());

		analyticsBatchExportImportManager.exportToAnalyticsCloud(
			ProductChannelBatchEngineTaskItemDelegate.KEY,
			dispatchTrigger.getCompanyId(), null,
			message -> updateDispatchLog(
				dispatchLog.getDispatchLogId(), dispatchTaskExecutorOutput,
				message),
			resourceLastModifiedDate, ProductChannel.class.getName(),
			dispatchTrigger.getUserId());
	}

	@Override
	public String getName() {
		return KEY;
	}

}