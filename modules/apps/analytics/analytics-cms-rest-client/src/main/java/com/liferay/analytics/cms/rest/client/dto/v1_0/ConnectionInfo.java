/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.dto.v1_0;

import com.liferay.analytics.cms.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.cms.rest.client.serdes.v1_0.ConnectionInfoSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class ConnectionInfo implements Cloneable, Serializable {

	public static ConnectionInfo toDTO(String json) {
		return ConnectionInfoSerDes.toDTO(json);
	}

	public String getAnalyticsSettingsPortletURL() {
		return analyticsSettingsPortletURL;
	}

	public void setAnalyticsSettingsPortletURL(
		String analyticsSettingsPortletURL) {

		this.analyticsSettingsPortletURL = analyticsSettingsPortletURL;
	}

	public void setAnalyticsSettingsPortletURL(
		UnsafeSupplier<String, Exception>
			analyticsSettingsPortletURLUnsafeSupplier) {

		try {
			analyticsSettingsPortletURL =
				analyticsSettingsPortletURLUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String analyticsSettingsPortletURL;

	public Boolean getConnectedToAnalyticsCloud() {
		return connectedToAnalyticsCloud;
	}

	public void setConnectedToAnalyticsCloud(
		Boolean connectedToAnalyticsCloud) {

		this.connectedToAnalyticsCloud = connectedToAnalyticsCloud;
	}

	public void setConnectedToAnalyticsCloud(
		UnsafeSupplier<Boolean, Exception>
			connectedToAnalyticsCloudUnsafeSupplier) {

		try {
			connectedToAnalyticsCloud =
				connectedToAnalyticsCloudUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean connectedToAnalyticsCloud;

	public Boolean getConnectedToSpace() {
		return connectedToSpace;
	}

	public void setConnectedToSpace(Boolean connectedToSpace) {
		this.connectedToSpace = connectedToSpace;
	}

	public void setConnectedToSpace(
		UnsafeSupplier<Boolean, Exception> connectedToSpaceUnsafeSupplier) {

		try {
			connectedToSpace = connectedToSpaceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean connectedToSpace;

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setIsAdmin(
		UnsafeSupplier<Boolean, Exception> isAdminUnsafeSupplier) {

		try {
			isAdmin = isAdminUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean isAdmin;

	public String getSiteEditDepotEntryDepotAdminPortletURL() {
		return siteEditDepotEntryDepotAdminPortletURL;
	}

	public void setSiteEditDepotEntryDepotAdminPortletURL(
		String siteEditDepotEntryDepotAdminPortletURL) {

		this.siteEditDepotEntryDepotAdminPortletURL =
			siteEditDepotEntryDepotAdminPortletURL;
	}

	public void setSiteEditDepotEntryDepotAdminPortletURL(
		UnsafeSupplier<String, Exception>
			siteEditDepotEntryDepotAdminPortletURLUnsafeSupplier) {

		try {
			siteEditDepotEntryDepotAdminPortletURL =
				siteEditDepotEntryDepotAdminPortletURLUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String siteEditDepotEntryDepotAdminPortletURL;

	public Boolean getSiteSyncedToAnalyticsCloud() {
		return siteSyncedToAnalyticsCloud;
	}

	public void setSiteSyncedToAnalyticsCloud(
		Boolean siteSyncedToAnalyticsCloud) {

		this.siteSyncedToAnalyticsCloud = siteSyncedToAnalyticsCloud;
	}

	public void setSiteSyncedToAnalyticsCloud(
		UnsafeSupplier<Boolean, Exception>
			siteSyncedToAnalyticsCloudUnsafeSupplier) {

		try {
			siteSyncedToAnalyticsCloud =
				siteSyncedToAnalyticsCloudUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean siteSyncedToAnalyticsCloud;

	@Override
	public ConnectionInfo clone() throws CloneNotSupportedException {
		return (ConnectionInfo)super.clone();
	}

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
		return ConnectionInfoSerDes.toJSON(this);
	}

}