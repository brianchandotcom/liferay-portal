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

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Geo")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Geo")
public class Geo {

	@Schema(description = "The latitude of a point in space.")
	public Number getLatitude() {
		return latitude;
	}

	public void setLatitude(Number latitude) {
		this.latitude = latitude;
	}

	@JsonIgnore
	public void setLatitude(
		UnsafeSupplier<Number, Exception> latitudeUnsafeSupplier) {

		try {
			latitude = latitudeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Number latitude;

	@Schema(description = "The longitude of a point in space.")
	public Number getLongitude() {
		return longitude;
	}

	public void setLongitude(Number longitude) {
		this.longitude = longitude;
	}

	@JsonIgnore
	public void setLongitude(
		UnsafeSupplier<Number, Exception> longitudeUnsafeSupplier) {

		try {
			longitude = longitudeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Number longitude;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Geo)) {
			return false;
		}

		Geo geo = (Geo)object;

		return Objects.equals(toString(), geo.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"latitude\": ");

		if (latitude == null) {
			sb.append("null");
		}
		else {
			sb.append(latitude);
		}

		sb.append(", ");

		sb.append("\"longitude\": ");

		if (longitude == null) {
			sb.append("null");
		}
		else {
			sb.append(longitude);
		}

		sb.append("}");

		return sb.toString();
	}

}