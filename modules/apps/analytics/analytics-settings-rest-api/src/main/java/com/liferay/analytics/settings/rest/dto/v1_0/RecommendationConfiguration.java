/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.dto.v1_0;

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
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
@GraphQLName("RecommendationConfiguration")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "RecommendationConfiguration")
public class RecommendationConfiguration implements Serializable {

	public static RecommendationConfiguration toDTO(String json) {
		return ObjectMapperUtil.readValue(
			RecommendationConfiguration.class, json);
	}

	public static RecommendationConfiguration unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			RecommendationConfiguration.class, json);
	}

	@Schema
	public Boolean getContentRecommenderMostPopularItemsEnabled() {
		if (_contentRecommenderMostPopularItemsEnabledSupplier != null) {
			contentRecommenderMostPopularItemsEnabled =
				_contentRecommenderMostPopularItemsEnabledSupplier.get();

			_contentRecommenderMostPopularItemsEnabledSupplier = null;
		}

		return contentRecommenderMostPopularItemsEnabled;
	}

	public void setContentRecommenderMostPopularItemsEnabled(
		Boolean contentRecommenderMostPopularItemsEnabled) {

		this.contentRecommenderMostPopularItemsEnabled =
			contentRecommenderMostPopularItemsEnabled;

		_contentRecommenderMostPopularItemsEnabledSupplier = null;
	}

	@JsonIgnore
	public void setContentRecommenderMostPopularItemsEnabled(
		UnsafeSupplier<Boolean, Exception>
			contentRecommenderMostPopularItemsEnabledUnsafeSupplier) {

		_contentRecommenderMostPopularItemsEnabledSupplier = () -> {
			try {
				return contentRecommenderMostPopularItemsEnabledUnsafeSupplier.
					get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean contentRecommenderMostPopularItemsEnabled;

	@JsonIgnore
	private Supplier<Boolean>
		_contentRecommenderMostPopularItemsEnabledSupplier;

	@Schema
	public Boolean getContentRecommenderUserPersonalizationEnabled() {
		if (_contentRecommenderUserPersonalizationEnabledSupplier != null) {
			contentRecommenderUserPersonalizationEnabled =
				_contentRecommenderUserPersonalizationEnabledSupplier.get();

			_contentRecommenderUserPersonalizationEnabledSupplier = null;
		}

		return contentRecommenderUserPersonalizationEnabled;
	}

	public void setContentRecommenderUserPersonalizationEnabled(
		Boolean contentRecommenderUserPersonalizationEnabled) {

		this.contentRecommenderUserPersonalizationEnabled =
			contentRecommenderUserPersonalizationEnabled;

		_contentRecommenderUserPersonalizationEnabledSupplier = null;
	}

	@JsonIgnore
	public void setContentRecommenderUserPersonalizationEnabled(
		UnsafeSupplier<Boolean, Exception>
			contentRecommenderUserPersonalizationEnabledUnsafeSupplier) {

		_contentRecommenderUserPersonalizationEnabledSupplier = () -> {
			try {
				return contentRecommenderUserPersonalizationEnabledUnsafeSupplier.
					get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean contentRecommenderUserPersonalizationEnabled;

	@JsonIgnore
	private Supplier<Boolean>
		_contentRecommenderUserPersonalizationEnabledSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RecommendationConfiguration)) {
			return false;
		}

		RecommendationConfiguration recommendationConfiguration =
			(RecommendationConfiguration)object;

		return Objects.equals(
			toString(), recommendationConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Boolean contentRecommenderMostPopularItemsEnabled =
			getContentRecommenderMostPopularItemsEnabled();

		if (contentRecommenderMostPopularItemsEnabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentRecommenderMostPopularItemsEnabled\": ");

			sb.append(contentRecommenderMostPopularItemsEnabled);
		}

		Boolean contentRecommenderUserPersonalizationEnabled =
			getContentRecommenderUserPersonalizationEnabled();

		if (contentRecommenderUserPersonalizationEnabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentRecommenderUserPersonalizationEnabled\": ");

			sb.append(contentRecommenderUserPersonalizationEnabled);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.analytics.settings.rest.dto.v1_0.RecommendationConfiguration",
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
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
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