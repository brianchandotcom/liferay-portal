/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
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
@GraphQLName("Report")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {
		"agentDefinitionExternalReferenceCodes", "feedback", "surface"
	}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Report")
public class Report implements Serializable {

	public static Report toDTO(String json) {
		return ObjectMapperUtil.readValue(Report.class, json);
	}

	public static Report unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Report.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public String[] getAgentDefinitionExternalReferenceCodes() {
		if (_agentDefinitionExternalReferenceCodesSupplier != null) {
			agentDefinitionExternalReferenceCodes =
				_agentDefinitionExternalReferenceCodesSupplier.get();

			_agentDefinitionExternalReferenceCodesSupplier = null;
		}

		return agentDefinitionExternalReferenceCodes;
	}

	public void setAgentDefinitionExternalReferenceCodes(
		String[] agentDefinitionExternalReferenceCodes) {

		this.agentDefinitionExternalReferenceCodes =
			agentDefinitionExternalReferenceCodes;

		_agentDefinitionExternalReferenceCodesSupplier = null;
	}

	@JsonIgnore
	public void setAgentDefinitionExternalReferenceCodes(
		UnsafeSupplier<String[], Exception>
			agentDefinitionExternalReferenceCodesUnsafeSupplier) {

		_agentDefinitionExternalReferenceCodesSupplier = () -> {
			try {
				return agentDefinitionExternalReferenceCodesUnsafeSupplier.
					get();
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
	@NotNull
	protected String[] agentDefinitionExternalReferenceCodes;

	@JsonIgnore
	private Supplier<String[]> _agentDefinitionExternalReferenceCodesSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getChatbotExternalReferenceCode() {
		if (_chatbotExternalReferenceCodeSupplier != null) {
			chatbotExternalReferenceCode =
				_chatbotExternalReferenceCodeSupplier.get();

			_chatbotExternalReferenceCodeSupplier = null;
		}

		return chatbotExternalReferenceCode;
	}

	public void setChatbotExternalReferenceCode(
		String chatbotExternalReferenceCode) {

		this.chatbotExternalReferenceCode = chatbotExternalReferenceCode;

		_chatbotExternalReferenceCodeSupplier = null;
	}

	@JsonIgnore
	public void setChatbotExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			chatbotExternalReferenceCodeUnsafeSupplier) {

		_chatbotExternalReferenceCodeSupplier = () -> {
			try {
				return chatbotExternalReferenceCodeUnsafeSupplier.get();
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
	protected String chatbotExternalReferenceCode;

	@JsonIgnore
	private Supplier<String> _chatbotExternalReferenceCodeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Date getDateCreated() {
		if (_dateCreatedSupplier != null) {
			dateCreated = _dateCreatedSupplier.get();

			_dateCreatedSupplier = null;
		}

		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;

		_dateCreatedSupplier = null;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		_dateCreatedSupplier = () -> {
			try {
				return dateCreatedUnsafeSupplier.get();
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
	protected Date dateCreated;

	@JsonIgnore
	private Supplier<Date> _dateCreatedSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getExternalReferenceCode() {
		if (_externalReferenceCodeSupplier != null) {
			externalReferenceCode = _externalReferenceCodeSupplier.get();

			_externalReferenceCodeSupplier = null;
		}

		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;

		_externalReferenceCodeSupplier = null;
	}

	@JsonIgnore
	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		_externalReferenceCodeSupplier = () -> {
			try {
				return externalReferenceCodeUnsafeSupplier.get();
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
	protected String externalReferenceCode;

	@JsonIgnore
	private Supplier<String> _externalReferenceCodeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getFeedback() {
		if (_feedbackSupplier != null) {
			feedback = _feedbackSupplier.get();

			_feedbackSupplier = null;
		}

		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;

		_feedbackSupplier = null;
	}

	@JsonIgnore
	public void setFeedback(
		UnsafeSupplier<String, Exception> feedbackUnsafeSupplier) {

		_feedbackSupplier = () -> {
			try {
				return feedbackUnsafeSupplier.get();
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
	@NotEmpty
	protected String feedback;

	@JsonIgnore
	private Supplier<String> _feedbackSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
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
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getLevel() {
		if (_levelSupplier != null) {
			level = _levelSupplier.get();

			_levelSupplier = null;
		}

		return level;
	}

	public void setLevel(String level) {
		this.level = level;

		_levelSupplier = null;
	}

	@JsonIgnore
	public void setLevel(
		UnsafeSupplier<String, Exception> levelUnsafeSupplier) {

		_levelSupplier = () -> {
			try {
				return levelUnsafeSupplier.get();
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
	protected String level;

	@JsonIgnore
	private Supplier<String> _levelSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getReason() {
		if (_reasonSupplier != null) {
			reason = _reasonSupplier.get();

			_reasonSupplier = null;
		}

		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;

		_reasonSupplier = null;
	}

	@JsonIgnore
	public void setReason(
		UnsafeSupplier<String, Exception> reasonUnsafeSupplier) {

		_reasonSupplier = () -> {
			try {
				return reasonUnsafeSupplier.get();
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
	protected String reason;

	@JsonIgnore
	private Supplier<String> _reasonSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getSurface() {
		if (_surfaceSupplier != null) {
			surface = _surfaceSupplier.get();

			_surfaceSupplier = null;
		}

		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;

		_surfaceSupplier = null;
	}

	@JsonIgnore
	public void setSurface(
		UnsafeSupplier<String, Exception> surfaceUnsafeSupplier) {

		_surfaceSupplier = () -> {
			try {
				return surfaceUnsafeSupplier.get();
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
	@NotEmpty
	protected String surface;

	@JsonIgnore
	private Supplier<String> _surfaceSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getUserMessage() {
		if (_userMessageSupplier != null) {
			userMessage = _userMessageSupplier.get();

			_userMessageSupplier = null;
		}

		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;

		_userMessageSupplier = null;
	}

	@JsonIgnore
	public void setUserMessage(
		UnsafeSupplier<String, Exception> userMessageUnsafeSupplier) {

		_userMessageSupplier = () -> {
			try {
				return userMessageUnsafeSupplier.get();
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
	protected String userMessage;

	@JsonIgnore
	private Supplier<String> _userMessageSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Report)) {
			return false;
		}

		Report report = (Report)object;

		return Objects.equals(toString(), report.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		String[] agentDefinitionExternalReferenceCodes =
			getAgentDefinitionExternalReferenceCodes();

		if (agentDefinitionExternalReferenceCodes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"agentDefinitionExternalReferenceCodes\": ");

			sb.append("[");

			for (int i = 0; i < agentDefinitionExternalReferenceCodes.length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(agentDefinitionExternalReferenceCodes[i]));

				sb.append("\"");

				if ((i + 1) < agentDefinitionExternalReferenceCodes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		String chatbotExternalReferenceCode = getChatbotExternalReferenceCode();

		if (chatbotExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"chatbotExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(chatbotExternalReferenceCode));

			sb.append("\"");
		}

		Date dateCreated = getDateCreated();

		if (dateCreated != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateCreated));

			sb.append("\"");
		}

		String externalReferenceCode = getExternalReferenceCode();

		if (externalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceCode));

			sb.append("\"");
		}

		String feedback = getFeedback();

		if (feedback != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"feedback\": ");

			sb.append("\"");

			sb.append(_escape(feedback));

			sb.append("\"");
		}

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		String level = getLevel();

		if (level != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"level\": ");

			sb.append("\"");

			sb.append(_escape(level));

			sb.append("\"");
		}

		String reason = getReason();

		if (reason != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reason\": ");

			sb.append("\"");

			sb.append(_escape(reason));

			sb.append("\"");
		}

		String surface = getSurface();

		if (surface != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"surface\": ");

			sb.append("\"");

			sb.append(_escape(surface));

			sb.append("\"");
		}

		String userMessage = getUserMessage();

		if (userMessage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userMessage\": ");

			sb.append("\"");

			sb.append(_escape(userMessage));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.ai.hub.rest.dto.v1_0.Report",
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
// LIFERAY-REST-BUILDER-HASH:1763177600