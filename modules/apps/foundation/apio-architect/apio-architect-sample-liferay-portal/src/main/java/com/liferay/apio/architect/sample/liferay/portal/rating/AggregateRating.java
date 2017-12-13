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

package com.liferay.apio.architect.sample.liferay.portal.rating;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.sample.liferay.portal.identifier.AggregateRatingIdentifier;

/**
 * Represents an average rating for an item.
 *
 * <p>
 * This conforms to the <a
 * href="http://schema.org/AggregateRating">AggregateRating </a> type from
 * schema.org.
 * </p>
 *
 * @author Alejandro Hernández
 */
@ProviderType
public interface AggregateRating {

	/**
	 * Returns the aggregate rating's identifier.
	 *
	 * @return the aggregate rating's identifier
	 */
	public AggregateRatingIdentifier getAggregateRatingIdentifier();

	/**
	 * Returns the total number of ratings in the aggregate rating.
	 *
	 * @return the total number of ratings in the aggregate rating
	 */
	public Integer getRatingCount();

	/**
	 * Returns the aggregate rating's value.
	 *
	 * @return the aggregate rating's value
	 */
	public Double getRatingValue();

}