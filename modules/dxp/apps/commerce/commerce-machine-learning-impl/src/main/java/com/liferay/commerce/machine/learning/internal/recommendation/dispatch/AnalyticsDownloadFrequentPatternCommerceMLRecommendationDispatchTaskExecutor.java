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

package com.liferay.commerce.machine.learning.internal.recommendation.dispatch;

import com.liferay.commerce.machine.learning.internal.data.integration.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.dispatch.AnalyticsDispatchTaskExecutor;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.FrequentPatternRecommendation;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"dispatch.task.executor.name=" + AnalyticsDownloadFrequentPatternCommerceMLRecommendationDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + AnalyticsDownloadFrequentPatternCommerceMLRecommendationDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class
	AnalyticsDownloadFrequentPatternCommerceMLRecommendationDispatchTaskExecutor
		extends BaseDispatchTaskExecutor {

	public static final String KEY =
		"analytics-download-frequent-pattern-commerce-ml-recommendation";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws IOException, PortalException {

		BatchEngineTaskItemDelegateResourceMapper
			batchEngineTaskItemDelegateResourceMapper =
				new BatchEngineTaskItemDelegateResourceMapper(
					FrequentPatternRecommendation.class.getName(),
					HashMapBuilder.put(
						"antecedentIds", "antecedentIds"
					).put(
						"antecedentIdsLength", "antecedentIdsLength"
					).put(
						"createDate", "createDate"
					).put(
						"jobId", "jobId"
					).put(
						"recommendedEntryClassPK", "recommendedProductId"
					).put(
						"score", "score"
					).build(),
					null);

		_analyticsDispatchTaskExecutor.downloadResources(
			dispatchTrigger, dispatchTaskExecutorOutput,
			new BatchEngineTaskItemDelegateResourceMapper[] {
				batchEngineTaskItemDelegateResourceMapper
			});
	}

	@Override
	public String getName() {
		return KEY;
	}

	@Reference
	private AnalyticsDispatchTaskExecutor _analyticsDispatchTaskExecutor;

}