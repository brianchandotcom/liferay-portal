/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.document.library.internal.dto.v1_0.util;

import com.liferay.headless.document.library.dto.v1_0.AggregateRating;
import com.liferay.headless.document.library.internal.dto.v1_0.AggregateRatingImpl;
import com.liferay.ratings.kernel.model.RatingsStats;

/**
 * @author Alejandro Hernández
 */
public class AggregateRatingUtil {

	public static AggregateRating toAggregateRating(RatingsStats ratingsStats) {
		if (ratingsStats == null) {
			return null;
		}

		return new AggregateRatingImpl() {
			{
				bestRating = 1;
				ratingCount = ratingsStats.getTotalEntries();
				ratingValue = ratingsStats.getAverageScore();
				worstRating = 0;
			}
		};
	}

}