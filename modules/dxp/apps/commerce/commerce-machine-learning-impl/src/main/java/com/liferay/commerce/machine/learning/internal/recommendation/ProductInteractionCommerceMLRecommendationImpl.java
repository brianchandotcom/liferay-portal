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

import com.liferay.commerce.machine.learning.recommendation.ProductInteractionCommerceMLRecommendation;

/**
 * @author Riccardo Ferrari
 */
public class ProductInteractionCommerceMLRecommendationImpl
	extends BaseCommerceMLRecommendationImpl
	implements ProductInteractionCommerceMLRecommendation {

	@Override
	public long getEntryClassPK() {
		return _entryClassPK;
	}

	@Override
	public int getRank() {
		return _rank;
	}

	@Override
	public void setEntryClassPK(long entryClassPK) {
		_entryClassPK = entryClassPK;
	}

	@Override
	public void setRank(int rank) {
		_rank = rank;
	}

	private long _entryClassPK;
	private int _rank;

}