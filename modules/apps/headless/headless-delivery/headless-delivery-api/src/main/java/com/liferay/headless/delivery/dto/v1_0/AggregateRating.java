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

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("AggregateRating")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AggregateRating")
public class AggregateRating {

	public Number getBestRating() {
		return bestRating;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Number bestRating;

	public Number getRatingCount() {
		return ratingCount;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Number ratingCount;

	public Number getRatingValue() {
		return ratingValue;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Number ratingValue;

	public Number getWorstRating() {
		return worstRating;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Number worstRating;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AggregateRating)) {
			return false;
		}

		AggregateRating aggregateRating = (AggregateRating)object;

		return Objects.equals(toString(), aggregateRating.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"bestRating\": ");

		if (bestRating == null) {
			sb.append("null");
		}
		else {
			sb.append(bestRating);
		}

		sb.append(", ");

		sb.append("\"ratingCount\": ");

		if (ratingCount == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingCount);
		}

		sb.append(", ");

		sb.append("\"ratingValue\": ");

		if (ratingValue == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingValue);
		}

		sb.append(", ");

		sb.append("\"worstRating\": ");

		if (worstRating == null) {
			sb.append("null");
		}
		else {
			sb.append(worstRating);
		}

		sb.append("}");

		return sb.toString();
	}

}