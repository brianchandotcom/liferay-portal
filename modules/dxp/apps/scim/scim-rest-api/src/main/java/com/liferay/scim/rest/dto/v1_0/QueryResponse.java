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
 * @author Olivér Kecskeméty
 * @generated
 */
@Generated("")
@GraphQLName("QueryResponse")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "QueryResponse")
public class QueryResponse implements Serializable {

	public static QueryResponse toDTO(String json) {
		return ObjectMapperUtil.readValue(QueryResponse.class, json);
	}

	public static QueryResponse unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(QueryResponse.class, json);
	}

	@Schema(
		description = "A multi-valued list of complex objects containing the requested resources."
	)
	@Valid
	public Object getResources() {
		return Resources;
	}

	public void setResources(Object Resources) {
		this.Resources = Resources;
	}

	@JsonIgnore
	public void setResources(
		UnsafeSupplier<Object, Exception> ResourcesUnsafeSupplier) {

		try {
			Resources = ResourcesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A multi-valued list of complex objects containing the requested resources."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object Resources;

	@Schema(
		description = "The number of resources returned in a list response page."
	)
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	@JsonIgnore
	public void setItemsPerPage(
		UnsafeSupplier<Integer, Exception> itemsPerPageUnsafeSupplier) {

		try {
			itemsPerPage = itemsPerPageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The number of resources returned in a list response page."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer itemsPerPage;

	@Schema(
		description = "The 1-based index of the first result in the current set of list results."
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
		description = "The 1-based index of the first result in the current set of list results."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer startIndex;

	@Schema(
		description = "The total number of results returned by the list or query operation."
	)
	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	@JsonIgnore
	public void setTotalResults(
		UnsafeSupplier<Integer, Exception> totalResultsUnsafeSupplier) {

		try {
			totalResults = totalResultsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The total number of results returned by the list or query operation."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer totalResults;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof QueryResponse)) {
			return false;
		}

		QueryResponse queryResponse = (QueryResponse)object;

		return Objects.equals(toString(), queryResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (Resources != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"Resources\": ");

			if (Resources instanceof Map) {
				sb.append(
					JSONFactoryUtil.createJSONObject((Map<?, ?>)Resources));
			}
			else if (Resources instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)Resources));
				sb.append("\"");
			}
			else {
				sb.append(Resources);
			}
		}

		if (itemsPerPage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"itemsPerPage\": ");

			sb.append(itemsPerPage);
		}

		if (startIndex != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startIndex\": ");

			sb.append(startIndex);
		}

		if (totalResults != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalResults\": ");

			sb.append(totalResults);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.scim.rest.dto.v1_0.QueryResponse",
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