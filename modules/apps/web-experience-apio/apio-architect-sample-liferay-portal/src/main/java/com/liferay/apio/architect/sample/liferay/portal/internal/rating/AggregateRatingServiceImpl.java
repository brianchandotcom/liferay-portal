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

package com.liferay.apio.architect.sample.liferay.portal.internal.rating;

import com.liferay.apio.architect.sample.liferay.portal.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.sample.liferay.portal.rating.AggregateRating;
import com.liferay.apio.architect.sample.liferay.portal.rating.AggregateRatingService;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class AggregateRatingServiceImpl implements AggregateRatingService {

	@Override
	public AggregateRating getAggregateRating(
		AggregateRatingIdentifier aggregateRatingIdentifier) {

		List<RatingsEntry> ratingsEntries =
			_ratingsEntryLocalService.getEntries(
				aggregateRatingIdentifier.getClassName(),
				aggregateRatingIdentifier.getClassPK());

		return new AggregateRatingImpl(
			aggregateRatingIdentifier, ratingsEntries);
	}

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

}