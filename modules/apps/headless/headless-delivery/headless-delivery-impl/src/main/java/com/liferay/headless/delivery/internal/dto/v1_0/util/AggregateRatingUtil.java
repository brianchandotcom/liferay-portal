/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.AggregateRating;
import com.liferay.ratings.kernel.model.RatingsStats;

/**
 * @author Javier Gamarra
 */
public class AggregateRatingUtil {

	public static AggregateRating toAggregateRating(RatingsStats ratingsStats) {
		if (ratingsStats == null) {
			return null;
		}

		return new AggregateRating() {
			{
				bestRating = 1D;
				ratingAverage = ratingsStats.getAverageScore();
				ratingCount = ratingsStats.getTotalEntries();
				ratingValue = ratingsStats.getTotalScore();
				worstRating = 0D;
			}
		};
	}

}