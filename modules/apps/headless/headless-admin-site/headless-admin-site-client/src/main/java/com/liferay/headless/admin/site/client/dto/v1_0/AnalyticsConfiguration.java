/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.AnalyticsConfigurationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class AnalyticsConfiguration implements Cloneable, Serializable {

	public static AnalyticsConfiguration toDTO(String json) {
		return AnalyticsConfigurationSerDes.toDTO(json);
	}

	public GoogleAnalyticsConfiguration getGoogleAnalyticsConfiguration() {
		return googleAnalyticsConfiguration;
	}

	public void setGoogleAnalyticsConfiguration(
		GoogleAnalyticsConfiguration googleAnalyticsConfiguration) {

		this.googleAnalyticsConfiguration = googleAnalyticsConfiguration;
	}

	public void setGoogleAnalyticsConfiguration(
		UnsafeSupplier<GoogleAnalyticsConfiguration, Exception>
			googleAnalyticsConfigurationUnsafeSupplier) {

		try {
			googleAnalyticsConfiguration =
				googleAnalyticsConfigurationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected GoogleAnalyticsConfiguration googleAnalyticsConfiguration;

	public String getMatomoAnalyticsScript() {
		return matomoAnalyticsScript;
	}

	public void setMatomoAnalyticsScript(String matomoAnalyticsScript) {
		this.matomoAnalyticsScript = matomoAnalyticsScript;
	}

	public void setMatomoAnalyticsScript(
		UnsafeSupplier<String, Exception> matomoAnalyticsScriptUnsafeSupplier) {

		try {
			matomoAnalyticsScript = matomoAnalyticsScriptUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String matomoAnalyticsScript;

	@Override
	public AnalyticsConfiguration clone() throws CloneNotSupportedException {
		return (AnalyticsConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AnalyticsConfiguration)) {
			return false;
		}

		AnalyticsConfiguration analyticsConfiguration =
			(AnalyticsConfiguration)object;

		return Objects.equals(toString(), analyticsConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AnalyticsConfigurationSerDes.toJSON(this);
	}

}