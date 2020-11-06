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

package com.liferay.commerce.machine.learning.internal.recommendation.data.integration;

import com.liferay.commerce.data.integration.service.ScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.data.integration.AnalyticsScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.data.integration.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.recommendation.data.integration.process.type.AnalyticsUploadOrderProcessType;
import com.liferay.commerce.machine.learning.internal.recommendation.data.integration.process.type.AnalyticsUploadProductProcessType;
import com.liferay.headless.commerce.admin.order.constants.v1_0.OrderBatchEngineTaskItemDelegateConstants;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	property = "data.integration.service.executor.key=" + AnalyticsUploadOrderProcessType.KEY,
	service = ScheduledTaskExecutorService.class
)
public class AnalyticsUploadOrderScheduledTaskExecutorServiceImpl
	implements ScheduledTaskExecutorService {

	@Override
	public String getName() {
		return AnalyticsUploadProductProcessType.KEY;
	}

	@Override
	public void runProcess(long commerceDataIntegrationProcessId)
		throws IOException, PortalException {

		_analyticsScheduledTaskExecutorService.uploadResources(
			commerceDataIntegrationProcessId, _EXPORT_RESOURCE_NAMES);
	}

	private static final BatchEngineTaskItemDelegateResourceMapper[]
		_EXPORT_RESOURCE_NAMES = {
			new BatchEngineTaskItemDelegateResourceMapper(
				Order.class.getName(), null,
				OrderBatchEngineTaskItemDelegateConstants.COMMERCE_ML_ORDER)
		};

	@Reference
	private AnalyticsScheduledTaskExecutorService
		_analyticsScheduledTaskExecutorService;

}