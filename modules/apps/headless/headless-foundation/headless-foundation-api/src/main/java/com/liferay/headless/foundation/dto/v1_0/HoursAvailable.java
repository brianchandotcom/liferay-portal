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
@GraphQLName("HoursAvailable")
@XmlRootElement(name = "HoursAvailable")
public class HoursAvailable {

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
		UnsafeSupplier<String, Exception> closesUnsafeSupplier) {

		try {
			closes = closesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@JsonIgnore
	public void setDayOfWeek(
		UnsafeSupplier<String, Exception> dayOfWeekUnsafeSupplier) {

		try {
			dayOfWeek = dayOfWeekUnsafeSupplier.get();
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

	public void setOpens(String opens) {
		this.opens = opens;
	}

	@JsonIgnore
	public void setOpens(
		UnsafeSupplier<String, Exception> opensUnsafeSupplier) {

		try {
			opens = opensUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{");

		sb.append("\"closes\": ");

		sb.append("\"");
		sb.append(closes);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dayOfWeek\": ");

		sb.append("\"");
		sb.append(dayOfWeek);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"opens\": ");

		sb.append("\"");
		sb.append(opens);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
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