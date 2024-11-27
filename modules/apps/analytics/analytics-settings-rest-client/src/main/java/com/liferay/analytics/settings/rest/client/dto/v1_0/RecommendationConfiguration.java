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

	public Boolean getContentRecommenderMostPopularItemsEnabled() {
		return contentRecommenderMostPopularItemsEnabled;
	}

	public void setContentRecommenderMostPopularItemsEnabled(
		Boolean contentRecommenderMostPopularItemsEnabled) {

		this.contentRecommenderMostPopularItemsEnabled =
			contentRecommenderMostPopularItemsEnabled;
	}

	public void setContentRecommenderMostPopularItemsEnabled(
		UnsafeSupplier<Boolean, Exception>
			contentRecommenderMostPopularItemsEnabledUnsafeSupplier) {

		try {
			contentRecommenderMostPopularItemsEnabled =
				contentRecommenderMostPopularItemsEnabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean contentRecommenderMostPopularItemsEnabled;

	public Boolean getContentRecommenderUserPersonalizationEnabled() {
		return contentRecommenderUserPersonalizationEnabled;
	}

	public void setContentRecommenderUserPersonalizationEnabled(
		Boolean contentRecommenderUserPersonalizationEnabled) {

		this.contentRecommenderUserPersonalizationEnabled =
			contentRecommenderUserPersonalizationEnabled;
	}

	public void setContentRecommenderUserPersonalizationEnabled(
		UnsafeSupplier<Boolean, Exception>
			contentRecommenderUserPersonalizationEnabledUnsafeSupplier) {

		try {
			contentRecommenderUserPersonalizationEnabled =
				contentRecommenderUserPersonalizationEnabledUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean contentRecommenderUserPersonalizationEnabled;

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