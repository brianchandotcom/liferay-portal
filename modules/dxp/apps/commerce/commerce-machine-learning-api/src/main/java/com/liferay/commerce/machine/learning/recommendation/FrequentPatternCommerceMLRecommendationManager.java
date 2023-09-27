/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.recommendation;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Riccardo Ferrari
 */
public interface FrequentPatternCommerceMLRecommendationManager {

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