/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.GoogleAnalyticsConfigurationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class GoogleAnalyticsConfiguration implements Cloneable, Serializable {

	public static GoogleAnalyticsConfiguration toDTO(String json) {
		return GoogleAnalyticsConfigurationSerDes.toDTO(json);
	}

	public String getGoogleAnalytics4Config() {
		return googleAnalytics4Config;
	}

	public void setGoogleAnalytics4Config(String googleAnalytics4Config) {
		this.googleAnalytics4Config = googleAnalytics4Config;
	}

	public void setGoogleAnalytics4Config(
		UnsafeSupplier<String, Exception>
			googleAnalytics4ConfigUnsafeSupplier) {

		try {
			googleAnalytics4Config = googleAnalytics4ConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String googleAnalytics4Config;

	public String getGoogleAnalytics4Id() {
		return googleAnalytics4Id;
	}

	public void setGoogleAnalytics4Id(String googleAnalytics4Id) {
		this.googleAnalytics4Id = googleAnalytics4Id;
	}

	public void setGoogleAnalytics4Id(
		UnsafeSupplier<String, Exception> googleAnalytics4IdUnsafeSupplier) {

		try {
			googleAnalytics4Id = googleAnalytics4IdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String googleAnalytics4Id;

	public String getGoogleAnalyticsConfig() {
		return googleAnalyticsConfig;
	}

	public void setGoogleAnalyticsConfig(String googleAnalyticsConfig) {
		this.googleAnalyticsConfig = googleAnalyticsConfig;
	}

	public void setGoogleAnalyticsConfig(
		UnsafeSupplier<String, Exception> googleAnalyticsConfigUnsafeSupplier) {

		try {
			googleAnalyticsConfig = googleAnalyticsConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String googleAnalyticsConfig;

	public String getGoogleAnalyticsCreateMethodConfig() {
		return googleAnalyticsCreateMethodConfig;
	}

	public void setGoogleAnalyticsCreateMethodConfig(
		String googleAnalyticsCreateMethodConfig) {

		this.googleAnalyticsCreateMethodConfig =
			googleAnalyticsCreateMethodConfig;
	}

	public void setGoogleAnalyticsCreateMethodConfig(
		UnsafeSupplier<String, Exception>
			googleAnalyticsCreateMethodConfigUnsafeSupplier) {

		try {
			googleAnalyticsCreateMethodConfig =
				googleAnalyticsCreateMethodConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String googleAnalyticsCreateMethodConfig;

	public String getGoogleAnalyticsId() {
		return googleAnalyticsId;
	}

	public void setGoogleAnalyticsId(String googleAnalyticsId) {
		this.googleAnalyticsId = googleAnalyticsId;
	}

	public void setGoogleAnalyticsId(
		UnsafeSupplier<String, Exception> googleAnalyticsIdUnsafeSupplier) {

		try {
			googleAnalyticsId = googleAnalyticsIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String googleAnalyticsId;

	@Override
	public GoogleAnalyticsConfiguration clone()
		throws CloneNotSupportedException {

		return (GoogleAnalyticsConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GoogleAnalyticsConfiguration)) {
			return false;
		}

		GoogleAnalyticsConfiguration googleAnalyticsConfiguration =
			(GoogleAnalyticsConfiguration)object;

		return Objects.equals(
			toString(), googleAnalyticsConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return GoogleAnalyticsConfigurationSerDes.toJSON(this);
	}

}