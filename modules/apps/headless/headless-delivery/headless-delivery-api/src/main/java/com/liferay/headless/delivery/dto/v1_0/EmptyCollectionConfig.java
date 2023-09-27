/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("EmptyCollectionConfig")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "EmptyCollectionConfig")
public class EmptyCollectionConfig implements Serializable {

	public static EmptyCollectionConfig toDTO(String json) {
		return ObjectMapperUtil.readValue(EmptyCollectionConfig.class, json);
	}

	public static EmptyCollectionConfig unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			EmptyCollectionConfig.class, json);
	}

	@Schema(
		description = "Whether to display a message when the collection is empty or no results match the applied filters (true by default)."
	)
	public Boolean getDisplayMessage() {
		return displayMessage;
	}

	public void setDisplayMessage(Boolean displayMessage) {
		this.displayMessage = displayMessage;
	}

	@JsonIgnore
	public void setDisplayMessage(
		UnsafeSupplier<Boolean, Exception> displayMessageUnsafeSupplier) {

		try {
			displayMessage = displayMessageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Whether to display a message when the collection is empty or no results match the applied filters (true by default)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean displayMessage;

	@Schema(
		description = "The localized message to display when the collection is empty or no results match the applied filters ('No Results Found' by default)."
	)
	@Valid
	public Map<String, String> getMessage_i18n() {
		return message_i18n;
	}

	public void setMessage_i18n(Map<String, String> message_i18n) {
		this.message_i18n = message_i18n;
	}

	@JsonIgnore
	public void setMessage_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			message_i18nUnsafeSupplier) {

		try {
			message_i18n = message_i18nUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The localized message to display when the collection is empty or no results match the applied filters ('No Results Found' by default)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> message_i18n;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EmptyCollectionConfig)) {
			return false;
		}

		EmptyCollectionConfig emptyCollectionConfig =
			(EmptyCollectionConfig)object;

		return Objects.equals(toString(), emptyCollectionConfig.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (displayMessage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayMessage\": ");

			sb.append(displayMessage);
		}

		if (message_i18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"message_i18n\": ");

			sb.append(_toJSON(message_i18n));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.EmptyCollectionConfig",
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