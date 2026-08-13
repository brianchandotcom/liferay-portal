/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Near duplicate clusters of CMS content for the selected space.",
	value = "SimilarityClusterResult"
)
@io.swagger.v3.oas.annotations.media.Schema(
	description = "Near duplicate clusters of CMS content for the selected space."
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SimilarityClusterResult")
public class SimilarityClusterResult implements Serializable {

	public static SimilarityClusterResult toDTO(String json) {
		return ObjectMapperUtil.readValue(SimilarityClusterResult.class, json);
	}

	public static SimilarityClusterResult unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			SimilarityClusterResult.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public SimilarityCluster[] getSimilarityClusters() {
		if (_similarityClustersSupplier != null) {
			similarityClusters = _similarityClustersSupplier.get();

			_similarityClustersSupplier = null;
		}

		return similarityClusters;
	}

	public void setSimilarityClusters(SimilarityCluster[] similarityClusters) {
		this.similarityClusters = similarityClusters;

		_similarityClustersSupplier = null;
	}

	@JsonIgnore
	public void setSimilarityClusters(
		UnsafeSupplier<SimilarityCluster[], Exception>
			similarityClustersUnsafeSupplier) {

		_similarityClustersSupplier = () -> {
			try {
				return similarityClustersUnsafeSupplier.get();
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
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected SimilarityCluster[] similarityClusters;

	@JsonIgnore
	private Supplier<SimilarityCluster[]> _similarityClustersSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The total number of clustered assets, independent of the requested page."
	)
	public Long getTotalCount() {
		if (_totalCountSupplier != null) {
			totalCount = _totalCountSupplier.get();

			_totalCountSupplier = null;
		}

		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;

		_totalCountSupplier = null;
	}

	@JsonIgnore
	public void setTotalCount(
		UnsafeSupplier<Long, Exception> totalCountUnsafeSupplier) {

		_totalCountSupplier = () -> {
			try {
				return totalCountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "The total number of clustered assets, independent of the requested page."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long totalCount;

	@JsonIgnore
	private Supplier<Long> _totalCountSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SimilarityClusterResult)) {
			return false;
		}

		SimilarityClusterResult similarityClusterResult =
			(SimilarityClusterResult)object;

		return Objects.equals(toString(), similarityClusterResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		SimilarityCluster[] similarityClusters = getSimilarityClusters();

		if (similarityClusters != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"similarityClusters\": ");

			sb.append("[");

			for (int i = 0; i < similarityClusters.length; i++) {
				sb.append(String.valueOf(similarityClusters[i]));

				if ((i + 1) < similarityClusters.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Long totalCount = getTotalCount();

		if (totalCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalCount\": ");

			sb.append(totalCount);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.cms.dto.v1_0.SimilarityClusterResult",
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
// LIFERAY-REST-BUILDER-HASH:994249262