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
import com.liferay.commerce.machine.learning.internal.data.integration.AnalyticsCommerceMLScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.data.integration.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.recommendation.data.integration.process.type.AnalyticsDownloadProductContentCommerceMLRecommendationProcessType;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductContentRecommendation;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	immediate = true,
	property = "data.integration.service.executor.key=" + AnalyticsDownloadProductContentCommerceMLRecommendationProcessType.KEY,
	service = ScheduledTaskExecutorService.class
)
public class
	AnalyticsDownloadProductContentCommerceMLRecommendScheduledTaskExecutorServiceImpl
		implements ScheduledTaskExecutorService {

	@Override
	public String getName() {
		return AnalyticsDownloadProductContentCommerceMLRecommendationProcessType.KEY;
	}

	@Override
	public void runProcess(long commerceDataIntegrationProcessId)
		throws IOException, PortalException {

		Map<String, String> productContentFieldMappings = new HashMap<>();

		productContentFieldMappings.put("createDate", "createDate");
		productContentFieldMappings.put("entryClassPK", "productId");
		productContentFieldMappings.put("jobId", "jobId");
		productContentFieldMappings.put("rank", "rank");
		productContentFieldMappings.put(
			"recommendedEntryClassPK", "recommendedProductId");
		productContentFieldMappings.put("score", "score");

		BatchEngineTaskItemDelegateResourceMapper
			batchEngineTaskItemDelegateResourceMapper =
				new BatchEngineTaskItemDelegateResourceMapper(
					ProductContentRecommendation.class.getName(),
					productContentFieldMappings, null);

		_analyticsCommerceMLScheduledTaskExecutorService.downloadResources(
			commerceDataIntegrationProcessId,
			new BatchEngineTaskItemDelegateResourceMapper[] {
				batchEngineTaskItemDelegateResourceMapper
			});
	}

	@Reference
	private AnalyticsCommerceMLScheduledTaskExecutorService
		_analyticsCommerceMLScheduledTaskExecutorService;

}