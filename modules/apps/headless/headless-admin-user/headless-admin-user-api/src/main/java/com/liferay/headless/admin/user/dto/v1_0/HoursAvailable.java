/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

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
@GraphQLName(
	description = "A list of hours when the organization is open. This follows the [`OpeningHoursSpecification`](https://www.schema.org/OpeningHoursSpecification) specification.",
	value = "HoursAvailable"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "HoursAvailable")
public class HoursAvailable implements Serializable {

	public static HoursAvailable toDTO(String json) {
		return ObjectMapperUtil.readValue(HoursAvailable.class, json);
	}

	public static HoursAvailable unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(HoursAvailable.class, json);
	}

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

	@GraphQLField(
		description = "The organization's closing time (in `HH:MM` format)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
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

	@GraphQLField(description = "The day of the week.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
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

	@GraphQLField(
		description = "The organization's opening time (in `HH:MM` format)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String opens;

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

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.admin.user.dto.v1_0.HoursAvailable",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}