/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("PageColumnDefinition")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "PageColumnDefinition")
public class PageColumnDefinition implements Serializable {

	public static PageColumnDefinition toDTO(String json) {
		return ObjectMapperUtil.readValue(PageColumnDefinition.class, json);
	}

	@Schema(
		deprecated = true,
		description = "Deprecated as of Athanasius (7.3.x), replaced by columnViewports"
	)
	@Valid
	public ColumnViewportConfig getColumnViewportConfig() {
		return columnViewportConfig;
	}

	public void setColumnViewportConfig(
		ColumnViewportConfig columnViewportConfig) {

		this.columnViewportConfig = columnViewportConfig;
	}

	@JsonIgnore
	public void setColumnViewportConfig(
		UnsafeSupplier<ColumnViewportConfig, Exception>
			columnViewportConfigUnsafeSupplier) {

		try {
			columnViewportConfig = columnViewportConfigUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	@GraphQLField(
		description = "Deprecated as of Athanasius (7.3.x), replaced by columnViewports"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ColumnViewportConfig columnViewportConfig;

	@Schema
	@Valid
	public ColumnViewport[] getColumnViewports() {
		return columnViewports;
	}

	public void setColumnViewports(ColumnViewport[] columnViewports) {
		this.columnViewports = columnViewports;
	}

	@JsonIgnore
	public void setColumnViewports(
		UnsafeSupplier<ColumnViewport[], Exception>
			columnViewportsUnsafeSupplier) {

		try {
			columnViewports = columnViewportsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ColumnViewport[] columnViewports;

	@DecimalMax("12")
	@DecimalMin("1")
	@Schema
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@JsonIgnore
	public void setSize(UnsafeSupplier<Integer, Exception> sizeUnsafeSupplier) {
		try {
			size = sizeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer size;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageColumnDefinition)) {
			return false;
		}

		PageColumnDefinition pageColumnDefinition =
			(PageColumnDefinition)object;

		return Objects.equals(toString(), pageColumnDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (columnViewportConfig != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"columnViewportConfig\": ");

			sb.append(String.valueOf(columnViewportConfig));
		}

		if (columnViewports != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"columnViewports\": ");

			sb.append("[");

			for (int i = 0; i < columnViewports.length; i++) {
				sb.append(String.valueOf(columnViewports[i]));

				if ((i + 1) < columnViewports.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (size != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"size\": ");

			sb.append(size);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.PageColumnDefinition",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
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
				sb.append(value);
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}