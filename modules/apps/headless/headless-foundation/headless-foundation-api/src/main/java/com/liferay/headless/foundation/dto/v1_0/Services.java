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
@GraphQLName("Services")
@XmlRootElement(name = "Services")
public class Services {

	public HoursAvailable[] getHoursAvailable() {
		return hoursAvailable;
	}

	public Long getId() {
		return id;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setHoursAvailable(HoursAvailable[] hoursAvailable) {
		this.hoursAvailable = hoursAvailable;
	}

	public void setHoursAvailable(
		UnsafeSupplier<HoursAvailable[], Throwable>
			hoursAvailableUnsafeSupplier) {

		try {
			hoursAvailable = hoursAvailableUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public void setServiceType(
		UnsafeSupplier<String, Throwable> serviceTypeUnsafeSupplier) {

		try {
			serviceType = serviceTypeUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected HoursAvailable[] hoursAvailable;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected String serviceType;

}