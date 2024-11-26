/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.client.dto.v1_0;

import com.liferay.analytics.settings.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.settings.rest.client.serdes.v1_0.RecommendationConfigurationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public class RecommendationConfiguration implements Cloneable, Serializable {

	public static RecommendationConfiguration toDTO(String json) {
		return RecommendationConfigurationSerDes.toDTO(json);
	}

	public Boolean getMostPopularContentEnabled() {
		return mostPopularContentEnabled;
	}

	public void setMostPopularContentEnabled(
		Boolean mostPopularContentEnabled) {

		this.mostPopularContentEnabled = mostPopularContentEnabled;
	}

	public void setMostPopularContentEnabled(
		UnsafeSupplier<Boolean, Exception>
			mostPopularContentEnabledUnsafeSupplier) {

		try {
			mostPopularContentEnabled =
				mostPopularContentEnabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean mostPopularContentEnabled;

	public Boolean getUserContentEnabled() {
		return userContentEnabled;
	}

	public void setUserContentEnabled(Boolean userContentEnabled) {
		this.userContentEnabled = userContentEnabled;
	}

	public void setUserContentEnabled(
		UnsafeSupplier<Boolean, Exception> userContentEnabledUnsafeSupplier) {

		try {
			userContentEnabled = userContentEnabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean userContentEnabled;

	@Override
	public RecommendationConfiguration clone()
		throws CloneNotSupportedException {

		return (RecommendationConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RecommendationConfiguration)) {
			return false;
		}

		RecommendationConfiguration recommendationConfiguration =
			(RecommendationConfiguration)object;

		return Objects.equals(
			toString(), recommendationConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RecommendationConfigurationSerDes.toJSON(this);
	}

}