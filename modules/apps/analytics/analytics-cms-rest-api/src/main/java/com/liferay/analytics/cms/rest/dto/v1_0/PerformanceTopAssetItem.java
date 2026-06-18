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
@GraphQLName("PerformanceTopAssetItem")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "PerformanceTopAssetItem")
public class PerformanceTopAssetItem implements Serializable {

	public static PerformanceTopAssetItem toDTO(String json) {
		return ObjectMapperUtil.readValue(PerformanceTopAssetItem.class, json);
	}

	public static PerformanceTopAssetItem unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			PerformanceTopAssetItem.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public Double getDownloads() {
		if (_downloadsSupplier != null) {
			downloads = _downloadsSupplier.get();

			_downloadsSupplier = null;
		}

		return downloads;
	}

	public void setDownloads(Double downloads) {
		this.downloads = downloads;

		_downloadsSupplier = null;
	}

	@JsonIgnore
	public void setDownloads(
		UnsafeSupplier<Double, Exception> downloadsUnsafeSupplier) {

		_downloadsSupplier = () -> {
			try {
				return downloadsUnsafeSupplier.get();
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
	protected Double downloads;

	@JsonIgnore
	private Supplier<Double> _downloadsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Double getEngagement() {
		if (_engagementSupplier != null) {
			engagement = _engagementSupplier.get();

			_engagementSupplier = null;
		}

		return engagement;
	}

	public void setEngagement(Double engagement) {
		this.engagement = engagement;

		_engagementSupplier = null;
	}

	@JsonIgnore
	public void setEngagement(
		UnsafeSupplier<Double, Exception> engagementUnsafeSupplier) {

		_engagementSupplier = () -> {
			try {
				return engagementUnsafeSupplier.get();
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
	protected Double engagement;

	@JsonIgnore
	private Supplier<Double> _engagementSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Double getImpressions() {
		if (_impressionsSupplier != null) {
			impressions = _impressionsSupplier.get();

			_impressionsSupplier = null;
		}

		return impressions;
	}

	public void setImpressions(Double impressions) {
		this.impressions = impressions;

		_impressionsSupplier = null;
	}

	@JsonIgnore
	public void setImpressions(
		UnsafeSupplier<Double, Exception> impressionsUnsafeSupplier) {

		_impressionsSupplier = () -> {
			try {
				return impressionsUnsafeSupplier.get();
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
	protected Double impressions;

	@JsonIgnore
	private Supplier<Double> _impressionsSupplier;

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
	@Valid
	public Trend getTrend() {
		if (_trendSupplier != null) {
			trend = _trendSupplier.get();

			_trendSupplier = null;
		}

		return trend;
	}

	public void setTrend(Trend trend) {
		this.trend = trend;

		_trendSupplier = null;
	}

	@JsonIgnore
	public void setTrend(UnsafeSupplier<Trend, Exception> trendUnsafeSupplier) {
		_trendSupplier = () -> {
			try {
				return trendUnsafeSupplier.get();
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
	protected Trend trend;

	@JsonIgnore
	private Supplier<Trend> _trendSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Double getViews() {
		if (_viewsSupplier != null) {
			views = _viewsSupplier.get();

			_viewsSupplier = null;
		}

		return views;
	}

	public void setViews(Double views) {
		this.views = views;

		_viewsSupplier = null;
	}

	@JsonIgnore
	public void setViews(
		UnsafeSupplier<Double, Exception> viewsUnsafeSupplier) {

		_viewsSupplier = () -> {
			try {
				return viewsUnsafeSupplier.get();
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
	protected Double views;

	@JsonIgnore
	private Supplier<Double> _viewsSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PerformanceTopAssetItem)) {
			return false;
		}

		PerformanceTopAssetItem performanceTopAssetItem =
			(PerformanceTopAssetItem)object;

		return Objects.equals(toString(), performanceTopAssetItem.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Double downloads = getDownloads();

		if (downloads != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"downloads\": ");

			sb.append(downloads);
		}

		Double engagement = getEngagement();

		if (engagement != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"engagement\": ");

			sb.append(engagement);
		}

		Double impressions = getImpressions();

		if (impressions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"impressions\": ");

			sb.append(impressions);
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

		Trend trend = getTrend();

		if (trend != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"trend\": ");

			sb.append(String.valueOf(trend));
		}

		Double views = getViews();

		if (views != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"views\": ");

			sb.append(views);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.analytics.cms.rest.dto.v1_0.PerformanceTopAssetItem",
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
// LIFERAY-REST-BUILDER-HASH:-1390459475