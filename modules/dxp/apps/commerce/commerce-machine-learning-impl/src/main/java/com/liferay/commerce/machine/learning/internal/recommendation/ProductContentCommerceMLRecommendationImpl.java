/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation;

import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendation;

/**
 * @author Riccardo Ferrari
 */
public class ProductContentCommerceMLRecommendationImpl
	extends BaseCommerceMLRecommendationImpl
	implements ProductContentCommerceMLRecommendation {

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