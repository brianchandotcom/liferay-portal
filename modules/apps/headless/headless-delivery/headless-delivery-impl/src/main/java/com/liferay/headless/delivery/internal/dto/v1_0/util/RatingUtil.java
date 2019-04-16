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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.ratings.kernel.model.RatingsEntry;

/**
 * @author Javier Gamarra
 */
public class RatingUtil {

	public static Rating toRating(
			Portal portal, RatingsEntry ratingsEntry,
			UserLocalService userLocalService)
		throws Exception {

		return new Rating() {
			{
				bestRating = 1D;
				creator = CreatorUtil.toCreator(
					portal, userLocalService.getUser(ratingsEntry.getUserId()));
				dateCreated = ratingsEntry.getCreateDate();
				dateModified = ratingsEntry.getModifiedDate();
				id = ratingsEntry.getEntryId();
				ratingValue = ratingsEntry.getScore();
				worstRating = 0D;
			}
		};
	}

}