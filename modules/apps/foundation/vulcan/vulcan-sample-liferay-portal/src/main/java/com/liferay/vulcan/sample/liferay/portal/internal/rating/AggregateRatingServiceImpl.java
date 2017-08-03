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

package com.liferay.vulcan.sample.liferay.portal.internal.rating;

import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;
import com.liferay.vulcan.liferay.portal.identifier.ClassNameClassPKIdentifier;
import com.liferay.vulcan.sample.liferay.portal.rating.AggregateRating;
import com.liferay.vulcan.sample.liferay.portal.rating.AggregateRatingService;

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
		ClassNameClassPKIdentifier identifier) {

		List<RatingsEntry> entries = _ratingsEntryLocalService.getEntries(
			identifier.getClassName(), identifier.getClassPK());

		return new AggregateRatingImpl(identifier, entries);
	}

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

}