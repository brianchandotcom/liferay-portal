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

package com.liferay.aggregate.rating.apio.internal.architect;

import com.liferay.portal.apio.architect.context.identifier.ClassNameClassPK;
import com.liferay.ratings.kernel.model.RatingsEntry;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Alejandro Hernández
 */
public class AggregateRating {

	public AggregateRating(
		ClassNameClassPK classNameClassPK, List<RatingsEntry> ratingsEntries) {

		_classNameClassPK = classNameClassPK;

		_ratingCount = ratingsEntries.size();

		Stream<RatingsEntry> stream = ratingsEntries.stream();

		_ratingValue = stream.mapToDouble(
			RatingsEntry::getScore
		).average(
		).orElse(
			0
		);
	}

	public ClassNameClassPK getClassNameClassPK() {
		return _classNameClassPK;
	}

	public Integer getRatingCount() {
		return _ratingCount;
	}

	public Double getRatingValue() {
		return _ratingValue;
	}

	private final ClassNameClassPK _classNameClassPK;
	private final Integer _ratingCount;
	private final Double _ratingValue;

}