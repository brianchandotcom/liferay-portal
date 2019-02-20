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

package com.liferay.headless.web.experience.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface AggregateRating {

	public Number getBestRating();

	public void setBestRating(
			Number bestRating);

	public void setBestRating(
			UnsafeSupplier<Number, Throwable>
				bestRatingUnsafeSupplier);
	public Number getRatingCount();

	public void setRatingCount(
			Number ratingCount);

	public void setRatingCount(
			UnsafeSupplier<Number, Throwable>
				ratingCountUnsafeSupplier);
	public Number getRatingValue();

	public void setRatingValue(
			Number ratingValue);

	public void setRatingValue(
			UnsafeSupplier<Number, Throwable>
				ratingValueUnsafeSupplier);
	public Number getWorstRating();

	public void setWorstRating(
			Number worstRating);

	public void setWorstRating(
			UnsafeSupplier<Number, Throwable>
				worstRatingUnsafeSupplier);

}