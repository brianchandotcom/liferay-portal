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

package com.liferay.headless.admin.user.client.dto.v1_0;

import com.liferay.headless.admin.user.client.function.UnsafeSupplier;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Service {

	public HoursAvailable[] getHoursAvailable() {
		return hoursAvailable;
	}

	public void setHoursAvailable(HoursAvailable[] hoursAvailable) {
		this.hoursAvailable = hoursAvailable;
	}

	public void setHoursAvailable(
		UnsafeSupplier<HoursAvailable[], Exception>
			hoursAvailableUnsafeSupplier) {

		try {
			hoursAvailable = hoursAvailableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected HoursAvailable[] hoursAvailable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public void setServiceType(
		UnsafeSupplier<String, Exception> serviceTypeUnsafeSupplier) {

		try {
			serviceType = serviceTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

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
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"hoursAvailable\": ");

		if (hoursAvailable == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < hoursAvailable.length; i++) {
				sb.append(hoursAvailable[i]);

				if ((i + 1) < hoursAvailable.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (id == null) {
			sb.append("null");
		}
		else {
			sb.append(id);
		}

		sb.append(", ");

		sb.append("\"serviceType\": ");

		if (serviceType == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(serviceType);
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

}