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

package com.liferay.headless.foundation.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.foundation.dto.v1_0.HoursAvailable;
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
@GraphQLName("HoursAvailable")
@XmlRootElement(name = "HoursAvailable")
public class HoursAvailableImpl implements HoursAvailable {

	public String getCloses() {
		return closes;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public Long getId() {
		return id;
	}

	public String getOpens() {
		return opens;
	}

	public void setCloses(String closes) {
		this.closes = closes;
	}

	@JsonIgnore
	public void setCloses(
		UnsafeSupplier<String, Throwable> closesUnsafeSupplier) {

		try {
			closes = closesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@JsonIgnore
	public void setDayOfWeek(
		UnsafeSupplier<String, Throwable> dayOfWeekUnsafeSupplier) {

		try {
			dayOfWeek = dayOfWeekUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setOpens(String opens) {
		this.opens = opens;
	}

	@JsonIgnore
	public void setOpens(
		UnsafeSupplier<String, Throwable> opensUnsafeSupplier) {

		try {
			opens = opensUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected String closes;

	@GraphQLField
	@JsonProperty
	protected String dayOfWeek;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String opens;

}