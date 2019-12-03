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

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Service")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Service")
public class Service {

	@Schema(
		description = "A list of hours when the organization is open. This follows the [`OpeningHoursSpecification`](https://www.schema.org/OpeningHoursSpecification) specification."
	)
	@Valid
	public HoursAvailable[] getHoursAvailable() {
		return hoursAvailable;
	}

	public void setHoursAvailable(HoursAvailable[] hoursAvailable) {
		this.hoursAvailable = hoursAvailable;
	}

	@JsonIgnore
	public void setHoursAvailable(
		UnsafeSupplier<HoursAvailable[], Exception>
			hoursAvailableUnsafeSupplier) {

		try {
			hoursAvailable = hoursAvailableUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A list of hours when the organization is open. This follows the [`OpeningHoursSpecification`](https://www.schema.org/OpeningHoursSpecification) specification."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected HoursAvailable[] hoursAvailable;

	@Schema(description = "The type of service the organization provides.")
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@JsonIgnore
	public void setServiceType(
		UnsafeSupplier<String, Exception> serviceTypeUnsafeSupplier) {

		try {
			serviceType = serviceTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The type of service the organization provides."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String serviceType;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Service)) {
			return false;
		}

		Service service = (Service)object;

		return Objects.equals(toString(), service.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (hoursAvailable != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hoursAvailable\": ");

			sb.append("[");

			for (int i = 0; i < hoursAvailable.length; i++) {
				sb.append(String.valueOf(hoursAvailable[i]));

				if ((i + 1) < hoursAvailable.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (serviceType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"serviceType\": ");

			sb.append("\"");

			sb.append(_escape(serviceType));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.admin.user.dto.v1_0.Service",
		name = "x-class-name"
	)
	public String xClassName;

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