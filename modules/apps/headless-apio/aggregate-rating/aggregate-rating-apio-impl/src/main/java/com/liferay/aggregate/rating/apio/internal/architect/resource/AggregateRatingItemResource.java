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

package com.liferay.aggregate.rating.apio.internal.architect.resource;

import com.liferay.aggregate.rating.apio.architect.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/AggregateRating">AggregateRating</a> resources
 * through a web API. The resources are mapped from the internal model {@link
 * RatingsStats}.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class AggregateRatingItemResource
	implements ItemResource
		<RatingsStats, ClassNameClassPK, AggregateRatingIdentifier> {

	@Override
	public String getName() {
		return "aggregate-rating";
	}

	@Override
	public ItemRoutes<RatingsStats, ClassNameClassPK> itemRoutes(
		ItemRoutes.Builder<RatingsStats, ClassNameClassPK> builder) {

		return builder.addGetter(
			classNameClassPK -> _ratingsStatsLocalService.getStats(
				classNameClassPK.getClassName(), classNameClassPK.getClassPK())
		).build();
	}

	@Override
	public Representor<RatingsStats> representor(
		Representor.Builder<RatingsStats, ClassNameClassPK> builder) {

		return builder.types(
			"AggregateRating"
		).identifier(
			ratingStats -> ClassNameClassPK.create(
				ratingStats.getClassName(), ratingStats.getClassPK())
		).addNumber(
			"bestRating", __ -> 1
		).addNumber(
			"ratingCount", RatingsStats::getTotalEntries
		).addNumber(
			"ratingValue", RatingsStats::getAverageScore
		).addNumber(
			"worstRating", __ -> 0
		).build();
	}

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

}