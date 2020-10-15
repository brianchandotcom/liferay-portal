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

package com.liferay.commerce.machine.learning.recommendation.service;

import com.liferay.commerce.machine.learning.recommendation.model.FrequentPatternCommerceMLRecommendation;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Riccardo Ferrari
 */
public interface FrequentPatternCommerceMLRecommendationService {

	public FrequentPatternCommerceMLRecommendation
			addFrequentPatternCommerceMLRecommendation(
				FrequentPatternCommerceMLRecommendation
					frequentPatternCommerceMLRecommendation)
		throws PortalException;

	public FrequentPatternCommerceMLRecommendation create();

	public List<FrequentPatternCommerceMLRecommendation>
			getFrequentPatternCommerceMLRecommendations(
				long companyId, long[] antecedentIds)
		throws PortalException;

}