/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation;

import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;

/**
 * @author Riccardo Ferrari
 */
public class FrequentPatternCommerceMLRecommendationImpl
	extends BaseCommerceMLRecommendationImpl
	implements FrequentPatternCommerceMLRecommendation {

	@Override
	public long[] getAntecedentIds() {
		return _antecedentIds;
	}

	@Override
	public long getAntecedentIdsLength() {
		return _antecedentIdsLength;
	}

	@Override
	public void setAntecedentIds(long[] antecedentIds) {
		_antecedentIds = antecedentIds;
	}

	@Override
	public void setAntecedentIdsLength(long antecedentIdsLength) {
		_antecedentIdsLength = antecedentIdsLength;
	}

	private long[] _antecedentIds;
	private long _antecedentIdsLength;

}