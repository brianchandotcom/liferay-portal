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
@GraphQLName("Configuration")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Configuration")
public class Configuration implements Serializable {

	public static Configuration toDTO(String json) {
		return ObjectMapperUtil.readValue(Configuration.class, json);
	}

	@Schema
	@Valid
	public Advanced getAdvanced() {
		return advanced;
	}

	public void setAdvanced(Advanced advanced) {
		this.advanced = advanced;
	}

	@JsonIgnore
	public void setAdvanced(
		UnsafeSupplier<Advanced, Exception> advancedUnsafeSupplier) {

		try {
			advanced = advancedUnsafeSupplier.get();
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
	protected Advanced advanced;

	@Schema
	@Valid
	public Map<String, Aggregation> getAggregations() {
		return aggregations;
	}

	public void setAggregations(Map<String, Aggregation> aggregations) {
		this.aggregations = aggregations;
	}

	@JsonIgnore
	public void setAggregations(
		UnsafeSupplier<Map<String, Aggregation>, Exception>
			aggregationsUnsafeSupplier) {

		try {
			aggregations = aggregationsUnsafeSupplier.get();
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
	protected Map<String, Aggregation> aggregations;

	@Schema
	@Valid
	public Facet getFacet() {
		return facet;
	}

	public void setFacet(Facet facet) {
		this.facet = facet;
	}

	@JsonIgnore
	public void setFacet(UnsafeSupplier<Facet, Exception> facetUnsafeSupplier) {
		try {
			facet = facetUnsafeSupplier.get();
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
	protected Facet facet;

	@Schema
	@Valid
	public General getGeneral() {
		return general;
	}

	public void setGeneral(General general) {
		this.general = general;
	}

	@JsonIgnore
	public void setGeneral(
		UnsafeSupplier<General, Exception> generalUnsafeSupplier) {

		try {
			general = generalUnsafeSupplier.get();
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
	protected General general;

	@Schema
	@Valid
	public Highlight getHighlight() {
		return highlight;
	}

	public void setHighlight(Highlight highlight) {
		this.highlight = highlight;
	}

	@JsonIgnore
	public void setHighlight(
		UnsafeSupplier<Highlight, Exception> highlightUnsafeSupplier) {

		try {
			highlight = highlightUnsafeSupplier.get();
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
	protected Highlight highlight;

	@Schema
	@Valid
	public Map<String, SearchParameter> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, SearchParameter> parameters) {
		this.parameters = parameters;
	}

	@JsonIgnore
	public void setParameters(
		UnsafeSupplier<Map<String, SearchParameter>, Exception>
			parametersUnsafeSupplier) {

		try {
			parameters = parametersUnsafeSupplier.get();
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
	protected Map<String, SearchParameter> parameters;

	@Schema
	@Valid
	public Query[] getQueries() {
		return queries;
	}

	public void setQueries(Query[] queries) {
		this.queries = queries;
	}

	@JsonIgnore
	public void setQueries(
		UnsafeSupplier<Query[], Exception> queriesUnsafeSupplier) {

		try {
			queries = queriesUnsafeSupplier.get();
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
	protected Query[] queries;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Configuration)) {
			return false;
		}

		Configuration configuration = (Configuration)object;

		return Objects.equals(toString(), configuration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (advanced != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"advanced\": ");

			sb.append(String.valueOf(advanced));
		}

		if (aggregations != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregations\": ");

			sb.append(_toJSON(aggregations));
		}

		if (facet != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"facet\": ");

			sb.append(String.valueOf(facet));
		}

		if (general != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"general\": ");

			sb.append(String.valueOf(general));
		}

		if (highlight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"highlight\": ");

			sb.append(String.valueOf(highlight));
		}

		if (parameters != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameters\": ");

			sb.append(_toJSON(parameters));
		}

		if (queries != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"queries\": ");

			sb.append("[");

			for (int i = 0; i < queries.length; i++) {
				sb.append(String.valueOf(queries[i]));

				if ((i + 1) < queries.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.Configuration",
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