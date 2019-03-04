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

package com.liferay.headless.collaboration.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

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
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "AggregateRating")
public class AggregateRating {

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
		UnsafeSupplier<Number, Exception> bestRatingUnsafeSupplier) {

		try {
			bestRating = bestRatingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setRatingCount(Number ratingCount) {
		this.ratingCount = ratingCount;
	}

	@JsonIgnore
	public void setRatingCount(
		UnsafeSupplier<Number, Exception> ratingCountUnsafeSupplier) {

		try {
			ratingCount = ratingCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setRatingValue(Number ratingValue) {
		this.ratingValue = ratingValue;
	}

	@JsonIgnore
	public void setRatingValue(
		UnsafeSupplier<Number, Exception> ratingValueUnsafeSupplier) {

		try {
			ratingValue = ratingValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setWorstRating(Number worstRating) {
		this.worstRating = worstRating;
	}

	@JsonIgnore
	public void setWorstRating(
		UnsafeSupplier<Number, Exception> worstRatingUnsafeSupplier) {

		try {
			worstRating = worstRatingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{");

		sb.append("\"bestRating\": ");

		sb.append(bestRating);
		sb.append(", ");

		sb.append("\"ratingCount\": ");

		sb.append(ratingCount);
		sb.append(", ");

		sb.append("\"ratingValue\": ");

		sb.append(ratingValue);
		sb.append(", ");

		sb.append("\"worstRating\": ");

		sb.append(worstRating);

		sb.append("}");

		return sb.toString();
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