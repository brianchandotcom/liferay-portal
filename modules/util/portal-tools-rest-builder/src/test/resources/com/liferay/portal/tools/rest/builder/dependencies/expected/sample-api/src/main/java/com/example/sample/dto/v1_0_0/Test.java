/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.dto.v1_0_0;

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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Test Component to test the REST Builder support for advanced features",
	value = "Test"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Test")
public class Test implements Serializable {

	public static Test toDTO(String json) {
		return ObjectMapperUtil.readValue(Test.class, json);
	}

	public static Test unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Test.class, json);
	}

	@Schema
	public Long getId() {
		if (_idSupplier != null) {
			id = _idSupplier.get();

			_idSupplier = null;
		}

		return id;
	}

	public void setId(Long id) {
		this.id = id;

		_idSupplier = null;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		_idSupplier = () -> {
			try {
				return idUnsafeSupplier.get();
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
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@Schema
	public String getJsonProperty() {
		if (_jsonPropertySupplier != null) {
			jsonProperty = _jsonPropertySupplier.get();

			_jsonPropertySupplier = null;
		}

		return jsonProperty;
	}

	public void setJsonProperty(String jsonProperty) {
		this.jsonProperty = jsonProperty;

		_jsonPropertySupplier = null;
	}

	@JsonIgnore
	public void setJsonProperty(
		UnsafeSupplier<String, Exception> jsonPropertyUnsafeSupplier) {

		_jsonPropertySupplier = () -> {
			try {
				return jsonPropertyUnsafeSupplier.get();
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
	@XmlElement(name = "xmlProperty")
	protected String jsonProperty;

	@JsonIgnore
	private Supplier<String> _jsonPropertySupplier;

	@Schema
	public String getPropertyWithHyphens() {
		if (_propertyWithHyphensSupplier != null) {
			propertyWithHyphens = _propertyWithHyphensSupplier.get();

			_propertyWithHyphensSupplier = null;
		}

		return propertyWithHyphens;
	}

	public void setPropertyWithHyphens(String propertyWithHyphens) {
		this.propertyWithHyphens = propertyWithHyphens;

		_propertyWithHyphensSupplier = null;
	}

	@JsonIgnore
	public void setPropertyWithHyphens(
		UnsafeSupplier<String, Exception> propertyWithHyphensUnsafeSupplier) {

		_propertyWithHyphensSupplier = () -> {
			try {
				return propertyWithHyphensUnsafeSupplier.get();
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
	@JsonProperty(
		access = JsonProperty.Access.READ_WRITE, value = "property-with-hyphens"
	)
	protected String propertyWithHyphens;

	@JsonIgnore
	private Supplier<String> _propertyWithHyphensSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Test)) {
			return false;
		}

		Test test = (Test)object;

		return Objects.equals(toString(), test.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		String jsonProperty = getJsonProperty();

		if (jsonProperty != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jsonProperty\": ");

			sb.append("\"");

			sb.append(_escape(jsonProperty));

			sb.append("\"");
		}

		String propertyWithHyphens = getPropertyWithHyphens();

		if (propertyWithHyphens != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"property-with-hyphens\": ");

			sb.append("\"");

			sb.append(_escape(propertyWithHyphens));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.example.sample.dto.v1_0_0.Test",
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