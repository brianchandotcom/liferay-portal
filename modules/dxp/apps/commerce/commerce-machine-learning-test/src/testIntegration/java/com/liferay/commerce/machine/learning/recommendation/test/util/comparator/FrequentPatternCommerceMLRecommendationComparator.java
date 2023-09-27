/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.recommendation.test.util.comparator;

import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Comparator;
import java.util.Set;

/**
 * @author Riccardo Ferrari
 */
public class FrequentPatternCommerceMLRecommendationComparator
	implements Comparator<FrequentPatternCommerceMLRecommendation> {

	public FrequentPatternCommerceMLRecommendationComparator(
		long[] antecedentIds) {

		_antecedentIds = antecedentIds;
	}

	@Override
	public int compare(
		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation1,
		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation2) {

		double delta =
			_getScore(frequentPatternCommerceMLRecommendation2) -
				_getScore(frequentPatternCommerceMLRecommendation1);

		if (delta == 0) {
			return 0;
		}

		if (delta > 0) {
			return 1;
		}

		return -1;
	}

	private double _getScore(
		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation) {

		Set<Long> recommendationAntecedentIdSet = SetUtil.fromArray(
			frequentPatternCommerceMLRecommendation.getAntecedentIds());

		Set<Long> expectedAntecedentIds = SetUtil.fromArray(_antecedentIds);

		expectedAntecedentIds.removeAll(recommendationAntecedentIdSet);

		// Number of matched items

		int functionScoreQueryScore =
			_antecedentIds.length - expectedAntecedentIds.size();

		// Ratio matched items over expected items (positive)

		double score = functionScoreQueryScore / (double)_antecedentIds.length;

		// Ratio matched items over recommendations items (negative)

		double recommendationCoverage =
			functionScoreQueryScore /
				(double)
					frequentPatternCommerceMLRecommendation.
						getAntecedentIdsLength();

		score -= 1.0F - recommendationCoverage;

		score /= 2;

		score += frequentPatternCommerceMLRecommendation.getScore();

		return Math.max(0, score * 100);
	}

	private final long[] _antecedentIds;

}