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

package com.liferay.headless.admin.user.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

	@Schema(description = "The address's country (e.g., USA).")
	public String getAddressCountry() {
		return addressCountry;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String addressCountry;

	@Schema(description = "The address's locality (e.g., city).")
	public String getAddressLocality() {
		return addressLocality;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String addressLocality;

	@Schema(description = "The address's region (e.g., state).")
	public String getAddressRegion() {
		return addressRegion;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String addressRegion;

	@Schema(description = "The address's type.")
	public String getAddressType() {
		return addressType;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String addressType;

	@Schema(description = "The address's ID.")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
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
	protected Long id;

	@Schema(description = "The address's postal code (e.g., zip code).")
	public String getPostalCode() {
		return postalCode;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String postalCode;

	@Schema(
		description = "A flag that identifies whether this is the main address of the user/organization."
	)
	public Boolean getPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	@JsonIgnore
	public void setPrimary(
		UnsafeSupplier<Boolean, Exception> primaryUnsafeSupplier) {

		try {
			primary = primaryUnsafeSupplier.get();
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
	protected Boolean primary;

	@Schema(
		description = "The street address's first line (e.g., 1600 Amphitheatre Pkwy.)."
	)
	public String getStreetAddressLine1() {
		return streetAddressLine1;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String streetAddressLine1;

	@Schema(description = "The street address's second line.")
	public String getStreetAddressLine2() {
		return streetAddressLine2;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String streetAddressLine2;

	@Schema(description = "The street address's third line.")
	public String getStreetAddressLine3() {
		return streetAddressLine3;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String streetAddressLine3;

	@Schema(
		defaultValue = "com.liferay.headless.admin.user.dto.v1_0.PostalAddress",
		name = "x-classname"
	)
	public String xClassName;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PostalAddress)) {
			return false;
		}

		PostalAddress postalAddress = (PostalAddress)object;

		return Objects.equals(toString(), postalAddress.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (addressCountry != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressCountry\": ");

			sb.append("\"");

			sb.append(_escape(addressCountry));

			sb.append("\"");
		}

		if (addressLocality != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressLocality\": ");

			sb.append("\"");

			sb.append(_escape(addressLocality));

			sb.append("\"");
		}

		if (addressRegion != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressRegion\": ");

			sb.append("\"");

			sb.append(_escape(addressRegion));

			sb.append("\"");
		}

		if (addressType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressType\": ");

			sb.append("\"");

			sb.append(_escape(addressType));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (postalCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postalCode\": ");

			sb.append("\"");

			sb.append(_escape(postalCode));

			sb.append("\"");
		}

		if (primary != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primary\": ");

			sb.append(primary);
		}

		if (streetAddressLine1 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"streetAddressLine1\": ");

			sb.append("\"");

			sb.append(_escape(streetAddressLine1));

			sb.append("\"");
		}

		if (streetAddressLine2 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"streetAddressLine2\": ");

			sb.append("\"");

			sb.append(_escape(streetAddressLine2));

			sb.append("\"");
		}

		if (streetAddressLine3 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"streetAddressLine3\": ");

			sb.append("\"");

			sb.append(_escape(streetAddressLine3));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}