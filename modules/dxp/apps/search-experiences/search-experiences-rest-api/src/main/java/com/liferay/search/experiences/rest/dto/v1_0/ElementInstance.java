/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@GraphQLName("ElementInstance")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ElementInstance")
public class ElementInstance implements Serializable {

	public static ElementInstance toDTO(String json) {
		return ObjectMapperUtil.readValue(ElementInstance.class, json);
	}

	public static ElementInstance unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ElementInstance.class, json);
	}

	@Schema
	@Valid
	public Configuration getConfigurationEntry() {
		return configurationEntry;
	}

	public void setConfigurationEntry(Configuration configurationEntry) {
		this.configurationEntry = configurationEntry;
	}

	@JsonIgnore
	public void setConfigurationEntry(
		UnsafeSupplier<Configuration, Exception>
			configurationEntryUnsafeSupplier) {

		try {
			configurationEntry = configurationEntryUnsafeSupplier.get();
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
	protected Configuration configurationEntry;

	@Schema
	@Valid
	public SXPElement getSxpElement() {
		return sxpElement;
	}

	public void setSxpElement(SXPElement sxpElement) {
		this.sxpElement = sxpElement;
	}

	@JsonIgnore
	public void setSxpElement(
		UnsafeSupplier<SXPElement, Exception> sxpElementUnsafeSupplier) {

		try {
			sxpElement = sxpElementUnsafeSupplier.get();
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
	protected SXPElement sxpElement;

	@Schema
	public Long getSxpElementId() {
		return sxpElementId;
	}

	public void setSxpElementId(Long sxpElementId) {
		this.sxpElementId = sxpElementId;
	}

	@JsonIgnore
	public void setSxpElementId(
		UnsafeSupplier<Long, Exception> sxpElementIdUnsafeSupplier) {

		try {
			sxpElementId = sxpElementIdUnsafeSupplier.get();
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
	protected Long sxpElementId;

	@Schema
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<Integer, Exception> typeUnsafeSupplier) {
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
	protected Integer type;

	@Schema
	@Valid
	public Map<String, Object> getUiConfigurationValues() {
		return uiConfigurationValues;
	}

	public void setUiConfigurationValues(
		Map<String, Object> uiConfigurationValues) {

		this.uiConfigurationValues = uiConfigurationValues;
	}

	@JsonIgnore
	public void setUiConfigurationValues(
		UnsafeSupplier<Map<String, Object>, Exception>
			uiConfigurationValuesUnsafeSupplier) {

		try {
			uiConfigurationValues = uiConfigurationValuesUnsafeSupplier.get();
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
	protected Map<String, Object> uiConfigurationValues;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ElementInstance)) {
			return false;
		}

		ElementInstance elementInstance = (ElementInstance)object;

		return Objects.equals(toString(), elementInstance.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (configurationEntry != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"configurationEntry\": ");

			sb.append(String.valueOf(configurationEntry));
		}

		if (sxpElement != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sxpElement\": ");

			sb.append(String.valueOf(sxpElement));
		}

		if (sxpElementId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sxpElementId\": ");

			sb.append(sxpElementId);
		}

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append(type);
		}

		if (uiConfigurationValues != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uiConfigurationValues\": ");

			sb.append(_toJSON(uiConfigurationValues));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.ElementInstance",
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

	private Map<String, Serializable> _extendedProperties;

}