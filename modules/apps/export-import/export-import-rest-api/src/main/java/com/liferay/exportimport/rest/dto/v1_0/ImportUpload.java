/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collection;
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
@GraphQLName(
	description = "Represents a file uploaded in chunks. Once every chunk is uploaded, the file is validated in the background and its import preview becomes available.",
	value = "ImportUpload"
)
@io.swagger.v3.oas.annotations.media.Schema(
	description = "Represents a file uploaded in chunks. Once every chunk is uploaded, the file is validated in the background and its import preview becomes available."
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ImportUpload")
public class ImportUpload implements Serializable {

	public static ImportUpload toDTO(String json) {
		return ObjectMapperUtil.readValue(ImportUpload.class, json);
	}

	public static ImportUpload unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ImportUpload.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The size in bytes of every chunk but the last one."
	)
	public Long getChunkSize() {
		if (_chunkSizeSupplier != null) {
			chunkSize = _chunkSizeSupplier.get();

			_chunkSizeSupplier = null;
		}

		return chunkSize;
	}

	public void setChunkSize(Long chunkSize) {
		this.chunkSize = chunkSize;

		_chunkSizeSupplier = null;
	}

	@JsonIgnore
	public void setChunkSize(
		UnsafeSupplier<Long, Exception> chunkSizeUnsafeSupplier) {

		_chunkSizeSupplier = () -> {
			try {
				return chunkSizeUnsafeSupplier.get();
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
		description = "The size in bytes of every chunk but the last one."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long chunkSize;

	@JsonIgnore
	private Supplier<Long> _chunkSizeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The validation error when the status is FAILED."
	)
	public String getErrorMessage() {
		if (_errorMessageSupplier != null) {
			errorMessage = _errorMessageSupplier.get();

			_errorMessageSupplier = null;
		}

		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;

		_errorMessageSupplier = null;
	}

	@JsonIgnore
	public void setErrorMessage(
		UnsafeSupplier<String, Exception> errorMessageUnsafeSupplier) {

		_errorMessageSupplier = () -> {
			try {
				return errorMessageUnsafeSupplier.get();
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
		description = "The validation error when the status is FAILED."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String errorMessage;

	@JsonIgnore
	private Supplier<String> _errorMessageSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getFileName() {
		if (_fileNameSupplier != null) {
			fileName = _fileNameSupplier.get();

			_fileNameSupplier = null;
		}

		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;

		_fileNameSupplier = null;
	}

	@JsonIgnore
	public void setFileName(
		UnsafeSupplier<String, Exception> fileNameUnsafeSupplier) {

		_fileNameSupplier = () -> {
			try {
				return fileNameUnsafeSupplier.get();
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
	protected String fileName;

	@JsonIgnore
	private Supplier<String> _fileNameSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getId() {
		if (_idSupplier != null) {
			id = _idSupplier.get();

			_idSupplier = null;
		}

		return id;
	}

	public void setId(String id) {
		this.id = id;

		_idSupplier = null;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
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
	protected String id;

	@JsonIgnore
	private Supplier<String> _idSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The import preview when the status is READY."
	)
	@Valid
	public ImportPreview getImportPreview() {
		if (_importPreviewSupplier != null) {
			importPreview = _importPreviewSupplier.get();

			_importPreviewSupplier = null;
		}

		return importPreview;
	}

	public void setImportPreview(ImportPreview importPreview) {
		this.importPreview = importPreview;

		_importPreviewSupplier = null;
	}

	@JsonIgnore
	public void setImportPreview(
		UnsafeSupplier<ImportPreview, Exception> importPreviewUnsafeSupplier) {

		_importPreviewSupplier = () -> {
			try {
				return importPreviewUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The import preview when the status is READY.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ImportPreview importPreview;

	@JsonIgnore
	private Supplier<ImportPreview> _importPreviewSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The validation progress when the status is VALIDATING."
	)
	public Integer getPercentage() {
		if (_percentageSupplier != null) {
			percentage = _percentageSupplier.get();

			_percentageSupplier = null;
		}

		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;

		_percentageSupplier = null;
	}

	@JsonIgnore
	public void setPercentage(
		UnsafeSupplier<Integer, Exception> percentageUnsafeSupplier) {

		_percentageSupplier = () -> {
			try {
				return percentageUnsafeSupplier.get();
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
		description = "The validation progress when the status is VALIDATING."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Integer percentage;

	@JsonIgnore
	private Supplier<Integer> _percentageSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The file's size in bytes."
	)
	public Long getSize() {
		if (_sizeSupplier != null) {
			size = _sizeSupplier.get();

			_sizeSupplier = null;
		}

		return size;
	}

	public void setSize(Long size) {
		this.size = size;

		_sizeSupplier = null;
	}

	@JsonIgnore
	public void setSize(UnsafeSupplier<Long, Exception> sizeUnsafeSupplier) {
		_sizeSupplier = () -> {
			try {
				return sizeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The file's size in bytes.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long size;

	@JsonIgnore
	private Supplier<Long> _sizeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("status")
	@Valid
	public Status getStatus() {
		if (_statusSupplier != null) {
			status = _statusSupplier.get();

			_statusSupplier = null;
		}

		return status;
	}

	@JsonIgnore
	public String getStatusAsString() {
		Status status = getStatus();

		if (status == null) {
			return null;
		}

		return status.toString();
	}

	public void setStatus(Status status) {
		this.status = status;

		_statusSupplier = null;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<Status, Exception> statusUnsafeSupplier) {

		_statusSupplier = () -> {
			try {
				return statusUnsafeSupplier.get();
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
	protected Status status;

	@JsonIgnore
	private Supplier<Status> _statusSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Integer getTotalChunksCount() {
		if (_totalChunksCountSupplier != null) {
			totalChunksCount = _totalChunksCountSupplier.get();

			_totalChunksCountSupplier = null;
		}

		return totalChunksCount;
	}

	public void setTotalChunksCount(Integer totalChunksCount) {
		this.totalChunksCount = totalChunksCount;

		_totalChunksCountSupplier = null;
	}

	@JsonIgnore
	public void setTotalChunksCount(
		UnsafeSupplier<Integer, Exception> totalChunksCountUnsafeSupplier) {

		_totalChunksCountSupplier = () -> {
			try {
				return totalChunksCountUnsafeSupplier.get();
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
	protected Integer totalChunksCount;

	@JsonIgnore
	private Supplier<Integer> _totalChunksCountSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The number of chunks uploaded so far, counted from the first one without gaps."
	)
	public Integer getUploadedChunksCount() {
		if (_uploadedChunksCountSupplier != null) {
			uploadedChunksCount = _uploadedChunksCountSupplier.get();

			_uploadedChunksCountSupplier = null;
		}

		return uploadedChunksCount;
	}

	public void setUploadedChunksCount(Integer uploadedChunksCount) {
		this.uploadedChunksCount = uploadedChunksCount;

		_uploadedChunksCountSupplier = null;
	}

	@JsonIgnore
	public void setUploadedChunksCount(
		UnsafeSupplier<Integer, Exception> uploadedChunksCountUnsafeSupplier) {

		_uploadedChunksCountSupplier = () -> {
			try {
				return uploadedChunksCountUnsafeSupplier.get();
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
		description = "The number of chunks uploaded so far, counted from the first one without gaps."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Integer uploadedChunksCount;

	@JsonIgnore
	private Supplier<Integer> _uploadedChunksCountSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ImportUpload)) {
			return false;
		}

		ImportUpload importUpload = (ImportUpload)object;

		return Objects.equals(toString(), importUpload.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long chunkSize = getChunkSize();

		if (chunkSize != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"chunkSize\": ");

			sb.append(chunkSize);
		}

		String errorMessage = getErrorMessage();

		if (errorMessage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessage\": ");

			sb.append("\"");

			sb.append(_escape(errorMessage));

			sb.append("\"");
		}

		String fileName = getFileName();

		if (fileName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileName\": ");

			sb.append("\"");

			sb.append(_escape(fileName));

			sb.append("\"");
		}

		String id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(id));

			sb.append("\"");
		}

		ImportPreview importPreview = getImportPreview();

		if (importPreview != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importPreview\": ");

			sb.append(String.valueOf(importPreview));
		}

		Integer percentage = getPercentage();

		if (percentage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"percentage\": ");

			sb.append(percentage);
		}

		Long size = getSize();

		if (size != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"size\": ");

			sb.append(size);
		}

		Status status = getStatus();

		if (status != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");
			sb.append(status);
			sb.append("\"");
		}

		Integer totalChunksCount = getTotalChunksCount();

		if (totalChunksCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalChunksCount\": ");

			sb.append(totalChunksCount);
		}

		Integer uploadedChunksCount = getUploadedChunksCount();

		if (uploadedChunksCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uploadedChunksCount\": ");

			sb.append(uploadedChunksCount);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.exportimport.rest.dto.v1_0.ImportUpload",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Status")
	public static enum Status {

		UPLOADING("UPLOADING"), VALIDATING("VALIDATING"), READY("READY"),
		FAILED("FAILED");

		@JsonCreator
		public static Status create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Status status : values()) {
				if (Objects.equals(status.getValue(), value)) {
					return status;
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

		private Status(String value) {
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

	private static String _toJSON(Object value) {
		if (value instanceof Collection) {
			return String.valueOf(
				JSONFactoryUtil.createJSONArray((Collection<?>)value));
		}
		else if (value instanceof Map) {
			return String.valueOf(
				JSONFactoryUtil.createJSONObject((Map<?, ?>)value));
		}
		else if (value instanceof Object[]) {
			return String.valueOf(
				JSONFactoryUtil.createJSONArray(
					Arrays.asList((Object[])value)));
		}
		else if (value instanceof String) {
			return StringBundler.concat("\"", _escape(value), "\"");
		}

		return String.valueOf(value);
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}
// LIFERAY-REST-BUILDER-HASH:-1044558282