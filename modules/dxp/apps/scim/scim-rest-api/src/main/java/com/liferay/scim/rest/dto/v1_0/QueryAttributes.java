/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.dto.v1_0;

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
 * @author Olivér Kecskeméty
 * @generated
 */
@Generated("")
@GraphQLName("QueryAttributes")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "QueryAttributes")
public class QueryAttributes implements Serializable {

	public static QueryAttributes toDTO(String json) {
		return ObjectMapperUtil.readValue(QueryAttributes.class, json);
	}

	public static QueryAttributes unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(QueryAttributes.class, json);
	}

	@Schema(
		description = "A multi-valued list of strings indicating the names of resource attributes to return in the response, overriding the set of attributes that would be returned by default."
	)
	public String[] getAttributes() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	@JsonIgnore
	public void setAttributes(
		UnsafeSupplier<String[], Exception> attributesUnsafeSupplier) {

		try {
			attributes = attributesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A multi-valued list of strings indicating the names of resource attributes to return in the response, overriding the set of attributes that would be returned by default."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] attributes;

	@Schema(
		description = "An integer indicating the desired maximum number of query results per page."
	)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@JsonIgnore
	public void setCount(
		UnsafeSupplier<Integer, Exception> countUnsafeSupplier) {

		try {
			count = countUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "An integer indicating the desired maximum number of query results per page."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer count;

	@Schema(
		description = "A multi-valued list of strings indicating the names of resource attributes to be removed from the default set of attributes to return."
	)
	public String[] getExcludedAttributes() {
		return excludedAttributes;
	}

	public void setExcludedAttributes(String[] excludedAttributes) {
		this.excludedAttributes = excludedAttributes;
	}

	@JsonIgnore
	public void setExcludedAttributes(
		UnsafeSupplier<String[], Exception> excludedAttributesUnsafeSupplier) {

		try {
			excludedAttributes = excludedAttributesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A multi-valued list of strings indicating the names of resource attributes to be removed from the default set of attributes to return."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] excludedAttributes;

	@Schema(
		description = "The filter string used to request a subset of resources."
	)
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	@JsonIgnore
	public void setFilter(
		UnsafeSupplier<String, Exception> filterUnsafeSupplier) {

		try {
			filter = filterUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The filter string used to request a subset of resources."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String filter;

	@Schema(
		description = "A string indicating the attribute whose value SHALL be used to order the returned responses."
	)
	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	@JsonIgnore
	public void setSortBy(
		UnsafeSupplier<String, Exception> sortByUnsafeSupplier) {

		try {
			sortBy = sortByUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A string indicating the attribute whose value SHALL be used to order the returned responses."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String sortBy;

	@Schema(
		description = "A string indicating the order in which the \"sortBy\" parameter is applied."
	)
	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@JsonIgnore
	public void setSortOrder(
		UnsafeSupplier<String, Exception> sortOrderUnsafeSupplier) {

		try {
			sortOrder = sortOrderUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A string indicating the order in which the \"sortBy\" parameter is applied."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String sortOrder;

	@Schema(
		description = "An integer indicating the 1-based index of the first query result."
	)
	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	@JsonIgnore
	public void setStartIndex(
		UnsafeSupplier<Integer, Exception> startIndexUnsafeSupplier) {

		try {
			startIndex = startIndexUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "An integer indicating the 1-based index of the first query result."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer startIndex;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof QueryAttributes)) {
			return false;
		}

		QueryAttributes queryAttributes = (QueryAttributes)object;

		return Objects.equals(toString(), queryAttributes.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (attributes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attributes\": ");

			sb.append("[");

			for (int i = 0; i < attributes.length; i++) {
				sb.append("\"");

				sb.append(_escape(attributes[i]));

				sb.append("\"");

				if ((i + 1) < attributes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (count != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"count\": ");

			sb.append(count);
		}

		if (excludedAttributes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"excludedAttributes\": ");

			sb.append("[");

			for (int i = 0; i < excludedAttributes.length; i++) {
				sb.append("\"");

				sb.append(_escape(excludedAttributes[i]));

				sb.append("\"");

				if ((i + 1) < excludedAttributes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (filter != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"filter\": ");

			sb.append("\"");

			sb.append(_escape(filter));

			sb.append("\"");
		}

		if (sortBy != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sortBy\": ");

			sb.append("\"");

			sb.append(_escape(sortBy));

			sb.append("\"");
		}

		if (sortOrder != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sortOrder\": ");

			sb.append("\"");

			sb.append(_escape(sortOrder));

			sb.append("\"");
		}

		if (startIndex != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startIndex\": ");

			sb.append(startIndex);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.scim.rest.dto.v1_0.QueryAttributes",
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