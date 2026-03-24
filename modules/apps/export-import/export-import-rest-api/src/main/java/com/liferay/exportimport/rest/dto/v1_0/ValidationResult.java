/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.dto.v1_0;

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

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
@GraphQLName("ValidationResult")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ValidationResult")
public class ValidationResult implements Serializable {

	public static ValidationResult toDTO(String json) {
		return ObjectMapperUtil.readValue(ValidationResult.class, json);
	}

	public static ValidationResult unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ValidationResult.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "List of error messages."
	)
	public String[] getErrorMessages() {
		if (_errorMessagesSupplier != null) {
			errorMessages = _errorMessagesSupplier.get();

			_errorMessagesSupplier = null;
		}

		return errorMessages;
	}

	public void setErrorMessages(String[] errorMessages) {
		this.errorMessages = errorMessages;

		_errorMessagesSupplier = null;
	}

	@JsonIgnore
	public void setErrorMessages(
		UnsafeSupplier<String[], Exception> errorMessagesUnsafeSupplier) {

		_errorMessagesSupplier = () -> {
			try {
				return errorMessagesUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "List of error messages.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] errorMessages;

	@JsonIgnore
	private Supplier<String[]> _errorMessagesSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Identifier of the temporary lar file created."
	)
	public Long getFileEntryId() {
		if (_fileEntryIdSupplier != null) {
			fileEntryId = _fileEntryIdSupplier.get();

			_fileEntryIdSupplier = null;
		}

		return fileEntryId;
	}

	public void setFileEntryId(Long fileEntryId) {
		this.fileEntryId = fileEntryId;

		_fileEntryIdSupplier = null;
	}

	@JsonIgnore
	public void setFileEntryId(
		UnsafeSupplier<Long, Exception> fileEntryIdUnsafeSupplier) {

		_fileEntryIdSupplier = () -> {
			try {
				return fileEntryIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "Identifier of the temporary lar file created.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long fileEntryId;

	@JsonIgnore
	private Supplier<Long> _fileEntryIdSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Shows if the file can be imported without critical errors."
	)
	public Boolean getSuccess() {
		if (_successSupplier != null) {
			success = _successSupplier.get();

			_successSupplier = null;
		}

		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;

		_successSupplier = null;
	}

	@JsonIgnore
	public void setSuccess(
		UnsafeSupplier<Boolean, Exception> successUnsafeSupplier) {

		_successSupplier = () -> {
			try {
				return successUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Shows if the file can be imported without critical errors."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean success;

	@JsonIgnore
	private Supplier<Boolean> _successSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ValidationResult)) {
			return false;
		}

		ValidationResult validationResult = (ValidationResult)object;

		return Objects.equals(toString(), validationResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String[] errorMessages = getErrorMessages();

		if (errorMessages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessages\": ");

			sb.append("[");

			for (int i = 0; i < errorMessages.length; i++) {
				sb.append("\"");

				sb.append(_escape(errorMessages[i]));

				sb.append("\"");

				if ((i + 1) < errorMessages.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Long fileEntryId = getFileEntryId();

		if (fileEntryId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileEntryId\": ");

			sb.append(fileEntryId);
		}

		Boolean success = getSuccess();

		if (success != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"success\": ");

			sb.append(success);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.exportimport.rest.dto.v1_0.ValidationResult",
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