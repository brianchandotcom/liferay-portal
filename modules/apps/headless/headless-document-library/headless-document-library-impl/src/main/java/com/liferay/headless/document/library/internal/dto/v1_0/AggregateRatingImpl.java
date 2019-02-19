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

package com.liferay.headless.document.library.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.document.library.dto.v1_0.AggregateRating;
import com.liferay.petra.function.UnsafeSupplier;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("AggregateRating")
@XmlRootElement(name = "AggregateRating")
public class AggregateRatingImpl implements AggregateRating {

	public Number getBestRating() {
		return bestRating;
	}

	public Number getRatingCount() {
		return ratingCount;
	}

	public Number getRatingValue() {
		return ratingValue;
	}

	public Number getWorstRating() {
		return worstRating;
	}

	public void setBestRating(Number bestRating) {
		this.bestRating = bestRating;
	}

	@JsonIgnore
	public void setBestRating(
		UnsafeSupplier<Number, Throwable> bestRatingUnsafeSupplier) {

		try {
			bestRating = bestRatingUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setRatingCount(Number ratingCount) {
		this.ratingCount = ratingCount;
	}

	@JsonIgnore
	public void setRatingCount(
		UnsafeSupplier<Number, Throwable> ratingCountUnsafeSupplier) {

		try {
			ratingCount = ratingCountUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setRatingValue(Number ratingValue) {
		this.ratingValue = ratingValue;
	}

	@JsonIgnore
	public void setRatingValue(
		UnsafeSupplier<Number, Throwable> ratingValueUnsafeSupplier) {

		try {
			ratingValue = ratingValueUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setWorstRating(Number worstRating) {
		this.worstRating = worstRating;
	}

	@JsonIgnore
	public void setWorstRating(
		UnsafeSupplier<Number, Throwable> worstRatingUnsafeSupplier) {

		try {
			worstRating = worstRatingUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Number bestRating;

	@GraphQLField
	@JsonProperty
	protected Number ratingCount;

	@GraphQLField
	@JsonProperty
	protected Number ratingValue;

	@GraphQLField
	@JsonProperty
	protected Number worstRating;

}