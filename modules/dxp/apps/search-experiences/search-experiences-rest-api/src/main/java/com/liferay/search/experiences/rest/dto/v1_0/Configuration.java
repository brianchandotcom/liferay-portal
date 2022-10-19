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

	public static Configuration unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Configuration.class, json);
	}

	@Schema
	@Valid
	public AdvancedConfiguration getAdvancedConfiguration() {
		return advancedConfiguration;
	}

	public void setAdvancedConfiguration(
		AdvancedConfiguration advancedConfiguration) {

		this.advancedConfiguration = advancedConfiguration;
	}

	@JsonIgnore
	public void setAdvancedConfiguration(
		UnsafeSupplier<AdvancedConfiguration, Exception>
			advancedConfigurationUnsafeSupplier) {

		try {
			advancedConfiguration = advancedConfigurationUnsafeSupplier.get();
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
	protected AdvancedConfiguration advancedConfiguration;

	@Schema
	@Valid
	public AggregationConfiguration getAggregationConfiguration() {
		return aggregationConfiguration;
	}

	public void setAggregationConfiguration(
		AggregationConfiguration aggregationConfiguration) {

		this.aggregationConfiguration = aggregationConfiguration;
	}

	@JsonIgnore
	public void setAggregationConfiguration(
		UnsafeSupplier<AggregationConfiguration, Exception>
			aggregationConfigurationUnsafeSupplier) {

		try {
			aggregationConfiguration =
				aggregationConfigurationUnsafeSupplier.get();
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
	protected AggregationConfiguration aggregationConfiguration;

	@Schema
	@Valid
	public GeneralConfiguration getGeneralConfiguration() {
		return generalConfiguration;
	}

	public void setGeneralConfiguration(
		GeneralConfiguration generalConfiguration) {

		this.generalConfiguration = generalConfiguration;
	}

	@JsonIgnore
	public void setGeneralConfiguration(
		UnsafeSupplier<GeneralConfiguration, Exception>
			generalConfigurationUnsafeSupplier) {

		try {
			generalConfiguration = generalConfigurationUnsafeSupplier.get();
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
	protected GeneralConfiguration generalConfiguration;

	@Schema
	@Valid
	public HighlightConfiguration getHighlightConfiguration() {
		return highlightConfiguration;
	}

	public void setHighlightConfiguration(
		HighlightConfiguration highlightConfiguration) {

		this.highlightConfiguration = highlightConfiguration;
	}

	@JsonIgnore
	public void setHighlightConfiguration(
		UnsafeSupplier<HighlightConfiguration, Exception>
			highlightConfigurationUnsafeSupplier) {

		try {
			highlightConfiguration = highlightConfigurationUnsafeSupplier.get();
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
	protected HighlightConfiguration highlightConfiguration;

	@Schema
	@Valid
	public IndexConfiguration getIndexConfiguration() {
		return indexConfiguration;
	}

	public void setIndexConfiguration(IndexConfiguration indexConfiguration) {
		this.indexConfiguration = indexConfiguration;
	}

	@JsonIgnore
	public void setIndexConfiguration(
		UnsafeSupplier<IndexConfiguration, Exception>
			indexConfigurationUnsafeSupplier) {

		try {
			indexConfiguration = indexConfigurationUnsafeSupplier.get();
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
	protected IndexConfiguration indexConfiguration;

	@Schema
	@Valid
	public ParameterConfiguration getParameterConfiguration() {
		return parameterConfiguration;
	}

	public void setParameterConfiguration(
		ParameterConfiguration parameterConfiguration) {

		this.parameterConfiguration = parameterConfiguration;
	}

	@JsonIgnore
	public void setParameterConfiguration(
		UnsafeSupplier<ParameterConfiguration, Exception>
			parameterConfigurationUnsafeSupplier) {

		try {
			parameterConfiguration = parameterConfigurationUnsafeSupplier.get();
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
	protected ParameterConfiguration parameterConfiguration;

	@Schema
	@Valid
	public QueryConfiguration getQueryConfiguration() {
		return queryConfiguration;
	}

	public void setQueryConfiguration(QueryConfiguration queryConfiguration) {
		this.queryConfiguration = queryConfiguration;
	}

	@JsonIgnore
	public void setQueryConfiguration(
		UnsafeSupplier<QueryConfiguration, Exception>
			queryConfigurationUnsafeSupplier) {

		try {
			queryConfiguration = queryConfigurationUnsafeSupplier.get();
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
	protected QueryConfiguration queryConfiguration;

	@Schema
	@Valid
	public Map<String, Object> getSearchContextAttributes() {
		return searchContextAttributes;
	}

	public void setSearchContextAttributes(
		Map<String, Object> searchContextAttributes) {

		this.searchContextAttributes = searchContextAttributes;
	}

	@JsonIgnore
	public void setSearchContextAttributes(
		UnsafeSupplier<Map<String, Object>, Exception>
			searchContextAttributesUnsafeSupplier) {

		try {
			searchContextAttributes =
				searchContextAttributesUnsafeSupplier.get();
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
	protected Map<String, Object> searchContextAttributes;

	@Schema
	@Valid
	public SortConfiguration getSortConfiguration() {
		return sortConfiguration;
	}

	public void setSortConfiguration(SortConfiguration sortConfiguration) {
		this.sortConfiguration = sortConfiguration;
	}

	@JsonIgnore
	public void setSortConfiguration(
		UnsafeSupplier<SortConfiguration, Exception>
			sortConfigurationUnsafeSupplier) {

		try {
			sortConfiguration = sortConfigurationUnsafeSupplier.get();
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
	protected SortConfiguration sortConfiguration;

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

		if (advancedConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"advancedConfiguration\": ");

			sb.append(String.valueOf(advancedConfiguration));
		}

		if (aggregationConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregationConfiguration\": ");

			sb.append(String.valueOf(aggregationConfiguration));
		}

		if (generalConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"generalConfiguration\": ");

			sb.append(String.valueOf(generalConfiguration));
		}

		if (highlightConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"highlightConfiguration\": ");

			sb.append(String.valueOf(highlightConfiguration));
		}

		if (indexConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexConfiguration\": ");

			sb.append(String.valueOf(indexConfiguration));
		}

		if (parameterConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameterConfiguration\": ");

			sb.append(String.valueOf(parameterConfiguration));
		}

		if (queryConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"queryConfiguration\": ");

			sb.append(String.valueOf(queryConfiguration));
		}

		if (searchContextAttributes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"searchContextAttributes\": ");

			sb.append(_toJSON(searchContextAttributes));
		}

		if (sortConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sortConfiguration\": ");

			sb.append(String.valueOf(sortConfiguration));
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