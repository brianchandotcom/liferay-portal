/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.dto.v1_0;

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
 * @author Stefano Motta
 * @generated
 */
@Generated("")
@GraphQLName("DigitalSalesRoomTemplate")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DigitalSalesRoomTemplate")
public class DigitalSalesRoomTemplate implements Serializable {

	public static DigitalSalesRoomTemplate toDTO(String json) {
		return ObjectMapperUtil.readValue(DigitalSalesRoomTemplate.class, json);
	}

	public static DigitalSalesRoomTemplate unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			DigitalSalesRoomTemplate.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Map<String, Map<String, String>> getActions() {
		if (_actionsSupplier != null) {
			actions = _actionsSupplier.get();

			_actionsSupplier = null;
		}

		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;

		_actionsSupplier = null;
	}

	@JsonIgnore
	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		_actionsSupplier = () -> {
			try {
				return actionsUnsafeSupplier.get();
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
	protected Map<String, Map<String, String>> actions;

	@JsonIgnore
	private Supplier<Map<String, Map<String, String>>> _actionsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public FileEntry getBanner() {
		if (_bannerSupplier != null) {
			banner = _bannerSupplier.get();

			_bannerSupplier = null;
		}

		return banner;
	}

	public void setBanner(FileEntry banner) {
		this.banner = banner;

		_bannerSupplier = null;
	}

	@JsonIgnore
	public void setBanner(
		UnsafeSupplier<FileEntry, Exception> bannerUnsafeSupplier) {

		_bannerSupplier = () -> {
			try {
				return bannerUnsafeSupplier.get();
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
	protected FileEntry banner;

	@JsonIgnore
	private Supplier<FileEntry> _bannerSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public FileEntry getClientLogo() {
		if (_clientLogoSupplier != null) {
			clientLogo = _clientLogoSupplier.get();

			_clientLogoSupplier = null;
		}

		return clientLogo;
	}

	public void setClientLogo(FileEntry clientLogo) {
		this.clientLogo = clientLogo;

		_clientLogoSupplier = null;
	}

	@JsonIgnore
	public void setClientLogo(
		UnsafeSupplier<FileEntry, Exception> clientLogoUnsafeSupplier) {

		_clientLogoSupplier = () -> {
			try {
				return clientLogoUnsafeSupplier.get();
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
	protected FileEntry clientLogo;

	@JsonIgnore
	private Supplier<FileEntry> _clientLogoSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getClientName() {
		if (_clientNameSupplier != null) {
			clientName = _clientNameSupplier.get();

			_clientNameSupplier = null;
		}

		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;

		_clientNameSupplier = null;
	}

	@JsonIgnore
	public void setClientName(
		UnsafeSupplier<String, Exception> clientNameUnsafeSupplier) {

		_clientNameSupplier = () -> {
			try {
				return clientNameUnsafeSupplier.get();
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
	protected String clientName;

	@JsonIgnore
	private Supplier<String> _clientNameSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Date getCreateDate() {
		if (_createDateSupplier != null) {
			createDate = _createDateSupplier.get();

			_createDateSupplier = null;
		}

		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;

		_createDateSupplier = null;
	}

	@JsonIgnore
	public void setCreateDate(
		UnsafeSupplier<Date, Exception> createDateUnsafeSupplier) {

		_createDateSupplier = () -> {
			try {
				return createDateUnsafeSupplier.get();
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
	protected Date createDate;

	@JsonIgnore
	private Supplier<Date> _createDateSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getDescription() {
		if (_descriptionSupplier != null) {
			description = _descriptionSupplier.get();

			_descriptionSupplier = null;
		}

		return description;
	}

	public void setDescription(String description) {
		this.description = description;

		_descriptionSupplier = null;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		_descriptionSupplier = () -> {
			try {
				return descriptionUnsafeSupplier.get();
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
	protected String description;

	@JsonIgnore
	private Supplier<String> _descriptionSupplier;

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
	public String getFriendlyUrlPath() {
		if (_friendlyUrlPathSupplier != null) {
			friendlyUrlPath = _friendlyUrlPathSupplier.get();

			_friendlyUrlPathSupplier = null;
		}

		return friendlyUrlPath;
	}

	public void setFriendlyUrlPath(String friendlyUrlPath) {
		this.friendlyUrlPath = friendlyUrlPath;

		_friendlyUrlPathSupplier = null;
	}

	@JsonIgnore
	public void setFriendlyUrlPath(
		UnsafeSupplier<String, Exception> friendlyUrlPathUnsafeSupplier) {

		_friendlyUrlPathSupplier = () -> {
			try {
				return friendlyUrlPathUnsafeSupplier.get();
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
	protected String friendlyUrlPath;

	@JsonIgnore
	private Supplier<String> _friendlyUrlPathSupplier;

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
	public Date getModifiedDate() {
		if (_modifiedDateSupplier != null) {
			modifiedDate = _modifiedDateSupplier.get();

			_modifiedDateSupplier = null;
		}

		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;

		_modifiedDateSupplier = null;
	}

	@JsonIgnore
	public void setModifiedDate(
		UnsafeSupplier<Date, Exception> modifiedDateUnsafeSupplier) {

		_modifiedDateSupplier = () -> {
			try {
				return modifiedDateUnsafeSupplier.get();
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
	protected Date modifiedDate;

	@JsonIgnore
	private Supplier<Date> _modifiedDateSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getName() {
		if (_nameSupplier != null) {
			name = _nameSupplier.get();

			_nameSupplier = null;
		}

		return name;
	}

	public void setName(String name) {
		this.name = name;

		_nameSupplier = null;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		_nameSupplier = () -> {
			try {
				return nameUnsafeSupplier.get();
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
	protected String name;

	@JsonIgnore
	private Supplier<String> _nameSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getPrimaryColor() {
		if (_primaryColorSupplier != null) {
			primaryColor = _primaryColorSupplier.get();

			_primaryColorSupplier = null;
		}

		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;

		_primaryColorSupplier = null;
	}

	@JsonIgnore
	public void setPrimaryColor(
		UnsafeSupplier<String, Exception> primaryColorUnsafeSupplier) {

		_primaryColorSupplier = () -> {
			try {
				return primaryColorUnsafeSupplier.get();
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
	protected String primaryColor;

	@JsonIgnore
	private Supplier<String> _primaryColorSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getSecondaryColor() {
		if (_secondaryColorSupplier != null) {
			secondaryColor = _secondaryColorSupplier.get();

			_secondaryColorSupplier = null;
		}

		return secondaryColor;
	}

	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;

		_secondaryColorSupplier = null;
	}

	@JsonIgnore
	public void setSecondaryColor(
		UnsafeSupplier<String, Exception> secondaryColorUnsafeSupplier) {

		_secondaryColorSupplier = () -> {
			try {
				return secondaryColorUnsafeSupplier.get();
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
	protected String secondaryColor;

	@JsonIgnore
	private Supplier<String> _secondaryColorSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DigitalSalesRoomTemplate)) {
			return false;
		}

		DigitalSalesRoomTemplate digitalSalesRoomTemplate =
			(DigitalSalesRoomTemplate)object;

		return Objects.equals(toString(), digitalSalesRoomTemplate.toString());
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

		Map<String, Map<String, String>> actions = getActions();

		if (actions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(actions));
		}

		FileEntry banner = getBanner();

		if (banner != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"banner\": ");

			sb.append(String.valueOf(banner));
		}

		FileEntry clientLogo = getClientLogo();

		if (clientLogo != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientLogo\": ");

			sb.append(String.valueOf(clientLogo));
		}

		String clientName = getClientName();

		if (clientName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientName\": ");

			sb.append("\"");

			sb.append(_escape(clientName));

			sb.append("\"");
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(createDate));

			sb.append("\"");
		}

		String description = getDescription();

		if (description != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(description));

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

		String friendlyUrlPath = getFriendlyUrlPath();

		if (friendlyUrlPath != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyUrlPath\": ");

			sb.append("\"");

			sb.append(_escape(friendlyUrlPath));

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

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(modifiedDate));

			sb.append("\"");
		}

		String name = getName();

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		String primaryColor = getPrimaryColor();

		if (primaryColor != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primaryColor\": ");

			sb.append("\"");

			sb.append(_escape(primaryColor));

			sb.append("\"");
		}

		String secondaryColor = getSecondaryColor();

		if (secondaryColor != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"secondaryColor\": ");

			sb.append("\"");

			sb.append(_escape(secondaryColor));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoomTemplate",
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