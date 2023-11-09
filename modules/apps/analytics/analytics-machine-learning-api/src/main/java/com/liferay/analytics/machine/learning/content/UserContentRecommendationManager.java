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
public interface UserContentRecommendationManager {

	public UserContentRecommendation addUserContentRecommendation(
			UserContentRecommendation userContentRecommendation)
		throws PortalException;

	public List<UserContentRecommendation> getUserContentRecommendations(
			long[] assetCategoryIds, long companyId, long userId, int start,
			int end)
		throws PortalException;

	public long getUserContentRecommendationsCount(
			long[] assetCategoryIds, long companyId, long userId)
		throws PortalException;

}