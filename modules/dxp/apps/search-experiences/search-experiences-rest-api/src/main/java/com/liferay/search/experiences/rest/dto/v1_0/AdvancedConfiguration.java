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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
@GraphQLName("AdvancedConfiguration")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AdvancedConfiguration")
public class AdvancedConfiguration implements Serializable {

	public static AdvancedConfiguration toDTO(String json) {
		return ObjectMapperUtil.readValue(AdvancedConfiguration.class, json);
	}

	public static AdvancedConfiguration unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			AdvancedConfiguration.class, json);
	}

	@Schema
	public String[] getExcludes() {
		return excludes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	@JsonIgnore
	public void setExcludes(
		UnsafeSupplier<String[], Exception> excludesUnsafeSupplier) {

		try {
			excludes = excludesUnsafeSupplier.get();
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
	protected String[] excludes;

	@Schema
	public Boolean getFetchSource() {
		return fetchSource;
	}

	public void setFetchSource(Boolean fetchSource) {
		this.fetchSource = fetchSource;
	}

	@JsonIgnore
	public void setFetchSource(
		UnsafeSupplier<Boolean, Exception> fetchSourceUnsafeSupplier) {

		try {
			fetchSource = fetchSourceUnsafeSupplier.get();
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
	protected Boolean fetchSource;

	@Schema
	public String[] getIncludes() {
		return includes;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}

	@JsonIgnore
	public void setIncludes(
		UnsafeSupplier<String[], Exception> includesUnsafeSupplier) {

		try {
			includes = includesUnsafeSupplier.get();
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
	protected String[] includes;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AdvancedConfiguration)) {
			return false;
		}

		AdvancedConfiguration advancedConfiguration =
			(AdvancedConfiguration)object;

		return Objects.equals(toString(), advancedConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (excludes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"excludes\": ");

			sb.append("[");

			for (int i = 0; i < excludes.length; i++) {
				sb.append("\"");

				sb.append(_escape(excludes[i]));

				sb.append("\"");

				if ((i + 1) < excludes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (fetchSource != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fetchSource\": ");

			sb.append(fetchSource);
		}

		if (includes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"includes\": ");

			sb.append("[");

			for (int i = 0; i < includes.length; i++) {
				sb.append("\"");

				sb.append(_escape(includes[i]));

				sb.append("\"");

				if ((i + 1) < includes.length) {
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
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.AdvancedConfiguration",
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