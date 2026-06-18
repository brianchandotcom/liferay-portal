/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.dto.v1_0;

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
@GraphQLName("PerformanceTopAsset")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "PerformanceTopAsset")
public class PerformanceTopAsset implements Serializable {

	public static PerformanceTopAsset toDTO(String json) {
		return ObjectMapperUtil.readValue(PerformanceTopAsset.class, json);
	}

	public static PerformanceTopAsset unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			PerformanceTopAsset.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getLastPage() {
		if (_lastPageSupplier != null) {
			lastPage = _lastPageSupplier.get();

			_lastPageSupplier = null;
		}

		return lastPage;
	}

	public void setLastPage(Long lastPage) {
		this.lastPage = lastPage;

		_lastPageSupplier = null;
	}

	@JsonIgnore
	public void setLastPage(
		UnsafeSupplier<Long, Exception> lastPageUnsafeSupplier) {

		_lastPageSupplier = () -> {
			try {
				return lastPageUnsafeSupplier.get();
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
	protected Long lastPage;

	@JsonIgnore
	private Supplier<Long> _lastPageSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getPage() {
		if (_pageSupplier != null) {
			page = _pageSupplier.get();

			_pageSupplier = null;
		}

		return page;
	}

	public void setPage(Long page) {
		this.page = page;

		_pageSupplier = null;
	}

	@JsonIgnore
	public void setPage(UnsafeSupplier<Long, Exception> pageUnsafeSupplier) {
		_pageSupplier = () -> {
			try {
				return pageUnsafeSupplier.get();
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
	protected Long page;

	@JsonIgnore
	private Supplier<Long> _pageSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getPageSize() {
		if (_pageSizeSupplier != null) {
			pageSize = _pageSizeSupplier.get();

			_pageSizeSupplier = null;
		}

		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;

		_pageSizeSupplier = null;
	}

	@JsonIgnore
	public void setPageSize(
		UnsafeSupplier<Long, Exception> pageSizeUnsafeSupplier) {

		_pageSizeSupplier = () -> {
			try {
				return pageSizeUnsafeSupplier.get();
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
	protected Long pageSize;

	@JsonIgnore
	private Supplier<Long> _pageSizeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public PerformanceTopAssetItem[] getPerformanceTopAssetItems() {
		if (_performanceTopAssetItemsSupplier != null) {
			performanceTopAssetItems = _performanceTopAssetItemsSupplier.get();

			_performanceTopAssetItemsSupplier = null;
		}

		return performanceTopAssetItems;
	}

	public void setPerformanceTopAssetItems(
		PerformanceTopAssetItem[] performanceTopAssetItems) {

		this.performanceTopAssetItems = performanceTopAssetItems;

		_performanceTopAssetItemsSupplier = null;
	}

	@JsonIgnore
	public void setPerformanceTopAssetItems(
		UnsafeSupplier<PerformanceTopAssetItem[], Exception>
			performanceTopAssetItemsUnsafeSupplier) {

		_performanceTopAssetItemsSupplier = () -> {
			try {
				return performanceTopAssetItemsUnsafeSupplier.get();
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
	protected PerformanceTopAssetItem[] performanceTopAssetItems;

	@JsonIgnore
	private Supplier<PerformanceTopAssetItem[]>
		_performanceTopAssetItemsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getTotalCount() {
		if (_totalCountSupplier != null) {
			totalCount = _totalCountSupplier.get();

			_totalCountSupplier = null;
		}

		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;

		_totalCountSupplier = null;
	}

	@JsonIgnore
	public void setTotalCount(
		UnsafeSupplier<Long, Exception> totalCountUnsafeSupplier) {

		_totalCountSupplier = () -> {
			try {
				return totalCountUnsafeSupplier.get();
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
	protected Long totalCount;

	@JsonIgnore
	private Supplier<Long> _totalCountSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PerformanceTopAsset)) {
			return false;
		}

		PerformanceTopAsset performanceTopAsset = (PerformanceTopAsset)object;

		return Objects.equals(toString(), performanceTopAsset.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long lastPage = getLastPage();

		if (lastPage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastPage\": ");

			sb.append(lastPage);
		}

		Long page = getPage();

		if (page != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"page\": ");

			sb.append(page);
		}

		Long pageSize = getPageSize();

		if (pageSize != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageSize\": ");

			sb.append(pageSize);
		}

		PerformanceTopAssetItem[] performanceTopAssetItems =
			getPerformanceTopAssetItems();

		if (performanceTopAssetItems != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"performanceTopAssetItems\": ");

			sb.append("[");

			for (int i = 0; i < performanceTopAssetItems.length; i++) {
				sb.append(String.valueOf(performanceTopAssetItems[i]));

				if ((i + 1) < performanceTopAssetItems.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Long totalCount = getTotalCount();

		if (totalCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalCount\": ");

			sb.append(totalCount);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.analytics.cms.rest.dto.v1_0.PerformanceTopAsset",
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
// LIFERAY-REST-BUILDER-HASH:1001946798