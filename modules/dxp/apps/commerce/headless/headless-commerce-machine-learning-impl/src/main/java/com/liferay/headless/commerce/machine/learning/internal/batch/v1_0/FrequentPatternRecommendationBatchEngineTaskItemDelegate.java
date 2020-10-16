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

package com.liferay.headless.commerce.machine.learning.internal.batch.v1_0;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendationManager;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.FrequentPatternRecommendation;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	service = BatchEngineTaskItemDelegate.class
)
public class FrequentPatternRecommendationBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<FrequentPatternRecommendation> {

	@Override
	public void createItem(
			FrequentPatternRecommendation item,
			Map<String, Serializable> parameters)
		throws Exception {

		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation =
				_frequentPatternCommerceMLRecommendationManager.create();

		frequentPatternCommerceMLRecommendation.setAntecedentIds(
			ArrayUtil.toArray(item.getAntecedentIds()));
		frequentPatternCommerceMLRecommendation.setAntecedentIdsLength(
			item.getAntecedentIdsLength());
		frequentPatternCommerceMLRecommendation.setCompanyId(
			contextCompany.getCompanyId());
		frequentPatternCommerceMLRecommendation.setCreateDate(
			item.getCreateDate());
		frequentPatternCommerceMLRecommendation.setJobId(item.getJobId());
		frequentPatternCommerceMLRecommendation.setRecommendedEntryClassPK(
			item.getRecommendedProductId());
		frequentPatternCommerceMLRecommendation.setScore(item.getScore());

		_frequentPatternCommerceMLRecommendationManager.
			addFrequentPatternCommerceMLRecommendation(
				frequentPatternCommerceMLRecommendation);
	}

	@Override
	public Page<FrequentPatternRecommendation> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return null;
	}

	@Reference
	private FrequentPatternCommerceMLRecommendationManager
		_frequentPatternCommerceMLRecommendationManager;

}