/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.dto.v1_0;

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
 * @author Alicia García
 * @generated
 */
@Generated("")
@GraphQLName("BatchEngineJobResponse")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "BatchEngineJobResponse")
public class BatchEngineJobResponse implements Serializable {

	public static BatchEngineJobResponse toDTO(String json) {
		return ObjectMapperUtil.readValue(BatchEngineJobResponse.class, json);
	}

	public static BatchEngineJobResponse unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			BatchEngineJobResponse.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(description = "The batch ID.")
	public Long getBatchId() {
		if (_batchIdSupplier != null) {
			batchId = _batchIdSupplier.get();

			_batchIdSupplier = null;
		}

		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;

		_batchIdSupplier = null;
	}

	@JsonIgnore
	public void setBatchId(
		UnsafeSupplier<Long, Exception> batchIdUnsafeSupplier) {

		_batchIdSupplier = () -> {
			try {
				return batchIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The batch ID.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long batchId;

	@JsonIgnore
	private Supplier<Long> _batchIdSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The object type.", example = "ObjectEntry"
	)
	public String getObjectType() {
		if (_objectTypeSupplier != null) {
			objectType = _objectTypeSupplier.get();

			_objectTypeSupplier = null;
		}

		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;

		_objectTypeSupplier = null;
	}

	@JsonIgnore
	public void setObjectType(
		UnsafeSupplier<String, Exception> objectTypeUnsafeSupplier) {

		_objectTypeSupplier = () -> {
			try {
				return objectTypeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The object type.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String objectType;

	@JsonIgnore
	private Supplier<String> _objectTypeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The processed IDs."
	)
	public Long[] getProcessedIds() {
		if (_processedIdsSupplier != null) {
			processedIds = _processedIdsSupplier.get();

			_processedIdsSupplier = null;
		}

		return processedIds;
	}

	public void setProcessedIds(Long[] processedIds) {
		this.processedIds = processedIds;

		_processedIdsSupplier = null;
	}

	@JsonIgnore
	public void setProcessedIds(
		UnsafeSupplier<Long[], Exception> processedIdsUnsafeSupplier) {

		_processedIdsSupplier = () -> {
			try {
				return processedIdsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The processed IDs.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long[] processedIds;

	@JsonIgnore
	private Supplier<Long[]> _processedIdsSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchEngineJobResponse)) {
			return false;
		}

		BatchEngineJobResponse batchEngineJobResponse =
			(BatchEngineJobResponse)object;

		return Objects.equals(toString(), batchEngineJobResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long batchId = getBatchId();

		if (batchId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"batchId\": ");

			sb.append(batchId);
		}

		String objectType = getObjectType();

		if (objectType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectType\": ");

			sb.append("\"");

			sb.append(_escape(objectType));

			sb.append("\"");
		}

		Long[] processedIds = getProcessedIds();

		if (processedIds != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"processedIds\": ");

			sb.append("[");

			for (int i = 0; i < processedIds.length; i++) {
				sb.append(processedIds[i]);

				if ((i + 1) < processedIds.length) {
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
		defaultValue = "com.liferay.headless.object.dto.v1_0.BatchEngineJobResponse",
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