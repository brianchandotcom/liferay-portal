/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("ValidateResult")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ValidateResult")
public class ValidateResult implements Serializable {

	public static ValidateResult toDTO(String json) {
		return ObjectMapperUtil.readValue(ValidateResult.class, json);
	}

	public static ValidateResult unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ValidateResult.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean success;

	@JsonIgnore
	private Supplier<Boolean> _successSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public ValidateError[] getValidateErrors() {
		if (_validateErrorsSupplier != null) {
			validateErrors = _validateErrorsSupplier.get();

			_validateErrorsSupplier = null;
		}

		return validateErrors;
	}

	public void setValidateErrors(ValidateError[] validateErrors) {
		this.validateErrors = validateErrors;

		_validateErrorsSupplier = null;
	}

	@JsonIgnore
	public void setValidateErrors(
		UnsafeSupplier<ValidateError[], Exception>
			validateErrorsUnsafeSupplier) {

		_validateErrorsSupplier = () -> {
			try {
				return validateErrorsUnsafeSupplier.get();
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
	protected ValidateError[] validateErrors;

	@JsonIgnore
	private Supplier<ValidateError[]> _validateErrorsSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ValidateResult)) {
			return false;
		}

		ValidateResult validateResult = (ValidateResult)object;

		return Objects.equals(toString(), validateResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Boolean success = getSuccess();

		if (success != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"success\": ");

			sb.append(success);
		}

		ValidateError[] validateErrors = getValidateErrors();

		if (validateErrors != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"validateErrors\": ");

			sb.append("[");

			for (int i = 0; i < validateErrors.length; i++) {
				sb.append(String.valueOf(validateErrors[i]));

				if ((i + 1) < validateErrors.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.object.rest.dto.v1_0.ValidateResult",
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