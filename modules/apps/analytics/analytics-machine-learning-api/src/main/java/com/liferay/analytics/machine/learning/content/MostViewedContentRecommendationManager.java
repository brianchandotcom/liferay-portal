/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.content;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Riccardo Ferrari
 */
public interface MostViewedContentRecommendationManager {

	public MostViewedContentRecommendation addMostViewedContentRecommendation(
			MostViewedContentRecommendation mostViewedContentRecommendation)
		throws PortalException;

	public List<MostViewedContentRecommendation>
			getMostViewedContentRecommendations(
				long[] assetCategoryIds, long companyId, int end, int start,
				long userId)
		throws PortalException;

	public long getMostViewedContentRecommendationsCount(
			long[] assetCategoryIds, long companyId, long userId)
		throws PortalException;

}