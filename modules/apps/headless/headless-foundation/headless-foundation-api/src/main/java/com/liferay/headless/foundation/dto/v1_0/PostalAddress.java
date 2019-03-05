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

package com.liferay.headless.foundation.dto.v1_0;

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
@GraphQLName("PostalAddress")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "PostalAddress")
public class PostalAddress {

	public String getAddressCountry() {
		return addressCountry;
	}

	public String getAddressLocality() {
		return addressLocality;
	}

	public String getAddressRegion() {
		return addressRegion;
	}

	public String getAddressType() {
		return addressType;
	}

	public Long getId() {
		return id;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getStreetAddressLine1() {
		return streetAddressLine1;
	}

	public String getStreetAddressLine2() {
		return streetAddressLine2;
	}

	public String getStreetAddressLine3() {
		return streetAddressLine3;
	}

	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}

	@JsonIgnore
	public void setAddressCountry(
		UnsafeSupplier<String, Exception> addressCountryUnsafeSupplier) {

		try {
			addressCountry = addressCountryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setAddressLocality(String addressLocality) {
		this.addressLocality = addressLocality;
	}

	@JsonIgnore
	public void setAddressLocality(
		UnsafeSupplier<String, Exception> addressLocalityUnsafeSupplier) {

		try {
			addressLocality = addressLocalityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setAddressRegion(String addressRegion) {
		this.addressRegion = addressRegion;
	}

	@JsonIgnore
	public void setAddressRegion(
		UnsafeSupplier<String, Exception> addressRegionUnsafeSupplier) {

		try {
			addressRegion = addressRegionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	@JsonIgnore
	public void setAddressType(
		UnsafeSupplier<String, Exception> addressTypeUnsafeSupplier) {

		try {
			addressType = addressTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@JsonIgnore
	public void setPostalCode(
		UnsafeSupplier<String, Exception> postalCodeUnsafeSupplier) {

		try {
			postalCode = postalCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setStreetAddressLine1(String streetAddressLine1) {
		this.streetAddressLine1 = streetAddressLine1;
	}

	@JsonIgnore
	public void setStreetAddressLine1(
		UnsafeSupplier<String, Exception> streetAddressLine1UnsafeSupplier) {

		try {
			streetAddressLine1 = streetAddressLine1UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setStreetAddressLine2(String streetAddressLine2) {
		this.streetAddressLine2 = streetAddressLine2;
	}

	@JsonIgnore
	public void setStreetAddressLine2(
		UnsafeSupplier<String, Exception> streetAddressLine2UnsafeSupplier) {

		try {
			streetAddressLine2 = streetAddressLine2UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setStreetAddressLine3(String streetAddressLine3) {
		this.streetAddressLine3 = streetAddressLine3;
	}

	@JsonIgnore
	public void setStreetAddressLine3(
		UnsafeSupplier<String, Exception> streetAddressLine3UnsafeSupplier) {

		try {
			streetAddressLine3 = streetAddressLine3UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(44);

		sb.append("{");

		sb.append("\"addressCountry\": ");

		sb.append("\"");
		sb.append(addressCountry);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"addressLocality\": ");

		sb.append("\"");
		sb.append(addressLocality);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"addressRegion\": ");

		sb.append("\"");
		sb.append(addressRegion);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"addressType\": ");

		sb.append("\"");
		sb.append(addressType);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"postalCode\": ");

		sb.append("\"");
		sb.append(postalCode);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"streetAddressLine1\": ");

		sb.append("\"");
		sb.append(streetAddressLine1);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"streetAddressLine2\": ");

		sb.append("\"");
		sb.append(streetAddressLine2);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"streetAddressLine3\": ");

		sb.append("\"");
		sb.append(streetAddressLine3);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String addressCountry;

	@GraphQLField
	@JsonProperty
	protected String addressLocality;

	@GraphQLField
	@JsonProperty
	protected String addressRegion;

	@GraphQLField
	@JsonProperty
	protected String addressType;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String postalCode;

	@GraphQLField
	@JsonProperty
	protected String streetAddressLine1;

	@GraphQLField
	@JsonProperty
	protected String streetAddressLine2;

	@GraphQLField
	@JsonProperty
	protected String streetAddressLine3;

}