/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.dto.v1_0;

import com.liferay.ai.hub.rest.client.function.UnsafeSupplier;
import com.liferay.ai.hub.rest.client.serdes.v1_0.MessageSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class Message implements Cloneable, Serializable {

	public static Message toDTO(String json) {
		return MessageSerDes.toDTO(json);
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public void setChat(UnsafeSupplier<Chat, Exception> chatUnsafeSupplier) {
		try {
			chat = chatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Chat chat;

	public String getChatbotExternalReferenceCode() {
		return chatbotExternalReferenceCode;
	}

	public void setChatbotExternalReferenceCode(
		String chatbotExternalReferenceCode) {

		this.chatbotExternalReferenceCode = chatbotExternalReferenceCode;
	}

	public void setChatbotExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			chatbotExternalReferenceCodeUnsafeSupplier) {

		try {
			chatbotExternalReferenceCode =
				chatbotExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String chatbotExternalReferenceCode;

	public Map<String, ?> getContext() {
		return context;
	}

	public void setContext(Map<String, ?> context) {
		this.context = context;
	}

	public void setContext(
		UnsafeSupplier<Map<String, ?>, Exception> contextUnsafeSupplier) {

		try {
			context = contextUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ?> context;

	public InstructionDefinitionScope getInstructionDefinitionScope() {
		return instructionDefinitionScope;
	}

	public String getInstructionDefinitionScopeAsString() {
		if (instructionDefinitionScope == null) {
			return null;
		}

		return instructionDefinitionScope.toString();
	}

	public void setInstructionDefinitionScope(
		InstructionDefinitionScope instructionDefinitionScope) {

		this.instructionDefinitionScope = instructionDefinitionScope;
	}

	public void setInstructionDefinitionScope(
		UnsafeSupplier<InstructionDefinitionScope, Exception>
			instructionDefinitionScopeUnsafeSupplier) {

		try {
			instructionDefinitionScope =
				instructionDefinitionScopeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected InstructionDefinitionScope instructionDefinitionScope;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setText(UnsafeSupplier<String, Exception> textUnsafeSupplier) {
		try {
			text = textUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String text;

	@Override
	public Message clone() throws CloneNotSupportedException {
		return (Message)super.clone();
	}

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
		return MessageSerDes.toJSON(this);
	}

	public static enum InstructionDefinitionScope {

		CLICK_TO_CHAT("clickToChat"), CMS("cms"), EVERYWHERE("everywhere");

		public static InstructionDefinitionScope create(String value) {
			for (InstructionDefinitionScope instructionDefinitionScope :
					values()) {

				if (Objects.equals(
						instructionDefinitionScope.getValue(), value) ||
					Objects.equals(instructionDefinitionScope.name(), value)) {

					return instructionDefinitionScope;
				}
			}

			return null;
		}

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

}
// LIFERAY-REST-BUILDER-HASH:-1968572402