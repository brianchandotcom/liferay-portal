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