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

package com.liferay.apio.architect.sample.liferay.portal.internal.resource;

import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.resource.ScopedCollectionResource;
import com.liferay.apio.architect.routes.Routes;
import com.liferay.apio.architect.sample.liferay.portal.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.sample.liferay.portal.rating.AggregateRating;
import com.liferay.apio.architect.sample.liferay.portal.rating.AggregateRatingService;
import com.liferay.apio.architect.wiring.osgi.manager.CollectionResourceManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@link AggregateRating}
 * resources through a web API.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = CollectionResource.class)
public class AggregateRatingScopedCollectionResource
	implements
		ScopedCollectionResource<AggregateRating, AggregateRatingIdentifier> {

	@Override
	public String getName() {
		return "aggregate-ratings";
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

	@Override
	public Routes<AggregateRating> routes(
		Routes.Builder<AggregateRating, AggregateRatingIdentifier> builder) {

		return builder.addCollectionPageItemGetter(
			aggregateRatingService::getAggregateRating
		).build();
	}

	@Reference
	protected AggregateRatingService aggregateRatingService;

	@Reference
	private CollectionResourceManager _collectionResourceManager;

}