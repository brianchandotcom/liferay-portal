/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
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

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
@GraphQLName("Condition")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Condition")
public class Condition implements Serializable {

	public static Condition toDTO(String json) {
		return ObjectMapperUtil.readValue(Condition.class, json);
	}

	public static Condition unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Condition.class, json);
	}

	@Schema
	@Valid
	public Condition[] getAllConditions() {
		return allConditions;
	}

	public void setAllConditions(Condition[] allConditions) {
		this.allConditions = allConditions;
	}

	@JsonIgnore
	public void setAllConditions(
		UnsafeSupplier<Condition[], Exception> allConditionsUnsafeSupplier) {

		try {
			allConditions = allConditionsUnsafeSupplier.get();
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
	protected Condition[] allConditions;

	@Schema
	@Valid
	public Condition[] getAnyConditions() {
		return anyConditions;
	}

	public void setAnyConditions(Condition[] anyConditions) {
		this.anyConditions = anyConditions;
	}

	@JsonIgnore
	public void setAnyConditions(
		UnsafeSupplier<Condition[], Exception> anyConditionsUnsafeSupplier) {

		try {
			anyConditions = anyConditionsUnsafeSupplier.get();
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
	protected Condition[] anyConditions;

	@Schema
	@Valid
	public Contains getContains() {
		return contains;
	}

	public void setContains(Contains contains) {
		this.contains = contains;
	}

	@JsonIgnore
	public void setContains(
		UnsafeSupplier<Contains, Exception> containsUnsafeSupplier) {

		try {
			contains = containsUnsafeSupplier.get();
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
	protected Contains contains;

	@Schema
	@Valid
	public Equals getEquals() {
		return equals;
	}

	public void setEquals(Equals equals) {
		this.equals = equals;
	}

	@JsonIgnore
	public void setEquals(
		UnsafeSupplier<Equals, Exception> equalsUnsafeSupplier) {

		try {
			equals = equalsUnsafeSupplier.get();
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
	protected Equals equals;

	@Schema
	@Valid
	public Exists getExists() {
		return exists;
	}

	public void setExists(Exists exists) {
		this.exists = exists;
	}

	@JsonIgnore
	public void setExists(
		UnsafeSupplier<Exists, Exception> existsUnsafeSupplier) {

		try {
			exists = existsUnsafeSupplier.get();
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
	protected Exists exists;

	@Schema
	@Valid
	public In getIn() {
		return in;
	}

	public void setIn(In in) {
		this.in = in;
	}

	@JsonIgnore
	public void setIn(UnsafeSupplier<In, Exception> inUnsafeSupplier) {
		try {
			in = inUnsafeSupplier.get();
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
	protected In in;

	@Schema
	@Valid
	public Condition getNot() {
		return not;
	}

	public void setNot(Condition not) {
		this.not = not;
	}

	@JsonIgnore
	public void setNot(UnsafeSupplier<Condition, Exception> notUnsafeSupplier) {
		try {
			not = notUnsafeSupplier.get();
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
	protected Condition not;

	@Schema
	@Valid
	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	@JsonIgnore
	public void setRange(UnsafeSupplier<Range, Exception> rangeUnsafeSupplier) {
		try {
			range = rangeUnsafeSupplier.get();
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
	protected Range range;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Condition)) {
			return false;
		}

		Condition condition = (Condition)object;

		return Objects.equals(toString(), condition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (allConditions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"allConditions\": ");

			sb.append("[");

			for (int i = 0; i < allConditions.length; i++) {
				sb.append(String.valueOf(allConditions[i]));

				if ((i + 1) < allConditions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (anyConditions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"anyConditions\": ");

			sb.append("[");

			for (int i = 0; i < anyConditions.length; i++) {
				sb.append(String.valueOf(anyConditions[i]));

				if ((i + 1) < anyConditions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contains != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contains\": ");

			sb.append(String.valueOf(contains));
		}

		if (equals != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"equals\": ");

			sb.append(String.valueOf(equals));
		}

		if (exists != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"exists\": ");

			sb.append(String.valueOf(exists));
		}

		if (in != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"in\": ");

			sb.append(String.valueOf(in));
		}

		if (not != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"not\": ");

			sb.append(String.valueOf(not));
		}

		if (range != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"range\": ");

			sb.append(String.valueOf(range));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.Condition",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append(entry.getKey());
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
				sb.append(value);
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

}