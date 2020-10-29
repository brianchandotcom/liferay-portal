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

import com.liferay.commerce.machine.learning.recommendation.UserCommerceMLRecommendation;

/**
 * @author Riccardo Ferrari
 */
public class UserCommerceMLRecommendationImpl
	extends BaseCommerceMLRecommendationImpl
	implements UserCommerceMLRecommendation {

	@Override
	public long[] getAssetCategoryIds() {
		return _assetCategoryIds;
	}

	@Override
	public long getEntryClassPK() {
		return _entryClassPK;
	}

	@Override
	public void setAssetCategoryIds(long[] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	@Override
	public void setEntryClassPK(long entryClassPK) {
		_entryClassPK = entryClassPK;
	}

	private long[] _assetCategoryIds;
	private long _entryClassPK;

}