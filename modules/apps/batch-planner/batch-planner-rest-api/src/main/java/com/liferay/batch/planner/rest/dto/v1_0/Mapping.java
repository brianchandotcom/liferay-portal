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

package com.liferay.batch.planner.rest.dto.v1_0;

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
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
@GraphQLName("Mapping")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Mapping")
public class Mapping implements Serializable {

	public static Mapping toDTO(String json) {
		return ObjectMapperUtil.readValue(Mapping.class, json);
	}

	@Schema
	public String getExternalFieldName() {
		return externalFieldName;
	}

	public void setExternalFieldName(String externalFieldName) {
		this.externalFieldName = externalFieldName;
	}

	@JsonIgnore
	public void setExternalFieldName(
		UnsafeSupplier<String, Exception> externalFieldNameUnsafeSupplier) {

		try {
			externalFieldName = externalFieldNameUnsafeSupplier.get();
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
	protected String externalFieldName;

	@Schema
	public String getExternalFieldType() {
		return externalFieldType;
	}

	public void setExternalFieldType(String externalFieldType) {
		this.externalFieldType = externalFieldType;
	}

	@JsonIgnore
	public void setExternalFieldType(
		UnsafeSupplier<String, Exception> externalFieldTypeUnsafeSupplier) {

		try {
			externalFieldType = externalFieldTypeUnsafeSupplier.get();
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
	protected String externalFieldType;

	@Schema
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
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
	protected Long id;

	@Schema
	public String getInternalFieldName() {
		return internalFieldName;
	}

	public void setInternalFieldName(String internalFieldName) {
		this.internalFieldName = internalFieldName;
	}

	@JsonIgnore
	public void setInternalFieldName(
		UnsafeSupplier<String, Exception> internalFieldNameUnsafeSupplier) {

		try {
			internalFieldName = internalFieldNameUnsafeSupplier.get();
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
	protected String internalFieldName;

	@Schema
	public String getInternalFieldType() {
		return internalFieldType;
	}

	public void setInternalFieldType(String internalFieldType) {
		this.internalFieldType = internalFieldType;
	}

	@JsonIgnore
	public void setInternalFieldType(
		UnsafeSupplier<String, Exception> internalFieldTypeUnsafeSupplier) {

		try {
			internalFieldType = internalFieldTypeUnsafeSupplier.get();
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
	protected String internalFieldType;

	@Schema
	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	@JsonIgnore
	public void setPlanId(
		UnsafeSupplier<Long, Exception> planIdUnsafeSupplier) {

		try {
			planId = planIdUnsafeSupplier.get();
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
	protected Long planId;

	@Schema
	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@JsonIgnore
	public void setScript(
		UnsafeSupplier<String, Exception> scriptUnsafeSupplier) {

		try {
			script = scriptUnsafeSupplier.get();
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
	protected String script;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Mapping)) {
			return false;
		}

		Mapping mapping = (Mapping)object;

		return Objects.equals(toString(), mapping.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (externalFieldName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalFieldName\": ");

			sb.append("\"");

			sb.append(_escape(externalFieldName));

			sb.append("\"");
		}

		if (externalFieldType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalFieldType\": ");

			sb.append("\"");

			sb.append(_escape(externalFieldType));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (internalFieldName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"internalFieldName\": ");

			sb.append("\"");

			sb.append(_escape(internalFieldName));

			sb.append("\"");
		}

		if (internalFieldType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"internalFieldType\": ");

			sb.append("\"");

			sb.append(_escape(internalFieldType));

			sb.append("\"");
		}

		if (planId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"planId\": ");

			sb.append(planId);
		}

		if (script != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"script\": ");

			sb.append("\"");

			sb.append(_escape(script));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.batch.planner.rest.dto.v1_0.Mapping",
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