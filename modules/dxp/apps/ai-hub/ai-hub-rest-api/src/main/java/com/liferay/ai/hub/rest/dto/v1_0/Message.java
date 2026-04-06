/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
@GraphQLName("Message")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Message")
public class Message implements Serializable {

	public static Message toDTO(String json) {
		return ObjectMapperUtil.readValue(Message.class, json);
	}

	public static Message unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Message.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Chat getChat() {
		if (_chatSupplier != null) {
			chat = _chatSupplier.get();

			_chatSupplier = null;
		}

		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;

		_chatSupplier = null;
	}

	@JsonIgnore
	public void setChat(UnsafeSupplier<Chat, Exception> chatUnsafeSupplier) {
		_chatSupplier = () -> {
			try {
				return chatUnsafeSupplier.get();
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
	protected Chat chat;

	@JsonIgnore
	private Supplier<Chat> _chatSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Map<String, ?> getContext() {
		if (_contextSupplier != null) {
			context = _contextSupplier.get();

			_contextSupplier = null;
		}

		return context;
	}

	public void setContext(Map<String, ?> context) {
		this.context = context;

		_contextSupplier = null;
	}

	@JsonIgnore
	public void setContext(
		UnsafeSupplier<Map<String, ?>, Exception> contextUnsafeSupplier) {

		_contextSupplier = () -> {
			try {
				return contextUnsafeSupplier.get();
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
	protected Map<String, ?> context;

	@JsonIgnore
	private Supplier<Map<String, ?>> _contextSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("instructionDefinitionScope")
	@Valid
	public InstructionDefinitionScope getInstructionDefinitionScope() {
		if (_instructionDefinitionScopeSupplier != null) {
			instructionDefinitionScope =
				_instructionDefinitionScopeSupplier.get();

			_instructionDefinitionScopeSupplier = null;
		}

		return instructionDefinitionScope;
	}

	@JsonIgnore
	public String getInstructionDefinitionScopeAsString() {
		InstructionDefinitionScope instructionDefinitionScope =
			getInstructionDefinitionScope();

		if (instructionDefinitionScope == null) {
			return null;
		}

		return instructionDefinitionScope.toString();
	}

	public void setInstructionDefinitionScope(
		InstructionDefinitionScope instructionDefinitionScope) {

		this.instructionDefinitionScope = instructionDefinitionScope;

		_instructionDefinitionScopeSupplier = null;
	}

	@JsonIgnore
	public void setInstructionDefinitionScope(
		UnsafeSupplier<InstructionDefinitionScope, Exception>
			instructionDefinitionScopeUnsafeSupplier) {

		_instructionDefinitionScopeSupplier = () -> {
			try {
				return instructionDefinitionScopeUnsafeSupplier.get();
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
	protected InstructionDefinitionScope instructionDefinitionScope;

	@JsonIgnore
	private Supplier<InstructionDefinitionScope>
		_instructionDefinitionScopeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getText() {
		if (_textSupplier != null) {
			text = _textSupplier.get();

			_textSupplier = null;
		}

		return text;
	}

	public void setText(String text) {
		this.text = text;

		_textSupplier = null;
	}

	@JsonIgnore
	public void setText(UnsafeSupplier<String, Exception> textUnsafeSupplier) {
		_textSupplier = () -> {
			try {
				return textUnsafeSupplier.get();
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
	protected String text;

	@JsonIgnore
	private Supplier<String> _textSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Message)) {
			return false;
		}

		Message message = (Message)object;

		return Objects.equals(toString(), message.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Chat chat = getChat();

		if (chat != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"chat\": ");

			sb.append(String.valueOf(chat));
		}

		Map<String, ?> context = getContext();

		if (context != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"context\": ");

			sb.append(_toJSON(context));
		}

		InstructionDefinitionScope instructionDefinitionScope =
			getInstructionDefinitionScope();

		if (instructionDefinitionScope != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instructionDefinitionScope\": ");

			sb.append("\"");
			sb.append(instructionDefinitionScope);
			sb.append("\"");
		}

		String text = getText();

		if (text != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			sb.append("\"");

			sb.append(_escape(text));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.ai.hub.rest.dto.v1_0.Message",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("InstructionDefinitionScope")
	public static enum InstructionDefinitionScope {

		CLICK_TO_CHAT("clickToChat"), CMS("cms"), EVERYWHERE("everywhere");

		@JsonCreator
		public static InstructionDefinitionScope create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (InstructionDefinitionScope instructionDefinitionScope :
					values()) {

				if (Objects.equals(
						instructionDefinitionScope.getValue(), value)) {

					return instructionDefinitionScope;
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

		private InstructionDefinitionScope(String value) {
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