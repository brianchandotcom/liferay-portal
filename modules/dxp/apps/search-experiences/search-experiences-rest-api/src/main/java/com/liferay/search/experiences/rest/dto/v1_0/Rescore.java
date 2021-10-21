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
@GraphQLName("Rescore")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Rescore")
public class Rescore implements Serializable {

	public static Rescore toDTO(String json) {
		return ObjectMapperUtil.readValue(Rescore.class, json);
	}

	public static Rescore unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Rescore.class, json);
	}

	@Schema
	@Valid
	public Object getQuery() {
		return query;
	}

	public void setQuery(Object query) {
		this.query = query;
	}

	@JsonIgnore
	public void setQuery(
		UnsafeSupplier<Object, Exception> queryUnsafeSupplier) {

		try {
			query = queryUnsafeSupplier.get();
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
	protected Object query;

	@Schema
	@Valid
	public Float getQueryWeight() {
		return queryWeight;
	}

	public void setQueryWeight(Float queryWeight) {
		this.queryWeight = queryWeight;
	}

	@JsonIgnore
	public void setQueryWeight(
		UnsafeSupplier<Float, Exception> queryWeightUnsafeSupplier) {

		try {
			queryWeight = queryWeightUnsafeSupplier.get();
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
	protected Float queryWeight;

	@Schema
	@Valid
	public Float getRescoreQueryWeight() {
		return rescoreQueryWeight;
	}

	public void setRescoreQueryWeight(Float rescoreQueryWeight) {
		this.rescoreQueryWeight = rescoreQueryWeight;
	}

	@JsonIgnore
	public void setRescoreQueryWeight(
		UnsafeSupplier<Float, Exception> rescoreQueryWeightUnsafeSupplier) {

		try {
			rescoreQueryWeight = rescoreQueryWeightUnsafeSupplier.get();
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
	protected Float rescoreQueryWeight;

	@Schema
	public String getScoreMode() {
		return scoreMode;
	}

	public void setScoreMode(String scoreMode) {
		this.scoreMode = scoreMode;
	}

	@JsonIgnore
	public void setScoreMode(
		UnsafeSupplier<String, Exception> scoreModeUnsafeSupplier) {

		try {
			scoreMode = scoreModeUnsafeSupplier.get();
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
	protected String scoreMode;

	@Schema
	public Integer getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(Integer windowSize) {
		this.windowSize = windowSize;
	}

	@JsonIgnore
	public void setWindowSize(
		UnsafeSupplier<Integer, Exception> windowSizeUnsafeSupplier) {

		try {
			windowSize = windowSizeUnsafeSupplier.get();
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
	protected Integer windowSize;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Rescore)) {
			return false;
		}

		Rescore rescore = (Rescore)object;

		return Objects.equals(toString(), rescore.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (query != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"query\": ");

			sb.append(String.valueOf(query));
		}

		if (queryWeight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"queryWeight\": ");

			sb.append(queryWeight);
		}

		if (rescoreQueryWeight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rescoreQueryWeight\": ");

			sb.append(rescoreQueryWeight);
		}

		if (scoreMode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"scoreMode\": ");

			sb.append("\"");

			sb.append(_escape(scoreMode));

			sb.append("\"");
		}

		if (windowSize != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"windowSize\": ");

			sb.append(windowSize);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.Rescore",
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