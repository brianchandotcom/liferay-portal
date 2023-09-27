/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author José Abelenda
 * @generated
 */
@Generated("")
@GraphQLName("CompareRuns")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "CompareRuns")
public class CompareRuns implements Serializable {

	public static CompareRuns toDTO(String json) {
		return ObjectMapperUtil.readValue(CompareRuns.class, json);
	}

	public static CompareRuns unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(CompareRuns.class, json);
	}

	@Schema(description = "A list of status of runs.")
	public String[] getDueStatuses() {
		return dueStatuses;
	}

	public void setDueStatuses(String[] dueStatuses) {
		this.dueStatuses = dueStatuses;
	}

	@JsonIgnore
	public void setDueStatuses(
		UnsafeSupplier<String[], Exception> dueStatusesUnsafeSupplier) {

		try {
			dueStatuses = dueStatusesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "A list of status of runs.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] dueStatuses;

	@Schema
	@Valid
	public Object getValues() {
		return values;
	}

	public void setValues(Object values) {
		this.values = values;
	}

	@JsonIgnore
	public void setValues(
		UnsafeSupplier<Object, Exception> valuesUnsafeSupplier) {

		try {
			values = valuesUnsafeSupplier.get();
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
	protected Object values;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CompareRuns)) {
			return false;
		}

		CompareRuns compareRuns = (CompareRuns)object;

		return Objects.equals(toString(), compareRuns.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (dueStatuses != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueStatuses\": ");

			sb.append("[");

			for (int i = 0; i < dueStatuses.length; i++) {
				sb.append("\"");

				sb.append(_escape(dueStatuses[i]));

				sb.append("\"");

				if ((i + 1) < dueStatuses.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (values != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"values\": ");

			if (values instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)values));
			}
			else if (values instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)values));
				sb.append("\"");
			}
			else {
				sb.append(values);
			}
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.osb.testray.rest.dto.v1_0.CompareRuns",
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