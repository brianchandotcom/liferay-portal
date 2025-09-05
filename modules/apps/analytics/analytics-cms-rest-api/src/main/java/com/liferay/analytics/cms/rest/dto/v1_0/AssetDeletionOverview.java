/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.dto.v1_0;

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
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
@GraphQLName("AssetDeletionOverview")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AssetDeletionOverview")
public class AssetDeletionOverview implements Serializable {

	public static AssetDeletionOverview toDTO(String json) {
		return ObjectMapperUtil.readValue(AssetDeletionOverview.class, json);
	}

	public static AssetDeletionOverview unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			AssetDeletionOverview.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("deletionType")
	@Valid
	public DeletionType getDeletionType() {
		if (_deletionTypeSupplier != null) {
			deletionType = _deletionTypeSupplier.get();

			_deletionTypeSupplier = null;
		}

		return deletionType;
	}

	@JsonIgnore
	public String getDeletionTypeAsString() {
		DeletionType deletionType = getDeletionType();

		if (deletionType == null) {
			return null;
		}

		return deletionType.toString();
	}

	public void setDeletionType(DeletionType deletionType) {
		this.deletionType = deletionType;

		_deletionTypeSupplier = null;
	}

	@JsonIgnore
	public void setDeletionType(
		UnsafeSupplier<DeletionType, Exception> deletionTypeUnsafeSupplier) {

		_deletionTypeSupplier = () -> {
			try {
				return deletionTypeUnsafeSupplier.get();
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
	protected DeletionType deletionType;

	@JsonIgnore
	private Supplier<DeletionType> _deletionTypeSupplier;

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
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getMimeType() {
		if (_mimeTypeSupplier != null) {
			mimeType = _mimeTypeSupplier.get();

			_mimeTypeSupplier = null;
		}

		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;

		_mimeTypeSupplier = null;
	}

	@JsonIgnore
	public void setMimeType(
		UnsafeSupplier<String, Exception> mimeTypeUnsafeSupplier) {

		_mimeTypeSupplier = () -> {
			try {
				return mimeTypeUnsafeSupplier.get();
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
	protected String mimeType;

	@JsonIgnore
	private Supplier<String> _mimeTypeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getTitle() {
		if (_titleSupplier != null) {
			title = _titleSupplier.get();

			_titleSupplier = null;
		}

		return title;
	}

	public void setTitle(String title) {
		this.title = title;

		_titleSupplier = null;
	}

	@JsonIgnore
	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		_titleSupplier = () -> {
			try {
				return titleUnsafeSupplier.get();
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
	protected String title;

	@JsonIgnore
	private Supplier<String> _titleSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Integer getUsages() {
		if (_usagesSupplier != null) {
			usages = _usagesSupplier.get();

			_usagesSupplier = null;
		}

		return usages;
	}

	public void setUsages(Integer usages) {
		this.usages = usages;

		_usagesSupplier = null;
	}

	@JsonIgnore
	public void setUsages(
		UnsafeSupplier<Integer, Exception> usagesUnsafeSupplier) {

		_usagesSupplier = () -> {
			try {
				return usagesUnsafeSupplier.get();
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
	protected Integer usages;

	@JsonIgnore
	private Supplier<Integer> _usagesSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetDeletionOverview)) {
			return false;
		}

		AssetDeletionOverview assetDeletionOverview =
			(AssetDeletionOverview)object;

		return Objects.equals(toString(), assetDeletionOverview.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DeletionType deletionType = getDeletionType();

		if (deletionType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deletionType\": ");

			sb.append("\"");

			sb.append(deletionType);

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

		String mimeType = getMimeType();

		if (mimeType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"mimeType\": ");

			sb.append("\"");

			sb.append(_escape(mimeType));

			sb.append("\"");
		}

		String title = getTitle();

		if (title != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(title));

			sb.append("\"");
		}

		Integer usages = getUsages();

		if (usages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"usages\": ");

			sb.append(usages);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.analytics.cms.rest.dto.v1_0.AssetDeletionOverview",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("DeletionType")
	public static enum DeletionType {

		PERMANENT_DELETION("PERMANENT_DELETION"), RECYCLE_BIN("RECYCLE_BIN");

		@JsonCreator
		public static DeletionType create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (DeletionType deletionType : values()) {
				if (Objects.equals(deletionType.getValue(), value)) {
					return deletionType;
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

		private DeletionType(String value) {
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