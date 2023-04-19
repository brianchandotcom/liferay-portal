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

package com.liferay.portal.search.rest.dto.v1_0;

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
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
@GraphQLName("SearchResponse")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SearchResponse")
public class SearchResponse implements Serializable {

	public static SearchResponse toDTO(String json) {
		return ObjectMapperUtil.readValue(SearchResponse.class, json);
	}

	public static SearchResponse unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(SearchResponse.class, json);
	}

	@Schema
	@Valid
	public Map<String, Object> getAggregationResults() {
		return aggregationResults;
	}

	public void setAggregationResults(Map<String, Object> aggregationResults) {
		this.aggregationResults = aggregationResults;
	}

	@JsonIgnore
	public void setAggregationResults(
		UnsafeSupplier<Map<String, Object>, Exception>
			aggregationResultsUnsafeSupplier) {

		try {
			aggregationResults = aggregationResultsUnsafeSupplier.get();
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
	protected Map<String, Object> aggregationResults;

	@Schema
	@Valid
	public Object[] getDocuments() {
		return documents;
	}

	public void setDocuments(Object[] documents) {
		this.documents = documents;
	}

	@JsonIgnore
	public void setDocuments(
		UnsafeSupplier<Object[], Exception> documentsUnsafeSupplier) {

		try {
			documents = documentsUnsafeSupplier.get();
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
	protected Object[] documents;

	@Schema
	@Valid
	public Map<String, Object> getFacets() {
		return facets;
	}

	public void setFacets(Map<String, Object> facets) {
		this.facets = facets;
	}

	@JsonIgnore
	public void setFacets(
		UnsafeSupplier<Map<String, Object>, Exception> facetsUnsafeSupplier) {

		try {
			facets = facetsUnsafeSupplier.get();
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
	protected Map<String, Object> facets;

	@Schema
	@Valid
	public Float getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}

	@JsonIgnore
	public void setMaxScore(
		UnsafeSupplier<Float, Exception> maxScoreUnsafeSupplier) {

		try {
			maxScore = maxScoreUnsafeSupplier.get();
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
	protected Float maxScore;

	@Schema
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@JsonIgnore
	public void setPage(UnsafeSupplier<Integer, Exception> pageUnsafeSupplier) {
		try {
			page = pageUnsafeSupplier.get();
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
	protected Integer page;

	@Schema
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@JsonIgnore
	public void setPageSize(
		UnsafeSupplier<Integer, Exception> pageSizeUnsafeSupplier) {

		try {
			pageSize = pageSizeUnsafeSupplier.get();
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
	protected Integer pageSize;

	@Schema
	@Valid
	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	@JsonIgnore
	public void setRequest(
		UnsafeSupplier<Object, Exception> requestUnsafeSupplier) {

		try {
			request = requestUnsafeSupplier.get();
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
	protected Object request;

	@Schema
	@Valid
	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	@JsonIgnore
	public void setResponse(
		UnsafeSupplier<Object, Exception> responseUnsafeSupplier) {

		try {
			response = responseUnsafeSupplier.get();
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
	protected Object response;

	@Schema
	public Long getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(Long totalHits) {
		this.totalHits = totalHits;
	}

	@JsonIgnore
	public void setTotalHits(
		UnsafeSupplier<Long, Exception> totalHitsUnsafeSupplier) {

		try {
			totalHits = totalHitsUnsafeSupplier.get();
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
	protected Long totalHits;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchResponse)) {
			return false;
		}

		SearchResponse searchResponse = (SearchResponse)object;

		return Objects.equals(toString(), searchResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (aggregationResults != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregationResults\": ");

			sb.append(_toJSON(aggregationResults));
		}

		if (documents != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documents\": ");

			sb.append("[");

			for (int i = 0; i < documents.length; i++) {
				sb.append("\"");

				sb.append(_escape(documents[i]));

				sb.append("\"");

				if ((i + 1) < documents.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (facets != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"facets\": ");

			sb.append(_toJSON(facets));
		}

		if (maxScore != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxScore\": ");

			sb.append(maxScore);
		}

		if (page != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"page\": ");

			sb.append(page);
		}

		if (pageSize != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageSize\": ");

			sb.append(pageSize);
		}

		if (request != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"request\": ");

			if (request instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)request));
			}
			else if (request instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)request));
				sb.append("\"");
			}
			else {
				sb.append(request);
			}
		}

		if (response != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"response\": ");

			if (response instanceof Map) {
				sb.append(
					JSONFactoryUtil.createJSONObject((Map<?, ?>)response));
			}
			else if (response instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)response));
				sb.append("\"");
			}
			else {
				sb.append(response);
			}
		}

		if (totalHits != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalHits\": ");

			sb.append(totalHits);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.portal.search.rest.dto.v1_0.SearchResponse",
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

}