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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

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
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
@GraphQLName("ExportRequest")
@io.swagger.v3.oas.annotations.media.Schema(requiredProperties = {"fileName"})
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ExportRequest")
public class ExportRequest implements Serializable {

	public static ExportRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(ExportRequest.class, json);
	}

	public static ExportRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ExportRequest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public Date getEndDate() {
		if (_endDateSupplier != null) {
			endDate = _endDateSupplier.get();

			_endDateSupplier = null;
		}

		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;

		_endDateSupplier = null;
	}

	@JsonIgnore
	public void setEndDate(
		UnsafeSupplier<Date, Exception> endDateUnsafeSupplier) {

		_endDateSupplier = () -> {
			try {
				return endDateUnsafeSupplier.get();
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
	protected Date endDate;

	@JsonIgnore
	private Supplier<Date> _endDateSupplier;

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
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String fileName;

	@JsonIgnore
	private Supplier<String> _fileNameSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Integer getLast() {
		if (_lastSupplier != null) {
			last = _lastSupplier.get();

			_lastSupplier = null;
		}

		return last;
	}

	public void setLast(Integer last) {
		this.last = last;

		_lastSupplier = null;
	}

	@JsonIgnore
	public void setLast(UnsafeSupplier<Integer, Exception> lastUnsafeSupplier) {
		_lastSupplier = () -> {
			try {
				return lastUnsafeSupplier.get();
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
	protected Integer last;

	@JsonIgnore
	private Supplier<Integer> _lastSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("range")
	@Valid
	public Range getRange() {
		if (_rangeSupplier != null) {
			range = _rangeSupplier.get();

			_rangeSupplier = null;
		}

		return range;
	}

	@JsonIgnore
	public String getRangeAsString() {
		Range range = getRange();

		if (range == null) {
			return null;
		}

		return range.toString();
	}

	public void setRange(Range range) {
		this.range = range;

		_rangeSupplier = null;
	}

	@JsonIgnore
	public void setRange(UnsafeSupplier<Range, Exception> rangeUnsafeSupplier) {
		_rangeSupplier = () -> {
			try {
				return rangeUnsafeSupplier.get();
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
	protected Range range;

	@JsonIgnore
	private Supplier<Range> _rangeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public RequestPortletDataHandler[] getRequestPortletDataHandlers() {
		if (_requestPortletDataHandlersSupplier != null) {
			requestPortletDataHandlers =
				_requestPortletDataHandlersSupplier.get();

			_requestPortletDataHandlersSupplier = null;
		}

		return requestPortletDataHandlers;
	}

	public void setRequestPortletDataHandlers(
		RequestPortletDataHandler[] requestPortletDataHandlers) {

		this.requestPortletDataHandlers = requestPortletDataHandlers;

		_requestPortletDataHandlersSupplier = null;
	}

	@JsonIgnore
	public void setRequestPortletDataHandlers(
		UnsafeSupplier<RequestPortletDataHandler[], Exception>
			requestPortletDataHandlersUnsafeSupplier) {

		_requestPortletDataHandlersSupplier = () -> {
			try {
				return requestPortletDataHandlersUnsafeSupplier.get();
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
	protected RequestPortletDataHandler[] requestPortletDataHandlers;

	@JsonIgnore
	private Supplier<RequestPortletDataHandler[]>
		_requestPortletDataHandlersSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Date getStartDate() {
		if (_startDateSupplier != null) {
			startDate = _startDateSupplier.get();

			_startDateSupplier = null;
		}

		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;

		_startDateSupplier = null;
	}

	@JsonIgnore
	public void setStartDate(
		UnsafeSupplier<Date, Exception> startDateUnsafeSupplier) {

		_startDateSupplier = () -> {
			try {
				return startDateUnsafeSupplier.get();
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
	protected Date startDate;

	@JsonIgnore
	private Supplier<Date> _startDateSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ExportRequest)) {
			return false;
		}

		ExportRequest exportRequest = (ExportRequest)object;

		return Objects.equals(toString(), exportRequest.toString());
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

		Date endDate = getEndDate();

		if (endDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"endDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(endDate));

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

		Integer last = getLast();

		if (last != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"last\": ");

			sb.append(last);
		}

		Range range = getRange();

		if (range != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"range\": ");

			sb.append("\"");
			sb.append(range);
			sb.append("\"");
		}

		RequestPortletDataHandler[] requestPortletDataHandlers =
			getRequestPortletDataHandlers();

		if (requestPortletDataHandlers != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"requestPortletDataHandlers\": ");

			sb.append("[");

			for (int i = 0; i < requestPortletDataHandlers.length; i++) {
				sb.append(String.valueOf(requestPortletDataHandlers[i]));

				if ((i + 1) < requestPortletDataHandlers.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Date startDate = getStartDate();

		if (startDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(startDate));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.exportimport.rest.dto.v1_0.ExportRequest",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Range")
	public static enum Range {

		ALL("all"), DATE_RANGE("dateRange"), LAST("last");

		@JsonCreator
		public static Range create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Range range : values()) {
				if (Objects.equals(range.getValue(), value)) {
					return range;
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

		private Range(String value) {
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
// LIFERAY-REST-BUILDER-HASH:-639160641