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

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLocalService;
import com.liferay.commerce.data.integration.service.ScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.data.integration.BatchCommerceMLScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.data.integration.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.recommendation.data.integration.process.type.BatchUserCommerceMLRecommendationProcessType;
import com.liferay.headless.commerce.admin.catalog.constants.v1_0.ProductBatchEngineTaskItemDelegateConstants;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.order.constants.v1_0.OrderBatchEngineTaskItemDelegateConstants;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductInteractionRecommendation;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.UserRecommendation;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	immediate = true,
	property = "data.integration.service.executor.key=" + BatchUserCommerceMLRecommendationProcessType.KEY,
	service = ScheduledTaskExecutorService.class
)
public class BatchUserCommerceMLRecommendationScheduledTaskExecutorService
	implements ScheduledTaskExecutorService {

	@Override
	public String getName() {
		return BatchUserCommerceMLRecommendationProcessType.KEY;
	}

	@Override
	public void runProcess(long commerceDataIntegrationProcessId)
		throws IOException, PortalException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			_commerceDataIntegrationProcessLocalService.
				getCommerceDataIntegrationProcess(
					commerceDataIntegrationProcessId);

		List<BatchEngineTaskItemDelegateResourceMapper> importResources =
			new ArrayList<>();

		Map<String, String> userRecommendationFieldMappings = new HashMap<>();

		userRecommendationFieldMappings.put(
			"assetCategoryIds", "assetCategoryIds");
		userRecommendationFieldMappings.put("createDate", "createDate");
		userRecommendationFieldMappings.put("entryClassPK", "productId");
		userRecommendationFieldMappings.put("jobId", "jobId");
		userRecommendationFieldMappings.put(
			"recommendedEntryClassPK", "recommendedProductId");
		userRecommendationFieldMappings.put("score", "score");

		importResources.add(
			new BatchEngineTaskItemDelegateResourceMapper(
				UserRecommendation.class.getName(),
				userRecommendationFieldMappings, null));

		Map<String, String> productInteractionFieldMappings = new HashMap<>();

		productInteractionFieldMappings.put("createDate", "createDate");
		productInteractionFieldMappings.put("entryClassPK", "productId");
		productInteractionFieldMappings.put("jobId", "jobId");
		productInteractionFieldMappings.put("rank", "rank");
		productInteractionFieldMappings.put(
			"recommendedEntryClassPK", "recommendedProductId");
		productInteractionFieldMappings.put("score", "score");

		importResources.add(
			new BatchEngineTaskItemDelegateResourceMapper(
				ProductInteractionRecommendation.class.getName(),
				productInteractionFieldMappings, null));

		_batchCommerceMLScheduledTaskExecutorService.executeScheduledTask(
			commerceDataIntegrationProcessId, _EXPORT_RESOURCE_NAMES,
			getContextProperties(commerceDataIntegrationProcess.getCompanyId()),
			importResources.toArray(
				new BatchEngineTaskItemDelegateResourceMapper[0]));
	}

	protected Map<String, String> getContextProperties(long companyId) {
		Map<String, String> contextProperties = new HashMap<>();

		contextProperties.put("COMMERCE_ML_PROCESS_TYPE", getName());

		contextProperties.put("LIFERAY_COMPANY_ID", String.valueOf(companyId));

		return contextProperties;
	}

	private static final BatchEngineTaskItemDelegateResourceMapper[]
		_EXPORT_RESOURCE_NAMES = {
			new BatchEngineTaskItemDelegateResourceMapper(
				Product.class.getName(), null,
				ProductBatchEngineTaskItemDelegateConstants.
					COMMERCE_ML_PRODUCT),
			new BatchEngineTaskItemDelegateResourceMapper(
				Order.class.getName(), null,
				OrderBatchEngineTaskItemDelegateConstants.COMMERCE_ML_ORDER)
		};

	@Reference
	private BatchCommerceMLScheduledTaskExecutorService
		_batchCommerceMLScheduledTaskExecutorService;

	@Reference
	private CommerceDataIntegrationProcessLocalService
		_commerceDataIntegrationProcessLocalService;

}