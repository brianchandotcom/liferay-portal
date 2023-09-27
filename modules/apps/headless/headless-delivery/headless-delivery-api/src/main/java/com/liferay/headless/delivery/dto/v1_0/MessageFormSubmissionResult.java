/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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
@GraphQLName(
	description = "Represents a definition of a submission result of type message.",
	value = "MessageFormSubmissionResult"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "MessageFormSubmissionResult")
public class MessageFormSubmissionResult implements Serializable {

	public static MessageFormSubmissionResult toDTO(String json) {
		return ObjectMapperUtil.readValue(
			MessageFormSubmissionResult.class, json);
	}

	public static MessageFormSubmissionResult unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			MessageFormSubmissionResult.class, json);
	}

	@Schema(description = "The localized submission of message type.")
	@Valid
	public FragmentInlineValue getMessage() {
		return message;
	}

	public void setMessage(FragmentInlineValue message) {
		this.message = message;
	}

	@JsonIgnore
	public void setMessage(
		UnsafeSupplier<FragmentInlineValue, Exception> messageUnsafeSupplier) {

		try {
			message = messageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The localized submission of message type.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FragmentInlineValue message;

	@Schema(description = "The message form submission type (embedded, none).")
	@Valid
	public MessageType getMessageType() {
		return messageType;
	}

	@JsonIgnore
	public String getMessageTypeAsString() {
		if (messageType == null) {
			return null;
		}

		return messageType.toString();
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	@JsonIgnore
	public void setMessageType(
		UnsafeSupplier<MessageType, Exception> messageTypeUnsafeSupplier) {

		try {
			messageType = messageTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The message form submission type (embedded, none)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MessageType messageType;

	@Schema
	public Boolean getShowNotification() {
		return showNotification;
	}

	public void setShowNotification(Boolean showNotification) {
		this.showNotification = showNotification;
	}

	@JsonIgnore
	public void setShowNotification(
		UnsafeSupplier<Boolean, Exception> showNotificationUnsafeSupplier) {

		try {
			showNotification = showNotificationUnsafeSupplier.get();
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
	protected Boolean showNotification;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MessageFormSubmissionResult)) {
			return false;
		}

		MessageFormSubmissionResult messageFormSubmissionResult =
			(MessageFormSubmissionResult)object;

		return Objects.equals(
			toString(), messageFormSubmissionResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (message != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"message\": ");

			sb.append(String.valueOf(message));
		}

		if (messageType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messageType\": ");

			sb.append("\"");

			sb.append(messageType);

			sb.append("\"");
		}

		if (showNotification != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showNotification\": ");

			sb.append(showNotification);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.MessageFormSubmissionResult",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("MessageType")
	public static enum MessageType {

		EMBEDDED("Embedded"), NONE("None");

		@JsonCreator
		public static MessageType create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (MessageType messageType : values()) {
				if (Objects.equals(messageType.getValue(), value)) {
					return messageType;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private MessageType(String value) {
			_value = value;
		}

		private final String _value;

	}

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