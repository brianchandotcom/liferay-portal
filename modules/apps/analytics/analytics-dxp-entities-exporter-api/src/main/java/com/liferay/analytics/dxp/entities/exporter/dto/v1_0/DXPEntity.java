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

package com.liferay.analytics.dxp.entities.exporter.dto.v1_0;

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
 * @author Marcos Martins, Rachael Koestartyo, Riccardo Ferrari
 * @generated
 */
@Generated("")
@GraphQLName("DXPEntity")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DXPEntity")
public class DXPEntity implements Serializable {

	public static DXPEntity toDTO(String json) {
		return ObjectMapperUtil.readValue(DXPEntity.class, json);
	}

	public static DXPEntity unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(DXPEntity.class, json);
	}

	@Schema
	@Valid
	public ExpandoField[] getExpandoFields() {
		return expandoFields;
	}

	public void setExpandoFields(ExpandoField[] expandoFields) {
		this.expandoFields = expandoFields;
	}

	@JsonIgnore
	public void setExpandoFields(
		UnsafeSupplier<ExpandoField[], Exception> expandoFieldsUnsafeSupplier) {

		try {
			expandoFields = expandoFieldsUnsafeSupplier.get();
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
	protected ExpandoField[] expandoFields;

	@Schema
	@Valid
	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	@JsonIgnore
	public void setFields(
		UnsafeSupplier<Field[], Exception> fieldsUnsafeSupplier) {

		try {
			fields = fieldsUnsafeSupplier.get();
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
	protected Field[] fields;

	@Schema
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
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
	protected String id;

	@Schema
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
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
	protected String type;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DXPEntity)) {
			return false;
		}

		DXPEntity dxpEntity = (DXPEntity)object;

		return Objects.equals(toString(), dxpEntity.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (expandoFields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expandoFields\": ");

			sb.append("[");

			for (int i = 0; i < expandoFields.length; i++) {
				sb.append(String.valueOf(expandoFields[i]));

				if ((i + 1) < expandoFields.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (fields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fields\": ");

			sb.append("[");

			for (int i = 0; i < fields.length; i++) {
				sb.append(String.valueOf(fields[i]));

				if ((i + 1) < fields.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(id));

			sb.append("\"");
		}

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(type));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.analytics.dxp.entities.exporter.dto.v1_0.DXPEntity",
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