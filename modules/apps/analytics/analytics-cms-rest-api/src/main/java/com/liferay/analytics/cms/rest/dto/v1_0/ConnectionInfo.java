/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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
@GraphQLName("ConnectionInfo")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ConnectionInfo")
public class ConnectionInfo implements Serializable {

	public static ConnectionInfo toDTO(String json) {
		return ObjectMapperUtil.readValue(ConnectionInfo.class, json);
	}

	public static ConnectionInfo unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ConnectionInfo.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public String getAnalyticsSettingsPortletURL() {
		if (_analyticsSettingsPortletURLSupplier != null) {
			analyticsSettingsPortletURL =
				_analyticsSettingsPortletURLSupplier.get();

			_analyticsSettingsPortletURLSupplier = null;
		}

		return analyticsSettingsPortletURL;
	}

	public void setAnalyticsSettingsPortletURL(
		String analyticsSettingsPortletURL) {

		this.analyticsSettingsPortletURL = analyticsSettingsPortletURL;

		_analyticsSettingsPortletURLSupplier = null;
	}

	@JsonIgnore
	public void setAnalyticsSettingsPortletURL(
		UnsafeSupplier<String, Exception>
			analyticsSettingsPortletURLUnsafeSupplier) {

		_analyticsSettingsPortletURLSupplier = () -> {
			try {
				return analyticsSettingsPortletURLUnsafeSupplier.get();
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
	protected String analyticsSettingsPortletURL;

	@JsonIgnore
	private Supplier<String> _analyticsSettingsPortletURLSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getConnectedToAnalyticsCloud() {
		if (_connectedToAnalyticsCloudSupplier != null) {
			connectedToAnalyticsCloud =
				_connectedToAnalyticsCloudSupplier.get();

			_connectedToAnalyticsCloudSupplier = null;
		}

		return connectedToAnalyticsCloud;
	}

	public void setConnectedToAnalyticsCloud(
		Boolean connectedToAnalyticsCloud) {

		this.connectedToAnalyticsCloud = connectedToAnalyticsCloud;

		_connectedToAnalyticsCloudSupplier = null;
	}

	@JsonIgnore
	public void setConnectedToAnalyticsCloud(
		UnsafeSupplier<Boolean, Exception>
			connectedToAnalyticsCloudUnsafeSupplier) {

		_connectedToAnalyticsCloudSupplier = () -> {
			try {
				return connectedToAnalyticsCloudUnsafeSupplier.get();
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
	protected Boolean connectedToAnalyticsCloud;

	@JsonIgnore
	private Supplier<Boolean> _connectedToAnalyticsCloudSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getConnectedToSpace() {
		if (_connectedToSpaceSupplier != null) {
			connectedToSpace = _connectedToSpaceSupplier.get();

			_connectedToSpaceSupplier = null;
		}

		return connectedToSpace;
	}

	public void setConnectedToSpace(Boolean connectedToSpace) {
		this.connectedToSpace = connectedToSpace;

		_connectedToSpaceSupplier = null;
	}

	@JsonIgnore
	public void setConnectedToSpace(
		UnsafeSupplier<Boolean, Exception> connectedToSpaceUnsafeSupplier) {

		_connectedToSpaceSupplier = () -> {
			try {
				return connectedToSpaceUnsafeSupplier.get();
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
	protected Boolean connectedToSpace;

	@JsonIgnore
	private Supplier<Boolean> _connectedToSpaceSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getIsAdmin() {
		if (_isAdminSupplier != null) {
			isAdmin = _isAdminSupplier.get();

			_isAdminSupplier = null;
		}

		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;

		_isAdminSupplier = null;
	}

	@JsonIgnore
	public void setIsAdmin(
		UnsafeSupplier<Boolean, Exception> isAdminUnsafeSupplier) {

		_isAdminSupplier = () -> {
			try {
				return isAdminUnsafeSupplier.get();
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
	protected Boolean isAdmin;

	@JsonIgnore
	private Supplier<Boolean> _isAdminSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getSiteEditDepotEntryDepotAdminPortletURL() {
		if (_siteEditDepotEntryDepotAdminPortletURLSupplier != null) {
			siteEditDepotEntryDepotAdminPortletURL =
				_siteEditDepotEntryDepotAdminPortletURLSupplier.get();

			_siteEditDepotEntryDepotAdminPortletURLSupplier = null;
		}

		return siteEditDepotEntryDepotAdminPortletURL;
	}

	public void setSiteEditDepotEntryDepotAdminPortletURL(
		String siteEditDepotEntryDepotAdminPortletURL) {

		this.siteEditDepotEntryDepotAdminPortletURL =
			siteEditDepotEntryDepotAdminPortletURL;

		_siteEditDepotEntryDepotAdminPortletURLSupplier = null;
	}

	@JsonIgnore
	public void setSiteEditDepotEntryDepotAdminPortletURL(
		UnsafeSupplier<String, Exception>
			siteEditDepotEntryDepotAdminPortletURLUnsafeSupplier) {

		_siteEditDepotEntryDepotAdminPortletURLSupplier = () -> {
			try {
				return siteEditDepotEntryDepotAdminPortletURLUnsafeSupplier.
					get();
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
	protected String siteEditDepotEntryDepotAdminPortletURL;

	@JsonIgnore
	private Supplier<String> _siteEditDepotEntryDepotAdminPortletURLSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getSiteSyncedToAnalyticsCloud() {
		if (_siteSyncedToAnalyticsCloudSupplier != null) {
			siteSyncedToAnalyticsCloud =
				_siteSyncedToAnalyticsCloudSupplier.get();

			_siteSyncedToAnalyticsCloudSupplier = null;
		}

		return siteSyncedToAnalyticsCloud;
	}

	public void setSiteSyncedToAnalyticsCloud(
		Boolean siteSyncedToAnalyticsCloud) {

		this.siteSyncedToAnalyticsCloud = siteSyncedToAnalyticsCloud;

		_siteSyncedToAnalyticsCloudSupplier = null;
	}

	@JsonIgnore
	public void setSiteSyncedToAnalyticsCloud(
		UnsafeSupplier<Boolean, Exception>
			siteSyncedToAnalyticsCloudUnsafeSupplier) {

		_siteSyncedToAnalyticsCloudSupplier = () -> {
			try {
				return siteSyncedToAnalyticsCloudUnsafeSupplier.get();
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
	protected Boolean siteSyncedToAnalyticsCloud;

	@JsonIgnore
	private Supplier<Boolean> _siteSyncedToAnalyticsCloudSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ConnectionInfo)) {
			return false;
		}

		ConnectionInfo connectionInfo = (ConnectionInfo)object;

		return Objects.equals(toString(), connectionInfo.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String analyticsSettingsPortletURL = getAnalyticsSettingsPortletURL();

		if (analyticsSettingsPortletURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"analyticsSettingsPortletURL\": ");

			sb.append("\"");

			sb.append(_escape(analyticsSettingsPortletURL));

			sb.append("\"");
		}

		Boolean connectedToAnalyticsCloud = getConnectedToAnalyticsCloud();

		if (connectedToAnalyticsCloud != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"connectedToAnalyticsCloud\": ");

			sb.append(connectedToAnalyticsCloud);
		}

		Boolean connectedToSpace = getConnectedToSpace();

		if (connectedToSpace != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"connectedToSpace\": ");

			sb.append(connectedToSpace);
		}

		Boolean isAdmin = getIsAdmin();

		if (isAdmin != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"isAdmin\": ");

			sb.append(isAdmin);
		}

		String siteEditDepotEntryDepotAdminPortletURL =
			getSiteEditDepotEntryDepotAdminPortletURL();

		if (siteEditDepotEntryDepotAdminPortletURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteEditDepotEntryDepotAdminPortletURL\": ");

			sb.append("\"");

			sb.append(_escape(siteEditDepotEntryDepotAdminPortletURL));

			sb.append("\"");
		}

		Boolean siteSyncedToAnalyticsCloud = getSiteSyncedToAnalyticsCloud();

		if (siteSyncedToAnalyticsCloud != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteSyncedToAnalyticsCloud\": ");

			sb.append(siteSyncedToAnalyticsCloud);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.analytics.cms.rest.dto.v1_0.ConnectionInfo",
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