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

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
@GraphQLName("ProvisioningRequest")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {"accountEntryName", "userAccounts"}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ProvisioningRequest")
public class ProvisioningRequest implements Serializable {

	public static ProvisioningRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(ProvisioningRequest.class, json);
	}

	public static ProvisioningRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			ProvisioningRequest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public String getAccountEntryExternalReferenceCode() {
		if (_accountEntryExternalReferenceCodeSupplier != null) {
			accountEntryExternalReferenceCode =
				_accountEntryExternalReferenceCodeSupplier.get();

			_accountEntryExternalReferenceCodeSupplier = null;
		}

		return accountEntryExternalReferenceCode;
	}

	public void setAccountEntryExternalReferenceCode(
		String accountEntryExternalReferenceCode) {

		this.accountEntryExternalReferenceCode =
			accountEntryExternalReferenceCode;

		_accountEntryExternalReferenceCodeSupplier = null;
	}

	@JsonIgnore
	public void setAccountEntryExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			accountEntryExternalReferenceCodeUnsafeSupplier) {

		_accountEntryExternalReferenceCodeSupplier = () -> {
			try {
				return accountEntryExternalReferenceCodeUnsafeSupplier.get();
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
	protected String accountEntryExternalReferenceCode;

	@JsonIgnore
	private Supplier<String> _accountEntryExternalReferenceCodeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getAccountEntryId() {
		if (_accountEntryIdSupplier != null) {
			accountEntryId = _accountEntryIdSupplier.get();

			_accountEntryIdSupplier = null;
		}

		return accountEntryId;
	}

	public void setAccountEntryId(Long accountEntryId) {
		this.accountEntryId = accountEntryId;

		_accountEntryIdSupplier = null;
	}

	@JsonIgnore
	public void setAccountEntryId(
		UnsafeSupplier<Long, Exception> accountEntryIdUnsafeSupplier) {

		_accountEntryIdSupplier = () -> {
			try {
				return accountEntryIdUnsafeSupplier.get();
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
	protected Long accountEntryId;

	@JsonIgnore
	private Supplier<Long> _accountEntryIdSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getAccountEntryName() {
		if (_accountEntryNameSupplier != null) {
			accountEntryName = _accountEntryNameSupplier.get();

			_accountEntryNameSupplier = null;
		}

		return accountEntryName;
	}

	public void setAccountEntryName(String accountEntryName) {
		this.accountEntryName = accountEntryName;

		_accountEntryNameSupplier = null;
	}

	@JsonIgnore
	public void setAccountEntryName(
		UnsafeSupplier<String, Exception> accountEntryNameUnsafeSupplier) {

		_accountEntryNameSupplier = () -> {
			try {
				return accountEntryNameUnsafeSupplier.get();
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
	protected String accountEntryName;

	@JsonIgnore
	private Supplier<String> _accountEntryNameSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public UserAccount[] getUserAccounts() {
		if (_userAccountsSupplier != null) {
			userAccounts = _userAccountsSupplier.get();

			_userAccountsSupplier = null;
		}

		return userAccounts;
	}

	public void setUserAccounts(UserAccount[] userAccounts) {
		this.userAccounts = userAccounts;

		_userAccountsSupplier = null;
	}

	@JsonIgnore
	public void setUserAccounts(
		UnsafeSupplier<UserAccount[], Exception> userAccountsUnsafeSupplier) {

		_userAccountsSupplier = () -> {
			try {
				return userAccountsUnsafeSupplier.get();
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
	protected UserAccount[] userAccounts;

	@JsonIgnore
	private Supplier<UserAccount[]> _userAccountsSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProvisioningRequest)) {
			return false;
		}

		ProvisioningRequest provisioningRequest = (ProvisioningRequest)object;

		return Objects.equals(toString(), provisioningRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String accountEntryExternalReferenceCode =
			getAccountEntryExternalReferenceCode();

		if (accountEntryExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(accountEntryExternalReferenceCode));

			sb.append("\"");
		}

		Long accountEntryId = getAccountEntryId();

		if (accountEntryId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryId\": ");

			sb.append(accountEntryId);
		}

		String accountEntryName = getAccountEntryName();

		if (accountEntryName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryName\": ");

			sb.append("\"");

			sb.append(_escape(accountEntryName));

			sb.append("\"");
		}

		UserAccount[] userAccounts = getUserAccounts();

		if (userAccounts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userAccounts\": ");

			sb.append("[");

			for (int i = 0; i < userAccounts.length; i++) {
				sb.append(String.valueOf(userAccounts[i]));

				if ((i + 1) < userAccounts.length) {
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
		defaultValue = "com.liferay.ai.hub.rest.dto.v1_0.ProvisioningRequest",
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
// LIFERAY-REST-BUILDER-HASH:1802502784