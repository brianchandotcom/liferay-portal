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
@GraphQLName("HoursAvailable")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "HoursAvailable")
public class HoursAvailable {

	@Schema(
		description = "The organization's closing time (in `HH:MM` format)."
	)
	public String getCloses() {
		return closes;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String closes;

	@Schema(description = "The day of the week.")
	public String getDayOfWeek() {
		return dayOfWeek;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String dayOfWeek;

	@Schema(
		description = "The organization's opening time (in `HH:MM` format)."
	)
	public String getOpens() {
		return opens;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String opens;

	@Schema(
		defaultValue = "com.liferay.headless.admin.user.dto.v1_0.HoursAvailable",
		name = "x-classname"
	)
	public String xClassName;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof HoursAvailable)) {
			return false;
		}

		HoursAvailable hoursAvailable = (HoursAvailable)object;

		return Objects.equals(toString(), hoursAvailable.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (closes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"closes\": ");

			sb.append("\"");

			sb.append(_escape(closes));

			sb.append("\"");
		}

		if (dayOfWeek != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dayOfWeek\": ");

			sb.append("\"");

			sb.append(_escape(dayOfWeek));

			sb.append("\"");
		}

		if (opens != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"opens\": ");

			sb.append("\"");

			sb.append(_escape(opens));

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