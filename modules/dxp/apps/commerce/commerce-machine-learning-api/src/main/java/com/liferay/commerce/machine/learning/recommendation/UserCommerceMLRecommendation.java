/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.recommendation;

/**
 * @author Riccardo Ferrari
 */
public interface UserCommerceMLRecommendation extends CommerceMLRecommendation {

	public long[] getAssetCategoryIds();

	public long getEntryClassPK();

	public void setAssetCategoryIds(long[] assetCategoryIds);

	public void setEntryClassPK(long entryClassPK);

}