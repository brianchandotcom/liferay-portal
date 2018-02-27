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
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.portal.apio.architect.context.identifier.ClassNameClassPK;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.util.List;

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
	implements ItemResource<AggregateRating, ClassNameClassPK,
		AggregateRatingIdentifier> {

	@Override
	public String getName() {
		return "aggregate-ratings";
	}

	@Override
	public ItemRoutes<AggregateRating, ClassNameClassPK> itemRoutes(
		ItemRoutes.Builder<AggregateRating, ClassNameClassPK> builder) {

		return builder.addGetter(
			this::_getAggregateRating
		).build();
	}

	@Override
	public Representor<AggregateRating, ClassNameClassPK> representor(
		Representor.Builder<AggregateRating, ClassNameClassPK> builder) {

		return builder.types(
			"AggregateRating"
		).identifier(
			AggregateRating::getClassNameClassPK
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

	private AggregateRating _getAggregateRating(
		ClassNameClassPK classNameClassPK) {

		List<RatingsEntry> ratingsEntries =
			_ratingsEntryLocalService.getEntries(
				classNameClassPK.getClassName(), classNameClassPK.getClassPK());

		return new AggregateRating(classNameClassPK, ratingsEntries);
	}

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

}