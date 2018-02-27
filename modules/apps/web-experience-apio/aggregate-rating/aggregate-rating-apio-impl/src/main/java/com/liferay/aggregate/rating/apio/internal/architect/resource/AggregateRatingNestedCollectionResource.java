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
import com.liferay.aggregate.rating.apio.internal.architect.AggregateRating;

import com.liferay.apio.architect.resource.ItemResource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@link AggregateRating}
 * resources through a web API.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class AggregateRatingNestedCollectionResource
	implements ItemResource<AggregateRating, AggregateRatingIdentifier> {

	@Override
	public String getName() {
		return "aggregate-ratings";
	}

	@Override
	public ItemRoutes<AggregateRating> itemRoutes(
		ItemRoutes.Builder<AggregateRating, AggregateRatingIdentifier>
			builder) {

		return builder.addGetter(
			aggregateRatingService::getAggregateRating
		).build();
	}

	@Override
	public Representor<AggregateRating, AggregateRatingIdentifier> representor(
		Representor.Builder<AggregateRating, AggregateRatingIdentifier>
			builder) {

		return builder.types(
			"AggregateRating"
		).identifier(
			AggregateRating::getAggregateRatingIdentifier
		).addNumber(
			"bestRating", aggregateRating -> 1
		).addNumber(
			"ratingCount", AggregateRating::getRatingCount
		).addNumber(
			"ratingValue", AggregateRating::getRatingValue
		).addNumber(
			"worstRating", aggregateRating -> 0
		).build();
	}

	@Reference
	protected AggregateRatingService aggregateRatingService;

}